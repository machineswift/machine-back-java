package com.machine.service.data.file.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.file.dto.input.DownloadFilePageClientInputDto;
import com.machine.service.data.file.dao.mapper.entity.DownloadFileEntity;

import java.util.List;

public interface IDownloadFileDao {

    String insert(DownloadFileEntity entity);

    DownloadFileEntity getById(String id);

    List<DownloadFileEntity> selectByUrlMd5(String urlMd5);

    Page<DownloadFileEntity> page(DownloadFilePageClientInputDto inputDto);
}
