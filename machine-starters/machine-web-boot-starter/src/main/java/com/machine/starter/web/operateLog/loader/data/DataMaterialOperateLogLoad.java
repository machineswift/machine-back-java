package com.machine.starter.web.operateLog.loader.data;

import com.machine.client.data.filecenter.material.IDataMaterialCategoryClient;
import com.machine.client.data.filecenter.material.IDataMaterialCategoryRelationClient;
import com.machine.client.data.filecenter.material.IDataMaterialClient;
import com.machine.client.data.filecenter.material.dto.output.DataMaterialCategoryListOutputDto;
import com.machine.client.data.filecenter.material.dto.output.DataMaterialCategoryRelationOutputDto;
import com.machine.client.data.filecenter.material.dto.output.DataMaterialDetailOutputDto;
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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作日志 DATA 模块-素材 业务快照加载器注册。
 * <p>
 * 覆盖修改（update）与关系数据变更（update_category，携带 素材+分类ID->名称）。
 */
@Slf4j
@Configuration
public class DataMaterialOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IDataMaterialClient> dataMaterialClientProvider;
    private final ObjectProvider<IDataMaterialCategoryRelationClient> materialCategoryRelationClientProvider;
    private final ObjectProvider<IDataMaterialCategoryClient> dataMaterialCategoryClientProvider;

    public DataMaterialOperateLogLoad(OperationLogLoaderRegistry registry,
                                      ObjectProvider<IDataMaterialClient> dataMaterialClientProvider,
                                      ObjectProvider<IDataMaterialCategoryRelationClient> materialCategoryRelationClientProvider,
                                      ObjectProvider<IDataMaterialCategoryClient> dataMaterialCategoryClientProvider) {
        super(registry);
        this.dataMaterialClientProvider = dataMaterialClientProvider;
        this.materialCategoryRelationClientProvider = materialCategoryRelationClientProvider;
        this.dataMaterialCategoryClientProvider = dataMaterialCategoryClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IDataMaterialClient dataMaterialClient = dataMaterialClientProvider.getIfAvailable();
        if (dataMaterialClient == null) {
            log.debug("未配置 IDataMaterialClient，跳过操作日志 DATA_MATERIAL 快照加载器注册");
            return;
        }
        // 修改素材：返回素材详情对象，对比工具按字段对比
        registry.register(ModuleEntityEnum.DATA_MATERIAL, "update",
                entityId -> loadDetail(entityId, dataMaterialClient::getById));

        // 修改素材分类：返回 素材 + 分类ID->分类名称 快照对象（关系变更，携带 name）
        IDataMaterialCategoryRelationClient materialCategoryRelationClient =
                materialCategoryRelationClientProvider.getIfAvailable();
        IDataMaterialCategoryClient dataMaterialCategoryClient = dataMaterialCategoryClientProvider.getIfAvailable();
        if (materialCategoryRelationClient != null && dataMaterialCategoryClient != null) {
            registry.register(ModuleEntityEnum.DATA_MATERIAL, "updateCategory",
                    entityId -> loadMaterial4UpdateCategory(dataMaterialClient, materialCategoryRelationClient,
                            dataMaterialCategoryClient, entityId));
        }
    }

    /**
     * 修改素材分类：快照为 素材 + 分类ID -> 分类名称（关系变更，携带 name）。
     */
    private MaterialCategorySnapshot loadMaterial4UpdateCategory(IDataMaterialClient materialClient,
                                                                IDataMaterialCategoryRelationClient relationClient,
                                                                IDataMaterialCategoryClient categoryClient,
                                                                Object entityId) {
        if (entityId == null) {
            return null;
        }
        DataMaterialDetailOutputDto detail = materialClient.getById(new IdRequest(String.valueOf(entityId)));
        if (detail == null) {
            return null;
        }
        List<DataMaterialCategoryRelationOutputDto> relationList =
                relationClient.listByMaterialId(new IdRequest(String.valueOf(entityId)));
        Map<String, String> categoryIdNameMap = loadMaterialCategoryIdNameMap(categoryClient);

        MaterialCategorySnapshot snapshot = new MaterialCategorySnapshot();
        snapshot.setId(detail.getId());
        snapshot.setTitle(detail.getTitle());
        LinkedHashMap<String, String> categoryIdNameLinked = new LinkedHashMap<>();
        if (relationList != null) {
            for (DataMaterialCategoryRelationOutputDto relation : relationList) {
                if (relation.getCategoryId() != null) {
                    categoryIdNameLinked.put(relation.getCategoryId(),
                            categoryIdNameMap.getOrDefault(relation.getCategoryId(), relation.getCategoryId()));
                }
            }
        }
        snapshot.setCategoryIdNameMap(categoryIdNameLinked);
        return snapshot;
    }

    /**
     * 素材分类ID -> 分类名称（全量分类列表）。
     */
    private Map<String, String> loadMaterialCategoryIdNameMap(IDataMaterialCategoryClient categoryClient) {
        if (categoryClient == null) {
            return Map.of();
        }
        List<DataMaterialCategoryListOutputDto> categoryList = categoryClient.listAll();
        if (categoryList == null || categoryList.isEmpty()) {
            return Map.of();
        }
        Map<String, String> idNameMap = new HashMap<>(categoryList.size());
        for (DataMaterialCategoryListOutputDto category : categoryList) {
            if (category.getId() != null) {
                idNameMap.put(category.getId(), category.getName() != null ? category.getName() : category.getId());
            }
        }
        return idNameMap;
    }

    @Data
    private static class MaterialCategorySnapshot {

        @Schema(description = "素材ID")
        private String id;

        @Schema(description = "素材标题")
        private String title;

        @Schema(description = "分类ID -> 分类名称")
        private Map<String, String> categoryIdNameMap;

    }

}
