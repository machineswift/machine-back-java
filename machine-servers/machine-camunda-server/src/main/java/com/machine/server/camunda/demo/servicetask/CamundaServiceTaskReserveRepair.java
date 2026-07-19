package com.machine.server.camunda.demo.servicetask;

import lombok.extern.slf4j.Slf4j;
import org.cibseven.bpm.engine.delegate.DelegateExecution;
import org.cibseven.bpm.engine.delegate.JavaDelegate;

@Slf4j
public class CamundaServiceTaskReserveRepair implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("进入到预约家电修理任务");
        String currentActivityName = execution.getCurrentActivityName();

        // 预约的具体调用

        String processDefinitionId = execution.getProcessDefinitionId();
        log.info("当前活动名称:{} 流程定义id:{}", currentActivityName, processDefinitionId);
    }

}
