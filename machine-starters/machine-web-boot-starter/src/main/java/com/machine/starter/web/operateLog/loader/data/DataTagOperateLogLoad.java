package com.machine.starter.web.operateLog.loader.data;

import com.machine.client.data.tag.IDataTagCategoryClient;
import com.machine.client.data.tag.IDataTagClient;
import com.machine.client.data.tag.dto.output.DataTagCategoryDetailOutputDto;
import com.machine.client.data.tag.dto.output.DataTagDetailOutputDto;
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
 * 操作日志 DATA 模块-智能标签 业务快照加载器注册。
 * <p>
 * 覆盖修改（update）与关系数据变更（update_category，携带 标签+分类名称）。
 */
@Slf4j
@Configuration
public class DataTagOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IDataTagClient> dataTagClientProvider;
    private final ObjectProvider<IDataTagCategoryClient> dataTagCategoryClientProvider;

    public DataTagOperateLogLoad(OperationLogLoaderRegistry registry,
                                 ObjectProvider<IDataTagClient> dataTagClientProvider,
                                 ObjectProvider<IDataTagCategoryClient> dataTagCategoryClientProvider) {
        super(registry);
        this.dataTagClientProvider = dataTagClientProvider;
        this.dataTagCategoryClientProvider = dataTagCategoryClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IDataTagClient dataTagClient = dataTagClientProvider.getIfAvailable();
        IDataTagCategoryClient dataTagCategoryClient = dataTagCategoryClientProvider.getIfAvailable();
        if (dataTagClient == null) {
            log.debug("未配置 IDataTagClient，跳过操作日志 DATA_TAG 快照加载器注册");
            return;
        }
        // 修改智能标签：返回标签详情对象，对比工具按字段对比
        registry.register(ModuleEntityEnum.DATA_TAG, "update",
                entityId -> loadDetail(entityId, dataTagClient::detail));

        // 删除智能标签：删除前加载标签详情快照，供 diff 记录被删对象
        registry.register(ModuleEntityEnum.DATA_TAG, "delete",
                entityId -> loadDetail(entityId, dataTagClient::detail));

        // 修改智能标签关联分类：返回 标签 + 分类名称 快照对象（关系变更，携带 name）
        if (dataTagCategoryClient != null) {
            registry.register(ModuleEntityEnum.DATA_TAG, "updateCategory",
                    entityId -> loadTag4UpdateCategory(dataTagClient, dataTagCategoryClient, entityId));
        }
    }

    /**
     * 修改智能标签关联分类：快照为 标签 + 分类名称（关系变更，携带 name）。
     */
    private CategoryRelationSnapshot loadTag4UpdateCategory(IDataTagClient tagClient,
                                                           IDataTagCategoryClient categoryClient,
                                                           Object entityId) {
        if (entityId == null) {
            return null;
        }
        DataTagDetailOutputDto detail = tagClient.detail(new IdRequest(String.valueOf(entityId)));
        if (detail == null) {
            return null;
        }
        CategoryRelationSnapshot snapshot = new CategoryRelationSnapshot();
        snapshot.setId(detail.getId());
        snapshot.setName(detail.getName());
        snapshot.setCategoryId(detail.getCategoryId());
        if (detail.getCategoryId() != null) {
            DataTagCategoryDetailOutputDto category = categoryClient.detail(new IdRequest(detail.getCategoryId()));
            if (category != null && category.getName() != null) {
                snapshot.setCategoryName(category.getName());
            }
        }
        return snapshot;
    }

    @Data
    private static class CategoryRelationSnapshot {

        @Schema(description = "标签ID")
        private String id;

        @Schema(description = "标签名称")
        private String name;

        @Schema(description = "分类ID")
        private String categoryId;

        @Schema(description = "分类名称")
        private String categoryName;

    }

}
