package com.machine.service.data.mesage.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.data.mesage.dao.mapper.entity.MqDeadMessageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MqDeadMessageMapper extends BaseMapper<MqDeadMessageEntity> {
    void insertByReliableMessageId(@Param("reliableMessageId") String reliableMessageId);
}
