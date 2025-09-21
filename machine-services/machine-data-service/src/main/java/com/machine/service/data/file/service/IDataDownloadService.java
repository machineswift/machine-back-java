package com.machine.service.data.file.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.file.dto.input.*;
import com.machine.client.data.file.dto.output.DataDownloadDetailOutputDto;
import com.machine.client.data.file.dto.output.DataDownloadListOutputDto;

import java.util.List;

public interface IDataDownloadService {

    String createTask(DataDownloadContentDto inputDto);

    int updateById(DataDownloadUpdateInputDto inputDto);

    void invoke(String id);

    DataDownloadDetailOutputDto getById(String id);

    List<DataDownloadDetailOutputDto> queryByLimit(DataDownloadQueryInputDto dto);

    Page<DataDownloadListOutputDto> selectPage(DataDownloadQueryPageInputDto dto);

}