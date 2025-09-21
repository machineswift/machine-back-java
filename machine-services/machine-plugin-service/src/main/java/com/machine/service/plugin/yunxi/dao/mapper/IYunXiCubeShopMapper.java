package com.machine.service.plugin.yunxi.dao.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.plugin.yunxi.dao.mapper.entity.YunXiCubeShopEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
@DS("yun_xi_cube_shop")
public interface IYunXiCubeShopMapper extends BaseMapper<YunXiCubeShopEntity> {

    List<YunXiCubeShopEntity> listByOffset(@Param("offset") Long offset,
                                           @Param("limit") int limit);

}
