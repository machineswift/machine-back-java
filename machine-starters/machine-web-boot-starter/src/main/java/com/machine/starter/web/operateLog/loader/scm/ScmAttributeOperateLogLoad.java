package com.machine.starter.web.operateLog.loader.scm;

import com.machine.client.scm.attribute.IScmAttributeClient;
import com.machine.client.scm.attribute.IScmAttributeGroupAttributeRelationClient;
import com.machine.client.scm.attribute.IScmAttributeGroupClient;
import com.machine.client.scm.attribute.IScmBackCategoryAttributeRelationClient;
import com.machine.client.scm.attribute.dto.output.ScmAttributeDetailOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupAttributeRelationDetailOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupSimpleListOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmBackCategoryAttributeRelationDetailOutputDto;
import com.machine.client.scm.category.IScmBackCategoryClient;
import com.machine.client.scm.category.dto.output.ScmBackCategoryListOutputDto;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.starter.web.operateLog.loader.AbstractModuleOperateLogLoad;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 操作日志 SCM 模块-属性 业务快照加载器注册。
 */
@Slf4j
@Configuration
public class ScmAttributeOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IScmAttributeClient> scmAttributeClientProvider;

    private final ObjectProvider<IScmAttributeGroupAttributeRelationClient> scmAttributeGroupAttributeRelationClientProvider;

    private final ObjectProvider<IScmAttributeGroupClient> scmAttributeGroupClientProvider;

    private final ObjectProvider<IScmBackCategoryAttributeRelationClient> scmBackCategoryAttributeRelationClientProvider;

    private final ObjectProvider<IScmBackCategoryClient> scmBackCategoryClientProvider;

    public ScmAttributeOperateLogLoad(OperationLogLoaderRegistry registry,
                                     ObjectProvider<IScmAttributeClient> scmAttributeClientProvider,
                                     ObjectProvider<IScmAttributeGroupAttributeRelationClient> scmAttributeGroupAttributeRelationClientProvider,
                                     ObjectProvider<IScmAttributeGroupClient> scmAttributeGroupClientProvider,
                                     ObjectProvider<IScmBackCategoryAttributeRelationClient> scmBackCategoryAttributeRelationClientProvider,
                                     ObjectProvider<IScmBackCategoryClient> scmBackCategoryClientProvider) {
        super(registry);
        this.scmAttributeClientProvider = scmAttributeClientProvider;
        this.scmAttributeGroupAttributeRelationClientProvider = scmAttributeGroupAttributeRelationClientProvider;
        this.scmAttributeGroupClientProvider = scmAttributeGroupClientProvider;
        this.scmBackCategoryAttributeRelationClientProvider = scmBackCategoryAttributeRelationClientProvider;
        this.scmBackCategoryClientProvider = scmBackCategoryClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IScmAttributeClient scmAttributeClient = scmAttributeClientProvider.getIfAvailable();
        if (scmAttributeClient != null) {
            registry.register(ModuleEntityEnum.SCM_ATTRIBUTE, "update",
                    entityId -> loadDetail(entityId, scmAttributeClient::getById));
            // 删除属性：删除前加载属性详情快照，供 diff 记录被删对象
            registry.register(ModuleEntityEnum.SCM_ATTRIBUTE, "deleteById",
                    entityId -> loadDetail(entityId, scmAttributeClient::getById));

            // 修改关联属性分组：属性详情本身不含关联，必须额外加载关联分组（含名称），否则 diff 恒为空
            IScmAttributeGroupAttributeRelationClient attributeGroupAttributeRelationClient =
                    scmAttributeGroupAttributeRelationClientProvider.getIfAvailable();
            IScmAttributeGroupClient attributeGroupClient = scmAttributeGroupClientProvider.getIfAvailable();
            if (null == attributeGroupAttributeRelationClient || null == attributeGroupClient) {
                log.debug("未配置属性分组关联 client，跳过操作日志 SCM_ATTRIBUTE updateAttributeGroup 快照加载器注册");
            } else {
                registry.register(ModuleEntityEnum.SCM_ATTRIBUTE, "updateAttributeGroup",
                        entityId -> load4UpdateAttributeGroup(scmAttributeClient,
                                attributeGroupAttributeRelationClient, attributeGroupClient, entityId));
            }

            // 修改关联后台分类：属性详情本身不含关联，必须额外加载关联后台分类（含名称），否则 diff 恒为空
            IScmBackCategoryAttributeRelationClient backCategoryAttributeRelationClient =
                    scmBackCategoryAttributeRelationClientProvider.getIfAvailable();
            IScmBackCategoryClient backCategoryClient = scmBackCategoryClientProvider.getIfAvailable();
            if (null == backCategoryAttributeRelationClient || null == backCategoryClient) {
                log.debug("未配置后台分类关联 client，跳过操作日志 SCM_ATTRIBUTE updateBackCategory 快照加载器注册");
            } else {
                registry.register(ModuleEntityEnum.SCM_ATTRIBUTE, "updateBackCategory",
                        entityId -> load4UpdateBackCategory(scmAttributeClient,
                                backCategoryAttributeRelationClient, backCategoryClient, entityId));
            }
        } else {
            log.debug("未配置 IScmAttributeClient，跳过操作日志 SCM_ATTRIBUTE 快照加载器注册");
        }
    }

    /**
     * 修改关联属性分组：快照 = 属性详情 + 关联属性分组列表（含分组名称/编码，便于 diff 阅读）。
     */
    private AttributeGroupSnapshot load4UpdateAttributeGroup(
            IScmAttributeClient attributeClient,
            IScmAttributeGroupAttributeRelationClient relationClient,
            IScmAttributeGroupClient attributeGroupClient,
            Object entityId) {
        if (entityId == null) {
            return null;
        }

        String attributeId = String.valueOf(entityId);
        AttributeGroupSnapshot snapshot = new AttributeGroupSnapshot();
        snapshot.setAttribute(attributeClient.getById(new IdRequest(attributeId)));

        List<ScmAttributeGroupAttributeRelationDetailOutputDto> relationList =
                relationClient.listByAttributeId(new IdRequest(attributeId));
        if (relationList == null || relationList.isEmpty()) {
            snapshot.setAttributeGroupList(List.of());
            return snapshot;
        }

        Set<String> groupIdSet = relationList.stream()
                .map(ScmAttributeGroupAttributeRelationDetailOutputDto::getGroupId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Map<String, ScmAttributeGroupSimpleListOutputDto> groupMap = attributeGroupClient
                .listByIdSet(new IdSetRequest(groupIdSet)).stream()
                .collect(Collectors.toMap(ScmAttributeGroupSimpleListOutputDto::getId, Function.identity(), (a, b) -> a));

        List<AttributeGroupItem> itemList = new ArrayList<>(relationList.size());
        for (ScmAttributeGroupAttributeRelationDetailOutputDto relation : relationList) {
            ScmAttributeGroupSimpleListOutputDto group = groupMap.get(relation.getGroupId());
            AttributeGroupItem item = new AttributeGroupItem();
            item.setGroupId(relation.getGroupId());
            item.setGroupCode(null == group ? null : group.getCode());
            item.setGroupName(null == group ? null : group.getName());
            item.setRequiredFlag(relation.getRequiredFlag());
            item.setDisplayFlag(relation.getDisplayFlag());
            item.setSearchableFlag(relation.getSearchableFlag());
            item.setSort(relation.getSort());
            item.setFeatures(relation.getFeatures());
            itemList.add(item);
        }
        snapshot.setAttributeGroupList(itemList);
        return snapshot;
    }

    /**
     * 修改关联后台分类：快照 = 属性详情 + 关联后台分类列表（含分类名称/编码，便于 diff 阅读）。
     */
    private BackCategorySnapshot load4UpdateBackCategory(
            IScmAttributeClient attributeClient,
            IScmBackCategoryAttributeRelationClient relationClient,
            IScmBackCategoryClient backCategoryClient,
            Object entityId) {
        if (entityId == null) {
            return null;
        }

        String attributeId = String.valueOf(entityId);
        BackCategorySnapshot snapshot = new BackCategorySnapshot();
        snapshot.setAttribute(attributeClient.getById(new IdRequest(attributeId)));

        List<ScmBackCategoryAttributeRelationDetailOutputDto> relationList =
                relationClient.listByAttributeId(new IdRequest(attributeId));
        if (relationList == null || relationList.isEmpty()) {
            snapshot.setBackCategoryList(List.of());
            return snapshot;
        }

        Map<String, ScmBackCategoryListOutputDto> backCategoryMap = loadBackCategoryMap(backCategoryClient);
        List<BackCategoryItem> itemList = new ArrayList<>(relationList.size());
        for (ScmBackCategoryAttributeRelationDetailOutputDto relation : relationList) {
            ScmBackCategoryListOutputDto backCategory = backCategoryMap.get(relation.getBackCategoryId());
            BackCategoryItem item = new BackCategoryItem();
            item.setBackCategoryId(relation.getBackCategoryId());
            item.setBackCategoryCode(null == backCategory ? null : backCategory.getCode());
            item.setBackCategoryName(null == backCategory ? null : backCategory.getName());
            item.setRequiredFlag(relation.getRequiredFlag());
            item.setDisplayFlag(relation.getDisplayFlag());
            item.setSearchableFlag(relation.getSearchableFlag());
            item.setSort(relation.getSort());
            item.setFeatures(relation.getFeatures());
            itemList.add(item);
        }
        snapshot.setBackCategoryList(itemList);
        return snapshot;
    }

    private Map<String, ScmBackCategoryListOutputDto> loadBackCategoryMap(IScmBackCategoryClient backCategoryClient) {
        List<ScmBackCategoryListOutputDto> list = backCategoryClient.listAll();
        if (list == null || list.isEmpty()) {
            return Map.of();
        }
        return list.stream()
                .collect(Collectors.toMap(ScmBackCategoryListOutputDto::getId, Function.identity(), (a, b) -> a));
    }

    @Data
    public static class BackCategorySnapshot {

        private ScmAttributeDetailOutputDto attribute;

        private List<BackCategoryItem> backCategoryList;
    }

    @Data
    public static class BackCategoryItem {

        private String backCategoryId;

        private String backCategoryCode;

        private String backCategoryName;

        private Boolean requiredFlag;

        private Boolean displayFlag;

        private Boolean searchableFlag;

        private Long sort;

        private String features;
    }

    @Data
    public static class AttributeGroupSnapshot {

        private ScmAttributeDetailOutputDto attribute;

        private List<AttributeGroupItem> attributeGroupList;
    }

    @Data
    public static class AttributeGroupItem {

        private String groupId;

        private String groupCode;

        private String groupName;

        private Boolean requiredFlag;

        private Boolean displayFlag;

        private Boolean searchableFlag;

        private Long sort;

        private String features;
    }
}
