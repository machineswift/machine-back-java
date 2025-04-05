DELETE
FROM `machine_iam`.`t_user`;
INSERT INTO `machine_iam`.`t_user` (`id`, `user_name`, `status`, `password`, `code`, `phone`, `name`, `gender`,
                                    `description`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('root', 'root', 'ENABLE', '{noop}123456', 'ROOT', '110', '超级管理员', 'UNDEFINED', '', 'system', 1729736640000,
        'system', 1729736640000),
       ('admin', 'admin', 'ENABLE', '{noop}123456', 'ADMIN', '119', '管理员', 'UNDEFINED', '', 'system', 1729736640000,
        'system', 1729736640000),
       ('system', 'system', 'ENABLE', '{noop}123456', 'SYSTEM', '120', '系统', 'UNDEFINED', '', 'system', 1729736640000,
        'system', 1729736640000);


DELETE
FROM `machine_iam`.`t_user_type_relation`;
INSERT INTO `machine_iam`.`t_user_type_relation` (`id`, `user_id`, `user_type`, `sort`, `create_by`, `create_time`,
                                                  `update_by`, `update_time`)
VALUES ('type001', 'root', 'COMPANY', 0, 'system', 1729736640000, 'system', 1729736640000),
       ('type002', 'admin', 'COMPANY', 0, 'system', 1729736640000, 'system', 1729736640000),
       ('type003', 'system', 'COMPANY', 0, 'system', 1729736640000, 'system', 1729736640000);


DELETE
FROM `machine_iam`.`t_organization`;
INSERT INTO `machine_iam`.`t_organization` (`id`, `parent_id`, `NAME`, `CODE`, `type`, `description`, `sort`,
                                            `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('operations', 'root', '运营', 'YWZZ202410240001', 'OPERATIONS', '运营根组织', 300, 'system', 1729736640000,
        'system', 1729736640000),
       ('marketing', 'root', '市场', 'YWZZ202410240011', 'MARKETING', '市场根组织', 100, 'system', 1729736640000,
        'system', 1729736640000),
       ('takeout', 'root', '外卖', 'YWZZ202410240111', 'TAKEOUT', '外卖根组织', 200, 'system', 1729736640000, 'system',
        1729736640000);


DELETE
FROM `machine_iam`.`t_role`;
INSERT INTO `machine_iam`.`t_role`
(`id`, `parent_id`, `code`, `status`, `type`, `NAME`, `description`, `sort`, `create_by`, `create_time`, `update_by`,
 `update_time`)
VALUES ('root', 'company', 'ROOT', 'ENABLE', 'COMPANY', '超级管理员', '超级管理员', 2834337826988, 'system',
        1729736640000, 'system', 1729736640000),
       ('admin', 'company', 'ADMIN', 'ENABLE', 'COMPANY', '管理员', '管理员', 2734337826988, 'system', 1729736640000,
        'system', 1729736640000);


DELETE
FROM `machine_iam`.`t_user_organization_relation`;
INSERT INTO `machine_iam`.`t_user_organization_relation`
(`id`, `user_id`, `organization_id`, `sort`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('rootXXX1', 'root', 'operations', 800, 'system', 1729736640000, 'system', 1729736640000),
       ('rootXXX2', 'root', 'marketing', 800, 'system', 1729736640000, 'system', 1729736640000),
       ('rootXXX3', 'root', 'takeout', 800, 'system', 1729736640000, 'system', 1729736640000),
       ('adminXXX1', 'admin', 'operations', 800, 'system', 1729736640000, 'admin', 1729736640000),
       ('adminXXX2', 'admin', 'marketing', 800, 'system', 1729736640000, 'system', 1729736640000),
       ('adminXXX', 'admin', 'takeout', 800, 'system', 1729736640000, 'system', 1729736640000);

DELETE
FROM `machine_iam`.`t_permission`;
INSERT INTO `machine_iam`.`t_permission` (`id`, `parent_id`, `status`, `resource_type`, `code`, `name`, `description`,
                                          `sort`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('machine', 'root', 'ENABLE', 'APP', 'MACHINE', 'machine', 'machine', 800, 'system', 1729736640000, 'system',
        1729736640000),

       ('manage', 'machine', 'ENABLE', 'APP', 'MANAGE', '喜管家后台', '喜管家后台', 800, 'system', 1729736640000,
        'system', 1729736640000),
       ('super_app', 'machine', 'ENABLE', 'APP', 'SUPE_APP', '喜管家APP', '喜管家APP', 400, 'system', 1729736640000,
        'system', 1729736640000),

       ('front_page', 'manage', 'ENABLE', 'MODULE', 'FRONT_PAGE', '首页', '首页', 8000, 'system', 1729736640000,
        'system', 1729736640000),
       ('main_data_center', 'manage', 'ENABLE', 'MODULE', 'MAIN_DATA_CENTER', '主数据中心', '主数据中心', 7800,
        'system', 1729736640000, 'system', 1729736640000),
       ('operations_center', 'manage', 'ENABLE', 'MODULE', 'OPERATIONS_CENTER', '运营中心', '运营中心', 7600, 'system',
        1729736640000, 'system', 1729736640000),
       ('marketing_center', 'manage', 'ENABLE', 'MODULE', 'MARKETING_CENTER', '营销中心', '营销中心', 7400, 'system',
        1729736640000, 'system', 1729736640000),
       ('pos_center', 'manage', 'ENABLE', 'MODULE', 'POS_CENTER', 'pos中心', 'pos中心', 7200, 'system', 1729736640000,
        'root', 1729736640000),
       ('financial_center', 'manage', 'ENABLE', 'MODULE', 'FINANCIAL_CENTER', '财务中心', '财务中心', 7000, 'system',
        1729736640000, 'system', 1729736640000),
       ('supply_chain_center', 'manage', 'ENABLE', 'MODULE', 'SUPPLY_CHAIN_CENTER', '供应链中心', '供应链中心', 6800,
        'system', 1729736640000, 'system', 1729736640000),
       ('report_center', 'manage', 'ENABLE', 'MODULE', 'REPORT_CENTER', '报表中心', '报表中心', 6800, 'root',
        1729736640000, 'system', 1729736640000),

       ('franchisee', 'super_app', 'ENABLE', 'MODULE', 'FRANCHISEE', '加盟商', '加盟商', 8000, 'system', 1729736640000,
        'system', 1729736640000),
       ('supplier', 'super_app', 'ENABLE', 'MODULE', 'SUPPLIER', '供应商', '供应商', 7800, 'system', 1729736640000,
        'system', 1729736640000),
       ('app_button', 'super_app', 'ENABLE', 'MODULE', 'APP_BUTTON', 'APP按钮', 'APP按钮', 7600, 'system',
        1729736640000, 'system', 1729736640000);


INSERT INTO `machine_iam`.`t_role` (`id`, `parent_id`, `code`, `status`, `type`, `NAME`, `description`, `sort`,
                                    `create_by`, `create_time`, `update_by`, `update_time`)
VALUES ('chairman', 'company', 'CHAIRMAN', 'ENABLE', 'COMPANY', '董事长', '董事长', 2634337880000, 'system',
        1729736640000, 'system', 1729736640000),
       ('vice_president', 'company', 'VICE_PRESIDENT', 'ENABLE', 'COMPANY', '副总', '副总', 2634337870000, 'system',
        1729736640000, 'system', 1729736640000),
       ('general_manager', 'company', 'GENERAL_MANAGER', 'ENABLE', 'COMPANY', '总经理', '总经理', 2634337860000,
        'system', 1729736640000, 'system', 1729736640000),
       ('assistant_manager', 'company', 'ASSISTANT_MANAGER', 'ENABLE', 'COMPANY', '副经理', '副经理', 2634337850000,
        'system', 1729736640000, 'system', 1729736640000),
       ('director', 'company', 'DIRECTOR', 'ENABLE', 'COMPANY', '总监', '总监', 2634337840000, 'system', 1729736640000,
        'system', 1729736640000),
       ('deputy_director', 'company', 'DEPUTY_DIRECTOR', 'ENABLE', 'COMPANY', '副总监', '副总监', 2634337830000,
        'system', 1729736640000, 'system', 1729736640000),
       ('regional_director', 'company', 'REGIONAL_DIRECTOR', 'ENABLE', 'COMPANY', '大区总监', '大区总监', 2634337826000,
        'system', 1729736640000, 'system', 1729736640000),
       ('branch_manager', 'company', 'BRANCH_MANAGER', 'ENABLE', 'COMPANY', '分公司负责人', '分公司负责人',
        2634337820000, 'system', 1729736640000, 'system', 1729736640000),
       ('area_manager', 'company', 'AREA_MANAGER', 'ENABLE', 'COMPANY', '区域经理', '区域经理', 2634337800000, 'system',
        1729736640000, 'system', 1729736640000),
       ('manager', 'company', 'MANAGER', 'ENABLE', 'COMPANY', '经理', '经理', 2634337790000, 'system', 1729736640000,
        'system', 1729736640000),
       ('supervisor', 'company', 'SUPERVISOR', 'ENABLE', 'COMPANY', '主管', '主管', 2634337780000, 'system',
        1729736640000, 'system', 1729736640000),
       ('inspector', 'company', 'INSPECTOR', 'ENABLE', 'COMPANY', '督导', '督导', 2634337770000, 'system',
        1729736640000, 'system', 1729736640000),
       ('designer', 'company', 'DESIGNER', 'ENABLE', 'COMPANY', '设计师', '设计师', 2634337768000, 'system',
        1729736640000, 'system', 1729736640000),
       ('engineer', 'company', 'ENGINEER', 'ENABLE', 'COMPANY', '工程师', '工程师', 2634337766000, 'system',
        1729736640000, 'system', 1729736640000),
       ('specialist', 'company', 'SPECIALIST', 'ENABLE', 'COMPANY', '专员', '专员', 2634337740000, 'system',
        1729736640000, 'system', 1729736640000),
       ('franchisee', 'shop', 'FRANCHISEE', 'ENABLE', 'SHOP', '加盟商', '加盟商', 2634337730000, 'system',
        1729736640000, 'system', 1729736640000),
       ('store_manager', 'shop', 'STORE_MANAGER', 'ENABLE', 'SHOP', '店长', '店长', 2634337720000, 'system',
        1729736640000, 'system', 1729736640000),
       ('sales_clerk', 'shop', 'SALES_CLERK', 'ENABLE', 'SHOP', '店员', '店员', 2634337710000, 'system', 1729736640000,
        'system', 1729736640000),
       ('supplier', 'supplier', 'SUPPLIER', 'ENABLE', 'SUPPLIER', '供应商', '供应商', 2634337700000, 'system',
        1729736640000, 'system', 1729736640000),
       ('employee', 'supplier', 'EMPLOYEE', 'ENABLE', 'SUPPLIER', '员工', '员工', 2634337690000, 'system',
        1729736640000, 'system', 1729736640000);