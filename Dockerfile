FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/HRMS-0.0.1-SNAPSHOT.jar /app/HRMS.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/HRMS.jar"]
