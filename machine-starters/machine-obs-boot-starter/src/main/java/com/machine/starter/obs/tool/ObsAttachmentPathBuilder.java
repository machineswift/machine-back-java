package com.machine.starter.obs.tool;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.exception.data.DataBusinessException;
import com.machine.starter.obs.path.IObsPathStrategy;
import com.machine.starter.obs.path.impl.ObsPathStrategyImpl;

public class ObsAttachmentPathBuilder {

    private final IObsPathStrategy strategy;

    public ObsAttachmentPathBuilder() {
        this.strategy = new ObsPathStrategyImpl();
    }


    /**
     * 构建附件路径
     */
    public String forAttachment(ModuleEntityEnum entityEnum,
                                String entityId,
                                String attachmentGroup,
                                int version) {
        ModuleEnum module;

        switch (entityEnum) {
            case BIAM_USER,
                 BIAM_ROLE,
                 BIAM_PERMISSION,
                 BIAM_ORGANIZATION -> module = ModuleEnum.BIAM;

            case DATA_MATERIAL,
                 DATA_DOWNLOAD,
                 DATA_BRAND,
                 DATA_SHOP,
                 DATA_AREA,
                 DATA_TAG -> module = ModuleEnum.DATA;

            default -> throw new DataBusinessException("data.attachment.business.upload.wrongEntityModule",
                    "上传附件没找到所属的模块");

        }
        return strategy.buildAttachmentPath(module, entityEnum, entityId, attachmentGroup, version);
    }
}