package com.machine.service.data.file.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.file.dto.input.DownloadFileQueryPageInputDto;
import com.machine.client.data.file.dto.input.QueryDownloadFileQueryInputDto;
import com.machine.service.data.file.dao.mapper.entity.DownloadFileEntity;

import java.util.List;

public interface IDownloadFileDao {

    String insert(DownloadFileEntity entity);

    int update(DownloadFileEntity downloadFileEntity);

    DownloadFileEntity getById(String id);

    List<DownloadFileEntity> selectByUrlMd5(String urlMd5);

    List<DownloadFileEntity> queryByLimit(QueryDownloadFileQueryInputDto inputDto);

    Page<DownloadFileEntity> page(DownloadFileQueryPageInputDto inputDto);
}
