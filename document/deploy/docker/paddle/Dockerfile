FROM paddlepaddle/paddle:3.3.0

# 安装系统依赖（PDF 处理需要 poppler）
RUN apt-get update && apt-get install -y \
    poppler-utils \
    libpoppler-dev \
    && rm -rf /var/lib/apt/lists/*

# 设置工作目录
WORKDIR /app

# 复制并安装 Python 依赖（统一用阿里云源）
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt \
    -i https://mirrors.aliyun.com/pypi/simple/

# 复制应用代码
COPY main.py .

# 创建上传文件目录
RUN mkdir -p /tmp/uploads

# 暴露端口
EXPOSE 8000

# 启动服务
CMD ["python", "main.py"]