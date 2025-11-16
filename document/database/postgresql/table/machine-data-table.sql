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

DROP TABLE IF EXISTS t_data_material_category;
CREATE TABLE t_data_material_category
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
    CONSTRAINT pk_t_data_material_category PRIMARY KEY (id),
    CONSTRAINT uk_t_data_material_category_01 UNIQUE (code),
    CONSTRAINT uk_t_data_material_category_02 UNIQUE (parent_id, name)
);

CREATE INDEX idx_t_data_material_category_01 ON t_data_material_category (update_time);

COMMENT ON TABLE t_data_material_category IS '素材分类表';
COMMENT ON COLUMN t_data_material_category.id IS 'ID';
COMMENT ON COLUMN t_data_material_category.parent_id IS '父ID';
COMMENT ON COLUMN t_data_material_category.code IS '编码';
COMMENT ON COLUMN t_data_material_category.name IS '名称';
COMMENT ON COLUMN t_data_material_category.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_data_material_category.create_by IS '创建人';
COMMENT ON COLUMN t_data_material_category.create_time IS '创建时间';
COMMENT ON COLUMN t_data_material_category.update_by IS '修改人';
COMMENT ON COLUMN t_data_material_category.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_material_category_relation;
CREATE TABLE t_data_material_category_relation
(
    id          VARCHAR(32) NOT NULL,
    category_id VARCHAR(32) NOT NULL,
    material_id VARCHAR(16) NOT NULL,
    sort        BIGINT NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_material_category_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_data_material_category_relation_01 UNIQUE (category_id, material_id)
);

CREATE INDEX idx_t_data_material_category_relation_01 ON t_data_material_category_relation (material_id);

COMMENT ON TABLE t_data_material_category_relation IS '素材与分类的关系表';
COMMENT ON COLUMN t_data_material_category_relation.id IS 'ID';
COMMENT ON COLUMN t_data_material_category_relation.category_id IS '分类id';
COMMENT ON COLUMN t_data_material_category_relation.material_id IS '素材ID';
COMMENT ON COLUMN t_data_material_category_relation.sort IS '排序';
COMMENT ON COLUMN t_data_material_category_relation.create_by IS '创建人';
COMMENT ON COLUMN t_data_material_category_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_data_material_category_relation.update_by IS '修改人';
COMMENT ON COLUMN t_data_material_category_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_material;
CREATE TABLE t_data_material
(
    id           VARCHAR(32) NOT NULL,
    status       VARCHAR(16) NOT NULL,
    type         VARCHAR(8) NOT NULL,
    storage_type VARCHAR(16) NOT NULL,
    title        VARCHAR(64) NOT NULL,
    name         VARCHAR(64) NOT NULL,
    size         BIGINT NOT NULL,
    file_info    VARCHAR(4096) NOT NULL,
    expire_time  BIGINT NOT NULL,
    create_by    VARCHAR(32) NOT NULL,
    create_time  BIGINT NOT NULL,
    update_by    VARCHAR(32) NOT NULL,
    update_time  BIGINT NOT NULL,
    CONSTRAINT pk_t_data_material PRIMARY KEY (id)
);

CREATE INDEX idx_t_data_material_01 ON t_data_material (expire_time);
CREATE INDEX idx_t_data_material_02 ON t_data_material (create_time);
CREATE INDEX idx_t_data_material_03 ON t_data_material (update_time);

COMMENT ON TABLE t_data_material IS '素材表';
COMMENT ON COLUMN t_data_material.id IS 'ID';
COMMENT ON COLUMN t_data_material.status IS '状态';
COMMENT ON COLUMN t_data_material.type IS '类型';
COMMENT ON COLUMN t_data_material.storage_type IS '持久化类型';
COMMENT ON COLUMN t_data_material.title IS '标题';
COMMENT ON COLUMN t_data_material.name IS '名称';
COMMENT ON COLUMN t_data_material.size IS '大小（字节）';
COMMENT ON COLUMN t_data_material.file_info IS 'obs文件信息';
COMMENT ON COLUMN t_data_material.expire_time IS '过期时间';
COMMENT ON COLUMN t_data_material.create_by IS '创建人';
COMMENT ON COLUMN t_data_material.create_time IS '创建时间';
COMMENT ON COLUMN t_data_material.update_by IS '修改人';
COMMENT ON COLUMN t_data_material.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_material_text;
CREATE TABLE t_data_material_text
(
    id          VARCHAR(32) NOT NULL,
    material_id VARCHAR(16) NOT NULL,
    word_count  INTEGER NOT NULL,
    format      VARCHAR(16) NOT NULL,
    content     TEXT NOT NULL,
    description TEXT,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_material_text PRIMARY KEY (id)
);

CREATE INDEX idx_t_data_material_text_01 ON t_data_material_text (material_id);

COMMENT ON TABLE t_data_material_text IS '文本素材表';
COMMENT ON COLUMN t_data_material_text.id IS 'ID';
COMMENT ON COLUMN t_data_material_text.material_id IS '素材ID';
COMMENT ON COLUMN t_data_material_text.word_count IS '字数统计';
COMMENT ON COLUMN t_data_material_text.format IS '文本格式';
COMMENT ON COLUMN t_data_material_text.content IS '内容';
COMMENT ON COLUMN t_data_material_text.description IS '描述';
COMMENT ON COLUMN t_data_material_text.create_by IS '创建人';
COMMENT ON COLUMN t_data_material_text.create_time IS '创建时间';
COMMENT ON COLUMN t_data_material_text.update_by IS '修改人';
COMMENT ON COLUMN t_data_material_text.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_material_image;
CREATE TABLE t_data_material_image
(
    id          VARCHAR(32) NOT NULL,
    material_id VARCHAR(16) NOT NULL,
    width       INTEGER NOT NULL,
    height      INTEGER NOT NULL,
    format      VARCHAR(16) NOT NULL,
    dpi         INTEGER,
    description TEXT,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_material_image PRIMARY KEY (id)
);

CREATE INDEX idx_t_data_material_image_01 ON t_data_material_image (material_id);

COMMENT ON TABLE t_data_material_image IS '图片素材表';
COMMENT ON COLUMN t_data_material_image.id IS 'ID';
COMMENT ON COLUMN t_data_material_image.material_id IS '素材ID';
COMMENT ON COLUMN t_data_material_image.width IS '图片宽度(像素)';
COMMENT ON COLUMN t_data_material_image.height IS '图片高度(像素)';
COMMENT ON COLUMN t_data_material_image.format IS '图片格式';
COMMENT ON COLUMN t_data_material_image.dpi IS '分辨率';
COMMENT ON COLUMN t_data_material_image.description IS '描述';
COMMENT ON COLUMN t_data_material_image.create_by IS '创建人';
COMMENT ON COLUMN t_data_material_image.create_time IS '创建时间';
COMMENT ON COLUMN t_data_material_image.update_by IS '修改人';
COMMENT ON COLUMN t_data_material_image.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_material_audio;
CREATE TABLE t_data_material_audio
(
    id          VARCHAR(32) NOT NULL,
    material_id VARCHAR(16) NOT NULL,
    duration    INTEGER NOT NULL,
    format      VARCHAR(16) NOT NULL,
    bitrate     INTEGER,
    sample_rate INTEGER,
    channels    SMALLINT,
    description TEXT,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_material_audio PRIMARY KEY (id)
);

CREATE INDEX idx_t_data_material_audio_01 ON t_data_material_audio (material_id);

COMMENT ON TABLE t_data_material_audio IS '音频素材表';
COMMENT ON COLUMN t_data_material_audio.id IS 'ID';
COMMENT ON COLUMN t_data_material_audio.material_id IS '素材ID';
COMMENT ON COLUMN t_data_material_audio.duration IS '时长(秒)';
COMMENT ON COLUMN t_data_material_audio.format IS '音频格式';
COMMENT ON COLUMN t_data_material_audio.bitrate IS '比特率(kbps)';
COMMENT ON COLUMN t_data_material_audio.sample_rate IS '采样率(Hz)';
COMMENT ON COLUMN t_data_material_audio.channels IS '声道数(1-单声道,2-立体声)';
COMMENT ON COLUMN t_data_material_audio.description IS '描述';
COMMENT ON COLUMN t_data_material_audio.create_by IS '创建人';
COMMENT ON COLUMN t_data_material_audio.create_time IS '创建时间';
COMMENT ON COLUMN t_data_material_audio.update_by IS '修改人';
COMMENT ON COLUMN t_data_material_audio.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_material_video;
CREATE TABLE t_data_material_video
(
    id          VARCHAR(32) NOT NULL,
    material_id VARCHAR(16) NOT NULL,
    duration    INTEGER NOT NULL,
    width       INTEGER NOT NULL,
    height      INTEGER NOT NULL,
    format      VARCHAR(16) NOT NULL,
    bitrate     INTEGER,
    fps         INTEGER,
    codec       VARCHAR(32),
    audio_codec VARCHAR(32),
    description TEXT,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_material_video PRIMARY KEY (id)
);

CREATE INDEX idx_t_data_material_video_01 ON t_data_material_video (material_id);

COMMENT ON TABLE t_data_material_video IS '视频素材表';
COMMENT ON COLUMN t_data_material_video.id IS 'ID';
COMMENT ON COLUMN t_data_material_video.material_id IS '素材ID';
COMMENT ON COLUMN t_data_material_video.duration IS '时长(秒)';
COMMENT ON COLUMN t_data_material_video.width IS '视频宽度(像素)';
COMMENT ON COLUMN t_data_material_video.height IS '视频高度(像素)';
COMMENT ON COLUMN t_data_material_video.format IS '视频格式';
COMMENT ON COLUMN t_data_material_video.bitrate IS '比特率(kbps)';
COMMENT ON COLUMN t_data_material_video.fps IS '帧率';
COMMENT ON COLUMN t_data_material_video.codec IS '视频编码格式';
COMMENT ON COLUMN t_data_material_video.audio_codec IS '音频编码格式';
COMMENT ON COLUMN t_data_material_video.description IS '描述';
COMMENT ON COLUMN t_data_material_video.create_by IS '创建人';
COMMENT ON COLUMN t_data_material_video.create_time IS '创建时间';
COMMENT ON COLUMN t_data_material_video.update_by IS '修改人';
COMMENT ON COLUMN t_data_material_video.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_material_document;
CREATE TABLE t_data_material_document
(
    id          VARCHAR(32) NOT NULL,
    material_id VARCHAR(16) NOT NULL,
    format      VARCHAR(16) NOT NULL,
    page_count  INTEGER,
    description TEXT,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_material_document PRIMARY KEY (id)
);

CREATE INDEX idx_t_data_material_document_01 ON t_data_material_document (material_id);

COMMENT ON TABLE t_data_material_document IS '文档素材表';
COMMENT ON COLUMN t_data_material_document.id IS 'ID';
COMMENT ON COLUMN t_data_material_document.material_id IS '素材ID';
COMMENT ON COLUMN t_data_material_document.format IS '文档格式';
COMMENT ON COLUMN t_data_material_document.page_count IS '页数';
COMMENT ON COLUMN t_data_material_document.description IS '描述';
COMMENT ON COLUMN t_data_material_document.create_by IS '创建人';
COMMENT ON COLUMN t_data_material_document.create_time IS '创建时间';
COMMENT ON COLUMN t_data_material_document.update_by IS '修改人';
COMMENT ON COLUMN t_data_material_document.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_material_file;
CREATE TABLE t_data_material_file
(
    id          VARCHAR(32) NOT NULL,
    material_id VARCHAR(16) NOT NULL,
    format      VARCHAR(16) NOT NULL,
    checksum    VARCHAR(512),
    description TEXT,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_material_file PRIMARY KEY (id)
);

CREATE INDEX idx_t_data_material_file_01 ON t_data_material_file (material_id);

COMMENT ON TABLE t_data_material_file IS '文件素材表';
COMMENT ON COLUMN t_data_material_file.id IS 'ID';
COMMENT ON COLUMN t_data_material_file.material_id IS '素材ID';
COMMENT ON COLUMN t_data_material_file.format IS '文件格式';
COMMENT ON COLUMN t_data_material_file.checksum IS '文件校验值';
COMMENT ON COLUMN t_data_material_file.description IS '描述';
COMMENT ON COLUMN t_data_material_file.create_by IS '创建人';
COMMENT ON COLUMN t_data_material_file.create_time IS '创建时间';
COMMENT ON COLUMN t_data_material_file.update_by IS '修改人';
COMMENT ON COLUMN t_data_material_file.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_attachment_category;
CREATE TABLE t_data_attachment_category
(
    id          VARCHAR(32) NOT NULL,
    parent_id   VARCHAR(32) NOT NULL,
    code        VARCHAR(32) NOT NULL,
    name        VARCHAR(64) NOT NULL,
    sort        INTEGER NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_data_attachment_category PRIMARY KEY (id),
    CONSTRAINT uk_t_data_attachment_category_01 UNIQUE (code),
    CONSTRAINT uk_t_data_attachment_category_02 UNIQUE (parent_id, name)
);

CREATE INDEX idx_t_data_attachment_category_01 ON t_data_attachment_category (update_time);

COMMENT ON TABLE t_data_attachment_category IS '附件分类表';
COMMENT ON COLUMN t_data_attachment_category.id IS '分类ID';
COMMENT ON COLUMN t_data_attachment_category.parent_id IS '父分类ID';
COMMENT ON COLUMN t_data_attachment_category.code IS '分类编码';
COMMENT ON COLUMN t_data_attachment_category.name IS '分类名称';
COMMENT ON COLUMN t_data_attachment_category.sort IS '排序，值大的靠前';
COMMENT ON COLUMN t_data_attachment_category.create_by IS '创建人';
COMMENT ON COLUMN t_data_attachment_category.create_time IS '创建时间(时间戳)';
COMMENT ON COLUMN t_data_attachment_category.update_by IS '更新人';
COMMENT ON COLUMN t_data_attachment_category.update_time IS '更新时间(时间戳)';

DROP TABLE IF EXISTS t_data_attachment_category_relation;
CREATE TABLE t_data_attachment_category_relation
(
    id            VARCHAR(32) NOT NULL,
    category_id   VARCHAR(32) NOT NULL,
    attachment_id VARCHAR(32) NOT NULL,
    sort          BIGINT NOT NULL DEFAULT 0,
    create_by     VARCHAR(32) NOT NULL,
    create_time   BIGINT NOT NULL,
    update_by     VARCHAR(32) NOT NULL,
    update_time   BIGINT NOT NULL,
    CONSTRAINT pk_t_data_attachment_category_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_data_attachment_category_relation_01 UNIQUE (category_id, attachment_id)
);

CREATE INDEX idx_t_data_attachment_category_relation_01 ON t_data_attachment_category_relation (attachment_id);

COMMENT ON TABLE t_data_attachment_category_relation IS '附件分类关系表';
COMMENT ON COLUMN t_data_attachment_category_relation.id IS '关系ID';
COMMENT ON COLUMN t_data_attachment_category_relation.category_id IS '分类ID';
COMMENT ON COLUMN t_data_attachment_category_relation.attachment_id IS '附件ID';
COMMENT ON COLUMN t_data_attachment_category_relation.sort IS '排序';
COMMENT ON COLUMN t_data_attachment_category_relation.create_by IS '创建人';
COMMENT ON COLUMN t_data_attachment_category_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_data_attachment_category_relation.update_by IS '修改人';
COMMENT ON COLUMN t_data_attachment_category_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_attachment;
CREATE TABLE t_data_attachment
(
    id           VARCHAR(32) NOT NULL,
    status       VARCHAR(16) NOT NULL,
    type         VARCHAR(8) NOT NULL,
    storage_type VARCHAR(16) NOT NULL,
    title        VARCHAR(64) NOT NULL,
    name         VARCHAR(64) NOT NULL,
    size         BIGINT NOT NULL,
    file_info    VARCHAR(4096) NOT NULL,
    expire_time  BIGINT NOT NULL,
    description  VARCHAR(2048),
    create_by    VARCHAR(32) NOT NULL,
    create_time  BIGINT NOT NULL,
    update_by    VARCHAR(32) NOT NULL,
    update_time  BIGINT NOT NULL,
    CONSTRAINT pk_t_data_attachment PRIMARY KEY (id)
);

CREATE INDEX idx_t_data_attachment_01 ON t_data_attachment (expire_time);
CREATE INDEX idx_t_data_attachment_02 ON t_data_attachment (create_time);
CREATE INDEX idx_t_data_attachment_03 ON t_data_attachment (update_time);
CREATE INDEX idx_t_data_attachment_04 ON t_data_attachment (create_by);
CREATE INDEX idx_t_data_attachment_05 ON t_data_attachment (update_by);

COMMENT ON TABLE t_data_attachment IS 'ERP附件主表';
COMMENT ON COLUMN t_data_attachment.id IS 'ID';
COMMENT ON COLUMN t_data_attachment.status IS '状态';
COMMENT ON COLUMN t_data_attachment.type IS '类型';
COMMENT ON COLUMN t_data_attachment.storage_type IS '持久化类型';
COMMENT ON COLUMN t_data_attachment.title IS '标题';
COMMENT ON COLUMN t_data_attachment.name IS '名称';
COMMENT ON COLUMN t_data_attachment.size IS '大小（字节）';
COMMENT ON COLUMN t_data_attachment.file_info IS 'obs文件信息';
COMMENT ON COLUMN t_data_attachment.expire_time IS '过期时间';
COMMENT ON COLUMN t_data_attachment.description IS '文件描述';
COMMENT ON COLUMN t_data_attachment.create_by IS '创建人';
COMMENT ON COLUMN t_data_attachment.create_time IS '创建时间(时间戳)';
COMMENT ON COLUMN t_data_attachment.update_by IS '更新人';
COMMENT ON COLUMN t_data_attachment.update_time IS '更新时间(时间戳)';

DROP TABLE IF EXISTS t_data_attachment_table_relation;
CREATE TABLE t_data_attachment_table_relation
(
    id            VARCHAR(32) NOT NULL,
    attachment_id VARCHAR(32) NOT NULL,
    table_name    VARCHAR(32) NOT NULL,
    data_id       VARCHAR(32) NOT NULL,
    sort          BIGINT NOT NULL DEFAULT 0,
    create_by     VARCHAR(32) NOT NULL,
    create_time   BIGINT NOT NULL,
    update_by     VARCHAR(32) NOT NULL,
    update_time   BIGINT NOT NULL,
    CONSTRAINT pk_t_data_attachment_table_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_data_attachment_table_relation_01 UNIQUE (attachment_id, data_id, table_name)
);

CREATE INDEX idx_t_data_attachment_table_relation_01 ON t_data_attachment_table_relation (data_id, table_name);

COMMENT ON TABLE t_data_attachment_table_relation IS 'ERP附件和表关联表';
COMMENT ON COLUMN t_data_attachment_table_relation.id IS 'ID';
COMMENT ON COLUMN t_data_attachment_table_relation.attachment_id IS '附件ID';
COMMENT ON COLUMN t_data_attachment_table_relation.table_name IS '表名称';
COMMENT ON COLUMN t_data_attachment_table_relation.data_id IS '数据ID';
COMMENT ON COLUMN t_data_attachment_table_relation.sort IS '排序';
COMMENT ON COLUMN t_data_attachment_table_relation.create_by IS '创建人';
COMMENT ON COLUMN t_data_attachment_table_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_data_attachment_table_relation.update_by IS '修改人';
COMMENT ON COLUMN t_data_attachment_table_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_data_attachment_operation_log;
CREATE TABLE t_data_attachment_operation_log
(
    id             VARCHAR(32) NOT NULL,
    attachment_id  VARCHAR(32) NOT NULL,
    operation_type VARCHAR(16) NOT NULL,
    ip_address     VARCHAR(128) NOT NULL,
    platform       VARCHAR(32) NOT NULL DEFAULT '',
    user_agent     VARCHAR(512) NOT NULL DEFAULT '',
    create_by      VARCHAR(32) NOT NULL,
    create_time    BIGINT NOT NULL,
    update_by      VARCHAR(32) NOT NULL,
    update_time    BIGINT NOT NULL,
    CONSTRAINT pk_t_data_attachment_operation_log PRIMARY KEY (id)
);

CREATE INDEX idx_t_data_attachment_operation_log_01 ON t_data_attachment_operation_log (attachment_id);
CREATE INDEX idx_t_data_attachment_operation_log_02 ON t_data_attachment_operation_log (ip_address);
CREATE INDEX idx_t_data_attachment_operation_log_03 ON t_data_attachment_operation_log (create_by);
CREATE INDEX idx_t_data_attachment_operation_log_04 ON t_data_attachment_operation_log (create_time);

COMMENT ON TABLE t_data_attachment_operation_log IS '附件操作日志表';
COMMENT ON COLUMN t_data_attachment_operation_log.id IS '日志ID';
COMMENT ON COLUMN t_data_attachment_operation_log.attachment_id IS '附件ID';
COMMENT ON COLUMN t_data_attachment_operation_log.operation_type IS '操作类型';
COMMENT ON COLUMN t_data_attachment_operation_log.ip_address IS 'IP 地址';
COMMENT ON COLUMN t_data_attachment_operation_log.platform IS '登录平台';
COMMENT ON COLUMN t_data_attachment_operation_log.user_agent IS '浏览器和操作系统等信息';
COMMENT ON COLUMN t_data_attachment_operation_log.create_by IS '创建人';
COMMENT ON COLUMN t_data_attachment_operation_log.create_time IS '创建时间(时间戳)';
COMMENT ON COLUMN t_data_attachment_operation_log.update_by IS '更新人';
COMMENT ON COLUMN t_data_attachment_operation_log.update_time IS '更新时间(时间戳)';


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
    CONSTRAINT uk_t_data_tag_option_01 UNIQUE (code),
    CONSTRAINT uk_t_data_tag_option_02 UNIQUE (tag_id)
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

DROP TABLE IF EXISTS t_data_download;
CREATE TABLE t_data_download
(
    id            VARCHAR(32) NOT NULL,
    status        VARCHAR(8) NOT NULL,
    attachment_id VARCHAR(32) NOT NULL DEFAULT '',
    content       TEXT,
    fail_cause    TEXT,
    create_by     VARCHAR(32) NOT NULL,
    create_time   BIGINT NOT NULL,
    update_by     VARCHAR(32) NOT NULL,
    update_time   BIGINT NOT NULL,
    CONSTRAINT pk_t_data_download PRIMARY KEY (id)
);

CREATE INDEX idx_t_data_download_01 ON t_data_download (attachment_id);
CREATE INDEX idx_t_data_download_02 ON t_data_download (update_by);
CREATE INDEX idx_t_data_download_03 ON t_data_download (create_time);

COMMENT ON TABLE t_data_download IS '下载文件表';
COMMENT ON COLUMN t_data_download.id IS 'ID';
COMMENT ON COLUMN t_data_download.status IS '任务状态';
COMMENT ON COLUMN t_data_download.attachment_id IS '附件ID';
COMMENT ON COLUMN t_data_download.content IS '内容';
COMMENT ON COLUMN t_data_download.fail_cause IS '失败原因';
COMMENT ON COLUMN t_data_download.create_by IS '创建人';
COMMENT ON COLUMN t_data_download.create_time IS '创建时间';
COMMENT ON COLUMN t_data_download.update_by IS '修改人';
COMMENT ON COLUMN t_data_download.update_time IS '更新时间';

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