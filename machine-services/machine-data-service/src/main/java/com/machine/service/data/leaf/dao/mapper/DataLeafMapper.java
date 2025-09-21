package com.machine.service.data.leaf.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.data.leaf.dao.mapper.entity.DataLeafEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DataLeafMapper extends BaseMapper<DataLeafEntity> {

    Integer updateMaxId( @Param("bizTag") String bizTag,
                         @Param("maxId") Long maxId);
}
