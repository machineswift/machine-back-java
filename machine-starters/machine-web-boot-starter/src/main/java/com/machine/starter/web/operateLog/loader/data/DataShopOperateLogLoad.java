package com.machine.starter.web.operateLog.loader.data;

import com.machine.client.data.label.IDataLabelOptionClient;
import com.machine.client.data.label.dto.output.DataLabelOptionListOutputDto;
import com.machine.client.data.shop.IDataShopClient;
import com.machine.client.data.shop.IDataShopLabelOptionRelationClient;
import com.machine.client.data.shop.dto.output.DataShopDetailOutputDto;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.starter.web.operateLog.loader.AbstractModuleOperateLogLoad;
import com.machine.starter.web.operateLog.loader.OperationLogLoaderRegistry;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 操作日志 DATA 模块-门店 业务快照加载器注册。
 * <p>
 * 覆盖修改（update）与关系数据变更（update_label_option，携带 标签选项ID->名称）。
 */
@Slf4j
@Configuration
public class DataShopOperateLogLoad extends AbstractModuleOperateLogLoad {

    private final ObjectProvider<IDataShopClient> dataShopClientProvider;
    private final ObjectProvider<IDataShopLabelOptionRelationClient> shopLabelOptionRelationClientProvider;
    private final ObjectProvider<IDataLabelOptionClient> labelOptionClientProvider;

    public DataShopOperateLogLoad(OperationLogLoaderRegistry registry,
                                  ObjectProvider<IDataShopClient> dataShopClientProvider,
                                  ObjectProvider<IDataShopLabelOptionRelationClient> shopLabelOptionRelationClientProvider,
                                  ObjectProvider<IDataLabelOptionClient> labelOptionClientProvider) {
        super(registry);
        this.dataShopClientProvider = dataShopClientProvider;
        this.shopLabelOptionRelationClientProvider = shopLabelOptionRelationClientProvider;
        this.labelOptionClientProvider = labelOptionClientProvider;
    }

    @PostConstruct
    public void registerLoaders() {
        IDataShopClient dataShopClient = dataShopClientProvider.getIfAvailable();
        if (dataShopClient == null) {
            log.debug("未配置 IDataShopClient，跳过操作日志 DATA_SHOP 快照加载器注册");
            return;
        }
        // 修改门店：返回门店详情对象，对比工具按字段对比
        registry.register(ModuleEntityEnum.DATA_SHOP, "update",
                entityId -> loadDetail(entityId, dataShopClient::getById));

        // 修改门店标签选项：返回 门店 + 标签选项ID->名称 快照对象（关系变更，携带 name）
        IDataShopLabelOptionRelationClient shopLabelOptionRelationClient =
                shopLabelOptionRelationClientProvider.getIfAvailable();
        IDataLabelOptionClient labelOptionClient = labelOptionClientProvider.getIfAvailable();
        if (shopLabelOptionRelationClient != null && labelOptionClient != null) {
            registry.register(ModuleEntityEnum.DATA_SHOP, "updateLabelOption",
                    entityId -> loadShop4UpdateLabelOption(dataShopClient, shopLabelOptionRelationClient,
                            labelOptionClient, entityId));
        }
    }

    /**
     * 修改门店标签选项：快照为 门店 + 标签选项ID -> 标签选项名称（关系变更，携带 name）。
     */
    private ShopLabelOptionSnapshot loadShop4UpdateLabelOption(IDataShopClient shopClient,
                                                               IDataShopLabelOptionRelationClient relationClient,
                                                               IDataLabelOptionClient labelOptionClient,
                                                               Object entityId) {
        if (entityId == null) {
            return null;
        }
        DataShopDetailOutputDto detail = shopClient.getById(new IdRequest(String.valueOf(entityId)));
        if (detail == null) {
            return null;
        }
        List<String> labelOptionIdList =
                relationClient.listLabelOptionIdByShopId(new IdRequest(String.valueOf(entityId)));
        Map<String, DataLabelOptionListOutputDto> labelOptionMap = Map.of();
        if (labelOptionIdList != null && !labelOptionIdList.isEmpty()) {
            labelOptionMap = labelOptionClient.mapByIdSet(new IdSetRequest(new HashSet<>(labelOptionIdList)));
        }

        ShopLabelOptionSnapshot snapshot = new ShopLabelOptionSnapshot();
        snapshot.setId(detail.getId());
        snapshot.setName(detail.getName());
        LinkedHashMap<String, String> labelOptionIdNameMap = new LinkedHashMap<>();
        if (labelOptionIdList != null) {
            for (String labelOptionId : labelOptionIdList) {
                DataLabelOptionListOutputDto option = labelOptionMap.get(labelOptionId);
                labelOptionIdNameMap.put(labelOptionId,
                        option != null && option.getName() != null ? option.getName() : labelOptionId);
            }
        }
        snapshot.setLabelOptionIdNameMap(labelOptionIdNameMap);
        return snapshot;
    }

    @Data
    private static class ShopLabelOptionSnapshot {

        @Schema(description = "门店ID")
        private String id;

        @Schema(description = "门店名称")
        private String name;

        @Schema(description = "标签选项ID -> 标签选项名称")
        private Map<String, String> labelOptionIdNameMap;

    }

}
