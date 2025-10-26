package com.machine.service.doc.convert.service.impl;

import com.machine.sdk.common.exception.data.DataBusinessException;
import com.machine.service.doc.convert.service.IDocConvertService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.document.DocumentFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Slf4j
@Service
public class IDocConvertServiceImpl implements IDocConvertService {

    @Autowired
    private DocumentConverter documentConverter;

    @Override
    public ResponseEntity<byte[]> convertToPdf(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String pdfFilename = getPdfFilename(originalFilename);
        String safeFilename = sanitizeFilename(pdfFilename);

        try (InputStream inputStream = file.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            // 自动识别输入格式
            DocumentFormat inputFormat = getDocumentFormat(originalFilename);
            documentConverter.convert(inputStream)
                    .as(inputFormat)
                    .to(outputStream)
                    .as(DefaultDocumentFormatRegistry.PDF)
                    .execute();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + safeFilename + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(outputStream.toByteArray());
        } catch (Exception e) {
            log.error("文件转换失败: {}", originalFilename, e);
            throw new DataBusinessException("doc.convert.service.convertToPdf.exception", "文件转换失败", e);
        }
    }

    @NotNull
    private static String getPdfFilename(String originalFilename) {
        String pdfFilename;

        if (originalFilename != null && !originalFilename.isEmpty()) {
            // 移除原扩展名，添加.pdf扩展名
            int lastDotIndex = originalFilename.lastIndexOf(".");
            if (lastDotIndex > 0) {
                pdfFilename = originalFilename.substring(0, lastDotIndex) + ".pdf";
            } else {
                pdfFilename = originalFilename + ".pdf";
            }
        } else {
            // 如果原始文件名为空，使用默认文件名
            pdfFilename = "converted.pdf";
        }
        return pdfFilename;
    }


    /**
     * 清理文件名，移除可能引起问题的字符
     */
    private String sanitizeFilename(String filename) {
        if (filename == null) {
            return "converted.pdf";
        }
        // 移除路径信息和特殊字符
        String cleanName = filename.replaceAll("[^a-zA-Z0-9.-]", "_");
        // 确保文件名不为空
        return cleanName.isEmpty() ? "converted.pdf" : cleanName;
    }

    /**
     * 根据文件名自动识别输入文档格式
     */
    private DocumentFormat getDocumentFormat(String filename) {
        if (filename == null || filename.isEmpty()) {
            return DefaultDocumentFormatRegistry.getFormatByExtension("noop");
        }
        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        return DefaultDocumentFormatRegistry.getFormatByExtension(extension);
    }
}
