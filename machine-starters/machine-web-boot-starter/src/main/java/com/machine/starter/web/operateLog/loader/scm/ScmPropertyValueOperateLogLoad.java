package com.machine.starter.web.operateLog.loader.scm;

import com.machine.client.scm.property.IScmPropertyValueClient;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.starter.web.operateLog.loader.AbstractModuleOperateLogLoad;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

/**
 * 操作日志 SCM 模块-属性值 业务快照加载器注册。
 */
@Slf4j
@Configuration
public class ScmPropertyValueOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IScmPropertyValueClient> scmPropertyValueClientProvider;

    public ScmPropertyValueOperateLogLoad(OperationLogLoaderRegistry registry,
                                          ObjectProvider<IScmPropertyValueClient> scmPropertyValueClientProvider) {
        super(registry);
        this.scmPropertyValueClientProvider = scmPropertyValueClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IScmPropertyValueClient scmPropertyValueClient = scmPropertyValueClientProvider.getIfAvailable();
        if (scmPropertyValueClient != null) {
            registry.register(ModuleEntityEnum.SCM_PROPERTY_VALUE, "update",
                    entityId -> loadDetail(entityId, scmPropertyValueClient::getById));
            // 删除属性值：删除前加载属性值详情快照，供 diff 记录被删对象
            registry.register(ModuleEntityEnum.SCM_PROPERTY_VALUE, "deleteById",
                    entityId -> loadDetail(entityId, scmPropertyValueClient::getById));
        } else {
            log.debug("未配置 IScmPropertyValueClient，跳过操作日志 SCM_PROPERTY_VALUE 快照加载器注册");
        }
    }
}
