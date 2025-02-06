package com.machine.service.data.file.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.file.dto.input.*;
import com.machine.client.data.file.dto.output.QueryDownloadFileListOutputDto;
import com.machine.client.data.file.dto.output.QueryDownloadFileOutputDto;

import java.util.List;

public interface IDownloadFileService {

    void invoke(String id);

    String create(CreateDownloadFileClientInputDto inputDto);

    String createFinish(DownloadFileCreateFinishInputDto inputDto);

    void updateById(UpdateDownloadFileClientInputDto inputDto);

    void updateBatchById(List<UpdateDownloadFileClientInputDto> inputDto);

    QueryDownloadFileOutputDto getById(String id);

    List<QueryDownloadFileOutputDto> queryByLimit(QueryDownloadFileInputDto dto);

    Page<QueryDownloadFileListOutputDto> page(DownloadFilePageClientInputDto dto);

}