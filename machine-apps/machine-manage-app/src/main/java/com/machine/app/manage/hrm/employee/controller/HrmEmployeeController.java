package com.machine.app.manage.hrm.employee.controller;

import com.machine.app.manage.hrm.employee.buiness.IHrmEmployeeBusiness;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【HRM】员工模块")
@RestController
@RequestMapping("manage/hrm/employee")
public class HrmEmployeeController {

    @Autowired
    private IHrmEmployeeBusiness employeeBusiness;

}