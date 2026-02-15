
DROP TABLE IF EXISTS t_data_tag_category;
CREATE TABLE t_data_tag_category
(
    id          VARCHAR(32) NOT NULL,
    parent_id   VARCHAR(32) NOT NULL,
    code        VARCHAR(32) NOT NULL,
    type        VARCHAR(16) NOT NULL,
    name        VARCHAR(32) NOT NULL DEFAULT '',
    sort        INTEGER NOT NULL,
    description TEXT,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_tag_category PRIMARY KEY (id),
    CONSTRAINT uk_t_data_tag_category_01 UNIQUE (parent_id, id),
    CONSTRAINT uk_t_data_tag_category_02 UNIQUE (code)
);

COMMENT ON TABLE t_data_tag_category IS '智能标签分类表';
COMMENT ON COLUMN t_data_tag_category.id IS 'ID';
COMMENT ON COLUMN t_data_tag_category.parent_id IS '父ID';
COMMENT ON COLUMN t_data_tag_category.code IS '编码';
COMMENT ON COLUMN t_data_tag_category.type IS '类型';
COMMENT ON COLUMN t_data_tag_category.name IS '名称';
COMMENT ON COLUMN t_data_tag_category.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_data_tag_category.description IS '描述';
COMMENT ON COLUMN t_data_tag_category.create_by IS '创建人';
COMMENT ON COLUMN t_data_tag_category.create_time IS '创建时间';
COMMENT ON COLUMN t_data_tag_category.update_by IS '修改人';
COMMENT ON COLUMN t_data_tag_category.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_tag;
CREATE TABLE t_data_tag
(
    id          VARCHAR(32) NOT NULL,
    category_id VARCHAR(32) NOT NULL,
    code        VARCHAR(32) NOT NULL,
    name        VARCHAR(32) NOT NULL DEFAULT '',
    status      VARCHAR(8) NOT NULL DEFAULT 'DISABLE',
    sort        BIGINT NOT NULL,
    description TEXT,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_tag PRIMARY KEY (id),
    CONSTRAINT uk_t_data_tag_01 UNIQUE (name)
);

CREATE INDEX idx_t_data_tag_01 ON t_data_tag (create_time);
CREATE INDEX idx_t_data_tag_02 ON t_data_tag (update_time);

COMMENT ON TABLE t_data_tag IS '智能标签表';
COMMENT ON COLUMN t_data_tag.id IS 'ID';
COMMENT ON COLUMN t_data_tag.category_id IS '分类ID';
COMMENT ON COLUMN t_data_tag.code IS '编码';
COMMENT ON COLUMN t_data_tag.name IS '名称';
COMMENT ON COLUMN t_data_tag.status IS '状态';
COMMENT ON COLUMN t_data_tag.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_data_tag.description IS '描述';
COMMENT ON COLUMN t_data_tag.create_by IS '创建人';
COMMENT ON COLUMN t_data_tag.create_time IS '创建时间';
COMMENT ON COLUMN t_data_tag.update_by IS '修改人';
COMMENT ON COLUMN t_data_tag.update_time IS '更新时间';


DROP TABLE IF EXISTS t_data_tag_option;
CREATE TABLE t_data_tag_option
(
    id          VARCHAR(32) NOT NULL,
    tag_id      VARCHAR(32) NOT NULL,
    code        VARCHAR(32) NOT NULL,
    name        VARCHAR(32) NOT NULL DEFAULT '',
    status      VARCHAR(8) NOT NULL DEFAULT 'DISABLE',
    sort        BIGINT NOT NULL,
    description TEXT,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_tag_option PRIMARY KEY (id),
    CONSTRAINT uk_t_data_tag_option_01 UNIQUE (tag_id,code),
    CONSTRAINT uk_t_data_tag_option_02 UNIQUE (tag_id,name)
);

COMMENT ON TABLE t_data_tag_option IS '智能标签选项表';
COMMENT ON COLUMN t_data_tag_option.id IS 'ID';
COMMENT ON COLUMN t_data_tag_option.tag_id IS '智能标签ID';
COMMENT ON COLUMN t_data_tag_option.code IS '编码';
COMMENT ON COLUMN t_data_tag_option.name IS '名称';
COMMENT ON COLUMN t_data_tag_option.status IS '状态';
COMMENT ON COLUMN t_data_tag_option.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_data_tag_option.description IS '描述';
COMMENT ON COLUMN t_data_tag_option.create_by IS '创建人';
COMMENT ON COLUMN t_data_tag_option.create_time IS '创建时间';
COMMENT ON COLUMN t_data_tag_option.update_by IS '修改人';
COMMENT ON COLUMN t_data_tag_option.update_time IS '更新时间';




DROP TABLE IF EXISTS t_data_label_category;
CREATE TABLE t_data_label_category
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
    CONSTRAINT pk_t_data_label_category PRIMARY KEY (id),
    CONSTRAINT uk_t_data_label_category_01 UNIQUE (code),
    CONSTRAINT uk_t_data_label_category_02 UNIQUE (parent_id, name)
);

CREATE INDEX idx_t_data_label_category_01 ON t_data_label_category (update_time);

COMMENT ON TABLE t_data_label_category IS '人工标签分类表';
COMMENT ON COLUMN t_data_label_category.id IS 'ID';
COMMENT ON COLUMN t_data_label_category.parent_id IS '父ID';
COMMENT ON COLUMN t_data_label_category.code IS '编码';
COMMENT ON COLUMN t_data_label_category.name IS '名称';
COMMENT ON COLUMN t_data_label_category.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_data_label_category.create_by IS '创建人';
COMMENT ON COLUMN t_data_label_category.create_time IS '创建时间';
COMMENT ON COLUMN t_data_label_category.update_by IS '修改人';
COMMENT ON COLUMN t_data_label_category.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_label;
CREATE TABLE t_data_label
(
    id          VARCHAR(32) NOT NULL,
    category_id VARCHAR(32) NOT NULL,
    code        VARCHAR(32) NOT NULL,
    name        VARCHAR(32) NOT NULL,
    sort        BIGINT NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_label PRIMARY KEY (id),
    CONSTRAINT uk_t_data_label_01 UNIQUE (code),
    CONSTRAINT uk_t_data_label_02 UNIQUE (category_id, name)
);

CREATE INDEX idx_t_data_label_01 ON t_data_label (create_time);
CREATE INDEX idx_t_data_label_02 ON t_data_label (update_time);

COMMENT ON TABLE t_data_label IS '人工标签表';
COMMENT ON COLUMN t_data_label.id IS 'ID';
COMMENT ON COLUMN t_data_label.category_id IS '分类ID';
COMMENT ON COLUMN t_data_label.code IS '编码';
COMMENT ON COLUMN t_data_label.name IS '名称';
COMMENT ON COLUMN t_data_label.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_data_label.create_by IS '创建人';
COMMENT ON COLUMN t_data_label.create_time IS '创建时间';
COMMENT ON COLUMN t_data_label.update_by IS '修改人';
COMMENT ON COLUMN t_data_label.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_label_option;
CREATE TABLE t_data_label_option
(
    id          VARCHAR(32) NOT NULL,
    label_id    VARCHAR(32) NOT NULL,
    code        VARCHAR(32) NOT NULL,
    name        VARCHAR(32) NOT NULL DEFAULT '',
    status      VARCHAR(8) NOT NULL DEFAULT 'DISABLE',
    sort        BIGINT NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_label_option PRIMARY KEY (id),
    CONSTRAINT uk_t_data_label_option_01 UNIQUE (code),
    CONSTRAINT uk_t_data_label_option_02 UNIQUE (label_id)
);

COMMENT ON TABLE t_data_label_option IS '人工标签选项表';
COMMENT ON COLUMN t_data_label_option.id IS 'ID';
COMMENT ON COLUMN t_data_label_option.label_id IS '人工标签ID';
COMMENT ON COLUMN t_data_label_option.code IS '编码';
COMMENT ON COLUMN t_data_label_option.name IS '名称';
COMMENT ON COLUMN t_data_label_option.status IS '状态';
COMMENT ON COLUMN t_data_label_option.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_data_label_option.create_by IS '创建人';
COMMENT ON COLUMN t_data_label_option.create_time IS '创建时间';
COMMENT ON COLUMN t_data_label_option.update_by IS '修改人';
COMMENT ON COLUMN t_data_label_option.update_time IS '更新时间';