package com.machine.service.data.franchisee.service.impl;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.franchisee.dto.output.DataFranchiseeShopRelationOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.service.data.franchisee.dao.IDataFranchiseeShopRelationDao;
import com.machine.service.data.franchisee.dao.mapper.entity.DataFranchiseeShopRelationEntity;
import com.machine.service.data.franchisee.service.IDataFranchiseeShopRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DataFranchiseeShopRelationServiceImpl implements IDataFranchiseeShopRelationService {

    @Autowired
    private IDataFranchiseeShopRelationDao franchiseeShopRelationDao;

    @Override
    public DataFranchiseeShopRelationOutputDto getByShopId(IdRequest request) {
        DataFranchiseeShopRelationEntity entity = franchiseeShopRelationDao.getByShopId(request.getId());
        if(entity == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataFranchiseeShopRelationOutputDto.class);
    }
}
