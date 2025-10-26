package com.machine.client.data.supplier;

import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/supplier_company_relation",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataSupplierCompanyRelationClient {

}



