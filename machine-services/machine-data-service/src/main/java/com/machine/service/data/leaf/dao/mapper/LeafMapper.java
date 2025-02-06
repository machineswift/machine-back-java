package com.machine.service.data.leaf.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.data.leaf.dao.mapper.entity.LeafEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LeafMapper extends BaseMapper<LeafEntity> {

    Integer updateMaxId( @Param("bizTag") String bizTag,
                         @Param("maxId") Long maxId);
}
