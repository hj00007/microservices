# Stage 1: Build the application using Maven
FROM maven:3.8.5-openjdk-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

# Stage 2: Run the application using a JDK runtime
FROM openjdk:17-jdk-alpine
COPY --from=build /home/app/target/*.jar /usr/local/lib/product.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/usr/local/lib/product.jar"]