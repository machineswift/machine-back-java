# 基础镜像
FROM swr.cn-east-3.myhuaweicloud.com/cloud-machine/sjzx_machine:20250121

# 设置工作目录
WORKDIR /app

# 将当前目录下的jar包复制到docker容器的/目录下
ADD ./machine-servers/machine-xxljob-server/target/machine-xxljob-server.jar .

# 暴露端口，并设置容器启动时执行命令
EXPOSE 8080

# 配置日期
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' > /etc/timezone

# 启动参数
ENV SKY_AGENT=""
ENV JAVA_OPTS=""
ENV PARAMS=""

# 指定docker容器启动时运行jar包
ENTRYPOINT ["sh","-c","java ${SKY_AGENT} -jar $JAVA_OPTS machine-xxljob-server.jar $PARAMS"]