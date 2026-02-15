INSERT INTO t_data_system_config (id, category, code, content, create_by, create_time, update_by, update_time)
VALUES ('bei_sen_root_department_id', 'BEI_SEN', 'ROOT_DEPARTMENT_ID', '900609093', 'system', 1729736640000, 'system',
        1729736640000);

INSERT INTO t_data_label_category (id, parent_id, code, sort, name, create_by, create_time, update_by, update_time)
VALUES ('shop_label_category', 'root', 'SHOP_LABEL_CATEGORY', 1, '门店', 'system', 1729736640000, 'system',
        1729736640000);

INSERT INTO t_data_label (id, category_id, code, sort, name, create_by, create_time, update_by, update_time)
VALUES ('shop_label', 'shop_label_category', 'SHOP_LABEL', 0, '门店', 'system', 1729736640000, 'system', 1729736640000);


DELETE
FROM t_data_company_employee;
INSERT INTO t_data_company_employee (id, user_id, employee_status, create_by, create_time, update_by, update_time)
VALUES ('employee_root', 'root', 'FULL_TIME', 'system', 1729736640000, 'system', 1729736640000),
       ('employee_admin', 'admin', 'FULL_TIME', 'system', 1729736640000, 'system', 1729736640000),
       ('employee_system', 'system', 'FULL_TIME', 'system', 1729736640000, 'system', 1729736640000);


DELETE
FROM t_data_material_category;
INSERT INTO t_data_material_category (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('material_category', 'root', 'MC202505010001', '素材分类', 0, 'system', 1729736640000, 'system', 1729736640000);

DELETE
FROM t_data_attachment_category;
INSERT INTO t_data_attachment_category (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('attachment_category', 'root', 'AC202505010001', '附件分类', 0, 'system', 1729736640000, 'system',
        1729736640000);


DELETE FROM t_data_tag_category;
INSERT INTO t_data_tag_category (id, parent_id, name, code, type, sort, description,
                                 create_by, create_time, update_by, update_time)
VALUES ('customer', 'root', '客户', 'TC202410240001', 'CUSTOMER', 300, '客户根组织',
        'system', 1729736640000, 'system', 1729736640000),
       ('shop', 'root', '门店', 'TC202410240011', 'SHOP', 100, '门店根组织',
        'system', 1729736640000, 'system', 1729736640000),
       ('item', 'root', '商品', 'TC202410240111', 'ITEM', 200, '商品根组织',
        'system', 1729736640000, 'system', 1729736640000);
/*************************************************************************************************/
/**************************************** 省市区信息 ***********************************************/
/*************************************************************************************************/

INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('CHINA', 'root', 'CHINA', '中国', 0, 'system', 1729736640000, 'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('AMERICAN', 'root', 'AMERICAN', '美国', 0, 'system', 1729736640000, 'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('110000000000', 'CHINA', '110000000000', '北京市', 110000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('110100000000', '110000000000', '110100000000', '北京市', 110100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('110101000000', '110100000000', '110101000000', '东城区', 110101000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('110102000000', '110100000000', '110102000000', '西城区', 110102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('110105000000', '110100000000', '110105000000', '朝阳区', 110105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('110106000000', '110100000000', '110106000000', '丰台区', 110106000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('110107000000', '110100000000', '110107000000', '石景山区', 110107000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('110108000000', '110100000000', '110108000000', '海淀区', 110108000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('110109000000', '110100000000', '110109000000', '门头沟区', 110109000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('110111000000', '110100000000', '110111000000', '房山区', 110111000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('110112000000', '110100000000', '110112000000', '通州区', 110112000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('110113000000', '110100000000', '110113000000', '顺义区', 110113000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('110114000000', '110100000000', '110114000000', '昌平区', 110114000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('110115000000', '110100000000', '110115000000', '大兴区', 110115000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('110116000000', '110100000000', '110116000000', '怀柔区', 110116000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('110117000000', '110100000000', '110117000000', '平谷区', 110117000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('110118000000', '110100000000', '110118000000', '密云区', 110118000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('110119000000', '110100000000', '110119000000', '延庆区', 110119000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('120000000000', 'CHINA', '120000000000', '天津市', 120000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('120100000000', '120000000000', '120100000000', '天津市', 120100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('120101000000', '120100000000', '120101000000', '和平区', 120101000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('120102000000', '120100000000', '120102000000', '河东区', 120102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('120103000000', '120100000000', '120103000000', '河西区', 120103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('120104000000', '120100000000', '120104000000', '南开区', 120104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('120105000000', '120100000000', '120105000000', '河北区', 120105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('120106000000', '120100000000', '120106000000', '红桥区', 120106000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('120110000000', '120100000000', '120110000000', '东丽区', 120110000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('120111000000', '120100000000', '120111000000', '西青区', 120111000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('120112000000', '120100000000', '120112000000', '津南区', 120112000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('120113000000', '120100000000', '120113000000', '北辰区', 120113000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('120114000000', '120100000000', '120114000000', '武清区', 120114000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('120115000000', '120100000000', '120115000000', '宝坻区', 120115000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('120116000000', '120100000000', '120116000000', '滨海新区', 120116000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('120117000000', '120100000000', '120117000000', '宁河区', 120117000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('120118000000', '120100000000', '120118000000', '静海区', 120118000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('120119000000', '120100000000', '120119000000', '蓟州区', 120119000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130000000000', 'CHINA', '130000000000', '河北省', 130000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130100000000', '130000000000', '130100000000', '石家庄市', 130100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130102000000', '130100000000', '130102000000', '长安区', 130102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130104000000', '130100000000', '130104000000', '桥西区', 130104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130105000000', '130100000000', '130105000000', '新华区', 130105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130107000000', '130100000000', '130107000000', '井陉矿区', 130107000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130108000000', '130100000000', '130108000000', '裕华区', 130108000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130109000000', '130100000000', '130109000000', '藁城区', 130109000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130110000000', '130100000000', '130110000000', '鹿泉区', 130110000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130111000000', '130100000000', '130111000000', '栾城区', 130111000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130121000000', '130100000000', '130121000000', '井陉县', 130121000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130123000000', '130100000000', '130123000000', '正定县', 130123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130125000000', '130100000000', '130125000000', '行唐县', 130125000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130126000000', '130100000000', '130126000000', '灵寿县', 130126000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130127000000', '130100000000', '130127000000', '高邑县', 130127000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130128000000', '130100000000', '130128000000', '深泽县', 130128000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130129000000', '130100000000', '130129000000', '赞皇县', 130129000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130130000000', '130100000000', '130130000000', '无极县', 130130000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130131000000', '130100000000', '130131000000', '平山县', 130131000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130132000000', '130100000000', '130132000000', '元氏县', 130132000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130133000000', '130100000000', '130133000000', '赵县', 130133000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130171000000', '130100000000', '130171000000', '石家庄高新技术产业开发区', 130171000000, 'system',
        1729736640000, 'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130172000000', '130100000000', '130172000000', '石家庄循环化工园区', 130172000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130181000000', '130100000000', '130181000000', '辛集市', 130181000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130183000000', '130100000000', '130183000000', '晋州市', 130183000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130184000000', '130100000000', '130184000000', '新乐市', 130184000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130200000000', '130000000000', '130200000000', '唐山市', 130200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130202000000', '130200000000', '130202000000', '路南区', 130202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130203000000', '130200000000', '130203000000', '路北区', 130203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130204000000', '130200000000', '130204000000', '古冶区', 130204000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130205000000', '130200000000', '130205000000', '开平区', 130205000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130207000000', '130200000000', '130207000000', '丰南区', 130207000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130208000000', '130200000000', '130208000000', '丰润区', 130208000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130209000000', '130200000000', '130209000000', '曹妃甸区', 130209000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130224000000', '130200000000', '130224000000', '滦南县', 130224000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130225000000', '130200000000', '130225000000', '乐亭县', 130225000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130227000000', '130200000000', '130227000000', '迁西县', 130227000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130229000000', '130200000000', '130229000000', '玉田县', 130229000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130271000000', '130200000000', '130271000000', '河北唐山芦台经济开发区', 130271000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130272000000', '130200000000', '130272000000', '唐山市汉沽管理区', 130272000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130273000000', '130200000000', '130273000000', '唐山高新技术产业开发区', 130273000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130274000000', '130200000000', '130274000000', '河北唐山海港经济开发区', 130274000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130281000000', '130200000000', '130281000000', '遵化市', 130281000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130283000000', '130200000000', '130283000000', '迁安市', 130283000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130284000000', '130200000000', '130284000000', '滦州市', 130284000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130300000000', '130000000000', '130300000000', '秦皇岛市', 130300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130302000000', '130300000000', '130302000000', '海港区', 130302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130303000000', '130300000000', '130303000000', '山海关区', 130303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130304000000', '130300000000', '130304000000', '北戴河区', 130304000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130306000000', '130300000000', '130306000000', '抚宁区', 130306000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130321000000', '130300000000', '130321000000', '青龙满族自治县', 130321000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130322000000', '130300000000', '130322000000', '昌黎县', 130322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130324000000', '130300000000', '130324000000', '卢龙县', 130324000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130371000000', '130300000000', '130371000000', '秦皇岛市经济技术开发区', 130371000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130372000000', '130300000000', '130372000000', '北戴河新区', 130372000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130400000000', '130000000000', '130400000000', '邯郸市', 130400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130402000000', '130400000000', '130402000000', '邯山区', 130402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130403000000', '130400000000', '130403000000', '丛台区', 130403000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130404000000', '130400000000', '130404000000', '复兴区', 130404000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130406000000', '130400000000', '130406000000', '峰峰矿区', 130406000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130407000000', '130400000000', '130407000000', '肥乡区', 130407000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130408000000', '130400000000', '130408000000', '永年区', 130408000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130423000000', '130400000000', '130423000000', '临漳县', 130423000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130424000000', '130400000000', '130424000000', '成安县', 130424000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130425000000', '130400000000', '130425000000', '大名县', 130425000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130426000000', '130400000000', '130426000000', '涉县', 130426000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130427000000', '130400000000', '130427000000', '磁县', 130427000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130430000000', '130400000000', '130430000000', '邱县', 130430000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130431000000', '130400000000', '130431000000', '鸡泽县', 130431000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130432000000', '130400000000', '130432000000', '广平县', 130432000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130433000000', '130400000000', '130433000000', '馆陶县', 130433000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130434000000', '130400000000', '130434000000', '魏县', 130434000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130435000000', '130400000000', '130435000000', '曲周县', 130435000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130471000000', '130400000000', '130471000000', '邯郸经济技术开发区', 130471000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130473000000', '130400000000', '130473000000', '邯郸冀南新区', 130473000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130481000000', '130400000000', '130481000000', '武安市', 130481000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130500000000', '130000000000', '130500000000', '邢台市', 130500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130502000000', '130500000000', '130502000000', '襄都区', 130502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130503000000', '130500000000', '130503000000', '信都区', 130503000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130505000000', '130500000000', '130505000000', '任泽区', 130505000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130506000000', '130500000000', '130506000000', '南和区', 130506000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130522000000', '130500000000', '130522000000', '临城县', 130522000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130523000000', '130500000000', '130523000000', '内丘县', 130523000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130524000000', '130500000000', '130524000000', '柏乡县', 130524000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130525000000', '130500000000', '130525000000', '隆尧县', 130525000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130528000000', '130500000000', '130528000000', '宁晋县', 130528000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130529000000', '130500000000', '130529000000', '巨鹿县', 130529000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130530000000', '130500000000', '130530000000', '新河县', 130530000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130531000000', '130500000000', '130531000000', '广宗县', 130531000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130532000000', '130500000000', '130532000000', '平乡县', 130532000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130533000000', '130500000000', '130533000000', '威县', 130533000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130534000000', '130500000000', '130534000000', '清河县', 130534000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130535000000', '130500000000', '130535000000', '临西县', 130535000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130571000000', '130500000000', '130571000000', '河北邢台经济开发区', 130571000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130581000000', '130500000000', '130581000000', '南宫市', 130581000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130582000000', '130500000000', '130582000000', '沙河市', 130582000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130600000000', '130000000000', '130600000000', '保定市', 130600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130602000000', '130600000000', '130602000000', '竞秀区', 130602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130606000000', '130600000000', '130606000000', '莲池区', 130606000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130607000000', '130600000000', '130607000000', '满城区', 130607000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130608000000', '130600000000', '130608000000', '清苑区', 130608000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130609000000', '130600000000', '130609000000', '徐水区', 130609000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130623000000', '130600000000', '130623000000', '涞水县', 130623000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130624000000', '130600000000', '130624000000', '阜平县', 130624000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130626000000', '130600000000', '130626000000', '定兴县', 130626000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130627000000', '130600000000', '130627000000', '唐县', 130627000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130628000000', '130600000000', '130628000000', '高阳县', 130628000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130629000000', '130600000000', '130629000000', '容城县', 130629000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130630000000', '130600000000', '130630000000', '涞源县', 130630000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130631000000', '130600000000', '130631000000', '望都县', 130631000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130632000000', '130600000000', '130632000000', '安新县', 130632000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130633000000', '130600000000', '130633000000', '易县', 130633000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130634000000', '130600000000', '130634000000', '曲阳县', 130634000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130635000000', '130600000000', '130635000000', '蠡县', 130635000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130636000000', '130600000000', '130636000000', '顺平县', 130636000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130637000000', '130600000000', '130637000000', '博野县', 130637000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130638000000', '130600000000', '130638000000', '雄县', 130638000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130671000000', '130600000000', '130671000000', '保定高新技术产业开发区', 130671000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130672000000', '130600000000', '130672000000', '保定白沟新城', 130672000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130681000000', '130600000000', '130681000000', '涿州市', 130681000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130682000000', '130600000000', '130682000000', '定州市', 130682000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130683000000', '130600000000', '130683000000', '安国市', 130683000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130684000000', '130600000000', '130684000000', '高碑店市', 130684000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130700000000', '130000000000', '130700000000', '张家口市', 130700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130702000000', '130700000000', '130702000000', '桥东区', 130702000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130703000000', '130700000000', '130703000000', '桥西区', 130703000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130705000000', '130700000000', '130705000000', '宣化区', 130705000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130706000000', '130700000000', '130706000000', '下花园区', 130706000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130708000000', '130700000000', '130708000000', '万全区', 130708000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130709000000', '130700000000', '130709000000', '崇礼区', 130709000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130722000000', '130700000000', '130722000000', '张北县', 130722000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130723000000', '130700000000', '130723000000', '康保县', 130723000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130724000000', '130700000000', '130724000000', '沽源县', 130724000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130725000000', '130700000000', '130725000000', '尚义县', 130725000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130726000000', '130700000000', '130726000000', '蔚县', 130726000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130727000000', '130700000000', '130727000000', '阳原县', 130727000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130728000000', '130700000000', '130728000000', '怀安县', 130728000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130730000000', '130700000000', '130730000000', '怀来县', 130730000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130731000000', '130700000000', '130731000000', '涿鹿县', 130731000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130732000000', '130700000000', '130732000000', '赤城县', 130732000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130771000000', '130700000000', '130771000000', '张家口经济开发区', 130771000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130772000000', '130700000000', '130772000000', '张家口市察北管理区', 130772000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130773000000', '130700000000', '130773000000', '张家口市塞北管理区', 130773000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130800000000', '130000000000', '130800000000', '承德市', 130800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130802000000', '130800000000', '130802000000', '双桥区', 130802000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130803000000', '130800000000', '130803000000', '双滦区', 130803000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130804000000', '130800000000', '130804000000', '鹰手营子矿区', 130804000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130821000000', '130800000000', '130821000000', '承德县', 130821000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130822000000', '130800000000', '130822000000', '兴隆县', 130822000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130824000000', '130800000000', '130824000000', '滦平县', 130824000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130825000000', '130800000000', '130825000000', '隆化县', 130825000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130826000000', '130800000000', '130826000000', '丰宁满族自治县', 130826000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130827000000', '130800000000', '130827000000', '宽城满族自治县', 130827000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130828000000', '130800000000', '130828000000', '围场满族蒙古族自治县', 130828000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130871000000', '130800000000', '130871000000', '承德高新技术产业开发区', 130871000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130881000000', '130800000000', '130881000000', '平泉市', 130881000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130900000000', '130000000000', '130900000000', '沧州市', 130900000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130902000000', '130900000000', '130902000000', '新华区', 130902000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130903000000', '130900000000', '130903000000', '运河区', 130903000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130921000000', '130900000000', '130921000000', '沧县', 130921000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130922000000', '130900000000', '130922000000', '青县', 130922000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130923000000', '130900000000', '130923000000', '东光县', 130923000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130924000000', '130900000000', '130924000000', '海兴县', 130924000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130925000000', '130900000000', '130925000000', '盐山县', 130925000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130926000000', '130900000000', '130926000000', '肃宁县', 130926000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130927000000', '130900000000', '130927000000', '南皮县', 130927000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130928000000', '130900000000', '130928000000', '吴桥县', 130928000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130929000000', '130900000000', '130929000000', '献县', 130929000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130930000000', '130900000000', '130930000000', '孟村回族自治县', 130930000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130971000000', '130900000000', '130971000000', '河北沧州经济开发区', 130971000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130972000000', '130900000000', '130972000000', '沧州高新技术产业开发区', 130972000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130973000000', '130900000000', '130973000000', '沧州渤海新区', 130973000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130981000000', '130900000000', '130981000000', '泊头市', 130981000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130982000000', '130900000000', '130982000000', '任丘市', 130982000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130983000000', '130900000000', '130983000000', '黄骅市', 130983000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('130984000000', '130900000000', '130984000000', '河间市', 130984000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131000000000', '130000000000', '131000000000', '廊坊市', 131000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131002000000', '131000000000', '131002000000', '安次区', 131002000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131003000000', '131000000000', '131003000000', '广阳区', 131003000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131022000000', '131000000000', '131022000000', '固安县', 131022000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131023000000', '131000000000', '131023000000', '永清县', 131023000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131024000000', '131000000000', '131024000000', '香河县', 131024000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131025000000', '131000000000', '131025000000', '大城县', 131025000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131026000000', '131000000000', '131026000000', '文安县', 131026000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131028000000', '131000000000', '131028000000', '大厂回族自治县', 131028000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131071000000', '131000000000', '131071000000', '廊坊经济技术开发区', 131071000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131081000000', '131000000000', '131081000000', '霸州市', 131081000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131082000000', '131000000000', '131082000000', '三河市', 131082000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131100000000', '130000000000', '131100000000', '衡水市', 131100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131102000000', '131100000000', '131102000000', '桃城区', 131102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131103000000', '131100000000', '131103000000', '冀州区', 131103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131121000000', '131100000000', '131121000000', '枣强县', 131121000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131122000000', '131100000000', '131122000000', '武邑县', 131122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131123000000', '131100000000', '131123000000', '武强县', 131123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131124000000', '131100000000', '131124000000', '饶阳县', 131124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131125000000', '131100000000', '131125000000', '安平县', 131125000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131126000000', '131100000000', '131126000000', '故城县', 131126000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131127000000', '131100000000', '131127000000', '景县', 131127000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131128000000', '131100000000', '131128000000', '阜城县', 131128000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131171000000', '131100000000', '131171000000', '河北衡水高新技术产业开发区', 131171000000, 'system',
        1729736640000, 'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131172000000', '131100000000', '131172000000', '衡水滨湖新区', 131172000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('131182000000', '131100000000', '131182000000', '深州市', 131182000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('133100000000', '130000000000', '133100000000', '雄安新区', 133100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140000000000', 'CHINA', '140000000000', '山西省', 140000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140100000000', '140000000000', '140100000000', '太原市', 140100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140105000000', '140100000000', '140105000000', '小店区', 140105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140106000000', '140100000000', '140106000000', '迎泽区', 140106000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140107000000', '140100000000', '140107000000', '杏花岭区', 140107000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140108000000', '140100000000', '140108000000', '尖草坪区', 140108000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140109000000', '140100000000', '140109000000', '万柏林区', 140109000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140110000000', '140100000000', '140110000000', '晋源区', 140110000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140121000000', '140100000000', '140121000000', '清徐县', 140121000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140122000000', '140100000000', '140122000000', '阳曲县', 140122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140123000000', '140100000000', '140123000000', '娄烦县', 140123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140171000000', '140100000000', '140171000000', '山西转型综合改革示范区', 140171000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140181000000', '140100000000', '140181000000', '古交市', 140181000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140200000000', '140000000000', '140200000000', '大同市', 140200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140212000000', '140200000000', '140212000000', '新荣区', 140212000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140213000000', '140200000000', '140213000000', '平城区', 140213000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140214000000', '140200000000', '140214000000', '云冈区', 140214000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140215000000', '140200000000', '140215000000', '云州区', 140215000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140221000000', '140200000000', '140221000000', '阳高县', 140221000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140222000000', '140200000000', '140222000000', '天镇县', 140222000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140223000000', '140200000000', '140223000000', '广灵县', 140223000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140224000000', '140200000000', '140224000000', '灵丘县', 140224000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140225000000', '140200000000', '140225000000', '浑源县', 140225000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140226000000', '140200000000', '140226000000', '左云县', 140226000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140271000000', '140200000000', '140271000000', '山西大同经济开发区', 140271000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140300000000', '140000000000', '140300000000', '阳泉市', 140300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140302000000', '140300000000', '140302000000', '城区', 140302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140303000000', '140300000000', '140303000000', '矿区', 140303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140311000000', '140300000000', '140311000000', '郊区', 140311000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140321000000', '140300000000', '140321000000', '平定县', 140321000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140322000000', '140300000000', '140322000000', '盂县', 140322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140400000000', '140000000000', '140400000000', '长治市', 140400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140403000000', '140400000000', '140403000000', '潞州区', 140403000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140404000000', '140400000000', '140404000000', '上党区', 140404000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140405000000', '140400000000', '140405000000', '屯留区', 140405000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140406000000', '140400000000', '140406000000', '潞城区', 140406000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140423000000', '140400000000', '140423000000', '襄垣县', 140423000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140425000000', '140400000000', '140425000000', '平顺县', 140425000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140426000000', '140400000000', '140426000000', '黎城县', 140426000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140427000000', '140400000000', '140427000000', '壶关县', 140427000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140428000000', '140400000000', '140428000000', '长子县', 140428000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140429000000', '140400000000', '140429000000', '武乡县', 140429000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140430000000', '140400000000', '140430000000', '沁县', 140430000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140431000000', '140400000000', '140431000000', '沁源县', 140431000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140500000000', '140000000000', '140500000000', '晋城市', 140500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140502000000', '140500000000', '140502000000', '城区', 140502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140521000000', '140500000000', '140521000000', '沁水县', 140521000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140522000000', '140500000000', '140522000000', '阳城县', 140522000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140524000000', '140500000000', '140524000000', '陵川县', 140524000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140525000000', '140500000000', '140525000000', '泽州县', 140525000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140581000000', '140500000000', '140581000000', '高平市', 140581000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140600000000', '140000000000', '140600000000', '朔州市', 140600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140602000000', '140600000000', '140602000000', '朔城区', 140602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140603000000', '140600000000', '140603000000', '平鲁区', 140603000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140621000000', '140600000000', '140621000000', '山阴县', 140621000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140622000000', '140600000000', '140622000000', '应县', 140622000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140623000000', '140600000000', '140623000000', '右玉县', 140623000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140671000000', '140600000000', '140671000000', '山西朔州经济开发区', 140671000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140681000000', '140600000000', '140681000000', '怀仁市', 140681000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140700000000', '140000000000', '140700000000', '晋中市', 140700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140702000000', '140700000000', '140702000000', '榆次区', 140702000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140703000000', '140700000000', '140703000000', '太谷区', 140703000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140721000000', '140700000000', '140721000000', '榆社县', 140721000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140722000000', '140700000000', '140722000000', '左权县', 140722000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140723000000', '140700000000', '140723000000', '和顺县', 140723000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140724000000', '140700000000', '140724000000', '昔阳县', 140724000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140725000000', '140700000000', '140725000000', '寿阳县', 140725000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140727000000', '140700000000', '140727000000', '祁县', 140727000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140728000000', '140700000000', '140728000000', '平遥县', 140728000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140729000000', '140700000000', '140729000000', '灵石县', 140729000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140781000000', '140700000000', '140781000000', '介休市', 140781000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140800000000', '140000000000', '140800000000', '运城市', 140800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140802000000', '140800000000', '140802000000', '盐湖区', 140802000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140821000000', '140800000000', '140821000000', '临猗县', 140821000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140822000000', '140800000000', '140822000000', '万荣县', 140822000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140823000000', '140800000000', '140823000000', '闻喜县', 140823000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140824000000', '140800000000', '140824000000', '稷山县', 140824000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140825000000', '140800000000', '140825000000', '新绛县', 140825000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140826000000', '140800000000', '140826000000', '绛县', 140826000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140827000000', '140800000000', '140827000000', '垣曲县', 140827000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140828000000', '140800000000', '140828000000', '夏县', 140828000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140829000000', '140800000000', '140829000000', '平陆县', 140829000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140830000000', '140800000000', '140830000000', '芮城县', 140830000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140881000000', '140800000000', '140881000000', '永济市', 140881000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140882000000', '140800000000', '140882000000', '河津市', 140882000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140900000000', '140000000000', '140900000000', '忻州市', 140900000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140902000000', '140900000000', '140902000000', '忻府区', 140902000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140921000000', '140900000000', '140921000000', '定襄县', 140921000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140922000000', '140900000000', '140922000000', '五台县', 140922000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140923000000', '140900000000', '140923000000', '代县', 140923000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140924000000', '140900000000', '140924000000', '繁峙县', 140924000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140925000000', '140900000000', '140925000000', '宁武县', 140925000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140926000000', '140900000000', '140926000000', '静乐县', 140926000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140927000000', '140900000000', '140927000000', '神池县', 140927000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140928000000', '140900000000', '140928000000', '五寨县', 140928000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140929000000', '140900000000', '140929000000', '岢岚县', 140929000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140930000000', '140900000000', '140930000000', '河曲县', 140930000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140931000000', '140900000000', '140931000000', '保德县', 140931000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140932000000', '140900000000', '140932000000', '偏关县', 140932000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140971000000', '140900000000', '140971000000', '五台山风景名胜区', 140971000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('140981000000', '140900000000', '140981000000', '原平市', 140981000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141000000000', '140000000000', '141000000000', '临汾市', 141000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141002000000', '141000000000', '141002000000', '尧都区', 141002000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141021000000', '141000000000', '141021000000', '曲沃县', 141021000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141022000000', '141000000000', '141022000000', '翼城县', 141022000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141023000000', '141000000000', '141023000000', '襄汾县', 141023000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141024000000', '141000000000', '141024000000', '洪洞县', 141024000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141025000000', '141000000000', '141025000000', '古县', 141025000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141026000000', '141000000000', '141026000000', '安泽县', 141026000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141027000000', '141000000000', '141027000000', '浮山县', 141027000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141028000000', '141000000000', '141028000000', '吉县', 141028000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141029000000', '141000000000', '141029000000', '乡宁县', 141029000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141030000000', '141000000000', '141030000000', '大宁县', 141030000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141031000000', '141000000000', '141031000000', '隰县', 141031000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141032000000', '141000000000', '141032000000', '永和县', 141032000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141033000000', '141000000000', '141033000000', '蒲县', 141033000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141034000000', '141000000000', '141034000000', '汾西县', 141034000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141081000000', '141000000000', '141081000000', '侯马市', 141081000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141082000000', '141000000000', '141082000000', '霍州市', 141082000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141100000000', '140000000000', '141100000000', '吕梁市', 141100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141102000000', '141100000000', '141102000000', '离石区', 141102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141121000000', '141100000000', '141121000000', '文水县', 141121000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141122000000', '141100000000', '141122000000', '交城县', 141122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141123000000', '141100000000', '141123000000', '兴县', 141123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141124000000', '141100000000', '141124000000', '临县', 141124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141125000000', '141100000000', '141125000000', '柳林县', 141125000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141126000000', '141100000000', '141126000000', '石楼县', 141126000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141127000000', '141100000000', '141127000000', '岚县', 141127000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141128000000', '141100000000', '141128000000', '方山县', 141128000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141129000000', '141100000000', '141129000000', '中阳县', 141129000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141130000000', '141100000000', '141130000000', '交口县', 141130000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141181000000', '141100000000', '141181000000', '孝义市', 141181000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('141182000000', '141100000000', '141182000000', '汾阳市', 141182000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150000000000', 'CHINA', '150000000000', '内蒙古自治区', 150000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150100000000', '150000000000', '150100000000', '呼和浩特市', 150100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150102000000', '150100000000', '150102000000', '新城区', 150102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150103000000', '150100000000', '150103000000', '回民区', 150103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150104000000', '150100000000', '150104000000', '玉泉区', 150104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150105000000', '150100000000', '150105000000', '赛罕区', 150105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150121000000', '150100000000', '150121000000', '土默特左旗', 150121000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150122000000', '150100000000', '150122000000', '托克托县', 150122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150123000000', '150100000000', '150123000000', '和林格尔县', 150123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150124000000', '150100000000', '150124000000', '清水河县', 150124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150125000000', '150100000000', '150125000000', '武川县', 150125000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150172000000', '150100000000', '150172000000', '呼和浩特经济技术开发区', 150172000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150200000000', '150000000000', '150200000000', '包头市', 150200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150202000000', '150200000000', '150202000000', '东河区', 150202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150203000000', '150200000000', '150203000000', '昆都仑区', 150203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150204000000', '150200000000', '150204000000', '青山区', 150204000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150205000000', '150200000000', '150205000000', '石拐区', 150205000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150206000000', '150200000000', '150206000000', '白云鄂博矿区', 150206000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150207000000', '150200000000', '150207000000', '九原区', 150207000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150221000000', '150200000000', '150221000000', '土默特右旗', 150221000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150222000000', '150200000000', '150222000000', '固阳县', 150222000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150223000000', '150200000000', '150223000000', '达尔罕茂明安联合旗', 150223000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150271000000', '150200000000', '150271000000', '包头稀土高新技术产业开发区', 150271000000, 'system',
        1729736640000, 'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150300000000', '150000000000', '150300000000', '乌海市', 150300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150302000000', '150300000000', '150302000000', '海勃湾区', 150302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150303000000', '150300000000', '150303000000', '海南区', 150303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150304000000', '150300000000', '150304000000', '乌达区', 150304000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150400000000', '150000000000', '150400000000', '赤峰市', 150400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150402000000', '150400000000', '150402000000', '红山区', 150402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150403000000', '150400000000', '150403000000', '元宝山区', 150403000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150404000000', '150400000000', '150404000000', '松山区', 150404000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150421000000', '150400000000', '150421000000', '阿鲁科尔沁旗', 150421000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150422000000', '150400000000', '150422000000', '巴林左旗', 150422000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150423000000', '150400000000', '150423000000', '巴林右旗', 150423000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150424000000', '150400000000', '150424000000', '林西县', 150424000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150425000000', '150400000000', '150425000000', '克什克腾旗', 150425000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150426000000', '150400000000', '150426000000', '翁牛特旗', 150426000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150428000000', '150400000000', '150428000000', '喀喇沁旗', 150428000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150429000000', '150400000000', '150429000000', '宁城县', 150429000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150430000000', '150400000000', '150430000000', '敖汉旗', 150430000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150500000000', '150000000000', '150500000000', '通辽市', 150500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150502000000', '150500000000', '150502000000', '科尔沁区', 150502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150521000000', '150500000000', '150521000000', '科尔沁左翼中旗', 150521000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150522000000', '150500000000', '150522000000', '科尔沁左翼后旗', 150522000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150523000000', '150500000000', '150523000000', '开鲁县', 150523000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150524000000', '150500000000', '150524000000', '库伦旗', 150524000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150525000000', '150500000000', '150525000000', '奈曼旗', 150525000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150526000000', '150500000000', '150526000000', '扎鲁特旗', 150526000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150571000000', '150500000000', '150571000000', '通辽经济技术开发区', 150571000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150581000000', '150500000000', '150581000000', '霍林郭勒市', 150581000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150600000000', '150000000000', '150600000000', '鄂尔多斯市', 150600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150602000000', '150600000000', '150602000000', '东胜区', 150602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150603000000', '150600000000', '150603000000', '康巴什区', 150603000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150621000000', '150600000000', '150621000000', '达拉特旗', 150621000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150622000000', '150600000000', '150622000000', '准格尔旗', 150622000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150623000000', '150600000000', '150623000000', '鄂托克前旗', 150623000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150624000000', '150600000000', '150624000000', '鄂托克旗', 150624000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150625000000', '150600000000', '150625000000', '杭锦旗', 150625000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150626000000', '150600000000', '150626000000', '乌审旗', 150626000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150627000000', '150600000000', '150627000000', '伊金霍洛旗', 150627000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150700000000', '150000000000', '150700000000', '呼伦贝尔市', 150700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150702000000', '150700000000', '150702000000', '海拉尔区', 150702000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150703000000', '150700000000', '150703000000', '扎赉诺尔区', 150703000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150721000000', '150700000000', '150721000000', '阿荣旗', 150721000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150722000000', '150700000000', '150722000000', '莫力达瓦达斡尔族自治旗', 150722000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150723000000', '150700000000', '150723000000', '鄂伦春自治旗', 150723000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150724000000', '150700000000', '150724000000', '鄂温克族自治旗', 150724000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150725000000', '150700000000', '150725000000', '陈巴尔虎旗', 150725000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150726000000', '150700000000', '150726000000', '新巴尔虎左旗', 150726000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150727000000', '150700000000', '150727000000', '新巴尔虎右旗', 150727000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150781000000', '150700000000', '150781000000', '满洲里市', 150781000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150782000000', '150700000000', '150782000000', '牙克石市', 150782000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150783000000', '150700000000', '150783000000', '扎兰屯市', 150783000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150784000000', '150700000000', '150784000000', '额尔古纳市', 150784000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150785000000', '150700000000', '150785000000', '根河市', 150785000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150800000000', '150000000000', '150800000000', '巴彦淖尔市', 150800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150802000000', '150800000000', '150802000000', '临河区', 150802000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150821000000', '150800000000', '150821000000', '五原县', 150821000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150822000000', '150800000000', '150822000000', '磴口县', 150822000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150823000000', '150800000000', '150823000000', '乌拉特前旗', 150823000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150824000000', '150800000000', '150824000000', '乌拉特中旗', 150824000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150825000000', '150800000000', '150825000000', '乌拉特后旗', 150825000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150826000000', '150800000000', '150826000000', '杭锦后旗', 150826000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150900000000', '150000000000', '150900000000', '乌兰察布市', 150900000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150902000000', '150900000000', '150902000000', '集宁区', 150902000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150921000000', '150900000000', '150921000000', '卓资县', 150921000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150922000000', '150900000000', '150922000000', '化德县', 150922000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150923000000', '150900000000', '150923000000', '商都县', 150923000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150924000000', '150900000000', '150924000000', '兴和县', 150924000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150925000000', '150900000000', '150925000000', '凉城县', 150925000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150926000000', '150900000000', '150926000000', '察哈尔右翼前旗', 150926000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150927000000', '150900000000', '150927000000', '察哈尔右翼中旗', 150927000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150928000000', '150900000000', '150928000000', '察哈尔右翼后旗', 150928000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150929000000', '150900000000', '150929000000', '四子王旗', 150929000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('150981000000', '150900000000', '150981000000', '丰镇市', 150981000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152200000000', '150000000000', '152200000000', '兴安盟', 152200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152201000000', '152200000000', '152201000000', '乌兰浩特市', 152201000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152202000000', '152200000000', '152202000000', '阿尔山市', 152202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152221000000', '152200000000', '152221000000', '科尔沁右翼前旗', 152221000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152222000000', '152200000000', '152222000000', '科尔沁右翼中旗', 152222000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152223000000', '152200000000', '152223000000', '扎赉特旗', 152223000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152224000000', '152200000000', '152224000000', '突泉县', 152224000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152500000000', '150000000000', '152500000000', '锡林郭勒盟', 152500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152501000000', '152500000000', '152501000000', '二连浩特市', 152501000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152502000000', '152500000000', '152502000000', '锡林浩特市', 152502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152522000000', '152500000000', '152522000000', '阿巴嘎旗', 152522000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152523000000', '152500000000', '152523000000', '苏尼特左旗', 152523000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152524000000', '152500000000', '152524000000', '苏尼特右旗', 152524000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152525000000', '152500000000', '152525000000', '东乌珠穆沁旗', 152525000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152526000000', '152500000000', '152526000000', '西乌珠穆沁旗', 152526000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152527000000', '152500000000', '152527000000', '太仆寺旗', 152527000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152528000000', '152500000000', '152528000000', '镶黄旗', 152528000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152529000000', '152500000000', '152529000000', '正镶白旗', 152529000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152530000000', '152500000000', '152530000000', '正蓝旗', 152530000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152531000000', '152500000000', '152531000000', '多伦县', 152531000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152571000000', '152500000000', '152571000000', '乌拉盖管理区管委会', 152571000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152900000000', '150000000000', '152900000000', '阿拉善盟', 152900000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152921000000', '152900000000', '152921000000', '阿拉善左旗', 152921000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152922000000', '152900000000', '152922000000', '阿拉善右旗', 152922000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152923000000', '152900000000', '152923000000', '额济纳旗', 152923000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('152971000000', '152900000000', '152971000000', '内蒙古阿拉善高新技术产业开发区', 152971000000, 'system',
        1729736640000, 'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210000000000', 'CHINA', '210000000000', '辽宁省', 210000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210100000000', '210000000000', '210100000000', '沈阳市', 210100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210102000000', '210100000000', '210102000000', '和平区', 210102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210103000000', '210100000000', '210103000000', '沈河区', 210103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210104000000', '210100000000', '210104000000', '大东区', 210104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210105000000', '210100000000', '210105000000', '皇姑区', 210105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210106000000', '210100000000', '210106000000', '铁西区', 210106000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210111000000', '210100000000', '210111000000', '苏家屯区', 210111000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210112000000', '210100000000', '210112000000', '浑南区', 210112000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210113000000', '210100000000', '210113000000', '沈北新区', 210113000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210114000000', '210100000000', '210114000000', '于洪区', 210114000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210115000000', '210100000000', '210115000000', '辽中区', 210115000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210123000000', '210100000000', '210123000000', '康平县', 210123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210124000000', '210100000000', '210124000000', '法库县', 210124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210181000000', '210100000000', '210181000000', '新民市', 210181000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210200000000', '210000000000', '210200000000', '大连市', 210200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210202000000', '210200000000', '210202000000', '中山区', 210202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210203000000', '210200000000', '210203000000', '西岗区', 210203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210204000000', '210200000000', '210204000000', '沙河口区', 210204000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210211000000', '210200000000', '210211000000', '甘井子区', 210211000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210212000000', '210200000000', '210212000000', '旅顺口区', 210212000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210213000000', '210200000000', '210213000000', '金州区', 210213000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210214000000', '210200000000', '210214000000', '普兰店区', 210214000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210224000000', '210200000000', '210224000000', '长海县', 210224000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210281000000', '210200000000', '210281000000', '瓦房店市', 210281000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210283000000', '210200000000', '210283000000', '庄河市', 210283000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210300000000', '210000000000', '210300000000', '鞍山市', 210300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210302000000', '210300000000', '210302000000', '铁东区', 210302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210303000000', '210300000000', '210303000000', '铁西区', 210303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210304000000', '210300000000', '210304000000', '立山区', 210304000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210311000000', '210300000000', '210311000000', '千山区', 210311000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210321000000', '210300000000', '210321000000', '台安县', 210321000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210323000000', '210300000000', '210323000000', '岫岩满族自治县', 210323000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210381000000', '210300000000', '210381000000', '海城市', 210381000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210400000000', '210000000000', '210400000000', '抚顺市', 210400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210402000000', '210400000000', '210402000000', '新抚区', 210402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210403000000', '210400000000', '210403000000', '东洲区', 210403000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210404000000', '210400000000', '210404000000', '望花区', 210404000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210411000000', '210400000000', '210411000000', '顺城区', 210411000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210421000000', '210400000000', '210421000000', '抚顺县', 210421000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210422000000', '210400000000', '210422000000', '新宾满族自治县', 210422000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210423000000', '210400000000', '210423000000', '清原满族自治县', 210423000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210500000000', '210000000000', '210500000000', '本溪市', 210500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210502000000', '210500000000', '210502000000', '平山区', 210502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210503000000', '210500000000', '210503000000', '溪湖区', 210503000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210504000000', '210500000000', '210504000000', '明山区', 210504000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210505000000', '210500000000', '210505000000', '南芬区', 210505000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210521000000', '210500000000', '210521000000', '本溪满族自治县', 210521000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210522000000', '210500000000', '210522000000', '桓仁满族自治县', 210522000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210600000000', '210000000000', '210600000000', '丹东市', 210600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210602000000', '210600000000', '210602000000', '元宝区', 210602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210603000000', '210600000000', '210603000000', '振兴区', 210603000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210604000000', '210600000000', '210604000000', '振安区', 210604000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210624000000', '210600000000', '210624000000', '宽甸满族自治县', 210624000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210681000000', '210600000000', '210681000000', '东港市', 210681000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210682000000', '210600000000', '210682000000', '凤城市', 210682000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210700000000', '210000000000', '210700000000', '锦州市', 210700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210702000000', '210700000000', '210702000000', '古塔区', 210702000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210703000000', '210700000000', '210703000000', '凌河区', 210703000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210711000000', '210700000000', '210711000000', '太和区', 210711000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210726000000', '210700000000', '210726000000', '黑山县', 210726000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210727000000', '210700000000', '210727000000', '义县', 210727000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210781000000', '210700000000', '210781000000', '凌海市', 210781000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210782000000', '210700000000', '210782000000', '北镇市', 210782000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210800000000', '210000000000', '210800000000', '营口市', 210800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210802000000', '210800000000', '210802000000', '站前区', 210802000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210803000000', '210800000000', '210803000000', '西市区', 210803000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210804000000', '210800000000', '210804000000', '鲅鱼圈区', 210804000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210811000000', '210800000000', '210811000000', '老边区', 210811000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210881000000', '210800000000', '210881000000', '盖州市', 210881000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210882000000', '210800000000', '210882000000', '大石桥市', 210882000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210900000000', '210000000000', '210900000000', '阜新市', 210900000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210902000000', '210900000000', '210902000000', '海州区', 210902000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210903000000', '210900000000', '210903000000', '新邱区', 210903000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210904000000', '210900000000', '210904000000', '太平区', 210904000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210905000000', '210900000000', '210905000000', '清河门区', 210905000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210911000000', '210900000000', '210911000000', '细河区', 210911000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210921000000', '210900000000', '210921000000', '阜新蒙古族自治县', 210921000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('210922000000', '210900000000', '210922000000', '彰武县', 210922000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211000000000', '210000000000', '211000000000', '辽阳市', 211000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211002000000', '211000000000', '211002000000', '白塔区', 211002000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211003000000', '211000000000', '211003000000', '文圣区', 211003000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211004000000', '211000000000', '211004000000', '宏伟区', 211004000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211005000000', '211000000000', '211005000000', '弓长岭区', 211005000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211011000000', '211000000000', '211011000000', '太子河区', 211011000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211021000000', '211000000000', '211021000000', '辽阳县', 211021000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211081000000', '211000000000', '211081000000', '灯塔市', 211081000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211100000000', '210000000000', '211100000000', '盘锦市', 211100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211102000000', '211100000000', '211102000000', '双台子区', 211102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211103000000', '211100000000', '211103000000', '兴隆台区', 211103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211104000000', '211100000000', '211104000000', '大洼区', 211104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211122000000', '211100000000', '211122000000', '盘山县', 211122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211200000000', '210000000000', '211200000000', '铁岭市', 211200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211202000000', '211200000000', '211202000000', '银州区', 211202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211204000000', '211200000000', '211204000000', '清河区', 211204000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211221000000', '211200000000', '211221000000', '铁岭县', 211221000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211223000000', '211200000000', '211223000000', '西丰县', 211223000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211224000000', '211200000000', '211224000000', '昌图县', 211224000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211281000000', '211200000000', '211281000000', '调兵山市', 211281000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211282000000', '211200000000', '211282000000', '开原市', 211282000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211300000000', '210000000000', '211300000000', '朝阳市', 211300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211302000000', '211300000000', '211302000000', '双塔区', 211302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211303000000', '211300000000', '211303000000', '龙城区', 211303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211321000000', '211300000000', '211321000000', '朝阳县', 211321000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211322000000', '211300000000', '211322000000', '建平县', 211322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211324000000', '211300000000', '211324000000', '喀喇沁左翼蒙古族自治县', 211324000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211381000000', '211300000000', '211381000000', '北票市', 211381000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211382000000', '211300000000', '211382000000', '凌源市', 211382000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211400000000', '210000000000', '211400000000', '葫芦岛市', 211400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211402000000', '211400000000', '211402000000', '连山区', 211402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211403000000', '211400000000', '211403000000', '龙港区', 211403000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211404000000', '211400000000', '211404000000', '南票区', 211404000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211421000000', '211400000000', '211421000000', '绥中县', 211421000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211422000000', '211400000000', '211422000000', '建昌县', 211422000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('211481000000', '211400000000', '211481000000', '兴城市', 211481000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220000000000', 'CHINA', '220000000000', '吉林省', 220000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220100000000', '220000000000', '220100000000', '长春市', 220100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220102000000', '220100000000', '220102000000', '南关区', 220102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220103000000', '220100000000', '220103000000', '宽城区', 220103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220104000000', '220100000000', '220104000000', '朝阳区', 220104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220105000000', '220100000000', '220105000000', '二道区', 220105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220106000000', '220100000000', '220106000000', '绿园区', 220106000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220112000000', '220100000000', '220112000000', '双阳区', 220112000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220113000000', '220100000000', '220113000000', '九台区', 220113000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220122000000', '220100000000', '220122000000', '农安县', 220122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220171000000', '220100000000', '220171000000', '长春经济技术开发区', 220171000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220172000000', '220100000000', '220172000000', '长春净月高新技术产业开发区', 220172000000, 'system',
        1729736640000, 'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220173000000', '220100000000', '220173000000', '长春高新技术产业开发区', 220173000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220174000000', '220100000000', '220174000000', '长春汽车经济技术开发区', 220174000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220182000000', '220100000000', '220182000000', '榆树市', 220182000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220183000000', '220100000000', '220183000000', '德惠市', 220183000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220184000000', '220100000000', '220184000000', '公主岭市', 220184000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220200000000', '220000000000', '220200000000', '吉林市', 220200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220202000000', '220200000000', '220202000000', '昌邑区', 220202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220203000000', '220200000000', '220203000000', '龙潭区', 220203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220204000000', '220200000000', '220204000000', '船营区', 220204000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220211000000', '220200000000', '220211000000', '丰满区', 220211000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220221000000', '220200000000', '220221000000', '永吉县', 220221000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220271000000', '220200000000', '220271000000', '吉林经济开发区', 220271000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220272000000', '220200000000', '220272000000', '吉林高新技术产业开发区', 220272000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220273000000', '220200000000', '220273000000', '吉林中国新加坡食品区', 220273000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220281000000', '220200000000', '220281000000', '蛟河市', 220281000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220282000000', '220200000000', '220282000000', '桦甸市', 220282000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220283000000', '220200000000', '220283000000', '舒兰市', 220283000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220284000000', '220200000000', '220284000000', '磐石市', 220284000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220300000000', '220000000000', '220300000000', '四平市', 220300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220302000000', '220300000000', '220302000000', '铁西区', 220302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220303000000', '220300000000', '220303000000', '铁东区', 220303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220322000000', '220300000000', '220322000000', '梨树县', 220322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220323000000', '220300000000', '220323000000', '伊通满族自治县', 220323000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220382000000', '220300000000', '220382000000', '双辽市', 220382000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220400000000', '220000000000', '220400000000', '辽源市', 220400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220402000000', '220400000000', '220402000000', '龙山区', 220402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220403000000', '220400000000', '220403000000', '西安区', 220403000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220421000000', '220400000000', '220421000000', '东丰县', 220421000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220422000000', '220400000000', '220422000000', '东辽县', 220422000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220500000000', '220000000000', '220500000000', '通化市', 220500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220502000000', '220500000000', '220502000000', '东昌区', 220502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220503000000', '220500000000', '220503000000', '二道江区', 220503000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220521000000', '220500000000', '220521000000', '通化县', 220521000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220523000000', '220500000000', '220523000000', '辉南县', 220523000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220524000000', '220500000000', '220524000000', '柳河县', 220524000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220581000000', '220500000000', '220581000000', '梅河口市', 220581000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220582000000', '220500000000', '220582000000', '集安市', 220582000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220600000000', '220000000000', '220600000000', '白山市', 220600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220602000000', '220600000000', '220602000000', '浑江区', 220602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220605000000', '220600000000', '220605000000', '江源区', 220605000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220621000000', '220600000000', '220621000000', '抚松县', 220621000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220622000000', '220600000000', '220622000000', '靖宇县', 220622000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220623000000', '220600000000', '220623000000', '长白朝鲜族自治县', 220623000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220681000000', '220600000000', '220681000000', '临江市', 220681000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220700000000', '220000000000', '220700000000', '松原市', 220700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220702000000', '220700000000', '220702000000', '宁江区', 220702000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220721000000', '220700000000', '220721000000', '前郭尔罗斯蒙古族自治县', 220721000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220722000000', '220700000000', '220722000000', '长岭县', 220722000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220723000000', '220700000000', '220723000000', '乾安县', 220723000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220771000000', '220700000000', '220771000000', '吉林松原经济开发区', 220771000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220781000000', '220700000000', '220781000000', '扶余市', 220781000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220800000000', '220000000000', '220800000000', '白城市', 220800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220802000000', '220800000000', '220802000000', '洮北区', 220802000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220821000000', '220800000000', '220821000000', '镇赉县', 220821000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220822000000', '220800000000', '220822000000', '通榆县', 220822000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220871000000', '220800000000', '220871000000', '吉林白城经济开发区', 220871000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220881000000', '220800000000', '220881000000', '洮南市', 220881000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('220882000000', '220800000000', '220882000000', '大安市', 220882000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('222400000000', '220000000000', '222400000000', '延边朝鲜族自治州', 222400000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('222401000000', '222400000000', '222401000000', '延吉市', 222401000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('222402000000', '222400000000', '222402000000', '图们市', 222402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('222403000000', '222400000000', '222403000000', '敦化市', 222403000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('222404000000', '222400000000', '222404000000', '珲春市', 222404000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('222405000000', '222400000000', '222405000000', '龙井市', 222405000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('222406000000', '222400000000', '222406000000', '和龙市', 222406000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('222424000000', '222400000000', '222424000000', '汪清县', 222424000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('222426000000', '222400000000', '222426000000', '安图县', 222426000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230000000000', 'CHINA', '230000000000', '黑龙江省', 230000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230100000000', '230000000000', '230100000000', '哈尔滨市', 230100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230102000000', '230100000000', '230102000000', '道里区', 230102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230103000000', '230100000000', '230103000000', '南岗区', 230103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230104000000', '230100000000', '230104000000', '道外区', 230104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230108000000', '230100000000', '230108000000', '平房区', 230108000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230109000000', '230100000000', '230109000000', '松北区', 230109000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230110000000', '230100000000', '230110000000', '香坊区', 230110000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230111000000', '230100000000', '230111000000', '呼兰区', 230111000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230112000000', '230100000000', '230112000000', '阿城区', 230112000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230113000000', '230100000000', '230113000000', '双城区', 230113000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230123000000', '230100000000', '230123000000', '依兰县', 230123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230124000000', '230100000000', '230124000000', '方正县', 230124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230125000000', '230100000000', '230125000000', '宾县', 230125000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230126000000', '230100000000', '230126000000', '巴彦县', 230126000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230127000000', '230100000000', '230127000000', '木兰县', 230127000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230128000000', '230100000000', '230128000000', '通河县', 230128000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230129000000', '230100000000', '230129000000', '延寿县', 230129000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230183000000', '230100000000', '230183000000', '尚志市', 230183000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230184000000', '230100000000', '230184000000', '五常市', 230184000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230200000000', '230000000000', '230200000000', '齐齐哈尔市', 230200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230202000000', '230200000000', '230202000000', '龙沙区', 230202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230203000000', '230200000000', '230203000000', '建华区', 230203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230204000000', '230200000000', '230204000000', '铁锋区', 230204000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230205000000', '230200000000', '230205000000', '昂昂溪区', 230205000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230206000000', '230200000000', '230206000000', '富拉尔基区', 230206000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230207000000', '230200000000', '230207000000', '碾子山区', 230207000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230208000000', '230200000000', '230208000000', '梅里斯达斡尔族区', 230208000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230221000000', '230200000000', '230221000000', '龙江县', 230221000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230223000000', '230200000000', '230223000000', '依安县', 230223000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230224000000', '230200000000', '230224000000', '泰来县', 230224000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230225000000', '230200000000', '230225000000', '甘南县', 230225000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230227000000', '230200000000', '230227000000', '富裕县', 230227000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230229000000', '230200000000', '230229000000', '克山县', 230229000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230230000000', '230200000000', '230230000000', '克东县', 230230000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230231000000', '230200000000', '230231000000', '拜泉县', 230231000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230281000000', '230200000000', '230281000000', '讷河市', 230281000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230300000000', '230000000000', '230300000000', '鸡西市', 230300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230302000000', '230300000000', '230302000000', '鸡冠区', 230302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230303000000', '230300000000', '230303000000', '恒山区', 230303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230304000000', '230300000000', '230304000000', '滴道区', 230304000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230305000000', '230300000000', '230305000000', '梨树区', 230305000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230306000000', '230300000000', '230306000000', '城子河区', 230306000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230307000000', '230300000000', '230307000000', '麻山区', 230307000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230321000000', '230300000000', '230321000000', '鸡东县', 230321000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230381000000', '230300000000', '230381000000', '虎林市', 230381000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230382000000', '230300000000', '230382000000', '密山市', 230382000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230400000000', '230000000000', '230400000000', '鹤岗市', 230400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230402000000', '230400000000', '230402000000', '向阳区', 230402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230403000000', '230400000000', '230403000000', '工农区', 230403000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230404000000', '230400000000', '230404000000', '南山区', 230404000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230405000000', '230400000000', '230405000000', '兴安区', 230405000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230406000000', '230400000000', '230406000000', '东山区', 230406000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230407000000', '230400000000', '230407000000', '兴山区', 230407000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230421000000', '230400000000', '230421000000', '萝北县', 230421000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230422000000', '230400000000', '230422000000', '绥滨县', 230422000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230500000000', '230000000000', '230500000000', '双鸭山市', 230500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230502000000', '230500000000', '230502000000', '尖山区', 230502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230503000000', '230500000000', '230503000000', '岭东区', 230503000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230505000000', '230500000000', '230505000000', '四方台区', 230505000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230506000000', '230500000000', '230506000000', '宝山区', 230506000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230521000000', '230500000000', '230521000000', '集贤县', 230521000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230522000000', '230500000000', '230522000000', '友谊县', 230522000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230523000000', '230500000000', '230523000000', '宝清县', 230523000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230524000000', '230500000000', '230524000000', '饶河县', 230524000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230600000000', '230000000000', '230600000000', '大庆市', 230600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230602000000', '230600000000', '230602000000', '萨尔图区', 230602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230603000000', '230600000000', '230603000000', '龙凤区', 230603000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230604000000', '230600000000', '230604000000', '让胡路区', 230604000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230605000000', '230600000000', '230605000000', '红岗区', 230605000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230606000000', '230600000000', '230606000000', '大同区', 230606000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230621000000', '230600000000', '230621000000', '肇州县', 230621000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230622000000', '230600000000', '230622000000', '肇源县', 230622000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230623000000', '230600000000', '230623000000', '林甸县', 230623000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230624000000', '230600000000', '230624000000', '杜尔伯特蒙古族自治县', 230624000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230671000000', '230600000000', '230671000000', '大庆高新技术产业开发区', 230671000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230700000000', '230000000000', '230700000000', '伊春市', 230700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230717000000', '230700000000', '230717000000', '伊美区', 230717000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230718000000', '230700000000', '230718000000', '乌翠区', 230718000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230719000000', '230700000000', '230719000000', '友好区', 230719000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230722000000', '230700000000', '230722000000', '嘉荫县', 230722000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230723000000', '230700000000', '230723000000', '汤旺县', 230723000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230724000000', '230700000000', '230724000000', '丰林县', 230724000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230725000000', '230700000000', '230725000000', '大箐山县', 230725000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230726000000', '230700000000', '230726000000', '南岔县', 230726000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230751000000', '230700000000', '230751000000', '金林区', 230751000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230781000000', '230700000000', '230781000000', '铁力市', 230781000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230800000000', '230000000000', '230800000000', '佳木斯市', 230800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230803000000', '230800000000', '230803000000', '向阳区', 230803000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230804000000', '230800000000', '230804000000', '前进区', 230804000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230805000000', '230800000000', '230805000000', '东风区', 230805000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230811000000', '230800000000', '230811000000', '郊区', 230811000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230822000000', '230800000000', '230822000000', '桦南县', 230822000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230826000000', '230800000000', '230826000000', '桦川县', 230826000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230828000000', '230800000000', '230828000000', '汤原县', 230828000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230881000000', '230800000000', '230881000000', '同江市', 230881000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230882000000', '230800000000', '230882000000', '富锦市', 230882000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230883000000', '230800000000', '230883000000', '抚远市', 230883000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230900000000', '230000000000', '230900000000', '七台河市', 230900000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230902000000', '230900000000', '230902000000', '新兴区', 230902000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230903000000', '230900000000', '230903000000', '桃山区', 230903000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230904000000', '230900000000', '230904000000', '茄子河区', 230904000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('230921000000', '230900000000', '230921000000', '勃利县', 230921000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231000000000', '230000000000', '231000000000', '牡丹江市', 231000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231002000000', '231000000000', '231002000000', '东安区', 231002000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231003000000', '231000000000', '231003000000', '阳明区', 231003000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231004000000', '231000000000', '231004000000', '爱民区', 231004000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231005000000', '231000000000', '231005000000', '西安区', 231005000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231025000000', '231000000000', '231025000000', '林口县', 231025000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231081000000', '231000000000', '231081000000', '绥芬河市', 231081000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231083000000', '231000000000', '231083000000', '海林市', 231083000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231084000000', '231000000000', '231084000000', '宁安市', 231084000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231085000000', '231000000000', '231085000000', '穆棱市', 231085000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231086000000', '231000000000', '231086000000', '东宁市', 231086000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231100000000', '230000000000', '231100000000', '黑河市', 231100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231102000000', '231100000000', '231102000000', '爱辉区', 231102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231123000000', '231100000000', '231123000000', '逊克县', 231123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231124000000', '231100000000', '231124000000', '孙吴县', 231124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231181000000', '231100000000', '231181000000', '北安市', 231181000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231182000000', '231100000000', '231182000000', '五大连池市', 231182000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231183000000', '231100000000', '231183000000', '嫩江市', 231183000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231200000000', '230000000000', '231200000000', '绥化市', 231200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231202000000', '231200000000', '231202000000', '北林区', 231202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231221000000', '231200000000', '231221000000', '望奎县', 231221000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231222000000', '231200000000', '231222000000', '兰西县', 231222000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231223000000', '231200000000', '231223000000', '青冈县', 231223000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231224000000', '231200000000', '231224000000', '庆安县', 231224000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231225000000', '231200000000', '231225000000', '明水县', 231225000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231226000000', '231200000000', '231226000000', '绥棱县', 231226000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231281000000', '231200000000', '231281000000', '安达市', 231281000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231282000000', '231200000000', '231282000000', '肇东市', 231282000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('231283000000', '231200000000', '231283000000', '海伦市', 231283000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('232700000000', '230000000000', '232700000000', '大兴安岭地区', 232700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('232701000000', '232700000000', '232701000000', '漠河市', 232701000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('232721000000', '232700000000', '232721000000', '呼玛县', 232721000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('232722000000', '232700000000', '232722000000', '塔河县', 232722000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('232761000000', '232700000000', '232761000000', '加格达奇区', 232761000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('232762000000', '232700000000', '232762000000', '松岭区', 232762000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('232763000000', '232700000000', '232763000000', '新林区', 232763000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('232764000000', '232700000000', '232764000000', '呼中区', 232764000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('310000000000', 'CHINA', '310000000000', '上海市', 310000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('310100000000', '310000000000', '310100000000', '上海市', 310100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('310101000000', '310100000000', '310101000000', '黄浦区', 310101000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('310104000000', '310100000000', '310104000000', '徐汇区', 310104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('310105000000', '310100000000', '310105000000', '长宁区', 310105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('310106000000', '310100000000', '310106000000', '静安区', 310106000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('310107000000', '310100000000', '310107000000', '普陀区', 310107000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('310109000000', '310100000000', '310109000000', '虹口区', 310109000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('310110000000', '310100000000', '310110000000', '杨浦区', 310110000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('310112000000', '310100000000', '310112000000', '闵行区', 310112000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('310113000000', '310100000000', '310113000000', '宝山区', 310113000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('310114000000', '310100000000', '310114000000', '嘉定区', 310114000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('310115000000', '310100000000', '310115000000', '浦东新区', 310115000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('310116000000', '310100000000', '310116000000', '金山区', 310116000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('310117000000', '310100000000', '310117000000', '松江区', 310117000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('310118000000', '310100000000', '310118000000', '青浦区', 310118000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('310120000000', '310100000000', '310120000000', '奉贤区', 310120000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('310151000000', '310100000000', '310151000000', '崇明区', 310151000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320000000000', 'CHINA', '320000000000', '江苏省', 320000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320100000000', '320000000000', '320100000000', '南京市', 320100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320102000000', '320100000000', '320102000000', '玄武区', 320102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320104000000', '320100000000', '320104000000', '秦淮区', 320104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320105000000', '320100000000', '320105000000', '建邺区', 320105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320106000000', '320100000000', '320106000000', '鼓楼区', 320106000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320111000000', '320100000000', '320111000000', '浦口区', 320111000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320113000000', '320100000000', '320113000000', '栖霞区', 320113000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320114000000', '320100000000', '320114000000', '雨花台区', 320114000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320115000000', '320100000000', '320115000000', '江宁区', 320115000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320116000000', '320100000000', '320116000000', '六合区', 320116000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320117000000', '320100000000', '320117000000', '溧水区', 320117000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320118000000', '320100000000', '320118000000', '高淳区', 320118000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320200000000', '320000000000', '320200000000', '无锡市', 320200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320205000000', '320200000000', '320205000000', '锡山区', 320205000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320206000000', '320200000000', '320206000000', '惠山区', 320206000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320211000000', '320200000000', '320211000000', '滨湖区', 320211000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320213000000', '320200000000', '320213000000', '梁溪区', 320213000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320214000000', '320200000000', '320214000000', '新吴区', 320214000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320281000000', '320200000000', '320281000000', '江阴市', 320281000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320282000000', '320200000000', '320282000000', '宜兴市', 320282000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320300000000', '320000000000', '320300000000', '徐州市', 320300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320302000000', '320300000000', '320302000000', '鼓楼区', 320302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320303000000', '320300000000', '320303000000', '云龙区', 320303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320305000000', '320300000000', '320305000000', '贾汪区', 320305000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320311000000', '320300000000', '320311000000', '泉山区', 320311000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320312000000', '320300000000', '320312000000', '铜山区', 320312000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320321000000', '320300000000', '320321000000', '丰县', 320321000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320322000000', '320300000000', '320322000000', '沛县', 320322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320324000000', '320300000000', '320324000000', '睢宁县', 320324000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320371000000', '320300000000', '320371000000', '徐州经济技术开发区', 320371000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320381000000', '320300000000', '320381000000', '新沂市', 320381000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320382000000', '320300000000', '320382000000', '邳州市', 320382000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320400000000', '320000000000', '320400000000', '常州市', 320400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320402000000', '320400000000', '320402000000', '天宁区', 320402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320404000000', '320400000000', '320404000000', '钟楼区', 320404000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320411000000', '320400000000', '320411000000', '新北区', 320411000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320412000000', '320400000000', '320412000000', '武进区', 320412000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320413000000', '320400000000', '320413000000', '金坛区', 320413000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320481000000', '320400000000', '320481000000', '溧阳市', 320481000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320500000000', '320000000000', '320500000000', '苏州市', 320500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320505000000', '320500000000', '320505000000', '虎丘区', 320505000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320506000000', '320500000000', '320506000000', '吴中区', 320506000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320507000000', '320500000000', '320507000000', '相城区', 320507000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320508000000', '320500000000', '320508000000', '姑苏区', 320508000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320509000000', '320500000000', '320509000000', '吴江区', 320509000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320576000000', '320500000000', '320576000000', '苏州工业园区', 320576000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320581000000', '320500000000', '320581000000', '常熟市', 320581000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320582000000', '320500000000', '320582000000', '张家港市', 320582000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320583000000', '320500000000', '320583000000', '昆山市', 320583000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320585000000', '320500000000', '320585000000', '太仓市', 320585000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320600000000', '320000000000', '320600000000', '南通市', 320600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320612000000', '320600000000', '320612000000', '通州区', 320612000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320613000000', '320600000000', '320613000000', '崇川区', 320613000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320614000000', '320600000000', '320614000000', '海门区', 320614000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320623000000', '320600000000', '320623000000', '如东县', 320623000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320671000000', '320600000000', '320671000000', '南通经济技术开发区', 320671000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320681000000', '320600000000', '320681000000', '启东市', 320681000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320682000000', '320600000000', '320682000000', '如皋市', 320682000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320685000000', '320600000000', '320685000000', '海安市', 320685000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320700000000', '320000000000', '320700000000', '连云港市', 320700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320703000000', '320700000000', '320703000000', '连云区', 320703000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320706000000', '320700000000', '320706000000', '海州区', 320706000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320707000000', '320700000000', '320707000000', '赣榆区', 320707000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320722000000', '320700000000', '320722000000', '东海县', 320722000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320723000000', '320700000000', '320723000000', '灌云县', 320723000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320724000000', '320700000000', '320724000000', '灌南县', 320724000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320771000000', '320700000000', '320771000000', '连云港经济技术开发区', 320771000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320800000000', '320000000000', '320800000000', '淮安市', 320800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320803000000', '320800000000', '320803000000', '淮安区', 320803000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320804000000', '320800000000', '320804000000', '淮阴区', 320804000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320812000000', '320800000000', '320812000000', '清江浦区', 320812000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320813000000', '320800000000', '320813000000', '洪泽区', 320813000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320826000000', '320800000000', '320826000000', '涟水县', 320826000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320830000000', '320800000000', '320830000000', '盱眙县', 320830000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320831000000', '320800000000', '320831000000', '金湖县', 320831000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320871000000', '320800000000', '320871000000', '淮安经济技术开发区', 320871000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320900000000', '320000000000', '320900000000', '盐城市', 320900000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320902000000', '320900000000', '320902000000', '亭湖区', 320902000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320903000000', '320900000000', '320903000000', '盐都区', 320903000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320904000000', '320900000000', '320904000000', '大丰区', 320904000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320921000000', '320900000000', '320921000000', '响水县', 320921000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320922000000', '320900000000', '320922000000', '滨海县', 320922000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320923000000', '320900000000', '320923000000', '阜宁县', 320923000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320924000000', '320900000000', '320924000000', '射阳县', 320924000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320925000000', '320900000000', '320925000000', '建湖县', 320925000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320971000000', '320900000000', '320971000000', '盐城经济技术开发区', 320971000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('320981000000', '320900000000', '320981000000', '东台市', 320981000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321000000000', '320000000000', '321000000000', '扬州市', 321000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321002000000', '321000000000', '321002000000', '广陵区', 321002000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321003000000', '321000000000', '321003000000', '邗江区', 321003000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321012000000', '321000000000', '321012000000', '江都区', 321012000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321023000000', '321000000000', '321023000000', '宝应县', 321023000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321071000000', '321000000000', '321071000000', '扬州经济技术开发区', 321071000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321081000000', '321000000000', '321081000000', '仪征市', 321081000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321084000000', '321000000000', '321084000000', '高邮市', 321084000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321100000000', '320000000000', '321100000000', '镇江市', 321100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321102000000', '321100000000', '321102000000', '京口区', 321102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321111000000', '321100000000', '321111000000', '润州区', 321111000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321112000000', '321100000000', '321112000000', '丹徒区', 321112000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321171000000', '321100000000', '321171000000', '镇江新区', 321171000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321181000000', '321100000000', '321181000000', '丹阳市', 321181000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321182000000', '321100000000', '321182000000', '扬中市', 321182000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321183000000', '321100000000', '321183000000', '句容市', 321183000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321200000000', '320000000000', '321200000000', '泰州市', 321200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321202000000', '321200000000', '321202000000', '海陵区', 321202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321203000000', '321200000000', '321203000000', '高港区', 321203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321204000000', '321200000000', '321204000000', '姜堰区', 321204000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321281000000', '321200000000', '321281000000', '兴化市', 321281000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321282000000', '321200000000', '321282000000', '靖江市', 321282000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321283000000', '321200000000', '321283000000', '泰兴市', 321283000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321300000000', '320000000000', '321300000000', '宿迁市', 321300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321302000000', '321300000000', '321302000000', '宿城区', 321302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321311000000', '321300000000', '321311000000', '宿豫区', 321311000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321322000000', '321300000000', '321322000000', '沭阳县', 321322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321323000000', '321300000000', '321323000000', '泗阳县', 321323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321324000000', '321300000000', '321324000000', '泗洪县', 321324000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('321371000000', '321300000000', '321371000000', '宿迁经济技术开发区', 321371000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330000000000', 'CHINA', '330000000000', '浙江省', 330000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330100000000', '330000000000', '330100000000', '杭州市', 330100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330102000000', '330100000000', '330102000000', '上城区', 330102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330105000000', '330100000000', '330105000000', '拱墅区', 330105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330106000000', '330100000000', '330106000000', '西湖区', 330106000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330108000000', '330100000000', '330108000000', '滨江区', 330108000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330109000000', '330100000000', '330109000000', '萧山区', 330109000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330110000000', '330100000000', '330110000000', '余杭区', 330110000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330111000000', '330100000000', '330111000000', '富阳区', 330111000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330112000000', '330100000000', '330112000000', '临安区', 330112000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330113000000', '330100000000', '330113000000', '临平区', 330113000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330114000000', '330100000000', '330114000000', '钱塘区', 330114000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330122000000', '330100000000', '330122000000', '桐庐县', 330122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330127000000', '330100000000', '330127000000', '淳安县', 330127000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330182000000', '330100000000', '330182000000', '建德市', 330182000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330200000000', '330000000000', '330200000000', '宁波市', 330200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330203000000', '330200000000', '330203000000', '海曙区', 330203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330205000000', '330200000000', '330205000000', '江北区', 330205000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330206000000', '330200000000', '330206000000', '北仑区', 330206000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330211000000', '330200000000', '330211000000', '镇海区', 330211000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330212000000', '330200000000', '330212000000', '鄞州区', 330212000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330213000000', '330200000000', '330213000000', '奉化区', 330213000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330225000000', '330200000000', '330225000000', '象山县', 330225000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330226000000', '330200000000', '330226000000', '宁海县', 330226000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330281000000', '330200000000', '330281000000', '余姚市', 330281000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330282000000', '330200000000', '330282000000', '慈溪市', 330282000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330300000000', '330000000000', '330300000000', '温州市', 330300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330302000000', '330300000000', '330302000000', '鹿城区', 330302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330303000000', '330300000000', '330303000000', '龙湾区', 330303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330304000000', '330300000000', '330304000000', '瓯海区', 330304000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330305000000', '330300000000', '330305000000', '洞头区', 330305000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330324000000', '330300000000', '330324000000', '永嘉县', 330324000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330326000000', '330300000000', '330326000000', '平阳县', 330326000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330327000000', '330300000000', '330327000000', '苍南县', 330327000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330328000000', '330300000000', '330328000000', '文成县', 330328000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330329000000', '330300000000', '330329000000', '泰顺县', 330329000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330381000000', '330300000000', '330381000000', '瑞安市', 330381000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330382000000', '330300000000', '330382000000', '乐清市', 330382000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330383000000', '330300000000', '330383000000', '龙港市', 330383000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330400000000', '330000000000', '330400000000', '嘉兴市', 330400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330402000000', '330400000000', '330402000000', '南湖区', 330402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330411000000', '330400000000', '330411000000', '秀洲区', 330411000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330421000000', '330400000000', '330421000000', '嘉善县', 330421000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330424000000', '330400000000', '330424000000', '海盐县', 330424000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330481000000', '330400000000', '330481000000', '海宁市', 330481000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330482000000', '330400000000', '330482000000', '平湖市', 330482000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330483000000', '330400000000', '330483000000', '桐乡市', 330483000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330500000000', '330000000000', '330500000000', '湖州市', 330500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330502000000', '330500000000', '330502000000', '吴兴区', 330502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330503000000', '330500000000', '330503000000', '南浔区', 330503000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330521000000', '330500000000', '330521000000', '德清县', 330521000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330522000000', '330500000000', '330522000000', '长兴县', 330522000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330523000000', '330500000000', '330523000000', '安吉县', 330523000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330600000000', '330000000000', '330600000000', '绍兴市', 330600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330602000000', '330600000000', '330602000000', '越城区', 330602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330603000000', '330600000000', '330603000000', '柯桥区', 330603000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330604000000', '330600000000', '330604000000', '上虞区', 330604000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330624000000', '330600000000', '330624000000', '新昌县', 330624000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330681000000', '330600000000', '330681000000', '诸暨市', 330681000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330683000000', '330600000000', '330683000000', '嵊州市', 330683000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330700000000', '330000000000', '330700000000', '金华市', 330700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330702000000', '330700000000', '330702000000', '婺城区', 330702000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330703000000', '330700000000', '330703000000', '金东区', 330703000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330723000000', '330700000000', '330723000000', '武义县', 330723000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330726000000', '330700000000', '330726000000', '浦江县', 330726000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330727000000', '330700000000', '330727000000', '磐安县', 330727000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330781000000', '330700000000', '330781000000', '兰溪市', 330781000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330782000000', '330700000000', '330782000000', '义乌市', 330782000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330783000000', '330700000000', '330783000000', '东阳市', 330783000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330784000000', '330700000000', '330784000000', '永康市', 330784000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330800000000', '330000000000', '330800000000', '衢州市', 330800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330802000000', '330800000000', '330802000000', '柯城区', 330802000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330803000000', '330800000000', '330803000000', '衢江区', 330803000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330822000000', '330800000000', '330822000000', '常山县', 330822000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330824000000', '330800000000', '330824000000', '开化县', 330824000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330825000000', '330800000000', '330825000000', '龙游县', 330825000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330881000000', '330800000000', '330881000000', '江山市', 330881000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330900000000', '330000000000', '330900000000', '舟山市', 330900000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330902000000', '330900000000', '330902000000', '定海区', 330902000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330903000000', '330900000000', '330903000000', '普陀区', 330903000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330921000000', '330900000000', '330921000000', '岱山县', 330921000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('330922000000', '330900000000', '330922000000', '嵊泗县', 330922000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('331000000000', '330000000000', '331000000000', '台州市', 331000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('331002000000', '331000000000', '331002000000', '椒江区', 331002000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('331003000000', '331000000000', '331003000000', '黄岩区', 331003000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('331004000000', '331000000000', '331004000000', '路桥区', 331004000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('331022000000', '331000000000', '331022000000', '三门县', 331022000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('331023000000', '331000000000', '331023000000', '天台县', 331023000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('331024000000', '331000000000', '331024000000', '仙居县', 331024000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('331081000000', '331000000000', '331081000000', '温岭市', 331081000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('331082000000', '331000000000', '331082000000', '临海市', 331082000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('331083000000', '331000000000', '331083000000', '玉环市', 331083000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('331100000000', '330000000000', '331100000000', '丽水市', 331100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('331102000000', '331100000000', '331102000000', '莲都区', 331102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('331121000000', '331100000000', '331121000000', '青田县', 331121000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('331122000000', '331100000000', '331122000000', '缙云县', 331122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('331123000000', '331100000000', '331123000000', '遂昌县', 331123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('331124000000', '331100000000', '331124000000', '松阳县', 331124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('331125000000', '331100000000', '331125000000', '云和县', 331125000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('331126000000', '331100000000', '331126000000', '庆元县', 331126000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('331127000000', '331100000000', '331127000000', '景宁畲族自治县', 331127000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('331181000000', '331100000000', '331181000000', '龙泉市', 331181000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340000000000', 'CHINA', '340000000000', '安徽省', 340000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340100000000', '340000000000', '340100000000', '合肥市', 340100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340102000000', '340100000000', '340102000000', '瑶海区', 340102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340103000000', '340100000000', '340103000000', '庐阳区', 340103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340104000000', '340100000000', '340104000000', '蜀山区', 340104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340111000000', '340100000000', '340111000000', '包河区', 340111000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340121000000', '340100000000', '340121000000', '长丰县', 340121000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340122000000', '340100000000', '340122000000', '肥东县', 340122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340123000000', '340100000000', '340123000000', '肥西县', 340123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340124000000', '340100000000', '340124000000', '庐江县', 340124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340176000000', '340100000000', '340176000000', '合肥高新技术产业开发区', 340176000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340177000000', '340100000000', '340177000000', '合肥经济技术开发区', 340177000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340178000000', '340100000000', '340178000000', '合肥新站高新技术产业开发区', 340178000000, 'system',
        1729736640000, 'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340181000000', '340100000000', '340181000000', '巢湖市', 340181000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340200000000', '340000000000', '340200000000', '芜湖市', 340200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340202000000', '340200000000', '340202000000', '镜湖区', 340202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340207000000', '340200000000', '340207000000', '鸠江区', 340207000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340209000000', '340200000000', '340209000000', '弋江区', 340209000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340210000000', '340200000000', '340210000000', '湾沚区', 340210000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340212000000', '340200000000', '340212000000', '繁昌区', 340212000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340223000000', '340200000000', '340223000000', '南陵县', 340223000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340271000000', '340200000000', '340271000000', '芜湖经济技术开发区', 340271000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340272000000', '340200000000', '340272000000', '安徽芜湖三山经济开发区', 340272000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340281000000', '340200000000', '340281000000', '无为市', 340281000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340300000000', '340000000000', '340300000000', '蚌埠市', 340300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340302000000', '340300000000', '340302000000', '龙子湖区', 340302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340303000000', '340300000000', '340303000000', '蚌山区', 340303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340304000000', '340300000000', '340304000000', '禹会区', 340304000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340311000000', '340300000000', '340311000000', '淮上区', 340311000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340321000000', '340300000000', '340321000000', '怀远县', 340321000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340322000000', '340300000000', '340322000000', '五河县', 340322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340323000000', '340300000000', '340323000000', '固镇县', 340323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340371000000', '340300000000', '340371000000', '蚌埠市高新技术开发区', 340371000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340372000000', '340300000000', '340372000000', '蚌埠市经济开发区', 340372000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340400000000', '340000000000', '340400000000', '淮南市', 340400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340402000000', '340400000000', '340402000000', '大通区', 340402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340403000000', '340400000000', '340403000000', '田家庵区', 340403000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340404000000', '340400000000', '340404000000', '谢家集区', 340404000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340405000000', '340400000000', '340405000000', '八公山区', 340405000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340406000000', '340400000000', '340406000000', '潘集区', 340406000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340421000000', '340400000000', '340421000000', '凤台县', 340421000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340422000000', '340400000000', '340422000000', '寿县', 340422000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340500000000', '340000000000', '340500000000', '马鞍山市', 340500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340503000000', '340500000000', '340503000000', '花山区', 340503000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340504000000', '340500000000', '340504000000', '雨山区', 340504000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340506000000', '340500000000', '340506000000', '博望区', 340506000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340521000000', '340500000000', '340521000000', '当涂县', 340521000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340522000000', '340500000000', '340522000000', '含山县', 340522000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340523000000', '340500000000', '340523000000', '和县', 340523000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340600000000', '340000000000', '340600000000', '淮北市', 340600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340602000000', '340600000000', '340602000000', '杜集区', 340602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340603000000', '340600000000', '340603000000', '相山区', 340603000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340604000000', '340600000000', '340604000000', '烈山区', 340604000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340621000000', '340600000000', '340621000000', '濉溪县', 340621000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340700000000', '340000000000', '340700000000', '铜陵市', 340700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340705000000', '340700000000', '340705000000', '铜官区', 340705000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340706000000', '340700000000', '340706000000', '义安区', 340706000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340711000000', '340700000000', '340711000000', '郊区', 340711000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340722000000', '340700000000', '340722000000', '枞阳县', 340722000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340800000000', '340000000000', '340800000000', '安庆市', 340800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340802000000', '340800000000', '340802000000', '迎江区', 340802000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340803000000', '340800000000', '340803000000', '大观区', 340803000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340811000000', '340800000000', '340811000000', '宜秀区', 340811000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340822000000', '340800000000', '340822000000', '怀宁县', 340822000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340825000000', '340800000000', '340825000000', '太湖县', 340825000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340826000000', '340800000000', '340826000000', '宿松县', 340826000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340827000000', '340800000000', '340827000000', '望江县', 340827000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340828000000', '340800000000', '340828000000', '岳西县', 340828000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340871000000', '340800000000', '340871000000', '安徽安庆经济开发区', 340871000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340881000000', '340800000000', '340881000000', '桐城市', 340881000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('340882000000', '340800000000', '340882000000', '潜山市', 340882000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341000000000', '340000000000', '341000000000', '黄山市', 341000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341002000000', '341000000000', '341002000000', '屯溪区', 341002000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341003000000', '341000000000', '341003000000', '黄山区', 341003000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341004000000', '341000000000', '341004000000', '徽州区', 341004000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341021000000', '341000000000', '341021000000', '歙县', 341021000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341022000000', '341000000000', '341022000000', '休宁县', 341022000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341023000000', '341000000000', '341023000000', '黟县', 341023000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341024000000', '341000000000', '341024000000', '祁门县', 341024000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341100000000', '340000000000', '341100000000', '滁州市', 341100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341102000000', '341100000000', '341102000000', '琅琊区', 341102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341103000000', '341100000000', '341103000000', '南谯区', 341103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341122000000', '341100000000', '341122000000', '来安县', 341122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341124000000', '341100000000', '341124000000', '全椒县', 341124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341125000000', '341100000000', '341125000000', '定远县', 341125000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341126000000', '341100000000', '341126000000', '凤阳县', 341126000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341171000000', '341100000000', '341171000000', '中新苏滁高新技术产业开发区', 341171000000, 'system',
        1729736640000, 'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341172000000', '341100000000', '341172000000', '滁州经济技术开发区', 341172000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341181000000', '341100000000', '341181000000', '天长市', 341181000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341182000000', '341100000000', '341182000000', '明光市', 341182000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341200000000', '340000000000', '341200000000', '阜阳市', 341200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341202000000', '341200000000', '341202000000', '颍州区', 341202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341203000000', '341200000000', '341203000000', '颍东区', 341203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341204000000', '341200000000', '341204000000', '颍泉区', 341204000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341221000000', '341200000000', '341221000000', '临泉县', 341221000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341222000000', '341200000000', '341222000000', '太和县', 341222000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341225000000', '341200000000', '341225000000', '阜南县', 341225000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341226000000', '341200000000', '341226000000', '颍上县', 341226000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341271000000', '341200000000', '341271000000', '阜阳合肥现代产业园区', 341271000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341272000000', '341200000000', '341272000000', '阜阳经济技术开发区', 341272000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341282000000', '341200000000', '341282000000', '界首市', 341282000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341300000000', '340000000000', '341300000000', '宿州市', 341300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341302000000', '341300000000', '341302000000', '埇桥区', 341302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341321000000', '341300000000', '341321000000', '砀山县', 341321000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341322000000', '341300000000', '341322000000', '萧县', 341322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341323000000', '341300000000', '341323000000', '灵璧县', 341323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341324000000', '341300000000', '341324000000', '泗县', 341324000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341371000000', '341300000000', '341371000000', '宿州马鞍山现代产业园区', 341371000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341372000000', '341300000000', '341372000000', '宿州经济技术开发区', 341372000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341500000000', '340000000000', '341500000000', '六安市', 341500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341502000000', '341500000000', '341502000000', '金安区', 341502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341503000000', '341500000000', '341503000000', '裕安区', 341503000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341504000000', '341500000000', '341504000000', '叶集区', 341504000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341522000000', '341500000000', '341522000000', '霍邱县', 341522000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341523000000', '341500000000', '341523000000', '舒城县', 341523000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341524000000', '341500000000', '341524000000', '金寨县', 341524000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341525000000', '341500000000', '341525000000', '霍山县', 341525000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341600000000', '340000000000', '341600000000', '亳州市', 341600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341602000000', '341600000000', '341602000000', '谯城区', 341602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341621000000', '341600000000', '341621000000', '涡阳县', 341621000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341622000000', '341600000000', '341622000000', '蒙城县', 341622000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341623000000', '341600000000', '341623000000', '利辛县', 341623000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341700000000', '340000000000', '341700000000', '池州市', 341700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341702000000', '341700000000', '341702000000', '贵池区', 341702000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341721000000', '341700000000', '341721000000', '东至县', 341721000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341722000000', '341700000000', '341722000000', '石台县', 341722000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341723000000', '341700000000', '341723000000', '青阳县', 341723000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341800000000', '340000000000', '341800000000', '宣城市', 341800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341802000000', '341800000000', '341802000000', '宣州区', 341802000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341821000000', '341800000000', '341821000000', '郎溪县', 341821000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341823000000', '341800000000', '341823000000', '泾县', 341823000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341824000000', '341800000000', '341824000000', '绩溪县', 341824000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341825000000', '341800000000', '341825000000', '旌德县', 341825000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341871000000', '341800000000', '341871000000', '宣城市经济开发区', 341871000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341881000000', '341800000000', '341881000000', '宁国市', 341881000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('341882000000', '341800000000', '341882000000', '广德市', 341882000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350000000000', 'CHINA', '350000000000', '福建省', 350000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350100000000', '350000000000', '350100000000', '福州市', 350100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350102000000', '350100000000', '350102000000', '鼓楼区', 350102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350103000000', '350100000000', '350103000000', '台江区', 350103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350104000000', '350100000000', '350104000000', '仓山区', 350104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350105000000', '350100000000', '350105000000', '马尾区', 350105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350111000000', '350100000000', '350111000000', '晋安区', 350111000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350112000000', '350100000000', '350112000000', '长乐区', 350112000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350121000000', '350100000000', '350121000000', '闽侯县', 350121000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350122000000', '350100000000', '350122000000', '连江县', 350122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350123000000', '350100000000', '350123000000', '罗源县', 350123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350124000000', '350100000000', '350124000000', '闽清县', 350124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350125000000', '350100000000', '350125000000', '永泰县', 350125000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350128000000', '350100000000', '350128000000', '平潭县', 350128000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350181000000', '350100000000', '350181000000', '福清市', 350181000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350200000000', '350000000000', '350200000000', '厦门市', 350200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350203000000', '350200000000', '350203000000', '思明区', 350203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350205000000', '350200000000', '350205000000', '海沧区', 350205000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350206000000', '350200000000', '350206000000', '湖里区', 350206000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350211000000', '350200000000', '350211000000', '集美区', 350211000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350212000000', '350200000000', '350212000000', '同安区', 350212000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350213000000', '350200000000', '350213000000', '翔安区', 350213000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350300000000', '350000000000', '350300000000', '莆田市', 350300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350302000000', '350300000000', '350302000000', '城厢区', 350302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350303000000', '350300000000', '350303000000', '涵江区', 350303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350304000000', '350300000000', '350304000000', '荔城区', 350304000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350305000000', '350300000000', '350305000000', '秀屿区', 350305000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350322000000', '350300000000', '350322000000', '仙游县', 350322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350400000000', '350000000000', '350400000000', '三明市', 350400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350404000000', '350400000000', '350404000000', '三元区', 350404000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350405000000', '350400000000', '350405000000', '沙县区', 350405000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350421000000', '350400000000', '350421000000', '明溪县', 350421000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350423000000', '350400000000', '350423000000', '清流县', 350423000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350424000000', '350400000000', '350424000000', '宁化县', 350424000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350425000000', '350400000000', '350425000000', '大田县', 350425000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350426000000', '350400000000', '350426000000', '尤溪县', 350426000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350428000000', '350400000000', '350428000000', '将乐县', 350428000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350429000000', '350400000000', '350429000000', '泰宁县', 350429000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350430000000', '350400000000', '350430000000', '建宁县', 350430000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350481000000', '350400000000', '350481000000', '永安市', 350481000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350500000000', '350000000000', '350500000000', '泉州市', 350500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350502000000', '350500000000', '350502000000', '鲤城区', 350502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350503000000', '350500000000', '350503000000', '丰泽区', 350503000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350504000000', '350500000000', '350504000000', '洛江区', 350504000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350505000000', '350500000000', '350505000000', '泉港区', 350505000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350521000000', '350500000000', '350521000000', '惠安县', 350521000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350524000000', '350500000000', '350524000000', '安溪县', 350524000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350525000000', '350500000000', '350525000000', '永春县', 350525000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350526000000', '350500000000', '350526000000', '德化县', 350526000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350581000000', '350500000000', '350581000000', '石狮市', 350581000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350582000000', '350500000000', '350582000000', '晋江市', 350582000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350583000000', '350500000000', '350583000000', '南安市', 350583000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350600000000', '350000000000', '350600000000', '漳州市', 350600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350602000000', '350600000000', '350602000000', '芗城区', 350602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350603000000', '350600000000', '350603000000', '龙文区', 350603000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350604000000', '350600000000', '350604000000', '龙海区', 350604000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350605000000', '350600000000', '350605000000', '长泰区', 350605000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350622000000', '350600000000', '350622000000', '云霄县', 350622000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350623000000', '350600000000', '350623000000', '漳浦县', 350623000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350624000000', '350600000000', '350624000000', '诏安县', 350624000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350626000000', '350600000000', '350626000000', '东山县', 350626000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350627000000', '350600000000', '350627000000', '南靖县', 350627000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350628000000', '350600000000', '350628000000', '平和县', 350628000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350629000000', '350600000000', '350629000000', '华安县', 350629000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350700000000', '350000000000', '350700000000', '南平市', 350700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350702000000', '350700000000', '350702000000', '延平区', 350702000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350703000000', '350700000000', '350703000000', '建阳区', 350703000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350721000000', '350700000000', '350721000000', '顺昌县', 350721000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350722000000', '350700000000', '350722000000', '浦城县', 350722000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350723000000', '350700000000', '350723000000', '光泽县', 350723000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350724000000', '350700000000', '350724000000', '松溪县', 350724000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350725000000', '350700000000', '350725000000', '政和县', 350725000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350781000000', '350700000000', '350781000000', '邵武市', 350781000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350782000000', '350700000000', '350782000000', '武夷山市', 350782000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350783000000', '350700000000', '350783000000', '建瓯市', 350783000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350800000000', '350000000000', '350800000000', '龙岩市', 350800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350802000000', '350800000000', '350802000000', '新罗区', 350802000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350803000000', '350800000000', '350803000000', '永定区', 350803000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350821000000', '350800000000', '350821000000', '长汀县', 350821000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350823000000', '350800000000', '350823000000', '上杭县', 350823000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350824000000', '350800000000', '350824000000', '武平县', 350824000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350825000000', '350800000000', '350825000000', '连城县', 350825000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350881000000', '350800000000', '350881000000', '漳平市', 350881000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350900000000', '350000000000', '350900000000', '宁德市', 350900000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350902000000', '350900000000', '350902000000', '蕉城区', 350902000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350921000000', '350900000000', '350921000000', '霞浦县', 350921000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350922000000', '350900000000', '350922000000', '古田县', 350922000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350923000000', '350900000000', '350923000000', '屏南县', 350923000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350924000000', '350900000000', '350924000000', '寿宁县', 350924000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350925000000', '350900000000', '350925000000', '周宁县', 350925000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350926000000', '350900000000', '350926000000', '柘荣县', 350926000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350981000000', '350900000000', '350981000000', '福安市', 350981000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('350982000000', '350900000000', '350982000000', '福鼎市', 350982000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360000000000', 'CHINA', '360000000000', '江西省', 360000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360100000000', '360000000000', '360100000000', '南昌市', 360100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360102000000', '360100000000', '360102000000', '东湖区', 360102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360103000000', '360100000000', '360103000000', '西湖区', 360103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360104000000', '360100000000', '360104000000', '青云谱区', 360104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360111000000', '360100000000', '360111000000', '青山湖区', 360111000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360112000000', '360100000000', '360112000000', '新建区', 360112000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360113000000', '360100000000', '360113000000', '红谷滩区', 360113000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360121000000', '360100000000', '360121000000', '南昌县', 360121000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360123000000', '360100000000', '360123000000', '安义县', 360123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360124000000', '360100000000', '360124000000', '进贤县', 360124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360200000000', '360000000000', '360200000000', '景德镇市', 360200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360202000000', '360200000000', '360202000000', '昌江区', 360202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360203000000', '360200000000', '360203000000', '珠山区', 360203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360222000000', '360200000000', '360222000000', '浮梁县', 360222000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360281000000', '360200000000', '360281000000', '乐平市', 360281000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360300000000', '360000000000', '360300000000', '萍乡市', 360300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360302000000', '360300000000', '360302000000', '安源区', 360302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360313000000', '360300000000', '360313000000', '湘东区', 360313000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360321000000', '360300000000', '360321000000', '莲花县', 360321000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360322000000', '360300000000', '360322000000', '上栗县', 360322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360323000000', '360300000000', '360323000000', '芦溪县', 360323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360400000000', '360000000000', '360400000000', '九江市', 360400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360402000000', '360400000000', '360402000000', '濂溪区', 360402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360403000000', '360400000000', '360403000000', '浔阳区', 360403000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360404000000', '360400000000', '360404000000', '柴桑区', 360404000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360423000000', '360400000000', '360423000000', '武宁县', 360423000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360424000000', '360400000000', '360424000000', '修水县', 360424000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360425000000', '360400000000', '360425000000', '永修县', 360425000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360426000000', '360400000000', '360426000000', '德安县', 360426000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360428000000', '360400000000', '360428000000', '都昌县', 360428000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360429000000', '360400000000', '360429000000', '湖口县', 360429000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360430000000', '360400000000', '360430000000', '彭泽县', 360430000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360481000000', '360400000000', '360481000000', '瑞昌市', 360481000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360482000000', '360400000000', '360482000000', '共青城市', 360482000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360483000000', '360400000000', '360483000000', '庐山市', 360483000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360500000000', '360000000000', '360500000000', '新余市', 360500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360502000000', '360500000000', '360502000000', '渝水区', 360502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360521000000', '360500000000', '360521000000', '分宜县', 360521000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360600000000', '360000000000', '360600000000', '鹰潭市', 360600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360602000000', '360600000000', '360602000000', '月湖区', 360602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360603000000', '360600000000', '360603000000', '余江区', 360603000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360681000000', '360600000000', '360681000000', '贵溪市', 360681000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360700000000', '360000000000', '360700000000', '赣州市', 360700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360702000000', '360700000000', '360702000000', '章贡区', 360702000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360703000000', '360700000000', '360703000000', '南康区', 360703000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360704000000', '360700000000', '360704000000', '赣县区', 360704000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360722000000', '360700000000', '360722000000', '信丰县', 360722000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360723000000', '360700000000', '360723000000', '大余县', 360723000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360724000000', '360700000000', '360724000000', '上犹县', 360724000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360725000000', '360700000000', '360725000000', '崇义县', 360725000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360726000000', '360700000000', '360726000000', '安远县', 360726000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360728000000', '360700000000', '360728000000', '定南县', 360728000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360729000000', '360700000000', '360729000000', '全南县', 360729000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360730000000', '360700000000', '360730000000', '宁都县', 360730000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360731000000', '360700000000', '360731000000', '于都县', 360731000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360732000000', '360700000000', '360732000000', '兴国县', 360732000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360733000000', '360700000000', '360733000000', '会昌县', 360733000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360734000000', '360700000000', '360734000000', '寻乌县', 360734000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360735000000', '360700000000', '360735000000', '石城县', 360735000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360781000000', '360700000000', '360781000000', '瑞金市', 360781000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360783000000', '360700000000', '360783000000', '龙南市', 360783000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360800000000', '360000000000', '360800000000', '吉安市', 360800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360802000000', '360800000000', '360802000000', '吉州区', 360802000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360803000000', '360800000000', '360803000000', '青原区', 360803000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360821000000', '360800000000', '360821000000', '吉安县', 360821000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360822000000', '360800000000', '360822000000', '吉水县', 360822000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360823000000', '360800000000', '360823000000', '峡江县', 360823000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360824000000', '360800000000', '360824000000', '新干县', 360824000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360825000000', '360800000000', '360825000000', '永丰县', 360825000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360826000000', '360800000000', '360826000000', '泰和县', 360826000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360827000000', '360800000000', '360827000000', '遂川县', 360827000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360828000000', '360800000000', '360828000000', '万安县', 360828000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360829000000', '360800000000', '360829000000', '安福县', 360829000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360830000000', '360800000000', '360830000000', '永新县', 360830000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360881000000', '360800000000', '360881000000', '井冈山市', 360881000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360900000000', '360000000000', '360900000000', '宜春市', 360900000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360902000000', '360900000000', '360902000000', '袁州区', 360902000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360921000000', '360900000000', '360921000000', '奉新县', 360921000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360922000000', '360900000000', '360922000000', '万载县', 360922000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360923000000', '360900000000', '360923000000', '上高县', 360923000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360924000000', '360900000000', '360924000000', '宜丰县', 360924000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360925000000', '360900000000', '360925000000', '靖安县', 360925000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360926000000', '360900000000', '360926000000', '铜鼓县', 360926000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360981000000', '360900000000', '360981000000', '丰城市', 360981000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360982000000', '360900000000', '360982000000', '樟树市', 360982000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('360983000000', '360900000000', '360983000000', '高安市', 360983000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361000000000', '360000000000', '361000000000', '抚州市', 361000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361002000000', '361000000000', '361002000000', '临川区', 361002000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361003000000', '361000000000', '361003000000', '东乡区', 361003000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361021000000', '361000000000', '361021000000', '南城县', 361021000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361022000000', '361000000000', '361022000000', '黎川县', 361022000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361023000000', '361000000000', '361023000000', '南丰县', 361023000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361024000000', '361000000000', '361024000000', '崇仁县', 361024000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361025000000', '361000000000', '361025000000', '乐安县', 361025000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361026000000', '361000000000', '361026000000', '宜黄县', 361026000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361027000000', '361000000000', '361027000000', '金溪县', 361027000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361028000000', '361000000000', '361028000000', '资溪县', 361028000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361030000000', '361000000000', '361030000000', '广昌县', 361030000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361100000000', '360000000000', '361100000000', '上饶市', 361100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361102000000', '361100000000', '361102000000', '信州区', 361102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361103000000', '361100000000', '361103000000', '广丰区', 361103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361104000000', '361100000000', '361104000000', '广信区', 361104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361123000000', '361100000000', '361123000000', '玉山县', 361123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361124000000', '361100000000', '361124000000', '铅山县', 361124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361125000000', '361100000000', '361125000000', '横峰县', 361125000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361126000000', '361100000000', '361126000000', '弋阳县', 361126000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361127000000', '361100000000', '361127000000', '余干县', 361127000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361128000000', '361100000000', '361128000000', '鄱阳县', 361128000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361129000000', '361100000000', '361129000000', '万年县', 361129000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361130000000', '361100000000', '361130000000', '婺源县', 361130000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('361181000000', '361100000000', '361181000000', '德兴市', 361181000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370000000000', 'CHINA', '370000000000', '山东省', 370000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370100000000', '370000000000', '370100000000', '济南市', 370100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370102000000', '370100000000', '370102000000', '历下区', 370102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370103000000', '370100000000', '370103000000', '市中区', 370103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370104000000', '370100000000', '370104000000', '槐荫区', 370104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370105000000', '370100000000', '370105000000', '天桥区', 370105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370112000000', '370100000000', '370112000000', '历城区', 370112000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370113000000', '370100000000', '370113000000', '长清区', 370113000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370114000000', '370100000000', '370114000000', '章丘区', 370114000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370115000000', '370100000000', '370115000000', '济阳区', 370115000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370116000000', '370100000000', '370116000000', '莱芜区', 370116000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370117000000', '370100000000', '370117000000', '钢城区', 370117000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370124000000', '370100000000', '370124000000', '平阴县', 370124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370126000000', '370100000000', '370126000000', '商河县', 370126000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370176000000', '370100000000', '370176000000', '济南高新技术产业开发区', 370176000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370200000000', '370000000000', '370200000000', '青岛市', 370200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370202000000', '370200000000', '370202000000', '市南区', 370202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370203000000', '370200000000', '370203000000', '市北区', 370203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370211000000', '370200000000', '370211000000', '黄岛区', 370211000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370212000000', '370200000000', '370212000000', '崂山区', 370212000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370213000000', '370200000000', '370213000000', '李沧区', 370213000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370214000000', '370200000000', '370214000000', '城阳区', 370214000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370215000000', '370200000000', '370215000000', '即墨区', 370215000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370281000000', '370200000000', '370281000000', '胶州市', 370281000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370283000000', '370200000000', '370283000000', '平度市', 370283000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370285000000', '370200000000', '370285000000', '莱西市', 370285000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370300000000', '370000000000', '370300000000', '淄博市', 370300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370302000000', '370300000000', '370302000000', '淄川区', 370302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370303000000', '370300000000', '370303000000', '张店区', 370303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370304000000', '370300000000', '370304000000', '博山区', 370304000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370305000000', '370300000000', '370305000000', '临淄区', 370305000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370306000000', '370300000000', '370306000000', '周村区', 370306000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370321000000', '370300000000', '370321000000', '桓台县', 370321000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370322000000', '370300000000', '370322000000', '高青县', 370322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370323000000', '370300000000', '370323000000', '沂源县', 370323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370400000000', '370000000000', '370400000000', '枣庄市', 370400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370402000000', '370400000000', '370402000000', '市中区', 370402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370403000000', '370400000000', '370403000000', '薛城区', 370403000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370404000000', '370400000000', '370404000000', '峄城区', 370404000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370405000000', '370400000000', '370405000000', '台儿庄区', 370405000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370406000000', '370400000000', '370406000000', '山亭区', 370406000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370481000000', '370400000000', '370481000000', '滕州市', 370481000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370500000000', '370000000000', '370500000000', '东营市', 370500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370502000000', '370500000000', '370502000000', '东营区', 370502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370503000000', '370500000000', '370503000000', '河口区', 370503000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370505000000', '370500000000', '370505000000', '垦利区', 370505000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370522000000', '370500000000', '370522000000', '利津县', 370522000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370523000000', '370500000000', '370523000000', '广饶县', 370523000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370571000000', '370500000000', '370571000000', '东营经济技术开发区', 370571000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370572000000', '370500000000', '370572000000', '东营港经济开发区', 370572000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370600000000', '370000000000', '370600000000', '烟台市', 370600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370602000000', '370600000000', '370602000000', '芝罘区', 370602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370611000000', '370600000000', '370611000000', '福山区', 370611000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370612000000', '370600000000', '370612000000', '牟平区', 370612000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370613000000', '370600000000', '370613000000', '莱山区', 370613000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370614000000', '370600000000', '370614000000', '蓬莱区', 370614000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370671000000', '370600000000', '370671000000', '烟台高新技术产业开发区', 370671000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370676000000', '370600000000', '370676000000', '烟台经济技术开发区', 370676000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370681000000', '370600000000', '370681000000', '龙口市', 370681000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370682000000', '370600000000', '370682000000', '莱阳市', 370682000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370683000000', '370600000000', '370683000000', '莱州市', 370683000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370685000000', '370600000000', '370685000000', '招远市', 370685000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370686000000', '370600000000', '370686000000', '栖霞市', 370686000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370687000000', '370600000000', '370687000000', '海阳市', 370687000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370700000000', '370000000000', '370700000000', '潍坊市', 370700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370702000000', '370700000000', '370702000000', '潍城区', 370702000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370703000000', '370700000000', '370703000000', '寒亭区', 370703000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370704000000', '370700000000', '370704000000', '坊子区', 370704000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370705000000', '370700000000', '370705000000', '奎文区', 370705000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370724000000', '370700000000', '370724000000', '临朐县', 370724000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370725000000', '370700000000', '370725000000', '昌乐县', 370725000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370772000000', '370700000000', '370772000000', '潍坊滨海经济技术开发区', 370772000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370781000000', '370700000000', '370781000000', '青州市', 370781000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370782000000', '370700000000', '370782000000', '诸城市', 370782000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370783000000', '370700000000', '370783000000', '寿光市', 370783000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370784000000', '370700000000', '370784000000', '安丘市', 370784000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370785000000', '370700000000', '370785000000', '高密市', 370785000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370786000000', '370700000000', '370786000000', '昌邑市', 370786000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370800000000', '370000000000', '370800000000', '济宁市', 370800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370811000000', '370800000000', '370811000000', '任城区', 370811000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370812000000', '370800000000', '370812000000', '兖州区', 370812000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370826000000', '370800000000', '370826000000', '微山县', 370826000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370827000000', '370800000000', '370827000000', '鱼台县', 370827000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370828000000', '370800000000', '370828000000', '金乡县', 370828000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370829000000', '370800000000', '370829000000', '嘉祥县', 370829000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370830000000', '370800000000', '370830000000', '汶上县', 370830000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370831000000', '370800000000', '370831000000', '泗水县', 370831000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370832000000', '370800000000', '370832000000', '梁山县', 370832000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370871000000', '370800000000', '370871000000', '济宁高新技术产业开发区', 370871000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370881000000', '370800000000', '370881000000', '曲阜市', 370881000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370883000000', '370800000000', '370883000000', '邹城市', 370883000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370900000000', '370000000000', '370900000000', '泰安市', 370900000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370902000000', '370900000000', '370902000000', '泰山区', 370902000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370911000000', '370900000000', '370911000000', '岱岳区', 370911000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370921000000', '370900000000', '370921000000', '宁阳县', 370921000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370923000000', '370900000000', '370923000000', '东平县', 370923000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370982000000', '370900000000', '370982000000', '新泰市', 370982000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('370983000000', '370900000000', '370983000000', '肥城市', 370983000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371000000000', '370000000000', '371000000000', '威海市', 371000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371002000000', '371000000000', '371002000000', '环翠区', 371002000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371003000000', '371000000000', '371003000000', '文登区', 371003000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371071000000', '371000000000', '371071000000', '威海火炬高技术产业开发区', 371071000000, 'system',
        1729736640000, 'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371072000000', '371000000000', '371072000000', '威海经济技术开发区', 371072000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371073000000', '371000000000', '371073000000', '威海临港经济技术开发区', 371073000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371082000000', '371000000000', '371082000000', '荣成市', 371082000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371083000000', '371000000000', '371083000000', '乳山市', 371083000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371100000000', '370000000000', '371100000000', '日照市', 371100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371102000000', '371100000000', '371102000000', '东港区', 371102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371103000000', '371100000000', '371103000000', '岚山区', 371103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371121000000', '371100000000', '371121000000', '五莲县', 371121000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371122000000', '371100000000', '371122000000', '莒县', 371122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371171000000', '371100000000', '371171000000', '日照经济技术开发区', 371171000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371300000000', '370000000000', '371300000000', '临沂市', 371300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371302000000', '371300000000', '371302000000', '兰山区', 371302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371311000000', '371300000000', '371311000000', '罗庄区', 371311000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371312000000', '371300000000', '371312000000', '河东区', 371312000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371321000000', '371300000000', '371321000000', '沂南县', 371321000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371322000000', '371300000000', '371322000000', '郯城县', 371322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371323000000', '371300000000', '371323000000', '沂水县', 371323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371324000000', '371300000000', '371324000000', '兰陵县', 371324000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371325000000', '371300000000', '371325000000', '费县', 371325000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371326000000', '371300000000', '371326000000', '平邑县', 371326000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371327000000', '371300000000', '371327000000', '莒南县', 371327000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371328000000', '371300000000', '371328000000', '蒙阴县', 371328000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371329000000', '371300000000', '371329000000', '临沭县', 371329000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371371000000', '371300000000', '371371000000', '临沂高新技术产业开发区', 371371000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371400000000', '370000000000', '371400000000', '德州市', 371400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371402000000', '371400000000', '371402000000', '德城区', 371402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371403000000', '371400000000', '371403000000', '陵城区', 371403000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371422000000', '371400000000', '371422000000', '宁津县', 371422000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371423000000', '371400000000', '371423000000', '庆云县', 371423000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371424000000', '371400000000', '371424000000', '临邑县', 371424000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371425000000', '371400000000', '371425000000', '齐河县', 371425000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371426000000', '371400000000', '371426000000', '平原县', 371426000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371427000000', '371400000000', '371427000000', '夏津县', 371427000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371428000000', '371400000000', '371428000000', '武城县', 371428000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371471000000', '371400000000', '371471000000', '德州天衢新区', 371471000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371481000000', '371400000000', '371481000000', '乐陵市', 371481000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371482000000', '371400000000', '371482000000', '禹城市', 371482000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371500000000', '370000000000', '371500000000', '聊城市', 371500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371502000000', '371500000000', '371502000000', '东昌府区', 371502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371503000000', '371500000000', '371503000000', '茌平区', 371503000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371521000000', '371500000000', '371521000000', '阳谷县', 371521000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371522000000', '371500000000', '371522000000', '莘县', 371522000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371524000000', '371500000000', '371524000000', '东阿县', 371524000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371525000000', '371500000000', '371525000000', '冠县', 371525000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371526000000', '371500000000', '371526000000', '高唐县', 371526000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371581000000', '371500000000', '371581000000', '临清市', 371581000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371600000000', '370000000000', '371600000000', '滨州市', 371600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371602000000', '371600000000', '371602000000', '滨城区', 371602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371603000000', '371600000000', '371603000000', '沾化区', 371603000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371621000000', '371600000000', '371621000000', '惠民县', 371621000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371622000000', '371600000000', '371622000000', '阳信县', 371622000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371623000000', '371600000000', '371623000000', '无棣县', 371623000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371625000000', '371600000000', '371625000000', '博兴县', 371625000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371681000000', '371600000000', '371681000000', '邹平市', 371681000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371700000000', '370000000000', '371700000000', '菏泽市', 371700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371702000000', '371700000000', '371702000000', '牡丹区', 371702000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371703000000', '371700000000', '371703000000', '定陶区', 371703000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371721000000', '371700000000', '371721000000', '曹县', 371721000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371722000000', '371700000000', '371722000000', '单县', 371722000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371723000000', '371700000000', '371723000000', '成武县', 371723000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371724000000', '371700000000', '371724000000', '巨野县', 371724000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371725000000', '371700000000', '371725000000', '郓城县', 371725000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371726000000', '371700000000', '371726000000', '鄄城县', 371726000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371728000000', '371700000000', '371728000000', '东明县', 371728000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371771000000', '371700000000', '371771000000', '菏泽经济技术开发区', 371771000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('371772000000', '371700000000', '371772000000', '菏泽高新技术开发区', 371772000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410000000000', 'CHINA', '410000000000', '河南省', 410000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410100000000', '410000000000', '410100000000', '郑州市', 410100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410102000000', '410100000000', '410102000000', '中原区', 410102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410103000000', '410100000000', '410103000000', '二七区', 410103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410104000000', '410100000000', '410104000000', '管城回族区', 410104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410105000000', '410100000000', '410105000000', '金水区', 410105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410106000000', '410100000000', '410106000000', '上街区', 410106000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410108000000', '410100000000', '410108000000', '惠济区', 410108000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410122000000', '410100000000', '410122000000', '中牟县', 410122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410171000000', '410100000000', '410171000000', '郑州经济技术开发区', 410171000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410172000000', '410100000000', '410172000000', '郑州高新技术产业开发区', 410172000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410173000000', '410100000000', '410173000000', '郑州航空港经济综合实验区', 410173000000, 'system',
        1729736640000, 'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410181000000', '410100000000', '410181000000', '巩义市', 410181000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410182000000', '410100000000', '410182000000', '荥阳市', 410182000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410183000000', '410100000000', '410183000000', '新密市', 410183000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410184000000', '410100000000', '410184000000', '新郑市', 410184000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410185000000', '410100000000', '410185000000', '登封市', 410185000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410200000000', '410000000000', '410200000000', '开封市', 410200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410202000000', '410200000000', '410202000000', '龙亭区', 410202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410203000000', '410200000000', '410203000000', '顺河回族区', 410203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410204000000', '410200000000', '410204000000', '鼓楼区', 410204000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410205000000', '410200000000', '410205000000', '禹王台区', 410205000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410212000000', '410200000000', '410212000000', '祥符区', 410212000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410221000000', '410200000000', '410221000000', '杞县', 410221000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410222000000', '410200000000', '410222000000', '通许县', 410222000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410223000000', '410200000000', '410223000000', '尉氏县', 410223000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410225000000', '410200000000', '410225000000', '兰考县', 410225000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410300000000', '410000000000', '410300000000', '洛阳市', 410300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410302000000', '410300000000', '410302000000', '老城区', 410302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410303000000', '410300000000', '410303000000', '西工区', 410303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410304000000', '410300000000', '410304000000', '瀍河回族区', 410304000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410305000000', '410300000000', '410305000000', '涧西区', 410305000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410307000000', '410300000000', '410307000000', '偃师区', 410307000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410308000000', '410300000000', '410308000000', '孟津区', 410308000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410311000000', '410300000000', '410311000000', '洛龙区', 410311000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410323000000', '410300000000', '410323000000', '新安县', 410323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410324000000', '410300000000', '410324000000', '栾川县', 410324000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410325000000', '410300000000', '410325000000', '嵩县', 410325000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410326000000', '410300000000', '410326000000', '汝阳县', 410326000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410327000000', '410300000000', '410327000000', '宜阳县', 410327000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410328000000', '410300000000', '410328000000', '洛宁县', 410328000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410329000000', '410300000000', '410329000000', '伊川县', 410329000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410371000000', '410300000000', '410371000000', '洛阳高新技术产业开发区', 410371000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410400000000', '410000000000', '410400000000', '平顶山市', 410400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410402000000', '410400000000', '410402000000', '新华区', 410402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410403000000', '410400000000', '410403000000', '卫东区', 410403000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410404000000', '410400000000', '410404000000', '石龙区', 410404000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410411000000', '410400000000', '410411000000', '湛河区', 410411000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410421000000', '410400000000', '410421000000', '宝丰县', 410421000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410422000000', '410400000000', '410422000000', '叶县', 410422000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410423000000', '410400000000', '410423000000', '鲁山县', 410423000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410425000000', '410400000000', '410425000000', '郏县', 410425000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410471000000', '410400000000', '410471000000', '平顶山高新技术产业开发区', 410471000000, 'system',
        1729736640000, 'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410472000000', '410400000000', '410472000000', '平顶山市城乡一体化示范区', 410472000000, 'system',
        1729736640000, 'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410481000000', '410400000000', '410481000000', '舞钢市', 410481000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410482000000', '410400000000', '410482000000', '汝州市', 410482000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410500000000', '410000000000', '410500000000', '安阳市', 410500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410502000000', '410500000000', '410502000000', '文峰区', 410502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410503000000', '410500000000', '410503000000', '北关区', 410503000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410505000000', '410500000000', '410505000000', '殷都区', 410505000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410506000000', '410500000000', '410506000000', '龙安区', 410506000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410522000000', '410500000000', '410522000000', '安阳县', 410522000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410523000000', '410500000000', '410523000000', '汤阴县', 410523000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410526000000', '410500000000', '410526000000', '滑县', 410526000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410527000000', '410500000000', '410527000000', '内黄县', 410527000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410571000000', '410500000000', '410571000000', '安阳高新技术产业开发区', 410571000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410581000000', '410500000000', '410581000000', '林州市', 410581000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410600000000', '410000000000', '410600000000', '鹤壁市', 410600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410602000000', '410600000000', '410602000000', '鹤山区', 410602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410603000000', '410600000000', '410603000000', '山城区', 410603000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410611000000', '410600000000', '410611000000', '淇滨区', 410611000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410621000000', '410600000000', '410621000000', '浚县', 410621000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410622000000', '410600000000', '410622000000', '淇县', 410622000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410671000000', '410600000000', '410671000000', '鹤壁经济技术开发区', 410671000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410700000000', '410000000000', '410700000000', '新乡市', 410700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410702000000', '410700000000', '410702000000', '红旗区', 410702000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410703000000', '410700000000', '410703000000', '卫滨区', 410703000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410704000000', '410700000000', '410704000000', '凤泉区', 410704000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410711000000', '410700000000', '410711000000', '牧野区', 410711000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410721000000', '410700000000', '410721000000', '新乡县', 410721000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410724000000', '410700000000', '410724000000', '获嘉县', 410724000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410725000000', '410700000000', '410725000000', '原阳县', 410725000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410726000000', '410700000000', '410726000000', '延津县', 410726000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410727000000', '410700000000', '410727000000', '封丘县', 410727000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410771000000', '410700000000', '410771000000', '新乡高新技术产业开发区', 410771000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410772000000', '410700000000', '410772000000', '新乡经济技术开发区', 410772000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410773000000', '410700000000', '410773000000', '新乡市平原城乡一体化示范区', 410773000000, 'system',
        1729736640000, 'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410781000000', '410700000000', '410781000000', '卫辉市', 410781000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410782000000', '410700000000', '410782000000', '辉县市', 410782000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410783000000', '410700000000', '410783000000', '长垣市', 410783000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410800000000', '410000000000', '410800000000', '焦作市', 410800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410802000000', '410800000000', '410802000000', '解放区', 410802000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410803000000', '410800000000', '410803000000', '中站区', 410803000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410804000000', '410800000000', '410804000000', '马村区', 410804000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410811000000', '410800000000', '410811000000', '山阳区', 410811000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410821000000', '410800000000', '410821000000', '修武县', 410821000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410822000000', '410800000000', '410822000000', '博爱县', 410822000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410823000000', '410800000000', '410823000000', '武陟县', 410823000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410825000000', '410800000000', '410825000000', '温县', 410825000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410871000000', '410800000000', '410871000000', '焦作城乡一体化示范区', 410871000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410882000000', '410800000000', '410882000000', '沁阳市', 410882000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410883000000', '410800000000', '410883000000', '孟州市', 410883000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410900000000', '410000000000', '410900000000', '濮阳市', 410900000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410902000000', '410900000000', '410902000000', '华龙区', 410902000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410922000000', '410900000000', '410922000000', '清丰县', 410922000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410923000000', '410900000000', '410923000000', '南乐县', 410923000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410926000000', '410900000000', '410926000000', '范县', 410926000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410927000000', '410900000000', '410927000000', '台前县', 410927000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410928000000', '410900000000', '410928000000', '濮阳县', 410928000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410971000000', '410900000000', '410971000000', '河南濮阳工业园区', 410971000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('410972000000', '410900000000', '410972000000', '濮阳经济技术开发区', 410972000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411000000000', '410000000000', '411000000000', '许昌市', 411000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411002000000', '411000000000', '411002000000', '魏都区', 411002000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411003000000', '411000000000', '411003000000', '建安区', 411003000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411024000000', '411000000000', '411024000000', '鄢陵县', 411024000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411025000000', '411000000000', '411025000000', '襄城县', 411025000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411071000000', '411000000000', '411071000000', '许昌经济技术开发区', 411071000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411081000000', '411000000000', '411081000000', '禹州市', 411081000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411082000000', '411000000000', '411082000000', '长葛市', 411082000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411100000000', '410000000000', '411100000000', '漯河市', 411100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411102000000', '411100000000', '411102000000', '源汇区', 411102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411103000000', '411100000000', '411103000000', '郾城区', 411103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411104000000', '411100000000', '411104000000', '召陵区', 411104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411121000000', '411100000000', '411121000000', '舞阳县', 411121000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411122000000', '411100000000', '411122000000', '临颍县', 411122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411171000000', '411100000000', '411171000000', '漯河经济技术开发区', 411171000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411200000000', '410000000000', '411200000000', '三门峡市', 411200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411202000000', '411200000000', '411202000000', '湖滨区', 411202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411203000000', '411200000000', '411203000000', '陕州区', 411203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411221000000', '411200000000', '411221000000', '渑池县', 411221000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411224000000', '411200000000', '411224000000', '卢氏县', 411224000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411271000000', '411200000000', '411271000000', '河南三门峡经济开发区', 411271000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411281000000', '411200000000', '411281000000', '义马市', 411281000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411282000000', '411200000000', '411282000000', '灵宝市', 411282000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411300000000', '410000000000', '411300000000', '南阳市', 411300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411302000000', '411300000000', '411302000000', '宛城区', 411302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411303000000', '411300000000', '411303000000', '卧龙区', 411303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411321000000', '411300000000', '411321000000', '南召县', 411321000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411322000000', '411300000000', '411322000000', '方城县', 411322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411323000000', '411300000000', '411323000000', '西峡县', 411323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411324000000', '411300000000', '411324000000', '镇平县', 411324000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411325000000', '411300000000', '411325000000', '内乡县', 411325000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411326000000', '411300000000', '411326000000', '淅川县', 411326000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411327000000', '411300000000', '411327000000', '社旗县', 411327000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411328000000', '411300000000', '411328000000', '唐河县', 411328000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411329000000', '411300000000', '411329000000', '新野县', 411329000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411330000000', '411300000000', '411330000000', '桐柏县', 411330000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411371000000', '411300000000', '411371000000', '南阳高新技术产业开发区', 411371000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411372000000', '411300000000', '411372000000', '南阳市城乡一体化示范区', 411372000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411381000000', '411300000000', '411381000000', '邓州市', 411381000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411400000000', '410000000000', '411400000000', '商丘市', 411400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411402000000', '411400000000', '411402000000', '梁园区', 411402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411403000000', '411400000000', '411403000000', '睢阳区', 411403000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411421000000', '411400000000', '411421000000', '民权县', 411421000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411422000000', '411400000000', '411422000000', '睢县', 411422000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411423000000', '411400000000', '411423000000', '宁陵县', 411423000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411424000000', '411400000000', '411424000000', '柘城县', 411424000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411425000000', '411400000000', '411425000000', '虞城县', 411425000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411426000000', '411400000000', '411426000000', '夏邑县', 411426000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411471000000', '411400000000', '411471000000', '豫东综合物流产业聚集区', 411471000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411472000000', '411400000000', '411472000000', '河南商丘经济开发区', 411472000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411481000000', '411400000000', '411481000000', '永城市', 411481000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411500000000', '410000000000', '411500000000', '信阳市', 411500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411502000000', '411500000000', '411502000000', '浉河区', 411502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411503000000', '411500000000', '411503000000', '平桥区', 411503000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411521000000', '411500000000', '411521000000', '罗山县', 411521000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411522000000', '411500000000', '411522000000', '光山县', 411522000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411523000000', '411500000000', '411523000000', '新县', 411523000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411524000000', '411500000000', '411524000000', '商城县', 411524000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411525000000', '411500000000', '411525000000', '固始县', 411525000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411526000000', '411500000000', '411526000000', '潢川县', 411526000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411527000000', '411500000000', '411527000000', '淮滨县', 411527000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411528000000', '411500000000', '411528000000', '息县', 411528000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411571000000', '411500000000', '411571000000', '信阳高新技术产业开发区', 411571000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411600000000', '410000000000', '411600000000', '周口市', 411600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411602000000', '411600000000', '411602000000', '川汇区', 411602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411603000000', '411600000000', '411603000000', '淮阳区', 411603000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411621000000', '411600000000', '411621000000', '扶沟县', 411621000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411622000000', '411600000000', '411622000000', '西华县', 411622000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411623000000', '411600000000', '411623000000', '商水县', 411623000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411624000000', '411600000000', '411624000000', '沈丘县', 411624000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411625000000', '411600000000', '411625000000', '郸城县', 411625000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411627000000', '411600000000', '411627000000', '太康县', 411627000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411628000000', '411600000000', '411628000000', '鹿邑县', 411628000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411671000000', '411600000000', '411671000000', '周口临港开发区', 411671000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411681000000', '411600000000', '411681000000', '项城市', 411681000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411700000000', '410000000000', '411700000000', '驻马店市', 411700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411702000000', '411700000000', '411702000000', '驿城区', 411702000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411721000000', '411700000000', '411721000000', '西平县', 411721000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411722000000', '411700000000', '411722000000', '上蔡县', 411722000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411723000000', '411700000000', '411723000000', '平舆县', 411723000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411724000000', '411700000000', '411724000000', '正阳县', 411724000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411725000000', '411700000000', '411725000000', '确山县', 411725000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411726000000', '411700000000', '411726000000', '泌阳县', 411726000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411727000000', '411700000000', '411727000000', '汝南县', 411727000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411728000000', '411700000000', '411728000000', '遂平县', 411728000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411729000000', '411700000000', '411729000000', '新蔡县', 411729000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('411771000000', '411700000000', '411771000000', '河南驻马店经济开发区', 411771000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('419001000000', '410000000000', '419001000000', '济源市', 419001000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420000000000', 'CHINA', '420000000000', '湖北省', 420000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420100000000', '420000000000', '420100000000', '武汉市', 420100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420102000000', '420100000000', '420102000000', '江岸区', 420102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420103000000', '420100000000', '420103000000', '江汉区', 420103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420104000000', '420100000000', '420104000000', '硚口区', 420104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420105000000', '420100000000', '420105000000', '汉阳区', 420105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420106000000', '420100000000', '420106000000', '武昌区', 420106000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420107000000', '420100000000', '420107000000', '青山区', 420107000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420111000000', '420100000000', '420111000000', '洪山区', 420111000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420112000000', '420100000000', '420112000000', '东西湖区', 420112000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420113000000', '420100000000', '420113000000', '汉南区', 420113000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420114000000', '420100000000', '420114000000', '蔡甸区', 420114000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420115000000', '420100000000', '420115000000', '江夏区', 420115000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420116000000', '420100000000', '420116000000', '黄陂区', 420116000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420117000000', '420100000000', '420117000000', '新洲区', 420117000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420200000000', '420000000000', '420200000000', '黄石市', 420200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420202000000', '420200000000', '420202000000', '黄石港区', 420202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420203000000', '420200000000', '420203000000', '西塞山区', 420203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420204000000', '420200000000', '420204000000', '下陆区', 420204000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420205000000', '420200000000', '420205000000', '铁山区', 420205000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420222000000', '420200000000', '420222000000', '阳新县', 420222000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420281000000', '420200000000', '420281000000', '大冶市', 420281000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420300000000', '420000000000', '420300000000', '十堰市', 420300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420302000000', '420300000000', '420302000000', '茅箭区', 420302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420303000000', '420300000000', '420303000000', '张湾区', 420303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420304000000', '420300000000', '420304000000', '郧阳区', 420304000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420322000000', '420300000000', '420322000000', '郧西县', 420322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420323000000', '420300000000', '420323000000', '竹山县', 420323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420324000000', '420300000000', '420324000000', '竹溪县', 420324000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420325000000', '420300000000', '420325000000', '房县', 420325000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420381000000', '420300000000', '420381000000', '丹江口市', 420381000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420500000000', '420000000000', '420500000000', '宜昌市', 420500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420502000000', '420500000000', '420502000000', '西陵区', 420502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420503000000', '420500000000', '420503000000', '伍家岗区', 420503000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420504000000', '420500000000', '420504000000', '点军区', 420504000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420505000000', '420500000000', '420505000000', '猇亭区', 420505000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420506000000', '420500000000', '420506000000', '夷陵区', 420506000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420525000000', '420500000000', '420525000000', '远安县', 420525000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420526000000', '420500000000', '420526000000', '兴山县', 420526000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420527000000', '420500000000', '420527000000', '秭归县', 420527000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420528000000', '420500000000', '420528000000', '长阳土家族自治县', 420528000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420529000000', '420500000000', '420529000000', '五峰土家族自治县', 420529000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420581000000', '420500000000', '420581000000', '宜都市', 420581000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420582000000', '420500000000', '420582000000', '当阳市', 420582000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420583000000', '420500000000', '420583000000', '枝江市', 420583000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420600000000', '420000000000', '420600000000', '襄阳市', 420600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420602000000', '420600000000', '420602000000', '襄城区', 420602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420606000000', '420600000000', '420606000000', '樊城区', 420606000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420607000000', '420600000000', '420607000000', '襄州区', 420607000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420624000000', '420600000000', '420624000000', '南漳县', 420624000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420625000000', '420600000000', '420625000000', '谷城县', 420625000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420626000000', '420600000000', '420626000000', '保康县', 420626000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420682000000', '420600000000', '420682000000', '老河口市', 420682000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420683000000', '420600000000', '420683000000', '枣阳市', 420683000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420684000000', '420600000000', '420684000000', '宜城市', 420684000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420700000000', '420000000000', '420700000000', '鄂州市', 420700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420702000000', '420700000000', '420702000000', '梁子湖区', 420702000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420703000000', '420700000000', '420703000000', '华容区', 420703000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420704000000', '420700000000', '420704000000', '鄂城区', 420704000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420800000000', '420000000000', '420800000000', '荆门市', 420800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420802000000', '420800000000', '420802000000', '东宝区', 420802000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420804000000', '420800000000', '420804000000', '掇刀区', 420804000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420822000000', '420800000000', '420822000000', '沙洋县', 420822000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420881000000', '420800000000', '420881000000', '钟祥市', 420881000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420882000000', '420800000000', '420882000000', '京山市', 420882000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420900000000', '420000000000', '420900000000', '孝感市', 420900000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420902000000', '420900000000', '420902000000', '孝南区', 420902000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420921000000', '420900000000', '420921000000', '孝昌县', 420921000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420922000000', '420900000000', '420922000000', '大悟县', 420922000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420923000000', '420900000000', '420923000000', '云梦县', 420923000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420981000000', '420900000000', '420981000000', '应城市', 420981000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420982000000', '420900000000', '420982000000', '安陆市', 420982000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('420984000000', '420900000000', '420984000000', '汉川市', 420984000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421000000000', '420000000000', '421000000000', '荆州市', 421000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421002000000', '421000000000', '421002000000', '沙市区', 421002000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421003000000', '421000000000', '421003000000', '荆州区', 421003000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421022000000', '421000000000', '421022000000', '公安县', 421022000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421024000000', '421000000000', '421024000000', '江陵县', 421024000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421071000000', '421000000000', '421071000000', '荆州经济技术开发区', 421071000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421081000000', '421000000000', '421081000000', '石首市', 421081000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421083000000', '421000000000', '421083000000', '洪湖市', 421083000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421087000000', '421000000000', '421087000000', '松滋市', 421087000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421088000000', '421000000000', '421088000000', '监利市', 421088000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421100000000', '420000000000', '421100000000', '黄冈市', 421100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421102000000', '421100000000', '421102000000', '黄州区', 421102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421121000000', '421100000000', '421121000000', '团风县', 421121000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421122000000', '421100000000', '421122000000', '红安县', 421122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421123000000', '421100000000', '421123000000', '罗田县', 421123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421124000000', '421100000000', '421124000000', '英山县', 421124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421125000000', '421100000000', '421125000000', '浠水县', 421125000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421126000000', '421100000000', '421126000000', '蕲春县', 421126000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421127000000', '421100000000', '421127000000', '黄梅县', 421127000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421171000000', '421100000000', '421171000000', '龙感湖管理区', 421171000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421181000000', '421100000000', '421181000000', '麻城市', 421181000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421182000000', '421100000000', '421182000000', '武穴市', 421182000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421200000000', '420000000000', '421200000000', '咸宁市', 421200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421202000000', '421200000000', '421202000000', '咸安区', 421202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421221000000', '421200000000', '421221000000', '嘉鱼县', 421221000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421222000000', '421200000000', '421222000000', '通城县', 421222000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421223000000', '421200000000', '421223000000', '崇阳县', 421223000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421224000000', '421200000000', '421224000000', '通山县', 421224000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421281000000', '421200000000', '421281000000', '赤壁市', 421281000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421300000000', '420000000000', '421300000000', '随州市', 421300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421303000000', '421300000000', '421303000000', '曾都区', 421303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421321000000', '421300000000', '421321000000', '随县', 421321000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('421381000000', '421300000000', '421381000000', '广水市', 421381000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('422800000000', '420000000000', '422800000000', '恩施土家族苗族自治州', 422800000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('422801000000', '422800000000', '422801000000', '恩施市', 422801000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('422802000000', '422800000000', '422802000000', '利川市', 422802000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('422822000000', '422800000000', '422822000000', '建始县', 422822000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('422823000000', '422800000000', '422823000000', '巴东县', 422823000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('422825000000', '422800000000', '422825000000', '宣恩县', 422825000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('422826000000', '422800000000', '422826000000', '咸丰县', 422826000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('422827000000', '422800000000', '422827000000', '来凤县', 422827000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('422828000000', '422800000000', '422828000000', '鹤峰县', 422828000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('429004000000', '420000000000', '429004000000', '仙桃市', 429004000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('429005000000', '420000000000', '429005000000', '潜江市', 429005000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('429006000000', '420000000000', '429006000000', '天门市', 429006000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('429021000000', '420000000000', '429021000000', '神农架林区', 429021000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430000000000', 'CHINA', '430000000000', '湖南省', 430000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430100000000', '430000000000', '430100000000', '长沙市', 430100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430102000000', '430100000000', '430102000000', '芙蓉区', 430102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430103000000', '430100000000', '430103000000', '天心区', 430103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430104000000', '430100000000', '430104000000', '岳麓区', 430104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430105000000', '430100000000', '430105000000', '开福区', 430105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430111000000', '430100000000', '430111000000', '雨花区', 430111000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430112000000', '430100000000', '430112000000', '望城区', 430112000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430121000000', '430100000000', '430121000000', '长沙县', 430121000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430181000000', '430100000000', '430181000000', '浏阳市', 430181000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430182000000', '430100000000', '430182000000', '宁乡市', 430182000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430200000000', '430000000000', '430200000000', '株洲市', 430200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430202000000', '430200000000', '430202000000', '荷塘区', 430202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430203000000', '430200000000', '430203000000', '芦淞区', 430203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430204000000', '430200000000', '430204000000', '石峰区', 430204000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430211000000', '430200000000', '430211000000', '天元区', 430211000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430212000000', '430200000000', '430212000000', '渌口区', 430212000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430223000000', '430200000000', '430223000000', '攸县', 430223000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430224000000', '430200000000', '430224000000', '茶陵县', 430224000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430225000000', '430200000000', '430225000000', '炎陵县', 430225000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430281000000', '430200000000', '430281000000', '醴陵市', 430281000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430300000000', '430000000000', '430300000000', '湘潭市', 430300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430302000000', '430300000000', '430302000000', '雨湖区', 430302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430304000000', '430300000000', '430304000000', '岳塘区', 430304000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430321000000', '430300000000', '430321000000', '湘潭县', 430321000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430371000000', '430300000000', '430371000000', '湖南湘潭高新技术产业园区', 430371000000, 'system',
        1729736640000, 'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430372000000', '430300000000', '430372000000', '湘潭昭山示范区', 430372000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430373000000', '430300000000', '430373000000', '湘潭九华示范区', 430373000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430381000000', '430300000000', '430381000000', '湘乡市', 430381000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430382000000', '430300000000', '430382000000', '韶山市', 430382000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430400000000', '430000000000', '430400000000', '衡阳市', 430400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430405000000', '430400000000', '430405000000', '珠晖区', 430405000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430406000000', '430400000000', '430406000000', '雁峰区', 430406000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430407000000', '430400000000', '430407000000', '石鼓区', 430407000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430408000000', '430400000000', '430408000000', '蒸湘区', 430408000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430412000000', '430400000000', '430412000000', '南岳区', 430412000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430421000000', '430400000000', '430421000000', '衡阳县', 430421000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430422000000', '430400000000', '430422000000', '衡南县', 430422000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430423000000', '430400000000', '430423000000', '衡山县', 430423000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430424000000', '430400000000', '430424000000', '衡东县', 430424000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430426000000', '430400000000', '430426000000', '祁东县', 430426000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430473000000', '430400000000', '430473000000', '湖南衡阳松木经济开发区', 430473000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430476000000', '430400000000', '430476000000', '湖南衡阳高新技术产业园区', 430476000000, 'system',
        1729736640000, 'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430481000000', '430400000000', '430481000000', '耒阳市', 430481000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430482000000', '430400000000', '430482000000', '常宁市', 430482000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430500000000', '430000000000', '430500000000', '邵阳市', 430500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430502000000', '430500000000', '430502000000', '双清区', 430502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430503000000', '430500000000', '430503000000', '大祥区', 430503000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430511000000', '430500000000', '430511000000', '北塔区', 430511000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430522000000', '430500000000', '430522000000', '新邵县', 430522000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430523000000', '430500000000', '430523000000', '邵阳县', 430523000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430524000000', '430500000000', '430524000000', '隆回县', 430524000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430525000000', '430500000000', '430525000000', '洞口县', 430525000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430527000000', '430500000000', '430527000000', '绥宁县', 430527000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430528000000', '430500000000', '430528000000', '新宁县', 430528000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430529000000', '430500000000', '430529000000', '城步苗族自治县', 430529000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430581000000', '430500000000', '430581000000', '武冈市', 430581000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430582000000', '430500000000', '430582000000', '邵东市', 430582000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430600000000', '430000000000', '430600000000', '岳阳市', 430600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430602000000', '430600000000', '430602000000', '岳阳楼区', 430602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430603000000', '430600000000', '430603000000', '云溪区', 430603000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430611000000', '430600000000', '430611000000', '君山区', 430611000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430621000000', '430600000000', '430621000000', '岳阳县', 430621000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430623000000', '430600000000', '430623000000', '华容县', 430623000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430624000000', '430600000000', '430624000000', '湘阴县', 430624000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430626000000', '430600000000', '430626000000', '平江县', 430626000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430671000000', '430600000000', '430671000000', '岳阳市屈原管理区', 430671000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430681000000', '430600000000', '430681000000', '汨罗市', 430681000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430682000000', '430600000000', '430682000000', '临湘市', 430682000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430700000000', '430000000000', '430700000000', '常德市', 430700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430702000000', '430700000000', '430702000000', '武陵区', 430702000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430703000000', '430700000000', '430703000000', '鼎城区', 430703000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430721000000', '430700000000', '430721000000', '安乡县', 430721000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430722000000', '430700000000', '430722000000', '汉寿县', 430722000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430723000000', '430700000000', '430723000000', '澧县', 430723000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430724000000', '430700000000', '430724000000', '临澧县', 430724000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430725000000', '430700000000', '430725000000', '桃源县', 430725000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430726000000', '430700000000', '430726000000', '石门县', 430726000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430771000000', '430700000000', '430771000000', '常德市西洞庭管理区', 430771000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430781000000', '430700000000', '430781000000', '津市市', 430781000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430800000000', '430000000000', '430800000000', '张家界市', 430800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430802000000', '430800000000', '430802000000', '永定区', 430802000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430811000000', '430800000000', '430811000000', '武陵源区', 430811000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430821000000', '430800000000', '430821000000', '慈利县', 430821000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430822000000', '430800000000', '430822000000', '桑植县', 430822000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430900000000', '430000000000', '430900000000', '益阳市', 430900000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430902000000', '430900000000', '430902000000', '资阳区', 430902000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430903000000', '430900000000', '430903000000', '赫山区', 430903000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430921000000', '430900000000', '430921000000', '南县', 430921000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430922000000', '430900000000', '430922000000', '桃江县', 430922000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430923000000', '430900000000', '430923000000', '安化县', 430923000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430971000000', '430900000000', '430971000000', '益阳市大通湖管理区', 430971000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430972000000', '430900000000', '430972000000', '湖南益阳高新技术产业园区', 430972000000, 'system',
        1729736640000, 'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('430981000000', '430900000000', '430981000000', '沅江市', 430981000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431000000000', '430000000000', '431000000000', '郴州市', 431000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431002000000', '431000000000', '431002000000', '北湖区', 431002000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431003000000', '431000000000', '431003000000', '苏仙区', 431003000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431021000000', '431000000000', '431021000000', '桂阳县', 431021000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431022000000', '431000000000', '431022000000', '宜章县', 431022000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431023000000', '431000000000', '431023000000', '永兴县', 431023000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431024000000', '431000000000', '431024000000', '嘉禾县', 431024000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431025000000', '431000000000', '431025000000', '临武县', 431025000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431026000000', '431000000000', '431026000000', '汝城县', 431026000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431027000000', '431000000000', '431027000000', '桂东县', 431027000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431028000000', '431000000000', '431028000000', '安仁县', 431028000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431081000000', '431000000000', '431081000000', '资兴市', 431081000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431100000000', '430000000000', '431100000000', '永州市', 431100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431102000000', '431100000000', '431102000000', '零陵区', 431102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431103000000', '431100000000', '431103000000', '冷水滩区', 431103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431122000000', '431100000000', '431122000000', '东安县', 431122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431123000000', '431100000000', '431123000000', '双牌县', 431123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431124000000', '431100000000', '431124000000', '道县', 431124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431125000000', '431100000000', '431125000000', '江永县', 431125000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431126000000', '431100000000', '431126000000', '宁远县', 431126000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431127000000', '431100000000', '431127000000', '蓝山县', 431127000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431128000000', '431100000000', '431128000000', '新田县', 431128000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431129000000', '431100000000', '431129000000', '江华瑶族自治县', 431129000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431171000000', '431100000000', '431171000000', '永州经济技术开发区', 431171000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431173000000', '431100000000', '431173000000', '永州市回龙圩管理区', 431173000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431181000000', '431100000000', '431181000000', '祁阳市', 431181000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431200000000', '430000000000', '431200000000', '怀化市', 431200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431202000000', '431200000000', '431202000000', '鹤城区', 431202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431221000000', '431200000000', '431221000000', '中方县', 431221000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431222000000', '431200000000', '431222000000', '沅陵县', 431222000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431223000000', '431200000000', '431223000000', '辰溪县', 431223000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431224000000', '431200000000', '431224000000', '溆浦县', 431224000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431225000000', '431200000000', '431225000000', '会同县', 431225000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431226000000', '431200000000', '431226000000', '麻阳苗族自治县', 431226000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431227000000', '431200000000', '431227000000', '新晃侗族自治县', 431227000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431228000000', '431200000000', '431228000000', '芷江侗族自治县', 431228000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431229000000', '431200000000', '431229000000', '靖州苗族侗族自治县', 431229000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431230000000', '431200000000', '431230000000', '通道侗族自治县', 431230000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431271000000', '431200000000', '431271000000', '怀化市洪江管理区', 431271000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431281000000', '431200000000', '431281000000', '洪江市', 431281000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431300000000', '430000000000', '431300000000', '娄底市', 431300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431302000000', '431300000000', '431302000000', '娄星区', 431302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431321000000', '431300000000', '431321000000', '双峰县', 431321000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431322000000', '431300000000', '431322000000', '新化县', 431322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431381000000', '431300000000', '431381000000', '冷水江市', 431381000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('431382000000', '431300000000', '431382000000', '涟源市', 431382000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('433100000000', '430000000000', '433100000000', '湘西土家族苗族自治州', 433100000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('433101000000', '433100000000', '433101000000', '吉首市', 433101000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('433122000000', '433100000000', '433122000000', '泸溪县', 433122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('433123000000', '433100000000', '433123000000', '凤凰县', 433123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('433124000000', '433100000000', '433124000000', '花垣县', 433124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('433125000000', '433100000000', '433125000000', '保靖县', 433125000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('433126000000', '433100000000', '433126000000', '古丈县', 433126000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('433127000000', '433100000000', '433127000000', '永顺县', 433127000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('433130000000', '433100000000', '433130000000', '龙山县', 433130000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440000000000', 'CHINA', '440000000000', '广东省', 440000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440100000000', '440000000000', '440100000000', '广州市', 440100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440103000000', '440100000000', '440103000000', '荔湾区', 440103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440104000000', '440100000000', '440104000000', '越秀区', 440104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440105000000', '440100000000', '440105000000', '海珠区', 440105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440106000000', '440100000000', '440106000000', '天河区', 440106000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440111000000', '440100000000', '440111000000', '白云区', 440111000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440112000000', '440100000000', '440112000000', '黄埔区', 440112000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440113000000', '440100000000', '440113000000', '番禺区', 440113000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440114000000', '440100000000', '440114000000', '花都区', 440114000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440115000000', '440100000000', '440115000000', '南沙区', 440115000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440117000000', '440100000000', '440117000000', '从化区', 440117000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440118000000', '440100000000', '440118000000', '增城区', 440118000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440200000000', '440000000000', '440200000000', '韶关市', 440200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440203000000', '440200000000', '440203000000', '武江区', 440203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440204000000', '440200000000', '440204000000', '浈江区', 440204000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440205000000', '440200000000', '440205000000', '曲江区', 440205000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440222000000', '440200000000', '440222000000', '始兴县', 440222000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440224000000', '440200000000', '440224000000', '仁化县', 440224000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440229000000', '440200000000', '440229000000', '翁源县', 440229000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440232000000', '440200000000', '440232000000', '乳源瑶族自治县', 440232000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440233000000', '440200000000', '440233000000', '新丰县', 440233000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440281000000', '440200000000', '440281000000', '乐昌市', 440281000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440282000000', '440200000000', '440282000000', '南雄市', 440282000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440300000000', '440000000000', '440300000000', '深圳市', 440300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440303000000', '440300000000', '440303000000', '罗湖区', 440303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440304000000', '440300000000', '440304000000', '福田区', 440304000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440305000000', '440300000000', '440305000000', '南山区', 440305000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440306000000', '440300000000', '440306000000', '宝安区', 440306000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440307000000', '440300000000', '440307000000', '龙岗区', 440307000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440308000000', '440300000000', '440308000000', '盐田区', 440308000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440309000000', '440300000000', '440309000000', '龙华区', 440309000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440310000000', '440300000000', '440310000000', '坪山区', 440310000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440311000000', '440300000000', '440311000000', '光明区', 440311000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440400000000', '440000000000', '440400000000', '珠海市', 440400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440402000000', '440400000000', '440402000000', '香洲区', 440402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440403000000', '440400000000', '440403000000', '斗门区', 440403000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440404000000', '440400000000', '440404000000', '金湾区', 440404000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440500000000', '440000000000', '440500000000', '汕头市', 440500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440507000000', '440500000000', '440507000000', '龙湖区', 440507000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440511000000', '440500000000', '440511000000', '金平区', 440511000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440512000000', '440500000000', '440512000000', '濠江区', 440512000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440513000000', '440500000000', '440513000000', '潮阳区', 440513000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440514000000', '440500000000', '440514000000', '潮南区', 440514000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440515000000', '440500000000', '440515000000', '澄海区', 440515000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440523000000', '440500000000', '440523000000', '南澳县', 440523000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440600000000', '440000000000', '440600000000', '佛山市', 440600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440604000000', '440600000000', '440604000000', '禅城区', 440604000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440605000000', '440600000000', '440605000000', '南海区', 440605000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440606000000', '440600000000', '440606000000', '顺德区', 440606000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440607000000', '440600000000', '440607000000', '三水区', 440607000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440608000000', '440600000000', '440608000000', '高明区', 440608000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440700000000', '440000000000', '440700000000', '江门市', 440700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440703000000', '440700000000', '440703000000', '蓬江区', 440703000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440704000000', '440700000000', '440704000000', '江海区', 440704000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440705000000', '440700000000', '440705000000', '新会区', 440705000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440781000000', '440700000000', '440781000000', '台山市', 440781000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440783000000', '440700000000', '440783000000', '开平市', 440783000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440784000000', '440700000000', '440784000000', '鹤山市', 440784000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440785000000', '440700000000', '440785000000', '恩平市', 440785000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440800000000', '440000000000', '440800000000', '湛江市', 440800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440802000000', '440800000000', '440802000000', '赤坎区', 440802000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440803000000', '440800000000', '440803000000', '霞山区', 440803000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440804000000', '440800000000', '440804000000', '坡头区', 440804000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440811000000', '440800000000', '440811000000', '麻章区', 440811000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440823000000', '440800000000', '440823000000', '遂溪县', 440823000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440825000000', '440800000000', '440825000000', '徐闻县', 440825000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440881000000', '440800000000', '440881000000', '廉江市', 440881000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440882000000', '440800000000', '440882000000', '雷州市', 440882000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440883000000', '440800000000', '440883000000', '吴川市', 440883000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440900000000', '440000000000', '440900000000', '茂名市', 440900000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440902000000', '440900000000', '440902000000', '茂南区', 440902000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440904000000', '440900000000', '440904000000', '电白区', 440904000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440981000000', '440900000000', '440981000000', '高州市', 440981000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440982000000', '440900000000', '440982000000', '化州市', 440982000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('440983000000', '440900000000', '440983000000', '信宜市', 440983000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441200000000', '440000000000', '441200000000', '肇庆市', 441200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441202000000', '441200000000', '441202000000', '端州区', 441202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441203000000', '441200000000', '441203000000', '鼎湖区', 441203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441204000000', '441200000000', '441204000000', '高要区', 441204000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441223000000', '441200000000', '441223000000', '广宁县', 441223000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441224000000', '441200000000', '441224000000', '怀集县', 441224000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441225000000', '441200000000', '441225000000', '封开县', 441225000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441226000000', '441200000000', '441226000000', '德庆县', 441226000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441284000000', '441200000000', '441284000000', '四会市', 441284000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441300000000', '440000000000', '441300000000', '惠州市', 441300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441302000000', '441300000000', '441302000000', '惠城区', 441302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441303000000', '441300000000', '441303000000', '惠阳区', 441303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441322000000', '441300000000', '441322000000', '博罗县', 441322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441323000000', '441300000000', '441323000000', '惠东县', 441323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441324000000', '441300000000', '441324000000', '龙门县', 441324000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441400000000', '440000000000', '441400000000', '梅州市', 441400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441402000000', '441400000000', '441402000000', '梅江区', 441402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441403000000', '441400000000', '441403000000', '梅县区', 441403000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441422000000', '441400000000', '441422000000', '大埔县', 441422000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441423000000', '441400000000', '441423000000', '丰顺县', 441423000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441424000000', '441400000000', '441424000000', '五华县', 441424000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441426000000', '441400000000', '441426000000', '平远县', 441426000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441427000000', '441400000000', '441427000000', '蕉岭县', 441427000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441481000000', '441400000000', '441481000000', '兴宁市', 441481000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441500000000', '440000000000', '441500000000', '汕尾市', 441500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441502000000', '441500000000', '441502000000', '城区', 441502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441521000000', '441500000000', '441521000000', '海丰县', 441521000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441523000000', '441500000000', '441523000000', '陆河县', 441523000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441581000000', '441500000000', '441581000000', '陆丰市', 441581000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441600000000', '440000000000', '441600000000', '河源市', 441600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441602000000', '441600000000', '441602000000', '源城区', 441602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441621000000', '441600000000', '441621000000', '紫金县', 441621000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441622000000', '441600000000', '441622000000', '龙川县', 441622000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441623000000', '441600000000', '441623000000', '连平县', 441623000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441624000000', '441600000000', '441624000000', '和平县', 441624000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441625000000', '441600000000', '441625000000', '东源县', 441625000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441700000000', '440000000000', '441700000000', '阳江市', 441700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441702000000', '441700000000', '441702000000', '江城区', 441702000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441704000000', '441700000000', '441704000000', '阳东区', 441704000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441721000000', '441700000000', '441721000000', '阳西县', 441721000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441781000000', '441700000000', '441781000000', '阳春市', 441781000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441800000000', '440000000000', '441800000000', '清远市', 441800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441802000000', '441800000000', '441802000000', '清城区', 441802000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441803000000', '441800000000', '441803000000', '清新区', 441803000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441821000000', '441800000000', '441821000000', '佛冈县', 441821000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441823000000', '441800000000', '441823000000', '阳山县', 441823000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441825000000', '441800000000', '441825000000', '连山壮族瑶族自治县', 441825000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441826000000', '441800000000', '441826000000', '连南瑶族自治县', 441826000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441881000000', '441800000000', '441881000000', '英德市', 441881000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441882000000', '441800000000', '441882000000', '连州市', 441882000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('441900000000', '440000000000', '441900000000', '东莞市', 441900000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('442000000000', '440000000000', '442000000000', '中山市', 442000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('445100000000', '440000000000', '445100000000', '潮州市', 445100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('445102000000', '445100000000', '445102000000', '湘桥区', 445102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('445103000000', '445100000000', '445103000000', '潮安区', 445103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('445122000000', '445100000000', '445122000000', '饶平县', 445122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('445200000000', '440000000000', '445200000000', '揭阳市', 445200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('445202000000', '445200000000', '445202000000', '榕城区', 445202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('445203000000', '445200000000', '445203000000', '揭东区', 445203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('445222000000', '445200000000', '445222000000', '揭西县', 445222000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('445224000000', '445200000000', '445224000000', '惠来县', 445224000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('445281000000', '445200000000', '445281000000', '普宁市', 445281000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('445300000000', '440000000000', '445300000000', '云浮市', 445300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('445302000000', '445300000000', '445302000000', '云城区', 445302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('445303000000', '445300000000', '445303000000', '云安区', 445303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('445321000000', '445300000000', '445321000000', '新兴县', 445321000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('445322000000', '445300000000', '445322000000', '郁南县', 445322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('445381000000', '445300000000', '445381000000', '罗定市', 445381000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450000000000', 'CHINA', '450000000000', '广西壮族自治区', 450000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450100000000', '450000000000', '450100000000', '南宁市', 450100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450102000000', '450100000000', '450102000000', '兴宁区', 450102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450103000000', '450100000000', '450103000000', '青秀区', 450103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450105000000', '450100000000', '450105000000', '江南区', 450105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450107000000', '450100000000', '450107000000', '西乡塘区', 450107000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450108000000', '450100000000', '450108000000', '良庆区', 450108000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450109000000', '450100000000', '450109000000', '邕宁区', 450109000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450110000000', '450100000000', '450110000000', '武鸣区', 450110000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450123000000', '450100000000', '450123000000', '隆安县', 450123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450124000000', '450100000000', '450124000000', '马山县', 450124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450125000000', '450100000000', '450125000000', '上林县', 450125000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450126000000', '450100000000', '450126000000', '宾阳县', 450126000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450181000000', '450100000000', '450181000000', '横州市', 450181000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450200000000', '450000000000', '450200000000', '柳州市', 450200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450202000000', '450200000000', '450202000000', '城中区', 450202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450203000000', '450200000000', '450203000000', '鱼峰区', 450203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450204000000', '450200000000', '450204000000', '柳南区', 450204000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450205000000', '450200000000', '450205000000', '柳北区', 450205000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450206000000', '450200000000', '450206000000', '柳江区', 450206000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450222000000', '450200000000', '450222000000', '柳城县', 450222000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450223000000', '450200000000', '450223000000', '鹿寨县', 450223000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450224000000', '450200000000', '450224000000', '融安县', 450224000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450225000000', '450200000000', '450225000000', '融水苗族自治县', 450225000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450226000000', '450200000000', '450226000000', '三江侗族自治县', 450226000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450300000000', '450000000000', '450300000000', '桂林市', 450300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450302000000', '450300000000', '450302000000', '秀峰区', 450302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450303000000', '450300000000', '450303000000', '叠彩区', 450303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450304000000', '450300000000', '450304000000', '象山区', 450304000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450305000000', '450300000000', '450305000000', '七星区', 450305000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450311000000', '450300000000', '450311000000', '雁山区', 450311000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450312000000', '450300000000', '450312000000', '临桂区', 450312000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450321000000', '450300000000', '450321000000', '阳朔县', 450321000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450323000000', '450300000000', '450323000000', '灵川县', 450323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450324000000', '450300000000', '450324000000', '全州县', 450324000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450325000000', '450300000000', '450325000000', '兴安县', 450325000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450326000000', '450300000000', '450326000000', '永福县', 450326000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450327000000', '450300000000', '450327000000', '灌阳县', 450327000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450328000000', '450300000000', '450328000000', '龙胜各族自治县', 450328000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450329000000', '450300000000', '450329000000', '资源县', 450329000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450330000000', '450300000000', '450330000000', '平乐县', 450330000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450332000000', '450300000000', '450332000000', '恭城瑶族自治县', 450332000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450381000000', '450300000000', '450381000000', '荔浦市', 450381000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450400000000', '450000000000', '450400000000', '梧州市', 450400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450403000000', '450400000000', '450403000000', '万秀区', 450403000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450405000000', '450400000000', '450405000000', '长洲区', 450405000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450406000000', '450400000000', '450406000000', '龙圩区', 450406000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450421000000', '450400000000', '450421000000', '苍梧县', 450421000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450422000000', '450400000000', '450422000000', '藤县', 450422000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450423000000', '450400000000', '450423000000', '蒙山县', 450423000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450481000000', '450400000000', '450481000000', '岑溪市', 450481000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450500000000', '450000000000', '450500000000', '北海市', 450500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450502000000', '450500000000', '450502000000', '海城区', 450502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450503000000', '450500000000', '450503000000', '银海区', 450503000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450512000000', '450500000000', '450512000000', '铁山港区', 450512000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450521000000', '450500000000', '450521000000', '合浦县', 450521000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450600000000', '450000000000', '450600000000', '防城港市', 450600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450602000000', '450600000000', '450602000000', '港口区', 450602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450603000000', '450600000000', '450603000000', '防城区', 450603000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450621000000', '450600000000', '450621000000', '上思县', 450621000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450681000000', '450600000000', '450681000000', '东兴市', 450681000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450700000000', '450000000000', '450700000000', '钦州市', 450700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450702000000', '450700000000', '450702000000', '钦南区', 450702000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450703000000', '450700000000', '450703000000', '钦北区', 450703000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450721000000', '450700000000', '450721000000', '灵山县', 450721000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450722000000', '450700000000', '450722000000', '浦北县', 450722000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450800000000', '450000000000', '450800000000', '贵港市', 450800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450802000000', '450800000000', '450802000000', '港北区', 450802000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450803000000', '450800000000', '450803000000', '港南区', 450803000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450804000000', '450800000000', '450804000000', '覃塘区', 450804000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450821000000', '450800000000', '450821000000', '平南县', 450821000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450881000000', '450800000000', '450881000000', '桂平市', 450881000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450900000000', '450000000000', '450900000000', '玉林市', 450900000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450902000000', '450900000000', '450902000000', '玉州区', 450902000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450903000000', '450900000000', '450903000000', '福绵区', 450903000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450921000000', '450900000000', '450921000000', '容县', 450921000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450922000000', '450900000000', '450922000000', '陆川县', 450922000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450923000000', '450900000000', '450923000000', '博白县', 450923000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450924000000', '450900000000', '450924000000', '兴业县', 450924000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('450981000000', '450900000000', '450981000000', '北流市', 450981000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451000000000', '450000000000', '451000000000', '百色市', 451000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451002000000', '451000000000', '451002000000', '右江区', 451002000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451003000000', '451000000000', '451003000000', '田阳区', 451003000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451022000000', '451000000000', '451022000000', '田东县', 451022000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451024000000', '451000000000', '451024000000', '德保县', 451024000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451026000000', '451000000000', '451026000000', '那坡县', 451026000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451027000000', '451000000000', '451027000000', '凌云县', 451027000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451028000000', '451000000000', '451028000000', '乐业县', 451028000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451029000000', '451000000000', '451029000000', '田林县', 451029000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451030000000', '451000000000', '451030000000', '西林县', 451030000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451031000000', '451000000000', '451031000000', '隆林各族自治县', 451031000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451081000000', '451000000000', '451081000000', '靖西市', 451081000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451082000000', '451000000000', '451082000000', '平果市', 451082000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451100000000', '450000000000', '451100000000', '贺州市', 451100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451102000000', '451100000000', '451102000000', '八步区', 451102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451103000000', '451100000000', '451103000000', '平桂区', 451103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451121000000', '451100000000', '451121000000', '昭平县', 451121000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451122000000', '451100000000', '451122000000', '钟山县', 451122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451123000000', '451100000000', '451123000000', '富川瑶族自治县', 451123000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451200000000', '450000000000', '451200000000', '河池市', 451200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451202000000', '451200000000', '451202000000', '金城江区', 451202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451203000000', '451200000000', '451203000000', '宜州区', 451203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451221000000', '451200000000', '451221000000', '南丹县', 451221000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451222000000', '451200000000', '451222000000', '天峨县', 451222000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451223000000', '451200000000', '451223000000', '凤山县', 451223000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451224000000', '451200000000', '451224000000', '东兰县', 451224000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451225000000', '451200000000', '451225000000', '罗城仫佬族自治县', 451225000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451226000000', '451200000000', '451226000000', '环江毛南族自治县', 451226000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451227000000', '451200000000', '451227000000', '巴马瑶族自治县', 451227000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451228000000', '451200000000', '451228000000', '都安瑶族自治县', 451228000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451229000000', '451200000000', '451229000000', '大化瑶族自治县', 451229000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451300000000', '450000000000', '451300000000', '来宾市', 451300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451302000000', '451300000000', '451302000000', '兴宾区', 451302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451321000000', '451300000000', '451321000000', '忻城县', 451321000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451322000000', '451300000000', '451322000000', '象州县', 451322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451323000000', '451300000000', '451323000000', '武宣县', 451323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451324000000', '451300000000', '451324000000', '金秀瑶族自治县', 451324000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451381000000', '451300000000', '451381000000', '合山市', 451381000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451400000000', '450000000000', '451400000000', '崇左市', 451400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451402000000', '451400000000', '451402000000', '江州区', 451402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451421000000', '451400000000', '451421000000', '扶绥县', 451421000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451422000000', '451400000000', '451422000000', '宁明县', 451422000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451423000000', '451400000000', '451423000000', '龙州县', 451423000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451424000000', '451400000000', '451424000000', '大新县', 451424000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451425000000', '451400000000', '451425000000', '天等县', 451425000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('451481000000', '451400000000', '451481000000', '凭祥市', 451481000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('460000000000', 'CHINA', '460000000000', '海南省', 460000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('460100000000', '460000000000', '460100000000', '海口市', 460100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('460105000000', '460100000000', '460105000000', '秀英区', 460105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('460106000000', '460100000000', '460106000000', '龙华区', 460106000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('460107000000', '460100000000', '460107000000', '琼山区', 460107000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('460108000000', '460100000000', '460108000000', '美兰区', 460108000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('460200000000', '460000000000', '460200000000', '三亚市', 460200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('460202000000', '460200000000', '460202000000', '海棠区', 460202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('460203000000', '460200000000', '460203000000', '吉阳区', 460203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('460204000000', '460200000000', '460204000000', '天涯区', 460204000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('460205000000', '460200000000', '460205000000', '崖州区', 460205000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('460300000000', '460000000000', '460300000000', '三沙市', 460300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('460321000000', '460300000000', '460321000000', '西沙群岛', 460321000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('460322000000', '460300000000', '460322000000', '南沙群岛', 460322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('460323000000', '460300000000', '460323000000', '中沙群岛的岛礁及其海域', 460323000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('460400000000', '460000000000', '460400000000', '儋州市', 460400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('469001000000', '460000000000', '469001000000', '五指山市', 469001000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('469002000000', '460000000000', '469002000000', '琼海市', 469002000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('469005000000', '460000000000', '469005000000', '文昌市', 469005000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('469006000000', '460000000000', '469006000000', '万宁市', 469006000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('469007000000', '460000000000', '469007000000', '东方市', 469007000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('469021000000', '460000000000', '469021000000', '定安县', 469021000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('469022000000', '460000000000', '469022000000', '屯昌县', 469022000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('469023000000', '460000000000', '469023000000', '澄迈县', 469023000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('469024000000', '460000000000', '469024000000', '临高县', 469024000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('469025000000', '460000000000', '469025000000', '白沙黎族自治县', 469025000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('469026000000', '460000000000', '469026000000', '昌江黎族自治县', 469026000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('469027000000', '460000000000', '469027000000', '乐东黎族自治县', 469027000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('469028000000', '460000000000', '469028000000', '陵水黎族自治县', 469028000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('469029000000', '460000000000', '469029000000', '保亭黎族苗族自治县', 469029000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('469030000000', '460000000000', '469030000000', '琼中黎族苗族自治县', 469030000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500000000000', 'CHINA', '500000000000', '重庆市', 500000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500100000000', '500000000000', '500100000000', '重庆市', 500100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500101000000', '500100000000', '500101000000', '万州区', 500101000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500102000000', '500100000000', '500102000000', '涪陵区', 500102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500103000000', '500100000000', '500103000000', '渝中区', 500103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500104000000', '500100000000', '500104000000', '大渡口区', 500104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500105000000', '500100000000', '500105000000', '江北区', 500105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500106000000', '500100000000', '500106000000', '沙坪坝区', 500106000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500107000000', '500100000000', '500107000000', '九龙坡区', 500107000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500108000000', '500100000000', '500108000000', '南岸区', 500108000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500109000000', '500100000000', '500109000000', '北碚区', 500109000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500110000000', '500100000000', '500110000000', '綦江区', 500110000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500111000000', '500100000000', '500111000000', '大足区', 500111000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500112000000', '500100000000', '500112000000', '渝北区', 500112000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500113000000', '500100000000', '500113000000', '巴南区', 500113000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500114000000', '500100000000', '500114000000', '黔江区', 500114000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500115000000', '500100000000', '500115000000', '长寿区', 500115000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500116000000', '500100000000', '500116000000', '江津区', 500116000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500117000000', '500100000000', '500117000000', '合川区', 500117000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500118000000', '500100000000', '500118000000', '永川区', 500118000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500119000000', '500100000000', '500119000000', '南川区', 500119000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500120000000', '500100000000', '500120000000', '璧山区', 500120000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500151000000', '500100000000', '500151000000', '铜梁区', 500151000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500152000000', '500100000000', '500152000000', '潼南区', 500152000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500153000000', '500100000000', '500153000000', '荣昌区', 500153000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500154000000', '500100000000', '500154000000', '开州区', 500154000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500155000000', '500100000000', '500155000000', '梁平区', 500155000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500156000000', '500100000000', '500156000000', '武隆区', 500156000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500229000000', '500100000000', '500229000000', '城口县', 500229000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500230000000', '500100000000', '500230000000', '丰都县', 500230000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500231000000', '500100000000', '500231000000', '垫江县', 500231000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500233000000', '500100000000', '500233000000', '忠县', 500233000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500235000000', '500100000000', '500235000000', '云阳县', 500235000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500236000000', '500100000000', '500236000000', '奉节县', 500236000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500237000000', '500100000000', '500237000000', '巫山县', 500237000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500238000000', '500100000000', '500238000000', '巫溪县', 500238000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500240000000', '500100000000', '500240000000', '石柱土家族自治县', 500240000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500241000000', '500100000000', '500241000000', '秀山土家族苗族自治县', 500241000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500242000000', '500100000000', '500242000000', '酉阳土家族苗族自治县', 500242000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('500243000000', '500100000000', '500243000000', '彭水苗族土家族自治县', 500243000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510000000000', 'CHINA', '510000000000', '四川省', 510000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510100000000', '510000000000', '510100000000', '成都市', 510100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510104000000', '510100000000', '510104000000', '锦江区', 510104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510105000000', '510100000000', '510105000000', '青羊区', 510105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510106000000', '510100000000', '510106000000', '金牛区', 510106000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510107000000', '510100000000', '510107000000', '武侯区', 510107000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510108000000', '510100000000', '510108000000', '成华区', 510108000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510112000000', '510100000000', '510112000000', '龙泉驿区', 510112000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510113000000', '510100000000', '510113000000', '青白江区', 510113000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510114000000', '510100000000', '510114000000', '新都区', 510114000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510115000000', '510100000000', '510115000000', '温江区', 510115000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510116000000', '510100000000', '510116000000', '双流区', 510116000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510117000000', '510100000000', '510117000000', '郫都区', 510117000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510118000000', '510100000000', '510118000000', '新津区', 510118000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510121000000', '510100000000', '510121000000', '金堂县', 510121000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510129000000', '510100000000', '510129000000', '大邑县', 510129000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510131000000', '510100000000', '510131000000', '蒲江县', 510131000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510181000000', '510100000000', '510181000000', '都江堰市', 510181000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510182000000', '510100000000', '510182000000', '彭州市', 510182000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510183000000', '510100000000', '510183000000', '邛崃市', 510183000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510184000000', '510100000000', '510184000000', '崇州市', 510184000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510185000000', '510100000000', '510185000000', '简阳市', 510185000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510300000000', '510000000000', '510300000000', '自贡市', 510300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510302000000', '510300000000', '510302000000', '自流井区', 510302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510303000000', '510300000000', '510303000000', '贡井区', 510303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510304000000', '510300000000', '510304000000', '大安区', 510304000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510311000000', '510300000000', '510311000000', '沿滩区', 510311000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510321000000', '510300000000', '510321000000', '荣县', 510321000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510322000000', '510300000000', '510322000000', '富顺县', 510322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510400000000', '510000000000', '510400000000', '攀枝花市', 510400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510402000000', '510400000000', '510402000000', '东区', 510402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510403000000', '510400000000', '510403000000', '西区', 510403000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510411000000', '510400000000', '510411000000', '仁和区', 510411000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510421000000', '510400000000', '510421000000', '米易县', 510421000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510422000000', '510400000000', '510422000000', '盐边县', 510422000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510500000000', '510000000000', '510500000000', '泸州市', 510500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510502000000', '510500000000', '510502000000', '江阳区', 510502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510503000000', '510500000000', '510503000000', '纳溪区', 510503000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510504000000', '510500000000', '510504000000', '龙马潭区', 510504000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510521000000', '510500000000', '510521000000', '泸县', 510521000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510522000000', '510500000000', '510522000000', '合江县', 510522000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510524000000', '510500000000', '510524000000', '叙永县', 510524000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510525000000', '510500000000', '510525000000', '古蔺县', 510525000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510600000000', '510000000000', '510600000000', '德阳市', 510600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510603000000', '510600000000', '510603000000', '旌阳区', 510603000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510604000000', '510600000000', '510604000000', '罗江区', 510604000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510623000000', '510600000000', '510623000000', '中江县', 510623000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510681000000', '510600000000', '510681000000', '广汉市', 510681000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510682000000', '510600000000', '510682000000', '什邡市', 510682000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510683000000', '510600000000', '510683000000', '绵竹市', 510683000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510700000000', '510000000000', '510700000000', '绵阳市', 510700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510703000000', '510700000000', '510703000000', '涪城区', 510703000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510704000000', '510700000000', '510704000000', '游仙区', 510704000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510705000000', '510700000000', '510705000000', '安州区', 510705000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510722000000', '510700000000', '510722000000', '三台县', 510722000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510723000000', '510700000000', '510723000000', '盐亭县', 510723000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510725000000', '510700000000', '510725000000', '梓潼县', 510725000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510726000000', '510700000000', '510726000000', '北川羌族自治县', 510726000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510727000000', '510700000000', '510727000000', '平武县', 510727000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510781000000', '510700000000', '510781000000', '江油市', 510781000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510800000000', '510000000000', '510800000000', '广元市', 510800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510802000000', '510800000000', '510802000000', '利州区', 510802000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510811000000', '510800000000', '510811000000', '昭化区', 510811000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510812000000', '510800000000', '510812000000', '朝天区', 510812000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510821000000', '510800000000', '510821000000', '旺苍县', 510821000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510822000000', '510800000000', '510822000000', '青川县', 510822000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510823000000', '510800000000', '510823000000', '剑阁县', 510823000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510824000000', '510800000000', '510824000000', '苍溪县', 510824000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510900000000', '510000000000', '510900000000', '遂宁市', 510900000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510903000000', '510900000000', '510903000000', '船山区', 510903000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510904000000', '510900000000', '510904000000', '安居区', 510904000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510921000000', '510900000000', '510921000000', '蓬溪县', 510921000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510923000000', '510900000000', '510923000000', '大英县', 510923000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('510981000000', '510900000000', '510981000000', '射洪市', 510981000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511000000000', '510000000000', '511000000000', '内江市', 511000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511002000000', '511000000000', '511002000000', '市中区', 511002000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511011000000', '511000000000', '511011000000', '东兴区', 511011000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511024000000', '511000000000', '511024000000', '威远县', 511024000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511025000000', '511000000000', '511025000000', '资中县', 511025000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511083000000', '511000000000', '511083000000', '隆昌市', 511083000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511100000000', '510000000000', '511100000000', '乐山市', 511100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511102000000', '511100000000', '511102000000', '市中区', 511102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511111000000', '511100000000', '511111000000', '沙湾区', 511111000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511112000000', '511100000000', '511112000000', '五通桥区', 511112000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511113000000', '511100000000', '511113000000', '金口河区', 511113000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511123000000', '511100000000', '511123000000', '犍为县', 511123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511124000000', '511100000000', '511124000000', '井研县', 511124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511126000000', '511100000000', '511126000000', '夹江县', 511126000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511129000000', '511100000000', '511129000000', '沐川县', 511129000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511132000000', '511100000000', '511132000000', '峨边彝族自治县', 511132000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511133000000', '511100000000', '511133000000', '马边彝族自治县', 511133000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511181000000', '511100000000', '511181000000', '峨眉山市', 511181000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511300000000', '510000000000', '511300000000', '南充市', 511300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511302000000', '511300000000', '511302000000', '顺庆区', 511302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511303000000', '511300000000', '511303000000', '高坪区', 511303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511304000000', '511300000000', '511304000000', '嘉陵区', 511304000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511321000000', '511300000000', '511321000000', '南部县', 511321000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511322000000', '511300000000', '511322000000', '营山县', 511322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511323000000', '511300000000', '511323000000', '蓬安县', 511323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511324000000', '511300000000', '511324000000', '仪陇县', 511324000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511325000000', '511300000000', '511325000000', '西充县', 511325000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511381000000', '511300000000', '511381000000', '阆中市', 511381000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511400000000', '510000000000', '511400000000', '眉山市', 511400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511402000000', '511400000000', '511402000000', '东坡区', 511402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511403000000', '511400000000', '511403000000', '彭山区', 511403000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511421000000', '511400000000', '511421000000', '仁寿县', 511421000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511423000000', '511400000000', '511423000000', '洪雅县', 511423000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511424000000', '511400000000', '511424000000', '丹棱县', 511424000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511425000000', '511400000000', '511425000000', '青神县', 511425000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511500000000', '510000000000', '511500000000', '宜宾市', 511500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511502000000', '511500000000', '511502000000', '翠屏区', 511502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511503000000', '511500000000', '511503000000', '南溪区', 511503000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511504000000', '511500000000', '511504000000', '叙州区', 511504000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511523000000', '511500000000', '511523000000', '江安县', 511523000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511524000000', '511500000000', '511524000000', '长宁县', 511524000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511525000000', '511500000000', '511525000000', '高县', 511525000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511526000000', '511500000000', '511526000000', '珙县', 511526000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511527000000', '511500000000', '511527000000', '筠连县', 511527000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511528000000', '511500000000', '511528000000', '兴文县', 511528000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511529000000', '511500000000', '511529000000', '屏山县', 511529000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511600000000', '510000000000', '511600000000', '广安市', 511600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511602000000', '511600000000', '511602000000', '广安区', 511602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511603000000', '511600000000', '511603000000', '前锋区', 511603000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511621000000', '511600000000', '511621000000', '岳池县', 511621000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511622000000', '511600000000', '511622000000', '武胜县', 511622000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511623000000', '511600000000', '511623000000', '邻水县', 511623000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511681000000', '511600000000', '511681000000', '华蓥市', 511681000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511700000000', '510000000000', '511700000000', '达州市', 511700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511702000000', '511700000000', '511702000000', '通川区', 511702000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511703000000', '511700000000', '511703000000', '达川区', 511703000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511722000000', '511700000000', '511722000000', '宣汉县', 511722000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511723000000', '511700000000', '511723000000', '开江县', 511723000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511724000000', '511700000000', '511724000000', '大竹县', 511724000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511725000000', '511700000000', '511725000000', '渠县', 511725000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511781000000', '511700000000', '511781000000', '万源市', 511781000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511800000000', '510000000000', '511800000000', '雅安市', 511800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511802000000', '511800000000', '511802000000', '雨城区', 511802000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511803000000', '511800000000', '511803000000', '名山区', 511803000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511822000000', '511800000000', '511822000000', '荥经县', 511822000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511823000000', '511800000000', '511823000000', '汉源县', 511823000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511824000000', '511800000000', '511824000000', '石棉县', 511824000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511825000000', '511800000000', '511825000000', '天全县', 511825000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511826000000', '511800000000', '511826000000', '芦山县', 511826000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511827000000', '511800000000', '511827000000', '宝兴县', 511827000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511900000000', '510000000000', '511900000000', '巴中市', 511900000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511902000000', '511900000000', '511902000000', '巴州区', 511902000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511903000000', '511900000000', '511903000000', '恩阳区', 511903000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511921000000', '511900000000', '511921000000', '通江县', 511921000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511922000000', '511900000000', '511922000000', '南江县', 511922000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('511923000000', '511900000000', '511923000000', '平昌县', 511923000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('512000000000', '510000000000', '512000000000', '资阳市', 512000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('512002000000', '512000000000', '512002000000', '雁江区', 512002000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('512021000000', '512000000000', '512021000000', '安岳县', 512021000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('512022000000', '512000000000', '512022000000', '乐至县', 512022000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513200000000', '510000000000', '513200000000', '阿坝藏族羌族自治州', 513200000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513201000000', '513200000000', '513201000000', '马尔康市', 513201000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513221000000', '513200000000', '513221000000', '汶川县', 513221000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513222000000', '513200000000', '513222000000', '理县', 513222000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513223000000', '513200000000', '513223000000', '茂县', 513223000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513224000000', '513200000000', '513224000000', '松潘县', 513224000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513225000000', '513200000000', '513225000000', '九寨沟县', 513225000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513226000000', '513200000000', '513226000000', '金川县', 513226000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513227000000', '513200000000', '513227000000', '小金县', 513227000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513228000000', '513200000000', '513228000000', '黑水县', 513228000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513230000000', '513200000000', '513230000000', '壤塘县', 513230000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513231000000', '513200000000', '513231000000', '阿坝县', 513231000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513232000000', '513200000000', '513232000000', '若尔盖县', 513232000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513233000000', '513200000000', '513233000000', '红原县', 513233000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513300000000', '510000000000', '513300000000', '甘孜藏族自治州', 513300000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513301000000', '513300000000', '513301000000', '康定市', 513301000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513322000000', '513300000000', '513322000000', '泸定县', 513322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513323000000', '513300000000', '513323000000', '丹巴县', 513323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513324000000', '513300000000', '513324000000', '九龙县', 513324000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513325000000', '513300000000', '513325000000', '雅江县', 513325000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513326000000', '513300000000', '513326000000', '道孚县', 513326000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513327000000', '513300000000', '513327000000', '炉霍县', 513327000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513328000000', '513300000000', '513328000000', '甘孜县', 513328000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513329000000', '513300000000', '513329000000', '新龙县', 513329000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513330000000', '513300000000', '513330000000', '德格县', 513330000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513331000000', '513300000000', '513331000000', '白玉县', 513331000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513332000000', '513300000000', '513332000000', '石渠县', 513332000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513333000000', '513300000000', '513333000000', '色达县', 513333000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513334000000', '513300000000', '513334000000', '理塘县', 513334000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513335000000', '513300000000', '513335000000', '巴塘县', 513335000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513336000000', '513300000000', '513336000000', '乡城县', 513336000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513337000000', '513300000000', '513337000000', '稻城县', 513337000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513338000000', '513300000000', '513338000000', '得荣县', 513338000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513400000000', '510000000000', '513400000000', '凉山彝族自治州', 513400000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513401000000', '513400000000', '513401000000', '西昌市', 513401000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513402000000', '513400000000', '513402000000', '会理市', 513402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513422000000', '513400000000', '513422000000', '木里藏族自治县', 513422000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513423000000', '513400000000', '513423000000', '盐源县', 513423000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513424000000', '513400000000', '513424000000', '德昌县', 513424000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513426000000', '513400000000', '513426000000', '会东县', 513426000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513427000000', '513400000000', '513427000000', '宁南县', 513427000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513428000000', '513400000000', '513428000000', '普格县', 513428000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513429000000', '513400000000', '513429000000', '布拖县', 513429000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513430000000', '513400000000', '513430000000', '金阳县', 513430000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513431000000', '513400000000', '513431000000', '昭觉县', 513431000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513432000000', '513400000000', '513432000000', '喜德县', 513432000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513433000000', '513400000000', '513433000000', '冕宁县', 513433000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513434000000', '513400000000', '513434000000', '越西县', 513434000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513435000000', '513400000000', '513435000000', '甘洛县', 513435000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513436000000', '513400000000', '513436000000', '美姑县', 513436000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('513437000000', '513400000000', '513437000000', '雷波县', 513437000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520000000000', 'CHINA', '520000000000', '贵州省', 520000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520100000000', '520000000000', '520100000000', '贵阳市', 520100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520102000000', '520100000000', '520102000000', '南明区', 520102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520103000000', '520100000000', '520103000000', '云岩区', 520103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520111000000', '520100000000', '520111000000', '花溪区', 520111000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520112000000', '520100000000', '520112000000', '乌当区', 520112000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520113000000', '520100000000', '520113000000', '白云区', 520113000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520115000000', '520100000000', '520115000000', '观山湖区', 520115000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520121000000', '520100000000', '520121000000', '开阳县', 520121000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520122000000', '520100000000', '520122000000', '息烽县', 520122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520123000000', '520100000000', '520123000000', '修文县', 520123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520181000000', '520100000000', '520181000000', '清镇市', 520181000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520200000000', '520000000000', '520200000000', '六盘水市', 520200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520201000000', '520200000000', '520201000000', '钟山区', 520201000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520203000000', '520200000000', '520203000000', '六枝特区', 520203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520204000000', '520200000000', '520204000000', '水城区', 520204000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520281000000', '520200000000', '520281000000', '盘州市', 520281000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520300000000', '520000000000', '520300000000', '遵义市', 520300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520302000000', '520300000000', '520302000000', '红花岗区', 520302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520303000000', '520300000000', '520303000000', '汇川区', 520303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520304000000', '520300000000', '520304000000', '播州区', 520304000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520322000000', '520300000000', '520322000000', '桐梓县', 520322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520323000000', '520300000000', '520323000000', '绥阳县', 520323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520324000000', '520300000000', '520324000000', '正安县', 520324000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520325000000', '520300000000', '520325000000', '道真仡佬族苗族自治县', 520325000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520326000000', '520300000000', '520326000000', '务川仡佬族苗族自治县', 520326000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520327000000', '520300000000', '520327000000', '凤冈县', 520327000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520328000000', '520300000000', '520328000000', '湄潭县', 520328000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520329000000', '520300000000', '520329000000', '余庆县', 520329000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520330000000', '520300000000', '520330000000', '习水县', 520330000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520381000000', '520300000000', '520381000000', '赤水市', 520381000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520382000000', '520300000000', '520382000000', '仁怀市', 520382000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520400000000', '520000000000', '520400000000', '安顺市', 520400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520402000000', '520400000000', '520402000000', '西秀区', 520402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520403000000', '520400000000', '520403000000', '平坝区', 520403000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520422000000', '520400000000', '520422000000', '普定县', 520422000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520423000000', '520400000000', '520423000000', '镇宁布依族苗族自治县', 520423000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520424000000', '520400000000', '520424000000', '关岭布依族苗族自治县', 520424000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520425000000', '520400000000', '520425000000', '紫云苗族布依族自治县', 520425000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520500000000', '520000000000', '520500000000', '毕节市', 520500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520502000000', '520500000000', '520502000000', '七星关区', 520502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520521000000', '520500000000', '520521000000', '大方县', 520521000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520523000000', '520500000000', '520523000000', '金沙县', 520523000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520524000000', '520500000000', '520524000000', '织金县', 520524000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520525000000', '520500000000', '520525000000', '纳雍县', 520525000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520526000000', '520500000000', '520526000000', '威宁彝族回族苗族自治县', 520526000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520527000000', '520500000000', '520527000000', '赫章县', 520527000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520581000000', '520500000000', '520581000000', '黔西市', 520581000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520600000000', '520000000000', '520600000000', '铜仁市', 520600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520602000000', '520600000000', '520602000000', '碧江区', 520602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520603000000', '520600000000', '520603000000', '万山区', 520603000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520621000000', '520600000000', '520621000000', '江口县', 520621000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520622000000', '520600000000', '520622000000', '玉屏侗族自治县', 520622000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520623000000', '520600000000', '520623000000', '石阡县', 520623000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520624000000', '520600000000', '520624000000', '思南县', 520624000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520625000000', '520600000000', '520625000000', '印江土家族苗族自治县', 520625000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520626000000', '520600000000', '520626000000', '德江县', 520626000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520627000000', '520600000000', '520627000000', '沿河土家族自治县', 520627000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('520628000000', '520600000000', '520628000000', '松桃苗族自治县', 520628000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522300000000', '520000000000', '522300000000', '黔西南布依族苗族自治州', 522300000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522301000000', '522300000000', '522301000000', '兴义市', 522301000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522302000000', '522300000000', '522302000000', '兴仁市', 522302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522323000000', '522300000000', '522323000000', '普安县', 522323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522324000000', '522300000000', '522324000000', '晴隆县', 522324000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522325000000', '522300000000', '522325000000', '贞丰县', 522325000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522326000000', '522300000000', '522326000000', '望谟县', 522326000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522327000000', '522300000000', '522327000000', '册亨县', 522327000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522328000000', '522300000000', '522328000000', '安龙县', 522328000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522600000000', '520000000000', '522600000000', '黔东南苗族侗族自治州', 522600000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522601000000', '522600000000', '522601000000', '凯里市', 522601000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522622000000', '522600000000', '522622000000', '黄平县', 522622000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522623000000', '522600000000', '522623000000', '施秉县', 522623000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522624000000', '522600000000', '522624000000', '三穗县', 522624000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522625000000', '522600000000', '522625000000', '镇远县', 522625000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522626000000', '522600000000', '522626000000', '岑巩县', 522626000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522627000000', '522600000000', '522627000000', '天柱县', 522627000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522628000000', '522600000000', '522628000000', '锦屏县', 522628000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522629000000', '522600000000', '522629000000', '剑河县', 522629000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522630000000', '522600000000', '522630000000', '台江县', 522630000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522631000000', '522600000000', '522631000000', '黎平县', 522631000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522632000000', '522600000000', '522632000000', '榕江县', 522632000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522633000000', '522600000000', '522633000000', '从江县', 522633000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522634000000', '522600000000', '522634000000', '雷山县', 522634000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522635000000', '522600000000', '522635000000', '麻江县', 522635000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522636000000', '522600000000', '522636000000', '丹寨县', 522636000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522700000000', '520000000000', '522700000000', '黔南布依族苗族自治州', 522700000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522701000000', '522700000000', '522701000000', '都匀市', 522701000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522702000000', '522700000000', '522702000000', '福泉市', 522702000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522722000000', '522700000000', '522722000000', '荔波县', 522722000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522723000000', '522700000000', '522723000000', '贵定县', 522723000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522725000000', '522700000000', '522725000000', '瓮安县', 522725000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522726000000', '522700000000', '522726000000', '独山县', 522726000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522727000000', '522700000000', '522727000000', '平塘县', 522727000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522728000000', '522700000000', '522728000000', '罗甸县', 522728000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522729000000', '522700000000', '522729000000', '长顺县', 522729000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522730000000', '522700000000', '522730000000', '龙里县', 522730000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522731000000', '522700000000', '522731000000', '惠水县', 522731000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('522732000000', '522700000000', '522732000000', '三都水族自治县', 522732000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530000000000', 'CHINA', '530000000000', '云南省', 530000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530100000000', '530000000000', '530100000000', '昆明市', 530100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530102000000', '530100000000', '530102000000', '五华区', 530102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530103000000', '530100000000', '530103000000', '盘龙区', 530103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530111000000', '530100000000', '530111000000', '官渡区', 530111000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530112000000', '530100000000', '530112000000', '西山区', 530112000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530113000000', '530100000000', '530113000000', '东川区', 530113000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530114000000', '530100000000', '530114000000', '呈贡区', 530114000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530115000000', '530100000000', '530115000000', '晋宁区', 530115000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530124000000', '530100000000', '530124000000', '富民县', 530124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530125000000', '530100000000', '530125000000', '宜良县', 530125000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530126000000', '530100000000', '530126000000', '石林彝族自治县', 530126000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530127000000', '530100000000', '530127000000', '嵩明县', 530127000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530128000000', '530100000000', '530128000000', '禄劝彝族苗族自治县', 530128000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530129000000', '530100000000', '530129000000', '寻甸回族彝族自治县', 530129000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530181000000', '530100000000', '530181000000', '安宁市', 530181000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530300000000', '530000000000', '530300000000', '曲靖市', 530300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530302000000', '530300000000', '530302000000', '麒麟区', 530302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530303000000', '530300000000', '530303000000', '沾益区', 530303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530304000000', '530300000000', '530304000000', '马龙区', 530304000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530322000000', '530300000000', '530322000000', '陆良县', 530322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530323000000', '530300000000', '530323000000', '师宗县', 530323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530324000000', '530300000000', '530324000000', '罗平县', 530324000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530325000000', '530300000000', '530325000000', '富源县', 530325000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530326000000', '530300000000', '530326000000', '会泽县', 530326000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530381000000', '530300000000', '530381000000', '宣威市', 530381000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530400000000', '530000000000', '530400000000', '玉溪市', 530400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530402000000', '530400000000', '530402000000', '红塔区', 530402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530403000000', '530400000000', '530403000000', '江川区', 530403000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530423000000', '530400000000', '530423000000', '通海县', 530423000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530424000000', '530400000000', '530424000000', '华宁县', 530424000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530425000000', '530400000000', '530425000000', '易门县', 530425000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530426000000', '530400000000', '530426000000', '峨山彝族自治县', 530426000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530427000000', '530400000000', '530427000000', '新平彝族傣族自治县', 530427000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530428000000', '530400000000', '530428000000', '元江哈尼族彝族傣族自治县', 530428000000, 'system',
        1729736640000, 'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530481000000', '530400000000', '530481000000', '澄江市', 530481000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530500000000', '530000000000', '530500000000', '保山市', 530500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530502000000', '530500000000', '530502000000', '隆阳区', 530502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530521000000', '530500000000', '530521000000', '施甸县', 530521000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530523000000', '530500000000', '530523000000', '龙陵县', 530523000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530524000000', '530500000000', '530524000000', '昌宁县', 530524000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530581000000', '530500000000', '530581000000', '腾冲市', 530581000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530600000000', '530000000000', '530600000000', '昭通市', 530600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530602000000', '530600000000', '530602000000', '昭阳区', 530602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530621000000', '530600000000', '530621000000', '鲁甸县', 530621000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530622000000', '530600000000', '530622000000', '巧家县', 530622000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530623000000', '530600000000', '530623000000', '盐津县', 530623000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530624000000', '530600000000', '530624000000', '大关县', 530624000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530625000000', '530600000000', '530625000000', '永善县', 530625000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530626000000', '530600000000', '530626000000', '绥江县', 530626000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530627000000', '530600000000', '530627000000', '镇雄县', 530627000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530628000000', '530600000000', '530628000000', '彝良县', 530628000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530629000000', '530600000000', '530629000000', '威信县', 530629000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530681000000', '530600000000', '530681000000', '水富市', 530681000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530700000000', '530000000000', '530700000000', '丽江市', 530700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530702000000', '530700000000', '530702000000', '古城区', 530702000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530721000000', '530700000000', '530721000000', '玉龙纳西族自治县', 530721000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530722000000', '530700000000', '530722000000', '永胜县', 530722000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530723000000', '530700000000', '530723000000', '华坪县', 530723000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530724000000', '530700000000', '530724000000', '宁蒗彝族自治县', 530724000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530800000000', '530000000000', '530800000000', '普洱市', 530800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530802000000', '530800000000', '530802000000', '思茅区', 530802000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530821000000', '530800000000', '530821000000', '宁洱哈尼族彝族自治县', 530821000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530822000000', '530800000000', '530822000000', '墨江哈尼族自治县', 530822000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530823000000', '530800000000', '530823000000', '景东彝族自治县', 530823000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530824000000', '530800000000', '530824000000', '景谷傣族彝族自治县', 530824000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530825000000', '530800000000', '530825000000', '镇沅彝族哈尼族拉祜族自治县', 530825000000, 'system',
        1729736640000, 'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530826000000', '530800000000', '530826000000', '江城哈尼族彝族自治县', 530826000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530827000000', '530800000000', '530827000000', '孟连傣族拉祜族佤族自治县', 530827000000, 'system',
        1729736640000, 'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530828000000', '530800000000', '530828000000', '澜沧拉祜族自治县', 530828000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530829000000', '530800000000', '530829000000', '西盟佤族自治县', 530829000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530900000000', '530000000000', '530900000000', '临沧市', 530900000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530902000000', '530900000000', '530902000000', '临翔区', 530902000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530921000000', '530900000000', '530921000000', '凤庆县', 530921000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530922000000', '530900000000', '530922000000', '云县', 530922000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530923000000', '530900000000', '530923000000', '永德县', 530923000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530924000000', '530900000000', '530924000000', '镇康县', 530924000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530925000000', '530900000000', '530925000000', '双江拉祜族佤族布朗族傣族自治县', 530925000000, 'system',
        1729736640000, 'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530926000000', '530900000000', '530926000000', '耿马傣族佤族自治县', 530926000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('530927000000', '530900000000', '530927000000', '沧源佤族自治县', 530927000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532300000000', '530000000000', '532300000000', '楚雄彝族自治州', 532300000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532301000000', '532300000000', '532301000000', '楚雄市', 532301000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532302000000', '532300000000', '532302000000', '禄丰市', 532302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532322000000', '532300000000', '532322000000', '双柏县', 532322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532323000000', '532300000000', '532323000000', '牟定县', 532323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532324000000', '532300000000', '532324000000', '南华县', 532324000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532325000000', '532300000000', '532325000000', '姚安县', 532325000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532326000000', '532300000000', '532326000000', '大姚县', 532326000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532327000000', '532300000000', '532327000000', '永仁县', 532327000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532328000000', '532300000000', '532328000000', '元谋县', 532328000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532329000000', '532300000000', '532329000000', '武定县', 532329000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532500000000', '530000000000', '532500000000', '红河哈尼族彝族自治州', 532500000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532501000000', '532500000000', '532501000000', '个旧市', 532501000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532502000000', '532500000000', '532502000000', '开远市', 532502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532503000000', '532500000000', '532503000000', '蒙自市', 532503000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532504000000', '532500000000', '532504000000', '弥勒市', 532504000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532523000000', '532500000000', '532523000000', '屏边苗族自治县', 532523000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532524000000', '532500000000', '532524000000', '建水县', 532524000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532525000000', '532500000000', '532525000000', '石屏县', 532525000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532527000000', '532500000000', '532527000000', '泸西县', 532527000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532528000000', '532500000000', '532528000000', '元阳县', 532528000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532529000000', '532500000000', '532529000000', '红河县', 532529000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532530000000', '532500000000', '532530000000', '金平苗族瑶族傣族自治县', 532530000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532531000000', '532500000000', '532531000000', '绿春县', 532531000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532532000000', '532500000000', '532532000000', '河口瑶族自治县', 532532000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532600000000', '530000000000', '532600000000', '文山壮族苗族自治州', 532600000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532601000000', '532600000000', '532601000000', '文山市', 532601000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532622000000', '532600000000', '532622000000', '砚山县', 532622000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532623000000', '532600000000', '532623000000', '西畴县', 532623000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532624000000', '532600000000', '532624000000', '麻栗坡县', 532624000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532625000000', '532600000000', '532625000000', '马关县', 532625000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532626000000', '532600000000', '532626000000', '丘北县', 532626000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532627000000', '532600000000', '532627000000', '广南县', 532627000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532628000000', '532600000000', '532628000000', '富宁县', 532628000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532800000000', '530000000000', '532800000000', '西双版纳傣族自治州', 532800000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532801000000', '532800000000', '532801000000', '景洪市', 532801000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532822000000', '532800000000', '532822000000', '勐海县', 532822000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532823000000', '532800000000', '532823000000', '勐腊县', 532823000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532900000000', '530000000000', '532900000000', '大理白族自治州', 532900000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532901000000', '532900000000', '532901000000', '大理市', 532901000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532922000000', '532900000000', '532922000000', '漾濞彝族自治县', 532922000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532923000000', '532900000000', '532923000000', '祥云县', 532923000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532924000000', '532900000000', '532924000000', '宾川县', 532924000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532925000000', '532900000000', '532925000000', '弥渡县', 532925000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532926000000', '532900000000', '532926000000', '南涧彝族自治县', 532926000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532927000000', '532900000000', '532927000000', '巍山彝族回族自治县', 532927000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532928000000', '532900000000', '532928000000', '永平县', 532928000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532929000000', '532900000000', '532929000000', '云龙县', 532929000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532930000000', '532900000000', '532930000000', '洱源县', 532930000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532931000000', '532900000000', '532931000000', '剑川县', 532931000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('532932000000', '532900000000', '532932000000', '鹤庆县', 532932000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('533100000000', '530000000000', '533100000000', '德宏傣族景颇族自治州', 533100000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('533102000000', '533100000000', '533102000000', '瑞丽市', 533102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('533103000000', '533100000000', '533103000000', '芒市', 533103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('533122000000', '533100000000', '533122000000', '梁河县', 533122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('533123000000', '533100000000', '533123000000', '盈江县', 533123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('533124000000', '533100000000', '533124000000', '陇川县', 533124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('533300000000', '530000000000', '533300000000', '怒江傈僳族自治州', 533300000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('533301000000', '533300000000', '533301000000', '泸水市', 533301000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('533323000000', '533300000000', '533323000000', '福贡县', 533323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('533324000000', '533300000000', '533324000000', '贡山独龙族怒族自治县', 533324000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('533325000000', '533300000000', '533325000000', '兰坪白族普米族自治县', 533325000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('533400000000', '530000000000', '533400000000', '迪庆藏族自治州', 533400000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('533401000000', '533400000000', '533401000000', '香格里拉市', 533401000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('533422000000', '533400000000', '533422000000', '德钦县', 533422000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('533423000000', '533400000000', '533423000000', '维西傈僳族自治县', 533423000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540000000000', 'CHINA', '540000000000', '西藏自治区', 540000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540100000000', '540000000000', '540100000000', '拉萨市', 540100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540102000000', '540100000000', '540102000000', '城关区', 540102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540103000000', '540100000000', '540103000000', '堆龙德庆区', 540103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540104000000', '540100000000', '540104000000', '达孜区', 540104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540121000000', '540100000000', '540121000000', '林周县', 540121000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540122000000', '540100000000', '540122000000', '当雄县', 540122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540123000000', '540100000000', '540123000000', '尼木县', 540123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540124000000', '540100000000', '540124000000', '曲水县', 540124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540127000000', '540100000000', '540127000000', '墨竹工卡县', 540127000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540171000000', '540100000000', '540171000000', '格尔木藏青工业园区', 540171000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540172000000', '540100000000', '540172000000', '拉萨经济技术开发区', 540172000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540173000000', '540100000000', '540173000000', '西藏文化旅游创意园区', 540173000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540174000000', '540100000000', '540174000000', '达孜工业园区', 540174000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540200000000', '540000000000', '540200000000', '日喀则市', 540200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540202000000', '540200000000', '540202000000', '桑珠孜区', 540202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540221000000', '540200000000', '540221000000', '南木林县', 540221000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540222000000', '540200000000', '540222000000', '江孜县', 540222000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540223000000', '540200000000', '540223000000', '定日县', 540223000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540224000000', '540200000000', '540224000000', '萨迦县', 540224000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540225000000', '540200000000', '540225000000', '拉孜县', 540225000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540226000000', '540200000000', '540226000000', '昂仁县', 540226000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540227000000', '540200000000', '540227000000', '谢通门县', 540227000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540228000000', '540200000000', '540228000000', '白朗县', 540228000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540229000000', '540200000000', '540229000000', '仁布县', 540229000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540230000000', '540200000000', '540230000000', '康马县', 540230000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540231000000', '540200000000', '540231000000', '定结县', 540231000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540232000000', '540200000000', '540232000000', '仲巴县', 540232000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540233000000', '540200000000', '540233000000', '亚东县', 540233000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540234000000', '540200000000', '540234000000', '吉隆县', 540234000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540235000000', '540200000000', '540235000000', '聂拉木县', 540235000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540236000000', '540200000000', '540236000000', '萨嘎县', 540236000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540237000000', '540200000000', '540237000000', '岗巴县', 540237000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540300000000', '540000000000', '540300000000', '昌都市', 540300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540302000000', '540300000000', '540302000000', '卡若区', 540302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540321000000', '540300000000', '540321000000', '江达县', 540321000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540322000000', '540300000000', '540322000000', '贡觉县', 540322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540323000000', '540300000000', '540323000000', '类乌齐县', 540323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540324000000', '540300000000', '540324000000', '丁青县', 540324000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540325000000', '540300000000', '540325000000', '察雅县', 540325000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540326000000', '540300000000', '540326000000', '八宿县', 540326000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540327000000', '540300000000', '540327000000', '左贡县', 540327000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540328000000', '540300000000', '540328000000', '芒康县', 540328000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540329000000', '540300000000', '540329000000', '洛隆县', 540329000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540330000000', '540300000000', '540330000000', '边坝县', 540330000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540400000000', '540000000000', '540400000000', '林芝市', 540400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540402000000', '540400000000', '540402000000', '巴宜区', 540402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540421000000', '540400000000', '540421000000', '工布江达县', 540421000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540423000000', '540400000000', '540423000000', '墨脱县', 540423000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540424000000', '540400000000', '540424000000', '波密县', 540424000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540425000000', '540400000000', '540425000000', '察隅县', 540425000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540426000000', '540400000000', '540426000000', '朗县', 540426000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540481000000', '540400000000', '540481000000', '米林市', 540481000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540500000000', '540000000000', '540500000000', '山南市', 540500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540502000000', '540500000000', '540502000000', '乃东区', 540502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540521000000', '540500000000', '540521000000', '扎囊县', 540521000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540522000000', '540500000000', '540522000000', '贡嘎县', 540522000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540523000000', '540500000000', '540523000000', '桑日县', 540523000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540524000000', '540500000000', '540524000000', '琼结县', 540524000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540525000000', '540500000000', '540525000000', '曲松县', 540525000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540526000000', '540500000000', '540526000000', '措美县', 540526000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540527000000', '540500000000', '540527000000', '洛扎县', 540527000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540528000000', '540500000000', '540528000000', '加查县', 540528000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540529000000', '540500000000', '540529000000', '隆子县', 540529000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540531000000', '540500000000', '540531000000', '浪卡子县', 540531000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540581000000', '540500000000', '540581000000', '错那市', 540581000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540600000000', '540000000000', '540600000000', '那曲市', 540600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540602000000', '540600000000', '540602000000', '色尼区', 540602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540621000000', '540600000000', '540621000000', '嘉黎县', 540621000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540622000000', '540600000000', '540622000000', '比如县', 540622000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540623000000', '540600000000', '540623000000', '聂荣县', 540623000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540624000000', '540600000000', '540624000000', '安多县', 540624000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540625000000', '540600000000', '540625000000', '申扎县', 540625000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540626000000', '540600000000', '540626000000', '索县', 540626000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540627000000', '540600000000', '540627000000', '班戈县', 540627000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540628000000', '540600000000', '540628000000', '巴青县', 540628000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540629000000', '540600000000', '540629000000', '尼玛县', 540629000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('540630000000', '540600000000', '540630000000', '双湖县', 540630000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('542500000000', '540000000000', '542500000000', '阿里地区', 542500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('542521000000', '542500000000', '542521000000', '普兰县', 542521000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('542522000000', '542500000000', '542522000000', '札达县', 542522000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('542523000000', '542500000000', '542523000000', '噶尔县', 542523000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('542524000000', '542500000000', '542524000000', '日土县', 542524000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('542525000000', '542500000000', '542525000000', '革吉县', 542525000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('542526000000', '542500000000', '542526000000', '改则县', 542526000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('542527000000', '542500000000', '542527000000', '措勤县', 542527000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610000000000', 'CHINA', '610000000000', '陕西省', 610000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610100000000', '610000000000', '610100000000', '西安市', 610100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610102000000', '610100000000', '610102000000', '新城区', 610102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610103000000', '610100000000', '610103000000', '碑林区', 610103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610104000000', '610100000000', '610104000000', '莲湖区', 610104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610111000000', '610100000000', '610111000000', '灞桥区', 610111000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610112000000', '610100000000', '610112000000', '未央区', 610112000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610113000000', '610100000000', '610113000000', '雁塔区', 610113000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610114000000', '610100000000', '610114000000', '阎良区', 610114000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610115000000', '610100000000', '610115000000', '临潼区', 610115000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610116000000', '610100000000', '610116000000', '长安区', 610116000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610117000000', '610100000000', '610117000000', '高陵区', 610117000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610118000000', '610100000000', '610118000000', '鄠邑区', 610118000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610122000000', '610100000000', '610122000000', '蓝田县', 610122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610124000000', '610100000000', '610124000000', '周至县', 610124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610200000000', '610000000000', '610200000000', '铜川市', 610200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610202000000', '610200000000', '610202000000', '王益区', 610202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610203000000', '610200000000', '610203000000', '印台区', 610203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610204000000', '610200000000', '610204000000', '耀州区', 610204000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610222000000', '610200000000', '610222000000', '宜君县', 610222000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610300000000', '610000000000', '610300000000', '宝鸡市', 610300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610302000000', '610300000000', '610302000000', '渭滨区', 610302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610303000000', '610300000000', '610303000000', '金台区', 610303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610304000000', '610300000000', '610304000000', '陈仓区', 610304000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610305000000', '610300000000', '610305000000', '凤翔区', 610305000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610323000000', '610300000000', '610323000000', '岐山县', 610323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610324000000', '610300000000', '610324000000', '扶风县', 610324000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610326000000', '610300000000', '610326000000', '眉县', 610326000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610327000000', '610300000000', '610327000000', '陇县', 610327000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610328000000', '610300000000', '610328000000', '千阳县', 610328000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610329000000', '610300000000', '610329000000', '麟游县', 610329000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610330000000', '610300000000', '610330000000', '凤县', 610330000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610331000000', '610300000000', '610331000000', '太白县', 610331000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610400000000', '610000000000', '610400000000', '咸阳市', 610400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610402000000', '610400000000', '610402000000', '秦都区', 610402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610403000000', '610400000000', '610403000000', '杨陵区', 610403000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610404000000', '610400000000', '610404000000', '渭城区', 610404000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610422000000', '610400000000', '610422000000', '三原县', 610422000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610423000000', '610400000000', '610423000000', '泾阳县', 610423000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610424000000', '610400000000', '610424000000', '乾县', 610424000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610425000000', '610400000000', '610425000000', '礼泉县', 610425000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610426000000', '610400000000', '610426000000', '永寿县', 610426000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610428000000', '610400000000', '610428000000', '长武县', 610428000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610429000000', '610400000000', '610429000000', '旬邑县', 610429000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610430000000', '610400000000', '610430000000', '淳化县', 610430000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610431000000', '610400000000', '610431000000', '武功县', 610431000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610481000000', '610400000000', '610481000000', '兴平市', 610481000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610482000000', '610400000000', '610482000000', '彬州市', 610482000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610500000000', '610000000000', '610500000000', '渭南市', 610500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610502000000', '610500000000', '610502000000', '临渭区', 610502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610503000000', '610500000000', '610503000000', '华州区', 610503000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610522000000', '610500000000', '610522000000', '潼关县', 610522000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610523000000', '610500000000', '610523000000', '大荔县', 610523000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610524000000', '610500000000', '610524000000', '合阳县', 610524000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610525000000', '610500000000', '610525000000', '澄城县', 610525000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610526000000', '610500000000', '610526000000', '蒲城县', 610526000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610527000000', '610500000000', '610527000000', '白水县', 610527000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610528000000', '610500000000', '610528000000', '富平县', 610528000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610581000000', '610500000000', '610581000000', '韩城市', 610581000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610582000000', '610500000000', '610582000000', '华阴市', 610582000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610600000000', '610000000000', '610600000000', '延安市', 610600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610602000000', '610600000000', '610602000000', '宝塔区', 610602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610603000000', '610600000000', '610603000000', '安塞区', 610603000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610621000000', '610600000000', '610621000000', '延长县', 610621000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610622000000', '610600000000', '610622000000', '延川县', 610622000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610625000000', '610600000000', '610625000000', '志丹县', 610625000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610626000000', '610600000000', '610626000000', '吴起县', 610626000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610627000000', '610600000000', '610627000000', '甘泉县', 610627000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610628000000', '610600000000', '610628000000', '富县', 610628000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610629000000', '610600000000', '610629000000', '洛川县', 610629000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610630000000', '610600000000', '610630000000', '宜川县', 610630000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610631000000', '610600000000', '610631000000', '黄龙县', 610631000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610632000000', '610600000000', '610632000000', '黄陵县', 610632000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610681000000', '610600000000', '610681000000', '子长市', 610681000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610700000000', '610000000000', '610700000000', '汉中市', 610700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610702000000', '610700000000', '610702000000', '汉台区', 610702000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610703000000', '610700000000', '610703000000', '南郑区', 610703000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610722000000', '610700000000', '610722000000', '城固县', 610722000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610723000000', '610700000000', '610723000000', '洋县', 610723000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610724000000', '610700000000', '610724000000', '西乡县', 610724000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610725000000', '610700000000', '610725000000', '勉县', 610725000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610726000000', '610700000000', '610726000000', '宁强县', 610726000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610727000000', '610700000000', '610727000000', '略阳县', 610727000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610728000000', '610700000000', '610728000000', '镇巴县', 610728000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610729000000', '610700000000', '610729000000', '留坝县', 610729000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610730000000', '610700000000', '610730000000', '佛坪县', 610730000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610800000000', '610000000000', '610800000000', '榆林市', 610800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610802000000', '610800000000', '610802000000', '榆阳区', 610802000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610803000000', '610800000000', '610803000000', '横山区', 610803000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610822000000', '610800000000', '610822000000', '府谷县', 610822000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610824000000', '610800000000', '610824000000', '靖边县', 610824000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610825000000', '610800000000', '610825000000', '定边县', 610825000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610826000000', '610800000000', '610826000000', '绥德县', 610826000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610827000000', '610800000000', '610827000000', '米脂县', 610827000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610828000000', '610800000000', '610828000000', '佳县', 610828000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610829000000', '610800000000', '610829000000', '吴堡县', 610829000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610830000000', '610800000000', '610830000000', '清涧县', 610830000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610831000000', '610800000000', '610831000000', '子洲县', 610831000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610881000000', '610800000000', '610881000000', '神木市', 610881000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610900000000', '610000000000', '610900000000', '安康市', 610900000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610902000000', '610900000000', '610902000000', '汉滨区', 610902000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610921000000', '610900000000', '610921000000', '汉阴县', 610921000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610922000000', '610900000000', '610922000000', '石泉县', 610922000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610923000000', '610900000000', '610923000000', '宁陕县', 610923000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610924000000', '610900000000', '610924000000', '紫阳县', 610924000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610925000000', '610900000000', '610925000000', '岚皋县', 610925000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610926000000', '610900000000', '610926000000', '平利县', 610926000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610927000000', '610900000000', '610927000000', '镇坪县', 610927000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610929000000', '610900000000', '610929000000', '白河县', 610929000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('610981000000', '610900000000', '610981000000', '旬阳市', 610981000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('611000000000', '610000000000', '611000000000', '商洛市', 611000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('611002000000', '611000000000', '611002000000', '商州区', 611002000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('611021000000', '611000000000', '611021000000', '洛南县', 611021000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('611022000000', '611000000000', '611022000000', '丹凤县', 611022000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('611023000000', '611000000000', '611023000000', '商南县', 611023000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('611024000000', '611000000000', '611024000000', '山阳县', 611024000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('611025000000', '611000000000', '611025000000', '镇安县', 611025000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('611026000000', '611000000000', '611026000000', '柞水县', 611026000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620000000000', 'CHINA', '620000000000', '甘肃省', 620000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620100000000', '620000000000', '620100000000', '兰州市', 620100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620102000000', '620100000000', '620102000000', '城关区', 620102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620103000000', '620100000000', '620103000000', '七里河区', 620103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620104000000', '620100000000', '620104000000', '西固区', 620104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620105000000', '620100000000', '620105000000', '安宁区', 620105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620111000000', '620100000000', '620111000000', '红古区', 620111000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620121000000', '620100000000', '620121000000', '永登县', 620121000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620122000000', '620100000000', '620122000000', '皋兰县', 620122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620123000000', '620100000000', '620123000000', '榆中县', 620123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620171000000', '620100000000', '620171000000', '兰州新区', 620171000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620200000000', '620000000000', '620200000000', '嘉峪关市', 620200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620201000000', '620200000000', '620201000000', '市辖区', 620201000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620300000000', '620000000000', '620300000000', '金昌市', 620300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620302000000', '620300000000', '620302000000', '金川区', 620302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620321000000', '620300000000', '620321000000', '永昌县', 620321000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620400000000', '620000000000', '620400000000', '白银市', 620400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620402000000', '620400000000', '620402000000', '白银区', 620402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620403000000', '620400000000', '620403000000', '平川区', 620403000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620421000000', '620400000000', '620421000000', '靖远县', 620421000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620422000000', '620400000000', '620422000000', '会宁县', 620422000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620423000000', '620400000000', '620423000000', '景泰县', 620423000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620500000000', '620000000000', '620500000000', '天水市', 620500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620502000000', '620500000000', '620502000000', '秦州区', 620502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620503000000', '620500000000', '620503000000', '麦积区', 620503000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620521000000', '620500000000', '620521000000', '清水县', 620521000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620522000000', '620500000000', '620522000000', '秦安县', 620522000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620523000000', '620500000000', '620523000000', '甘谷县', 620523000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620524000000', '620500000000', '620524000000', '武山县', 620524000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620525000000', '620500000000', '620525000000', '张家川回族自治县', 620525000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620600000000', '620000000000', '620600000000', '武威市', 620600000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620602000000', '620600000000', '620602000000', '凉州区', 620602000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620621000000', '620600000000', '620621000000', '民勤县', 620621000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620622000000', '620600000000', '620622000000', '古浪县', 620622000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620623000000', '620600000000', '620623000000', '天祝藏族自治县', 620623000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620700000000', '620000000000', '620700000000', '张掖市', 620700000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620702000000', '620700000000', '620702000000', '甘州区', 620702000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620721000000', '620700000000', '620721000000', '肃南裕固族自治县', 620721000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620722000000', '620700000000', '620722000000', '民乐县', 620722000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620723000000', '620700000000', '620723000000', '临泽县', 620723000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620724000000', '620700000000', '620724000000', '高台县', 620724000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620725000000', '620700000000', '620725000000', '山丹县', 620725000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620800000000', '620000000000', '620800000000', '平凉市', 620800000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620802000000', '620800000000', '620802000000', '崆峒区', 620802000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620821000000', '620800000000', '620821000000', '泾川县', 620821000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620822000000', '620800000000', '620822000000', '灵台县', 620822000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620823000000', '620800000000', '620823000000', '崇信县', 620823000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620825000000', '620800000000', '620825000000', '庄浪县', 620825000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620826000000', '620800000000', '620826000000', '静宁县', 620826000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620881000000', '620800000000', '620881000000', '华亭市', 620881000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620900000000', '620000000000', '620900000000', '酒泉市', 620900000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620902000000', '620900000000', '620902000000', '肃州区', 620902000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620921000000', '620900000000', '620921000000', '金塔县', 620921000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620922000000', '620900000000', '620922000000', '瓜州县', 620922000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620923000000', '620900000000', '620923000000', '肃北蒙古族自治县', 620923000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620924000000', '620900000000', '620924000000', '阿克塞哈萨克族自治县', 620924000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620981000000', '620900000000', '620981000000', '玉门市', 620981000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('620982000000', '620900000000', '620982000000', '敦煌市', 620982000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621000000000', '620000000000', '621000000000', '庆阳市', 621000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621002000000', '621000000000', '621002000000', '西峰区', 621002000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621021000000', '621000000000', '621021000000', '庆城县', 621021000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621022000000', '621000000000', '621022000000', '环县', 621022000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621023000000', '621000000000', '621023000000', '华池县', 621023000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621024000000', '621000000000', '621024000000', '合水县', 621024000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621025000000', '621000000000', '621025000000', '正宁县', 621025000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621026000000', '621000000000', '621026000000', '宁县', 621026000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621027000000', '621000000000', '621027000000', '镇原县', 621027000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621100000000', '620000000000', '621100000000', '定西市', 621100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621102000000', '621100000000', '621102000000', '安定区', 621102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621121000000', '621100000000', '621121000000', '通渭县', 621121000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621122000000', '621100000000', '621122000000', '陇西县', 621122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621123000000', '621100000000', '621123000000', '渭源县', 621123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621124000000', '621100000000', '621124000000', '临洮县', 621124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621125000000', '621100000000', '621125000000', '漳县', 621125000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621126000000', '621100000000', '621126000000', '岷县', 621126000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621200000000', '620000000000', '621200000000', '陇南市', 621200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621202000000', '621200000000', '621202000000', '武都区', 621202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621221000000', '621200000000', '621221000000', '成县', 621221000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621222000000', '621200000000', '621222000000', '文县', 621222000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621223000000', '621200000000', '621223000000', '宕昌县', 621223000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621224000000', '621200000000', '621224000000', '康县', 621224000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621225000000', '621200000000', '621225000000', '西和县', 621225000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621226000000', '621200000000', '621226000000', '礼县', 621226000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621227000000', '621200000000', '621227000000', '徽县', 621227000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('621228000000', '621200000000', '621228000000', '两当县', 621228000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('622900000000', '620000000000', '622900000000', '临夏回族自治州', 622900000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('622901000000', '622900000000', '622901000000', '临夏市', 622901000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('622921000000', '622900000000', '622921000000', '临夏县', 622921000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('622922000000', '622900000000', '622922000000', '康乐县', 622922000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('622923000000', '622900000000', '622923000000', '永靖县', 622923000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('622924000000', '622900000000', '622924000000', '广河县', 622924000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('622925000000', '622900000000', '622925000000', '和政县', 622925000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('622926000000', '622900000000', '622926000000', '东乡族自治县', 622926000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('622927000000', '622900000000', '622927000000', '积石山保安族东乡族撒拉族自治县', 622927000000, 'system',
        1729736640000, 'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('623000000000', '620000000000', '623000000000', '甘南藏族自治州', 623000000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('623001000000', '623000000000', '623001000000', '合作市', 623001000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('623021000000', '623000000000', '623021000000', '临潭县', 623021000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('623022000000', '623000000000', '623022000000', '卓尼县', 623022000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('623023000000', '623000000000', '623023000000', '舟曲县', 623023000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('623024000000', '623000000000', '623024000000', '迭部县', 623024000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('623025000000', '623000000000', '623025000000', '玛曲县', 623025000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('623026000000', '623000000000', '623026000000', '碌曲县', 623026000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('623027000000', '623000000000', '623027000000', '夏河县', 623027000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('630000000000', 'CHINA', '630000000000', '青海省', 630000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('630100000000', '630000000000', '630100000000', '西宁市', 630100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('630102000000', '630100000000', '630102000000', '城东区', 630102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('630103000000', '630100000000', '630103000000', '城中区', 630103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('630104000000', '630100000000', '630104000000', '城西区', 630104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('630105000000', '630100000000', '630105000000', '城北区', 630105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('630106000000', '630100000000', '630106000000', '湟中区', 630106000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('630121000000', '630100000000', '630121000000', '大通回族土族自治县', 630121000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('630123000000', '630100000000', '630123000000', '湟源县', 630123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('630200000000', '630000000000', '630200000000', '海东市', 630200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('630202000000', '630200000000', '630202000000', '乐都区', 630202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('630203000000', '630200000000', '630203000000', '平安区', 630203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('630222000000', '630200000000', '630222000000', '民和回族土族自治县', 630222000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('630223000000', '630200000000', '630223000000', '互助土族自治县', 630223000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('630224000000', '630200000000', '630224000000', '化隆回族自治县', 630224000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('630225000000', '630200000000', '630225000000', '循化撒拉族自治县', 630225000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632200000000', '630000000000', '632200000000', '海北藏族自治州', 632200000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632221000000', '632200000000', '632221000000', '门源回族自治县', 632221000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632222000000', '632200000000', '632222000000', '祁连县', 632222000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632223000000', '632200000000', '632223000000', '海晏县', 632223000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632224000000', '632200000000', '632224000000', '刚察县', 632224000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632300000000', '630000000000', '632300000000', '黄南藏族自治州', 632300000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632301000000', '632300000000', '632301000000', '同仁市', 632301000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632322000000', '632300000000', '632322000000', '尖扎县', 632322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632323000000', '632300000000', '632323000000', '泽库县', 632323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632324000000', '632300000000', '632324000000', '河南蒙古族自治县', 632324000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632500000000', '630000000000', '632500000000', '海南藏族自治州', 632500000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632521000000', '632500000000', '632521000000', '共和县', 632521000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632522000000', '632500000000', '632522000000', '同德县', 632522000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632523000000', '632500000000', '632523000000', '贵德县', 632523000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632524000000', '632500000000', '632524000000', '兴海县', 632524000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632525000000', '632500000000', '632525000000', '贵南县', 632525000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632600000000', '630000000000', '632600000000', '果洛藏族自治州', 632600000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632621000000', '632600000000', '632621000000', '玛沁县', 632621000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632622000000', '632600000000', '632622000000', '班玛县', 632622000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632623000000', '632600000000', '632623000000', '甘德县', 632623000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632624000000', '632600000000', '632624000000', '达日县', 632624000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632625000000', '632600000000', '632625000000', '久治县', 632625000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632626000000', '632600000000', '632626000000', '玛多县', 632626000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632700000000', '630000000000', '632700000000', '玉树藏族自治州', 632700000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632701000000', '632700000000', '632701000000', '玉树市', 632701000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632722000000', '632700000000', '632722000000', '杂多县', 632722000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632723000000', '632700000000', '632723000000', '称多县', 632723000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632724000000', '632700000000', '632724000000', '治多县', 632724000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632725000000', '632700000000', '632725000000', '囊谦县', 632725000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632726000000', '632700000000', '632726000000', '曲麻莱县', 632726000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632800000000', '630000000000', '632800000000', '海西蒙古族藏族自治州', 632800000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632801000000', '632800000000', '632801000000', '格尔木市', 632801000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632802000000', '632800000000', '632802000000', '德令哈市', 632802000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632803000000', '632800000000', '632803000000', '茫崖市', 632803000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632821000000', '632800000000', '632821000000', '乌兰县', 632821000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632822000000', '632800000000', '632822000000', '都兰县', 632822000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632823000000', '632800000000', '632823000000', '天峻县', 632823000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('632857000000', '632800000000', '632857000000', '大柴旦行政委员会', 632857000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640000000000', 'CHINA', '640000000000', '宁夏回族自治区', 640000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640100000000', '640000000000', '640100000000', '银川市', 640100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640104000000', '640100000000', '640104000000', '兴庆区', 640104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640105000000', '640100000000', '640105000000', '西夏区', 640105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640106000000', '640100000000', '640106000000', '金凤区', 640106000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640121000000', '640100000000', '640121000000', '永宁县', 640121000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640122000000', '640100000000', '640122000000', '贺兰县', 640122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640181000000', '640100000000', '640181000000', '灵武市', 640181000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640200000000', '640000000000', '640200000000', '石嘴山市', 640200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640202000000', '640200000000', '640202000000', '大武口区', 640202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640205000000', '640200000000', '640205000000', '惠农区', 640205000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640221000000', '640200000000', '640221000000', '平罗县', 640221000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640300000000', '640000000000', '640300000000', '吴忠市', 640300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640302000000', '640300000000', '640302000000', '利通区', 640302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640303000000', '640300000000', '640303000000', '红寺堡区', 640303000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640323000000', '640300000000', '640323000000', '盐池县', 640323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640324000000', '640300000000', '640324000000', '同心县', 640324000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640381000000', '640300000000', '640381000000', '青铜峡市', 640381000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640400000000', '640000000000', '640400000000', '固原市', 640400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640402000000', '640400000000', '640402000000', '原州区', 640402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640422000000', '640400000000', '640422000000', '西吉县', 640422000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640423000000', '640400000000', '640423000000', '隆德县', 640423000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640424000000', '640400000000', '640424000000', '泾源县', 640424000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640425000000', '640400000000', '640425000000', '彭阳县', 640425000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640500000000', '640000000000', '640500000000', '中卫市', 640500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640502000000', '640500000000', '640502000000', '沙坡头区', 640502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640521000000', '640500000000', '640521000000', '中宁县', 640521000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('640522000000', '640500000000', '640522000000', '海原县', 640522000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('650000000000', 'CHINA', '650000000000', '新疆维吾尔自治区', 650000000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('650100000000', '650000000000', '650100000000', '乌鲁木齐市', 650100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('650102000000', '650100000000', '650102000000', '天山区', 650102000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('650103000000', '650100000000', '650103000000', '沙依巴克区', 650103000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('650104000000', '650100000000', '650104000000', '新市区', 650104000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('650105000000', '650100000000', '650105000000', '水磨沟区', 650105000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('650106000000', '650100000000', '650106000000', '头屯河区', 650106000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('650107000000', '650100000000', '650107000000', '达坂城区', 650107000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('650109000000', '650100000000', '650109000000', '米东区', 650109000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('650121000000', '650100000000', '650121000000', '乌鲁木齐县', 650121000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('650200000000', '650000000000', '650200000000', '克拉玛依市', 650200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('650202000000', '650200000000', '650202000000', '独山子区', 650202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('650203000000', '650200000000', '650203000000', '克拉玛依区', 650203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('650204000000', '650200000000', '650204000000', '白碱滩区', 650204000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('650205000000', '650200000000', '650205000000', '乌尔禾区', 650205000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('650400000000', '650000000000', '650400000000', '吐鲁番市', 650400000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('650402000000', '650400000000', '650402000000', '高昌区', 650402000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('650421000000', '650400000000', '650421000000', '鄯善县', 650421000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('650422000000', '650400000000', '650422000000', '托克逊县', 650422000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('650500000000', '650000000000', '650500000000', '哈密市', 650500000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('650502000000', '650500000000', '650502000000', '伊州区', 650502000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('650521000000', '650500000000', '650521000000', '巴里坤哈萨克自治县', 650521000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('650522000000', '650500000000', '650522000000', '伊吾县', 650522000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652300000000', '650000000000', '652300000000', '昌吉回族自治州', 652300000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652301000000', '652300000000', '652301000000', '昌吉市', 652301000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652302000000', '652300000000', '652302000000', '阜康市', 652302000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652323000000', '652300000000', '652323000000', '呼图壁县', 652323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652324000000', '652300000000', '652324000000', '玛纳斯县', 652324000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652325000000', '652300000000', '652325000000', '奇台县', 652325000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652327000000', '652300000000', '652327000000', '吉木萨尔县', 652327000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652328000000', '652300000000', '652328000000', '木垒哈萨克自治县', 652328000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652700000000', '650000000000', '652700000000', '博尔塔拉蒙古自治州', 652700000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652701000000', '652700000000', '652701000000', '博乐市', 652701000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652702000000', '652700000000', '652702000000', '阿拉山口市', 652702000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652722000000', '652700000000', '652722000000', '精河县', 652722000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652723000000', '652700000000', '652723000000', '温泉县', 652723000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652800000000', '650000000000', '652800000000', '巴音郭楞蒙古自治州', 652800000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652801000000', '652800000000', '652801000000', '库尔勒市', 652801000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652822000000', '652800000000', '652822000000', '轮台县', 652822000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652823000000', '652800000000', '652823000000', '尉犁县', 652823000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652824000000', '652800000000', '652824000000', '若羌县', 652824000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652825000000', '652800000000', '652825000000', '且末县', 652825000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652826000000', '652800000000', '652826000000', '焉耆回族自治县', 652826000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652827000000', '652800000000', '652827000000', '和静县', 652827000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652828000000', '652800000000', '652828000000', '和硕县', 652828000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652829000000', '652800000000', '652829000000', '博湖县', 652829000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652900000000', '650000000000', '652900000000', '阿克苏地区', 652900000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652901000000', '652900000000', '652901000000', '阿克苏市', 652901000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652902000000', '652900000000', '652902000000', '库车市', 652902000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652922000000', '652900000000', '652922000000', '温宿县', 652922000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652924000000', '652900000000', '652924000000', '沙雅县', 652924000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652925000000', '652900000000', '652925000000', '新和县', 652925000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652926000000', '652900000000', '652926000000', '拜城县', 652926000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652927000000', '652900000000', '652927000000', '乌什县', 652927000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652928000000', '652900000000', '652928000000', '阿瓦提县', 652928000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('652929000000', '652900000000', '652929000000', '柯坪县', 652929000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653000000000', '650000000000', '653000000000', '克孜勒苏柯尔克孜自治州', 653000000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653001000000', '653000000000', '653001000000', '阿图什市', 653001000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653022000000', '653000000000', '653022000000', '阿克陶县', 653022000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653023000000', '653000000000', '653023000000', '阿合奇县', 653023000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653024000000', '653000000000', '653024000000', '乌恰县', 653024000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653100000000', '650000000000', '653100000000', '喀什地区', 653100000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653101000000', '653100000000', '653101000000', '喀什市', 653101000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653121000000', '653100000000', '653121000000', '疏附县', 653121000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653122000000', '653100000000', '653122000000', '疏勒县', 653122000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653123000000', '653100000000', '653123000000', '英吉沙县', 653123000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653124000000', '653100000000', '653124000000', '泽普县', 653124000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653125000000', '653100000000', '653125000000', '莎车县', 653125000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653126000000', '653100000000', '653126000000', '叶城县', 653126000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653127000000', '653100000000', '653127000000', '麦盖提县', 653127000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653128000000', '653100000000', '653128000000', '岳普湖县', 653128000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653129000000', '653100000000', '653129000000', '伽师县', 653129000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653130000000', '653100000000', '653130000000', '巴楚县', 653130000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653131000000', '653100000000', '653131000000', '塔什库尔干塔吉克自治县', 653131000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653200000000', '650000000000', '653200000000', '和田地区', 653200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653201000000', '653200000000', '653201000000', '和田市', 653201000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653221000000', '653200000000', '653221000000', '和田县', 653221000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653222000000', '653200000000', '653222000000', '墨玉县', 653222000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653223000000', '653200000000', '653223000000', '皮山县', 653223000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653224000000', '653200000000', '653224000000', '洛浦县', 653224000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653225000000', '653200000000', '653225000000', '策勒县', 653225000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653226000000', '653200000000', '653226000000', '于田县', 653226000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('653227000000', '653200000000', '653227000000', '民丰县', 653227000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654000000000', '650000000000', '654000000000', '伊犁哈萨克自治州', 654000000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654002000000', '654000000000', '654002000000', '伊宁市', 654002000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654003000000', '654000000000', '654003000000', '奎屯市', 654003000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654004000000', '654000000000', '654004000000', '霍尔果斯市', 654004000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654021000000', '654000000000', '654021000000', '伊宁县', 654021000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654022000000', '654000000000', '654022000000', '察布查尔锡伯自治县', 654022000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654023000000', '654000000000', '654023000000', '霍城县', 654023000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654024000000', '654000000000', '654024000000', '巩留县', 654024000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654025000000', '654000000000', '654025000000', '新源县', 654025000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654026000000', '654000000000', '654026000000', '昭苏县', 654026000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654027000000', '654000000000', '654027000000', '特克斯县', 654027000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654028000000', '654000000000', '654028000000', '尼勒克县', 654028000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654200000000', '650000000000', '654200000000', '塔城地区', 654200000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654201000000', '654200000000', '654201000000', '塔城市', 654201000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654202000000', '654200000000', '654202000000', '乌苏市', 654202000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654203000000', '654200000000', '654203000000', '沙湾市', 654203000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654221000000', '654200000000', '654221000000', '额敏县', 654221000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654224000000', '654200000000', '654224000000', '托里县', 654224000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654225000000', '654200000000', '654225000000', '裕民县', 654225000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654226000000', '654200000000', '654226000000', '和布克赛尔蒙古自治县', 654226000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654300000000', '650000000000', '654300000000', '阿勒泰地区', 654300000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654301000000', '654300000000', '654301000000', '阿勒泰市', 654301000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654321000000', '654300000000', '654321000000', '布尔津县', 654321000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654322000000', '654300000000', '654322000000', '富蕴县', 654322000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654323000000', '654300000000', '654323000000', '福海县', 654323000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654324000000', '654300000000', '654324000000', '哈巴河县', 654324000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654325000000', '654300000000', '654325000000', '青河县', 654325000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('654326000000', '654300000000', '654326000000', '吉木乃县', 654326000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('659000000000', '650000000000', '659000000000', '自治区直辖县级行政区划', 659000000000, 'system', 1729736640000,
        'system', 1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('659001000000', '659000000000', '659001000000', '石河子市', 659001000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('659002000000', '659000000000', '659002000000', '阿拉尔市', 659002000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('659003000000', '659000000000', '659003000000', '图木舒克市', 659003000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('659004000000', '659000000000', '659004000000', '五家渠市', 659004000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('659005000000', '659000000000', '659005000000', '北屯市', 659005000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('659006000000', '659000000000', '659006000000', '铁门关市', 659006000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('659007000000', '659000000000', '659007000000', '双河市', 659007000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('659008000000', '659000000000', '659008000000', '可克达拉市', 659008000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('659009000000', '659000000000', '659009000000', '昆玉市', 659009000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('659010000000', '659000000000', '659010000000', '胡杨河市', 659010000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('659011000000', '659000000000', '659011000000', '新星市', 659011000000, 'system', 1729736640000, 'system',
        1729736640000);
INSERT INTO t_data_area (id, parent_id, code, name, sort, create_by, create_time, update_by, update_time)
VALUES ('659012000000', '659000000000', '659012000000', '白杨市', 659012000000, 'system', 1729736640000, 'system',
        1729736640000);

/*************************************************************************************************/
/**************************************** 省市区信息 ***********************************************/
/*************************************************************************************************/
