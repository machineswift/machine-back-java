package com.machine.service.data.sms.dao.impl;

import com.machine.client.data.sms.dto.input.DataSmsCountRecordInputDto;
import com.machine.service.data.sms.dao.ISmsRecordDao;
import com.machine.service.data.sms.dao.mapper.ISmsRecordMapper;
import com.machine.service.data.sms.dao.mapper.entity.SmsRecordEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SmsRecordDaoImpl implements ISmsRecordDao {

    @Autowired
    private ISmsRecordMapper smsRecordMapper;

    @Override
    public String insert(SmsRecordEntity entity) {
        smsRecordMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int countByCondition(DataSmsCountRecordInputDto inputDto) {
        return smsRecordMapper.countByCondition(inputDto);
    }

}
