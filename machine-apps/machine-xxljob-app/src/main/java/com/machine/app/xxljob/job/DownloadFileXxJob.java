package com.machine.app.xxljob.job;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.file.IDownloadFileClient;
import com.machine.client.data.file.dto.input.CreateDownloadFileClientInputDto;
import com.machine.client.data.file.dto.input.UpdateDownloadFileClientInputDto;
import com.machine.client.data.file.dto.input.QueryDownloadFileInputDto;
import com.machine.client.data.file.dto.output.QueryDownloadFileOutputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.data.ExportTaskStatusEnum;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.machine.sdk.common.constant.ContextConstant.SYSTEM_USER_ID;

@Slf4j
@Component
public class DownloadFileXxJob {

    @Value("${xxjob.downloadFile.taskDownloadNumber:5}")
    private static final int TASK_DOWNLOAD_NUMBER = 5;

    private final IDownloadFileClient downloadFileClient;
    private final ExecutorService executorService = Executors.newFixedThreadPool(TASK_DOWNLOAD_NUMBER);

    public DownloadFileXxJob(IDownloadFileClient downloadFileClient) {
        this.downloadFileClient = downloadFileClient;
    }

    @XxlJob("DownloadFileTaskScheduledJobHandler")
    public void downloadFileTaskScheduledJobHandler() {
        log.info("定时任务开始");
        XxlJobHelper.log("XXL-JOB,DownloadFileTaskScheduledJobHandler start .....");

        try {
            AppContext.getContext().setUserId(SYSTEM_USER_ID);
            //查询
            List<QueryDownloadFileOutputDto> allTasks = getAllTasks();
            //超时
            handleOvertimeTasks(allTasks);
            //线程池 执行任务
            executeTasks(filterTasksToExecute(allTasks));

        } catch (Exception e) {
            log.error("定时任务执行异常", e);
            XxlJobHelper.log(e);
            XxlJobHelper.handleFail("XXL-JOB,DownloadFileTaskScheduledJobHandler fail:" + e.getMessage());
        } finally {
            XxlJobHelper.log("XXL-JOB,DownloadFileTaskScheduledJobHandler end .....");
            AppContext.getContext().clear();
        }
    }

    private List<QueryDownloadFileOutputDto> getAllTasks() {
        QueryDownloadFileInputDto queryDto = new QueryDownloadFileInputDto();
        //重试状态  0 代表可以重试  1不可以
        queryDto.setRetryStatus(0);
        queryDto.setPageSize(100);
        queryDto.setStatusList(Arrays.asList(ExportTaskStatusEnum.RUNNING.getCode(), ExportTaskStatusEnum.FAIL.getCode(), ExportTaskStatusEnum.READY.getCode()));
        return downloadFileClient.queryByLimit(queryDto);
    }

    private void handleOvertimeTasks(List<QueryDownloadFileOutputDto> allTasks) {
        long currentTimeMillis = System.currentTimeMillis();
        List<QueryDownloadFileOutputDto> overtimeTasks = allTasks.stream()
                //判断 超时
                .filter(dto -> isOvertime(dto, currentTimeMillis))
                //设置 超时原因
                .peek(this::markAsFail).toList();

        if (!overtimeTasks.isEmpty()) {
            List<UpdateDownloadFileClientInputDto> updateBatch = overtimeTasks.stream().map(this::convertToUpdateDto).collect(Collectors.toList());
            downloadFileClient.batchUpdate(updateBatch);
        }
    }

    private boolean isOvertime(QueryDownloadFileOutputDto dto,
                               long currentTimeMillis) {
        //超时 只有进行状态  其他状态 跳过
        if (!ExportTaskStatusEnum.RUNNING.getCode().equals(dto.getStatus())) {
            return false;
        }
        //用户自定义时间 单位是分钟
        CreateDownloadFileClientInputDto params = JSONUtil.toBean(dto.getJsonParams(), CreateDownloadFileClientInputDto.class);
        return params != null && currentTimeMillis - dto.getUpdateTime() > params.getOverTimeMinute() * 60000;
    }

    /**
     * 设置超时 原因
     */
    private void markAsFail(QueryDownloadFileOutputDto dto) {
        dto.setStatus(ExportTaskStatusEnum.FAIL);
        dto.setFailCause("over time");
    }

    private UpdateDownloadFileClientInputDto convertToUpdateDto(QueryDownloadFileOutputDto dto) {
        UpdateDownloadFileClientInputDto updateDto = new UpdateDownloadFileClientInputDto();
        updateDto.setId(dto.getId());
        updateDto.setStatus(dto.getStatus());
        updateDto.setFailCause(dto.getFailCause());
        return updateDto;
    }

    /**
     * 计算执行条数
     * 不能超过当前任务总数
     * <p> <p> <p>
     */
    private List<QueryDownloadFileOutputDto> filterTasksToExecute(List<QueryDownloadFileOutputDto> allTasks) {
        //进行中条数
        int runningTasksCount = (int) allTasks.stream().filter(dto -> dto.getStatus().equals(ExportTaskStatusEnum.RUNNING.getCode())).count();

        //任务数-进行中=空余任务数
        return allTasks.stream().filter(dto -> dto.getStatus().equals(ExportTaskStatusEnum.FAIL.getCode()) ||
                        dto.getStatus().equals(ExportTaskStatusEnum.READY.getCode()))
                //保证 条数
                .limit(TASK_DOWNLOAD_NUMBER - runningTasksCount).collect(Collectors.toList());
    }

    private void executeTasks(List<QueryDownloadFileOutputDto> tasksToExecute) {
        if (tasksToExecute.isEmpty()) {
            log.info("当前没有需要执行的导出任务");
            XxlJobHelper.log("当前没有需要执行的导出任务");
            return;
        }
        XxlJobHelper.log("执行的导出任务明细:{}", JSONUtil.toJsonStr(tasksToExecute));
        tasksToExecute.forEach(task -> executorService.execute(() -> downloadFileClient.invoke(task.getId())));
        log.info("存在执行任务,结束");
    }
}