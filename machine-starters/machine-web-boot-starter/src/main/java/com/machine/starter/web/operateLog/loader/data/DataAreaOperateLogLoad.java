package com.machine.starter.web.operateLog.loader.data;

import com.machine.client.data.area.IDataAreaClient;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.starter.web.operateLog.loader.AbstractModuleOperateLogLoad;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

/**
 * 操作日志 DATA 模块-区域 业务快照加载器注册。
 */
@Slf4j
@Configuration
public class DataAreaOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IDataAreaClient> dataAreaClientProvider;

    public DataAreaOperateLogLoad(OperationLogLoaderRegistry registry,
                                  ObjectProvider<IDataAreaClient> dataAreaClientProvider) {
        super(registry);
        this.dataAreaClientProvider = dataAreaClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IDataAreaClient dataAreaClient = dataAreaClientProvider.getIfAvailable();
        if (dataAreaClient != null) {
            registry.register(ModuleEntityEnum.DATA_AREA, "update",
                    entityId -> loadDetail(entityId, dataAreaClient::detail));
            // 删除区域：删除前加载区域详情快照，供 diff 记录被删对象
            registry.register(ModuleEntityEnum.DATA_AREA, "delete",
                    entityId -> loadDetail(entityId, dataAreaClient::detail));
        } else {
            log.debug("未配置 IDataAreaClient，跳过操作日志 DATA_AREA 快照加载器注册");
        }
    }
}
