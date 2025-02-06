package com.machine.service.data.franchisee.service.impl;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.franchisee.dto.output.DataFranchiseeShopRelationOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.service.data.franchisee.dao.IFranchiseeShopRelationDao;
import com.machine.service.data.franchisee.dao.mapper.entity.FranchiseeShopRelationEntity;
import com.machine.service.data.franchisee.service.IFranchiseeShopRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FranchiseeShopRelationServiceImpl implements IFranchiseeShopRelationService {

    @Autowired
    private IFranchiseeShopRelationDao franchiseeShopRelationDao;

    @Override
    public DataFranchiseeShopRelationOutputDto getByShopId(IdRequest request) {
        FranchiseeShopRelationEntity entity = franchiseeShopRelationDao.getByShopId(request.getId());
        if(entity == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataFranchiseeShopRelationOutputDto.class);
    }
}
