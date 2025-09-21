
package com.machine.service.data.shop.dao;

import java.util.List;
import java.util.Set;

public interface IDataUserCollectShopDao {

    int deleteByShopIdSet(String userId,
                          Set<String> unCollectShopIdSet);

    void insertByShopIdSet(String userId,
                           Set<String> unCollectShopIdSet);

    List<String> listCollectedIdByShopIdSet(String userId,
                                            Set<String> shopIdSet);
}
