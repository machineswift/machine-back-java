package com.machine.service.data.mesage.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.data.mesage.dao.mapper.entity.MqMessageExternalEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MqMessageExternalMapper extends BaseMapper<MqMessageExternalEntity> {
    void insertByReliableMessageId(String reliableMessageId);

    void deleteByReliableMessageId(@Param("reliableMessageId") String reliableMessageId);

    void updateTableNameByReliableMessageId(@Param("reliableMessageId") String reliableMessageId);

    void updateFailReasonByReliableMessageId(@Param("reliableMessageId") String reliableMessageId,
                                             @Param("failReason") String failReason);
}
