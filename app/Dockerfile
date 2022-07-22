FROM docker.io/alpine:latest

RUN apk --update add openjdk11

RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

COPY ./target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "/app.jar" ]