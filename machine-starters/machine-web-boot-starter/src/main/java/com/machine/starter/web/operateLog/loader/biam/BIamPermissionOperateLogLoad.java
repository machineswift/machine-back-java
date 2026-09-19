package com.machine.starter.web.operateLog.loader.biam;

import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.permission.IBIamPermissionClient;
import com.machine.client.iam.biam.permission.dto.input.BIamPermissionUpdateInputDto;
import com.machine.client.iam.biam.permission.dto.output.BIamPermissionDetailOutputDto;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.starter.web.operateLog.diff.dto.ParentChangeSnapshot;
import com.machine.starter.web.operateLog.loader.AbstractModuleOperateLogLoad;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

/**
 * 操作日志 BIAM_PERMISSION 业务快照加载器注册。
 */
@Slf4j
@Configuration
public class BIamPermissionOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IBIamPermissionClient> permissionClientProvider;

    public BIamPermissionOperateLogLoad(OperationLogLoaderRegistry registry,
                                        ObjectProvider<IBIamPermissionClient> permissionClientProvider) {
        super(registry);
        this.permissionClientProvider = permissionClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IBIamPermissionClient permissionClient = permissionClientProvider.getIfAvailable();
        if (permissionClient == null) {
            log.debug("未配置 IBIamPermissionClient，跳过操作日志 BIAM_PERMISSION 快照加载器注册");
            return;
        }

        // 修改权限：返回权限详情对象，对比工具按字段对比
        registry.register(ModuleEntityEnum.BIAM_PERMISSION, "update",
                entityId -> load4Update(permissionClient, entityId));

        // 删除权限：删除前加载权限详情快照，供 diff 记录被删对象
        registry.register(ModuleEntityEnum.BIAM_PERMISSION, "delete",
                entityId -> load4Update(permissionClient, entityId));

        // 修改权限父节点：返回 权限 + 父节点名称 快照对象（关系变更，携带 name 便于审计）
        registry.register(ModuleEntityEnum.BIAM_PERMISSION, "updateParent",
                entityId -> load4UpdateParent(permissionClient, entityId));
    }

    private BIamPermissionUpdateInputDto load4Update(IBIamPermissionClient permissionClient,
                                                     Object entityId) {
        Object detail = loadDetail(entityId, permissionClient::detail);
        return detail == null ? null
                : JSONUtil.toBean(JSONUtil.toJsonStr(detail), BIamPermissionUpdateInputDto.class);
    }

    private ParentChangeSnapshot load4UpdateParent(IBIamPermissionClient permissionClient,
                                                   Object entityId) {
        BIamPermissionDetailOutputDto detail = (BIamPermissionDetailOutputDto)
                loadDetail(entityId, permissionClient::detail);
        if (detail == null) {
            return null;
        }
        ParentChangeSnapshot snapshot = new ParentChangeSnapshot();
        snapshot.setId(detail.getId());
        snapshot.setName(detail.getName());
        snapshot.setParentId(detail.getParentId());
        if (detail.getParentId() != null) {
            BIamPermissionDetailOutputDto parent =
                    permissionClient.detail(new IdRequest(detail.getParentId()));
            if (parent != null && parent.getName() != null) {
                snapshot.setParentName(parent.getName());
            }
        }
        return snapshot;
    }
}
