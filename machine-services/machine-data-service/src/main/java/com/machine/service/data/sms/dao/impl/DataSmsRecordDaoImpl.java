package com.machine.service.data.sms.dao.impl;

import com.machine.client.data.sms.dto.input.DataSmsCountRecordInputDto;
import com.machine.service.data.sms.dao.IDataSmsRecordDao;
import com.machine.service.data.sms.dao.mapper.SmsRecordMapper;
import com.machine.service.data.sms.dao.mapper.entity.DataSmsRecordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DataSmsRecordDaoImpl implements IDataSmsRecordDao {

    @Autowired
    private SmsRecordMapper smsRecordMapper;

    @Override
    public String insert(DataSmsRecordEntity entity) {
        smsRecordMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int countByCondition(DataSmsCountRecordInputDto inputDto) {
        return smsRecordMapper.countByCondition(inputDto);
    }

}
