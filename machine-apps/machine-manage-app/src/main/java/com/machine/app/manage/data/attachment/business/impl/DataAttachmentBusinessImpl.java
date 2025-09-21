package com.machine.app.manage.data.attachment.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.attachment.business.IDataAttachmentBusiness;
import com.machine.app.manage.data.attachment.controller.vo.response.DataAttachmentDetailResponseVo;
import com.machine.app.manage.data.attachment.controller.vo.response.DataAttachmentExpandListResponseVo;
import com.machine.app.manage.data.attachment.controller.vo.resquest.DataAttachmentQueryPageRequestVo;
import com.machine.app.manage.data.attachment.controller.vo.resquest.DataAttachmentUpdateRequestVo;
import com.machine.client.data.attachment.IDataAttachmentClient;
import com.machine.client.data.attachment.dto.input.DataAttachmentCreateTemporaryInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentQueryPageInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentUpdateInputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentDetailOutputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentListOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.sdk.common.envm.data.attachment.DataAttachmentTypeEnum;
import com.machine.sdk.common.exception.data.DataBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.starter.obs.function.ObsFunction;
import com.machine.starter.redis.function.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.machine.sdk.common.constant.CommonConstant.EMPTY_STR;
import static com.machine.starter.obs.function.ObsFunction.PRESIGNED_URL_DEFAULT_EXPIRE_SECOND;
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
    private IIamUserClient userClient;

    @Autowired
    private IDataAttachmentClient attachmentClient;

    @Override
    public String upload(DataAttachmentTypeEnum attachmentType,
                         MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new DataBusinessException("data.attachment.business.upload.empty", "上传文件不能为空");
        }

        DataAttachmentCreateTemporaryInputDto inputDto = new DataAttachmentCreateTemporaryInputDto();
        inputDto.setType(attachmentType);
        inputDto.setSize(file.getSize());
        inputDto.setName(file.getOriginalFilename());
        if (DataAttachmentTypeEnum.IMAGE == attachmentType) {
            inputDto.setFileInfo(JSONUtil.toJsonStr(obsFunction.uploadImage(file)));
        } else {
            inputDto.setFileInfo(JSONUtil.toJsonStr(obsFunction.upload(file)));
        }
        return attachmentClient.createTemporary(inputDto);
    }

    @Override
    public String uploadImage(int thumbnailWeight,
                              int thumbnailHeight,
                              MultipartFile file) {
        DataAttachmentCreateTemporaryInputDto inputDto = new DataAttachmentCreateTemporaryInputDto();
        inputDto.setType(DataAttachmentTypeEnum.IMAGE);
        inputDto.setSize(file.getSize());
        inputDto.setName(file.getOriginalFilename());
        FileInfo fileInfo = obsFunction.uploadImage(file);
        if (null == fileInfo.getHashInfo() || fileInfo.getHashInfo().isEmpty()) {
            fileInfo.setHashInfo(null);
        }
        inputDto.setFileInfo(JSONUtil.toJsonStr(fileInfo));
        return attachmentClient.createTemporary(inputDto);
    }

    @Override
    public String getPresignedUrl(String attachmentId) {
        return getPresignedUrl(attachmentId, PRESIGNED_URL_DEFAULT_EXPIRE_SECOND);
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

            DataAttachmentDetailOutputDto outputDto = attachmentClient.getById(new IdRequest(attachmentId));
            if (null == outputDto) {
                //缓存穿透
                customerRedisCommands.set(DATA_ATTACHMENT_URL_KEY + attachmentId, EMPTY_STR, 30 * 60);
                return null;
            }

            if (System.currentTimeMillis() + 5 * 1000 > outputDto.getExpireTime()) {
                //附件过期
                customerRedisCommands.set(DATA_ATTACHMENT_URL_KEY + attachmentId, EMPTY_STR, 30 * 60);
                return null;
            }

            //生成地址
            String presignedUrl = obsFunction.generatePresignedUrl(JSONUtil.toBean(outputDto.getFileInfo(), FileInfo.class), expireSecond);
            if (StrUtil.isBlank(presignedUrl)) {
                //缓存穿透
                customerRedisCommands.set(DATA_ATTACHMENT_URL_KEY + attachmentId, EMPTY_STR, 30 * 60);
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
    public String getThumbnailUrl(String attachmentId) {
        return getThumbnailUrl(attachmentId, PRESIGNED_URL_DEFAULT_EXPIRE_SECOND);
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

            DataAttachmentDetailOutputDto outputDto = attachmentClient.getById(new IdRequest(attachmentId));
            if (null == outputDto) {
                //缓存穿透
                customerRedisCommands.set(DATA_ATTACHMENT_THUMBNAIL_URL_KEY + attachmentId, EMPTY_STR, 30 * 60);
                return null;
            }

            if (System.currentTimeMillis() + 5 * 1000 > outputDto.getExpireTime()) {
                //附件过期
                customerRedisCommands.set(DATA_ATTACHMENT_THUMBNAIL_URL_KEY + attachmentId, EMPTY_STR, 30 * 60);
                return null;
            }

            //生成地址
            String thPresignedUrl = obsFunction.generateThPresignedUrl(JSONUtil.toBean(outputDto.getFileInfo(), FileInfo.class), expireSecond);
            if (StrUtil.isBlank(thPresignedUrl)) {
                //缓存穿透
                customerRedisCommands.set(DATA_ATTACHMENT_THUMBNAIL_URL_KEY + attachmentId, EMPTY_STR, 30 * 60);
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

    @Override
    public void update(DataAttachmentUpdateRequestVo request) {
        DataAttachmentUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataAttachmentUpdateInputDto.class);
        attachmentClient.update(inputDto);
    }

    @Override
    public DataAttachmentDetailResponseVo detail(IdRequest request) {
        DataAttachmentDetailOutputDto outputDto = attachmentClient.getById(request);
        if (null == outputDto) {
            return null;
        }

        DataAttachmentDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataAttachmentDetailResponseVo.class);
        {//填充修改人创建人信息
            Set<String> userIdSet = new HashSet<>();
            userIdSet.add(outputDto.getCreateBy());
            userIdSet.add(outputDto.getUpdateBy());
            Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
            responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());
        }
        return responseVo;
    }

    @Override
    public PageResponse<DataAttachmentExpandListResponseVo> pageExpand(DataAttachmentQueryPageRequestVo request) {
        //查询分页数据
        DataAttachmentQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataAttachmentQueryPageInputDto.class);
        PageResponse<DataAttachmentListOutputDto> pageOutput = attachmentClient.selectPage(inputDto);

        if (CollectionUtil.isEmpty(pageOutput.getRecords())) {
            return new PageResponse<>(pageOutput.getCurrent(), pageOutput.getSize(), pageOutput.getTotal());
        }

        //转化为返回数据
        PageResponse<DataAttachmentExpandListResponseVo> pageResponse = new PageResponse<>(
                pageOutput.getCurrent(),
                pageOutput.getSize(),
                pageOutput.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutput.getRecords()), DataAttachmentExpandListResponseVo.class));

        //创建人、修改文姓名
        Set<String> userIdSet = pageResponse.getRecords().stream().map(DataAttachmentExpandListResponseVo::getCreateBy).collect(Collectors.toSet());
        userIdSet.addAll(pageResponse.getRecords().stream().map(DataAttachmentExpandListResponseVo::getUpdateBy).collect(Collectors.toSet()));
        Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        for (DataAttachmentExpandListResponseVo vo : pageResponse.getRecords()) {
            vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
            vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
        }

        return pageResponse;
    }

}
