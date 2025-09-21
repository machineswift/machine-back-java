package com.machine.service.data.sms.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.client.data.sms.dto.input.DataSmsCountRecordInputDto;
import com.machine.service.data.sms.dao.mapper.entity.DataSmsRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SmsRecordMapper extends BaseMapper<DataSmsRecordEntity> {

    int countByCondition(@Param("inputDto") DataSmsCountRecordInputDto inputDto);

}
