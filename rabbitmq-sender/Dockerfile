FROM openjdk:17

COPY ./target/*.jar /app.jar

CMD ["--server.port=10220"]

EXPOSE 10220

ENTRYPOINT ["java","-jar","/app.jar"]