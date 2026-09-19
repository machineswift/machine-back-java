package com.machine.starter.web.operateLog.loader.data;

import com.machine.client.data.supplier.IDataSupplierCompanyClient;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.starter.web.operateLog.loader.AbstractModuleOperateLogLoad;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

/**
 * 操作日志 DATA 模块-供应商公司 业务快照加载器注册。
 */
@Slf4j
@Configuration
public class DataSupplierOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IDataSupplierCompanyClient> dataSupplierCompanyClientProvider;

    public DataSupplierOperateLogLoad(OperationLogLoaderRegistry registry,
                                      ObjectProvider<IDataSupplierCompanyClient> dataSupplierCompanyClientProvider) {
        super(registry);
        this.dataSupplierCompanyClientProvider = dataSupplierCompanyClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IDataSupplierCompanyClient dataSupplierCompanyClient = dataSupplierCompanyClientProvider.getIfAvailable();
        if (dataSupplierCompanyClient != null) {
            registry.register(ModuleEntityEnum.DATA_SUPPLIER, "update",
                    entityId -> loadDetail(entityId, dataSupplierCompanyClient::detail));
        } else {
            log.debug("未配置 IDataSupplierCompanyClient，跳过操作日志 DATA_SUPPLIER 快照加载器注册");
        }
    }
}
