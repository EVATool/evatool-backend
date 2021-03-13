FROM openjdk:11-jdk-slim
VOLUME /tmp
ARG JAR_FILE=evatoolapp.jar
COPY ${JAR_FILE} evatoolapp.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /evatoolapp.jar ${0} ${@}"]
