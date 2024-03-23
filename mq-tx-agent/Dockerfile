FROM openjdk:17

COPY ./target/*.jar /app.jar
COPY ./project/tasks /tasks

CMD ["--server.port=10270"]

EXPOSE 10270

ENTRYPOINT ["java","-jar","/app.jar"]