# Login to Azure CLI
az login

# Configure the ACR
export ACR_NAME=<your_acr_name>
az acr login --name $ACR_NAME

