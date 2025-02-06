
package com.machine.service.data.shop.dao;

import com.machine.sdk.common.model.dto.IdCountDto;
import com.machine.service.data.shop.dao.mapper.entity.ShopLabelOptionRelationEntity;

import java.util.List;
import java.util.Set;

public interface IShopLabelOptionRelationDao {

    int deleteByShopId(String shopId);

    void batchInsert(String shopId,
                     Set<String> labelOptionIdSet);

    int batchUpdateLabel(String labelOptionIdId,
                         Set<String> shopIdSet);

    List<String> listLabelOptionIdByShopId(String shopId);


    List<String> listShopIdByLabelOptionId(String labelOptionId);

    List<String> listShopIdByLabelOptionIds(Set<String> labelOptionIdSet);

    List<IdCountDto> countShopByLabelOptionIds(Set<String> labelOptionIdSet);

    List<ShopLabelOptionRelationEntity> listByLabelOptionId(String labelOptionId);

    List<ShopLabelOptionRelationEntity> listByShopIds(Set<String> shopIdSet);

}
