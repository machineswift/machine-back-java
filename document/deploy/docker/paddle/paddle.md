# 构建镜像
```bash
cd paddle-ocr-api
docker build -t paddle-ocr-api .
```

# 运行容器（暴露 8000 端口）
```bash
docker run -d \
--name paddle \
--hostname paddle \
--network machine \
-p 8000:8000 \
--shm-size=4g \
--cpus=4 \
--memory=8g \
--restart unless-stopped \
paddle-ocr-api
```


# PaddleOCR API 接口文档

服务启动后，访问 `http://你的IP:8000`

---

## 接口概览

| 接口              | 方法   | 说明          |
|-----------------|------|-------------|
| `/health`       | GET  | 健康检查，查看可用能力 |
| `/ocr/image`    | POST | 图片 OCR 识别   |
| `/ocr/pdf`      | POST | PDF OCR 识别  |
| `/ocr/document` | POST | 文档结构分析（含表格） |
| `/ocr/batch`    | POST | 批量图片 OCR    |

---


# 图片识别
```bash
curl -X POST http://localhost:8000/ocr/image \
-F "file=@your_image.jpg"
```

# PDF 识别
```bash
curl -X POST http://localhost:8000/ocr/pdf \
-F "file=@document.pdf"
```

# 文档结构分析
```bash
curl -X POST http://localhost:8000/ocr/document \
-F "file=@report.png"
```