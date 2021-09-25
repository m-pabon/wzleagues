# wzleagues

## Build Commands

### Gradle Local Build
`./gradlew build`

### Gradle Local Build + Run
`./gradlew bootRun`

### Run just the jar
`java -jar build/libs/wzleagues-0.0.1-SNAPSHOT.jar`

## Documentation

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

### Build Docker Image
`docker build -t mpabon/wzleagues .`

### Run Docker Container
`$ docker run -p 8080:8080 mpabon/wzleagues`


### Run ash shell inside container
`docker run -ti --entrypoint /bin/sh mpabon/wzleagues` <br/>
`docker exec -ti <container name> /bin/sh`

### Build image using Spring Boot docker plugin (instead of custom DOCKERFILE)
`./gradlew bootBuildImage --imageName=mpabon/wzleagues`
