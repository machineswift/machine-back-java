package com.machine.app.manage.data.file.attachment.business.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.file.attachment.business.IDataAttachmentBusiness;
import com.machine.client.data.file.attachment.IDataAttachmentClient;
import com.machine.client.data.file.attachment.IDataFileClient;
import com.machine.client.data.file.attachment.dto.input.DataAttachmentCreateInputDto;
import com.machine.client.data.file.attachment.dto.output.DataAttachmentDetailOutputDto;
import com.machine.client.data.file.attachment.dto.output.DataFileDetailOutputDto;
import com.machine.sdk.common.envm.base.ModuleEntityEnum;
import com.machine.sdk.common.envm.data.file.DataFileTypeEnum;
import com.machine.sdk.common.exception.data.DataBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.starter.obs.function.ObsFunction;
import com.machine.starter.obs.tool.ObsAttachmentPathBuilder;
import com.machine.starter.obs.tool.TikaFileTypeDetector;
import com.machine.starter.redis.function.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static com.machine.sdk.common.constant.CommonConstant.EMPTY_STR;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Data.LOCK_DATA_ATTACHMENT_THUMBNAIL_URL;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Data.LOCK_DATA_ATTACHMENT_URL;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.Attachment.DATA_ATTACHMENT_THUMBNAIL_URL_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.Attachment.DATA_ATTACHMENT_URL_KEY;

@Slf4j
@Component
public class DataAttachmentBusinessImpl implements IDataAttachmentBusiness {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private ObsFunction obsFunction;

    @Autowired
    private IDataFileClient dataFileClient;

    @Autowired
    private IDataAttachmentClient attachmentClient;

    @Override
    public String upload(ModuleEntityEnum entity,
                         String entityId,
                         String version,
                         MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new DataBusinessException("data.attachment.business.upload.empty", "上传文件不能为空");
        }

        // 获取文件类型
        DataFileTypeEnum fileType = TikaFileTypeDetector.getInstance().getFileType(file);

        // 上传附件
        String path = new ObsAttachmentPathBuilder().forAttachment(entity, entityId, version);
        FileInfo fileInfo = obsFunction.upload(file, path);

        DataAttachmentCreateInputDto inputDto = new DataAttachmentCreateInputDto();
        inputDto.setEntity(entity);
        inputDto.setEntityId(entityId);
        inputDto.setFileType(fileType);
        inputDto.setOriginalName(file.getOriginalFilename());
        inputDto.setStorageName(fileInfo.getFilename());
        inputDto.setMd5Hash(UUID.randomUUID().toString().replace("-", ""));
        inputDto.setFileInfo(JSONUtil.toJsonStr(fileInfo));
        inputDto.setSize(fileInfo.getSize());
        inputDto.setExpireTime(Long.MAX_VALUE);
        return attachmentClient.create(inputDto);
    }

    @Override
    public String uploadImage(ModuleEntityEnum entity,
                              String entityId,
                              String version,
                              int thumbnailWeight,
                              int thumbnailHeight,
                              MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new DataBusinessException("data.attachment.business.upload.empty", "上传文件不能为空");
        }

        // 获取文件类型
        DataFileTypeEnum fileType = TikaFileTypeDetector.getInstance().getFileType(file);
        if (DataFileTypeEnum.IMAGE != fileType) {
            throw new DataBusinessException("data.attachment.business.upload.notImage", "上传文件不是图片");
        }

        // 上传附件
        String path = new ObsAttachmentPathBuilder().forAttachment(entity, entityId, version);
        FileInfo fileInfo = obsFunction.uploadImage(file, path, thumbnailWeight, thumbnailHeight);

        DataAttachmentCreateInputDto inputDto = new DataAttachmentCreateInputDto();
        inputDto.setEntity(entity);
        inputDto.setEntityId(entityId);
        inputDto.setFileType(fileType);
        inputDto.setOriginalName(file.getOriginalFilename());
        inputDto.setStorageName(fileInfo.getFilename());
        inputDto.setMd5Hash(UUID.randomUUID().toString().replace("-", ""));
        inputDto.setFileInfo(JSONUtil.toJsonStr(fileInfo));
        inputDto.setSize(fileInfo.getSize());
        inputDto.setExpireTime(Long.MAX_VALUE);
        return attachmentClient.create(inputDto);
    }


    @Override
    public String getPresignedUrl(String attachmentId,
                                  int expireSecond) {
        String url = customerRedisCommands.get(DATA_ATTACHMENT_URL_KEY + attachmentId);
        if (EMPTY_STR.equals(url)) {
            //缓存穿透
            return null;
        }
        if (StrUtil.isNotBlank(url)) {
            return url;
        }

        //缓存击穿
        RLock lock = redissonClient.getLock(LOCK_DATA_ATTACHMENT_URL + attachmentId);
        try {
            lock.lock();
            url = customerRedisCommands.get(DATA_ATTACHMENT_URL_KEY + attachmentId);
            if (EMPTY_STR.equals(url)) {
                //缓存穿透
                return null;
            }
            if (StrUtil.isNotBlank(url)) {
                return url;
            }

            DataAttachmentDetailOutputDto attachment = attachmentClient.getById(new IdRequest(attachmentId));
            if (null == attachment) {
                //缓存穿透
                customerRedisCommands.set(DATA_ATTACHMENT_URL_KEY + attachmentId, EMPTY_STR, SECONDS_CACHE_PENETRATION);
                return null;
            }

            if (System.currentTimeMillis() + 5 * 1000 > attachment.getExpireTime()) {
                //附件过期
                customerRedisCommands.set(DATA_ATTACHMENT_URL_KEY + attachmentId, EMPTY_STR, SECONDS_CACHE_PENETRATION);
                return null;
            }

            //生成地址
            DataFileDetailOutputDto dataFile = dataFileClient.getById(new IdRequest(attachment.getFileId()));
            String presignedUrl = obsFunction.generatePresignedUrl(dataFile.getFileInfo(), expireSecond);
            if (StrUtil.isBlank(presignedUrl)) {
                //缓存穿透
                customerRedisCommands.set(DATA_ATTACHMENT_URL_KEY + attachmentId, EMPTY_STR, SECONDS_CACHE_PENETRATION);
                return null;
            } else {
                //设置缓存
                customerRedisCommands.set(DATA_ATTACHMENT_URL_KEY + attachmentId, presignedUrl, expireSecond - 30);
                return presignedUrl;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String getThumbnailUrl(String attachmentId,
                                  int expireSecond) {
        String url = customerRedisCommands.get(DATA_ATTACHMENT_THUMBNAIL_URL_KEY + attachmentId);
        if (EMPTY_STR.equals(url)) {
            //缓存穿透
            return null;
        }
        if (StrUtil.isNotBlank(url)) {
            return url;
        }

        //缓存击穿
        RLock lock = redissonClient.getLock(LOCK_DATA_ATTACHMENT_THUMBNAIL_URL + attachmentId);
        try {
            lock.lock();
            url = customerRedisCommands.get(DATA_ATTACHMENT_THUMBNAIL_URL_KEY + attachmentId);
            if (EMPTY_STR.equals(url)) {
                //缓存穿透
                return null;
            }
            if (StrUtil.isNotBlank(url)) {
                return url;
            }

            DataAttachmentDetailOutputDto attachment = attachmentClient.getById(new IdRequest(attachmentId));
            if (null == attachment) {
                //缓存穿透
                customerRedisCommands.set(DATA_ATTACHMENT_THUMBNAIL_URL_KEY + attachmentId, EMPTY_STR, SECONDS_CACHE_PENETRATION);
                return null;
            }

            if (System.currentTimeMillis() + 5 * 1000 > attachment.getExpireTime()) {
                //附件过期
                customerRedisCommands.set(DATA_ATTACHMENT_THUMBNAIL_URL_KEY + attachmentId, EMPTY_STR, SECONDS_CACHE_PENETRATION);
                return null;
            }

            //生成地址
            DataFileDetailOutputDto dataFile = dataFileClient.getById(new IdRequest(attachment.getFileId()));
            String thPresignedUrl = obsFunction.generateThPresignedUrl(dataFile.getFileInfo(), expireSecond);
            if (StrUtil.isBlank(thPresignedUrl)) {
                //缓存穿透
                customerRedisCommands.set(DATA_ATTACHMENT_THUMBNAIL_URL_KEY + attachmentId, EMPTY_STR, SECONDS_CACHE_PENETRATION);
                return null;
            } else {
                //设置缓存
                customerRedisCommands.set(DATA_ATTACHMENT_THUMBNAIL_URL_KEY + attachmentId, thPresignedUrl, expireSecond - 30);
                return thPresignedUrl;
            }
        } finally {
            lock.unlock();
        }
    }

    private static final long SECONDS_CACHE_PENETRATION = 2 * 60 * 60;

}
