FROM openjdk:17

COPY ./target/*.jar /app.jar

CMD ["--server.port=10020"]

EXPOSE 10020

ENTRYPOINT ["java","-jar","/app.jar"]