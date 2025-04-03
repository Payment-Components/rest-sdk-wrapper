FROM eclipse-temurin:21-jre-jammy

COPY ./target/*.jar /work/app.jar
WORKDIR /work

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]