# Estágio de Build: Usa uma imagem que já tem Maven e JDK prontos
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copia apenas o pom.xml primeiro para aproveitar o cache de dependências
COPY pom.xml .
# Baixa as dependências (se o pom.xml não mudou, o Docker pula essa etapa na próxima vez)
RUN mvn dependency:go-offline

# Copia o código fonte e faz o build
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio Final: Apenas o JRE para rodar a aplicação (imagem leve)
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
EXPOSE 8080

# Copia o JAR gerado no estágio anterior
# NOTA: Verifique se o nome do arquivo gerado na pasta target é realmente esse.
# O Maven costuma gerar algo como 'nome-versao.jar'. O asterisco ajuda a pegar qualquer .jar.
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar" ]