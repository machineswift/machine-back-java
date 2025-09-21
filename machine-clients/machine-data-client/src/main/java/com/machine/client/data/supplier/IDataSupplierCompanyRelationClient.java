package com.machine.client.data.supplier;

import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/supplier_company_relation",
        configuration = OpenFeignDefaultConfig.class)
public interface IDataSupplierCompanyRelationClient {

}
