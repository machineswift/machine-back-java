package com.machine.test.temp.sdk;

import cn.hutool.json.JSONUtil;
import com.obs.services.model.DeleteObjectResult;
import com.obs.services.model.PutObjectResult;
import com.machine.sdk.huawei.client.file.HuaWeiObsHttpClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("sdk/obs")
public class HuaWeiObsController {

    @Resource(name = "huaWeiObsHttpClient")
    private HuaWeiObsHttpClient huaWeiObsHttpClient;

    //    @Value("${huawei.obs.upload.bucketName:machine-app-file}")
////    private String bucketName = "machine-app-file";
//
//    @Value("${huawei.obs.upload.parentDir:songzhongjin}")
//    private String parentDir = "songzhongjin";

    @GetMapping("uploadFileByFileName")
    public PutObjectResult uploadFileByFileName() throws Exception {

        File file = new File("C:\\Users\\10546\\Desktop\\fyWcPGtiXE.png");
        InputStream inputStream = new FileInputStream(file);
        String string = UUID.randomUUID().toString() + ".png";
        log.info("文件名:" + string);
        PutObjectResult putObjectResult = huaWeiObsHttpClient.uploadFileByFileName(inputStream, string);
        log.info("文件上传:{}", JSONUtil.toJsonStr(putObjectResult));
        return putObjectResult;
    }

    @GetMapping("deleteFile")
    public DeleteObjectResult deleteFile() throws Exception {

        String fileName = "songzhongjin/2F281c858c-bfa6-4237-8562-7fdf03fe1fea.png";

        log.info("文件名:" + fileName);
        DeleteObjectResult deleteObjectResult = huaWeiObsHttpClient.deleteFile(fileName);
        return deleteObjectResult;
    }
}