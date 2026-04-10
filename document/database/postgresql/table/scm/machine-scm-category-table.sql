DROP TABLE IF EXISTS t_scm_back_category;
CREATE TABLE t_scm_back_category
(
    id          VARCHAR(32) NOT NULL,
    parent_id   VARCHAR(32) NOT NULL,
    code        VARCHAR(32) NOT NULL,
    name        VARCHAR(32) NOT NULL,
    status      VARCHAR(8) NOT NULL DEFAULT 'DISABLE',
    sort        BIGINT NOT NULL DEFAULT 0,
    features    TEXT,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_back_category PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_back_category_01 UNIQUE (code),
    CONSTRAINT uk_t_scm_back_category_02 UNIQUE (parent_id, name)
);

COMMENT ON TABLE t_scm_back_category IS '商品后台分类表';
COMMENT ON COLUMN t_scm_back_category.id IS 'ID';
COMMENT ON COLUMN t_scm_back_category.parent_id IS '父ID';
COMMENT ON COLUMN t_scm_back_category.code IS '编码';
COMMENT ON COLUMN t_scm_back_category.name IS '名称';
COMMENT ON COLUMN t_scm_back_category.status IS '状态';
COMMENT ON COLUMN t_scm_back_category.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_back_category.features IS '扩展特性JSON';
COMMENT ON COLUMN t_scm_back_category.create_by IS '创建人';
COMMENT ON COLUMN t_scm_back_category.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_back_category.update_by IS '修改人';
COMMENT ON COLUMN t_scm_back_category.update_time IS '更新时间';

DROP TABLE IF EXISTS t_scm_front_category;
CREATE TABLE t_scm_front_category
(
    id          VARCHAR(32) NOT NULL,
    parent_id   VARCHAR(32) NOT NULL,
    code        VARCHAR(32) NOT NULL,
    name        VARCHAR(32) NOT NULL,
    status      VARCHAR(8) NOT NULL DEFAULT 'DISABLE',
    sort        BIGINT NOT NULL DEFAULT 0,
    features    TEXT,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_front_category PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_front_category_01 UNIQUE (code),
    CONSTRAINT uk_t_scm_front_category_02 UNIQUE (parent_id, name)
);

COMMENT ON TABLE t_scm_front_category IS '商品前台分类表';
COMMENT ON COLUMN t_scm_front_category.id IS 'ID';
COMMENT ON COLUMN t_scm_front_category.parent_id IS '父ID';
COMMENT ON COLUMN t_scm_front_category.code IS '编码';
COMMENT ON COLUMN t_scm_front_category.name IS '名称';
COMMENT ON COLUMN t_scm_front_category.status IS '状态';
COMMENT ON COLUMN t_scm_front_category.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_front_category.features IS '扩展特性JSON';
COMMENT ON COLUMN t_scm_front_category.create_by IS '创建人';
COMMENT ON COLUMN t_scm_front_category.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_front_category.update_by IS '修改人';
COMMENT ON COLUMN t_scm_front_category.update_time IS '更新时间';

DROP TABLE IF EXISTS t_scm_front_back_category_relation;
CREATE TABLE t_scm_front_back_category_relation
(
    id                VARCHAR(32) NOT NULL,
    front_category_id VARCHAR(32) NOT NULL,
    back_category_id VARCHAR(32) NOT NULL,
    sort              BIGINT NOT NULL DEFAULT 0,
    create_by         VARCHAR(32) NOT NULL,
    create_time       BIGINT NOT NULL,
    update_by         VARCHAR(32) NOT NULL,
    update_time       BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_front_back_category_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_front_back_category_relation_01 UNIQUE (front_category_id, back_category_id)
);

ALTER TABLE t_scm_front_back_category_relation
    ADD CONSTRAINT fk_t_scm_front_back_category_relation_01
        FOREIGN KEY (front_category_id) REFERENCES t_scm_front_category(id) ON DELETE CASCADE;

ALTER TABLE t_scm_front_back_category_relation
    ADD CONSTRAINT fk_t_scm_front_back_category_relation_02
        FOREIGN KEY (back_category_id) REFERENCES t_scm_back_category(id) ON DELETE CASCADE;

CREATE INDEX idx_t_scm_front_back_category_relation_01 ON t_scm_front_back_category_relation (back_category_id);

COMMENT ON TABLE t_scm_front_back_category_relation IS '前台类目与后台叶子类目映射表';
COMMENT ON COLUMN t_scm_front_back_category_relation.id IS 'ID';
COMMENT ON COLUMN t_scm_front_back_category_relation.front_category_id IS '前台类目ID';
COMMENT ON COLUMN t_scm_front_back_category_relation.back_category_id IS '后台类目ID';
COMMENT ON COLUMN t_scm_front_back_category_relation.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_front_back_category_relation.create_by IS '创建人';
COMMENT ON COLUMN t_scm_front_back_category_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_front_back_category_relation.update_by IS '修改人';
COMMENT ON COLUMN t_scm_front_back_category_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_scm_property;
CREATE TABLE t_scm_property
(
    id            VARCHAR(32) NOT NULL,
    code          VARCHAR(32) NOT NULL,
    name          VARCHAR(32) NOT NULL,
    property_type VARCHAR(32) NOT NULL,
    input_type    VARCHAR(32) NOT NULL,
    is_required   BOOLEAN NOT NULL DEFAULT false,
    is_multiple   BOOLEAN NOT NULL DEFAULT false,
    is_search     BOOLEAN NOT NULL DEFAULT false,
    sort               BIGINT NOT NULL DEFAULT 0,
    features           TEXT,
    create_by          VARCHAR(32) NOT NULL,
    create_time        BIGINT NOT NULL,
    update_by          VARCHAR(32) NOT NULL,
    update_time        BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_property PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_property_01 UNIQUE (code),
    CONSTRAINT uk_t_scm_property_02 UNIQUE (name)
);

COMMENT ON TABLE t_scm_property IS '商品属性表';
COMMENT ON COLUMN t_scm_property.id IS 'ID';
COMMENT ON COLUMN t_scm_property.code IS '编码';
COMMENT ON COLUMN t_scm_property.name IS '名称';
COMMENT ON COLUMN t_scm_property.property_type IS '属性类型';
COMMENT ON COLUMN t_scm_property.input_type IS '输入方式';
COMMENT ON COLUMN t_scm_property.is_required IS '是否必需';
COMMENT ON COLUMN t_scm_property.is_multiple IS '是否多选';
COMMENT ON COLUMN t_scm_property.is_search IS '是否可搜索';
COMMENT ON COLUMN t_scm_property.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_property.features IS '扩展特性JSON';
COMMENT ON COLUMN t_scm_property.create_by IS '创建人';
COMMENT ON COLUMN t_scm_property.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_property.update_by IS '修改人';
COMMENT ON COLUMN t_scm_property.update_time IS '更新时间';

DROP TABLE IF EXISTS t_scm_property_value;
CREATE TABLE t_scm_property_value
(
    id          VARCHAR(32) NOT NULL,
    property_id VARCHAR(32) NOT NULL,
    value       VARCHAR(64) NOT NULL,
    sort        BIGINT NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_property_value PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_property_value_01 UNIQUE (property_id, value)
);

COMMENT ON TABLE t_scm_property_value IS '商品属性值表';
COMMENT ON COLUMN t_scm_property_value.id IS 'ID';
COMMENT ON COLUMN t_scm_property_value.property_id IS '商品属性ID';
COMMENT ON COLUMN t_scm_property_value.value IS '值';
COMMENT ON COLUMN t_scm_property_value.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_property_value.create_by IS '创建人';
COMMENT ON COLUMN t_scm_property_value.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_property_value.update_by IS '修改人';
COMMENT ON COLUMN t_scm_property_value.update_time IS '更新时间';

DROP TABLE IF EXISTS t_scm_property_value_relation;
CREATE TABLE t_scm_property_value_relation
(
    id                   VARCHAR(32) NOT NULL,
    parent_value_id      VARCHAR(32) NOT NULL,
    child_property_id    VARCHAR(32) NOT NULL,
    sort                 BIGINT NOT NULL DEFAULT 0,
    create_by            VARCHAR(32) NOT NULL,
    create_time          BIGINT NOT NULL,
    update_by            VARCHAR(32) NOT NULL,
    update_time          BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_property_value_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_property_value_relation_01 UNIQUE (parent_value_id, child_property_id)
);

ALTER TABLE t_scm_property_value_relation
    ADD CONSTRAINT fk_t_scm_property_value_relation_01
        FOREIGN KEY (parent_value_id) REFERENCES t_scm_property_value(id) ON DELETE CASCADE;

ALTER TABLE t_scm_property_value_relation
    ADD CONSTRAINT fk_t_scm_property_value_relation_02
        FOREIGN KEY (child_property_id) REFERENCES t_scm_property(id) ON DELETE CASCADE;

CREATE INDEX idx_t_scm_property_value_relation_01 ON t_scm_property_value_relation (child_property_id);

COMMENT ON TABLE t_scm_property_value_relation IS '属性值驱动子属性关系表';
COMMENT ON COLUMN t_scm_property_value_relation.parent_value_id IS '属性值ID';
COMMENT ON COLUMN t_scm_property_value_relation.child_property_id IS '子属性ID';
COMMENT ON COLUMN t_scm_property_value_relation.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_property_value_relation.create_by IS '创建人';
COMMENT ON COLUMN t_scm_property_value_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_property_value_relation.update_by IS '修改人';
COMMENT ON COLUMN t_scm_property_value_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_scm_back_category_property_relation;
CREATE TABLE t_scm_back_category_property_relation
(
    id               VARCHAR(32) NOT NULL,
    back_category_id VARCHAR(32) NOT NULL,
    property_id      VARCHAR(32) NOT NULL,
    sort             BIGINT NOT NULL DEFAULT 0,
    create_by        VARCHAR(32) NOT NULL,
    create_time      BIGINT NOT NULL,
    update_by        VARCHAR(32) NOT NULL,
    update_time      BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_back_category_property_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_back_category_property_relation_01 UNIQUE (back_category_id, property_id)
);

ALTER TABLE t_scm_back_category_property_relation
    ADD CONSTRAINT fk_t_scm_back_category_property_relation_01
        FOREIGN KEY (back_category_id) REFERENCES t_scm_back_category(id) ON DELETE CASCADE;

ALTER TABLE t_scm_back_category_property_relation
    ADD CONSTRAINT fk_t_scm_back_category_property_relation_02
        FOREIGN KEY (property_id) REFERENCES t_scm_property(id) ON DELETE CASCADE;

CREATE INDEX idx_t_scm_back_category_property_relation_01 ON t_scm_back_category_property_relation (property_id);

COMMENT ON TABLE t_scm_back_category_property_relation IS '后台类目与属性绑定表';
COMMENT ON COLUMN t_scm_back_category_property_relation.id IS 'ID';
COMMENT ON COLUMN t_scm_back_category_property_relation.back_category_id IS '后台类目ID';
COMMENT ON COLUMN t_scm_back_category_property_relation.property_id IS '属性ID';
COMMENT ON COLUMN t_scm_back_category_property_relation.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_back_category_property_relation.create_by IS '创建人';
COMMENT ON COLUMN t_scm_back_category_property_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_back_category_property_relation.update_by IS '修改人';
COMMENT ON COLUMN t_scm_back_category_property_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_scm_property_group;
CREATE TABLE t_scm_property_group
(
    id               VARCHAR(32) NOT NULL,
    back_category_id VARCHAR(32) NOT NULL,
    name             VARCHAR(64) NOT NULL,
    sort             BIGINT NOT NULL DEFAULT 0,
    create_by        VARCHAR(32) NOT NULL,
    create_time      BIGINT NOT NULL,
    update_by        VARCHAR(32) NOT NULL,
    update_time      BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_property_group PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_property_group_01 UNIQUE (back_category_id, name)
);

ALTER TABLE t_scm_property_group
    ADD CONSTRAINT fk_t_scm_property_group_01
        FOREIGN KEY (back_category_id) REFERENCES t_scm_back_category(id) ON DELETE CASCADE;

COMMENT ON TABLE t_scm_property_group IS '商品属性组表';
COMMENT ON COLUMN t_scm_property_group.id IS 'ID';
COMMENT ON COLUMN t_scm_property_group.back_category_id IS '后台分类ID';
COMMENT ON COLUMN t_scm_property_group.name IS '名称';
COMMENT ON COLUMN t_scm_property_group.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_property_group.create_by IS '创建人';
COMMENT ON COLUMN t_scm_property_group.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_property_group.update_by IS '修改人';
COMMENT ON COLUMN t_scm_property_group.update_time IS '更新时间';

DROP TABLE IF EXISTS t_scm_property_group_relation;
CREATE TABLE t_scm_property_group_relation
(
    id               VARCHAR(32) NOT NULL,
    group_id         VARCHAR(32) NOT NULL,
    property_id      VARCHAR(32) NOT NULL,
    sort             BIGINT NOT NULL DEFAULT 0,
    create_by        VARCHAR(32) NOT NULL,
    create_time      BIGINT NOT NULL,
    update_by        VARCHAR(32) NOT NULL,
    update_time      BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_property_group_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_property_group_relation_01 UNIQUE (group_id, property_id)
);

ALTER TABLE t_scm_property_group_relation
    ADD CONSTRAINT fk_t_scm_property_group_relation_01
        FOREIGN KEY (group_id) REFERENCES t_scm_property_group(id) ON DELETE CASCADE;

ALTER TABLE t_scm_property_group_relation
    ADD CONSTRAINT fk_t_scm_property_group_relation_02
        FOREIGN KEY (property_id) REFERENCES t_scm_property(id) ON DELETE CASCADE;

CREATE INDEX idx_t_scm_property_group_relation_01 ON t_scm_property_group_relation (property_id);

COMMENT ON TABLE t_scm_property_group_relation IS '商品属性组与属性关系表';
COMMENT ON COLUMN t_scm_property_group_relation.id IS 'ID';
COMMENT ON COLUMN t_scm_property_group_relation.group_id IS '属性组ID';
COMMENT ON COLUMN t_scm_property_group_relation.property_id IS '属性ID';
COMMENT ON COLUMN t_scm_property_group_relation.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_property_group_relation.create_by IS '创建人';
COMMENT ON COLUMN t_scm_property_group_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_property_group_relation.update_by IS '修改人';
COMMENT ON COLUMN t_scm_property_group_relation.update_time IS '更新时间';