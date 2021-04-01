FROM openjdk:8-jdk-alpine

EXPOSE 5500

ADD target/m-t-service1-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]