package com.machine.service.doc.convert.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IDocConvertService {
    ResponseEntity<byte[]> convertToPdf(MultipartFile file);
}
