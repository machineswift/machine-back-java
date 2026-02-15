
DROP TABLE IF EXISTS t_data_item_category;
CREATE TABLE t_data_item_category
(
    id          VARCHAR(32) NOT NULL,
    parent_id   VARCHAR(32) NOT NULL,
    code        VARCHAR(32) NOT NULL,
    name        VARCHAR(32) NOT NULL,
    status      VARCHAR(8) NOT NULL DEFAULT 'DISABLE',
    sort        BIGINT NOT NULL DEFAULT 0,
    description VARCHAR(2048) NOT NULL DEFAULT '',
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_item_category PRIMARY KEY (id),
    CONSTRAINT uk_t_data_item_category_01 UNIQUE (code),
    CONSTRAINT uk_t_data_item_category_02 UNIQUE (parent_id, name)
);

CREATE INDEX idx_t_data_item_category_01 ON t_data_item_category (create_time);
CREATE INDEX idx_t_data_item_category_02 ON t_data_item_category (update_time);

COMMENT ON TABLE t_data_item_category IS '商品分类表';
COMMENT ON COLUMN t_data_item_category.id IS 'ID';
COMMENT ON COLUMN t_data_item_category.parent_id IS '父ID';
COMMENT ON COLUMN t_data_item_category.code IS '编码';
COMMENT ON COLUMN t_data_item_category.name IS '名称';
COMMENT ON COLUMN t_data_item_category.status IS '状态';
COMMENT ON COLUMN t_data_item_category.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_data_item_category.description IS '描述';
COMMENT ON COLUMN t_data_item_category.create_by IS '创建人';
COMMENT ON COLUMN t_data_item_category.create_time IS '创建时间';
COMMENT ON COLUMN t_data_item_category.update_by IS '修改人';
COMMENT ON COLUMN t_data_item_category.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_item_property;
CREATE TABLE t_data_item_property
(
    id          VARCHAR(32) NOT NULL,
    code        VARCHAR(32) NOT NULL,
    name        VARCHAR(32) NOT NULL,
    sort        BIGINT NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_item_property PRIMARY KEY (id),
    CONSTRAINT uk_t_data_item_property_01 UNIQUE (code),
    CONSTRAINT uk_t_data_item_property_02 UNIQUE (name)
);

CREATE INDEX idx_t_data_item_property_01 ON t_data_item_property (name);
CREATE INDEX idx_t_data_item_property_02 ON t_data_item_property (create_time);
CREATE INDEX idx_t_data_item_property_03 ON t_data_item_property (update_time);

COMMENT ON TABLE t_data_item_property IS '商品属性表';
COMMENT ON COLUMN t_data_item_property.id IS 'ID';
COMMENT ON COLUMN t_data_item_property.code IS '编码';
COMMENT ON COLUMN t_data_item_property.name IS '名称';
COMMENT ON COLUMN t_data_item_property.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_data_item_property.create_by IS '创建人';
COMMENT ON COLUMN t_data_item_property.create_time IS '创建时间';
COMMENT ON COLUMN t_data_item_property.update_by IS '修改人';
COMMENT ON COLUMN t_data_item_property.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_item_property_value;
CREATE TABLE t_data_item_property_value
(
    id          VARCHAR(32) NOT NULL,
    value       VARCHAR(64) NOT NULL,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_item_property_value PRIMARY KEY (id),
    CONSTRAINT uk_t_data_item_property_value_01 UNIQUE (value)
);

CREATE INDEX idx_t_data_item_property_value_01 ON t_data_item_property_value (update_time);

COMMENT ON TABLE t_data_item_property_value IS '商品属性值表';
COMMENT ON COLUMN t_data_item_property_value.id IS 'ID';
COMMENT ON COLUMN t_data_item_property_value.value IS '值';
COMMENT ON COLUMN t_data_item_property_value.create_by IS '创建人';
COMMENT ON COLUMN t_data_item_property_value.create_time IS '创建时间';
COMMENT ON COLUMN t_data_item_property_value.update_by IS '修改人';
COMMENT ON COLUMN t_data_item_property_value.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_item_property_value_relation;
CREATE TABLE t_data_item_property_value_relation
(
    id                     VARCHAR(32) NOT NULL,
    item_property_id       VARCHAR(32) NOT NULL,
    item_property_value_id VARCHAR(32) NOT NULL,
    sort                   BIGINT NOT NULL DEFAULT 0,
    create_by              VARCHAR(32) NOT NULL,
    create_time            BIGINT NOT NULL,
    update_by              VARCHAR(32) NOT NULL,
    update_time            BIGINT NOT NULL,
    CONSTRAINT pk_t_data_item_property_value_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_data_item_property_value_relation_01 UNIQUE (item_property_id, item_property_value_id)
);

CREATE INDEX idx_t_data_item_property_value_relation_01 ON t_data_item_property_value_relation (item_property_value_id);

COMMENT ON TABLE t_data_item_property_value_relation IS '商品属性值关系表';
COMMENT ON COLUMN t_data_item_property_value_relation.id IS 'ID';
COMMENT ON COLUMN t_data_item_property_value_relation.item_property_id IS '商品属性ID';
COMMENT ON COLUMN t_data_item_property_value_relation.item_property_value_id IS '商品属性值ID';
COMMENT ON COLUMN t_data_item_property_value_relation.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_data_item_property_value_relation.create_by IS '创建人';
COMMENT ON COLUMN t_data_item_property_value_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_data_item_property_value_relation.update_by IS '修改人';
COMMENT ON COLUMN t_data_item_property_value_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_item_spu;
CREATE TABLE t_data_item_spu
(
    id          VARCHAR(32) NOT NULL,
    code        VARCHAR(32) NOT NULL,
    name        VARCHAR(32) NOT NULL,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_item_spu PRIMARY KEY (id),
    CONSTRAINT uk_t_data_item_spu_01 UNIQUE (code),
    CONSTRAINT uk_t_data_item_spu_02 UNIQUE (name)
);

CREATE INDEX idx_t_data_item_spu_01 ON t_data_item_spu (update_time);

COMMENT ON TABLE t_data_item_spu IS '商品spu表';
COMMENT ON COLUMN t_data_item_spu.id IS 'ID';
COMMENT ON COLUMN t_data_item_spu.code IS '编码';
COMMENT ON COLUMN t_data_item_spu.name IS '名称';
COMMENT ON COLUMN t_data_item_spu.create_by IS '创建人';
COMMENT ON COLUMN t_data_item_spu.create_time IS '创建时间';
COMMENT ON COLUMN t_data_item_spu.update_by IS '修改人';
COMMENT ON COLUMN t_data_item_spu.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_item_sku;
CREATE TABLE t_data_item_sku
(
    id          VARCHAR(32) NOT NULL,
    item_spu_id VARCHAR(32) NOT NULL,
    code        VARCHAR(32) NOT NULL,
    name        VARCHAR(32) NOT NULL,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_item_sku PRIMARY KEY (id),
    CONSTRAINT uk_t_data_item_sku_01 UNIQUE (code),
    CONSTRAINT uk_t_data_item_sku_02 UNIQUE (item_spu_id, name)
);

CREATE INDEX idx_t_data_item_sku_01 ON t_data_item_sku (update_time);

COMMENT ON TABLE t_data_item_sku IS '商品sku表';
COMMENT ON COLUMN t_data_item_sku.id IS 'ID';
COMMENT ON COLUMN t_data_item_sku.item_spu_id IS '商品spu ID';
COMMENT ON COLUMN t_data_item_sku.code IS '编码';
COMMENT ON COLUMN t_data_item_sku.name IS '名称';
COMMENT ON COLUMN t_data_item_sku.create_by IS '创建人';
COMMENT ON COLUMN t_data_item_sku.create_time IS '创建时间';
COMMENT ON COLUMN t_data_item_sku.update_by IS '修改人';
COMMENT ON COLUMN t_data_item_sku.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_item_sn;
CREATE TABLE t_data_item_sn
(
    id          VARCHAR(32) NOT NULL,
    item_sku_id VARCHAR(32) NOT NULL,
    code        VARCHAR(32) NOT NULL,
    name        VARCHAR(32) NOT NULL,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_item_sn PRIMARY KEY (id),
    CONSTRAINT uk_t_data_item_sn_01 UNIQUE (code)
);

CREATE INDEX idx_t_data_item_sn_01 ON t_data_item_sn (item_sku_id);
CREATE INDEX idx_t_data_item_sn_02 ON t_data_item_sn (update_time);

COMMENT ON TABLE t_data_item_sn IS '商品sn表';
COMMENT ON COLUMN t_data_item_sn.id IS 'ID';
COMMENT ON COLUMN t_data_item_sn.item_sku_id IS '商品sku ID';
COMMENT ON COLUMN t_data_item_sn.code IS '编码';
COMMENT ON COLUMN t_data_item_sn.name IS '名称';
COMMENT ON COLUMN t_data_item_sn.create_by IS '创建人';
COMMENT ON COLUMN t_data_item_sn.create_time IS '创建时间';
COMMENT ON COLUMN t_data_item_sn.update_by IS '修改人';
COMMENT ON COLUMN t_data_item_sn.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_item_category_property_relation;
CREATE TABLE t_data_item_category_property_relation
(
    id                          VARCHAR(32) NOT NULL,
    item_category_id            VARCHAR(32) NOT NULL,
    item_property_id            VARCHAR(32) NOT NULL,
    item_category_property_type VARCHAR(16) NOT NULL,
    item_property_alias         VARCHAR(32) NOT NULL,
    sort                        BIGINT NOT NULL DEFAULT 0,
    create_by                   VARCHAR(32) NOT NULL,
    create_time                 BIGINT NOT NULL,
    update_by                   VARCHAR(32) NOT NULL,
    update_time                 BIGINT NOT NULL,
    CONSTRAINT pk_t_data_item_category_property_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_data_item_category_property_relation_01 UNIQUE (item_category_id, item_property_id)
);

CREATE INDEX idx_t_data_item_category_property_relation_01 ON t_data_item_category_property_relation (update_time);

COMMENT ON TABLE t_data_item_category_property_relation IS '商品分类属性关系表';
COMMENT ON COLUMN t_data_item_category_property_relation.id IS 'ID';
COMMENT ON COLUMN t_data_item_category_property_relation.item_category_id IS '商品分类ID';
COMMENT ON COLUMN t_data_item_category_property_relation.item_property_id IS '商品属性ID';
COMMENT ON COLUMN t_data_item_category_property_relation.item_category_property_type IS '商品属性类型';
COMMENT ON COLUMN t_data_item_category_property_relation.item_property_alias IS '商品属性别名';
COMMENT ON COLUMN t_data_item_category_property_relation.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_data_item_category_property_relation.create_by IS '创建人';
COMMENT ON COLUMN t_data_item_category_property_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_data_item_category_property_relation.update_by IS '修改人';
COMMENT ON COLUMN t_data_item_category_property_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_item_category_property_value_relation;
CREATE TABLE t_data_item_category_property_value_relation
(
    id                                 VARCHAR(32) NOT NULL,
    item_category_property_relation_id VARCHAR(32) NOT NULL,
    item_property_value_id             VARCHAR(32) NOT NULL,
    item_property_value_alias          VARCHAR(32) NOT NULL,
    sort                               BIGINT NOT NULL DEFAULT 0,
    create_by                          VARCHAR(32) NOT NULL,
    create_time                        BIGINT NOT NULL,
    update_by                          VARCHAR(32) NOT NULL,
    update_time                        BIGINT NOT NULL,
    CONSTRAINT pk_t_data_item_category_property_value_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_data_item_category_property_value_relation_01 UNIQUE (item_category_property_relation_id, item_property_value_id)
);

CREATE INDEX idx_t_data_item_category_property_value_relation_01 ON t_data_item_category_property_value_relation (update_time);

COMMENT ON TABLE t_data_item_category_property_value_relation IS '商品分类属性与属性值关系表';
COMMENT ON COLUMN t_data_item_category_property_value_relation.id IS 'ID';
COMMENT ON COLUMN t_data_item_category_property_value_relation.item_category_property_relation_id IS '商品分类属性关系ID';
COMMENT ON COLUMN t_data_item_category_property_value_relation.item_property_value_id IS '商品属性值ID';
COMMENT ON COLUMN t_data_item_category_property_value_relation.item_property_value_alias IS '商品属性值别名';
COMMENT ON COLUMN t_data_item_category_property_value_relation.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_data_item_category_property_value_relation.create_by IS '创建人';
COMMENT ON COLUMN t_data_item_category_property_value_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_data_item_category_property_value_relation.update_by IS '修改人';
COMMENT ON COLUMN t_data_item_category_property_value_relation.update_time IS '更新时间';