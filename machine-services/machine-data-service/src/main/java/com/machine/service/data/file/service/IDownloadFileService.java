package com.machine.service.data.file.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.file.dto.input.*;
import com.machine.client.data.file.dto.output.QueryDownloadFileDetailOutputDto;
import com.machine.client.data.file.dto.output.QueryDownloadFileListOutputDto;

import java.util.List;

public interface IDownloadFileService {

    String createFinish(DownloadFileCreateFinishInputDto inputDto);

    String createTask(DownloadFileContentDto inputDto);

    int updateById(DownloadFileUpdateInputDto inputDto);

    void invoke(String id);

    QueryDownloadFileDetailOutputDto getById(String id);

    List<QueryDownloadFileDetailOutputDto> queryByLimit(QueryDownloadFileQueryInputDto dto);

    Page<QueryDownloadFileListOutputDto> page(DownloadFileQueryPageInputDto dto);

}