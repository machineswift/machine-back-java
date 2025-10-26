package com.machine.client.doc.convert;

import com.machine.sdk.common.config.OpenFeignMaxTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "machine-doc-service", path = "machine-doc-service/server/doc/convert",
        configuration = OpenFeignMaxTimeConfig.class)
public interface IDocConvertClient {

    @PostMapping(value = "toPdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<byte[]> convertToPdf(@RequestPart("file") MultipartFile file);

}



