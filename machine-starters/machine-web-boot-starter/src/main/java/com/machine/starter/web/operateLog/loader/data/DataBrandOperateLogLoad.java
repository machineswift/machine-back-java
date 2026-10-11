package com.machine.starter.web.operateLog.loader.data;

import com.machine.client.data.brand.IDataBrandCategoryClient;
import com.machine.client.data.brand.IDataBrandCategoryRelationClient;
import com.machine.client.data.brand.IDataBrandClient;
import com.machine.client.data.brand.dto.output.DataBrandCategoryListOutputDto;
import com.machine.client.data.brand.dto.output.DataBrandCategoryRelationOutputDto;
import com.machine.client.data.brand.dto.output.DataBrandDetailOutputDto;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.starter.web.operateLog.diff.dto.ParentChangeSnapshot;
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

@Slf4j
@Configuration
public class DataBrandOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IDataBrandClient> dataBrandClientProvider;
    private final ObjectProvider<IDataBrandCategoryRelationClient> brandCategoryRelationClientProvider;
    private final ObjectProvider<IDataBrandCategoryClient> dataBrandCategoryClientProvider;

    public DataBrandOperateLogLoad(OperationLogLoaderRegistry registry,
                                   ObjectProvider<IDataBrandClient> dataBrandClientProvider,
                                   ObjectProvider<IDataBrandCategoryRelationClient> brandCategoryRelationClientProvider,
                                   ObjectProvider<IDataBrandCategoryClient> dataBrandCategoryClientProvider) {
        super(registry);
        this.dataBrandClientProvider = dataBrandClientProvider;
        this.brandCategoryRelationClientProvider = brandCategoryRelationClientProvider;
        this.dataBrandCategoryClientProvider = dataBrandCategoryClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IDataBrandClient dataBrandClient = dataBrandClientProvider.getIfAvailable();
        if (dataBrandClient != null) {
            registry.register(ModuleEntityEnum.DATA_BRAND, "update",
                    entityId -> loadDetail(entityId, dataBrandClient::detail));
            // 删除品牌：删除前加载品牌详情快照，供 diff 记录被删对象
            registry.register(ModuleEntityEnum.DATA_BRAND, "delete",
                    entityId -> loadDetail(entityId, dataBrandClient::detail));

            // 修改品牌父节点：返回 权限 + 父节点名称 快照对象（关系变更，携带 name 便于审计）
            registry.register(ModuleEntityEnum.DATA_BRAND, "updateParent",
                    entityId -> load4UpdateParent(dataBrandClient, entityId));

            // 修改品牌分类：返回 品牌 + 分类ID->分类名称 快照对象（关系变更，携带 name）
            IDataBrandCategoryRelationClient brandCategoryRelationClient =
                    brandCategoryRelationClientProvider.getIfAvailable();
            IDataBrandCategoryClient dataBrandCategoryClient = dataBrandCategoryClientProvider.getIfAvailable();
            if (brandCategoryRelationClient != null && dataBrandCategoryClient != null) {
                registry.register(ModuleEntityEnum.DATA_BRAND, "updateCategory",
                        entityId -> loadBrand4UpdateCategory(dataBrandClient, brandCategoryRelationClient,
                                dataBrandCategoryClient, entityId));
            }
        } else {
            log.debug("未配置 IDataBrandClient，跳过操作日志 DATA_BRAND 快照加载器注册");
        }
    }

    /**
     * 修改品牌分类：快照为 品牌 + 分类ID -> 分类名称（关系变更，携带 name）。
     */
    private BrandCategorySnapshot loadBrand4UpdateCategory(IDataBrandClient brandClient,
                                                           IDataBrandCategoryRelationClient relationClient,
                                                           IDataBrandCategoryClient categoryClient,
                                                           Object entityId) {
        if (entityId == null) {
            return null;
        }
        DataBrandDetailOutputDto detail = brandClient.detail(new IdRequest(String.valueOf(entityId)));
        if (detail == null) {
            return null;
        }
        List<DataBrandCategoryRelationOutputDto> relationList =
                relationClient.listByBrandId(new IdRequest(String.valueOf(entityId)));
        Map<String, String> categoryIdNameMap = loadBrandCategoryIdNameMap(categoryClient);

        BrandCategorySnapshot snapshot = new BrandCategorySnapshot();
        snapshot.setId(detail.getId());
        snapshot.setName(detail.getName());
        LinkedHashMap<String, String> categoryIdNameLinked = new LinkedHashMap<>();
        if (relationList != null) {
            for (DataBrandCategoryRelationOutputDto relation : relationList) {
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
     * 品牌分类ID -> 分类名称（全量分类列表）。
     */
    private Map<String, String> loadBrandCategoryIdNameMap(IDataBrandCategoryClient categoryClient) {
        if (categoryClient == null) {
            return Map.of();
        }
        List<DataBrandCategoryListOutputDto> categoryList = categoryClient.listAll();
        if (categoryList == null || categoryList.isEmpty()) {
            return Map.of();
        }
        Map<String, String> idNameMap = new HashMap<>(categoryList.size());
        for (DataBrandCategoryListOutputDto category : categoryList) {
            if (category.getId() != null) {
                idNameMap.put(category.getId(), category.getName() != null ? category.getName() : category.getId());
            }
        }
        return idNameMap;
    }

    @Data
    private static class BrandCategorySnapshot {

        @Schema(description = "品牌ID")
        private String id;

        @Schema(description = "品牌名称")
        private String name;

        @Schema(description = "分类ID -> 分类名称")
        private Map<String, String> categoryIdNameMap;

    }

    private ParentChangeSnapshot load4UpdateParent(IDataBrandClient brandClient,
                                                   Object entityId) {
        DataBrandDetailOutputDto detail = (DataBrandDetailOutputDto)
                loadDetail(entityId, brandClient::detail);
        if (detail == null) {
            return null;
        }
        ParentChangeSnapshot snapshot = new ParentChangeSnapshot();
        snapshot.setId(detail.getId());
        snapshot.setName(detail.getName());
        snapshot.setParentId(detail.getParentId());
        if (detail.getParentId() != null) {
            DataBrandDetailOutputDto parent =
                    brandClient.detail(new IdRequest(detail.getParentId()));
            if (parent != null && parent.getName() != null) {
                snapshot.setParentName(parent.getName());
            }
        }
        return snapshot;
    }
}
