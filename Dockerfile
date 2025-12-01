# Etapa de build
FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven

WORKDIR /app

COPY . .

RUN mvn clean install -DskipTests

# Etapa final (imagem estável de Java)
FROM eclipse-temurin:21-jre

WORKDIR /app

EXPOSE 8080

# Cuidado: aqui é barra normal "/" (Linux), não "\"
COPY --from=build /app/target/encurtador-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
