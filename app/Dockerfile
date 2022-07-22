FROM maven:3.6.3-jdk-11-slim AS MAVEN_BUILD

COPY pom.xml /build/

COPY src /build/src/

WORKDIR /build/

RUN mvn -T 2C clean package

FROM alpine:latest

RUN apk --update add openjdk11

RUN addgroup -S spring && adduser -S spring -G spring

USER spring:spring

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "app.jar" ]
