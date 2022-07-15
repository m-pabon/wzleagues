# Wz Leagues

## Tech Stack
### Service Development
- Java
- Spring Boot Web
- Spring HATEOAS
- Spring Security
- Jasypt Encription
### Data Layer
- MongoDB hosted on MongoDB Atlas
### Deployment
- Docker + Kubernetes
- Github Actions for CI/CD

## Setup for Local Development

### Environment Variables
You need to set the following environment variables with their correct values in order to run the project

- `APP_ENCRYPTION_PASSWORD`

## Build Commands

### Gradle Local Build
`APP_ENCRYPTION_PASSWORD=$APP_ENCRYPTION_PASSWORD ./gradlew clean build`

### Gradle Local Build + Run
`APP_ENCRYPTION_PASSWORD=$APP_ENCRYPTION_PASSWORD ./gradlew bootRun`

### Run just the jar
`java -jar build/libs/playerService-0.0.1-SNAPSHOT.jar`

## Documentation

### Github Pages
[http://m-pabon.github.io/wzleagues](http://m-pabon.github.io/wzleagues)

### OpenAPI JSON spec
[http://localhost:8080/api/v3/api-docs/](http://localhost:8080/api/v3/api-docs/)

### Swagger UI
[http://localhost:8080/api/swagger-ui.html](http://localhost:8080/api/swagger-ui.html)

## Useful JAR commands

### Unpack Jar
`jar -xf playerService-0.0.1-SNAPSHOT.jar`

### Extract the JAR contents into a directory for each layer
java -Djarmode=layertools -jar build/libs/playerService-0.0.1-SNAPSHOT.jar extract --destination target/extracted

## Docker Commands

### Repo
[https://hub.docker.com/repository/docker/mpabon/wzleagues/general](https://hub.docker.com/repository/docker/mpabon/wzleagues/general)

### Build Docker Image
`docker build --build-arg KEY=$APP_ENCRYPTION_PASSWORD -t mpabon/wzleagues .`

### Run Docker Container
`docker run -p 8080:8080 mpabon/wzleagues`

### Push Docker Image
`docker push mpabon/wzleagues:<tag>`


### Run ash shell inside container
`docker run -ti --entrypoint /bin/sh mpabon/wzleagues` <br/>
`docker exec -ti <container name> /bin/sh`

## Jasypt Encryption
This project uses jasypt encryption to store credentials safely. To encrypt a value download the sources from the jasypt
repo and use the `encrypt.sh` script. You can store the encrypted value in the `resources/encrypted.properties` file.

Example usages:<br/>
`chmod +x encrypt.sh decrypt.sh` <br/>
`./encrypt.sh input=<value to encrypt> password=<encryption key>`<br/>
`./decrypt.sh input=<value to decrypt> password=<encryption key>`
