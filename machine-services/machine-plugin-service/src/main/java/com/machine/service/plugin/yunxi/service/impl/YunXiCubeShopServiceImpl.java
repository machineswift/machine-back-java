package com.machine.service.plugin.yunxi.service.impl;

import com.machine.service.plugin.yunxi.dao.IYunXiCubeShopDao;
import com.machine.service.plugin.yunxi.service.IYunXiCubeShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class YunXiCubeShopServiceImpl implements IYunXiCubeShopService {

    @Autowired
    private IYunXiCubeShopDao cubeShopDao;
}
