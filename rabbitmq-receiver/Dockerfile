FROM openjdk:17

COPY ./target/*.jar /app.jar

CMD ["--server.port=10221"]

EXPOSE 10221

ENTRYPOINT ["java","-jar","/app.jar"]