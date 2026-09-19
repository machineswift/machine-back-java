package com.machine.starter.web.operateLog.loader.crm;

import com.machine.client.crm.customer.ICrmCustomerClient;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.starter.web.operateLog.loader.AbstractModuleOperateLogLoad;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

/**
 * 操作日志 CRM 模块-客户 业务快照加载器注册。
 */
@Slf4j
@Configuration
public class CrmCustomerOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<ICrmCustomerClient> crmCustomerClientProvider;

    public CrmCustomerOperateLogLoad(OperationLogLoaderRegistry registry,
                                     ObjectProvider<ICrmCustomerClient> crmCustomerClientProvider) {
        super(registry);
        this.crmCustomerClientProvider = crmCustomerClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        ICrmCustomerClient crmCustomerClient = crmCustomerClientProvider.getIfAvailable();
        if (crmCustomerClient != null) {
            registry.register(ModuleEntityEnum.CRM_CUSTOMER, "update",
                    entityId -> loadDetail(entityId, crmCustomerClient::detail));
            // 删除客户：删除前加载客户详情快照，供 diff 记录被删对象
            registry.register(ModuleEntityEnum.CRM_CUSTOMER, "delete",
                    entityId -> loadDetail(entityId, crmCustomerClient::detail));
        } else {
            log.debug("未配置 ICrmCustomerClient，跳过操作日志 CRM_CUSTOMER 快照加载器注册");
        }
    }
}
