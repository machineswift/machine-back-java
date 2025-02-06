# 使用官方的Java运行时作为父镜像
FROM swr.cn-east-3.myhuaweicloud.com/machine-cloud-im/machine-image_21_a:v1.1

# 设置工作目录
WORKDIR /app

# 将当前目录下的jar包复制到docker容器的/目录下
ADD ./machine-tests/machine-temp-test/target/machine-temp-test.jar .

# 暴露端口，并设置容器启动时执行命令
EXPOSE 8080

# 配置日期
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' > /etc/timezone

# 启动参数
ENV JAVA_OPTS="-Xms256m -Xmx2048m -XX:MaxGCPauseMillis=256 -Dserver.port=8080 \
-Dspring.profiles.active=DEV \
-Dspring.security.strategy=MODE_INHERITABLETHREADLOCAL \
-Dmachine.nacos.server-addr=XXX.XXX.XXX.XXX:8848 \
-Dmachine.nacos.username=nacos \
-Dmachine.nacos.password=nacos"
ENV PARAMS=""

# 指定docker容器启动时运行jar包
ENTRYPOINT ["sh","-c","java ${SKY_AGENT} -jar $JAVA_OPTS machine-temp-test.jar $PARAMS"]