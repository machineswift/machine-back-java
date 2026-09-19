package com.machine.starter.web.operateLog.loader.biam;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.permission.IBIamPermissionClient;
import com.machine.client.iam.biam.permission.dto.output.BIamPermissionTreeOutputDto;
import com.machine.client.iam.biam.role.IBIamRoleClient;
import com.machine.client.iam.biam.role.IBIamRolePermissionClient;
import com.machine.client.iam.biam.role.dto.input.BIamRoleUpdateInputDto;
import com.machine.client.iam.biam.role.dto.output.BIamRolePermissionListOutputDto;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.model.dto.biam.auth.BIamDataPermissionRuleDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.sdk.base.tool.Tuples;
import com.machine.starter.web.operateLog.loader.AbstractModuleOperateLogLoad;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作日志 BIAM_ROLE 业务快照加载器注册。
 */
@Slf4j
@Configuration
public class BIamRoleOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IBIamRoleClient> roleClientProvider;
    private final ObjectProvider<IBIamRolePermissionClient> rolePermissionClientProvider;
    private final ObjectProvider<IBIamPermissionClient> permissionClientProvider;

    public BIamRoleOperateLogLoad(OperationLogLoaderRegistry registry,
                                  ObjectProvider<IBIamRoleClient> roleClientProvider,
                                  ObjectProvider<IBIamRolePermissionClient> rolePermissionClientProvider,
                                  ObjectProvider<IBIamPermissionClient> permissionClientProvider) {
        super(registry);
        this.roleClientProvider = roleClientProvider;
        this.rolePermissionClientProvider = rolePermissionClientProvider;
        this.permissionClientProvider = permissionClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IBIamRoleClient roleClient = roleClientProvider.getIfAvailable();
        IBIamRolePermissionClient rolePermissionClient = rolePermissionClientProvider.getIfAvailable();
        IBIamPermissionClient permissionClient = permissionClientProvider.getIfAvailable();

        if (roleClient == null && rolePermissionClient == null) {
            log.debug("未配置 IBIamRoleClient/IBIamRolePermissionClient，跳过操作日志 BIAM_ROLE 快照加载器注册");
            return;
        }

        // 修改角色：返回角色详情对象，对比工具按字段对比
        if (roleClient != null) {
            registry.register(ModuleEntityEnum.BIAM_ROLE, "update",
                    entityId -> load4Update(roleClient, entityId));
            // 删除角色：删除前加载角色详情快照，供 diff 记录被删对象
            registry.register(ModuleEntityEnum.BIAM_ROLE, "delete",
                    entityId -> load4Update(roleClient, entityId));
        }

        // 修改角色权限：返回 权限ID -> 权限名称 + 数据权限规则 快照对象，用于授权关系变更对比
        if (rolePermissionClient != null) {
            registry.register(ModuleEntityEnum.BIAM_ROLE, "updatePermission",
                    entityId -> load4UpdatePermission(rolePermissionClient, permissionClient, entityId));
        }
    }

    private BIamRoleUpdateInputDto load4Update(IBIamRoleClient roleClient,
                                               Object entityId) {
        Object detail = loadDetail(entityId, roleClient::detail);
        return detail == null ? null
                : JSONUtil.toBean(JSONUtil.toJsonStr(detail), BIamRoleUpdateInputDto.class);
    }

    /**
     * 修改角色权限：快照为 权限ID -> 权限名称（Map 而非 List，避免 diff 按下标对比产生噪音），
     * 并附带各权限的数据权限规则。
     */
    private RolePermissionSnapshot load4UpdatePermission(IBIamRolePermissionClient rolePermissionClient,
                                                         IBIamPermissionClient permissionClient,
                                                         Object entityId) {
        if (entityId == null) {
            return null;
        }
        String roleId = String.valueOf(entityId);
        List<BIamRolePermissionListOutputDto> relationList =
                rolePermissionClient.listByRoleId(new IdRequest(roleId));
        if (CollectionUtil.isEmpty(relationList)) {
            return null;
        }

        // 权限ID -> 权限名称（从权限树全量节点取，已删除时以 ID 兜底展示，保证 diff 稳定）
        Map<String, String> permissionIdNameMap = loadPermissionIdNameMap(permissionClient);

        RolePermissionSnapshot snapshot = new RolePermissionSnapshot();
        LinkedHashMap<String, String> permissionIdNameLinked = new LinkedHashMap<>();
        LinkedHashMap<String, List<BIamDataPermissionRuleDto>> dataPermissionRuleMap = new LinkedHashMap<>();
        for (BIamRolePermissionListOutputDto relation : relationList) {
            String permissionId = relation.getPermissionId();
            if (permissionId == null) {
                continue;
            }
            permissionIdNameLinked.put(permissionId,
                    permissionIdNameMap.getOrDefault(permissionId, permissionId));
            if (CollectionUtil.isNotEmpty(relation.getDataPermissionRuleList())) {
                dataPermissionRuleMap.put(permissionId, relation.getDataPermissionRuleList());
            }
        }
        snapshot.setPermissionIdNameMap(permissionIdNameLinked);
        snapshot.setDataPermissionRuleMap(dataPermissionRuleMap);
        return snapshot;
    }

    /**
     * 权限ID -> 权限名称（全量权限树）
     */
    private Map<String, String> loadPermissionIdNameMap(IBIamPermissionClient permissionClient) {
        if (permissionClient == null) {
            return Map.of();
        }
        Tuples.Tuple2<String, BIamPermissionTreeOutputDto> treeTuple = permissionClient.treeAll();
        if (treeTuple == null || treeTuple._2() == null) {
            return Map.of();
        }
        List<BIamPermissionTreeOutputDto> nodeList = TreeUtil.collectAllNodes(treeTuple._2());
        Map<String, String> permissionIdNameMap = new HashMap<>(nodeList.size());
        for (BIamPermissionTreeOutputDto node : nodeList) {
            if (node.getId() != null) {
                permissionIdNameMap.put(node.getId(),
                        node.getName() != null ? node.getName() : node.getId());
            }
        }
        return permissionIdNameMap;
    }

    @Data
    private static class RolePermissionSnapshot {

        @Schema(description = "权限ID -> 权限名称")
        private LinkedHashMap<String, String> permissionIdNameMap;

        @Schema(description = "权限ID -> 数据权限规则")
        private Map<String, List<BIamDataPermissionRuleDto>> dataPermissionRuleMap;

    }

}
