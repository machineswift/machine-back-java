
package com.machine.service.data.sms.dao;

import com.machine.client.data.sms.dto.input.DataSmsCountRecordInputDto;
import com.machine.service.data.sms.dao.mapper.entity.SmsRecordEntity;

public interface ISmsRecordDao {

    String insert(SmsRecordEntity entity);

    int countByCondition(DataSmsCountRecordInputDto inputDto);
}
