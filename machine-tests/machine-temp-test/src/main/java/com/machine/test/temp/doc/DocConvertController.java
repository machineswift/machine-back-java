package com.machine.test.temp.doc;

import com.machine.client.doc.convert.IDocConvertClient;
import com.machine.sdk.common.constant.CommonIamConstant;
import com.machine.sdk.common.context.AppContext;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("doc/convert")
public class DocConvertController {

    @Autowired
    private IDocConvertClient docConvertClient;

    @PostMapping(value = "toPdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void test(@RequestPart("file") MultipartFile file,
                     HttpServletResponse response) {

        AppContext.getContext().setUserId(CommonIamConstant.User.ROOT_USER_ID);
        String originalFilename = file.getOriginalFilename();
        log.info("开始文件转换: {}", originalFilename);

        // 验证文件是否为空
        if (file.isEmpty()) {
            handleError(response, "上传的文件为空", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 验证文件大小
        if (file.getSize() > 512 * 1024 * 1024) {
            handleError(response, "文件大小超过限制", HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            // 调用 Feign Client 进行文件转换
            ResponseEntity<byte[]> responseEntity = docConvertClient.convertToPdf(file);

            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                // 设置响应头
                String pdfFilename = getPdfFilename(originalFilename);
                String safeFilename = sanitizeFilename(pdfFilename);

                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + safeFilename + "\"");
                response.setContentType("application/pdf");
                response.setCharacterEncoding("UTF-8");

                // 写入文件数据
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(responseEntity.getBody());
                outputStream.flush();

                log.info("文件转换成功: {}", originalFilename);
            } else {
                handleError(response, "文件转换失败: 远程服务返回异常", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            log.error("文件转换失败: {}", originalFilename, e);
            handleError(response, "文件转换失败: " + e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private String getPdfFilename(String originalFilename) {
        if (originalFilename != null && !originalFilename.isEmpty()) {
            int lastDotIndex = originalFilename.lastIndexOf(".");
            if (lastDotIndex > 0) {
                return originalFilename.substring(0, lastDotIndex) + ".pdf";
            } else {
                return originalFilename + ".pdf";
            }
        }
        return "converted.pdf";
    }

    private String sanitizeFilename(String filename) {
        if (filename == null) {
            return "converted.pdf";
        }
        String cleanName = filename.replaceAll("[^a-zA-Z0-9.-]", "_");
        return cleanName.isEmpty() ? "converted.pdf" : cleanName;
    }

    /**
     * 统一错误处理
     */
    private void handleError(HttpServletResponse response, String errorMessage, int statusCode) {
        try {
            response.setStatus(statusCode);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            String errorJson = String.format("{\"success\": false, \"error\": \"%s\", \"code\": %d}",
                    errorMessage, statusCode);
            response.getWriter().write(errorJson);

        } catch (IOException e) {
            log.error("写入错误响应失败", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}