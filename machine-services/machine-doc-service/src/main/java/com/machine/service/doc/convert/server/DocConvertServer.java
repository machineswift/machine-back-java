package com.machine.service.doc.convert.server;

import com.machine.client.doc.convert.IDocConvertClient;
import com.machine.sdk.common.context.AppContext;
import com.machine.service.doc.convert.service.IDocConvertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("server/doc/convert")
public class DocConvertServer implements IDocConvertClient {

    @Autowired
    private IDocConvertService docConvertService;

    @Override
    @PostMapping(value = "toPdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> convertToPdf(@RequestPart("file") MultipartFile file) {
        log.info("文档转化为pdf，userId:{} fileName:{}", AppContext.getContext().getUserId(), file.getOriginalFilename());
        return docConvertService.convertToPdf(file);
    }
}
