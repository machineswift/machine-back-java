package com.machine.starter.web.operateLog.loader.data;

import com.machine.client.data.area.IDataAreaClient;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.starter.web.operateLog.loader.AbstractModuleOperateLogLoad;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

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
            registry.register(ModuleEntityEnum.DATA_AREA, "delete",
                    entityId -> loadDetail(entityId, dataAreaClient::detail));
        } else {
            log.debug("未配置 IDataAreaClient，跳过操作日志 DATA_AREA 快照加载器注册");
        }
    }
}
