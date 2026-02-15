package com.machine.starter.obs.function;

import cn.hutool.core.date.DateUtil;
import com.machine.sdk.common.exception.data.DataBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Slf4j
@Component
public class ObsFunction {

    public static final int PRESIGNED_URL_DEFAULT_EXPIRE_SECOND = 60 * 60;

    private static final int PRESIGNED_URL_MAX_EXPIRE_SECOND = 7 * 24 * 60 * 60;

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * 上传文件
     */
    public FileInfo upload(MultipartFile file,
                           String path) {
        FileInfo fileInfo = fileStorageService.of(file)
                .setPath(path)
                .upload();
        if (null == fileInfo.getHashInfo() || fileInfo.getHashInfo().isEmpty()) {
            fileInfo.setHashInfo(null);
        }
        return fileInfo;
    }

    /**
     * 上传文件
     */
    public FileInfo upload(byte[] bytes,
                           String originalFilename,
                           String path) {
        FileInfo fileInfo = fileStorageService.of(bytes)
                .setPath(path)
                .setOriginalFilename(originalFilename)
                .upload();
        if (null == fileInfo.getHashInfo() || fileInfo.getHashInfo().isEmpty()) {
            fileInfo.setHashInfo(null);
        }
        return fileInfo;
    }

    /**
     * 上传图片
     */
    public FileInfo uploadImage(MultipartFile file,
                                String path) {
        FileInfo fileInfo = uploadImage(file, path, 256, 256);
        if (null == fileInfo.getHashInfo() || fileInfo.getHashInfo().isEmpty()) {
            fileInfo.setHashInfo(null);
        }
        return fileInfo;
    }

    /**
     * 上传图片
     */
    public FileInfo uploadImage(MultipartFile file,
                                String path,
                                int width,
                                int height) {
        FileInfo fileInfo = fileStorageService.of(file)
                .setPath(path)
                .thumbnail(th -> th.size(width, height))  //生成一张的缩略图
                .upload();
        if (null == fileInfo.getHashInfo() || fileInfo.getHashInfo().isEmpty()) {
            fileInfo.setHashInfo(null);
        }
        return fileInfo;
    }


    /**
     * 删除文件
     */
    public boolean delete(FileInfo fileInfo) {
        return fileStorageService.delete(fileInfo);
    }

    /**
     * 文件是否存在
     */
    public boolean exists(FileInfo fileInfo) {
        return fileStorageService.exists(fileInfo);
    }


    /**
     * 生成预签名URL（默认过期时间1小时）
     */
    public String generatePresignedUrl(FileInfo fileInfo) {
        return generatePresignedUrl(fileInfo, PRESIGNED_URL_DEFAULT_EXPIRE_SECOND);
    }


    public String generatePresignedUrl(FileInfo fileInfo,
                                       int expireSecond) {
        if (PRESIGNED_URL_MAX_EXPIRE_SECOND <= expireSecond) {
            throw new DataBusinessException("data.obs.function.generatePresignedUrl.wrongExpireSecond", "预签名URL过期时间有误");
        }
        return fileStorageService.generatePresignedUrl(fileInfo, DateUtil.offsetSecond(new Date(), expireSecond));
    }


    /**
     * 生成缩略图预签名URL（默认过期时间1小时）
     */
    public String generateThPresignedUrl(FileInfo fileInfo) {
        return generateThPresignedUrl(fileInfo, PRESIGNED_URL_DEFAULT_EXPIRE_SECOND);
    }


    public String generateThPresignedUrl(FileInfo fileInfo,
                                         int expireSecond) {
        if (PRESIGNED_URL_MAX_EXPIRE_SECOND <= expireSecond) {
            throw new DataBusinessException("data.obs.function.generateThPresignedUrl.wrongExpireSecond", "缩略图预签名URL过期时间有误");
        }
        return fileStorageService.generateThPresignedUrl(fileInfo, DateUtil.offsetSecond(new Date(), expireSecond));
    }

}