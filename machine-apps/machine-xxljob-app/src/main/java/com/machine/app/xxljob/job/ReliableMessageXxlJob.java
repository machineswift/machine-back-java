package com.machine.app.xxljob.job;

import com.machine.client.data.message.IDataMqReliableMessageClient;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReliableMessageXxlJob {

    @Autowired
    private IDataMqReliableMessageClient reliableMessageClient;

    @XxlJob("ReliableMessageJobHandler")
    public void rabbitReliableMessageJobHandler() {
        XxlJobHelper.log("XXL-JOB,可靠消息 start .....");
        try {
            reliableMessageClient.resendMessage();
        } catch (Exception e) {
            XxlJobHelper.log(e);
            XxlJobHelper.handleFail("XXL-JOB,可靠消息 fail:" + e.getMessage());
        }
        XxlJobHelper.log("XXL-JOB,可靠消息 end .....");
    }
}
