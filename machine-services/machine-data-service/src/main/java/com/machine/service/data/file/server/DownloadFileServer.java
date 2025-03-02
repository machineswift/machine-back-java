package com.machine.service.data.file.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.file.IDownloadFileClient;
import com.machine.client.data.file.dto.input.*;
import com.machine.client.data.file.dto.output.QueryDownloadFileDetailOutputDto;
import com.machine.client.data.file.dto.output.QueryDownloadFileListOutputDto;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.data.file.service.IDownloadFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/data/download_file")
public class DownloadFileServer implements IDownloadFileClient {

    @Autowired
    private IDownloadFileService downloadFileService;

    @Override
    @PostMapping("create_finish")
    public String createFinish(@RequestBody @Validated DownloadFileCreateFinishInputDto inputDto) {
        log.info("下载中心新增文件，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return downloadFileService.createFinish(inputDto);
    }

    @Override
    @PostMapping("create_task")
    public String createTask(@RequestBody @Validated DownloadFileContentDto inputDto) {
        log.info("下载中心新增任务，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return downloadFileService.createTask(inputDto);
    }

    @Override
    @GetMapping("schedule_task")
    public void scheduleTask(@RequestParam("id") String id) {
        log.info("下载中心调度任务，id={}", id);
        downloadFileService.invoke(id);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated DownloadFileUpdateInputDto inputDto) {
        log.info("下载中心修改文件，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return downloadFileService.updateById(inputDto);
    }

    @Override
    @GetMapping("get_by_id")
    public QueryDownloadFileDetailOutputDto getById(@RequestParam("id") String id) {
        return downloadFileService.getById(id);
    }

    @Override
    @PostMapping("query_by_limit")
    public List<QueryDownloadFileDetailOutputDto> queryByLimit(@RequestBody @Validated QueryDownloadFileQueryInputDto inputDto) {
        return downloadFileService.queryByLimit(inputDto);
    }

    @Override
    @PostMapping("page")
    public PageResponse<QueryDownloadFileListOutputDto> page(@RequestBody @Validated DownloadFileQueryPageInputDto inputDto) {
        Page<QueryDownloadFileListOutputDto> pageResult = downloadFileService.page(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }

}