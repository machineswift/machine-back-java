package com.machine.service.data.shop.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.data.shop.dao.mapper.entity.DataUserCollectShopEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface DataUserCollectShopMapper extends BaseMapper<DataUserCollectShopEntity> {

    List<String> listCollectedIdByShopIdSet(@Param("userId") String userId,
                                            @Param("shopIdSet") Set<String> shopIdSet);

}
