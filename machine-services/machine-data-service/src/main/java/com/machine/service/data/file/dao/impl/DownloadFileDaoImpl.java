package com.machine.service.data.file.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.file.dto.input.DownloadFilePageClientInputDto;
import com.machine.service.data.file.dao.IDownloadFileDao;
import com.machine.service.data.file.dao.mapper.DownloadFileEntityMapper;
import com.machine.service.data.file.dao.mapper.entity.DownloadFileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DownloadFileDaoImpl  implements IDownloadFileDao {

    @Autowired
    private DownloadFileEntityMapper downloadFileEntityMapper;

    @Override
    public String insert(DownloadFileEntity entity) {
        downloadFileEntityMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public DownloadFileEntity getById(String id) {
        return downloadFileEntityMapper.selectById(id);
    }

    @Override
    public List<DownloadFileEntity> selectByUrlMd5(String urlMd5) {
        Wrapper<DownloadFileEntity> queryWrapper = new LambdaQueryWrapper<DownloadFileEntity>()
                .eq(DownloadFileEntity::getUrlMd5, urlMd5);
        return downloadFileEntityMapper.selectList(queryWrapper);
    }

    @Override
    public Page<DownloadFileEntity> page(DownloadFilePageClientInputDto inputDto) {
        IPage<DownloadFileEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return downloadFileEntityMapper.page(inputDto, page);
    }
}
