DELETE FROM t_iam_user;
INSERT INTO t_iam_user (id, username, status, password, code, phone, name, gender,
                        description, create_by, create_time, update_by, update_time)
VALUES ('root', 'root', 'ENABLE', '{noop}123456', 'ROOT', '110', '超级管理员', 'UNDEFINED', '', 'system', 1729736640000,
        'system', 1729736640000),
       ('admin', 'admin', 'ENABLE', '{noop}123456', 'ADMIN', '119', '管理员', 'UNDEFINED', '', 'system', 1729736640000,
        'system', 1729736640000),
       ('system', 'system', 'ENABLE', '{noop}123456', 'SYSTEM', '120', '系统', 'UNDEFINED', '', 'system', 1729736640000,
        'system', 1729736640000);


DELETE FROM t_iam_user_type_relation;
INSERT INTO t_iam_user_type_relation (id, user_id, user_type, sort, create_by, create_time,
                                      update_by, update_time)
VALUES ('type001', 'root', 'COMPANY', 0, 'system', 1729736640000, 'system', 1729736640000),
       ('type002', 'admin', 'COMPANY', 0, 'system', 1729736640000, 'system', 1729736640000),
       ('type003', 'system', 'COMPANY', 0, 'system', 1729736640000, 'system', 1729736640000);


DELETE FROM t_iam_organization;
INSERT INTO t_iam_organization (id, parent_id, name, code, type, description, sort,
                                create_by, create_time, update_by, update_time)
VALUES ('operations', 'root', '运营', 'YWZZ202410240001', 'OPERATIONS', '运营根组织', 300, 'system', 1729736640000,
        'system', 1729736640000),
       ('marketing', 'root', '市场', 'YWZZ202410240011', 'MARKETING', '市场根组织', 100, 'system', 1729736640000,
        'system', 1729736640000),
       ('takeout', 'root', '外卖', 'YWZZ202410240111', 'TAKEOUT', '外卖根组织', 200, 'system', 1729736640000, 'system',
        1729736640000);


DELETE FROM t_iam_role;
INSERT INTO t_iam_role
(id, parent_id, code, status, type, name, description, sort, create_by, create_time, update_by,
 update_time)
VALUES ('root', 'company', 'ROOT', 'ENABLE', 'COMPANY', '超级管理员', '超级管理员', 2834337826988, 'system',
        1729736640000, 'system', 1729736640000),
       ('admin', 'company', 'ADMIN', 'ENABLE', 'COMPANY', '管理员', '管理员', 2734337826988, 'system', 1729736640000,
        'system', 1729736640000);


DELETE FROM t_iam_user_organization_relation;
INSERT INTO t_iam_user_organization_relation
(id, user_id, organization_id, organization_type, sort, create_by, create_time, update_by,
 update_time)
VALUES ('rootXXX1', 'root', 'operations', 'OPERATIONS', 800, 'system', 1729736640000, 'system', 1729736640000),
       ('rootXXX2', 'root', 'marketing', 'MARKETING', 800, 'system', 1729736640000, 'system', 1729736640000),
       ('rootXXX3', 'root', 'takeout', 'TAKEOUT', 800, 'system', 1729736640000, 'system', 1729736640000),
       ('adminXXX1', 'admin', 'operations', 'OPERATIONS', 800, 'system', 1729736640000, 'admin', 1729736640000),
       ('adminXXX2', 'admin', 'marketing', 'MARKETING', 800, 'system', 1729736640000, 'system', 1729736640000),
       ('adminXXX', 'admin', 'takeout', 'TAKEOUT', 800, 'system', 1729736640000, 'system', 1729736640000);

DELETE FROM t_iam_permission;
INSERT INTO t_iam_permission (id, parent_id, resource_type, code, name,
                              description, sort, create_by, create_time, update_by, update_time)
VALUES ('machine', 'root', 'APP', 'MACHINE', 'machine', 'machine', 800, 'system', 1729736640000, 'system',
        1729736640000),


       ('manage_app', 'machine', 'APP', 'MANAGE_APP', '后台管理', '后台管理', 800, 'system',
        1729736640000,
        'system', 1729736640000),
       ('super_app', 'machine', 'APP', 'SUPE_APP', '超级APP', '超级APP', 400, 'system', 1729736640000,
        'system', 1729736640000),

       ('system', 'manage_app', 'MODULE', 'SYSTEM', '系统管理', '系统管理', 9000, 'system',
        1729736640000, 'system', 1729736640000),
       ('fm', 'manage_app', 'MODULE', 'FM', '财务管理', '财务管理', 8000, 'system',
        1729736640000, 'system', 1729736640000),
       ('hrm', 'manage_app', 'MODULE', 'HRM', '人力资源', '人力资源', 7000, 'system',
        1729736640000, 'system', 1729736640000),
       ('scm', 'manage_app', 'MODULE', 'SCM', '供应链', '供应链', 6000, 'system',
        1729736640000, 'system', 1729736640000),
       ('crm', 'manage_app', 'MODULE', 'CRM', '客户关系', '客户关系', 4000, 'system',
        1729736640000, 'system', 1729736640000),
       ('bi', 'manage_app', 'MODULE', 'BI', '商业智能', '商业智能', 2000, 'root',
        1729736640000, 'system', 1729736640000),
       ('process_center', 'manage_app', 'MODULE', 'PCE', '流程中心', '流程中心', 2000, 'root',
        1729736640000, 'system', 1729736640000),

       ('system:basic_data', 'system', 'DIRECTORY', 'SYSTEM:BASIC_DATA', '基础数据管理', '基础数据管理',
        8000, 'system',
        1729736640000, 'system', 1729736640000),
       ('system:auth', 'system', 'DIRECTORY', 'SYSTEM:AUTH', '权限管理', '权限管理', 1000, 'system',
        1729736640000, 'system', 1729736640000),

       ('scm:item', 'scm', 'DIRECTORY', 'SCM:ITEM', '商品管理', '商品管理', 2000, 'system',
        1729736640000, 'system', 1729736640000),

       ('bi:dashboard', 'bi', 'MENU', 'BI:DASHBOARD', '智能看板', '智能看板', 8000, 'system',
        1729736640000, 'system', 1729736640000)
;


INSERT INTO t_iam_permission (id, parent_id, resource_type, code, name,
                              description, sort, create_by, create_time, update_by, update_time)
VALUES ('scm:item:sku', 'scm:item', 'MENU', 'SCM:ITEM:SKU', 'SKU管理', 'SKU管理', 8000,
        'root', 1729736640000, 'system', 1729736640000),
       ('scm:item:spu', 'scm:item', 'MENU', 'SCM:ITEM:SPU', 'SPU管理', 'SPU管理', 7000,
        'root', 1729736640000, 'system', 1729736640000),
       ('scm:item:attribute', 'scm:item', 'MENU', 'SCM:ITEM:ATTRIBUTE', '商品属性',
        '商品属性', 6000, 'root', 1729736640000, 'system', 1729736640000)
;


INSERT INTO t_iam_permission (id, parent_id, resource_type, code, name,
                              description, sort, create_by, create_time, update_by, update_time)
VALUES ('system:basic_data:brand', 'system:basic_data', 'MENU', 'SYSTEM:BASIC_DATA:BRAND', '品牌管理',
        '品牌管理', 8000, 'root', 1729736640000, 'system', 1729736640000)
;


INSERT INTO t_iam_permission (id, parent_id, resource_type, code, name,
                              description, sort, create_by, create_time, update_by, update_time)
VALUES ('system:auth:user', 'system:auth', 'MENU', 'SYSTEM:AUTH:USER', '用户管理', '用户管理', 8000,
        'root', 1729736640000, 'system', 1729736640000),
       ('system:auth:role', 'system:auth', 'MENU', 'SYSTEM:AUTH:ROLE', '角色管理', '角色管理', 7000,
        'root', 1729736640000, 'system', 1729736640000),
       ('system:auth:permission', 'system:auth', 'MENU', 'SYSTEM:AUTH:PERMISSION', '菜单管理',
        '菜单管理', 6000, 'root', 1729736640000, 'system', 1729736640000),
       ('system:auth:organization', 'system:auth', 'MENU', 'SYSTEM:AUTH:ORGANIZATION', '组织管理',
        '组织管理', 5000, 'root', 1729736640000, 'system', 1729736640000),
       ('system:auth:operation_log', 'system:auth', 'MENU', 'SYSTEM:AUTH:OPERATION_LOG', '操作日志',
        '操作日志', 1200, 'root', 1729736640000, 'system', 1729736640000),
       ('system:auth:login_log', 'system:auth', 'MENU', 'SYSTEM:AUTH:LOGIN_LOG', '登录日志',
        '登录日志', 1000, 'root', 1729736640000, 'system', 1729736640000)
;


INSERT INTO t_iam_role (id, parent_id, code, status, type, name, description, sort,
                        create_by, create_time, update_by, update_time)
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