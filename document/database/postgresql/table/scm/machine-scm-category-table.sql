DROP TABLE IF EXISTS t_scm_back_category;
CREATE TABLE t_scm_back_category
(
    id          VARCHAR(32) NOT NULL,
    parent_id   VARCHAR(32) NOT NULL,
    code        VARCHAR(32) NOT NULL,
    name        VARCHAR(32) NOT NULL,
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
COMMENT ON COLUMN t_scm_front_category.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_front_category.features IS '扩展特性JSON';
COMMENT ON COLUMN t_scm_front_category.create_by IS '创建人';
COMMENT ON COLUMN t_scm_front_category.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_front_category.update_by IS '修改人';
COMMENT ON COLUMN t_scm_front_category.update_time IS '更新时间';

DROP TABLE IF EXISTS t_scm_attribute_group;
CREATE TABLE t_scm_attribute_group
(
    id          VARCHAR(32) NOT NULL,
    code        VARCHAR(32) NOT NULL,
    name        VARCHAR(64) NOT NULL,
    group_type  VARCHAR(32) NOT NULL DEFAULT 'NORMAL',
    status      VARCHAR(16) NOT NULL DEFAULT 'ENABLE',
    features    TEXT,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_attribute_group PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_attribute_group_01 UNIQUE (code)
);

CREATE INDEX idx_t_scm_attribute_group_01 ON t_scm_attribute_group (create_time);

COMMENT ON TABLE t_scm_attribute_group IS '属性组表';
COMMENT ON COLUMN t_scm_attribute_group.id IS 'ID';
COMMENT ON COLUMN t_scm_attribute_group.code IS '编码';
COMMENT ON COLUMN t_scm_attribute_group.name IS '名称';
COMMENT ON COLUMN t_scm_attribute_group.group_type IS '组类型';
COMMENT ON COLUMN t_scm_attribute_group.status IS '状态';
COMMENT ON COLUMN t_scm_attribute_group.features IS '扩展特性JSON';
COMMENT ON COLUMN t_scm_attribute_group.create_by IS '创建人';
COMMENT ON COLUMN t_scm_attribute_group.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_attribute_group.update_by IS '修改人';
COMMENT ON COLUMN t_scm_attribute_group.update_time IS '更新时间';


DROP TABLE IF EXISTS t_scm_attribute;
CREATE TABLE t_scm_attribute
(
    id              VARCHAR(32) NOT NULL,
    code            VARCHAR(32) NOT NULL,
    name            VARCHAR(32) NOT NULL,
    attribute_type  VARCHAR(32) NOT NULL,
    input_type      VARCHAR(32) NOT NULL,
    required_flag   BOOLEAN NOT NULL DEFAULT false,
    multiple_flag   BOOLEAN NOT NULL DEFAULT false,
    searchable_flag BOOLEAN NOT NULL DEFAULT false,
    features        TEXT,
    create_by       VARCHAR(32) NOT NULL,
    create_time     BIGINT NOT NULL,
    update_by       VARCHAR(32) NOT NULL,
    update_time     BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_attribute PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_attribute_01 UNIQUE (code)
);

CREATE INDEX idx_t_scm_attribute_01 ON t_scm_attribute (create_time);

COMMENT ON TABLE t_scm_attribute IS '商品属性表';
COMMENT ON COLUMN t_scm_attribute.id IS 'ID';
COMMENT ON COLUMN t_scm_attribute.code IS '编码';
COMMENT ON COLUMN t_scm_attribute.name IS '名称';
COMMENT ON COLUMN t_scm_attribute.attribute_type IS '属性类型';
COMMENT ON COLUMN t_scm_attribute.input_type IS '输入方式';
COMMENT ON COLUMN t_scm_attribute.required_flag IS '是否必需';
COMMENT ON COLUMN t_scm_attribute.multiple_flag IS '是否多选';
COMMENT ON COLUMN t_scm_attribute.searchable_flag IS '是否可搜索';
COMMENT ON COLUMN t_scm_attribute.features IS '扩展特性JSON';
COMMENT ON COLUMN t_scm_attribute.create_by IS '创建人';
COMMENT ON COLUMN t_scm_attribute.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_attribute.update_by IS '修改人';
COMMENT ON COLUMN t_scm_attribute.update_time IS '更新时间';


DROP TABLE IF EXISTS t_scm_attribute_value;
CREATE TABLE t_scm_attribute_value
(
    id           VARCHAR(32)  NOT NULL,
    attribute_id VARCHAR(32)  NOT NULL,

    code         VARCHAR(32)  NOT NULL,
    name         VARCHAR(32)  NOT NULL,
    value        VARCHAR(256),

    sort         BIGINT       NOT NULL DEFAULT 0,
    status       VARCHAR(16)  NOT NULL DEFAULT 'ENABLE',

    features     TEXT,

    create_by    VARCHAR(32)  NOT NULL,
    create_time  BIGINT       NOT NULL,
    update_by    VARCHAR(32)  NOT NULL,
    update_time  BIGINT       NOT NULL,

    CONSTRAINT pk_t_scm_attribute_value PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_attribute_value_01 UNIQUE (attribute_id, code)
);

COMMENT ON TABLE t_scm_attribute_value IS '商品属性值表';

COMMENT ON COLUMN t_scm_attribute_value.id IS 'ID';
COMMENT ON COLUMN t_scm_attribute_value.attribute_id IS '属性ID';
COMMENT ON COLUMN t_scm_attribute_value.code IS '属性值编码';
COMMENT ON COLUMN t_scm_attribute_value.name IS '属性值名称';
COMMENT ON COLUMN t_scm_attribute_value.value IS '属性值实际值';
COMMENT ON COLUMN t_scm_attribute_value.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_attribute_value.status IS '状态：ENABLE启用，DISABLE禁用';
COMMENT ON COLUMN t_scm_attribute_value.features IS '扩展特性JSON';
COMMENT ON COLUMN t_scm_attribute_value.create_by IS '创建人';
COMMENT ON COLUMN t_scm_attribute_value.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_attribute_value.update_by IS '修改人';
COMMENT ON COLUMN t_scm_attribute_value.update_time IS '更新时间';


DROP TABLE IF EXISTS t_scm_attribute_value_relation;
CREATE TABLE t_scm_attribute_value_relation
(
    id                 VARCHAR(32) NOT NULL,
    parent_value_id    VARCHAR(32) NOT NULL,
    child_attribute_id VARCHAR(32) NOT NULL,
    sort               BIGINT NOT NULL DEFAULT 0,
    create_by          VARCHAR(32) NOT NULL,
    create_time        BIGINT NOT NULL,
    update_by          VARCHAR(32) NOT NULL,
    update_time        BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_attribute_value_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_attribute_value_relation_01 UNIQUE (parent_value_id, child_attribute_id)
);

ALTER TABLE t_scm_attribute_value_relation
    ADD CONSTRAINT fk_t_scm_attribute_value_relation_01
        FOREIGN KEY (parent_value_id) REFERENCES t_scm_attribute_value(id) ON DELETE RESTRICT;

ALTER TABLE t_scm_attribute_value_relation
    ADD CONSTRAINT fk_t_scm_attribute_value_relation_02
        FOREIGN KEY (child_attribute_id) REFERENCES t_scm_attribute(id) ON DELETE RESTRICT;

CREATE INDEX idx_t_scm_attribute_value_relation_01 ON t_scm_attribute_value_relation (child_attribute_id);

COMMENT ON TABLE t_scm_attribute_value_relation IS '属性值驱动子属性关系表';
COMMENT ON COLUMN t_scm_attribute_value_relation.parent_value_id IS '属性值ID';
COMMENT ON COLUMN t_scm_attribute_value_relation.child_attribute_id IS '子属性ID';
COMMENT ON COLUMN t_scm_attribute_value_relation.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_attribute_value_relation.create_by IS '创建人';
COMMENT ON COLUMN t_scm_attribute_value_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_attribute_value_relation.update_by IS '修改人';
COMMENT ON COLUMN t_scm_attribute_value_relation.update_time IS '更新时间';


DROP TABLE IF EXISTS t_scm_attribute_group_attribute_relation;
CREATE TABLE t_scm_attribute_group_attribute_relation
(
    id               VARCHAR(32) NOT NULL,
    group_id         VARCHAR(32) NOT NULL,
    attribute_id     VARCHAR(32) NOT NULL,
    required_flag    BOOLEAN NOT NULL DEFAULT false,
    display_flag     BOOLEAN NOT NULL DEFAULT true,
    searchable_flag  BOOLEAN NOT NULL DEFAULT false,
    sort             BIGINT NOT NULL DEFAULT 0,
    features         TEXT,
    create_by        VARCHAR(32) NOT NULL,
    create_time      BIGINT NOT NULL,
    update_by        VARCHAR(32) NOT NULL,
    update_time      BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_attribute_group_attribute_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_attribute_group_attribute_relation_01 UNIQUE (group_id, attribute_id)
);

ALTER TABLE t_scm_attribute_group_attribute_relation
    ADD CONSTRAINT fk_t_scm_attribute_group_attribute_relation_01
        FOREIGN KEY (group_id) REFERENCES t_scm_attribute_group(id) ON DELETE RESTRICT;

ALTER TABLE t_scm_attribute_group_attribute_relation
    ADD CONSTRAINT fk_t_scm_attribute_group_attribute_relation_02
        FOREIGN KEY (attribute_id) REFERENCES t_scm_attribute(id) ON DELETE RESTRICT;

-- 索引
CREATE INDEX idx_t_scm_attribute_group_attribute_relation_01
    ON t_scm_attribute_group_attribute_relation (attribute_id);

COMMENT ON TABLE t_scm_attribute_group_attribute_relation IS '商品属性组与属性关系表';
COMMENT ON COLUMN t_scm_attribute_group_attribute_relation.id IS 'ID';
COMMENT ON COLUMN t_scm_attribute_group_attribute_relation.group_id IS '属性组ID';
COMMENT ON COLUMN t_scm_attribute_group_attribute_relation.attribute_id IS '属性ID';
COMMENT ON COLUMN t_scm_attribute_group_attribute_relation.required_flag IS '组内是否必填';
COMMENT ON COLUMN t_scm_attribute_group_attribute_relation.display_flag IS '组内是否展示';
COMMENT ON COLUMN t_scm_attribute_group_attribute_relation.searchable_flag IS '组内是否可搜索';
COMMENT ON COLUMN t_scm_attribute_group_attribute_relation.sort IS '组内属性排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_attribute_group_attribute_relation.features IS '扩展特性JSON';
COMMENT ON COLUMN t_scm_attribute_group_attribute_relation.create_by IS '创建人';
COMMENT ON COLUMN t_scm_attribute_group_attribute_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_attribute_group_attribute_relation.update_by IS '修改人';
COMMENT ON COLUMN t_scm_attribute_group_attribute_relation.update_time IS '更新时间';


DROP TABLE IF EXISTS t_scm_back_category_front_relation;
CREATE TABLE t_scm_back_category_front_relation
(
    id                VARCHAR(32) NOT NULL,
    back_category_id  VARCHAR(32) NOT NULL,
    front_category_id VARCHAR(32) NOT NULL,
    sort              BIGINT NOT NULL DEFAULT 0,
    create_by         VARCHAR(32) NOT NULL,
    create_time       BIGINT NOT NULL,
    update_by         VARCHAR(32) NOT NULL,
    update_time       BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_back_category_front_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_back_category_front_relation_01 UNIQUE (back_category_id, front_category_id)
);

ALTER TABLE t_scm_back_category_front_relation
    ADD CONSTRAINT fk_t_scm_back_category_front_relation_01
        FOREIGN KEY (back_category_id) REFERENCES t_scm_back_category(id) ON DELETE RESTRICT;

ALTER TABLE t_scm_back_category_front_relation
    ADD CONSTRAINT fk_t_scm_back_category_front_relation_02
        FOREIGN KEY (front_category_id) REFERENCES t_scm_front_category(id) ON DELETE RESTRICT;

CREATE INDEX idx_t_scm_back_category_front_relation_01 ON t_scm_back_category_front_relation (back_category_id);

COMMENT ON TABLE t_scm_back_category_front_relation IS '前台类目与后台叶子类目映射表';
COMMENT ON COLUMN t_scm_back_category_front_relation.id IS 'ID';
COMMENT ON COLUMN t_scm_back_category_front_relation.back_category_id IS '后台类目ID（叶子类目）';
COMMENT ON COLUMN t_scm_back_category_front_relation.front_category_id IS '前台类目ID（叶子类目）';
COMMENT ON COLUMN t_scm_back_category_front_relation.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_back_category_front_relation.create_by IS '创建人';
COMMENT ON COLUMN t_scm_back_category_front_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_back_category_front_relation.update_by IS '修改人';
COMMENT ON COLUMN t_scm_back_category_front_relation.update_time IS '更新时间';


DROP TABLE IF EXISTS t_scm_back_category_attribute_group_relation;
CREATE TABLE t_scm_back_category_attribute_group_relation
(
    id               VARCHAR(32) NOT NULL,
    back_category_id VARCHAR(32) NOT NULL,
    group_id         VARCHAR(32) NOT NULL,
    required_flag    BOOLEAN NOT NULL DEFAULT false,
    display_flag     BOOLEAN NOT NULL DEFAULT true,
    searchable_flag  BOOLEAN NOT NULL DEFAULT false,
    sort             BIGINT NOT NULL DEFAULT 0,
    features         TEXT,
    create_by        VARCHAR(32) NOT NULL,
    create_time      BIGINT NOT NULL,
    update_by        VARCHAR(32) NOT NULL,
    update_time      BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_back_category_attribute_group_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_back_category_attribute_group_relation_01
        UNIQUE (back_category_id, group_id)
);

ALTER TABLE t_scm_back_category_attribute_group_relation
    ADD CONSTRAINT fk_t_scm_back_category_attribute_group_relation_01
        FOREIGN KEY (back_category_id) REFERENCES t_scm_back_category(id) ON DELETE RESTRICT;

ALTER TABLE t_scm_back_category_attribute_group_relation
    ADD CONSTRAINT fk_t_scm_back_category_attribute_group_relation_02
        FOREIGN KEY (group_id) REFERENCES t_scm_attribute_group(id) ON DELETE RESTRICT;

CREATE INDEX idx_t_scm_back_category_attribute_group_relation_01
    ON t_scm_back_category_attribute_group_relation (back_category_id);

COMMENT ON TABLE t_scm_back_category_attribute_group_relation IS '属性组与后台类目多对多关系表';
COMMENT ON COLUMN t_scm_back_category_attribute_group_relation.id IS 'ID';
COMMENT ON COLUMN t_scm_back_category_attribute_group_relation.back_category_id IS '后台类目ID';
COMMENT ON COLUMN t_scm_back_category_attribute_group_relation.group_id IS '属性组ID';
COMMENT ON COLUMN t_scm_back_category_attribute_group_relation.required_flag IS '该组在此类目下是否必填';
COMMENT ON COLUMN t_scm_back_category_attribute_group_relation.display_flag IS '该组在此类目下是否展示';
COMMENT ON COLUMN t_scm_back_category_attribute_group_relation.searchable_flag IS '该组在此类目下是否可搜索';
COMMENT ON COLUMN t_scm_back_category_attribute_group_relation.sort IS '类目下属性组排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_back_category_attribute_group_relation.features IS '扩展特性JSON';
COMMENT ON COLUMN t_scm_back_category_attribute_group_relation.create_by IS '创建人';
COMMENT ON COLUMN t_scm_back_category_attribute_group_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_back_category_attribute_group_relation.update_by IS '修改人';
COMMENT ON COLUMN t_scm_back_category_attribute_group_relation.update_time IS '更新时间';


DROP TABLE IF EXISTS t_scm_back_category_attribute_relation;
CREATE TABLE t_scm_back_category_attribute_relation
(
    id               VARCHAR(32) NOT NULL,
    back_category_id VARCHAR(32) NOT NULL,
    attribute_id     VARCHAR(32) NOT NULL,
    required_flag    BOOLEAN NOT NULL DEFAULT false,
    display_flag     BOOLEAN NOT NULL DEFAULT true,
    searchable_flag  BOOLEAN NOT NULL DEFAULT false,
    sort             BIGINT NOT NULL DEFAULT 0,
    features         TEXT,
    create_by        VARCHAR(32) NOT NULL,
    create_time      BIGINT NOT NULL,
    update_by        VARCHAR(32) NOT NULL,
    update_time      BIGINT NOT NULL,
    CONSTRAINT pk_t_scm_back_category_attribute_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_scm_back_category_attribute_relation_01 UNIQUE (back_category_id, attribute_id)
);

ALTER TABLE t_scm_back_category_attribute_relation
    ADD CONSTRAINT fk_t_scm_back_category_attribute_relation_01
        FOREIGN KEY (back_category_id) REFERENCES t_scm_back_category(id) ON DELETE RESTRICT;

ALTER TABLE t_scm_back_category_attribute_relation
    ADD CONSTRAINT fk_t_scm_back_category_attribute_relation_02
        FOREIGN KEY (attribute_id) REFERENCES t_scm_attribute(id) ON DELETE RESTRICT;

CREATE INDEX idx_t_scm_back_category_attribute_relation_01
    ON t_scm_back_category_attribute_relation (attribute_id);

COMMENT ON TABLE t_scm_back_category_attribute_relation IS '后台类目与属性绑定表';
COMMENT ON COLUMN t_scm_back_category_attribute_relation.id IS 'ID';
COMMENT ON COLUMN t_scm_back_category_attribute_relation.back_category_id IS '后台类目ID（叶子类目）';
COMMENT ON COLUMN t_scm_back_category_attribute_relation.attribute_id IS '属性ID';
COMMENT ON COLUMN t_scm_back_category_attribute_relation.required_flag IS '此类目下是否必填';
COMMENT ON COLUMN t_scm_back_category_attribute_relation.display_flag IS '此类目下是否展示';
COMMENT ON COLUMN t_scm_back_category_attribute_relation.searchable_flag IS '此类目下是否可搜索';
COMMENT ON COLUMN t_scm_back_category_attribute_relation.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_scm_back_category_attribute_relation.features IS '扩展特性JSON';
COMMENT ON COLUMN t_scm_back_category_attribute_relation.create_by IS '创建人';
COMMENT ON COLUMN t_scm_back_category_attribute_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_scm_back_category_attribute_relation.update_by IS '修改人';
COMMENT ON COLUMN t_scm_back_category_attribute_relation.update_time IS '更新时间';