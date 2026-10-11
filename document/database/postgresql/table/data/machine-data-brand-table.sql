DROP TABLE IF EXISTS t_data_brand;
CREATE TABLE t_data_brand
(
    id                  VARCHAR(32)   NOT NULL,
    parent_id           VARCHAR(32)   NOT NULL,
    code                VARCHAR(16)   NOT NULL,
    name                VARCHAR(64)   NOT NULL DEFAULT '',
    name_pinyin_full    VARCHAR(64)   NOT NULL DEFAULT '',
    name_pinyin_initial VARCHAR(64)   NOT NULL DEFAULT '',
    name_en             VARCHAR(64)   NOT NULL DEFAULT '',
    status              VARCHAR(8)    NOT NULL DEFAULT 'DISABLE',
    sort                BIGINT        NOT NULL DEFAULT 0,
    logo_attachment_id  VARCHAR(32)   NOT NULL DEFAULT '',
    description         VARCHAR(2048) NOT NULL DEFAULT '',
    create_by           VARCHAR(32)   NOT NULL,
    create_time         BIGINT        NOT NULL,
    update_by           VARCHAR(32)   NOT NULL,
    update_time         BIGINT        NOT NULL,
    CONSTRAINT pk_t_data_brand PRIMARY KEY (id),
    CONSTRAINT uk_t_data_brand_01 UNIQUE (code),
    CONSTRAINT uk_t_data_brand_02 UNIQUE (name)
);

CREATE INDEX idx_t_data_brand_01 ON t_data_brand (name_pinyin_full);
CREATE INDEX idx_t_data_brand_02 ON t_data_brand (name_pinyin_initial);
CREATE INDEX idx_t_data_brand_03 ON t_data_brand (create_time);

COMMENT ON TABLE t_data_brand IS '品牌信息表';
COMMENT ON COLUMN t_data_brand.id IS 'ID';
COMMENT ON COLUMN t_data_brand.parent_id IS '父品牌ID';
COMMENT ON COLUMN t_data_brand.code IS '编码';
COMMENT ON COLUMN t_data_brand.name IS '名称';
COMMENT ON COLUMN t_data_brand.name_pinyin_full IS '拼音全拼';
COMMENT ON COLUMN t_data_brand.name_pinyin_initial IS '拼音首字母';
COMMENT ON COLUMN t_data_brand.name_en IS '英文名称';
COMMENT ON COLUMN t_data_brand.status IS '状态';
COMMENT ON COLUMN t_data_brand.sort IS '排序';
COMMENT ON COLUMN t_data_brand.logo_attachment_id IS 'logo附件ID';
COMMENT ON COLUMN t_data_brand.description IS '描述';
COMMENT ON COLUMN t_data_brand.create_by IS '创建人';
COMMENT ON COLUMN t_data_brand.create_time IS '创建时间';
COMMENT ON COLUMN t_data_brand.update_by IS '修改人';
COMMENT ON COLUMN t_data_brand.update_time IS '更新时间';


DROP TABLE IF EXISTS t_data_brand_category;
CREATE TABLE t_data_brand_category
(
    id          VARCHAR(32) NOT NULL,
    parent_id   VARCHAR(32) NOT NULL,
    code        VARCHAR(32) NOT NULL,
    name        VARCHAR(32) NOT NULL,
    sort        BIGINT NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_brand_category PRIMARY KEY (id),
    CONSTRAINT uk_t_data_brand_category_01 UNIQUE (code),
    CONSTRAINT uk_t_data_brand_category_02 UNIQUE (parent_id, name)
);

CREATE INDEX idx_t_data_brand_category_01 ON t_data_brand_category (update_time);

COMMENT ON TABLE t_data_brand_category IS '品牌分类表';
COMMENT ON COLUMN t_data_brand_category.id IS 'ID';
COMMENT ON COLUMN t_data_brand_category.parent_id IS '父ID';
COMMENT ON COLUMN t_data_brand_category.code IS '编码';
COMMENT ON COLUMN t_data_brand_category.name IS '名称';
COMMENT ON COLUMN t_data_brand_category.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_data_brand_category.create_by IS '创建人';
COMMENT ON COLUMN t_data_brand_category.create_time IS '创建时间';
COMMENT ON COLUMN t_data_brand_category.update_by IS '修改人';
COMMENT ON COLUMN t_data_brand_category.update_time IS '更新时间';


DROP TABLE IF EXISTS t_data_brand_category_relation;
CREATE TABLE t_data_brand_category_relation
(
    id          VARCHAR(32) NOT NULL,
    brand_id    VARCHAR(32) NOT NULL,
    category_id VARCHAR(32) NOT NULL,
    sort        BIGINT NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_brand_category_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_data_brand_category_relation_01 UNIQUE (brand_id, category_id)
);

ALTER TABLE t_data_brand_category_relation
    ADD CONSTRAINT fk_t_data_brand_category_relation_01
        FOREIGN KEY (brand_id) REFERENCES t_data_brand (id);

ALTER TABLE t_data_brand_category_relation
    ADD CONSTRAINT fk_t_data_brand_category_relation_02
        FOREIGN KEY (category_id) REFERENCES t_data_brand_category (id);

CREATE INDEX idx_t_data_brand_category_relation_01 ON t_data_brand_category_relation (category_id);
CREATE INDEX idx_t_data_brand_category_relation_02 ON t_data_brand_category_relation (update_time);

COMMENT ON TABLE t_data_brand_category_relation IS '品牌分类关联表';
COMMENT ON COLUMN t_data_brand_category_relation.id IS 'ID';
COMMENT ON COLUMN t_data_brand_category_relation.brand_id IS '品牌ID';
COMMENT ON COLUMN t_data_brand_category_relation.category_id IS '品牌分类ID';
COMMENT ON COLUMN t_data_brand_category_relation.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_data_brand_category_relation.create_by IS '创建人';
COMMENT ON COLUMN t_data_brand_category_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_data_brand_category_relation.update_by IS '修改人';
COMMENT ON COLUMN t_data_brand_category_relation.update_time IS '更新时间';