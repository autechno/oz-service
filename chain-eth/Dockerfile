# 基于 Eclipse Temurin 的 JDK 17 镜像
FROM eclipse-temurin:17-jdk-alpine

# 设置工作目录
WORKDIR /app

# 将项目构建的 JAR 文件复制到镜像
COPY target/chain-eth-3.0.jar chain-eth.jar

#ENV SERVER_PORT=8102
#ENV SPRING_PROFILES_ACTIVE=test

# 暴露端口
EXPOSE 8080

# 启动应用
ENTRYPOINT ["java", "-jar", "chain-eth.jar"]
