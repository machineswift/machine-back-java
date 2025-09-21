
package com.machine.service.data.sms.dao;

import com.machine.client.data.sms.dto.input.DataSmsCountRecordInputDto;
import com.machine.service.data.sms.dao.mapper.entity.DataSmsRecordEntity;

public interface IDataSmsRecordDao {

    String insert(DataSmsRecordEntity entity);

    int countByCondition(DataSmsCountRecordInputDto inputDto);
}
