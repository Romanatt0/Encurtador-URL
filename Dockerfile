# Estágio de Build: Usa Maven com JDK 21
# Mudamos de openjdk-17 para eclipse-temurin-21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Estágio Final: Usa JRE 21
# Mudamos de eclipse-temurin:17 para eclipse-temurin:21
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
EXPOSE 8080

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]