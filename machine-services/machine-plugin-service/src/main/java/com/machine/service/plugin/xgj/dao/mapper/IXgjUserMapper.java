package com.machine.service.plugin.xgj.dao.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.plugin.xgj.dao.mapper.entity.XgjUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@DS("yun_xi_cube_shop")
public interface IXgjUserMapper extends BaseMapper<XgjUserEntity> {

    List<XgjUserEntity> listByOffset(@Param("offset") Long offset,
                                     @Param("limit") int limit);
}
