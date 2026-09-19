package com.machine.starter.web.operateLog.loader.scm;

import com.machine.client.scm.category.IScmFrontCategoryClient;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.starter.web.operateLog.loader.AbstractModuleOperateLogLoad;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

/**
 * 操作日志 SCM 模块-前台分类 业务快照加载器注册。
 */
@Slf4j
@Configuration
public class ScmFrontCategoryOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IScmFrontCategoryClient> scmFrontCategoryClientProvider;

    public ScmFrontCategoryOperateLogLoad(OperationLogLoaderRegistry registry,
                                          ObjectProvider<IScmFrontCategoryClient> scmFrontCategoryClientProvider) {
        super(registry);
        this.scmFrontCategoryClientProvider = scmFrontCategoryClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IScmFrontCategoryClient scmFrontCategoryClient = scmFrontCategoryClientProvider.getIfAvailable();
        if (scmFrontCategoryClient != null) {
            registry.register(ModuleEntityEnum.SCM_FRONT_CATEGORY, "update",
                    entityId -> loadDetail(entityId, scmFrontCategoryClient::getById));
            // 删除前台分类：删除前加载分类详情快照，供 diff 记录被删对象
            registry.register(ModuleEntityEnum.SCM_FRONT_CATEGORY, "deleteById",
                    entityId -> loadDetail(entityId, scmFrontCategoryClient::getById));
        } else {
            log.debug("未配置 IScmFrontCategoryClient，跳过操作日志 SCM_FRONT_CATEGORY 快照加载器注册");
        }
    }
}
