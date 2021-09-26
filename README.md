# wzleagues

## Setup

### Environment Variables
You need to set the following environment variables with their correct values in order to run the project

- `APP_ENCRYPTION_PASSWORD`

## Build Commands

### Gradle Local Build
`./gradlew build`

### Gradle Local Build + Run
`./gradlew bootRun`

### Run just the jar
`java -jar build/libs/wzleagues-0.0.1-SNAPSHOT.jar`

## Documentation

### Github Pages
[http://m-pabon.github.io/wzleagues](http://m-pabon.github.io/wzleagues)

### OpenAPI JSON spec
[http://localhost:8080/v3/api-docs/](http://localhost:8080/v3/api-docs/)

### Swagger UI
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Useful JAR commands

### Unpack Jar
`jar -xf wzleagues-0.0.1-SNAPSHOT.jar`

### Extract the JAR contents into a directory for each layer
java -Djarmode=layertools -jar build/libs/wzleagues-0.0.1-SNAPSHOT.jar extract --destination target/extracted

## Docker Commands

### Repo
[https://hub.docker.com/repository/docker/mpabon/wzleagues/general](https://hub.docker.com/repository/docker/mpabon/wzleagues/general)

### Build Docker Image
`docker build -t mpabon/wzleagues .`

### Run Docker Container
`$ docker run -p 8080:8080 mpabon/wzleagues`


### Run ash shell inside container
`docker run -ti --entrypoint /bin/sh mpabon/wzleagues` <br/>
`docker exec -ti <container name> /bin/sh`

### Build image using Spring Boot docker plugin (instead of custom DOCKERFILE)
`./gradlew bootBuildImage --imageName=mpabon/wzleagues`

## Jasypt Encryption
This project uses jasypt encryption to store credentials safely. To encrypt a value download the sources from the jasypt
repo and use the `encrypt.sh` script. You can store the encrypted value in the `resources/encrypted.properties` file.

Example usages:<br/>
`chmod +x encrypt.sh decrypt.sh` <br/>
`./encrypt.sh input=<value to encrypt> password=<encryption key>`<br/>
`./decrypt.sh input=<value to decrypt> password=<encryption key>`
