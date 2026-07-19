package com.machine.server.camunda.demo.servicetask;

import lombok.extern.slf4j.Slf4j;
import org.cibseven.bpm.engine.delegate.DelegateExecution;
import org.cibseven.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component("camundaServiceTaskGoToRepair")
public class CamundaServiceTaskGoToRepair implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("开始上门修理");
        String currentActivityName = execution.getCurrentActivityName();
        log.info("当前活动名:{}", currentActivityName);
        execution.setVariable("repairManName", "王小满");
    }

}
