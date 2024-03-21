FROM java:8

COPY *.jar /app.jar

CMD ["--server.port=10080"]

EXPOSE 10080

ENTRYPOINT ["java","-jar","/app.jar"]