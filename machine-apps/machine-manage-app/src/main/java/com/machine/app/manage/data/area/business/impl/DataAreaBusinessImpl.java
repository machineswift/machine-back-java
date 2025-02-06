package com.machine.app.manage.data.area.business.impl;

import com.machine.app.manage.data.area.business.IDataAreaBusiness;
import com.machine.app.manage.data.area.controller.vo.request.DataAreaTreeRequestVo;
import com.machine.client.data.area.IDataAreaClient;
import com.machine.client.data.area.dto.output.AreaTreeOutputDto;
import com.machine.starter.redis.cache.RedisCacheDataArea;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class DataAreaBusinessImpl implements IDataAreaBusiness {

    @Autowired
    private RedisCacheDataArea redisCacheDataArea;

    @Autowired
    private IDataAreaClient areaClient;

    @Override
    public void clearCache() {
        areaClient.clearCache();
    }
    @Override
    public AreaTreeOutputDto treeSimple(DataAreaTreeRequestVo request) {
        return redisCacheDataArea.treeSimple();
    }
}
