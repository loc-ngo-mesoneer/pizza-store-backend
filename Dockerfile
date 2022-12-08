FROM maven:3.8.6-eclipse-temurin-17-alpine AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests=true

FROM eclipse-temurin:17-alpine
COPY --from=build /usr/src/app/target/*.jar /usr/src/app/pizza-store-backend.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/src/app/pizza-store-backend.jar"]