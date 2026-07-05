import os
import tempfile
from typing import List, Optional
from pathlib import Path

from fastapi import FastAPI, UploadFile, File, Form
from fastapi.responses import JSONResponse
import uvicorn

from paddleocr import PaddleOCR
from pdf2image import convert_from_bytes
from PIL import Image
import numpy as np

app = FastAPI(title="PaddleOCR Full API", version="1.0.0")

# 初始化基础 OCR
# PaddleOCR 3.5.x: use_textline_orientation 替代 use_angle_cls
# CPU 模式默认自动检测，无需 use_gpu
ocr = PaddleOCR(lang='ch', use_textline_orientation=True)

# 初始化高级能力（表格识别、版面分析）
try:
    table_engine = PaddleOCR(
        lang='ch',
        use_textline_orientation=True
    )
    has_structure = True
    print("高级结构分析初始化成功（版面分析 + 表格识别）")
except Exception as e:
    print(f"高级结构分析初始化失败，仅支持基础 OCR: {e}")
    has_structure = False


@app.get("/health")
async def health_check():
    """健康检查"""
    return {
        "status": "ok",
        "has_structure": has_structure,
        "capabilities": [
            "text_detection",
            "text_recognition",
            "textline_orientation",
            "layout_analysis" if has_structure else None,
            "table_recognition" if has_structure else None,
        ]
    }


@app.post("/ocr/image")
async def ocr_image(
    file: UploadFile = File(...),
    return_image: Optional[bool] = Form(False)
):
    """
    图片 OCR 识别（支持 jpg/png/bmp/tiff 等）
    返回检测到的文本及其位置
    """
    suffix = Path(file.filename).suffix or '.png'
    with tempfile.NamedTemporaryFile(delete=False, suffix=suffix) as tmp:
        content = await file.read()
        tmp.write(content)
        tmp_path = tmp.name

    try:
        # 执行 OCR
        result = ocr.ocr(tmp_path)

        # 解析结果
        ocr_results = []
        if result and result[0]:
            for line in result[0]:
                box, (text, confidence) = line[0], line[1]
                ocr_results.append({
                    "text": text,
                    "confidence": round(float(confidence), 4),
                    "box": [[int(x), int(y)] for x, y in box]
                })

        return JSONResponse({
            "success": True,
            "file_name": file.filename,
            "results": ocr_results,
            "total_texts": len(ocr_results)
        })

    finally:
        if os.path.exists(tmp_path):
            os.unlink(tmp_path)


@app.post("/ocr/pdf")
async def ocr_pdf(
    file: UploadFile = File(...),
    dpi: int = Form(200)
):
    """
    PDF 文件 OCR 识别
    将 PDF 每一页转为图片后进行 OCR
    """
    if not file.filename.lower().endswith('.pdf'):
        return JSONResponse({"success": False, "error": "仅支持 PDF 文件"}, status_code=400)

    content = await file.read()

    with tempfile.NamedTemporaryFile(delete=False, suffix='.pdf') as tmp_pdf:
        tmp_pdf.write(content)
        pdf_path = tmp_pdf.name

    try:
        images = convert_from_bytes(content, dpi=dpi)

        all_pages_results = []
        for page_num, image in enumerate(images, 1):
            img_array = np.array(image)
            result = ocr.ocr(img_array)

            page_texts = []
            if result and result[0]:
                for line in result[0]:
                    box, (text, confidence) = line[0], line[1]
                    page_texts.append({
                        "text": text,
                        "confidence": round(float(confidence), 4),
                        "box": [[int(x), int(y)] for x, y in box]
                    })

            all_pages_results.append({
                "page": page_num,
                "results": page_texts,
                "total_texts": len(page_texts)
            })

        return JSONResponse({
            "success": True,
            "file_name": file.filename,
            "total_pages": len(images),
            "pages": all_pages_results
        })

    finally:
        if os.path.exists(pdf_path):
            os.unlink(pdf_path)


@app.post("/ocr/document")
async def ocr_document_with_structure(
    file: UploadFile = File(...)
):
    """
    文档结构分析（版面分析 + 表格识别）
    支持：图片、PDF
    """
    if not has_structure:
        return JSONResponse(
            {"success": False, "error": "高级结构分析能力未启用"},
            status_code=501
        )

    suffix = Path(file.filename).suffix.lower()
    allowed_formats = ['.jpg', '.jpeg', '.png', '.bmp', '.tiff', '.pdf']

    if suffix not in allowed_formats:
        return JSONResponse(
            {"success": False, "error": f"不支持的格式，支持: {allowed_formats}"},
            status_code=400
        )

    content = await file.read()

    with tempfile.NamedTemporaryFile(delete=False, suffix=suffix) as tmp:
        tmp.write(content)
        tmp_path = tmp.name

    tmp_img_path = None
    try:
        if suffix == '.pdf':
            images = convert_from_bytes(content, dpi=200)
            if not images:
                return JSONResponse(
                    {"success": False, "error": "PDF 转换失败，无可用页面"},
                    status_code=400
                )
            img_array = np.array(images[0])
            tmp_img = tempfile.NamedTemporaryFile(delete=False, suffix='.png')
            Image.fromarray(img_array).save(tmp_img.name)
            analysis_path = tmp_img.name
            tmp_img_path = tmp_img.name
        else:
            analysis_path = tmp_path

        # layout=True, table=True 在 predict 时传入
        result = table_engine.predict(analysis_path, layout=True, table=True)

        structured_results = []
        for item in result:
            item_type = item.get('type', 'unknown')
            item_result = {"type": item_type}

            if item_type == 'table':
                item_result["table_cells"] = item.get('res', {}).get('cells', [])
                item_result["html"] = item.get('res', {}).get('html', '')
                item_result["box"] = item.get('bbox', [])
            elif item_type == 'text':
                item_result["text"] = item.get('res', '')
                item_result["box"] = item.get('bbox', [])
            else:
                item_result["data"] = str(item.get('res', ''))
                item_result["box"] = item.get('bbox', [])

            structured_results.append(item_result)

        return JSONResponse({
            "success": True,
            "file_name": file.filename,
            "structure_results": structured_results
        })

    finally:
        if os.path.exists(tmp_path):
            os.unlink(tmp_path)
        if tmp_img_path and os.path.exists(tmp_img_path):
            os.unlink(tmp_img_path)


@app.post("/ocr/batch")
async def ocr_batch(
    files: List[UploadFile] = File(...)
):
    """批量图片 OCR"""
    all_results = []

    for file in files:
        suffix = Path(file.filename).suffix or '.png'
        with tempfile.NamedTemporaryFile(delete=False, suffix=suffix) as tmp:
            content = await file.read()
            tmp.write(content)
            tmp_path = tmp.name

        try:
            result = ocr.ocr(tmp_path)
            texts = []
            if result and result[0]:
                for line in result[0]:
                    box, (text, confidence) = line[0], line[1]
                    texts.append({"text": text, "confidence": round(float(confidence), 4)})

            all_results.append({
                "file_name": file.filename,
                "results": texts
            })
        finally:
            if os.path.exists(tmp_path):
                os.unlink(tmp_path)

    return JSONResponse({
        "success": True,
        "total_files": len(files),
        "results": all_results
    })


if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)