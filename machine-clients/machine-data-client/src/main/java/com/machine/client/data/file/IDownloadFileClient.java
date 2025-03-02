package com.machine.client.data.file;

import com.machine.client.data.file.dto.input.*;
import com.machine.client.data.file.dto.output.QueryDownloadFileDetailOutputDto;
import com.machine.client.data.file.dto.output.QueryDownloadFileListOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/download_file",
        configuration = OpenFeignDefaultConfig.class)
public interface IDownloadFileClient {

    @PostMapping("create_finish")
    String createFinish(@RequestBody @Validated DownloadFileCreateFinishInputDto inputDto);

    @PostMapping("create_task")
    String createTask(@RequestBody @Validated DownloadFileContentDto inputDto);

    @PostMapping("update")
    int update(@RequestBody @Validated DownloadFileUpdateInputDto inputDto);

    @GetMapping("schedule_task")
    void scheduleTask(@RequestParam("id") String id);

    @GetMapping("get_by_id")
    QueryDownloadFileDetailOutputDto getById(@RequestParam("id") String id);

    @PostMapping("query_by_limit")
    List<QueryDownloadFileDetailOutputDto> queryByLimit(@RequestBody @Validated QueryDownloadFileQueryInputDto inputDto);

    @PostMapping("page")
    PageResponse<QueryDownloadFileListOutputDto> page(@RequestBody @Validated DownloadFileQueryPageInputDto inputDto);
}