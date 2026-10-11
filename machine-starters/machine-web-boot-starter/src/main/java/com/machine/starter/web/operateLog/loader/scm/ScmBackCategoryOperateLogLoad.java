package com.machine.starter.web.operateLog.loader.scm;

import com.machine.client.scm.category.IScmBackCategoryClient;
import com.machine.client.scm.attribute.IScmAttributeClient;
import com.machine.client.scm.attribute.IScmAttributeGroupClient;
import com.machine.client.scm.attribute.IScmBackCategoryAttributeRelationClient;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupItemOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeListOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmBackCategoryAttributeRelationDetailOutputDto;
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

@Slf4j
@Configuration
public class ScmBackCategoryOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IScmBackCategoryClient> scmBackCategoryClientProvider;

    private final ObjectProvider<IScmAttributeGroupClient> scmAttributeGroupClientProvider;

    private final ObjectProvider<IScmBackCategoryAttributeRelationClient> scmBackCategoryAttributeRelationClientProvider;

    private final ObjectProvider<IScmAttributeClient> scmAttributeClientProvider;

    public ScmBackCategoryOperateLogLoad(OperationLogLoaderRegistry registry,
                                         ObjectProvider<IScmBackCategoryClient> scmBackCategoryClientProvider,
                                         ObjectProvider<IScmAttributeGroupClient> scmAttributeGroupClientProvider,
                                         ObjectProvider<IScmBackCategoryAttributeRelationClient> scmBackCategoryAttributeRelationClientProvider,
                                         ObjectProvider<IScmAttributeClient> scmAttributeClientProvider) {
        super(registry);
        this.scmBackCategoryClientProvider = scmBackCategoryClientProvider;
        this.scmAttributeGroupClientProvider = scmAttributeGroupClientProvider;
        this.scmBackCategoryAttributeRelationClientProvider = scmBackCategoryAttributeRelationClientProvider;
        this.scmAttributeClientProvider = scmAttributeClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IScmBackCategoryClient scmBackCategoryClient = scmBackCategoryClientProvider.getIfAvailable();
        if (scmBackCategoryClient == null) {
            log.debug("未配置 IScmBackCategoryClient，跳过操作日志 SCM_BACK_CATEGORY 快照加载器注册");
            return;
        }

        registry.register(ModuleEntityEnum.SCM_BACK_CATEGORY, "update",
                entityId -> loadDetail(entityId, scmBackCategoryClient::getById));
        // 删除后台分类：删除前加载分类详情快照
        registry.register(ModuleEntityEnum.SCM_BACK_CATEGORY, "deleteById",
                entityId -> loadDetail(entityId, scmBackCategoryClient::getById));

        // 修改关联属性分组：快照为该分类关联的属性分组列表（含分组名称）
        IScmAttributeGroupClient scmAttributeGroupClient = scmAttributeGroupClientProvider.getIfAvailable();
        if (null == scmAttributeGroupClient) {
            log.debug("未配置 IScmAttributeGroupClient，跳过操作日志 SCM_BACK_CATEGORY updateAttributeGroup 快照加载器注册");
        } else {
            registry.register(ModuleEntityEnum.SCM_BACK_CATEGORY, "updateAttributeGroup",
                    entityId -> load4UpdateAttributeGroup(scmAttributeGroupClient, entityId));
        }

        // 修改关联属性：快照为该分类关联的属性列表（含属性名称/编码，便于 diff 阅读）
        IScmBackCategoryAttributeRelationClient scmBackCategoryAttributeRelationClient =
                scmBackCategoryAttributeRelationClientProvider.getIfAvailable();
        IScmAttributeClient scmAttributeClient = scmAttributeClientProvider.getIfAvailable();
        if (null == scmBackCategoryAttributeRelationClient || null == scmAttributeClient) {
            log.debug("未配置后台分类属性关联 client，跳过操作日志 SCM_BACK_CATEGORY updateAttribute 快照加载器注册");
        } else {
            registry.register(ModuleEntityEnum.SCM_BACK_CATEGORY, "updateAttribute",
                    entityId -> load4UpdateAttribute(scmBackCategoryAttributeRelationClient, scmAttributeClient, entityId));
        }
    }

    private AttributeGroupSnapshot load4UpdateAttributeGroup(IScmAttributeGroupClient attributeGroupClient,
                                                           Object entityId) {
        if (entityId == null) {
            return null;
        }

        String backCategoryId = String.valueOf(entityId);
        Map<String, List<ScmAttributeGroupItemOutputDto>> itemMap = attributeGroupClient
                .mapAttributeGroupByBackCategoryIdSet(new IdSetRequest(Set.of(backCategoryId)));

        AttributeGroupSnapshot snapshot = new AttributeGroupSnapshot();
        snapshot.setAttributeGroupList(null == itemMap ? List.of() : itemMap.getOrDefault(backCategoryId, List.of()));
        return snapshot;
    }

    /**
     * 修改关联属性：快照 = 关联属性列表（含属性名称/编码）。
     */
    private AttributeSnapshot load4UpdateAttribute(IScmBackCategoryAttributeRelationClient relationClient,
                                                  IScmAttributeClient attributeClient,
                                                  Object entityId) {
        if (entityId == null) {
            return null;
        }

        String backCategoryId = String.valueOf(entityId);
        List<ScmBackCategoryAttributeRelationDetailOutputDto> relationList =
                relationClient.listByBackCategoryId(new IdRequest(backCategoryId));
        if (relationList == null || relationList.isEmpty()) {
            AttributeSnapshot empty = new AttributeSnapshot();
            empty.setAttributeList(List.of());
            return empty;
        }

        Set<String> attributeIdSet = relationList.stream()
                .map(ScmBackCategoryAttributeRelationDetailOutputDto::getAttributeId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Map<String, ScmAttributeListOutputDto> attributeMap = attributeClient.listByIdSet(new IdSetRequest(attributeIdSet))
                .stream()
                .collect(Collectors.toMap(ScmAttributeListOutputDto::getId, Function.identity(), (a, b) -> a));

        List<AttributeItem> itemList = new ArrayList<>(relationList.size());
        for (ScmBackCategoryAttributeRelationDetailOutputDto relation : relationList) {
            ScmAttributeListOutputDto attribute = attributeMap.get(relation.getAttributeId());
            AttributeItem item = new AttributeItem();
            item.setAttributeId(relation.getAttributeId());
            item.setAttributeCode(null == attribute ? null : attribute.getCode());
            item.setAttributeName(null == attribute ? null : attribute.getName());
            item.setRequiredFlag(relation.getRequiredFlag());
            item.setDisplayFlag(relation.getDisplayFlag());
            item.setSearchableFlag(relation.getSearchableFlag());
            item.setSort(relation.getSort());
            item.setFeatures(relation.getFeatures());
            itemList.add(item);
        }

        AttributeSnapshot snapshot = new AttributeSnapshot();
        snapshot.setAttributeList(itemList);
        return snapshot;
    }

    @Data
    public static class AttributeGroupSnapshot {

        private List<ScmAttributeGroupItemOutputDto> attributeGroupList;
    }

    @Data
    public static class AttributeSnapshot {

        private List<AttributeItem> attributeList;
    }

    @Data
    public static class AttributeItem {

        private String attributeId;

        private String attributeCode;

        private String attributeName;

        private Boolean requiredFlag;

        private Boolean displayFlag;

        private Boolean searchableFlag;

        private Long sort;

        private String features;
    }
}
