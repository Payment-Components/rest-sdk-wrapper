FROM openjdk:8-alpine
COPY ./target/*.jar /tmp/app.jar

EXPOSE 8080
CMD ["java", "-jar", "/tmp/app.jar"]
