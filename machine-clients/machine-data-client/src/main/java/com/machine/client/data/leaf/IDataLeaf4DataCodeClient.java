package com.machine.client.data.leaf;

import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/leaf_4_data",
        configuration = OpenFeignDefaultConfig.class)
public interface IDataLeaf4DataCodeClient {

    /**
     * 品牌编码
     */
    @GetMapping("brand_code")
    String brandCode();


    /**
     * 素材分类编码
     */
    @GetMapping("material_category_code")
    String materialCategoryCode();

    /**
     * 附件分类编码
     */
    @GetMapping("attachment_category_code")
    String attachmentCategoryCode();

    /**
     * 门店编码
     */
    @GetMapping("shop_code")
    String shopCode();

    /**
     * 标签编码（手动标签和人工标签编码）
     */
    @GetMapping("label_code")
    String labelCode();

    /**
     * 供应商公司编码b编码
     */
    @GetMapping("supplier_company_code")
    String supplierCompanyCode();

}
