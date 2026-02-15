package com.machine.starter.obs.tool;

import com.machine.sdk.common.envm.base.ModuleEnum;
import com.machine.sdk.common.envm.base.ModuleEntityEnum;
import com.machine.starter.obs.path.IObsPathStrategy;
import com.machine.starter.obs.path.impl.ObsPathStrategyImpl;

public class ObsDownloadPathBuilder {

    private final IObsPathStrategy strategy;

    public ObsDownloadPathBuilder() {
        this.strategy = new ObsPathStrategyImpl();
    }


    /**
     * 构建【IAM-user】路径
     */
    public String forIamUserExport(String ownerId) {
        return strategy.buildDownloadPath(ModuleEnum.IAM, ModuleEntityEnum.IAM_USER, ownerId);
    }

    /**
     * 构建【DATA-shop】导出门店路径
     */
    public String forDataShopExport(String ownerId) {
        return strategy.buildDownloadPath(ModuleEnum.DATA, ModuleEntityEnum.DATA_SHOP, ownerId);
    }
}