package com.machine.starter.web.operateLog.loader.data;

import com.machine.client.data.brand.IDataBrandClient;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.starter.web.operateLog.loader.AbstractModuleOperateLogLoad;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

/**
 * 操作日志 DATA 模块-品牌 业务快照加载器注册。
 */
@Slf4j
@Configuration
public class DataBrandOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IDataBrandClient> dataBrandClientProvider;

    public DataBrandOperateLogLoad(OperationLogLoaderRegistry registry,
                                   ObjectProvider<IDataBrandClient> dataBrandClientProvider) {
        super(registry);
        this.dataBrandClientProvider = dataBrandClientProvider;
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
        } else {
            log.debug("未配置 IDataBrandClient，跳过操作日志 DATA_BRAND 快照加载器注册");
        }
    }
}
