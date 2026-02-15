package com.machine.starter.obs.path;

import com.machine.sdk.common.envm.base.ModuleEntityEnum;
import com.machine.sdk.common.envm.base.ModuleEnum;
import com.machine.sdk.common.envm.data.file.DataFileTypeEnum;

/**
 * OBS路径生成策略接口
 */
public interface IObsPathStrategy {

    /**
     * 生成下载中心路径
     *
     * @param module  模块 (IAM/DATA/HRM/CRM)
     * @param entity  实体 (users/role/permission)
     * @param ownerId 所有者ID (zhang3/SH-FLAGSHIP)
     * @return 完整的下载中心存储路径
     */
    String buildDownloadPath(ModuleEnum module,
                             ModuleEntityEnum entity,
                             String ownerId);

    /**
     * 生成素材路径
     *
     * @param type 素材类型
     * @return 完整的素材存储路径
     */
    String buildMaterialPath(DataFileTypeEnum type);

    /**
     * 生成附件路径
     *
     * @param module  模块 (IAM/DATA/HRM/CRM)
     * @param entity  实体 (users/role/permission)
     * @param entityId 实体ID (zhang3/SH-FLAGSHIP)
     * @param version 版本号 (v2.1)
     * @return 完整的附件存储路径
     */
    String buildAttachmentPath(ModuleEnum module,
                               ModuleEntityEnum entity,
                               String entityId,
                               String version);
}