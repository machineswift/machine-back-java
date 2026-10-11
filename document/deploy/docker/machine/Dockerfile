# Java 25 JRE Alpine
FROM eclipse-temurin:25-jre-alpine

# 时区、字体及必要工具
RUN apk add --no-cache \
    tzdata \
    fontconfig \
    font-dejavu \
    wqy-zenhei \
    curl \
    && ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo 'Asia/Shanghai' > /etc/timezone \
    && fc-cache -f \
    && rm -rf /var/cache/fontconfig/*

# 创建目录
RUN mkdir -p /opt/skywalking-agent

# 复制 skywalking-agent
COPY skywalking-agent /opt/skywalking-agent