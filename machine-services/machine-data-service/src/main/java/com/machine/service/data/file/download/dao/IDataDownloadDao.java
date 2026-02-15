package com.machine.service.data.file.download.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.file.download.dto.input.DataDownloadQueryPageInputDto;
import com.machine.client.data.file.download.dto.input.DataDownloadQueryInputDto;
import com.machine.service.data.file.download.dao.mapper.entity.DataDownloadEntity;

import java.util.List;

public interface IDataDownloadDao {

    String insert(DataDownloadEntity entity);

    int update(DataDownloadEntity dataDownloadEntity);

    DataDownloadEntity getById(String id);

    List<DataDownloadEntity> queryByLimit(DataDownloadQueryInputDto inputDto);

    Page<DataDownloadEntity> selectPage(DataDownloadQueryPageInputDto inputDto);
}
