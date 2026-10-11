package com.machine.starter.web.operateLog.loader.scm;

import com.machine.client.scm.attribute.IScmAttributeValueClient;
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
public class ScmAttributeValueOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IScmAttributeValueClient> scmAttributeValueClientProvider;

    public ScmAttributeValueOperateLogLoad(OperationLogLoaderRegistry registry,
                                          ObjectProvider<IScmAttributeValueClient> scmAttributeValueClientProvider) {
        super(registry);
        this.scmAttributeValueClientProvider = scmAttributeValueClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IScmAttributeValueClient scmAttributeValueClient = scmAttributeValueClientProvider.getIfAvailable();
        if (scmAttributeValueClient != null) {
            registry.register(ModuleEntityEnum.SCM_ATTRIBUTE_VALUE, "update",
                    entityId -> loadDetail(entityId, scmAttributeValueClient::getById));
            // 删除属性值：删除前加载属性值详情快照，供 diff 记录被删对象
            registry.register(ModuleEntityEnum.SCM_ATTRIBUTE_VALUE, "deleteById",
                    entityId -> loadDetail(entityId, scmAttributeValueClient::getById));
        } else {
            log.debug("未配置 IScmAttributeValueClient，跳过操作日志 SCM_ATTRIBUTE_VALUE 快照加载器注册");
        }
    }
}
