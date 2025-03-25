FROM eclipse-temurin:21-jdk-jammy AS build-image
WORKDIR /app

COPY .mvn/ .mvn
COPY ./src/main/ ./src/main/
COPY mvnw pom.xml ./

RUN chmod +x mvnw
RUN ./mvnw clean package

FROM eclipse-temurin:21-jre-jammy

COPY --from=build-image /app/target/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]