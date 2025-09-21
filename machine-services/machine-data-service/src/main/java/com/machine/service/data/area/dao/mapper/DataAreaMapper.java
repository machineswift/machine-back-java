package com.machine.service.data.area.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.data.area.dao.mapper.entity.DataAreaEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DataAreaMapper extends BaseMapper<DataAreaEntity> {

    int updateIdByCode(@Param("code") String code);
}
