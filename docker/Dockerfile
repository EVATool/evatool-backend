FROM openjdk:11-jdk-slim
ENV DATABASE_PORT=${DATABASE_PORT}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD}
COPY evatoolapp.jar evatoolapp.jar
ENTRYPOINT ["java", "-jar", "evatoolapp.jar"]
