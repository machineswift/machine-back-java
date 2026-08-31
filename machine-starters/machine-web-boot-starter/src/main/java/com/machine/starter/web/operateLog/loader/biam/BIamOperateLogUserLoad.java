package com.machine.starter.web.operateLog.loader.biam;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.shop.IDataShopClient;
import com.machine.client.data.shop.dto.output.DataShopDetailOutputDto;
import com.machine.client.iam.biam.organization.dto.output.BIamOrganizationTreeSimpleOutputDto;
import com.machine.client.iam.biam.role.IBIamRoleClient;
import com.machine.client.iam.biam.role.dto.output.BIamRoleDetailOutputDto;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.user.IBIamUserOrganizationRelationClient;
import com.machine.client.iam.biam.user.IBIamUserRoleBusinessRelationClient;
import com.machine.client.iam.biam.user.IBIamUserRoleRelationClient;
import com.machine.client.iam.biam.user.dto.input.BIamUserUpdateInputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserOrganizationRelationOutputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserRoleBusinessRelationListOutputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserRoleRelationListOutputDto;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.biam.organization.BIamOrganizationTypeEnum;
import com.machine.sdk.base.envm.biam.role.BIamUserRoleBusinessTypeEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.starter.redis.cache.biam.RedisBIamOrganizationCache;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 操作日志 BIAM_USER 业务快照加载器注册
 */
@Slf4j
@Configuration
public class BIamOperateLogUserLoad {

    private final RedisBIamOrganizationCache bIamOrganizationCache;
    private final OperationLogLoaderRegistry registry;
    private final ObjectProvider<IBIamUserClient> userClientProvider;
    private final ObjectProvider<IBIamRoleClient> roleClientProvider;
    private final ObjectProvider<IDataShopClient> shopClientProvider;
    private final ObjectProvider<IBIamUserRoleRelationClient> userRoleRelationClientProvider;
    private final ObjectProvider<IBIamUserOrganizationRelationClient> userOrganizationRelationClientProvider;
    private final ObjectProvider<IBIamUserRoleBusinessRelationClient> userRoleBusinessRelationClientProvider;

    public BIamOperateLogUserLoad(RedisBIamOrganizationCache bIamOrganizationCache,
                                  OperationLogLoaderRegistry registry,
                                  ObjectProvider<IBIamUserClient> userClientProvider,
                                  ObjectProvider<IBIamRoleClient> roleClientProvider,
                                  ObjectProvider<IDataShopClient> shopClientProvider,
                                  ObjectProvider<IBIamUserRoleRelationClient> userRoleRelationClientProvider,
                                  ObjectProvider<IBIamUserOrganizationRelationClient> userOrganizationRelationClientProvider,
                                  ObjectProvider<IBIamUserRoleBusinessRelationClient> userRoleBusinessRelationClientProvider) {

        this.bIamOrganizationCache = bIamOrganizationCache;
        this.registry = registry;
        this.userClientProvider = userClientProvider;
        this.roleClientProvider = roleClientProvider;
        this.shopClientProvider = shopClientProvider;
        this.userRoleRelationClientProvider = userRoleRelationClientProvider;
        this.userOrganizationRelationClientProvider = userOrganizationRelationClientProvider;
        this.userRoleBusinessRelationClientProvider = userRoleBusinessRelationClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IBIamUserClient userClient = userClientProvider.getIfAvailable();
        IBIamRoleClient roleClient = roleClientProvider.getIfAvailable();
        IDataShopClient shopClient = shopClientProvider.getIfAvailable();
        IBIamUserRoleRelationClient userRoleRelationClient = userRoleRelationClientProvider.getIfAvailable();
        IBIamUserOrganizationRelationClient userOrganizationRelationClient = userOrganizationRelationClientProvider.getIfAvailable();
        IBIamUserRoleBusinessRelationClient userRoleBusinessRelationClient = userRoleBusinessRelationClientProvider.getIfAvailable();

        if (userClient == null && userRoleRelationClient == null) {
            log.debug("未配置 IBIamUserClient/IBIamUserRoleRelationClient，跳过操作日志 BIAM_USER 快照加载器注册");
            return;
        }

        // 修改用户：返回用户详情对象，对比工具按字段对比
        registry.register(ModuleEntityEnum.BIAM_USER, "update",
                entityId -> loadUser4Update(userClient, entityId));

        // 修改用户权限：返回 组织 + 角色 + 门店 快照对象，用于授权变更对比
        registry.register(ModuleEntityEnum.BIAM_USER, "updatePermission",
                entityId -> loadUser4UpdatePermission(userRoleRelationClient, roleClient,
                        userOrganizationRelationClient, userRoleBusinessRelationClient, shopClient, entityId));
    }


    private BIamUserUpdateInputDto loadUser4Update(IBIamUserClient userClient,
                                                   Object entityId) {
        if (entityId == null || userClient == null) {
            return null;
        }
        BIamUserDetailOutputDto outputDto = userClient.detail(new IdRequest(String.valueOf(entityId)));
        return JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), BIamUserUpdateInputDto.class);
    }

    private UserPermissionSnapshot loadUser4UpdatePermission(IBIamUserRoleRelationClient userRoleRelationClient,
                                                             IBIamRoleClient roleClient,
                                                             IBIamUserOrganizationRelationClient userOrganizationRelationClient,
                                                             IBIamUserRoleBusinessRelationClient userRoleBusinessRelationClient,
                                                             IDataShopClient shopClient,
                                                             Object entityId) {
        if (entityId == null) {
            return null;
        }
        String userId = String.valueOf(entityId);

        UserPermissionSnapshot snapshot = new UserPermissionSnapshot();

        // 组织关系：organizationIdMap = 组织类型 -> (组织ID -> 组织名称)
        if (userOrganizationRelationClient != null) {
            snapshot.setOrganizationIdMap(loadOrganizationIdMap(userOrganizationRelationClient, userId));
        }

        // 角色关系：userRoleInfoMap = 角色ID -> 角色快照（角色 + 角色名 + 排序 + 门店）
        // 用 Map（key = roleId）而非 List，避免 diff 按列表下标对比产生“角色原地改名”的噪音
        if (userRoleRelationClient != null && roleClient != null) {
            snapshot.setUserRoleInfoMap(loadUserRoleInfoMap(userRoleRelationClient, roleClient,
                    userRoleBusinessRelationClient, shopClient, userId));
        }

        return snapshot;
    }

    /**
     * 用户组织关系：组织类型 -> (组织ID -> 组织名称)
     */
    private Map<BIamOrganizationTypeEnum, Map<String, String>> loadOrganizationIdMap(
            IBIamUserOrganizationRelationClient userOrganizationRelationClient,
            String userId) {
        List<BIamUserOrganizationRelationOutputDto> relationList =
                userOrganizationRelationClient.listByUserId(new IdRequest(userId));
        if (CollectionUtil.isEmpty(relationList)) {
            return null;
        }

        // 按组织类型分组，得到 组织类型 -> 组织ID集合
        Map<BIamOrganizationTypeEnum, Set<String>> typeOrgIdMap = new HashMap<>();
        for (BIamUserOrganizationRelationOutputDto relation : relationList) {
            if (relation.getOrganizationId() == null) {
                continue;
            }
            typeOrgIdMap.computeIfAbsent(relation.getOrganizationType(), _ -> new HashSet<>())
                    .add(relation.getOrganizationId());
        }

        Map<BIamOrganizationTypeEnum, Map<String, String>> result = new HashMap<>();
        for (Map.Entry<BIamOrganizationTypeEnum, Set<String>> entry : typeOrgIdMap.entrySet()) {
            Map<String, String> orgIdNameMap = new HashMap<>();
            // 从 Redis 组织树缓存取全量节点，取 ID -> 名称（组织已删除时以 ID 兜底展示，保证 diff 稳定）
            List<BIamOrganizationTreeSimpleOutputDto> orgNodeList =
                    TreeUtil.collectAllNodes(bIamOrganizationCache.treeAllSimple(entry.getKey()));
            if (CollectionUtil.isNotEmpty(orgNodeList)) {
                for (BIamOrganizationTreeSimpleOutputDto org : orgNodeList) {
                    if (entry.getValue().contains(org.getId())) {
                        orgIdNameMap.put(org.getId(), org.getName() != null ? org.getName() : org.getId());
                    }
                }
            }
            result.put(entry.getKey(), orgIdNameMap);
        }
        return result;
    }

    /**
     * 用户角色关系：角色ID -> 角色名 + 排序 + 门店(门店ID -> 门店名称)
     */
    private LinkedHashMap<String, BIamUserRoleInfoSnapshot> loadUserRoleInfoMap(
            IBIamUserRoleRelationClient userRoleRelationClient,
            IBIamRoleClient roleClient,
            IBIamUserRoleBusinessRelationClient userRoleBusinessRelationClient,
            IDataShopClient shopClient,
            String userId) {
        List<BIamUserRoleRelationListOutputDto> relationList =
                userRoleRelationClient.listByUserId(new IdRequest(userId));
        if (CollectionUtil.isEmpty(relationList)) {
            return new LinkedHashMap<>();
        }

        // 角色信息：角色ID -> 角色
        Set<String> roleIdSet = relationList.stream()
                .map(BIamUserRoleRelationListOutputDto::getRoleId)
                .collect(Collectors.toSet());
        Map<String, BIamRoleDetailOutputDto> roleIdInfoMap = roleClient.mapByIdSet(new IdSetRequest(roleIdSet));

        // 角色业务关系：角色关联的门店业务数据
        List<BIamUserRoleBusinessRelationListOutputDto> businessRelationList = null;
        if (userRoleBusinessRelationClient != null) {
            Set<String> userRoleRelationIdSet = relationList.stream()
                    .map(BIamUserRoleRelationListOutputDto::getId)
                    .collect(Collectors.toSet());
            if (CollectionUtil.isNotEmpty(userRoleRelationIdSet)) {
                businessRelationList = userRoleBusinessRelationClient
                        .listByUserRoleRelationIdSet(new IdSetRequest(userRoleRelationIdSet));
            }
        }

        // 门店信息：门店ID -> 门店
        Map<String, DataShopDetailOutputDto> shopIdInfoMap = loadShopIdInfoMap(businessRelationList, shopClient);

        LinkedHashMap<String, BIamUserRoleInfoSnapshot> userRoleInfoMap = new LinkedHashMap<>();
        for (BIamUserRoleRelationListOutputDto relation : relationList) {
            BIamUserRoleInfoSnapshot roleInfoSnapshot = new BIamUserRoleInfoSnapshot();
            roleInfoSnapshot.setRoleId(relation.getRoleId());
            roleInfoSnapshot.setSort(relation.getSort());
            BIamRoleDetailOutputDto role = roleIdInfoMap.get(relation.getRoleId());
            if (role != null) {
                roleInfoSnapshot.setRoleName(role.getName());
            }
            roleInfoSnapshot.setShopIdNameMap(
                    loadShopIdNameMap(relation.getId(), businessRelationList, shopIdInfoMap));
            userRoleInfoMap.put(relation.getRoleId(), roleInfoSnapshot);
        }
        return userRoleInfoMap;
    }

    /**
     * 门店ID -> 门店信息
     */
    private Map<String, DataShopDetailOutputDto> loadShopIdInfoMap(
            List<BIamUserRoleBusinessRelationListOutputDto> businessRelationList,
            IDataShopClient shopClient) {
        if (CollectionUtil.isEmpty(businessRelationList) || shopClient == null) {
            return Map.of();
        }
        Set<String> shopIdSet = businessRelationList.stream()
                .filter(outputDto -> BIamUserRoleBusinessTypeEnum.SHOP == outputDto.getBusinessType())
                .map(BIamUserRoleBusinessRelationListOutputDto::getBusinessId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (CollectionUtil.isEmpty(shopIdSet)) {
            return Map.of();
        }
        return shopClient.mapByIdSet(new IdSetRequest(shopIdSet));
    }

    /**
     * 某角色关系下的 门店ID -> 门店名称（保持 sort 顺序）
     */
    private LinkedHashMap<String, String> loadShopIdNameMap(
            String userRoleRelationId,
            List<BIamUserRoleBusinessRelationListOutputDto> businessRelationList,
            Map<String, DataShopDetailOutputDto> shopIdInfoMap) {
        LinkedHashMap<String, String> shopIdNameMap = new LinkedHashMap<>();
        if (CollectionUtil.isEmpty(businessRelationList)) {
            return shopIdNameMap;
        }
        businessRelationList.stream()
                .filter(outputDto -> Objects.equals(outputDto.getUserRoleRelationId(), userRoleRelationId))
                .filter(outputDto -> BIamUserRoleBusinessTypeEnum.SHOP == outputDto.getBusinessType())
                .sorted(Comparator.comparing(BIamUserRoleBusinessRelationListOutputDto::getSort,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .forEach(outputDto -> {
                    DataShopDetailOutputDto shop = shopIdInfoMap.get(outputDto.getBusinessId());
                    shopIdNameMap.put(outputDto.getBusinessId(),
                            shop != null && shop.getName() != null ? shop.getName() : outputDto.getBusinessId());
                });
        return shopIdNameMap;
    }


    @Data
    private static class UserPermissionSnapshot {

        @Schema(description = "组织ID集合")
        private Map<BIamOrganizationTypeEnum, Map<String, String>> organizationIdMap;

        @Schema(description = "用户角色集合")
        private LinkedHashMap<String, BIamUserRoleInfoSnapshot> userRoleInfoMap;

    }


    @Data
    @Schema
    @NoArgsConstructor
    private static class BIamUserRoleInfoSnapshot {

        @Schema(description = "角色ID")
        private String roleId;

        @Schema(description = "角色名称")
        private String roleName;

        @Schema(description = "排序")
        private Long sort;

        @Schema(description = "门店ID集合")
        private LinkedHashMap<String, String> shopIdNameMap;
    }

}
