FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/coffee-shop.jar /app/coffee-shop.jar

EXPOSE 8080

ENTRYPOINT java \
  -jar /app/coffee-shop.jar \
  --spring.profiles.active=${PROFILE} \
  --db.user.name=${DB_USER_NAME} \
  --db.user.password=${DB_USER_PASSWORD}
