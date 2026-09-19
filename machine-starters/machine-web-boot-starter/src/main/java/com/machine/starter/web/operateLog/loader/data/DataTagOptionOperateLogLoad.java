package com.machine.starter.web.operateLog.loader.data;

import com.machine.client.data.tag.IDataTagOptionClient;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.starter.web.operateLog.loader.AbstractModuleOperateLogLoad;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

/**
 * 操作日志 DATA 模块-智能标签选项 业务快照加载器注册。
 */
@Slf4j
@Configuration
public class DataTagOptionOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IDataTagOptionClient> dataTagOptionClientProvider;

    public DataTagOptionOperateLogLoad(OperationLogLoaderRegistry registry,
                                       ObjectProvider<IDataTagOptionClient> dataTagOptionClientProvider) {
        super(registry);
        this.dataTagOptionClientProvider = dataTagOptionClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IDataTagOptionClient dataTagOptionClient = dataTagOptionClientProvider.getIfAvailable();
        if (dataTagOptionClient != null) {
            registry.register(ModuleEntityEnum.DATA_TAG_OPTION, "update",
                    entityId -> loadDetail(entityId, dataTagOptionClient::detail));
            // 删除智能标签选项：删除前加载选项详情快照，供 diff 记录被删对象
            registry.register(ModuleEntityEnum.DATA_TAG_OPTION, "delete",
                    entityId -> loadDetail(entityId, dataTagOptionClient::detail));
        } else {
            log.debug("未配置 IDataTagOptionClient，跳过操作日志 DATA_TAG_OPTION 快照加载器注册");
        }
    }
}
