FROM openjdk:17-jdk

WORKDIR /app

# Copy the first JAR file in the target directory
COPY target/*.jar application.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "application.jar"]
