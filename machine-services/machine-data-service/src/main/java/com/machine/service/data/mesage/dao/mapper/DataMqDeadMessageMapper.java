package com.machine.service.data.mesage.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.data.mesage.dao.mapper.entity.DataMqDeadMessageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DataMqDeadMessageMapper extends BaseMapper<DataMqDeadMessageEntity> {
    void insertByReliableMessageId(@Param("reliableMessageId") String reliableMessageId);
}
