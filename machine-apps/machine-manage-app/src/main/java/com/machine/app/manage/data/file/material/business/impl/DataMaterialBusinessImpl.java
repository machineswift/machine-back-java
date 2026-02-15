package com.machine.app.manage.data.file.material.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.file.material.business.IDataMaterialBusiness;
import com.machine.app.manage.data.file.material.controller.vo.response.DataMaterialDetailResponseVo;
import com.machine.app.manage.data.file.material.controller.vo.response.DataMaterialExpandListResponseVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialCreateRequestVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialQueryPageRequestVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialUpdateRequestVo;
import com.machine.client.data.file.material.IDataMaterialClient;
import com.machine.client.data.file.material.dto.input.DataMaterialCreatePermanentInputDto;
import com.machine.client.data.file.material.dto.input.DataMaterialQueryPageInputDto;
import com.machine.client.data.file.material.dto.input.DataMaterialUpdateInputDto;
import com.machine.client.data.file.material.dto.output.DataMaterialDetailOutputDto;
import com.machine.client.data.file.material.dto.output.DataMaterialListOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.machine.sdk.common.constant.CommonConstant.EMPTY_STR;
import static com.machine.starter.obs.function.ObsFunction.PRESIGNED_URL_DEFAULT_EXPIRE_SECOND;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Data.LOCK_DATA_MATERIAL_THUMBNAIL_URL;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Data.LOCK_DATA_MATERIAL_URL;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.Material.DATA_MATERIAL_THUMBNAIL_URL_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.Material.DATA_MATERIAL_URL_KEY;

@Slf4j
@Component
public class DataMaterialBusinessImpl implements IDataMaterialBusiness {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private ObsFunction obsFunction;

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IDataMaterialClient materialClient;

    @Override
    public void create(DataMaterialCreateRequestVo request) {
        DataMaterialCreatePermanentInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataMaterialCreatePermanentInputDto.class);
        materialClient.createPermanent(inputDto);
    }

    @Override
    public void update(DataMaterialUpdateRequestVo request) {
        DataMaterialUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataMaterialUpdateInputDto.class);
        materialClient.updatePermanent(inputDto);
    }

    @Override
    public String getPresignedUrl(String materialId) {
        return getPresignedUrl(materialId, PRESIGNED_URL_DEFAULT_EXPIRE_SECOND);
    }

    @Override
    public String getPresignedUrl(String materialId,
                                  int expireSecond) {
        String url = customerRedisCommands.get(DATA_MATERIAL_URL_KEY + materialId);
        if (EMPTY_STR.equals(url)) {
            //缓存穿透
            return null;
        }
        if (StrUtil.isNotBlank(url)) {
            return url;
        }

        //缓存击穿
        RLock lock = redissonClient.getLock(LOCK_DATA_MATERIAL_URL + materialId);
        try {
            lock.lock();
            url = customerRedisCommands.get(DATA_MATERIAL_URL_KEY + materialId);
            if (EMPTY_STR.equals(url)) {
                //缓存穿透
                return null;
            }
            if (StrUtil.isNotBlank(url)) {
                return url;
            }

            DataMaterialDetailOutputDto outputDto = materialClient.getById(new IdRequest(materialId));
            if (null == outputDto) {
                //缓存穿透
                customerRedisCommands.set(DATA_MATERIAL_URL_KEY + materialId, EMPTY_STR, 30 * 60);
                return null;
            }

            if (System.currentTimeMillis() + 5 * 1000 > outputDto.getExpireTime()) {
                //素材过期
                customerRedisCommands.set(DATA_MATERIAL_URL_KEY + materialId, EMPTY_STR, 30 * 60);
                return null;
            }

            //生成地址
            String presignedUrl = obsFunction.generatePresignedUrl(JSONUtil.toBean(outputDto.getFileInfo(), FileInfo.class), expireSecond);
            if (StrUtil.isBlank(presignedUrl)) {
                //缓存穿透
                customerRedisCommands.set(DATA_MATERIAL_URL_KEY + materialId, EMPTY_STR, 30 * 60);
                return null;
            } else {
                customerRedisCommands.set(DATA_MATERIAL_URL_KEY + materialId, presignedUrl, expireSecond - 30);
                return presignedUrl;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String getThumbnailUrl(String materialId) {
        return getThumbnailUrl(materialId, PRESIGNED_URL_DEFAULT_EXPIRE_SECOND);
    }

    @Override
    public String getThumbnailUrl(String materialId,
                                  int expireSecond) {
        String url = customerRedisCommands.get(DATA_MATERIAL_THUMBNAIL_URL_KEY + materialId);
        if (EMPTY_STR.equals(url)) {
            //缓存穿透
            return null;
        }
        if (StrUtil.isNotBlank(url)) {
            return url;
        }

        //缓存击穿
        RLock lock = redissonClient.getLock(LOCK_DATA_MATERIAL_THUMBNAIL_URL + materialId);
        try {
            lock.lock();
            url = customerRedisCommands.get(DATA_MATERIAL_THUMBNAIL_URL_KEY + materialId);
            if (EMPTY_STR.equals(url)) {
                //缓存穿透
                return null;
            }
            if (StrUtil.isNotBlank(url)) {
                return url;
            }

            DataMaterialDetailOutputDto outputDto = materialClient.getById(new IdRequest(materialId));
            if (null == outputDto) {
                //缓存穿透
                customerRedisCommands.set(DATA_MATERIAL_THUMBNAIL_URL_KEY + materialId, EMPTY_STR, 30 * 60);
                return null;
            }

            if (System.currentTimeMillis() + 5 * 1000 > outputDto.getExpireTime()) {
                //素材过期
                customerRedisCommands.set(DATA_MATERIAL_THUMBNAIL_URL_KEY + materialId, EMPTY_STR, 30 * 60);
                return null;
            }

            //生成地址
            String presignedUrl = obsFunction.generateThPresignedUrl(JSONUtil.toBean(outputDto.getFileInfo(), FileInfo.class), expireSecond);
            if (StrUtil.isBlank(presignedUrl)) {
                //缓存穿透
                customerRedisCommands.set(DATA_MATERIAL_THUMBNAIL_URL_KEY + materialId, EMPTY_STR, 30 * 60);
                return null;
            } else {
                customerRedisCommands.set(DATA_MATERIAL_THUMBNAIL_URL_KEY + materialId, presignedUrl, expireSecond - 30);
                return presignedUrl;
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public DataMaterialDetailResponseVo detail(IdRequest request) {
        DataMaterialDetailOutputDto outputDto = materialClient.getById(request);
        if (null == outputDto) {
            return null;
        }

        DataMaterialDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataMaterialDetailResponseVo.class);
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
    public PageResponse<DataMaterialExpandListResponseVo> pageExpand(DataMaterialQueryPageRequestVo request) {
        //查询分页数据
        DataMaterialQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataMaterialQueryPageInputDto.class);
        PageResponse<DataMaterialListOutputDto> pageOutput = materialClient.selectPage(inputDto);

        if (CollectionUtil.isEmpty(pageOutput.getRecords())) {
            return new PageResponse<>(pageOutput.getCurrent(), pageOutput.getSize(), pageOutput.getTotal());
        }

        //转化为返回数据
        PageResponse<DataMaterialExpandListResponseVo> pageResponse = new PageResponse<>(
                pageOutput.getCurrent(),
                pageOutput.getSize(),
                pageOutput.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutput.getRecords()), DataMaterialExpandListResponseVo.class));

        //创建人、修改文姓名
        Set<String> userIdSet = pageResponse.getRecords().stream().map(DataMaterialExpandListResponseVo::getCreateBy).collect(Collectors.toSet());
        userIdSet.addAll(pageResponse.getRecords().stream().map(DataMaterialExpandListResponseVo::getUpdateBy).collect(Collectors.toSet()));
        Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        for (DataMaterialExpandListResponseVo vo : pageResponse.getRecords()) {
            vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
            vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
        }

        return pageResponse;
    }

}
