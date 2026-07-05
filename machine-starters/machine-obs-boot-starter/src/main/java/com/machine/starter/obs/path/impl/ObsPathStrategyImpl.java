package com.machine.starter.obs.path.impl;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.starter.obs.constant.ObsFileConstant;
import com.machine.starter.obs.path.IObsPathStrategy;

public class ObsPathStrategyImpl implements IObsPathStrategy {

    @Override
    public String buildAttachmentPath(ModuleEnum module,
                                      ModuleEntityEnum entityEnum,
                                      String attachmentGroup,
                                      String entityId,
                                      int version) {
        String safeEntityId = sanitize(entityId);

        return String.join(ObsFileConstant.SEPARATOR,
                module.getName(),
                entityEnum.getName(),
                attachmentGroup,
                safeEntityId,
                version + "/"
        );
    }

    private String sanitize(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "unknown";
        }
        // 移除路径遍历风险和特殊字符
        return input.trim()
                .replaceAll("[\\\\/:*?\"<>|]", "_")
                .replaceAll("\\.\\.", "_");
    }
}