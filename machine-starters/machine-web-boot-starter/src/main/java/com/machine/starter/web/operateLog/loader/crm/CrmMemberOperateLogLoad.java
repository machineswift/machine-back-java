package com.machine.starter.web.operateLog.loader.crm;

import com.machine.client.crm.member.ICrmMemberClient;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.starter.web.operateLog.loader.AbstractModuleOperateLogLoad;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

/**
 * 操作日志 CRM 模块-会员 业务快照加载器注册。
 */
@Slf4j
@Configuration
public class CrmMemberOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<ICrmMemberClient> crmMemberClientProvider;

    public CrmMemberOperateLogLoad(OperationLogLoaderRegistry registry,
                                   ObjectProvider<ICrmMemberClient> crmMemberClientProvider) {
        super(registry);
        this.crmMemberClientProvider = crmMemberClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        ICrmMemberClient crmMemberClient = crmMemberClientProvider.getIfAvailable();
        if (crmMemberClient != null) {
            registry.register(ModuleEntityEnum.CRM_MEMBER, "update",
                    entityId -> loadDetail(entityId, crmMemberClient::detail));
            // 删除会员：删除前加载会员详情快照，供 diff 记录被删对象
            registry.register(ModuleEntityEnum.CRM_MEMBER, "delete",
                    entityId -> loadDetail(entityId, crmMemberClient::detail));
        } else {
            log.debug("未配置 ICrmMemberClient，跳过操作日志 CRM_MEMBER 快照加载器注册");
        }
    }
}
