package com.machine.starter.obs.service;

import cn.hutool.core.date.DateUtil;
import com.machine.sdk.base.exception.data.DataBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.dromara.x.file.storage.core.hash.MessageDigestHashCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

@Slf4j
@Service
public class ObsFileService {

    public static final int URL_DEFAULT_EXPIRE_SECOND = 5 * 60;

    private static final int URL_MAX_EXPIRE_SECOND = 7 * 24 * 60 * 60;

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
    public FileInfo upload(InputStream inputStream,
                           String originalFilename,
                           String path) {
        FileInfo fileInfo = fileStorageService.of(inputStream)
                .setPath(path)
                .setOriginalFilename(originalFilename)
                .upload();
        if (null == fileInfo.getHashInfo() || fileInfo.getHashInfo().isEmpty()) {
            fileInfo.setHashInfo(null);
        }
        return fileInfo;
    }

    /**
     * 上传文件（计算 SHA-256 哈希）
     */
    public FileInfo uploadWithHash(InputStream inputStream,
                                   String originalFilename,
                                   String path) {
        FileInfo fileInfo = fileStorageService.of(inputStream)
                .setPath(path)
                .setOriginalFilename(originalFilename)
                .setHashCalculator(new MessageDigestHashCalculator("SHA-256"))
                .upload();
        if (null == fileInfo.getHashInfo() || fileInfo.getHashInfo().isEmpty()) {
            fileInfo.setHashInfo(null);
        }
        return fileInfo;
    }

    /**
     * 上传图片（计算 SHA-256 哈希）
     */
    public FileInfo uploadImageWithHash(InputStream inputStream,
                                        String originalFilename,
                                        String path) {
        FileInfo fileInfo = fileStorageService.of(inputStream)
                .setPath(path)
                .setOriginalFilename(originalFilename)
                .setHashCalculator(new MessageDigestHashCalculator("SHA-256"))
                .thumbnail(th -> th.size(256, 256))
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
     * 下载文件到流
     */
    public InputStream downloadToStream(FileInfo fileInfo) {
        return new ByteArrayInputStream(fileStorageService.download(fileInfo).bytes());
    }


    /**
     * 生成缩略图预签名URL(默认5分钟)
     */
    public String generateThPresignedUrl(FileInfo fileInfo) {
        return generateThPresignedUrl(fileInfo, URL_DEFAULT_EXPIRE_SECOND);
    }

    /**
     * 生成缩略图预签名URL
     */
    public String generateThPresignedUrl(FileInfo fileInfo,
                                         int expireSecond) {
        if (URL_MAX_EXPIRE_SECOND <= expireSecond) {
            throw new DataBusinessException("data.obs.function.generateThPresignedUrl.wrongExpireSecond", "缩略图预签名URL过期时间有误");
        }
        return fileStorageService.generateThPresignedUrl(fileInfo, DateUtil.offsetSecond(new Date(), expireSecond));
    }


    /**
     * 生成预签名URL(默认5分钟)
     */
    public String generatePresignedUrl(FileInfo fileInfo,
                                       boolean forceDownload) {
        return generatePresignedUrl(fileInfo, URL_DEFAULT_EXPIRE_SECOND, forceDownload);
    }


    /**
     * 生成预签名URL
     */
    public String generatePresignedUrl(FileInfo fileInfo,
                                       int expireSecond,
                                       boolean forceDownload) {
        if (URL_MAX_EXPIRE_SECOND <= expireSecond) {
            throw new DataBusinessException("data.obs.function.generatePresignedUrl.wrongExpireSecond", "预签名URL过期时间有误");
        }
        var pretreatment = fileStorageService.generatePresignedUrl()
                .setPlatform(fileInfo.getPlatform())
                .setPath(fileInfo.getPath())
                .setFilename(fileInfo.getFilename())
                .setExpiration(DateUtil.offsetSecond(new Date(), expireSecond))
                .setMethod(HttpMethod.GET);
        if (forceDownload) {
            pretreatment.putResponseHeaders("Content-Disposition", "attachment");
        }
        return pretreatment.generatePresignedUrl().getUrl();
    }

}