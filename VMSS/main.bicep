// VMSS + Load Balancer + CPU autoscale (nginx on Ubuntu)
// Deploy at resource-group scope:
//   az group create -n arouissTask12 -l westus
//   az deployment group create -g arouissTask12 -f main.bicep \
//     --parameters adminSshKey="$(cat ~/.ssh/id_rsa.pub)"

param location string = resourceGroup().location
param prefix string = 'arouiss'
param adminUsername string = 'azureuser'

@description('SSH public key (contents of your .pub file)')
param adminSshKey string

param vmSize string = 'Standard_D2s_v3'

var vnetName = '${prefix}-vnet'
var subnetName = 'subnet-${prefix}12'
var nsgName = '${prefix}-nsg'
var pipName = '${prefix}-pip-lb'
var lbName = '${prefix}-lb'
var vmssName = '${prefix}-VMSS'
var frontendName = 'frontIP'
var poolName = '${prefix}-backend'
var probeName = 'probe-http'
var ruleName = '${prefix}-rule'

var cloudInit = '''
#cloud-config
packages:
  - nginx
runcmd:
  - systemctl enable --now nginx
  - echo "<h1>Served by $(hostname)</h1>" > /var/www/html/index.html
'''

// Step 1 is the resource group (created with az group create, see top)

// NSG: allow HTTP and SSH
resource nsg 'Microsoft.Network/networkSecurityGroups@2023-11-01' = {
  name: nsgName
  location: location
  properties: {
    securityRules: [
      {
        name: 'allow-http'
        properties: {
          priority: 100
          direction: 'Inbound'
          access: 'Allow'
          protocol: 'Tcp'
          sourcePortRange: '*'
          destinationPortRange: '80'
          sourceAddressPrefix: '*'
          destinationAddressPrefix: '*'
        }
      }
      {
        name: 'allow-ssh'
        properties: {
          priority: 110
          direction: 'Inbound'
          access: 'Allow'
          protocol: 'Tcp'
          sourcePortRange: '*'
          destinationPortRange: '22'
          sourceAddressPrefix: '*'
          destinationAddressPrefix: '*'
        }
      }
    ]
  }
}

// Step 2: Virtual network + subnet (with NSG)
resource vnet 'Microsoft.Network/virtualNetworks@2023-11-01' = {
  name: vnetName
  location: location
  properties: {
    addressSpace: {
      addressPrefixes: ['10.0.0.0/16']
    }
    subnets: [
      {
        name: subnetName
        properties: {
          addressPrefix: '10.0.1.0/24'
          networkSecurityGroup: {
            id: nsg.id
          }
        }
      }
    ]
  }
}

// Step 3: Public IP + Load balancer (frontend, pool, probe, rule)
resource pip 'Microsoft.Network/publicIPAddresses@2023-11-01' = {
  name: pipName
  location: location
  sku: {
    name: 'Standard'
  }
  properties: {
    publicIPAllocationMethod: 'Static'
  }
}

resource lb 'Microsoft.Network/loadBalancers@2023-11-01' = {
  name: lbName
  location: location
  sku: {
    name: 'Standard'
  }
  properties: {
    frontendIPConfigurations: [
      {
        name: frontendName
        properties: {
          publicIPAddress: {
            id: pip.id
          }
        }
      }
    ]
    backendAddressPools: [
      {
        name: poolName
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
        name: ruleName
        properties: {
          protocol: 'Tcp'
          frontendPort: 80
          backendPort: 80
          idleTimeoutInMinutes: 4
          enableFloatingIP: false
          frontendIPConfiguration: {
            id: resourceId('Microsoft.Network/loadBalancers/frontendIPConfigurations', lbName, frontendName)
          }
          backendAddressPool: {
            id: resourceId('Microsoft.Network/loadBalancers/backendAddressPools', lbName, poolName)
          }
          probe: {
            id: resourceId('Microsoft.Network/loadBalancers/probes', lbName, probeName)
          }
        }
      }
    ]
  }
}

// Step 4: VM Scale Set (2 instances, nginx via cloud-init, attached to LB pool)
resource vmss 'Microsoft.Compute/virtualMachineScaleSets@2024-03-01' = {
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
      mode: 'Automatic'
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
          caching: 'ReadWrite'
          managedDisk: {
            storageAccountType: 'Standard_LRS'
          }
        }
      }
      osProfile: {
        computerNamePrefix: prefix
        adminUsername: adminUsername
        customData: base64(cloudInit)
        linuxConfiguration: {
          disablePasswordAuthentication: true
          ssh: {
            publicKeys: [
              {
                path: '/home/${adminUsername}/.ssh/authorized_keys'
                keyData: adminSshKey
              }
            ]
          }
        }
      }
      networkProfile: {
        networkInterfaceConfigurations: [
          {
            name: '${prefix}-nic'
            properties: {
              primary: true
              ipConfigurations: [
                {
                  name: 'ipconfig1'
                  properties: {
                    subnet: {
                      id: '${vnet.id}/subnets/${subnetName}'
                    }
                    loadBalancerBackendAddressPools: [
                      {
                        id: resourceId('Microsoft.Network/loadBalancers/backendAddressPools', lbName, poolName)
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
    lb
  ]
}

// Autoscale: min 2, max 4
//   scale out: avg CPU > 40 for 2 min -> +1
//   scale in:  avg CPU < 20 for 2 min -> -1
resource autoscale 'Microsoft.Insights/autoscalesettings@2022-10-01' = {
  name: '${prefix}-autoscale'
  location: location
  properties: {
    enabled: true
    targetResourceUri: vmss.id
    profiles: [
      {
        name: 'cpu-profile'
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
            }
            scaleAction: {
              direction: 'Increase'
              type: 'ChangeCount'
              value: '1'
              cooldown: 'PT5M'
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
            }
            scaleAction: {
              direction: 'Decrease'
              type: 'ChangeCount'
              value: '1'
              cooldown: 'PT5M'
            }
          }
        ]
      }
    ]
  }
}

output lbPublicIp string = pip.properties.ipAddress
