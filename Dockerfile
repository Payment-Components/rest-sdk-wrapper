FROM eclipse-temurin:8-jre-focal

COPY ./target/*.jar /work/app.jar
WORKDIR /work

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]