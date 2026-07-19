package com.machine.test.temp.camunda;

import lombok.extern.slf4j.Slf4j;
import org.cibseven.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.cibseven.bpm.client.task.ExternalTask;
import org.cibseven.bpm.client.task.ExternalTaskHandler;
import org.cibseven.bpm.client.task.ExternalTaskService;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@ExternalTaskSubscription(
        topicName = "topic_cangshi_zixiu",
        processDefinitionKey = "process_yuyue_weixiu_waibu",
        includeExtensionProperties = true,
        variableNames = "isFree",
        lockDuration = 30
)
public class CamundaExternalTaskSelfRepair implements ExternalTaskHandler {

    @Override
    public void execute(ExternalTask externalTask,
                        ExternalTaskService externalTaskService) {
        log.info("外部任务进入偿试自修");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        boolean isFree = externalTask.getVariable("isFree");
        if (isFree) {
            log.info("免费维修");
            externalTaskService.handleFailure(externalTask,
                    "维修是免费的，我不想自修了",
                    "这里可以打印异常", 0, 5000);
        } else {
            log.info("收费维修");
            externalTaskService.complete(externalTask);
        }
    }


}
