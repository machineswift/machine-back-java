package com.machine.service.data.shop.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.sdk.common.model.dto.IdCountDto;
import com.machine.service.data.shop.dao.mapper.entity.DataShopLabelOptionRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface DataShopLabelOptionRelationMapper extends BaseMapper<DataShopLabelOptionRelationEntity> {

    List<String> listLabelOptionIdByShopId(@Param("shopId") String shopId);

    List<String> listShopIdByLabelOptionId(@Param("labelOptionId") String labelOptionId);

    List<String> listShopIdByLabelOptionIds(@Param("labelOptionIdSet") Set<String> labelOptionIdSet);

    List<IdCountDto> countShopByLabelOptionIds(@Param("labelOptionIdSet") Set<String> labelOptionIdSet);

}
