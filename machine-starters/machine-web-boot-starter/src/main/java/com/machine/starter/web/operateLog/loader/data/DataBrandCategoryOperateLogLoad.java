package com.machine.starter.web.operateLog.loader.data;

import com.machine.client.data.brand.IDataBrandCategoryClient;
import com.machine.client.data.brand.dto.output.DataBrandCategoryDetailOutputDto;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.starter.web.operateLog.diff.dto.ParentChangeSnapshot;
import com.machine.starter.web.operateLog.loader.AbstractModuleOperateLogLoad;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DataBrandCategoryOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IDataBrandCategoryClient> brandCategoryClientProvider;

    public DataBrandCategoryOperateLogLoad(OperationLogLoaderRegistry registry,
                                           ObjectProvider<IDataBrandCategoryClient> brandCategoryClientProvider) {
        super(registry);
        this.brandCategoryClientProvider = brandCategoryClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IDataBrandCategoryClient client = brandCategoryClientProvider.getIfAvailable();
        if (client == null) {
            log.debug("未配置 IDataBrandCategoryClient，跳过操作日志 DATA_BRAND_CATEGORY 快照加载器注册");
            return;
        }
        registry.register(ModuleEntityEnum.DATA_BRAND_CATEGORY, "update",
                entityId -> loadDetail(entityId, client::detail));
        registry.register(ModuleEntityEnum.DATA_BRAND_CATEGORY, "delete",
                entityId -> loadDetail(entityId, client::detail));
        registry.register(ModuleEntityEnum.DATA_BRAND_CATEGORY, "updateParent",
                entityId -> loadBrandCategory4UpdateParent(client, entityId));
    }

    private ParentChangeSnapshot loadBrandCategory4UpdateParent(IDataBrandCategoryClient client, Object entityId) {
        if (entityId == null) {
            return null;
        }
        DataBrandCategoryDetailOutputDto detail = client.detail(new IdRequest(String.valueOf(entityId)));
        if (detail == null) {
            return null;
        }
        ParentChangeSnapshot snapshot = new ParentChangeSnapshot();
        snapshot.setId(detail.getId());
        snapshot.setName(detail.getName());
        snapshot.setParentId(detail.getParentId());
        if (detail.getParentId() != null) {
            DataBrandCategoryDetailOutputDto parent = client.detail(new IdRequest(detail.getParentId()));
            if (parent != null) {
                snapshot.setParentName(parent.getName());
            }
        }
        return snapshot;
    }
}
