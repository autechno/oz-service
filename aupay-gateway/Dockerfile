# 基于 Eclipse Temurin 的 JDK 17 镜像
FROM eclipse-temurin:17-jdk-alpine

COPY target/aupay-gateway-3.0.jar /app/aupay-gateway.jar

ENV SERVER_PORT=9000
ENV SPRING_PROFILES_ACTIVE=test

EXPOSE $SERVER_PORT

WORKDIR /app

ENTRYPOINT ["java", "-jar", "./aupay-gateway.jar"]
