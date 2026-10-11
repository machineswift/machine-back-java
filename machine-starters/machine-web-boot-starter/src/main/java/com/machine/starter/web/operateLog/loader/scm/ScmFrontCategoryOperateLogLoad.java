package com.machine.starter.web.operateLog.loader.scm;

import com.machine.client.scm.category.IScmBackCategoryClient;
import com.machine.client.scm.category.IScmBackCategoryFrontRelationClient;
import com.machine.client.scm.category.IScmFrontCategoryClient;
import com.machine.client.scm.category.dto.output.ScmBackCategoryFrontRelationListOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryListOutputDto;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.model.request.IdRequest;
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
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 操作日志 SCM 模块-前台分类 业务快照加载器注册。
 */
@Slf4j
@Configuration
public class ScmFrontCategoryOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IScmFrontCategoryClient> scmFrontCategoryClientProvider;

    private final ObjectProvider<IScmBackCategoryFrontRelationClient> scmBackCategoryFrontRelationClientProvider;

    private final ObjectProvider<IScmBackCategoryClient> scmBackCategoryClientProvider;

    public ScmFrontCategoryOperateLogLoad(OperationLogLoaderRegistry registry,
                                          ObjectProvider<IScmFrontCategoryClient> scmFrontCategoryClientProvider,
                                          ObjectProvider<IScmBackCategoryFrontRelationClient> scmBackCategoryFrontRelationClientProvider,
                                          ObjectProvider<IScmBackCategoryClient> scmBackCategoryClientProvider) {
        super(registry);
        this.scmFrontCategoryClientProvider = scmFrontCategoryClientProvider;
        this.scmBackCategoryFrontRelationClientProvider = scmBackCategoryFrontRelationClientProvider;
        this.scmBackCategoryClientProvider = scmBackCategoryClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IScmFrontCategoryClient scmFrontCategoryClient = scmFrontCategoryClientProvider.getIfAvailable();
        if (scmFrontCategoryClient == null) {
            log.debug("未配置 IScmFrontCategoryClient，跳过操作日志 SCM_FRONT_CATEGORY 快照加载器注册");
            return;
        }

        registry.register(ModuleEntityEnum.SCM_FRONT_CATEGORY, "update",
                entityId -> loadDetail(entityId, scmFrontCategoryClient::getById));
        // 删除前台分类：删除前加载分类详情快照，供 diff 记录被删对象
        registry.register(ModuleEntityEnum.SCM_FRONT_CATEGORY, "deleteById",
                entityId -> loadDetail(entityId, scmFrontCategoryClient::getById));

        // 修改关联后台分类：快照为该分类关联的后台分类列表（含分类名称，便于 diff 阅读）
        IScmBackCategoryFrontRelationClient scmBackCategoryFrontRelationClient =
                scmBackCategoryFrontRelationClientProvider.getIfAvailable();
        IScmBackCategoryClient scmBackCategoryClient = scmBackCategoryClientProvider.getIfAvailable();
        if (null == scmBackCategoryFrontRelationClient || null == scmBackCategoryClient) {
            log.debug("未配置后台分类关联 client，跳过操作日志 SCM_FRONT_CATEGORY updateBackCategory 快照加载器注册");
        } else {
            registry.register(ModuleEntityEnum.SCM_FRONT_CATEGORY, "updateBackCategory",
                    entityId -> load4UpdateBackCategory(scmBackCategoryFrontRelationClient, scmBackCategoryClient, entityId));
        }
    }

    /**
     * 修改关联后台分类：快照 = 关联后台分类列表（含分类名称/编码）。
     */
    private BackCategorySnapshot load4UpdateBackCategory(IScmBackCategoryFrontRelationClient relationClient,
                                                        IScmBackCategoryClient backCategoryClient,
                                                        Object entityId) {
        if (entityId == null) {
            return null;
        }

        List<ScmBackCategoryFrontRelationListOutputDto> relationList =
                relationClient.listByFrontCategoryId(new IdRequest(String.valueOf(entityId)));
        if (relationList == null || relationList.isEmpty()) {
            BackCategorySnapshot empty = new BackCategorySnapshot();
            empty.setBackCategoryList(List.of());
            return empty;
        }

        Map<String, ScmBackCategoryListOutputDto> backCategoryMap = loadBackCategoryMap(backCategoryClient);
        List<BackCategoryItem> itemList = new ArrayList<>(relationList.size());
        for (ScmBackCategoryFrontRelationListOutputDto relation : relationList) {
            ScmBackCategoryListOutputDto backCategory = backCategoryMap.get(relation.getBackCategoryId());
            BackCategoryItem item = new BackCategoryItem();
            item.setBackCategoryId(relation.getBackCategoryId());
            item.setBackCategoryCode(null == backCategory ? null : backCategory.getCode());
            item.setBackCategoryName(null == backCategory ? null : backCategory.getName());
            itemList.add(item);
        }

        BackCategorySnapshot snapshot = new BackCategorySnapshot();
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

        private List<BackCategoryItem> backCategoryList;
    }

    @Data
    public static class BackCategoryItem {

        private String backCategoryId;

        private String backCategoryCode;

        private String backCategoryName;
    }
}
