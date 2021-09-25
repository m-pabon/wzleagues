FROM gradle:jdk11 as build
WORKDIR /workspace/app

COPY . /workspace/app

RUN ./gradlew clean build
ARG JAR_FILE=build/libs/wzleagues-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} target/application.jar
RUN java -Djarmode=layertools -jar target/application.jar extract --destination target/extracted


FROM openjdk:11-jre-slim
RUN adduser --system --group app
VOLUME /tmp
USER app
ARG EXTRACTED=/workspace/app/target/extracted
WORKDIR application
COPY --from=build ${EXTRACTED}/dependencies/ ./
COPY --from=build ${EXTRACTED}/spring-boot-loader/ ./
COPY --from=build ${EXTRACTED}/snapshot-dependencies/ ./
COPY --from=build ${EXTRACTED}/application/ ./
ENTRYPOINT ["java","-noverify","-XX:TieredStopAtLevel=1","-Dspring.main.lazy-initialization=true","org.springframework.boot.loader.JarLauncher"]