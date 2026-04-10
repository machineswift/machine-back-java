DROP TABLE IF EXISTS t_scm_product_brand;
CREATE TABLE t_scm_product_brand
(
    id                 VARCHAR(32)   NOT NULL,
    code               VARCHAR(16)   NOT NULL,
    name               VARCHAR(64)   NOT NULL DEFAULT '',
    status             VARCHAR(8)    NOT NULL DEFAULT 'DISABLE',
    logo_attachment_id VARCHAR(32)   NOT NULL DEFAULT '',
    description        VARCHAR(2048) NOT NULL DEFAULT '',
    create_by          VARCHAR(32)   NOT NULL,
    create_time        BIGINT        NOT NULL,
    update_by          VARCHAR(32)   NOT NULL,
    update_time        BIGINT        NOT NULL,
    CONSTRAINT pk_t_scm_product_brand PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_product_brand_01 UNIQUE (code)
);

CREATE INDEX idx_t_data_brand_01 ON t_data_brand (create_time);

COMMENT ON TABLE t_scm_product_brand IS '商品品牌表';
COMMENT ON COLUMN t_scm_product_brand.id IS 'ID';
COMMENT ON COLUMN t_scm_product_brand.code IS '编码';
COMMENT ON COLUMN t_scm_product_brand.name IS '名称';
COMMENT ON COLUMN t_scm_product_brand.status IS '状态';
COMMENT ON COLUMN t_scm_product_brand.logo_attachment_id IS 'logo附件ID';
COMMENT ON COLUMN t_scm_product_brand.description IS '描述';
COMMENT ON COLUMN t_scm_product_brand.create_by IS '创建人';
COMMENT ON COLUMN t_scm_product_brand.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_product_brand.update_by IS '修改人';
COMMENT ON COLUMN t_scm_product_brand.update_time IS '更新时间';

DROP TABLE IF EXISTS t_scm_product_series;
CREATE TABLE t_scm_product_series
(
    id          VARCHAR(32)   NOT NULL,
    brand_id    VARCHAR(32)   NOT NULL,
    code        VARCHAR(16)   NOT NULL,
    name        VARCHAR(64)   NOT NULL DEFAULT '',
    status      VARCHAR(8)    NOT NULL DEFAULT 'DISABLE',
    sort        BIGINT        NOT NULL DEFAULT 0,
    description VARCHAR(2048) NOT NULL DEFAULT '',
    create_by   VARCHAR(32)   NOT NULL,
    create_time BIGINT        NOT NULL,
    update_by   VARCHAR(32)   NOT NULL,
    update_time BIGINT        NOT NULL,
    CONSTRAINT pk_t_scm_product_series PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_product_series_01 UNIQUE (code)
);

ALTER TABLE t_scm_product_series
    ADD CONSTRAINT fk_t_scm_product_series_01
        FOREIGN KEY (brand_id) REFERENCES t_scm_back_category(id) ON DELETE CASCADE;

CREATE INDEX idx_t_scm_product_series_01 ON t_scm_product_brand (brand_id);

COMMENT ON TABLE t_scm_product_series IS '商品品牌系列表';
COMMENT ON COLUMN t_scm_product_series.id IS 'ID';
COMMENT ON COLUMN t_scm_product_series.brand_id IS '商品品牌ID';
COMMENT ON COLUMN t_scm_product_series.code IS '编码';
COMMENT ON COLUMN t_scm_product_series.name IS '名称';
COMMENT ON COLUMN t_scm_product_series.status IS '状态';
COMMENT ON COLUMN t_scm_product_series.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_product_series.description IS '描述';
COMMENT ON COLUMN t_scm_product_series.create_by IS '创建人';
COMMENT ON COLUMN t_scm_product_series.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_product_series.update_by IS '修改人';
COMMENT ON COLUMN t_scm_product_series.update_time IS '更新时间';


DROP TABLE IF EXISTS t_scm_spu;
CREATE TABLE t_scm_spu
(
    id          VARCHAR(32) NOT NULL,
    category_id VARCHAR(32) NOT NULL,
    brand_id    VARCHAR(32) NOT NULL,
    series_id   VARCHAR(32) NOT NULL,
    code        VARCHAR(32) NOT NULL,
    name        VARCHAR(32) NOT NULL,
    status      VARCHAR(8) NOT NULL DEFAULT 'DISABLE',
    sort        BIGINT NOT NULL DEFAULT 0,
    features    TEXT,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_spu PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_spu_01 UNIQUE (code),
    CONSTRAINT uk_t_scm_spu_02 UNIQUE (name)
);

ALTER TABLE t_scm_spu
    ADD CONSTRAINT fk_t_scm_spu_01
        FOREIGN KEY (category_id) REFERENCES t_scm_back_category(id) ON DELETE CASCADE;
ALTER TABLE t_scm_spu
    ADD CONSTRAINT fk_t_scm_spu_02
        FOREIGN KEY (series_id) REFERENCES t_scm_product_series(id) ON DELETE CASCADE;

CREATE INDEX idx_t_scm_spu_01 ON t_scm_spu (category_id,brand_id);
CREATE INDEX idx_t_scm_spu_02 ON t_scm_spu (brand_id,series_id);
CREATE INDEX idx_t_scm_spu_03 ON t_scm_spu (series_id);

COMMENT ON TABLE t_scm_spu IS 'spu表';
COMMENT ON COLUMN t_scm_spu.id IS 'ID';
COMMENT ON COLUMN t_scm_spu.category_id IS '后台分类ID';
COMMENT ON COLUMN t_scm_spu.brand_id IS '品牌ID';
COMMENT ON COLUMN t_scm_spu.series_id IS '系列ID';
COMMENT ON COLUMN t_scm_spu.code IS '编码';
COMMENT ON COLUMN t_scm_spu.name IS '名称';
COMMENT ON COLUMN t_scm_spu.status IS '状态';
COMMENT ON COLUMN t_scm_spu.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_spu.features IS '扩展特性JSON';
COMMENT ON COLUMN t_scm_spu.create_by IS '创建人';
COMMENT ON COLUMN t_scm_spu.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_spu.update_by IS '修改人';
COMMENT ON COLUMN t_scm_spu.update_time IS '更新时间';

DROP TABLE IF EXISTS t_scm_spu_property_value;
CREATE TABLE t_scm_spu_property_value
(
    id                VARCHAR(32) NOT NULL,
    spu_id            VARCHAR(32) NOT NULL,
    property_id       VARCHAR(32) NOT NULL,
    property_value_id VARCHAR(32) NOT NULL,
    sort              BIGINT NOT NULL DEFAULT 0,
    create_by         VARCHAR(32) NOT NULL,
    create_time       BIGINT NOT NULL,
    update_by         VARCHAR(32) NOT NULL,
    update_time       BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_spu_property_value PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_spu_property_value_01 UNIQUE (spu_id, property_id, property_value_id)
);

ALTER TABLE t_scm_spu_property_value
    ADD CONSTRAINT fk_t_scm_spu_property_value_01
        FOREIGN KEY (spu_id) REFERENCES t_scm_spu(id) ON DELETE CASCADE;
ALTER TABLE t_scm_spu_property_value
    ADD CONSTRAINT fk_t_scm_spu_property_value_02
        FOREIGN KEY (property_id) REFERENCES t_scm_property(id) ON DELETE CASCADE;
ALTER TABLE t_scm_spu_property_value
    ADD CONSTRAINT fk_t_scm_spu_property_value_03
        FOREIGN KEY (property_value_id) REFERENCES t_scm_property_value(id) ON DELETE CASCADE;

COMMENT ON TABLE t_scm_spu_property_value IS 'spu属性值表';
COMMENT ON COLUMN t_scm_spu_property_value.id IS 'ID';
COMMENT ON COLUMN t_scm_spu_property_value.spu_id IS 'SPU ID';
COMMENT ON COLUMN t_scm_spu_property_value.property_id IS '属性ID';
COMMENT ON COLUMN t_scm_spu_property_value.property_value_id IS '属性值ID';
COMMENT ON COLUMN t_scm_spu_property_value.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_spu_property_value.create_by IS '创建人';
COMMENT ON COLUMN t_scm_spu_property_value.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_spu_property_value.update_by IS '修改人';
COMMENT ON COLUMN t_scm_spu_property_value.update_time IS '更新时间';

DROP TABLE IF EXISTS t_scm_spu_media;
CREATE TABLE t_scm_spu_media
(
    id            VARCHAR(32) NOT NULL,
    spu_id        VARCHAR(32) NOT NULL,
    attachment_id VARCHAR(32) NOT NULL,
    status        VARCHAR(8) NOT NULL DEFAULT 'DISABLE',
    sort          BIGINT NOT NULL DEFAULT 0,
    create_by     VARCHAR(32) NOT NULL,
    create_time   BIGINT NOT NULL,
    update_by     VARCHAR(32) NOT NULL,
    update_time   BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_spu_media PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_spu_media_01 UNIQUE (spu_id, attachment_id)
);

ALTER TABLE t_scm_spu_media
    ADD CONSTRAINT fk_t_scm_spu_media_01
        FOREIGN KEY (spu_id) REFERENCES t_scm_spu(id) ON DELETE CASCADE;

CREATE INDEX idx_t_scm_spu_media_01 ON t_scm_spu_media (attachment_id);

COMMENT ON TABLE t_scm_spu_media IS 'spu图片表';
COMMENT ON COLUMN t_scm_spu_media.id IS 'ID';
COMMENT ON COLUMN t_scm_spu_media.spu_id IS 'SPU ID';
COMMENT ON COLUMN t_scm_spu_media.attachment_id IS '附件ID';
COMMENT ON COLUMN t_scm_spu_media.status IS '状态';
COMMENT ON COLUMN t_scm_spu_media.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_spu_media.create_by IS '创建人';
COMMENT ON COLUMN t_scm_spu_media.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_spu_media.update_by IS '修改人';
COMMENT ON COLUMN t_scm_spu_media.update_time IS '更新时间';

DROP TABLE IF EXISTS t_scm_product;
CREATE TABLE t_scm_product
(
    id          VARCHAR(32) NOT NULL,
    spu_id      VARCHAR(32) NOT NULL,
    merchant_id VARCHAR(32) NOT NULL,
    code        VARCHAR(32) NOT NULL,
    name        VARCHAR(32) NOT NULL,
    status      VARCHAR(8) NOT NULL DEFAULT 'DISABLE',
    sort        BIGINT NOT NULL DEFAULT 0,
    features    TEXT,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_product PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_product_01 UNIQUE (spu_id, merchant_id),
    CONSTRAINT uk_t_scm_product_02 UNIQUE (code)
);

ALTER TABLE t_scm_product
    ADD CONSTRAINT fk_t_scm_product_01
        FOREIGN KEY (spu_id) REFERENCES t_scm_spu(id) ON DELETE CASCADE;

CREATE INDEX idx_t_scm_product_01 ON t_scm_product (merchant_id);

COMMENT ON TABLE t_scm_product IS '商品表';
COMMENT ON COLUMN t_scm_product.id IS 'ID';
COMMENT ON COLUMN t_scm_product.spu_id IS 'SPU ID';
COMMENT ON COLUMN t_scm_product.merchant_id IS '商家ID';
COMMENT ON COLUMN t_scm_product.name IS '名称';
COMMENT ON COLUMN t_scm_product.code IS '商品编码';
COMMENT ON COLUMN t_scm_product.status IS '状态';
COMMENT ON COLUMN t_scm_product.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_product.features IS '扩展特性JSON';
COMMENT ON COLUMN t_scm_product.create_by IS '创建人';
COMMENT ON COLUMN t_scm_product.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_product.update_by IS '修改人';
COMMENT ON COLUMN t_scm_product.update_time IS '更新时间';

DROP TABLE IF EXISTS t_scm_product_property_value;
CREATE TABLE t_scm_product_property_value
(
    id                VARCHAR(32) NOT NULL,
    product_id        VARCHAR(32) NOT NULL,
    property_id       VARCHAR(32) NOT NULL,
    property_value_id VARCHAR(32) NOT NULL,
    sort              BIGINT NOT NULL DEFAULT 0,
    create_by         VARCHAR(32) NOT NULL,
    create_time       BIGINT NOT NULL,
    update_by         VARCHAR(32) NOT NULL,
    update_time       BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_product_property_value PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_product_property_value_01 UNIQUE (product_id, property_id, property_value_id)
);

ALTER TABLE t_scm_product_property_value
    ADD CONSTRAINT fk_t_scm_product_property_value_01
        FOREIGN KEY (product_id) REFERENCES t_scm_product(id) ON DELETE CASCADE;
ALTER TABLE t_scm_product_property_value
    ADD CONSTRAINT fk_t_scm_product_property_value_02
        FOREIGN KEY (property_id) REFERENCES t_scm_property(id) ON DELETE CASCADE;
ALTER TABLE t_scm_product_property_value
    ADD CONSTRAINT fk_t_scm_product_property_value_03
        FOREIGN KEY (property_value_id) REFERENCES t_scm_property_value(id) ON DELETE CASCADE;

COMMENT ON TABLE t_scm_product_property_value IS '商品属性值表';
COMMENT ON COLUMN t_scm_product_property_value.id IS 'ID';
COMMENT ON COLUMN t_scm_product_property_value.product_id IS '商品ID';
COMMENT ON COLUMN t_scm_product_property_value.property_id IS '属性ID';
COMMENT ON COLUMN t_scm_product_property_value.property_value_id IS '属性值ID';
COMMENT ON COLUMN t_scm_product_property_value.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_product_property_value.create_by IS '创建人';
COMMENT ON COLUMN t_scm_product_property_value.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_product_property_value.update_by IS '修改人';
COMMENT ON COLUMN t_scm_product_property_value.update_time IS '更新时间';

DROP TABLE IF EXISTS t_scm_product_media;
CREATE TABLE t_scm_product_media
(
    id            VARCHAR(32) NOT NULL,
    product_id    VARCHAR(32) NOT NULL,
    attachment_id VARCHAR(32) NOT NULL,
    status        VARCHAR(8) NOT NULL DEFAULT 'DISABLE',
    sort          BIGINT NOT NULL DEFAULT 0,
    create_by     VARCHAR(32) NOT NULL,
    create_time   BIGINT NOT NULL,
    update_by     VARCHAR(32) NOT NULL,
    update_time   BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_product_media PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_product_media_01 UNIQUE (product_id, attachment_id)
);

ALTER TABLE t_scm_product_media
    ADD CONSTRAINT fk_t_scm_product_media_01
        FOREIGN KEY (product_id) REFERENCES t_scm_product(id) ON DELETE CASCADE;

CREATE INDEX idx_t_scm_product_media_01 ON t_scm_product_media (attachment_id);

COMMENT ON TABLE t_scm_product_media IS '商品图片表';
COMMENT ON COLUMN t_scm_product_media.id IS 'ID';
COMMENT ON COLUMN t_scm_product_media.product_id IS '商品ID';
COMMENT ON COLUMN t_scm_product_media.attachment_id IS '附件ID';
COMMENT ON COLUMN t_scm_product_media.status IS '状态';
COMMENT ON COLUMN t_scm_product_media.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_product_media.create_by IS '创建人';
COMMENT ON COLUMN t_scm_product_media.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_product_media.update_by IS '修改人';
COMMENT ON COLUMN t_scm_product_media.update_time IS '更新时间';

DROP TABLE IF EXISTS t_scm_sku;
CREATE TABLE t_scm_sku
(
    id           VARCHAR(32) NOT NULL,
    product_id   VARCHAR(32) NOT NULL,
    code         VARCHAR(32) NOT NULL,
    name         VARCHAR(32) NOT NULL,
    status       VARCHAR(8) NOT NULL DEFAULT 'DISABLE',
    sale_price   DECIMAL(10, 2) NOT NULL,
    market_price DECIMAL(10, 2),
    sort         BIGINT NOT NULL DEFAULT 0,
    features     TEXT,
    create_by    VARCHAR(32) NOT NULL,
    create_time  BIGINT NOT NULL,
    update_by    VARCHAR(32) NOT NULL,
    update_time  BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_sku PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_sku_01 UNIQUE (code)
);

ALTER TABLE t_scm_sku
    ADD CONSTRAINT fk_t_scm_sku_01
        FOREIGN KEY (product_id) REFERENCES t_scm_product(id) ON DELETE CASCADE;

CREATE INDEX idx_t_scm_sku_01 ON t_scm_sku (product_id);

COMMENT ON TABLE t_scm_sku IS 'SKU表';
COMMENT ON COLUMN t_scm_sku.id IS 'ID';
COMMENT ON COLUMN t_scm_sku.product_id IS '商品ID';
COMMENT ON COLUMN t_scm_sku.name IS '名称';
COMMENT ON COLUMN t_scm_sku.code IS '商品编码';
COMMENT ON COLUMN t_scm_sku.status IS '状态';
COMMENT ON COLUMN t_scm_sku.sale_price IS '销售价格';
COMMENT ON COLUMN t_scm_sku.market_price IS '市场价格';
COMMENT ON COLUMN t_scm_sku.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_sku.features IS '扩展特性JSON';
COMMENT ON COLUMN t_scm_sku.create_by IS '创建人';
COMMENT ON COLUMN t_scm_sku.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_sku.update_by IS '修改人';
COMMENT ON COLUMN t_scm_sku.update_time IS '更新时间';

DROP TABLE IF EXISTS t_scm_sku_property_value;
CREATE TABLE t_scm_sku_property_value
(
    id                VARCHAR(32) NOT NULL,
    sku_id            VARCHAR(32) NOT NULL,
    property_id       VARCHAR(32) NOT NULL,
    property_value_id VARCHAR(32) NOT NULL,
    sort              BIGINT NOT NULL DEFAULT 0,
    create_by         VARCHAR(32) NOT NULL,
    create_time       BIGINT NOT NULL,
    update_by         VARCHAR(32) NOT NULL,
    update_time       BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_sku_property_value PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_sku_property_value_01 UNIQUE (sku_id, property_id, property_value_id)
);

ALTER TABLE t_scm_sku_property_value
    ADD CONSTRAINT fk_t_scm_sku_property_value_01
        FOREIGN KEY (sku_id) REFERENCES t_scm_sku(id) ON DELETE CASCADE;
ALTER TABLE t_scm_sku_property_value
    ADD CONSTRAINT fk_t_scm_sku_property_value_02
        FOREIGN KEY (property_id) REFERENCES t_scm_property(id) ON DELETE CASCADE;
ALTER TABLE t_scm_sku_property_value
    ADD CONSTRAINT fk_t_scm_sku_property_value_03
        FOREIGN KEY (property_value_id) REFERENCES t_scm_property_value(id) ON DELETE CASCADE;

COMMENT ON TABLE t_scm_sku_property_value IS 'sku属性值表';
COMMENT ON COLUMN t_scm_sku_property_value.id IS 'ID';
COMMENT ON COLUMN t_scm_sku_property_value.sku_id IS 'SKU ID';
COMMENT ON COLUMN t_scm_sku_property_value.property_id IS '属性ID';
COMMENT ON COLUMN t_scm_sku_property_value.property_value_id IS '属性值ID';
COMMENT ON COLUMN t_scm_sku_property_value.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_sku_property_value.create_by IS '创建人';
COMMENT ON COLUMN t_scm_sku_property_value.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_sku_property_value.update_by IS '修改人';
COMMENT ON COLUMN t_scm_sku_property_value.update_time IS '更新时间';

DROP TABLE IF EXISTS t_scm_sku_media;
CREATE TABLE t_scm_sku_media
(
    id            VARCHAR(32) NOT NULL,
    sku_id        VARCHAR(32) NOT NULL,
    attachment_id VARCHAR(32) NOT NULL,
    status        VARCHAR(8) NOT NULL DEFAULT 'DISABLE',
    sort          BIGINT NOT NULL DEFAULT 0,
    create_by     VARCHAR(32) NOT NULL,
    create_time   BIGINT NOT NULL,
    update_by     VARCHAR(32) NOT NULL,
    update_time   BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_sku_media PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_sku_media_01 UNIQUE (sku_id, attachment_id)
);

ALTER TABLE t_scm_sku_media
    ADD CONSTRAINT fk_t_scm_sku_media_01
        FOREIGN KEY (sku_id) REFERENCES t_scm_sku(id) ON DELETE CASCADE;

CREATE INDEX idx_t_scm_sku_media_01 ON t_scm_sku_media (attachment_id);

COMMENT ON TABLE t_scm_sku_media IS 'sku图片表';
COMMENT ON COLUMN t_scm_sku_media.id IS 'ID';
COMMENT ON COLUMN t_scm_sku_media.sku_id IS 'SKU ID';
COMMENT ON COLUMN t_scm_sku_media.attachment_id IS '附件ID';
COMMENT ON COLUMN t_scm_sku_media.status IS '状态';
COMMENT ON COLUMN t_scm_sku_media.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_sku_media.create_by IS '创建人';
COMMENT ON COLUMN t_scm_sku_media.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_sku_media.update_by IS '修改人';
COMMENT ON COLUMN t_scm_sku_media.update_time IS '更新时间';

DROP TABLE IF EXISTS t_scm_sku_sn;
CREATE TABLE t_scm_sku_sn
(
    id          VARCHAR(32) NOT NULL,
    sku_id      VARCHAR(32) NOT NULL,
    code        VARCHAR(32) NOT NULL,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_sku_sn PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_sku_sn_01 UNIQUE (code)
);

ALTER TABLE t_scm_sku_sn
    ADD CONSTRAINT fk_t_scm_sku_sn_01
        FOREIGN KEY (sku_id) REFERENCES t_scm_sku(id) ON DELETE CASCADE;

CREATE INDEX idx_t_scm_sku_sn_01 ON t_scm_sku_sn (sku_id);

COMMENT ON TABLE t_scm_sku_sn IS '序列号表';
COMMENT ON COLUMN t_scm_sku_sn.id IS 'ID';
COMMENT ON COLUMN t_scm_sku_sn.sku_id IS 'SKU ID';
COMMENT ON COLUMN t_scm_sku_sn.code IS '编码';
COMMENT ON COLUMN t_scm_sku_sn.create_by IS '创建人';
COMMENT ON COLUMN t_scm_sku_sn.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_sku_sn.update_by IS '修改人';
COMMENT ON COLUMN t_scm_sku_sn.update_time IS '更新时间';