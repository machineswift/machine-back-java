package com.machine.starter.web.operateLog.loader.biam;

import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.identity.IBIamOauth2RegisteredClientClient;
import com.machine.client.iam.biam.identity.dto.input.BIamOAuth2RegisteredClientUpdateInputDto;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.starter.web.operateLog.loader.AbstractModuleOperateLogLoad;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

/**
 * 操作日志 BIAM_AUTH2_CLIENT 业务快照加载器注册。
 */
@Slf4j
@Configuration
public class BIamAuth2ClientOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IBIamOauth2RegisteredClientClient> auth2ClientProvider;

    public BIamAuth2ClientOperateLogLoad(OperationLogLoaderRegistry registry,
                                         ObjectProvider<IBIamOauth2RegisteredClientClient> auth2ClientProvider) {
        super(registry);
        this.auth2ClientProvider = auth2ClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IBIamOauth2RegisteredClientClient auth2Client = auth2ClientProvider.getIfAvailable();
        if (auth2Client == null) {
            log.debug("未配置 IBIamOauth2RegisteredClientClient，跳过操作日志 BIAM_AUTH2_CLIENT 快照加载器注册");
            return;
        }

        // 修改认证客户端：返回客户端详情对象，对比工具按字段对比
        registry.register(ModuleEntityEnum.BIAM_AUTH2_CLIENT, "update",
                entityId -> load4Update(auth2Client, entityId));

        // 删除认证客户端：删除前加载客户端详情快照，供 diff 记录被删对象
        registry.register(ModuleEntityEnum.BIAM_AUTH2_CLIENT, "delete",
                entityId -> load4Update(auth2Client, entityId));
    }

    private BIamOAuth2RegisteredClientUpdateInputDto load4Update(
            IBIamOauth2RegisteredClientClient auth2Client,
            Object entityId) {
        Object detail = loadDetail(entityId, auth2Client::detail);
        return detail == null ? null
                : JSONUtil.toBean(JSONUtil.toJsonStr(detail), BIamOAuth2RegisteredClientUpdateInputDto.class);
    }
}
