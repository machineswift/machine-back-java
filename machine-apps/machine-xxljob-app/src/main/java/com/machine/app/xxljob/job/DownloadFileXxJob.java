package com.machine.app.xxljob.job;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.file.IDownloadFileClient;
import com.machine.client.data.file.dto.input.DownloadFileContentDto;
import com.machine.client.data.file.dto.input.DownloadFileUpdateInputDto;
import com.machine.client.data.file.dto.input.QueryDownloadFileQueryInputDto;
import com.machine.client.data.file.dto.output.QueryDownloadFileDetailOutputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.data.download.ExportTaskStatusEnum;
import com.machine.sdk.common.model.dto.data.MaterialDto;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.machine.sdk.common.constant.ContextConstant.SYSTEM_USER_ID;


@Component
public class DownloadFileXxJob {

    /**
     * 同时执行任务的数量
     */
    private static final int TASK_DOWNLOAD_NUMBER = 5;

    private final ExecutorService executorService = new ThreadPoolExecutor(
            TASK_DOWNLOAD_NUMBER, TASK_DOWNLOAD_NUMBER + 3,
            300L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(TASK_DOWNLOAD_NUMBER * 2),
            new ThreadPoolExecutor.AbortPolicy());


    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private IDownloadFileClient downloadFileClient;

    @XxlJob("DownloadFileTaskScheduledJobHandler")
    public void downloadFileTaskScheduledJobHandler() {
        XxlJobHelper.log("XXL-JOB,下载中心调度任务 start .....");

        try {
            AppContext.getContext().setUserId(SYSTEM_USER_ID);

            QueryDownloadFileQueryInputDto queryRunningInputDto = new QueryDownloadFileQueryInputDto();
            queryRunningInputDto.setStatus(ExportTaskStatusEnum.RUNNING);
            queryRunningInputDto.setLimit(Integer.MAX_VALUE);
            List<QueryDownloadFileDetailOutputDto> runningTasks = downloadFileClient.queryByLimit(queryRunningInputDto);

            // 处理超时任务
            int runningTaskSize = 0;
            int overtimeTaskSize = 0;
            if (CollectionUtil.isNotEmpty(runningTasks)) {
                runningTaskSize = runningTasks.size();
                for (QueryDownloadFileDetailOutputDto runningTask : runningTasks) {
                    String content = runningTask.getContent();
                    DownloadFileContentDto contentDto = JSONUtil.toBean(content, DownloadFileContentDto.class);
                    Integer usageCount = contentDto.getUsageCount();

                    if (runningTask.getUpdateTime().compareTo(
                            System.currentTimeMillis() - contentDto.getOverTimeMinute() * 60 * 1000) < 0) {
                        overtimeTaskSize++;

                        //超时 修改任务
                        DownloadFileUpdateInputDto updateInputDto = new DownloadFileUpdateInputDto();
                        updateInputDto.setId(runningTask.getId());

                        if (usageCount >= contentDto.getFailRetryNumber()) {
                            updateInputDto.setStatus(ExportTaskStatusEnum.DEAD);
                        } else {
                            updateInputDto.setStatus(ExportTaskStatusEnum.FAIL);
                        }
                        updateInputDto.setFailCause("执行超时");
                        downloadFileClient.update(updateInputDto);
                    }
                }
            }

            // 任务是否满负荷
            int remainingQuantity = TASK_DOWNLOAD_NUMBER - (runningTaskSize - overtimeTaskSize);
            if (remainingQuantity <= 0) {
                XxlJobHelper.log("XXL-JOB,下载中心调度任务 队列已满 .....");
                return;
            }

            //失败的任务
            QueryDownloadFileQueryInputDto queryFailInputDto = new QueryDownloadFileQueryInputDto();
            queryFailInputDto.setStatus(ExportTaskStatusEnum.FAIL);
            queryFailInputDto.setLimit(remainingQuantity);
            List<QueryDownloadFileDetailOutputDto> failTasks = downloadFileClient.queryByLimit(queryFailInputDto);
            if (CollectionUtil.isNotEmpty(failTasks)) {
                // 剩余空队列数量
                remainingQuantity = remainingQuantity - failTasks.size();
                for (QueryDownloadFileDetailOutputDto failTask : failTasks) {
                    executorService.execute(() -> executeTask(failTask));
                }
            }

            if (remainingQuantity <= 0) {
                XxlJobHelper.log("XXL-JOB,下载中心调度任务 队列已满 .....");
                return;
            }

            //未开始的任务
            QueryDownloadFileQueryInputDto queryReadyInputDto = new QueryDownloadFileQueryInputDto();
            queryReadyInputDto.setStatus(ExportTaskStatusEnum.READY);
            queryReadyInputDto.setLimit(remainingQuantity);
            List<QueryDownloadFileDetailOutputDto> readyTasks = downloadFileClient.queryByLimit(queryReadyInputDto);
            if (CollectionUtil.isNotEmpty(readyTasks)) {
                for (QueryDownloadFileDetailOutputDto readyTask : readyTasks) {
                    executorService.execute(() -> executeTask(readyTask));
                }
            }
        } catch (Exception e) {
            XxlJobHelper.log(e);
            XxlJobHelper.handleFail("XXL-JOB,下载中心调度任务 fail:" + e.getMessage());
        } finally {
            XxlJobHelper.log("XXL-JOB,下载中心调度任务 end .....");
            AppContext.getContext().clear();
        }
    }

    /**
     * 执行任务
     */
    private void executeTask(QueryDownloadFileDetailOutputDto outputDto) {
        AppContext.getContext().setUserId(SYSTEM_USER_ID);

        //内容
        String id = outputDto.getId();
        String content = outputDto.getContent();
        DownloadFileContentDto contentDto = JSONUtil.toBean(content, DownloadFileContentDto.class);

        try {
            //进行中
            DownloadFileUpdateInputDto running = new DownloadFileUpdateInputDto();
            running.setId(id);
            running.setStatus(ExportTaskStatusEnum.RUNNING);
            running.setUsageCount(contentDto.getUsageCount() + 1);
            downloadFileClient.update(running);

            //反射
            Object bean = applicationContext.getBean(Class.forName(contentDto.getClassName()));
            Class<?> paramsClass = Class.forName(contentDto.getParamsClassName());
            Method method = bean.getClass().getMethod(contentDto.getMethodName(), paramsClass);
            MaterialDto material = (MaterialDto) method.invoke(bean, JSONUtil.toBean(contentDto.getJsonParams(), paramsClass));

            // 完成
            DownloadFileUpdateInputDto finish = new DownloadFileUpdateInputDto();
            finish.setId(id);
            finish.setStatus(ExportTaskStatusEnum.DEAD);
            finish.setMaterial(material);
            finish.setFailCause("结果为空");
            downloadFileClient.update(finish);
        } catch (Exception e) {
            //异常处理
            DownloadFileUpdateInputDto fail = new DownloadFileUpdateInputDto();
            fail.setId(id);
            fail.setFailCause(e.getMessage());
            fail.setStatus(ExportTaskStatusEnum.FAIL);
            //重试机制
            if (contentDto.getUsageCount() >= contentDto.getFailRetryNumber()) {
                //关闭
                fail.setStatus(ExportTaskStatusEnum.DEAD);
            }
            fail.setFailCause(ExceptionUtil.stacktraceToString(e));
            downloadFileClient.update(fail);
        } finally {
            AppContext.getContext().clear();
        }
    }
}