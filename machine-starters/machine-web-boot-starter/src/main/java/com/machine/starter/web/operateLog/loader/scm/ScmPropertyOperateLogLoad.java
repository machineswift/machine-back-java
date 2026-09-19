package com.machine.starter.web.operateLog.loader.scm;

import com.machine.client.scm.property.IScmPropertyClient;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.starter.web.operateLog.loader.AbstractModuleOperateLogLoad;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

/**
 * 操作日志 SCM 模块-属性 业务快照加载器注册。
 */
@Slf4j
@Configuration
public class ScmPropertyOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IScmPropertyClient> scmPropertyClientProvider;

    public ScmPropertyOperateLogLoad(OperationLogLoaderRegistry registry,
                                     ObjectProvider<IScmPropertyClient> scmPropertyClientProvider) {
        super(registry);
        this.scmPropertyClientProvider = scmPropertyClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IScmPropertyClient scmPropertyClient = scmPropertyClientProvider.getIfAvailable();
        if (scmPropertyClient != null) {
            registry.register(ModuleEntityEnum.SCM_PROPERTY, "update",
                    entityId -> loadDetail(entityId, scmPropertyClient::getById));
            // 删除属性：删除前加载属性详情快照，供 diff 记录被删对象
            registry.register(ModuleEntityEnum.SCM_PROPERTY, "deleteById",
                    entityId -> loadDetail(entityId, scmPropertyClient::getById));
        } else {
            log.debug("未配置 IScmPropertyClient，跳过操作日志 SCM_PROPERTY 快照加载器注册");
        }
    }
}
