package com.machine.service.data.mesage.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.data.mesage.dao.mapper.entity.MqReliableMessageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MqReliableMessageMapper extends BaseMapper<MqReliableMessageEntity> {

    void update4Exception(@Param("id")String id,
                          @Param("nextUnixTimestamp") Long nextUnixTimestamp);

    int update4Resend(@Param("id") String id,
                      @Param("updateTime") long updateTime,
                      @Param("nextUnixTimestamp") Long nextUnixTimestamp);

    Boolean contain(@Param("id") String id);

    List<MqReliableMessageEntity> dynamicSelect(@Param("unixTimestamp") Long unixTimestamp);

}
