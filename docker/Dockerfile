FROM openjdk:11-jdk-slim
ENV DATABASE_PORT=${DATABASE_PORT}
COPY evatoolapp.jar evatoolapp.jar
ENTRYPOINT ["java", "-jar", "evatoolapp.jar"]
