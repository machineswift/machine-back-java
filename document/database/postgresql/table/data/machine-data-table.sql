DROP TABLE IF EXISTS t_data_area;
CREATE TABLE t_data_area
(
    id          VARCHAR(32) NOT NULL,
    parent_id   VARCHAR(32) NOT NULL,
    code        VARCHAR(12) NOT NULL,
    name        VARCHAR(64) NOT NULL,
    sort        BIGINT NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_area PRIMARY KEY (id),
    CONSTRAINT uk_t_data_area_01 UNIQUE (code)
);

CREATE INDEX idx_t_data_area_01 ON t_data_area (parent_id);

COMMENT ON TABLE t_data_area IS '地区表';
COMMENT ON COLUMN t_data_area.id IS 'ID';
COMMENT ON COLUMN t_data_area.parent_id IS '父ID';
COMMENT ON COLUMN t_data_area.code IS '编码';
COMMENT ON COLUMN t_data_area.name IS '名称';
COMMENT ON COLUMN t_data_area.sort IS '排序';
COMMENT ON COLUMN t_data_area.create_by IS '创建人';
COMMENT ON COLUMN t_data_area.create_time IS '创建时间';
COMMENT ON COLUMN t_data_area.update_by IS '修改人';
COMMENT ON COLUMN t_data_area.update_time IS '更新时间';


DROP TABLE IF EXISTS t_data_leaf_alloc;
CREATE TABLE t_data_leaf_alloc
(
    id          VARCHAR(32) NOT NULL,
    biz_tag     VARCHAR(128) NOT NULL,
    max_id      BIGINT NOT NULL DEFAULT 0,
    step        INTEGER NOT NULL DEFAULT 100,
    expire_time BIGINT NOT NULL,
    remark      VARCHAR(2048) NOT NULL,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_leaf_alloc PRIMARY KEY (id),
    CONSTRAINT uk_t_data_leaf_alloc_01 UNIQUE (biz_tag)
);

CREATE INDEX idx_t_data_leaf_alloc_01 ON t_data_leaf_alloc (expire_time);

COMMENT ON TABLE t_data_leaf_alloc IS 'leaf 表';
COMMENT ON COLUMN t_data_leaf_alloc.id IS 'ID';
COMMENT ON COLUMN t_data_leaf_alloc.biz_tag IS '业务标签';
COMMENT ON COLUMN t_data_leaf_alloc.max_id IS '当前已分配的最大ID';
COMMENT ON COLUMN t_data_leaf_alloc.step IS '每次分配的步长，默认为100';
COMMENT ON COLUMN t_data_leaf_alloc.expire_time IS '过期时间';
COMMENT ON COLUMN t_data_leaf_alloc.remark IS '备注';
COMMENT ON COLUMN t_data_leaf_alloc.create_by IS '创建人';
COMMENT ON COLUMN t_data_leaf_alloc.create_time IS '创建时间';
COMMENT ON COLUMN t_data_leaf_alloc.update_by IS '修改人';
COMMENT ON COLUMN t_data_leaf_alloc.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_system_config;
CREATE TABLE t_data_system_config
(
    id          VARCHAR(32) NOT NULL,
    category    VARCHAR(32) NOT NULL DEFAULT '',
    code        VARCHAR(64) NOT NULL,
    content     TEXT,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_system_config PRIMARY KEY (id),
    CONSTRAINT uk_t_data_system_config_01 UNIQUE (category, code)
);

COMMENT ON TABLE t_data_system_config IS '配置信息表';
COMMENT ON COLUMN t_data_system_config.id IS 'ID';
COMMENT ON COLUMN t_data_system_config.category IS '分类';
COMMENT ON COLUMN t_data_system_config.code IS '编码';
COMMENT ON COLUMN t_data_system_config.content IS '内容';
COMMENT ON COLUMN t_data_system_config.create_by IS '创建人';
COMMENT ON COLUMN t_data_system_config.create_time IS '创建时间';
COMMENT ON COLUMN t_data_system_config.update_by IS '修改人';
COMMENT ON COLUMN t_data_system_config.update_time IS '更新时间';


DROP TABLE IF EXISTS t_data_company_employee;
CREATE TABLE t_data_company_employee
(
    id              VARCHAR(32) NOT NULL,
    user_id         VARCHAR(32) NOT NULL,
    employee_status VARCHAR(16) NOT NULL,
    create_by       VARCHAR(32) NOT NULL,
    create_time     BIGINT NOT NULL,
    update_by       VARCHAR(32) NOT NULL,
    update_time     BIGINT NOT NULL,
    CONSTRAINT pk_t_data_company_employee PRIMARY KEY (id),
    CONSTRAINT uk_t_data_company_employee_01 UNIQUE (user_id)
);

COMMENT ON TABLE t_data_company_employee IS '公司员工';
COMMENT ON COLUMN t_data_company_employee.id IS 'ID';
COMMENT ON COLUMN t_data_company_employee.user_id IS '用户id';
COMMENT ON COLUMN t_data_company_employee.employee_status IS '状态,（CompanyEmployeeStatusEnum）';
COMMENT ON COLUMN t_data_company_employee.create_by IS '创建人';
COMMENT ON COLUMN t_data_company_employee.create_time IS '创建时间';
COMMENT ON COLUMN t_data_company_employee.update_by IS '修改人';
COMMENT ON COLUMN t_data_company_employee.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_shop_employee;
CREATE TABLE t_data_shop_employee
(
    id              VARCHAR(32) NOT NULL,
    user_id         VARCHAR(32) NOT NULL,
    employee_status VARCHAR(16) NOT NULL,
    create_by       VARCHAR(32) NOT NULL,
    create_time     BIGINT NOT NULL,
    update_by       VARCHAR(32) NOT NULL,
    update_time     BIGINT NOT NULL,
    CONSTRAINT pk_t_data_shop_employee PRIMARY KEY (id),
    CONSTRAINT uk_t_data_shop_employee_01 UNIQUE (user_id)
);

COMMENT ON TABLE t_data_shop_employee IS '门店员工';
COMMENT ON COLUMN t_data_shop_employee.id IS 'ID';
COMMENT ON COLUMN t_data_shop_employee.user_id IS '用户id';
COMMENT ON COLUMN t_data_shop_employee.employee_status IS '状态,（ShopEmployeeStatusEnum）';
COMMENT ON COLUMN t_data_shop_employee.create_by IS '创建人';
COMMENT ON COLUMN t_data_shop_employee.create_time IS '创建时间';
COMMENT ON COLUMN t_data_shop_employee.update_by IS '修改人';
COMMENT ON COLUMN t_data_shop_employee.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_supplier_user;
CREATE TABLE t_data_supplier_user
(
    id          VARCHAR(32) NOT NULL,
    user_id     VARCHAR(32) NOT NULL,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_supplier_user PRIMARY KEY (id),
    CONSTRAINT uk_t_data_supplier_user_01 UNIQUE (user_id)
);

COMMENT ON TABLE t_data_supplier_user IS '供应商用户表';
COMMENT ON COLUMN t_data_supplier_user.id IS 'ID';
COMMENT ON COLUMN t_data_supplier_user.user_id IS '用户id';
COMMENT ON COLUMN t_data_supplier_user.create_by IS '创建人';
COMMENT ON COLUMN t_data_supplier_user.create_time IS '创建时间';
COMMENT ON COLUMN t_data_supplier_user.update_by IS '修改人';
COMMENT ON COLUMN t_data_supplier_user.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_franchisee;
CREATE TABLE t_data_franchisee
(
    id                        VARCHAR(32) NOT NULL,
    user_id                   VARCHAR(32) NOT NULL,
    name                      VARCHAR(32) NOT NULL,
    code                      VARCHAR(32) NOT NULL,
    entity_type               VARCHAR(16) NOT NULL,
    certificate_type          VARCHAR(48) NOT NULL,
    certificate_number        VARCHAR(32) NOT NULL,
    educational_qualification VARCHAR(32),
    work_experience_type      VARCHAR(32),
    work_experience_value     VARCHAR(16),
    personal_assets           DECIMAL(15, 2) NOT NULL DEFAULT 0,
    franchise_date            BIGINT NOT NULL DEFAULT 0,
    incoming_channel_first    VARCHAR(32),
    incoming_channel_second   VARCHAR(32),
    incoming_channel_third    VARCHAR(32),
    description               VARCHAR(2048) NOT NULL DEFAULT '',
    create_by                 VARCHAR(32) NOT NULL,
    create_time               BIGINT NOT NULL,
    update_by                 VARCHAR(32) NOT NULL,
    update_time               BIGINT NOT NULL,
    CONSTRAINT pk_t_data_franchisee PRIMARY KEY (id),
    CONSTRAINT uk_t_data_franchisee_01 UNIQUE (user_id),
    CONSTRAINT uk_t_data_franchisee_02 UNIQUE (code),
    CONSTRAINT uk_t_data_franchisee_03 UNIQUE (certificate_number, certificate_type)
);

CREATE INDEX idx_t_data_franchisee_01 ON t_data_franchisee (create_time);
CREATE INDEX idx_t_data_franchisee_02 ON t_data_franchisee (update_time);

COMMENT ON TABLE t_data_franchisee IS '加盟商表';
COMMENT ON COLUMN t_data_franchisee.id IS 'ID';
COMMENT ON COLUMN t_data_franchisee.user_id IS '用户id';
COMMENT ON COLUMN t_data_franchisee.name IS '加盟商名称';
COMMENT ON COLUMN t_data_franchisee.code IS '加盟商编码';
COMMENT ON COLUMN t_data_franchisee.entity_type IS '主体类型';
COMMENT ON COLUMN t_data_franchisee.certificate_type IS '证件类型（CertificateTypeEnum）';
COMMENT ON COLUMN t_data_franchisee.certificate_number IS '证件号码';
COMMENT ON COLUMN t_data_franchisee.educational_qualification IS '学历';
COMMENT ON COLUMN t_data_franchisee.work_experience_type IS '工作经验类型';
COMMENT ON COLUMN t_data_franchisee.work_experience_value IS '工作经验值';
COMMENT ON COLUMN t_data_franchisee.personal_assets IS '个人资产';
COMMENT ON COLUMN t_data_franchisee.franchise_date IS '加盟日期';
COMMENT ON COLUMN t_data_franchisee.incoming_channel_first IS '一级进线渠道';
COMMENT ON COLUMN t_data_franchisee.incoming_channel_second IS '二级进线渠道';
COMMENT ON COLUMN t_data_franchisee.incoming_channel_third IS '三级进线渠道';
COMMENT ON COLUMN t_data_franchisee.description IS '描述';
COMMENT ON COLUMN t_data_franchisee.create_by IS '创建人';
COMMENT ON COLUMN t_data_franchisee.create_time IS '创建时间';
COMMENT ON COLUMN t_data_franchisee.update_by IS '修改人';
COMMENT ON COLUMN t_data_franchisee.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_franchisee_shop_relation;
CREATE TABLE t_data_franchisee_shop_relation
(
    id            VARCHAR(32) NOT NULL,
    franchisee_id VARCHAR(32) NOT NULL,
    shop_id       VARCHAR(32) NOT NULL,
    sort          BIGINT NOT NULL DEFAULT 0,
    create_by     VARCHAR(32) NOT NULL,
    create_time   BIGINT NOT NULL,
    update_by     VARCHAR(32) NOT NULL,
    update_time   BIGINT NOT NULL,
    CONSTRAINT pk_t_data_franchisee_shop_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_data_franchisee_shop_relation_01 UNIQUE (shop_id)
);

CREATE INDEX idx_t_data_franchisee_shop_relation_01 ON t_data_franchisee_shop_relation (franchisee_id);

COMMENT ON TABLE t_data_franchisee_shop_relation IS '加盟商门店关系表';
COMMENT ON COLUMN t_data_franchisee_shop_relation.id IS 'ID';
COMMENT ON COLUMN t_data_franchisee_shop_relation.franchisee_id IS '加盟商id';
COMMENT ON COLUMN t_data_franchisee_shop_relation.shop_id IS '门店ID';
COMMENT ON COLUMN t_data_franchisee_shop_relation.sort IS '排序';
COMMENT ON COLUMN t_data_franchisee_shop_relation.create_by IS '创建人';
COMMENT ON COLUMN t_data_franchisee_shop_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_data_franchisee_shop_relation.update_by IS '修改人';
COMMENT ON COLUMN t_data_franchisee_shop_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_shop_organization_relation;
CREATE TABLE t_data_shop_organization_relation
(
    id                VARCHAR(32) NOT NULL,
    shop_id           VARCHAR(32) NOT NULL,
    organization_id   VARCHAR(32) NOT NULL,
    organization_type VARCHAR(16) NOT NULL,
    sort              BIGINT NOT NULL DEFAULT 0,
    create_by         VARCHAR(32) NOT NULL,
    create_time       BIGINT NOT NULL,
    update_by         VARCHAR(32) NOT NULL,
    update_time       BIGINT NOT NULL,
    CONSTRAINT pk_t_data_shop_organization_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_data_shop_organization_relation_01 UNIQUE (shop_id, organization_type),
    CONSTRAINT uk_t_data_shop_organization_relation_02 UNIQUE (organization_id, shop_id)
);

COMMENT ON TABLE t_data_shop_organization_relation IS '门店组织关系表';
COMMENT ON COLUMN t_data_shop_organization_relation.id IS 'ID';
COMMENT ON COLUMN t_data_shop_organization_relation.shop_id IS '门店id';
COMMENT ON COLUMN t_data_shop_organization_relation.organization_id IS '组织id';
COMMENT ON COLUMN t_data_shop_organization_relation.organization_type IS '类型';
COMMENT ON COLUMN t_data_shop_organization_relation.sort IS '排序';
COMMENT ON COLUMN t_data_shop_organization_relation.create_by IS '创建人';
COMMENT ON COLUMN t_data_shop_organization_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_data_shop_organization_relation.update_by IS '修改人';
COMMENT ON COLUMN t_data_shop_organization_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_supplier_company;
CREATE TABLE t_data_supplier_company
(
    id                     VARCHAR(32) NOT NULL,
    name                   VARCHAR(32) NOT NULL,
    status                 VARCHAR(8) NOT NULL DEFAULT 'DISABLE',
    business_category      VARCHAR(32) NOT NULL,
    code                   VARCHAR(32) NOT NULL DEFAULT '',
    contact_name           VARCHAR(20) NOT NULL DEFAULT '',
    contact_phone          VARCHAR(20) NOT NULL DEFAULT '',
    correspondence_address VARCHAR(512) NOT NULL DEFAULT '',
    financial_information  VARCHAR(512) NOT NULL DEFAULT '',
    contract_information   VARCHAR(1024) NOT NULL DEFAULT '',
    create_by              VARCHAR(32) NOT NULL,
    create_time            BIGINT NOT NULL,
    update_by              VARCHAR(32) NOT NULL,
    update_time            BIGINT NOT NULL,
    CONSTRAINT pk_t_data_supplier_company PRIMARY KEY (id),
    CONSTRAINT uk_t_data_supplier_company_01 UNIQUE (code),
    CONSTRAINT uk_t_data_supplier_company_02 UNIQUE (name),
    CONSTRAINT uk_t_data_supplier_company_03 UNIQUE (contact_phone)
);

COMMENT ON TABLE t_data_supplier_company IS '供应商公司表';
COMMENT ON COLUMN t_data_supplier_company.id IS 'ID';
COMMENT ON COLUMN t_data_supplier_company.name IS '名称';
COMMENT ON COLUMN t_data_supplier_company.status IS '状态,（StatusEnum）';
COMMENT ON COLUMN t_data_supplier_company.business_category IS '供应商业务类别（SupplierBusinessCategoryEnum）';
COMMENT ON COLUMN t_data_supplier_company.code IS '编码';
COMMENT ON COLUMN t_data_supplier_company.contact_name IS '联系人';
COMMENT ON COLUMN t_data_supplier_company.contact_phone IS '联系电话';
COMMENT ON COLUMN t_data_supplier_company.correspondence_address IS '通讯地址';
COMMENT ON COLUMN t_data_supplier_company.financial_information IS '财务信息';
COMMENT ON COLUMN t_data_supplier_company.contract_information IS '合同信息';
COMMENT ON COLUMN t_data_supplier_company.create_by IS '创建人';
COMMENT ON COLUMN t_data_supplier_company.create_time IS '创建时间';
COMMENT ON COLUMN t_data_supplier_company.update_by IS '修改人';
COMMENT ON COLUMN t_data_supplier_company.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_supplier_company_relation;
CREATE TABLE t_data_supplier_company_relation
(
    id          VARCHAR(32) NOT NULL,
    supplier_id VARCHAR(32) NOT NULL,
    company_id  VARCHAR(32) NOT NULL,
    sort        BIGINT NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_supplier_company_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_data_supplier_company_relation_01 UNIQUE (supplier_id, company_id)
);

CREATE INDEX idx_t_data_supplier_company_relation_01 ON t_data_supplier_company_relation (company_id);

COMMENT ON TABLE t_data_supplier_company_relation IS '供应商公司关系表';
COMMENT ON COLUMN t_data_supplier_company_relation.id IS 'ID';
COMMENT ON COLUMN t_data_supplier_company_relation.supplier_id IS '供应商id';
COMMENT ON COLUMN t_data_supplier_company_relation.company_id IS '归属公司ID';
COMMENT ON COLUMN t_data_supplier_company_relation.sort IS '排序';
COMMENT ON COLUMN t_data_supplier_company_relation.create_by IS '创建人';
COMMENT ON COLUMN t_data_supplier_company_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_data_supplier_company_relation.update_by IS '修改人';
COMMENT ON COLUMN t_data_supplier_company_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_shop;
CREATE TABLE t_data_shop
(
    id               VARCHAR(32) NOT NULL,
    code             VARCHAR(16) NOT NULL,
    name             VARCHAR(32) NOT NULL,
    business_status  VARCHAR(32) NOT NULL,
    operation_status VARCHAR(32) NOT NULL,
    physical_status  VARCHAR(32) NOT NULL,
    country_code     VARCHAR(16) NOT NULL DEFAULT '',
    province_code    VARCHAR(16) NOT NULL DEFAULT '',
    city_code        VARCHAR(16) NOT NULL DEFAULT '',
    area_code        VARCHAR(16) NOT NULL DEFAULT '',
    address          VARCHAR(512) NOT NULL DEFAULT '',
    latitude         DECIMAL(11, 8),
    longitude        DECIMAL(11, 8),
    description      VARCHAR(2048) NOT NULL DEFAULT '',
    create_by        VARCHAR(32) NOT NULL,
    create_time      BIGINT NOT NULL,
    update_by        VARCHAR(32) NOT NULL,
    update_time      BIGINT NOT NULL,
    CONSTRAINT pk_t_data_shop PRIMARY KEY (id),
    CONSTRAINT uk_t_data_shop_01 UNIQUE (code),
    CONSTRAINT uk_t_data_shop_02 UNIQUE (name)
);

CREATE INDEX idx_t_data_shop_01 ON t_data_shop (business_status);
CREATE INDEX idx_t_data_shop_02 ON t_data_shop (operation_status);
CREATE INDEX idx_t_data_shop_03 ON t_data_shop (physical_status);
CREATE INDEX idx_t_data_shop_04 ON t_data_shop (province_code);
CREATE INDEX idx_t_data_shop_05 ON t_data_shop (city_code);
CREATE INDEX idx_t_data_shop_06 ON t_data_shop (area_code);
CREATE INDEX idx_t_data_shop_07 ON t_data_shop (create_time);
CREATE INDEX idx_t_data_shop_08 ON t_data_shop (update_time);

COMMENT ON TABLE t_data_shop IS '门店表';
COMMENT ON COLUMN t_data_shop.id IS 'ID';
COMMENT ON COLUMN t_data_shop.code IS '编码';
COMMENT ON COLUMN t_data_shop.name IS '名称';
COMMENT ON COLUMN t_data_shop.business_status IS '经营状态：门店的商业生命周期阶段';
COMMENT ON COLUMN t_data_shop.operation_status IS '运营状态：日常营业的可操作性状态';
COMMENT ON COLUMN t_data_shop.physical_status IS '物理状态：门店物理空间的可用性';
COMMENT ON COLUMN t_data_shop.country_code IS '国家编码';
COMMENT ON COLUMN t_data_shop.province_code IS '省编码';
COMMENT ON COLUMN t_data_shop.city_code IS '市编码';
COMMENT ON COLUMN t_data_shop.area_code IS '区编码';
COMMENT ON COLUMN t_data_shop.address IS '详细地址';
COMMENT ON COLUMN t_data_shop.latitude IS '经度';
COMMENT ON COLUMN t_data_shop.longitude IS '纬度';
COMMENT ON COLUMN t_data_shop.description IS '描述';
COMMENT ON COLUMN t_data_shop.create_by IS '创建人';
COMMENT ON COLUMN t_data_shop.create_time IS '创建时间';
COMMENT ON COLUMN t_data_shop.update_by IS '修改人';
COMMENT ON COLUMN t_data_shop.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_shop_label_option_relation;
CREATE TABLE t_data_shop_label_option_relation
(
    id              VARCHAR(32) NOT NULL,
    shop_id         VARCHAR(32) NOT NULL,
    label_option_id VARCHAR(32) NOT NULL,
    sort            BIGINT NOT NULL DEFAULT 0,
    create_by       VARCHAR(32) NOT NULL,
    create_time     BIGINT NOT NULL,
    update_by       VARCHAR(32) NOT NULL,
    update_time     BIGINT NOT NULL,
    CONSTRAINT pk_t_data_shop_label_option_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_data_shop_label_option_relation_01 UNIQUE (shop_id, label_option_id)
);

CREATE INDEX idx_t_data_shop_label_option_relation_01 ON t_data_shop_label_option_relation (label_option_id, shop_id);

COMMENT ON TABLE t_data_shop_label_option_relation IS '门店&人工标签选项关系表';
COMMENT ON COLUMN t_data_shop_label_option_relation.id IS 'ID';
COMMENT ON COLUMN t_data_shop_label_option_relation.shop_id IS '门店ID';
COMMENT ON COLUMN t_data_shop_label_option_relation.label_option_id IS '人工标签选项id';
COMMENT ON COLUMN t_data_shop_label_option_relation.sort IS '排序';
COMMENT ON COLUMN t_data_shop_label_option_relation.create_by IS '创建人';
COMMENT ON COLUMN t_data_shop_label_option_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_data_shop_label_option_relation.update_by IS '修改人';
COMMENT ON COLUMN t_data_shop_label_option_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_user_collect_shop;
CREATE TABLE t_data_user_collect_shop
(
    id          VARCHAR(32) NOT NULL,
    user_id     VARCHAR(32) NOT NULL,
    shop_id     VARCHAR(32) NOT NULL,
    sort        BIGINT NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_user_collect_shop PRIMARY KEY (id),
    CONSTRAINT uk_t_data_user_collect_shop_01 UNIQUE (user_id, shop_id)
);

COMMENT ON TABLE t_data_user_collect_shop IS '用户收藏门店信息';
COMMENT ON COLUMN t_data_user_collect_shop.id IS 'ID';
COMMENT ON COLUMN t_data_user_collect_shop.user_id IS '用户Id';
COMMENT ON COLUMN t_data_user_collect_shop.shop_id IS '门店ID';
COMMENT ON COLUMN t_data_user_collect_shop.sort IS '排序';
COMMENT ON COLUMN t_data_user_collect_shop.create_by IS '创建人';
COMMENT ON COLUMN t_data_user_collect_shop.create_time IS '创建时间';
COMMENT ON COLUMN t_data_user_collect_shop.update_by IS '修改人';
COMMENT ON COLUMN t_data_user_collect_shop.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_external_field_data;
CREATE TABLE t_data_external_field_data
(
    id             VARCHAR(32) NOT NULL,
    table_name     VARCHAR(32) NOT NULL,
    field_name     VARCHAR(32) NOT NULL,
    data_id        VARCHAR(32) NOT NULL,
    external_value TEXT,
    create_by      VARCHAR(32) NOT NULL,
    create_time    BIGINT NOT NULL,
    update_by      VARCHAR(32) NOT NULL,
    update_time    BIGINT NOT NULL,
    CONSTRAINT pk_t_data_external_field_data PRIMARY KEY (id),
    CONSTRAINT uk_t_data_external_field_data_01 UNIQUE (table_name, field_name, data_id)
);

COMMENT ON TABLE t_data_external_field_data IS '扩展字段数据表';
COMMENT ON COLUMN t_data_external_field_data.id IS 'ID';
COMMENT ON COLUMN t_data_external_field_data.table_name IS '表名';
COMMENT ON COLUMN t_data_external_field_data.field_name IS '字段名';
COMMENT ON COLUMN t_data_external_field_data.data_id IS '数据Id';
COMMENT ON COLUMN t_data_external_field_data.external_value IS '扩展字段值';
COMMENT ON COLUMN t_data_external_field_data.create_by IS '创建人';
COMMENT ON COLUMN t_data_external_field_data.create_time IS '创建时间';
COMMENT ON COLUMN t_data_external_field_data.update_by IS '修改人';
COMMENT ON COLUMN t_data_external_field_data.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_mq_reliable_message;
CREATE TABLE t_data_mq_reliable_message
(
    id                 VARCHAR(32) NOT NULL,
    message_uuid       VARCHAR(32) NOT NULL,
    producer_name      VARCHAR(64) NOT NULL,
    consumer_name      VARCHAR(64) NOT NULL,
    resend_times       INTEGER DEFAULT 0,
    last_send_time     BIGINT NOT NULL,
    consumer_times     INTEGER DEFAULT 0,
    last_consumer_time BIGINT NOT NULL,
    next_exe_time      BIGINT NOT NULL,
    retry_strategy     VARCHAR(512),
    create_by          VARCHAR(32) NOT NULL,
    create_time        BIGINT NOT NULL,
    update_by          VARCHAR(32) NOT NULL,
    update_time        BIGINT NOT NULL,
    CONSTRAINT pk_t_data_mq_reliable_message PRIMARY KEY (id),
    CONSTRAINT uk_t_data_mq_reliable_message_01 UNIQUE (message_uuid, consumer_name)
);

CREATE INDEX idx_t_data_mq_reliable_message_01 ON t_data_mq_reliable_message (next_exe_time);

COMMENT ON TABLE t_data_mq_reliable_message IS 'MQ 可靠消息表';
COMMENT ON COLUMN t_data_mq_reliable_message.id IS 'id';
COMMENT ON COLUMN t_data_mq_reliable_message.message_uuid IS '消息唯一标识';
COMMENT ON COLUMN t_data_mq_reliable_message.producer_name IS '生产者名称';
COMMENT ON COLUMN t_data_mq_reliable_message.consumer_name IS '消费者名称';
COMMENT ON COLUMN t_data_mq_reliable_message.resend_times IS '重发次数';
COMMENT ON COLUMN t_data_mq_reliable_message.last_send_time IS '最后一次重发时间';
COMMENT ON COLUMN t_data_mq_reliable_message.consumer_times IS '消费次数';
COMMENT ON COLUMN t_data_mq_reliable_message.last_consumer_time IS '最后一次消费时间';
COMMENT ON COLUMN t_data_mq_reliable_message.next_exe_time IS '下一次执行时间';
COMMENT ON COLUMN t_data_mq_reliable_message.retry_strategy IS '重试策略';
COMMENT ON COLUMN t_data_mq_reliable_message.create_by IS '创建人';
COMMENT ON COLUMN t_data_mq_reliable_message.create_time IS '创建时间';
COMMENT ON COLUMN t_data_mq_reliable_message.update_by IS '修改人';
COMMENT ON COLUMN t_data_mq_reliable_message.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_mq_dead_message;
CREATE TABLE t_data_mq_dead_message
(
    id                 VARCHAR(32) NOT NULL,
    message_uuid       VARCHAR(32) NOT NULL,
    producer_name      VARCHAR(64) NOT NULL,
    consumer_name      VARCHAR(64) NOT NULL,
    resend_times       INTEGER DEFAULT 0,
    last_send_time     BIGINT NOT NULL,
    consumer_times     INTEGER DEFAULT 0,
    last_consumer_time BIGINT NOT NULL,
    next_exe_time      BIGINT NOT NULL,
    retry_strategy     VARCHAR(512),
    create_by          VARCHAR(32) NOT NULL,
    create_time        BIGINT NOT NULL,
    update_by          VARCHAR(32) NOT NULL,
    update_time        BIGINT NOT NULL,
    CONSTRAINT pk_t_data_mq_dead_message PRIMARY KEY (id),
    CONSTRAINT uk_t_data_mq_dead_message_01 UNIQUE (message_uuid, consumer_name)
);

CREATE INDEX idx_t_data_mq_dead_message_01 ON t_data_mq_dead_message (create_time);

COMMENT ON TABLE t_data_mq_dead_message IS 'MQ 死亡消息信息表';
COMMENT ON COLUMN t_data_mq_dead_message.id IS 'id';
COMMENT ON COLUMN t_data_mq_dead_message.message_uuid IS '消息唯一标识';
COMMENT ON COLUMN t_data_mq_dead_message.producer_name IS '生产者名称';
COMMENT ON COLUMN t_data_mq_dead_message.consumer_name IS '消费者名称';
COMMENT ON COLUMN t_data_mq_dead_message.resend_times IS '重发次数';
COMMENT ON COLUMN t_data_mq_dead_message.last_send_time IS '最后一次重发时间';
COMMENT ON COLUMN t_data_mq_dead_message.consumer_times IS '消费次数';
COMMENT ON COLUMN t_data_mq_dead_message.last_consumer_time IS '最后一次消费时间';
COMMENT ON COLUMN t_data_mq_dead_message.next_exe_time IS '下一次执行时间';
COMMENT ON COLUMN t_data_mq_dead_message.retry_strategy IS '重试策略';
COMMENT ON COLUMN t_data_mq_dead_message.create_by IS '创建人';
COMMENT ON COLUMN t_data_mq_dead_message.create_time IS '创建时间';
COMMENT ON COLUMN t_data_mq_dead_message.update_by IS '修改人';
COMMENT ON COLUMN t_data_mq_dead_message.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_mq_message_external;
CREATE TABLE t_data_mq_message_external
(
    id          VARCHAR(32) NOT NULL,
    table_name  VARCHAR(32) NOT NULL,
    data_id     VARCHAR(32) NOT NULL,
    content     TEXT,
    fail_reason TEXT,
    description TEXT,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_mq_message_external PRIMARY KEY (id)
);

CREATE INDEX idx_t_data_mq_message_external_01 ON t_data_mq_message_external (data_id, table_name);

COMMENT ON TABLE t_data_mq_message_external IS 'MQ 消息扩展信息表';
COMMENT ON COLUMN t_data_mq_message_external.id IS 'id';
COMMENT ON COLUMN t_data_mq_message_external.table_name IS '表名';
COMMENT ON COLUMN t_data_mq_message_external.data_id IS '数据Id';
COMMENT ON COLUMN t_data_mq_message_external.content IS '内容';
COMMENT ON COLUMN t_data_mq_message_external.fail_reason IS '最近一次失败原因';
COMMENT ON COLUMN t_data_mq_message_external.description IS '描述';
COMMENT ON COLUMN t_data_mq_message_external.create_by IS '创建人';
COMMENT ON COLUMN t_data_mq_message_external.create_time IS '创建时间';
COMMENT ON COLUMN t_data_mq_message_external.update_by IS '修改人';
COMMENT ON COLUMN t_data_mq_message_external.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_app_message_template;
CREATE TABLE t_data_app_message_template
(
    id                VARCHAR(32) NOT NULL,
    template_type     VARCHAR(100),
    template_category VARCHAR(100),
    channel           VARCHAR(500),
    template_info     TEXT,
    inform_title      VARCHAR(100),
    inform_content    TEXT,
    sms_content       TEXT,
    status            VARCHAR(10),
    create_by         VARCHAR(32) NOT NULL,
    create_time       BIGINT NOT NULL,
    update_by         VARCHAR(32) NOT NULL,
    update_time       BIGINT NOT NULL,
    dr                SMALLINT NOT NULL DEFAULT 0,
    CONSTRAINT pk_t_data_app_message_template PRIMARY KEY (id)
);

CREATE INDEX idx_t_data_app_message_template_01 ON t_data_app_message_template (template_type);
CREATE INDEX idx_t_data_app_message_template_02 ON t_data_app_message_template (channel);

COMMENT ON TABLE t_data_app_message_template IS '超级APP消息模版表';
COMMENT ON COLUMN t_data_app_message_template.id IS 'ID';
COMMENT ON COLUMN t_data_app_message_template.template_type IS '模版类型(选址跟进提醒、迁址流程等具体消息的类型)';
COMMENT ON COLUMN t_data_app_message_template.template_category IS '消息分类(合同、选址任务等)';
COMMENT ON COLUMN t_data_app_message_template.channel IS '通知渠道(站内信、AppPush、企微工作通知)';
COMMENT ON COLUMN t_data_app_message_template.template_info IS '模版信息(通知时间、通知对象等)';
COMMENT ON COLUMN t_data_app_message_template.inform_title IS '通知标题';
COMMENT ON COLUMN t_data_app_message_template.inform_content IS '通知内容';
COMMENT ON COLUMN t_data_app_message_template.sms_content IS '短信内容';
COMMENT ON COLUMN t_data_app_message_template.status IS '状态(DISABLE-禁用，ENABLE-启用)';
COMMENT ON COLUMN t_data_app_message_template.create_by IS '创建人';
COMMENT ON COLUMN t_data_app_message_template.create_time IS '创建时间';
COMMENT ON COLUMN t_data_app_message_template.update_by IS '修改人';
COMMENT ON COLUMN t_data_app_message_template.update_time IS '更新时间';
COMMENT ON COLUMN t_data_app_message_template.dr IS '';

DROP TABLE IF EXISTS t_data_app_message;
CREATE TABLE t_data_app_message
(
    id                  VARCHAR(32) NOT NULL,
    batch_code          VARCHAR(32),
    message_template_id VARCHAR(32) NOT NULL,
    message_category    VARCHAR(255),
    operator_id         VARCHAR(32),
    operator_phone      VARCHAR(20),
    receiver            VARCHAR(32),
    phone               VARCHAR(20),
    readed              SMALLINT,
    title               VARCHAR(255),
    inform_content      TEXT,
    dispose             SMALLINT,
    channel             VARCHAR(20),
    query_source        VARCHAR(255),
    business_content    TEXT,
    create_by           VARCHAR(32) NOT NULL,
    create_time         BIGINT NOT NULL,
    update_by           VARCHAR(32) NOT NULL,
    update_time         BIGINT NOT NULL,
    dr                  SMALLINT NOT NULL DEFAULT 0,
    CONSTRAINT pk_t_data_app_message PRIMARY KEY (id)
);

CREATE INDEX idx_t_data_app_message_01 ON t_data_app_message (phone);
CREATE INDEX idx_t_data_app_message_02 ON t_data_app_message (message_template_id);
CREATE INDEX idx_t_data_app_message_03 ON t_data_app_message (create_time);

COMMENT ON TABLE t_data_app_message IS '超级APP消息表';
COMMENT ON COLUMN t_data_app_message.id IS 'ID';
COMMENT ON COLUMN t_data_app_message.batch_code IS '批次号';
COMMENT ON COLUMN t_data_app_message.message_template_id IS '模版id';
COMMENT ON COLUMN t_data_app_message.message_category IS '消息分类';
COMMENT ON COLUMN t_data_app_message.operator_id IS '操作人';
COMMENT ON COLUMN t_data_app_message.operator_phone IS '操作人手机号';
COMMENT ON COLUMN t_data_app_message.receiver IS '接收人';
COMMENT ON COLUMN t_data_app_message.phone IS '接收人手机号';
COMMENT ON COLUMN t_data_app_message.readed IS '是否已读';
COMMENT ON COLUMN t_data_app_message.title IS '消息标题';
COMMENT ON COLUMN t_data_app_message.inform_content IS '消息内容';
COMMENT ON COLUMN t_data_app_message.dispose IS '是否已处理';
COMMENT ON COLUMN t_data_app_message.channel IS '消息渠道';
COMMENT ON COLUMN t_data_app_message.query_source IS '查询来源';
COMMENT ON COLUMN t_data_app_message.business_content IS '业务内容';
COMMENT ON COLUMN t_data_app_message.create_by IS '创建人';
COMMENT ON COLUMN t_data_app_message.create_time IS '创建时间';
COMMENT ON COLUMN t_data_app_message.update_by IS '修改人';
COMMENT ON COLUMN t_data_app_message.update_time IS '更新时间';
COMMENT ON COLUMN t_data_app_message.dr IS '';

DROP TABLE IF EXISTS t_data_sms_record;
CREATE TABLE t_data_sms_record
(
    id              VARCHAR(32) NOT NULL,
    category        VARCHAR(32) NOT NULL,
    code            VARCHAR(64) NOT NULL,
    result          VARCHAR(8) NOT NULL,
    phone           VARCHAR(16) NOT NULL,
    message_content TEXT,
    result_content  TEXT,
    fail_reason     TEXT,
    create_by       VARCHAR(32) NOT NULL,
    create_time     BIGINT NOT NULL,
    update_by       VARCHAR(32) NOT NULL,
    update_time     BIGINT NOT NULL,
    CONSTRAINT pk_t_data_sms_record PRIMARY KEY (id)
);

CREATE INDEX idx_t_data_sms_record_01 ON t_data_sms_record (phone);
CREATE INDEX idx_t_data_sms_record_02 ON t_data_sms_record (category, code, phone);
CREATE INDEX idx_t_data_sms_record_03 ON t_data_sms_record (create_time);

COMMENT ON TABLE t_data_sms_record IS '短信记录表';
COMMENT ON COLUMN t_data_sms_record.id IS 'ID';
COMMENT ON COLUMN t_data_sms_record.category IS '分类(SmsCategoryEnum)';
COMMENT ON COLUMN t_data_sms_record.code IS '编码';
COMMENT ON COLUMN t_data_sms_record.result IS '结果(SmsSendResultEnum)';
COMMENT ON COLUMN t_data_sms_record.phone IS '手机号';
COMMENT ON COLUMN t_data_sms_record.message_content IS '消息内容';
COMMENT ON COLUMN t_data_sms_record.result_content IS '返回内容';
COMMENT ON COLUMN t_data_sms_record.fail_reason IS '失败原因';
COMMENT ON COLUMN t_data_sms_record.create_by IS '创建人';
COMMENT ON COLUMN t_data_sms_record.create_time IS '创建时间';
COMMENT ON COLUMN t_data_sms_record.update_by IS '修改人';
COMMENT ON COLUMN t_data_sms_record.update_time IS '更新时间';


DROP TABLE IF EXISTS t_data_brand;
CREATE TABLE t_data_brand
(
    id               VARCHAR(32) NOT NULL,
    code             VARCHAR(16) NOT NULL,
    name             VARCHAR(64) NOT NULL DEFAULT '',
    status           VARCHAR(8) NOT NULL DEFAULT 'DISABLE',
    logo_material_id VARCHAR(32) NOT NULL DEFAULT '',
    description      VARCHAR(2048) NOT NULL DEFAULT '',
    create_by        VARCHAR(32) NOT NULL,
    create_time      BIGINT NOT NULL,
    update_by        VARCHAR(32) NOT NULL,
    update_time      BIGINT NOT NULL,
    CONSTRAINT pk_t_data_brand PRIMARY KEY (id),
    CONSTRAINT uk_t_data_brand_01 UNIQUE (code)
);

CREATE INDEX idx_t_data_brand_01 ON t_data_brand (create_time);
CREATE INDEX idx_t_data_brand_02 ON t_data_brand (update_time);

COMMENT ON TABLE t_data_brand IS '品牌信息表';
COMMENT ON COLUMN t_data_brand.id IS 'ID';
COMMENT ON COLUMN t_data_brand.code IS '编码';
COMMENT ON COLUMN t_data_brand.name IS '名称';
COMMENT ON COLUMN t_data_brand.status IS '状态';
COMMENT ON COLUMN t_data_brand.logo_material_id IS 'logo素材ID';
COMMENT ON COLUMN t_data_brand.description IS '描述';
COMMENT ON COLUMN t_data_brand.create_by IS '创建人';
COMMENT ON COLUMN t_data_brand.create_time IS '创建时间';
COMMENT ON COLUMN t_data_brand.update_by IS '修改人';
COMMENT ON COLUMN t_data_brand.update_time IS '更新时间';