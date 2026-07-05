package com.machine.starter.obs.path;

import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.data.filecenter.DataFileTypeEnum;

/**
 * OBS路径生成策略接口
 */
public interface IObsPathStrategy {


    /**
     * 生成附件路径
     *
     * @param moduleEnum  模块 (IAM/DATA/HRM/CRM)
     * @param entityEnum  实体 (users/role/permission)
     * @param entityId 实体ID (zhang3/SH-FLAGSHIP)
     * @param version 版本号 (v2.1)
     * @return 完整的附件存储路径
     */
    String buildAttachmentPath(ModuleEnum moduleEnum,
                               ModuleEntityEnum entityEnum,
                               String entityId,
                               String attachmentGroup,
                               int version);
}