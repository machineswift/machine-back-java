package com.machine.starter.obs.tool;

import com.machine.sdk.common.envm.base.ModuleEntityEnum;
import com.machine.sdk.common.envm.base.ModuleEnum;
import com.machine.sdk.common.exception.data.DataBusinessException;
import com.machine.starter.obs.path.IObsPathStrategy;
import com.machine.starter.obs.path.impl.ObsPathStrategyImpl;
import org.springframework.web.bind.annotation.RequestParam;

public class ObsAttachmentPathBuilder {

    private final IObsPathStrategy strategy;

    public ObsAttachmentPathBuilder() {
        this.strategy = new ObsPathStrategyImpl();
    }


    /**
     * 构建附件路径
     */
    public String forAttachment(ModuleEntityEnum entity,
                                String entityId,
                                String version) {
        ModuleEnum module;

        switch (entity) {
            case IAM_USER,
                 IAM_ROLE,
                 IAM_PERMISSION,
                 IAM_ORGANIZATION -> module = ModuleEnum.IAM;

            case DATA_MATERIAL,
                 DATA_DOWNLOAD,
                 DATA_BRAND,
                 DATA_SHOP,
                 DATA_AREA,
                 DATA_TAG -> module = ModuleEnum.DATA;

            default -> throw new DataBusinessException("data.attachment.business.upload.wrongEntityModule",
                    "上传附件没找到所属的模块");

        }
        return strategy.buildAttachmentPath(module, entity, entityId, version);
    }
}