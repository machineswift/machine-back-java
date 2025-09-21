package com.machine.client.data.file;

import com.machine.client.data.file.dto.input.*;
import com.machine.client.data.file.dto.output.DataDownloadDetailOutputDto;
import com.machine.client.data.file.dto.output.DataDownloadListOutputDto;
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
public interface IDataDownloadClient {

    @PostMapping("create_task")
    String createTask(@RequestBody @Validated DataDownloadContentDto inputDto);

    @PostMapping("update")
    int update(@RequestBody @Validated DataDownloadUpdateInputDto inputDto);

    @GetMapping("retry")
    void retry(@RequestParam("id") String id);

    @GetMapping("get_by_id")
    DataDownloadDetailOutputDto getById(@RequestParam("id") String id);

    @PostMapping("query_by_limit")
    List<DataDownloadDetailOutputDto> queryByLimit(@RequestBody @Validated DataDownloadQueryInputDto inputDto);

    @PostMapping("select_page")
    PageResponse<DataDownloadListOutputDto> selectPage(@RequestBody @Validated DataDownloadQueryPageInputDto inputDto);
}