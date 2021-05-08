FROM openjdk:11-jdk-slim
COPY target/evatool-0.0.1-SNAPSHOT.jar evatoolapp.jar
ENTRYPOINT ["java", "-jar", "evatoolapp.jar"]
