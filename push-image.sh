mvn clean install -Dquarkus.container-image.build=true 
podman tag localhost/nextgen-api:1.0.0-SNAPSHOT quay.io/wael2000/nextgen-api 
podman push quay.io/wael2000/nextgen-api 