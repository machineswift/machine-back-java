package com.machine.app.xxljob.job;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.file.download.IDataDownloadClient;
import com.machine.client.data.file.download.dto.input.DataDownloadContentDto;
import com.machine.client.data.file.download.dto.input.DataDownloadUpdateInputDto;
import com.machine.client.data.file.download.dto.input.DataDownloadQueryInputDto;
import com.machine.client.data.file.download.dto.output.DataDownloadDetailOutputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.data.file.DataDownloadStatusEnum;
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
public class DownloadXxJob {

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
    private IDataDownloadClient downloadClient;


    @XxlJob("DownloadTaskScheduledJobHandler")
    public void downloadTaskScheduledJobHandler() {
        XxlJobHelper.log("XXL-JOB,下载中心调度任务 start .....");

        try {
            AppContext.getContext().setUserId(SYSTEM_USER_ID);

            DataDownloadQueryInputDto queryRunningInputDto = new DataDownloadQueryInputDto();
            queryRunningInputDto.setStatus(DataDownloadStatusEnum.RUNNING);
            queryRunningInputDto.setLimit(Integer.MAX_VALUE);
            List<DataDownloadDetailOutputDto> runningTasks = downloadClient.queryByLimit(queryRunningInputDto);

            // 处理超时任务
            int runningTaskSize = 0;
            int overtimeTaskSize = 0;
            if (CollectionUtil.isNotEmpty(runningTasks)) {
                runningTaskSize = runningTasks.size();
                for (DataDownloadDetailOutputDto runningTask : runningTasks) {
                    String content = runningTask.getContent();
                    DataDownloadContentDto contentDto = JSONUtil.toBean(content, DataDownloadContentDto.class);
                    Integer usageCount = contentDto.getUsageCount();

                    if (runningTask.getUpdateTime().compareTo(
                            System.currentTimeMillis() - contentDto.getOverTimeMinute() * 60 * 1000) < 0) {
                        overtimeTaskSize++;

                        //超时 修改任务
                        DataDownloadUpdateInputDto updateInputDto = new DataDownloadUpdateInputDto();
                        updateInputDto.setId(runningTask.getId());

                        if (usageCount >= contentDto.getFailRetryNumber()) {
                            updateInputDto.setStatus(DataDownloadStatusEnum.DEAD);
                        } else {
                            updateInputDto.setStatus(DataDownloadStatusEnum.FAIL);
                        }
                        updateInputDto.setFailCause("执行超时");
                        downloadClient.update(updateInputDto);
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
            DataDownloadQueryInputDto queryFailInputDto = new DataDownloadQueryInputDto();
            queryFailInputDto.setStatus(DataDownloadStatusEnum.FAIL);
            queryFailInputDto.setLimit(remainingQuantity);
            List<DataDownloadDetailOutputDto> failTasks = downloadClient.queryByLimit(queryFailInputDto);
            if (CollectionUtil.isNotEmpty(failTasks)) {
                // 剩余空队列数量
                remainingQuantity = remainingQuantity - failTasks.size();
                for (DataDownloadDetailOutputDto failTask : failTasks) {
                    executorService.execute(() -> executeTask(failTask));
                }
            }

            if (remainingQuantity <= 0) {
                XxlJobHelper.log("XXL-JOB,下载中心调度任务 队列已满 .....");
                return;
            }

            //未开始的任务
            DataDownloadQueryInputDto queryReadyInputDto = new DataDownloadQueryInputDto();
            queryReadyInputDto.setStatus(DataDownloadStatusEnum.READY);
            queryReadyInputDto.setLimit(remainingQuantity);
            List<DataDownloadDetailOutputDto> readyTasks = downloadClient.queryByLimit(queryReadyInputDto);
            if (CollectionUtil.isNotEmpty(readyTasks)) {
                for (DataDownloadDetailOutputDto readyTask : readyTasks) {
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
    private void executeTask(DataDownloadDetailOutputDto outputDto) {
        AppContext.getContext().setUserId(SYSTEM_USER_ID);

        //内容
        String id = outputDto.getId();
        String content = outputDto.getContent();
        DataDownloadContentDto contentDto = JSONUtil.toBean(content, DataDownloadContentDto.class);

        try {
            //进行中
            DataDownloadUpdateInputDto running = new DataDownloadUpdateInputDto();
            running.setId(id);
            running.setStatus(DataDownloadStatusEnum.RUNNING);
            running.setUsageCount(contentDto.getUsageCount() + 1);
            downloadClient.update(running);

            //反射
            Object bean = applicationContext.getBean(Class.forName(contentDto.getClassName()));
            Class<?> paramsClass = Class.forName(contentDto.getParamsClassName());
            Method method = bean.getClass().getMethod(contentDto.getMethodName(), paramsClass);
            String attachmentId = (String) method.invoke(bean, JSONUtil.toBean(contentDto.getJsonParams(), paramsClass));

            // 完成
            DataDownloadUpdateInputDto finish = new DataDownloadUpdateInputDto();
            finish.setId(id);
            finish.setStatus(DataDownloadStatusEnum.FINISH);
            finish.setAttachmentId(attachmentId);
            downloadClient.update(finish);
        } catch (Exception e) {
            //异常处理
            DataDownloadUpdateInputDto fail = new DataDownloadUpdateInputDto();
            fail.setId(id);
            fail.setFailCause(e.getMessage());
            fail.setStatus(DataDownloadStatusEnum.FAIL);
            //重试机制
            if (contentDto.getUsageCount() >= contentDto.getFailRetryNumber()) {
                //关闭
                fail.setStatus(DataDownloadStatusEnum.DEAD);
            }
            fail.setFailCause(ExceptionUtil.stacktraceToString(e));
            downloadClient.update(fail);
        } finally {
            AppContext.getContext().clear();
        }
    }
}