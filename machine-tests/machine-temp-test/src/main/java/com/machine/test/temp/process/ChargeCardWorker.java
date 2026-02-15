package com.machine.test.temp.process;

import java.util.logging.Logger;

import org.camunda.bpm.client.ExternalTaskClient;

public class ChargeCardWorker {

    private final static Logger LOGGER =
            Logger.getLogger(ChargeCardWorker.class.getName());

    public static void main(String[] args) {
        ExternalTaskClient client =
                ExternalTaskClient.create().baseUrl("http://localhost:8084/machine-camunda-server/engine-rest")
                        .asyncResponseTimeout(10000)
                        .build();

        // 订阅流程中指定的外部任务主题
        client.subscribe("charge-card")
                .lockDuration(1000) // 默认的锁定持续时间是20秒，可以覆盖它
                .handler((externalTask, externalTaskService) -> {
                    // 把业务逻辑放在这里

                    // 获取流程变量
                    String item = (String) externalTask.getVariable("item");
                    Long amount = (Long) externalTask.getVariable("amount");
                    LOGGER.info(
                            "==============Charging credit card with an amount of '" + amount +
                                    "' for the item '" + item + "'...");

                    // 完成任务
                    externalTaskService.complete(externalTask);
                })
                .open();
    }
}