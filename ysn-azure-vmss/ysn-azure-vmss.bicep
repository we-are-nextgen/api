// NextGen Azure Bicep VM Scale Set Lab
// Two initial private Ubuntu + Nginx instances, one public Load Balancer.

targetScope = 'resourceGroup'

// STEP 1: Parameters for the lab.
@description('Azure region. Defaults to the resource group region.')
param location string = resourceGroup().location

@description('SSH public key for Ubuntu; never provide the private key.')
param sshPublicKey string

@description('VM size. Choose a SKU available in your subscription and region.')
param vmSize string = 'Standard_D2_v4'

@description('Linux administrator username.')
param adminUsername string = 'azureuser'

// STEP 2: Shared names.
var prefix = 'ysn'
var lbName = '${prefix}-lb'
var frontendName = '${prefix}-frontend'
var backendName = '${prefix}-backend'
var probeName = '${prefix}-http-probe'
var nsgName = '${prefix}-nsg'
var vnetName = '${prefix}-vnet'
var subnetName = '${prefix}-subnet'
var vmssName = '${prefix}-vmss'

// STEP 3: Nginx first-boot setup, reused by every VMSS instance.
// $(hostname) gives each instance its own name when you test the load balancer.
var webSetup = '''
#!/bin/bash
set -euxo pipefail
export DEBIAN_FRONTEND=noninteractive
apt-get update
apt-get install -y nginx
cat > /var/www/html/index.html <<HTML
<!doctype html>
<html>
<head><title>NextGen VMSS Lab</title></head>
<body>
<h1>VMSS instance: $(hostname)</h1>
</body>
</html>
HTML
systemctl enable --now nginx
'''

// STEP 4: One Standard static IPv4 public IP (for the load balancer only).
resource publicIp 'Microsoft.Network/publicIPAddresses@2023-11-01' = {
  name: '${prefix}-public-ip'
  location: location
  sku: {
    name: 'Standard'
  }
  properties: {
    publicIPAllocationMethod: 'Static'
    publicIPAddressVersion: 'IPv4'
  }
}

// STEP 5: Allow inbound HTTP. Keep SSH closed to the internet.
resource nsg 'Microsoft.Network/networkSecurityGroups@2023-11-01' = {
  name: nsgName
  location: location
  properties: {
    securityRules: [
      {
        name: '${prefix}-allow-http'
        properties: {
          priority: 100
          direction: 'Inbound'
          access: 'Allow'
          protocol: 'Tcp'
          sourcePortRange: '*'
          destinationPortRange: '80'
          sourceAddressPrefix: 'Internet'
          destinationAddressPrefix: '*'
        }
      }
    ]
  }
}

// STEP 6: Private virtual network and subnet.
resource vnet 'Microsoft.Network/virtualNetworks@2023-11-01' = {
  name: vnetName
  location: location
  properties: {
    addressSpace: {
      addressPrefixes: [
        '10.0.0.0/16'
      ]
    }
    subnets: [
      {
        name: subnetName
        properties: {
          addressPrefix: '10.0.1.0/24'
          defaultOutboundAccess: false
          networkSecurityGroup: {
            id: nsg.id
          }
        }
      }
    ]
  }
}

// STEP 7: Public Standard Load Balancer.
resource lb 'Microsoft.Network/loadBalancers@2023-11-01' = {
  name: lbName
  location: location
  sku: {
    name: 'Standard'
    tier: 'Regional'
  }
  properties: {
    frontendIPConfigurations: [
      {
        name: frontendName
        properties: {
          publicIPAddress: {
            id: publicIp.id
          }
        }
      }
    ]
    backendAddressPools: [
      {
        name: backendName
      }
    ]
    probes: [
      {
        name: probeName
        properties: {
          protocol: 'Http'
          port: 80
          requestPath: '/'
          intervalInSeconds: 5
          numberOfProbes: 2
        }
      }
    ]
    loadBalancingRules: [
      {
        name: '${prefix}-http-rule'
        properties: {
          protocol: 'Tcp'
          frontendPort: 80
          backendPort: 80
          enableFloatingIP: false
          idleTimeoutInMinutes: 4
          enableTcpReset: true
          disableOutboundSnat: true
          frontendIPConfiguration: {
            id: resourceId('Microsoft.Network/loadBalancers/frontendIPConfigurations', lbName, frontendName)
          }
          backendAddressPool: {
            id: resourceId('Microsoft.Network/loadBalancers/backendAddressPools', lbName, backendName)
          }
          probe: {
            id: resourceId('Microsoft.Network/loadBalancers/probes', lbName, probeName)
          }
        }
      }
    ]
    outboundRules: [
      {
        name: '${prefix}-internet-outbound'
        properties: {
          protocol: 'All'
          allocatedOutboundPorts: 1024
          idleTimeoutInMinutes: 4
          enableTcpReset: true
          backendAddressPool: {
            id: resourceId('Microsoft.Network/loadBalancers/backendAddressPools', lbName, backendName)
          }
          frontendIPConfigurations: [
            {
              id: resourceId('Microsoft.Network/loadBalancers/frontendIPConfigurations', lbName, frontendName)
            }
          ]
        }
      }
    ]
  }
}

// STEP 8: VM Scale Set instead of two individual VMs and two individual NICs.

resource vmss 'Microsoft.Compute/virtualMachineScaleSets@2023-09-01' = {
  name: vmssName
  location: location
  sku: {
    name: vmSize
    tier: 'Standard'
    capacity: 2 
  }
  properties: {
    orchestrationMode: 'Uniform'
    overprovision: false
    upgradePolicy: {
      mode: 'Manual'
    }
    virtualMachineProfile: {
      storageProfile: {
        imageReference: {
          publisher: 'Canonical'
          offer: '0001-com-ubuntu-server-jammy'
          sku: '22_04-lts-gen2'
          version: 'latest'
        }
        osDisk: {
          createOption: 'FromImage'
          diskSizeGB: 30
          managedDisk: {
            storageAccountType: 'Standard_LRS'
          }
        }
      }
      osProfile: {
        computerNamePrefix: '${prefix}-web'
        adminUsername: adminUsername
        customData: base64(webSetup)
        linuxConfiguration: {
          disablePasswordAuthentication: true
          ssh: {
            publicKeys: [
              {
                path: '/home/${adminUsername}/.ssh/authorized_keys'
                keyData: sshPublicKey
              }
            ]
          }
        }
      }
      networkProfile: {
        networkInterfaceConfigurations: [
          {
            name: '${prefix}-vmss-nic'
            properties: {
              primary: true
              ipConfigurations: [
                {
                  name: '${prefix}-vmss-ipconfig'
                  properties: {
                    subnet: {
                      id: resourceId('Microsoft.Network/virtualNetworks/subnets', vnetName, subnetName)
                    }
                    loadBalancerBackendAddressPools: [
                      {
                        id: resourceId('Microsoft.Network/loadBalancers/backendAddressPools', lbName, backendName)
                      }
                    ]
                  }
                }
              ]
            }
          }
        ]
      }
    }
  }
  dependsOn: [
    vnet
    lb
  ]
}

// STEP 9: Azure Monitor CPU autoscaling policy.
resource autoscale 'Microsoft.Insights/autoscalesettings@2022-10-01' = {
  name: '${prefix}-vmss-autoscale'
  location: location
  properties: {
    enabled: true
    targetResourceUri: vmss.id
    profiles: [
      {
        name: '${prefix}-cpu-profile'
        capacity: {
          minimum: '2'
          maximum: '4'
          default: '2'
        }
        rules: [
          {
            metricTrigger: {
              metricName: 'Percentage CPU'
              metricResourceUri: vmss.id
              timeGrain: 'PT1M'
              statistic: 'Average'
              timeWindow: 'PT2M'
              timeAggregation: 'Average'
              operator: 'GreaterThan'
              threshold: 40
              dividePerInstance: false
            }
            scaleAction: {
              direction: 'Increase'
              type: 'ChangeCount'
              value: '1'
              cooldown: 'PT1M'
            }
          }
          {
            metricTrigger: {
              metricName: 'Percentage CPU'
              metricResourceUri: vmss.id
              timeGrain: 'PT1M'
              statistic: 'Average'
              timeWindow: 'PT2M'
              timeAggregation: 'Average'
              operator: 'LessThan'
              threshold: 20
              dividePerInstance: false
            }
            scaleAction: {
              direction: 'Decrease'
              type: 'ChangeCount'
              value: '1'
              cooldown: 'PT1M'
            }
          }
        ]
      }
    ]
  }
}

// STEP 10: Useful values after deployment.
output publicIpAddress string = publicIp.properties.ipAddress
output websiteUrl string = 'http://${publicIp.properties.ipAddress}'
output scaleSetName string = vmss.name
output autoscaleName string = autoscale.name
