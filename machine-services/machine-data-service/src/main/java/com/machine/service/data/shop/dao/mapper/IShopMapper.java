package com.machine.service.data.shop.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.shop.dto.input.*;
import com.machine.service.data.shop.dao.mapper.entity.ShopEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface IShopMapper extends BaseMapper<ShopEntity> {

    int countNotBindOrganization(@Param("inputDto") DataShopNotBindOrganizationInputDto inputDto);

    List<String> listNotBindOrganization(@Param("inputDto") DataShopNotBindOrganizationInputDto inputDto);

    List<String> listShopIdByShopCodeSet(@Param("shopCodeSet") Set<String> shopCodeSet);

    List<String> listAreaCodeByShopIdSet(@Param("shopIdSet") Set<String> shopIdSet);

    List<String> listShopIdByAreaCodeSet(@Param("areaCodeSet") Set<String> areaCodeSet);

    List<ShopEntity> listByOffset(@Param("inputDto") DataShopQueryListOffsetInputDto inputDto);

    List<ShopEntity> listAll(@Param("inputDto") DataShopQueryListAllInputDto inputDto);

    Page<ShopEntity> pageShop(@Param("inputDto") DataShopQueryPageInputDto inputDto,
                              IPage<ShopEntity> page);

    Page<ShopEntity> pageCollectShop(@Param("inputDto") SuperShopListCollectShopInputDto inputDto,
                                     IPage<ShopEntity> page);

}
