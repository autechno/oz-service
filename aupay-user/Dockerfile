# 基于 Eclipse Temurin 的 JDK 17 镜像
FROM eclipse-temurin:17-jdk-alpine

# 设置工作目录
WORKDIR /app

# 将项目构建的 JAR 文件复制到镜像
COPY target/aupay-user-3.0.jar aupay-user.jar

ENV SPRING_PROFILES_ACTIVE=test

# 暴露端口
EXPOSE 8080

# 启动应用
ENTRYPOINT ["java", "-jar", "aupay-user.jar"]
