package com.machine.app.manage.hrm.employee.buiness.impl;

import com.machine.app.manage.hrm.employee.buiness.IHrmEmployeeBusiness;
import com.machine.client.hrm.employee.IHrmEmployeeLongTimeClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HrmEmployeeBusinessImpl implements IHrmEmployeeBusiness {

    @Autowired
    private IHrmEmployeeLongTimeClient employeeLongTimeClient;

    @Override
    public void sync() {
        employeeLongTimeClient.sync();
    }
}
