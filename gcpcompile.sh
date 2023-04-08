# Replace <your_project_id> with the actual GCP project ID
mvn compile jib:build -Djib.to.image=gcr.io/<your_project_id>/trading-orders-system:${project.version}

