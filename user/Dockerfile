FROM java:8

COPY *.jar /app.jar

CMD ["--server.port=10020"]

EXPOSE 10020

ENTRYPOINT ["java","-jar","/app.jar"]