package com.machine.starter.web.operateLog.loader.scm;

import com.machine.client.scm.attribute.IScmAttributeClient;
import com.machine.client.scm.attribute.IScmAttributeGroupClient;
import com.machine.client.scm.attribute.IScmAttributeGroupAttributeRelationClient;
import com.machine.client.scm.attribute.dto.ScmAttributeGroupBackCategoryItemDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupAttributeRelationDetailOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupDetailOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeListOutputDto;
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

@Slf4j
@Configuration
public class ScmAttributeGroupOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IScmAttributeGroupClient> scmAttributeGroupClientProvider;

    private final ObjectProvider<IScmAttributeGroupAttributeRelationClient> scmAttributeGroupAttributeRelationClientProvider;

    private final ObjectProvider<IScmAttributeClient> scmAttributeClientProvider;

    private final ObjectProvider<IScmBackCategoryClient> scmBackCategoryClientProvider;

    public ScmAttributeGroupOperateLogLoad(OperationLogLoaderRegistry registry,
                                          ObjectProvider<IScmAttributeGroupClient> scmAttributeGroupClientProvider,
                                          ObjectProvider<IScmAttributeGroupAttributeRelationClient> scmAttributeGroupAttributeRelationClientProvider,
                                          ObjectProvider<IScmAttributeClient> scmAttributeClientProvider,
                                          ObjectProvider<IScmBackCategoryClient> scmBackCategoryClientProvider) {
        super(registry);
        this.scmAttributeGroupClientProvider = scmAttributeGroupClientProvider;
        this.scmAttributeGroupAttributeRelationClientProvider = scmAttributeGroupAttributeRelationClientProvider;
        this.scmAttributeClientProvider = scmAttributeClientProvider;
        this.scmBackCategoryClientProvider = scmBackCategoryClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IScmAttributeGroupClient scmAttributeGroupClient = scmAttributeGroupClientProvider.getIfAvailable();
        if (scmAttributeGroupClient == null) {
            log.debug("未配置 IScmAttributeGroupClient，跳过操作日志 SCM_ATTRIBUTE_GROUP 快照加载器注册");
            return;
        }

        registry.register(ModuleEntityEnum.SCM_ATTRIBUTE_GROUP, "update",
                entityId -> loadDetail(entityId, scmAttributeGroupClient::getById));
        registry.register(ModuleEntityEnum.SCM_ATTRIBUTE_GROUP, "deleteById",
                entityId -> loadDetail(entityId, scmAttributeGroupClient::getById));

        // 修改关联后台分类：快照 = 分组详情 + 关联后台分类列表（含名称，便于 diff 阅读）
        IScmBackCategoryClient scmBackCategoryClient = scmBackCategoryClientProvider.getIfAvailable();
        if (null == scmBackCategoryClient) {
            log.debug("未配置 IScmBackCategoryClient，跳过操作日志 SCM_ATTRIBUTE_GROUP updateBackCategory 快照加载器注册");
        } else {
            registry.register(ModuleEntityEnum.SCM_ATTRIBUTE_GROUP, "updateBackCategory",
                    entityId -> load4UpdateBackCategory(scmAttributeGroupClient, scmBackCategoryClient, entityId));
        }

        // 修改关联属性：快照 = 分组详情 + 关联属性列表（含属性名称/编码，便于 diff 阅读）
        IScmAttributeGroupAttributeRelationClient scmAttributeGroupAttributeRelationClient =
                scmAttributeGroupAttributeRelationClientProvider.getIfAvailable();
        IScmAttributeClient scmAttributeClient = scmAttributeClientProvider.getIfAvailable();
        if (null == scmAttributeGroupAttributeRelationClient || null == scmAttributeClient) {
            log.debug("未配置属性关联 client，跳过操作日志 SCM_ATTRIBUTE_GROUP updateAttribute 快照加载器注册");
        } else {
            registry.register(ModuleEntityEnum.SCM_ATTRIBUTE_GROUP, "updateAttribute",
                    entityId -> load4UpdateAttribute(scmAttributeGroupClient,
                            scmAttributeGroupAttributeRelationClient, scmAttributeClient, entityId));
        }
    }

    /**
     * 修改关联后台分类：快照 = 分组详情 + 关联后台分类列表（含分类名称）。
     */
    private AttributeGroupBackCategorySnapshot load4UpdateBackCategory(IScmAttributeGroupClient attributeGroupClient,
                                                                      IScmBackCategoryClient backCategoryClient,
                                                                      Object entityId) {
        if (entityId == null) {
            return null;
        }

        IdRequest idRequest = new IdRequest(String.valueOf(entityId));
        AttributeGroupBackCategorySnapshot snapshot = new AttributeGroupBackCategorySnapshot();
        snapshot.setAttributeGroup(attributeGroupClient.getById(idRequest));

        List<ScmAttributeGroupBackCategoryItemDto> relationList =
                attributeGroupClient.listBackCategoryByGroupId(idRequest);
        if (relationList == null || relationList.isEmpty()) {
            snapshot.setBackCategoryList(List.of());
            return snapshot;
        }

        Map<String, ScmBackCategoryListOutputDto> backCategoryMap = loadBackCategoryMap(backCategoryClient);
        List<BackCategoryItem> itemList = new ArrayList<>(relationList.size());
        for (ScmAttributeGroupBackCategoryItemDto relation : relationList) {
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

    /**
     * 修改关联属性：快照 = 分组详情 + 关联属性列表（含属性名称/编码）。
     */
    private AttributeGroupAttributeSnapshot load4UpdateAttribute(IScmAttributeGroupClient attributeGroupClient,
                                                                 IScmAttributeGroupAttributeRelationClient relationClient,
                                                                 IScmAttributeClient attributeClient,
                                                                 Object entityId) {
        if (entityId == null) {
            return null;
        }

        IdRequest idRequest = new IdRequest(String.valueOf(entityId));
        AttributeGroupAttributeSnapshot snapshot = new AttributeGroupAttributeSnapshot();
        snapshot.setAttributeGroup(attributeGroupClient.getById(idRequest));

        List<ScmAttributeGroupAttributeRelationDetailOutputDto> relationList =
                relationClient.listByGroupId(idRequest);
        if (relationList == null || relationList.isEmpty()) {
            snapshot.setAttributeList(List.of());
            return snapshot;
        }

        Set<String> attributeIdSet = relationList.stream()
                .map(ScmAttributeGroupAttributeRelationDetailOutputDto::getAttributeId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        Map<String, ScmAttributeListOutputDto> attributeMap = attributeClient.listByIdSet(new IdSetRequest(attributeIdSet))
                .stream()
                .collect(Collectors.toMap(ScmAttributeListOutputDto::getId, Function.identity(), (a, b) -> a));

        List<AttributeItem> itemList = new ArrayList<>(relationList.size());
        for (ScmAttributeGroupAttributeRelationDetailOutputDto relation : relationList) {
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
        snapshot.setAttributeList(itemList);
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
    public static class AttributeGroupBackCategorySnapshot {

        private ScmAttributeGroupDetailOutputDto attributeGroup;

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
    public static class AttributeGroupAttributeSnapshot {

        private ScmAttributeGroupDetailOutputDto attributeGroup;

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
