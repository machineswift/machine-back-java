package com.machine.server.camunda.demo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.cibseven.bpm.engine.IdentityService;
import org.cibseven.bpm.engine.RuntimeService;
import org.cibseven.bpm.engine.variable.VariableMap;
import org.cibseven.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "【CAMUNDA】用户任务（请假）")
@RestController
@RequestMapping("camunda/demo")
public class CamundaUserTaskController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private IdentityService identityService;

    @Operation(summary = "开始")
    @GetMapping("start")
    public void treeSimple(@RequestParam(name = "processDefinitionKey") String processDefinitionKey) {
        log.info("开始流程");
        identityService.setAuthenticatedUserId("guest");

        VariableMap variableMap = Variables.createVariables();
        variableMap.put("isFree", false);
        runtimeService.startProcessInstanceByKey(processDefinitionKey,variableMap);
    }
}
