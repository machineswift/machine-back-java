
package com.machine.service.data.shop.dao;

import com.machine.sdk.common.model.dto.IdCountDto;
import com.machine.service.data.shop.dao.mapper.entity.DataShopLabelOptionRelationEntity;

import java.util.List;
import java.util.Set;

public interface IDataShopLabelOptionRelationDao {

    int deleteByShopId(String shopId);

    void batchInsert(String shopId,
                     Set<String> labelOptionIdSet);

    int batchUpdateLabel(String labelOptionIdId,
                         Set<String> shopIdSet);

    List<String> listLabelOptionIdByShopId(String shopId);


    List<String> listShopIdByLabelOptionId(String labelOptionId);

    List<String> listShopIdByLabelOptionIds(Set<String> labelOptionIdSet);

    List<IdCountDto> countShopByLabelOptionIds(Set<String> labelOptionIdSet);

    List<DataShopLabelOptionRelationEntity> listByLabelOptionId(String labelOptionId);

    List<DataShopLabelOptionRelationEntity> listByShopIds(Set<String> shopIdSet);

}
