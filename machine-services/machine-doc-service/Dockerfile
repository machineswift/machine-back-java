FROM linuxserver/libreoffice:25.2.5

# 设置非交互模式和时区
ENV DEBIAN_FRONTEND=noninteractive
ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime \
    && echo $TZ > /etc/timezone

# ---------------------------------------------------------
# 安装 Java 21 和 curl（最小安装）
# ---------------------------------------------------------
RUN apk update && apk add --no-cache \
    openjdk21-jre-headless \
    curl \
    fontconfig \
    ttf-dejavu \
    ttf-liberation \
    wqy-zenhei \
    font-noto-cjk \
    ttf-droid \
    ttf-freefont \
    ttf-inconsolata \
    && rm -rf /var/cache/apk/*

# ---------------------------------------------------------
# 创建工作目录与 SkyWalking Agent 目录
# ---------------------------------------------------------
RUN mkdir -p /opt/skywalking-agent /app

COPY ./skywalking-agent /opt/skywalking-agent

WORKDIR /app
COPY ./target/machine-doc-service.jar .

EXPOSE 8080

ENV SKY_AGENT=""
ENV JAVA_OPTS=""
ENV PARAMS=""

ENTRYPOINT ["sh", "-c", "java ${SKY_AGENT} ${JAVA_OPTS} -jar /app/machine-doc-service.jar ${PARAMS}"]