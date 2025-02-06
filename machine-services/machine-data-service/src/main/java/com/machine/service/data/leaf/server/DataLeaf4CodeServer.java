package com.machine.service.data.leaf.server;

import com.machine.client.data.leaf.IDataLeaf4CodeClient;
import com.machine.service.data.leaf.service.ILeafService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.machine.sdk.common.constant.CommonConstant.Shop.BIAO_QIAN_PREFIX;

@Slf4j
@RestController
@RequestMapping("server/data/leaf_4_code")
public class DataLeaf4CodeServer implements IDataLeaf4CodeClient {

    private static final int DEFAULT_LENGTH = 4;
    private static final int DEFAULT_STEP = 5;

    @Autowired
    private ILeafService leafService;

    @Override
    @GetMapping("iam_organization_ywzz")
    public String iamOrganizationYwbm() {
        return generateCode(CodePrefix.IAM_ORGANIZATION);
    }

    @Override
    @GetMapping("iam_role_gs")
    public String iamRoleGs() {
        return generateCode(CodePrefix.IAM_ROLE_GS);
    }

    @Override
    @GetMapping("iam_role_md")
    public String iamRoleMd() {
        return generateCode(CodePrefix.IAM_ROLE_MD);
    }

    @Override
    @GetMapping("iam_role_gys")
    public String iamRoleGys() {
        return generateCode(CodePrefix.IAM_ROLE_GYS);
    }

    @Override
    @GetMapping("iam_user_mdyg")
    public String iamUserMdyg() {
        return generateCode(CodePrefix.IAM_USER_MDYG);
    }

    @Override
    @GetMapping("iam_user_gysyg")
    public String iamUserGysyg() {
        return generateCode(CodePrefix.IAM_USER_GYSYG);
    }

    @Override
    @GetMapping("iam_user_jmsyg")
    public String iamUserJmsyg() {
        return generateCode(CodePrefix.IAM_USER_JMSYG);
    }

    @Override
    @GetMapping("data_biaoQian_bq")
    public String dataBiaoQianBq() {
        return generateCode(CodePrefix.DATA_BIAO_QIAN);
    }

    @Override
    @GetMapping("data_supplierCompany_gysgs")
    public String dataSupplierCompanyGysgs() {
        return generateCode(CodePrefix.DATA_SUPPLIER_COMPANY);
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
        IAM_ORGANIZATION("CODE_IAM_ORGANIZATION_", "YWZZ", "业务组织编码"),
        IAM_ROLE_GS("CODE_IAM_ROLE_", "GS", "公司角色编码"),
        IAM_ROLE_MD("CODE_IAM_ROLE_", "MD", "门店角色编码"),
        IAM_ROLE_GYS("CODE_IAM_ROLE_", "GYS", "供应商角色编码"),
        IAM_USER_MDYG("CODE_IAM_USER_", "MDYG", "门店员工编码"),
        IAM_USER_GYSYG("CODE_IAM_USER_", "GYSYG", "供应商编码"),
        IAM_USER_JMSYG("CODE_IAM_USER_", "JMSYG", "加盟商编码"),
        DATA_BIAO_QIAN("CODE_DATA_BIAO_QIAN_", BIAO_QIAN_PREFIX, "标签编码"),
        DATA_SUPPLIER_COMPANY("CODE_DATA_SUPPLIER_COMPANY_", "GYSGS", "供应商公司编码");

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
