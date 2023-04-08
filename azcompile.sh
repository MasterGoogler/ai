# Replace <your_acr_name> with the actual ACR name
mvn compile jib:build -Djib.to.image=<your_acr_name>.azurecr.io/trading-orders-system:${project.version}

