package com.machine.service.data.file.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.machine.client.data.file.dto.input.*;
import com.machine.client.data.file.dto.output.QueryDownloadFileListOutputDto;
import com.machine.client.data.file.dto.output.QueryDownloadFileOutputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.model.dto.data.MaterialDto;
import com.machine.sdk.common.tool.DateUtils;
import com.machine.sdk.common.envm.data.ExportTaskStatusEnum;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.service.data.file.dao.IDownloadFileDao;
import com.machine.service.data.file.dao.mapper.DownloadFileEntityMapper;
import com.machine.service.data.file.dao.mapper.entity.DownloadFileEntity;
import com.machine.service.data.file.service.IDownloadFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DownloadFileServiceImpl extends ServiceImpl<DownloadFileEntityMapper, DownloadFileEntity> implements IDownloadFileService {

    @Autowired
    private DownloadFileEntityMapper downloadFileEntityMapper;

    /**
     * 有效期180天
     */
    @Value("${file.download.expirationTime:180}")
    private long expirationTime = 180;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private IDownloadFileDao downloadFileDao;

    @Override
    public String createFinish(DownloadFileCreateFinishInputDto inputDto) {
        MaterialDto material = inputDto.getMaterial();
        String urlMd5 = DigestUtil.md5Hex(material.getUrl());
        List<DownloadFileEntity> dbEntityList = downloadFileDao.selectByUrlMd5(urlMd5);
        if (CollectionUtil.isNotEmpty(dbEntityList)) {
            for (DownloadFileEntity dbEntity : dbEntityList) {
                String dbMaterialInfo = dbEntity.getMaterial();
                MaterialDto dbMaterial = JSONUtil.toBean(dbMaterialInfo, MaterialDto.class);
                if (dbMaterial.getUrl().equals(material.getUrl())) {
                    throw new IamBusinessException("data.downloadFile.createFinish.fileAlreadyExists", "文件已经存在");
                }
            }
        }

        DownloadFileEntity entity = new DownloadFileEntity();
        entity.setChannel(inputDto.getChannel());
        entity.setStatus(ExportTaskStatusEnum.FINISH);
        entity.setUserId(inputDto.getUserId());
        entity.setFileName(material.getName());
        entity.setFileType(material.getType());
        entity.setUrlMd5(urlMd5);
        entity.setMaterial(JSONUtil.toJsonStr(material));
        entity.setExpirationIn(inputDto.getExpirationIn());
        return downloadFileDao.insert(entity);
    }

    @Override
    public String create(CreateDownloadFileClientInputDto inputDto) {
        String userId = AppContext.getContext().getUserId();
        DownloadFileEntity downloadFileEntity = BeanUtil.copyProperties(inputDto, DownloadFileEntity.class);
        downloadFileEntity.setStatus(ExportTaskStatusEnum.READY);
        downloadFileEntity.setUserId(userId);
        downloadFileEntity.setRetryStatus(0);
        downloadFileEntity.setJsonParams(JSONUtil.toJsonStr(inputDto));
        // 获取当前时间的时间戳
        long currentTimeMillis = System.currentTimeMillis();
        // 180天的毫秒数
        long days = expirationTime * 24 * 60 * 60 * 1000;

        long futureTimeMillis = currentTimeMillis + days;
        downloadFileEntity.setExpirationIn(futureTimeMillis);
        downloadFileEntity.setUserId(userId);
        // 设置过期时间
        return downloadFileDao.insert(downloadFileEntity);
    }


    @Override
    public void updateById(UpdateDownloadFileClientInputDto inputDto) {
        DownloadFileEntity downloadFileEntity = new DownloadFileEntity();
        downloadFileEntity.setId(inputDto.getId());
        downloadFileEntity.setStatus(inputDto.getStatus());
        downloadFileEntity.setFailCause(inputDto.getFailCause());
        downloadFileEntity.setRetryStatus(inputDto.getRetryStatus());
        downloadFileEntity.setUsageCount(inputDto.getUsageCount());

//
//        @Schema(description = "附件信息")
//        private MaterialDto material;
//

        downloadFileEntityMapper.update(downloadFileEntity);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updateBatchById(List<UpdateDownloadFileClientInputDto> inputDto) {
        List<DownloadFileEntity> updateBatch = BeanUtil.copyToList(inputDto, DownloadFileEntity.class);
        this.updateBatchById(updateBatch);
    }

    @Override
    public void invoke(String id) {
        DownloadFileEntity entity = downloadFileEntityMapper.selectById(id);
        if (entity == null) {
            log.error("当前下载中心任务id不存在：{}", id);
            return;
        }

        try {
            UpdateDownloadFileClientInputDto running = new UpdateDownloadFileClientInputDto();
            running.setId(id);
            running.setUsageCount(entity.getUsageCount() + 1);
            running.setStatus(ExportTaskStatusEnum.RUNNING);
            //进行中
            this.updateById(running);

            CreateDownloadFileClientInputDto inputDto = JSONUtil.toBean(entity.getJsonParams(), CreateDownloadFileClientInputDto.class);
            //反射
            Object bean = applicationContext.getBean(Class.forName(inputDto.getClassName()));
            Method method = bean.getClass().getMethod(inputDto.getMethodName(), String.class);
            UpdateDownloadFileClientInputDto finish = (UpdateDownloadFileClientInputDto) method.invoke(bean, inputDto.getJsonParamsRequest());
            finish.setId(id);
            finish.setStatus(ExportTaskStatusEnum.FINISH);

            // 完成
            this.updateById(finish);

        } catch (Exception e) {
            //异常处理
            handleTaskFailure(entity, e);
        }
    }

    @Override
    public QueryDownloadFileOutputDto getById(String id) {
        DownloadFileEntity dbEntity = downloadFileDao.getById(id);
        QueryDownloadFileOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(dbEntity), QueryDownloadFileOutputDto.class);
        outputDto.setExpirationDay(DateUtils.daysUntilExpiration(dbEntity.getExpirationIn()));
        return outputDto;
    }

    @Override
    public List<QueryDownloadFileOutputDto> queryByLimit(QueryDownloadFileInputDto inputDto) {
        QueryWrapper<DownloadFileEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("status", inputDto.getStatusList())
                //设置重试状态
                .eq("retry_status", inputDto.getRetryStatus())
                //创建时间
                .orderByAsc("create_time")
                //默认每次拉取100条
                .last("LIMIT " + inputDto.getPageSize());

        return BeanUtil.copyToList(downloadFileEntityMapper.selectList(queryWrapper), QueryDownloadFileOutputDto.class);
    }

    @Override
    public Page<QueryDownloadFileListOutputDto> page(DownloadFilePageClientInputDto inputDto) {
        Page<DownloadFileEntity> page = downloadFileDao.page(inputDto);

        Page<QueryDownloadFileListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());

        List<QueryDownloadFileListOutputDto> outputDtoList = new ArrayList<>();
        for (DownloadFileEntity entity : page.getRecords()) {
            QueryDownloadFileListOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity), QueryDownloadFileListOutputDto.class, true);
            //设置过期状态
            outputDto.setExpirationDay(DateUtils.daysUntilExpiration(entity.getExpirationIn()));
            //附件信息
            outputDto.setMaterial(JSONUtil.toBean(entity.getMaterial(), MaterialDto.class));
            outputDtoList.add(outputDto);
        }
        pageResult.setRecords(outputDtoList);

        return pageResult;
    }

    private void handleTaskFailure(DownloadFileEntity entity,
                                   Throwable e) {
        String jsonParams = entity.getJsonParams();
        CreateDownloadFileClientInputDto inputDto = JSONUtil.toBean(jsonParams, CreateDownloadFileClientInputDto.class);

        UpdateDownloadFileClientInputDto fail = new UpdateDownloadFileClientInputDto();
        fail.setId(entity.getId());
        fail.setFailCause(e.getMessage());
        fail.setStatus(ExportTaskStatusEnum.FAIL);
        //重试机制
        if (entity.getUsageCount() >= inputDto.getFailRetryNumber()) {
            //关闭
            fail.setRetryStatus(1);
        }
        log.error("执行任务失败堆栈信息", e);
        this.updateById(fail);
    }
}