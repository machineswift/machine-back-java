package com.machine.service.data.file.download.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.file.download.IDataDownloadClient;
import com.machine.client.data.file.download.dto.input.DataDownloadContentDto;
import com.machine.client.data.file.download.dto.input.DataDownloadQueryInputDto;
import com.machine.client.data.file.download.dto.input.DataDownloadQueryPageInputDto;
import com.machine.client.data.file.download.dto.input.DataDownloadUpdateInputDto;
import com.machine.client.data.file.download.dto.output.DataDownloadDetailOutputDto;
import com.machine.client.data.file.download.dto.output.DataDownloadListOutputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.data.file.download.service.IDataDownloadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/data/file/download")
public class DataDownloadServer implements IDataDownloadClient {

    @Autowired
    private IDataDownloadService downloadService;

    @Override
    @PostMapping("create_task")
    public String createTask(@RequestBody @Validated DataDownloadContentDto inputDto) {
        log.info("下载中心新增任务，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return downloadService.createTask(inputDto);
    }

    @Override
    @GetMapping("retry")
    public void retry(@RequestParam("id") String id) {
        log.info("下载中心重试，id={}", id);
        downloadService.invoke(id);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated DataDownloadUpdateInputDto inputDto) {
        log.info("下载中心修改文件，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return downloadService.updateById(inputDto);
    }

    @Override
    @GetMapping("get_by_id")
    public DataDownloadDetailOutputDto getById(@RequestParam("id") String id) {
        return downloadService.getById(id);
    }

    @Override
    @PostMapping("query_by_limit")
    public List<DataDownloadDetailOutputDto> queryByLimit(@RequestBody @Validated DataDownloadQueryInputDto inputDto) {
        return downloadService.queryByLimit(inputDto);
    }

    @Override
    @PostMapping("select_page")
    public PageResponse<DataDownloadListOutputDto> selectPage(@RequestBody @Validated DataDownloadQueryPageInputDto inputDto) {
        inputDto.setUserId(AppContext.getContext().getUserId());
        Page<DataDownloadListOutputDto> pageResult = downloadService.selectPage(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }

}