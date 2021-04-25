FROM adoptopenjdk/openjdk11:alpine-jre

#copy application WAR/JAR (with libraries inside)
COPY target/task-0.0.1.jar task-0.0.1.jar

EXPOSE 8080

# specify default command
ENTRYPOINT ["java", "-jar", "task-0.0.1.jar"]