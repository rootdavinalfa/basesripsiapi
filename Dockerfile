FROM openjdk:15-alpine3.11

WORKDIR /app
ADD build/libs/*.jar /app/dvn-backend.jar
ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar /app/dvn-backend.jar


EXPOSE 8080
