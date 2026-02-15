package com.machine.service.data.shop.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.shop.dto.input.*;
import com.machine.client.data.shop.dto.output.DataShopListSimpleOutputDto;
import com.machine.sdk.common.model.response.IdCodeResponse;
import com.machine.service.data.shop.dao.mapper.entity.DataShopEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface DataShopMapper extends BaseMapper<DataShopEntity> {

    String getIdByCode(@Param("code") String code);

    int countNotBindOrganization(@Param("inputDto") DataShopNotBindOrganizationInputDto inputDto);

    List<String> listNotBindOrganization(@Param("inputDto") DataShopNotBindOrganizationInputDto inputDto);

    List<String> listShopIdByShopCodeSet(@Param("shopCodeSet") Set<String> shopCodeSet);

    List<String> listAreaCodeByShopIdSet(@Param("shopIdSet") Set<String> shopIdSet);

    List<String> listShopIdByAreaCodeSet(@Param("areaCodeSet") Set<String> areaCodeSet);

    List<IdCodeResponse> selectSimpleByCodeSet(@Param("inputDto") Set<String> codeSet);

    List<DataShopEntity> listByOffset(@Param("inputDto") DataShopQueryListOffsetInputDto inputDto);

    List<DataShopListSimpleOutputDto> listAll(@Param("inputDto") DataShopQueryListAllInputDto inputDto);

    Page<DataShopEntity> selectPage(@Param("inputDto") DataShopQueryPageInputDto inputDto,
                                    IPage<DataShopEntity> page);

    Page<DataShopEntity> pageCollectShop(@Param("inputDto") DataSuperShopListCollectShopInputDto inputDto,
                                         IPage<DataShopEntity> page);

    List<DataShopEntity> listForExport(@Param("inputDto") DataShopExportInputDto inputDto);

}
