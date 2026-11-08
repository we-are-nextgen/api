
### Complete resource definitions, API versions, properties and code Bicep examples
https://learn.microsoft.com/en-us/azure/templates/?utm_source=chatgpt.com

# Azure VMSS Autoscaling Lab

Deploy an Ubuntu **Nginx VM Scale Set** behind a public Azure Load Balancer using [`ysn-azure-vmss.bicep`](ysn-azure-vmss.bicep). Test automatic scaling **2 → 3 → 4 → 3 → 2**, then remove the lab.

**Configuration:** `eastus` · `Standard_D2_v4` (2 vCPUs/VM) · 2–4 instances · scale out at **CPU > 40%** · scale in at **CPU < 20%** · **2-minute** CPU averaging window · **1-minute** cooldown · 1-minute metric granularity. Azure may take additional time to evaluate rules and provision or remove VMs.

**Prerequisites:** Azure CLI, access to an Azure subscription, and an SSH public key at `~/.ssh/ysn-azure.pub`. Run these commands from the folder containing the Bicep file.

## 1. Create the resource group

The template deploys at **resource-group scope**, so create the group first.

```bash
az login
az group create -n ysn-rg-lab -l eastus
```

## 2. Validate and deploy Bicep

```bash
az bicep build -f ysn-azure-vmss.bicep
az deployment group validate -g ysn-rg-lab -f ysn-azure-vmss.bicep -p sshPublicKey="$(cat ~/.ssh/ysn-azure.pub)"
az deployment group create -g ysn-rg-lab -f ysn-azure-vmss.bicep -p sshPublicKey="$(cat ~/.ssh/ysn-azure.pub)"
```

Creates the VNet, NSG, public IP, Load Balancer, two initial Nginx VMs in `ysn-vmss`, and Azure Monitor autoscaling.

## 4. Start CPU stress to trigger scale-out

Run on all current instances. Each temporary service loads one CPU core and stops after **20 minutes**.

```bash
for ID in $(az vmss list-instances -g ysn-rg-lab -n ysn-vmss --query '[].instanceId' -o tsv); do
  az vmss run-command invoke -g ysn-rg-lab -n ysn-vmss \
    --instance-id "$ID" --command-id RunShellScript \
    --scripts "systemctl stop ysn-cpu-test.service 2>/dev/null || true; systemctl reset-failed ysn-cpu-test.service 2>/dev/null || true; systemd-run --unit=ysn-cpu-test --property=RuntimeMaxSec=1200 /bin/bash -c 'exec yes > /dev/null'; systemctl is-active ysn-cpu-test.service" \
    --query 'value[].message' -o tsv
done
```

Expect `active` and about **50% average CPU** across the original two-vCPU instances.

## 6. Stop stress and watch scale-in: 4 → 3 → 2

```bash
for ID in $(az vmss list-instances -g ysn-rg-lab -n ysn-vmss --query '[].instanceId' -o tsv); do
  az vmss run-command invoke -g ysn-rg-lab -n ysn-vmss \
    --instance-id "$ID" --command-id RunShellScript \
    --scripts 'systemctl stop ysn-cpu-test.service 2>/dev/null || true; systemctl is-active ysn-cpu-test.service || true' \
    --query 'value[].message' -o tsv
done
```

Expect `inactive`. Keep **Terminal A/B** running and repeat the Activity Log command from Step 5. Once average CPU stays below 20%, Azure should eventually scale down to the **minimum of 2**. Do not manually resize the VMSS for this test.

## 7. Delete everything

```bash
az group delete -n ysn-rg-lab --yes --no-wait
az group exists -n ysn-rg-lab
```

The delete runs asynchronously; `az group exists` returns `false` once deletion is finished. Deleting the group removes the lab resources and avoids ongoing VM charges.


