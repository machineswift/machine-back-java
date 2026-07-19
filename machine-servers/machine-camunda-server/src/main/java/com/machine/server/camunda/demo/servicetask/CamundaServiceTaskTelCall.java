package com.machine.server.camunda.demo.servicetask;

import lombok.extern.slf4j.Slf4j;
import org.cibseven.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

@Slf4j
@Component("camundaServiceTaskTelCall")
public class CamundaServiceTaskTelCall {

    public int doCall(DelegateExecution execution) {
        log.info("开始电话回访");
        String repairManName = String.valueOf(execution.getVariable("repairManName"));
        log.info("您对{}的服务打几分", repairManName);
        return 10;
    }

    public void getScore(DelegateExecution execution) {
        log.info("查询评分");
        String repairManName = String.valueOf(execution.getVariable("repairManName"));
        int score = (int) execution.getVariable("score");
        log.info("顾客对{}的评分:{}", repairManName, score);
    }

}
