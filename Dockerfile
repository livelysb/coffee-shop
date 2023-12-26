FROM openjdk:17-jdk-slim

ENV TZ=Asia/Seoul

WORKDIR /app

COPY build/libs/coffee-shop.jar /app/coffee-shop.jar

EXPOSE 8080

ENTRYPOINT java \
  -jar /app/coffee-shop.jar \
  --spring.profiles.active=${PROFILE}
