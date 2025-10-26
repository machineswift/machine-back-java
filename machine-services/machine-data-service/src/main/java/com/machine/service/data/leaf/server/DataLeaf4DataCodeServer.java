package com.machine.service.data.leaf.server;

import com.machine.client.data.leaf.IDataLeaf4DataCodeClient;
import com.machine.service.data.leaf.service.IDataLeafService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.machine.sdk.common.constant.CommonDataConstant.Label.LABEL_PREFIX;

@Slf4j
@RestController
@RequestMapping("server/data/leaf_4_data")
public class DataLeaf4DataCodeServer implements IDataLeaf4DataCodeClient {

    private static final int DEFAULT_LENGTH = 4;
    private static final int DEFAULT_STEP = 8;

    @Autowired
    private IDataLeafService leafService;

    @Override
    @GetMapping("brand_code")
    public String brandCode() {
        return generateCode(CodePrefix.DATA_BRAND);
    }

    @Override
    @GetMapping("material_category_code")
    public String materialCategoryCode() {
        return generateCode(CodePrefix.DATA_MATERIAL_CATEGORY);
    }

    @Override
    @GetMapping("attachment_category_code")
    public String attachmentCategoryCode() {
        return generateCode(CodePrefix.DATA_ATTACHMENT_CATEGORY);
    }

    @Override
    @GetMapping("tag_category_code")
    public String tagCategoryCode() {
        return generateCode(CodePrefix.DATA_TAG_CATEGORY);
    }

    @Override
    @GetMapping("shop_code")
    public String shopCode() {
        return generateCode(CodePrefix.DATA_BRAND);
    }

    @Override
    @GetMapping("label_code")
    public String labelCode() {
        return generateCode(CodePrefix.DATA_LABEL);
    }

    @Override
    @GetMapping("supplier_company_code")
    public String supplierCompanyCode() {
        return generateCode(CodePrefix.DATA_SUPPLIER_COMPANY);
    }

    @Override
    @GetMapping("tag_code")
    public String tagCode() {
        return generateCode(CodePrefix.DATA_TAG);
    }

    private String generateCode(CodePrefix codePrefix) {
        return leafService.getKqBatchNo(
                codePrefix.code,
                codePrefix.prefix,
                DEFAULT_LENGTH,
                DEFAULT_STEP,
                codePrefix.description
        );
    }

    private enum CodePrefix {
        DATA_BRAND("DATA_BRAND_CODE_", "B", "品牌编码"),
        DATA_MATERIAL_CATEGORY("DATA_MATERIAL_CATEGORY_CODE_", "MC", "素材分类编码"),
        DATA_ATTACHMENT_CATEGORY("DATA_ATTACHMENT_CATEGORY_CODE_", "AC", "附件分类编码"),
        DATA_TAG_CATEGORY("DATA_TAG_CATEGORY_CODE_", "TC", "智能标签分类编码"),
        DATA_TAG("DATA_TAG_CODE_", "T", "标签编码"),
        DATA_SHOP("DATA_SHOP_CODE_", "S", "门店编码"),
        DATA_LABEL("DATA_LABEL_CODE_", LABEL_PREFIX, "标签编码"),
        DATA_SUPPLIER_COMPANY("DATA_SUPPLIER_COMPANY_CODE_", "SC", "供应商公司编码");

        private final String code;
        private final String prefix;
        private final String description;

        CodePrefix(String code, String prefix,
                   String description) {
            this.code = code;
            this.prefix = prefix;
            this.description = description;
        }
    }
}
