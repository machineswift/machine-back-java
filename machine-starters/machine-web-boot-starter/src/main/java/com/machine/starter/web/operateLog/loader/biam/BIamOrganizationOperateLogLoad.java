package com.machine.starter.web.operateLog.loader.biam;

import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.organization.IBIamOrganizationClient;
import com.machine.client.iam.biam.organization.dto.input.BIamOrganizationUpdateInputDto;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationDetailOutputDto;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.starter.web.operateLog.loader.AbstractModuleOperateLogLoad;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

/**
 * 操作日志 BIAM_ORGANIZATION 业务快照加载器注册。
 */
@Slf4j
@Configuration
public class BIamOrganizationOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IBIamOrganizationClient> organizationClientProvider;

    public BIamOrganizationOperateLogLoad(OperationLogLoaderRegistry registry,
                                          ObjectProvider<IBIamOrganizationClient> organizationClientProvider) {
        super(registry);
        this.organizationClientProvider = organizationClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IBIamOrganizationClient organizationClient = organizationClientProvider.getIfAvailable();
        if (organizationClient == null) {
            log.debug("未配置 IBIamOrganizationClient，跳过操作日志 BIAM_ORGANIZATION 快照加载器注册");
            return;
        }

        // 修改组织：返回组织详情对象，对比工具按字段对比
        registry.register(ModuleEntityEnum.BIAM_ORGANIZATION, "update",
                entityId -> load4Update(organizationClient, entityId));

        // 删除组织：删除前加载组织详情快照，供 diff 记录被删对象
        registry.register(ModuleEntityEnum.BIAM_ORGANIZATION, "delete",
                entityId -> load4Update(organizationClient, entityId));

        // 修改组织父节点：返回 组织 + 父节点名称 快照对象（关系变更，携带 name 便于审计）
        registry.register(ModuleEntityEnum.BIAM_ORGANIZATION, "updateParent",
                entityId -> load4UpdateParent(organizationClient, entityId));
    }

    private BIamOrganizationUpdateInputDto load4Update(IBIamOrganizationClient organizationClient,
                                                       Object entityId) {
        Object detail = loadDetail(entityId, organizationClient::detail);
        return detail == null ? null
                : JSONUtil.toBean(JSONUtil.toJsonStr(detail), BIamOrganizationUpdateInputDto.class);
    }

    private BIamParentChangeSnapshot load4UpdateParent(IBIamOrganizationClient organizationClient,
                                                       Object entityId) {
        BIamOrganizationDetailOutputDto detail = (BIamOrganizationDetailOutputDto)
                loadDetail(entityId, organizationClient::detail);
        if (detail == null) {
            return null;
        }
        BIamParentChangeSnapshot snapshot = new BIamParentChangeSnapshot();
        snapshot.setId(detail.getId());
        snapshot.setName(detail.getName());
        snapshot.setParentId(detail.getParentId());
        if (detail.getParentId() != null) {
            BIamOrganizationDetailOutputDto parent =
                    organizationClient.detail(new IdRequest(detail.getParentId()));
            if (parent != null && parent.getName() != null) {
                snapshot.setParentName(parent.getName());
            }
        }
        return snapshot;
    }

    @Data
    public static class BIamParentChangeSnapshot {

        @Schema(description = "ID")
        private String id;

        @Schema(description = "名称")
        private String name;

        @Schema(description = "父ID")
        private String parentId;

        @Schema(description = "父节点名称")
        private String parentName;

    }

}
