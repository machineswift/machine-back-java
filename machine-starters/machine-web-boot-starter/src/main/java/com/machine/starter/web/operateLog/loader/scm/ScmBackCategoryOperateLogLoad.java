package com.machine.starter.web.operateLog.loader.scm;

import com.machine.client.scm.category.IScmBackCategoryClient;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.starter.web.operateLog.loader.AbstractModuleOperateLogLoad;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

/**
 * 操作日志 SCM 模块-后台分类 业务快照加载器注册。
 */
@Slf4j
@Configuration
public class ScmBackCategoryOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IScmBackCategoryClient> scmBackCategoryClientProvider;

    public ScmBackCategoryOperateLogLoad(OperationLogLoaderRegistry registry,
                                         ObjectProvider<IScmBackCategoryClient> scmBackCategoryClientProvider) {
        super(registry);
        this.scmBackCategoryClientProvider = scmBackCategoryClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IScmBackCategoryClient scmBackCategoryClient = scmBackCategoryClientProvider.getIfAvailable();
        if (scmBackCategoryClient != null) {
            registry.register(ModuleEntityEnum.SCM_BACK_CATEGORY, "update",
                    entityId -> loadDetail(entityId, scmBackCategoryClient::getById));
            // 删除后台分类：删除前加载分类详情快照，供 diff 记录被删对象
            registry.register(ModuleEntityEnum.SCM_BACK_CATEGORY, "deleteById",
                    entityId -> loadDetail(entityId, scmBackCategoryClient::getById));
        } else {
            log.debug("未配置 IScmBackCategoryClient，跳过操作日志 SCM_BACK_CATEGORY 快照加载器注册");
        }
    }
}
