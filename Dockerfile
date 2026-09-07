FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/*.jar"]
