FROM openjdk:17

COPY ./target/*.jar /app.jar

CMD ["--server.port=10080"]

EXPOSE 10080

ENTRYPOINT ["java","-jar","/app.jar"]