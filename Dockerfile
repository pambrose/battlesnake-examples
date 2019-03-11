FROM openjdk:8-jre-alpine

WORKDIR /app
COPY ./build/libs/battlesnake-examples.jar /app

EXPOSE 8080

CMD []
ENTRYPOINT ["java", "-server", "-jar", "/app/battlesnake-examples.jar"]