package com.machine.client.data.leaf;

import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/leaf_4_code", configuration = OpenFeignDefaultConfig.class)
public interface IDataLeaf4CodeClient {

    /**
     * 业务组织编码
     */
    @GetMapping("iam_organization_ywzz")
    String iamOrganizationYwbm();

    /**
     * 公司角色编码
     */
    @GetMapping("iam_role_gs")
    String iamRoleGs();

    /**
     * 门店角色编码
     */
    @GetMapping("iam_role_md")
    String iamRoleMd();

    /**
     * 供应商角色编码
     */
    @GetMapping("iam_role_gys")
    String iamRoleGys();

    /**
     * 门店员工编码
     */
    @GetMapping("iam_user_mdyg")
    String iamUserMdyg();

    /**
     * 供应商用户编码
     */
    @GetMapping("iam_user_gysyg")
    String iamUserGysyg();

    /**
     * 加盟商用户编码
     */
    @GetMapping("iam_user_jmsyg")
    String iamUserJmsyg();

    /**
     * 标签编码（手动标签和人工标签编码）
     */
    @GetMapping("data_biaoQian_bq")
    String dataBiaoQianBq();

    /**
     * 供应商公司编码
     */
    @GetMapping("data_supplierCompany_gysgs")
    String dataSupplierCompanyGysgs();

}
