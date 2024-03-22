FROM openjdk:17

COPY ./target/*.jar /app.jar

CMD ["--server.port=10000"]

EXPOSE 10000

ENTRYPOINT ["java","-jar","/app.jar"]