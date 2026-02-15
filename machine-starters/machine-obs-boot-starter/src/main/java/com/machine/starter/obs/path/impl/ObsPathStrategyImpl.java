package com.machine.starter.obs.path.impl;

import com.machine.sdk.common.envm.base.ModuleEntityEnum;
import com.machine.sdk.common.envm.base.ModuleEnum;
import com.machine.sdk.common.envm.data.file.DataFileTypeEnum;
import com.machine.starter.obs.constant.ObsPathConstant;
import com.machine.starter.obs.path.IObsPathStrategy;

import java.time.LocalDate;

public class ObsPathStrategyImpl implements IObsPathStrategy {

    @Override
    public String buildDownloadPath(ModuleEnum module,
                                    ModuleEntityEnum entity,
                                    String ownerId) {
        String safeOwnerId = sanitize(ownerId);
        String date = ObsPathConstant.getCurrentDateForFile();

        return String.join(ObsPathConstant.SEPARATOR,
                ObsPathConstant.ROOT_DOWNLOADS,
                module.getName(),
                entity.getName(),
                safeOwnerId,
                date
        );
    }

    @Override
    public String buildMaterialPath(DataFileTypeEnum type) {
        // 获取当前日期
        LocalDate today = LocalDate.now();

        // 分别获取年、月、日
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();

        return String.join(ObsPathConstant.SEPARATOR,
                ObsPathConstant.ROOT_MATERIAL,
                type.getName(),
                year + "",
                month + "",
                day + ""
        );
    }

    @Override
    public String buildAttachmentPath(ModuleEnum module,
                                      ModuleEntityEnum entity,
                                      String entityId,
                                      String version) {
        String safeEntityId = sanitize(entityId);
        String safeVersion = (version == null || version.trim().isEmpty())
                ? "v1" : version;

        return String.join(ObsPathConstant.SEPARATOR,
                ObsPathConstant.ROOT_ATTACHMENT,
                ObsPathConstant.ROOT_ATTACHMENT,
                module.getName(),
                entity.getName(),
                safeEntityId,
                safeVersion
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