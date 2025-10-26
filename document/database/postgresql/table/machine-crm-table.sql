DROP TABLE IF EXISTS t_crm_customer;
CREATE TABLE t_crm_customer
(
    id                   VARCHAR(32) NOT NULL,
    code                 VARCHAR(16) NOT NULL,
    identity_card_number VARCHAR(32) NOT NULL,
    name                 VARCHAR(64) NOT NULL,
    gender               VARCHAR(16) NOT NULL DEFAULT 'UNDEFINED',
    create_by            VARCHAR(32) NOT NULL,
    create_time          BIGINT NOT NULL,
    update_by            VARCHAR(32) NOT NULL,
    update_time          BIGINT NOT NULL,
    CONSTRAINT pk_t_crm_customer PRIMARY KEY (id),
    CONSTRAINT uk_t_crm_customer_01 UNIQUE (code),
    CONSTRAINT uk_t_crm_customer_02 UNIQUE (identity_card_number)
);

CREATE INDEX idx_t_crm_customer_01 ON t_crm_customer (create_time);

COMMENT ON TABLE t_crm_customer IS '客户表';
COMMENT ON COLUMN t_crm_customer.id IS 'ID';
COMMENT ON COLUMN t_crm_customer.code IS '编码';
COMMENT ON COLUMN t_crm_customer.identity_card_number IS '身份证号';
COMMENT ON COLUMN t_crm_customer.name IS '姓名';
COMMENT ON COLUMN t_crm_customer.gender IS '性别';
COMMENT ON COLUMN t_crm_customer.create_by IS '创建人';
COMMENT ON COLUMN t_crm_customer.create_time IS '创建时间';
COMMENT ON COLUMN t_crm_customer.update_by IS '修改人';
COMMENT ON COLUMN t_crm_customer.update_time IS '更新时间';


DROP TABLE IF EXISTS t_crm_member;
CREATE TABLE t_crm_member
(
    id          VARCHAR(32) NOT NULL,
    code        VARCHAR(16) NOT NULL,
    email       VARCHAR(32) NOT NULL DEFAULT '',
    phone       VARCHAR(16) NOT NULL,
    name        VARCHAR(64) NOT NULL,
    gender      VARCHAR(16) NOT NULL DEFAULT 'UNDEFINED',
    birth_year  INTEGER NOT NULL DEFAULT 0,
    birth_month INTEGER NOT NULL DEFAULT 0,
    birth_day   INTEGER NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_crm_member PRIMARY KEY (id),
    CONSTRAINT uk_t_crm_member_01 UNIQUE (code),
    CONSTRAINT uk_t_crm_member_02 UNIQUE (phone)
);

CREATE INDEX idx_t_crm_member_01 ON t_crm_member (email);
CREATE INDEX idx_t_crm_member_02 ON t_crm_member (create_time);

COMMENT ON TABLE t_crm_member IS '会员表';
COMMENT ON COLUMN t_crm_member.id IS 'ID';
COMMENT ON COLUMN t_crm_member.code IS '编码';
COMMENT ON COLUMN t_crm_member.email IS '邮箱';
COMMENT ON COLUMN t_crm_member.phone IS '手机号';
COMMENT ON COLUMN t_crm_member.name IS '姓名';
COMMENT ON COLUMN t_crm_member.gender IS '性别';
COMMENT ON COLUMN t_crm_member.birth_year IS '生日年';
COMMENT ON COLUMN t_crm_member.birth_month IS '生日月';
COMMENT ON COLUMN t_crm_member.birth_day IS '生日天';
COMMENT ON COLUMN t_crm_member.create_by IS '创建人';
COMMENT ON COLUMN t_crm_member.create_time IS '创建时间';
COMMENT ON COLUMN t_crm_member.update_by IS '修改人';
COMMENT ON COLUMN t_crm_member.update_time IS '更新时间';
