package com.machine.starter.web.operateLog.loader.scm;

import com.machine.client.scm.property.IScmPropertyGroupClient;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.starter.web.operateLog.loader.AbstractModuleOperateLogLoad;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

/**
 * 操作日志 SCM 模块-属性分组 业务快照加载器注册。
 */
@Slf4j
@Configuration
public class ScmPropertyGroupOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IScmPropertyGroupClient> scmPropertyGroupClientProvider;

    public ScmPropertyGroupOperateLogLoad(OperationLogLoaderRegistry registry,
                                          ObjectProvider<IScmPropertyGroupClient> scmPropertyGroupClientProvider) {
        super(registry);
        this.scmPropertyGroupClientProvider = scmPropertyGroupClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IScmPropertyGroupClient scmPropertyGroupClient = scmPropertyGroupClientProvider.getIfAvailable();
        if (scmPropertyGroupClient != null) {
            registry.register(ModuleEntityEnum.SCM_PROPERTY_GROUP, "update",
                    entityId -> loadDetail(entityId, scmPropertyGroupClient::getById));
            // 删除属性分组：删除前加载分组详情快照，供 diff 记录被删对象
            registry.register(ModuleEntityEnum.SCM_PROPERTY_GROUP, "deleteById",
                    entityId -> loadDetail(entityId, scmPropertyGroupClient::getById));
        } else {
            log.debug("未配置 IScmPropertyGroupClient，跳过操作日志 SCM_PROPERTY_GROUP 快照加载器注册");
        }
    }
}
