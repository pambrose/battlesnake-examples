FROM openjdk:8-jre-alpine

WORKDIR /app
COPY ./build/libs/simple-battlesnake.jar /app

EXPOSE 8080

CMD []
ENTRYPOINT ["java", "-server", "-jar", "/app/simple-battlesnake.jar"]