package com.machine.service.data.shop.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.data.shop.dao.mapper.entity.UserCollectShopEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface IUserCollectShopMapper extends BaseMapper<UserCollectShopEntity> {

    List<String> listCollectedIdByShopIdSet(@Param("userId") String userId,
                                            @Param("shopIdSet") Set<String> shopIdSet);

}
