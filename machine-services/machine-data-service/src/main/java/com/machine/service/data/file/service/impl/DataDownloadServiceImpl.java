package com.machine.service.data.file.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.file.dto.input.*;
import com.machine.client.data.file.dto.output.DataDownloadDetailOutputDto;
import com.machine.client.data.file.dto.output.DataDownloadListOutputDto;
import com.machine.sdk.common.envm.data.download.DataDownloadStatusEnum;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.service.data.file.dao.IDataDownloadDao;
import com.machine.service.data.file.dao.mapper.entity.DataDownloadEntity;
import com.machine.service.data.file.service.IDataDownloadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.machine.sdk.common.constant.CommonConstant.EMPTY_STR;

@Slf4j
@Service
public class DataDownloadServiceImpl implements IDataDownloadService {

    @Autowired
    private IDataDownloadDao downloadDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createTask(DataDownloadContentDto inputDto) {
        DataDownloadEntity insertEntity = BeanUtil.copyProperties(inputDto, DataDownloadEntity.class);
        insertEntity.setStatus(DataDownloadStatusEnum.READY);
        insertEntity.setContent(JSONUtil.toJsonStr(inputDto));
        return downloadDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(DataDownloadUpdateInputDto inputDto) {
        DataDownloadEntity dbEntity = downloadDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        DataDownloadEntity dataDownloadEntity = new DataDownloadEntity();
        dataDownloadEntity.setId(inputDto.getId());
        dataDownloadEntity.setStatus(inputDto.getStatus());

        //附件信息
        if (StrUtil.isNotBlank(inputDto.getAttachmentId())) {
            dataDownloadEntity.setAttachmentId(inputDto.getAttachmentId());
        }

        //内容
        String content = dbEntity.getContent();
        DataDownloadContentDto contentDto = JSONUtil.toBean(content, DataDownloadContentDto.class);
        contentDto.setUsageCount(inputDto.getUsageCount());
        dataDownloadEntity.setContent(JSONUtil.toJsonStr(contentDto));

        dataDownloadEntity.setFailCause(inputDto.getFailCause());
        return downloadDao.update(dataDownloadEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void invoke(String id) {
        DataDownloadEntity dbEntity = downloadDao.getById(id);
        if (dbEntity == null) {
            throw new IamBusinessException("data.download.service.invoke.taskNotExists", "任务不存在");
        }

        if (DataDownloadStatusEnum.READY == dbEntity.getStatus() ||
                DataDownloadStatusEnum.RUNNING == dbEntity.getStatus()) {
            return;
        }

        if (DataDownloadStatusEnum.FINISH == dbEntity.getStatus()) {
            throw new IamBusinessException("data.download.service.invoke.taskHasFinish", "任务已经完成");
        }

        DataDownloadEntity updateEntity = new DataDownloadEntity();
        updateEntity.setId(id);
        updateEntity.setStatus(DataDownloadStatusEnum.READY);
        updateEntity.setFailCause(EMPTY_STR);
        downloadDao.update(updateEntity);
    }

    @Override
    public DataDownloadDetailOutputDto getById(String id) {
        DataDownloadEntity dbEntity = downloadDao.getById(id);
        if (null == dbEntity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(dbEntity), DataDownloadDetailOutputDto.class);
    }

    @Override
    public List<DataDownloadDetailOutputDto> queryByLimit(DataDownloadQueryInputDto inputDto) {
        List<DataDownloadEntity> entityList = downloadDao.queryByLimit(inputDto);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataDownloadDetailOutputDto.class);
    }

    @Override
    public Page<DataDownloadListOutputDto> selectPage(DataDownloadQueryPageInputDto inputDto) {
        Page<DataDownloadEntity> page = downloadDao.selectPage(inputDto);
        Page<DataDownloadListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), DataDownloadListOutputDto.class));
        return pageResult;

    }
}