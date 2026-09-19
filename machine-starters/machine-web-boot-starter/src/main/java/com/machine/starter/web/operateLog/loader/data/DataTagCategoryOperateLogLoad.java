package com.machine.starter.web.operateLog.loader.data;

import com.machine.client.data.tag.IDataTagCategoryClient;
import com.machine.client.data.tag.dto.output.DataTagCategoryDetailOutputDto;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.starter.web.operateLog.diff.dto.ParentChangeSnapshot;
import com.machine.starter.web.operateLog.loader.AbstractModuleOperateLogLoad;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

/**
 * 操作日志 DATA 模块-智能标签分类 业务快照加载器注册。
 * <p>
 * 覆盖修改（update）与父节点关系变更（update_parent，携带 分类+父节点名称）。
 */
@Slf4j
@Configuration
public class DataTagCategoryOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IDataTagCategoryClient> dataTagCategoryClientProvider;

    public DataTagCategoryOperateLogLoad(OperationLogLoaderRegistry registry,
                                         ObjectProvider<IDataTagCategoryClient> dataTagCategoryClientProvider) {
        super(registry);
        this.dataTagCategoryClientProvider = dataTagCategoryClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IDataTagCategoryClient dataTagCategoryClient = dataTagCategoryClientProvider.getIfAvailable();
        if (dataTagCategoryClient == null) {
            log.debug("未配置 IDataTagCategoryClient，跳过操作日志 DATA_TAG_CATEGORY 快照加载器注册");
            return;
        }
        // 修改智能标签分类：返回分类详情对象，对比工具按字段对比
        registry.register(ModuleEntityEnum.DATA_TAG_CATEGORY, "update",
                entityId -> loadDetail(entityId, dataTagCategoryClient::detail));

        // 删除智能标签分类：删除前加载分类详情快照，供 diff 记录被删对象
        registry.register(ModuleEntityEnum.DATA_TAG_CATEGORY, "delete",
                entityId -> loadDetail(entityId, dataTagCategoryClient::detail));

        // 修改智能标签分类父节点：返回 分类 + 父节点名称 快照对象（关系变更，携带 name）
        registry.register(ModuleEntityEnum.DATA_TAG_CATEGORY, "updateParent",
                entityId -> loadTagCategory4UpdateParent(dataTagCategoryClient, entityId));
    }

    private ParentChangeSnapshot loadTagCategory4UpdateParent(IDataTagCategoryClient client,
                                                             Object entityId) {
        if (entityId == null) {
            return null;
        }
        DataTagCategoryDetailOutputDto detail = client.detail(new IdRequest(String.valueOf(entityId)));
        if (detail == null) {
            return null;
        }
        ParentChangeSnapshot snapshot = new ParentChangeSnapshot();
        snapshot.setId(detail.getId());
        snapshot.setName(detail.getName());
        snapshot.setParentId(detail.getParentId());
        if (detail.getParentId() != null) {
            DataTagCategoryDetailOutputDto parent = client.detail(new IdRequest(detail.getParentId()));
            if (parent != null && parent.getName() != null) {
                snapshot.setParentName(parent.getName());
            }
        }
        return snapshot;
    }
}
