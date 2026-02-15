package com.machine.service.data.file.download.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.file.download.dto.input.DataDownloadContentDto;
import com.machine.client.data.file.download.dto.input.DataDownloadQueryInputDto;
import com.machine.client.data.file.download.dto.input.DataDownloadQueryPageInputDto;
import com.machine.client.data.file.download.dto.input.DataDownloadUpdateInputDto;
import com.machine.client.data.file.download.dto.output.DataDownloadDetailOutputDto;
import com.machine.client.data.file.download.dto.output.DataDownloadListOutputDto;

import java.util.List;

public interface IDataDownloadService {

    String createTask(DataDownloadContentDto inputDto);

    int updateById(DataDownloadUpdateInputDto inputDto);

    void invoke(String id);

    DataDownloadDetailOutputDto getById(String id);

    List<DataDownloadDetailOutputDto> queryByLimit(DataDownloadQueryInputDto dto);

    Page<DataDownloadListOutputDto> selectPage(DataDownloadQueryPageInputDto dto);

}