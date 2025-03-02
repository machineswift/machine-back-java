package com.machine.app.xxljob.job;

import com.machine.client.hrm.department.IHrmDepartmentClient;
import com.machine.client.hrm.employee.IHrmEmployeeLongTimeClient;
import com.machine.client.hrm.jobpost.IHrmJobPostClient;
import com.machine.sdk.common.context.AppContext;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.machine.sdk.common.constant.ContextConstant.SYSTEM_USER_ID;

@Slf4j
@Component
public class BeiSenXxlJob {

    @Autowired
    private IHrmJobPostClient jobPostClient;

    @Autowired
    private IHrmDepartmentClient departmentClient;

    @Autowired
    private IHrmEmployeeLongTimeClient employeeLongTimeClient;

    @XxlJob("BeiSenJobHandler")
    public void beiSenJobHandler() {
        AppContext.getContext().setUserId(SYSTEM_USER_ID);
        XxlJobHelper.log("XXL-JOB, 同步北森数据开始");
        jobPostClient.sync();
        departmentClient.sync();
        employeeLongTimeClient.sync();
        departmentClient.syncDepartmentPersonInCharge();
        XxlJobHelper.log("XXL-JOB, 同步北森数据结束");
        AppContext.getContext().clear();
    }

}
