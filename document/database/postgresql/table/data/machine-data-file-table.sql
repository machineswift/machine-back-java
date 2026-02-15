DROP TABLE IF EXISTS t_data_file;
CREATE TABLE t_data_file
(
    id            VARCHAR(32)   NOT NULL,
    file_type     VARCHAR(16)   NOT NULL,
    original_name VARCHAR(64)   NOT NULL,
    storage_name  VARCHAR(64)   NOT NULL,
    md5_hash      VARCHAR(32),
    file_info      VARCHAR(4096) NOT NULL,
    size          BIGINT        NOT NULL,
    create_by     VARCHAR(32)   NOT NULL,
    create_time   BIGINT        NOT NULL,
    update_by     VARCHAR(32)   NOT NULL,
    update_time   BIGINT        NOT NULL,
    CONSTRAINT pk_t_data_file PRIMARY KEY (id)
);

CREATE INDEX idx_t_data_file_01 ON t_data_file (md5_hash);

COMMENT ON TABLE t_data_file IS 'ERP文件主表';
COMMENT ON COLUMN t_data_file.id IS 'ID';
COMMENT ON COLUMN t_data_file.file_type IS '文件类型';
COMMENT ON COLUMN t_data_file.storage_name IS '原始名称';
COMMENT ON COLUMN t_data_file.storage_name IS '存储名称';
COMMENT ON COLUMN t_data_file.md5_hash IS '文件MD5-用于去重和秒传';
COMMENT ON COLUMN t_data_file.file_info IS 'obs文件信息';
COMMENT ON COLUMN t_data_file.size IS '大小（字节）';
COMMENT ON COLUMN t_data_file.create_by IS '创建人';
COMMENT ON COLUMN t_data_file.create_time IS '创建时间(时间戳)';
COMMENT ON COLUMN t_data_file.update_by IS '更新人';
COMMENT ON COLUMN t_data_file.update_time IS '更新时间(时间戳)';


DROP TABLE IF EXISTS t_data_attachment;
CREATE TABLE t_data_attachment
(
    id          VARCHAR(32)  NOT NULL,
    status      VARCHAR(16)  NOT NULL,
    entity      VARCHAR(32)  NOT NULL,
    entity_id   VARCHAR(32)  NOT NULL,
    file_id     VARCHAR(32)  NOT NULL,
    expire_time BIGINT       NOT NULL,
    create_by   VARCHAR(32)  NOT NULL,
    create_time BIGINT       NOT NULL,
    update_by   VARCHAR(32)  NOT NULL,
    update_time BIGINT       NOT NULL,
    CONSTRAINT pk_t_data_attachment PRIMARY KEY (id)
);

CREATE INDEX idx_t_data_attachment_01 ON t_data_attachment (entity_id);
CREATE INDEX idx_t_data_attachment_02 ON t_data_attachment (file_id);
CREATE INDEX idx_t_data_attachment_03 ON t_data_attachment (expire_time);
CREATE INDEX idx_t_data_attachment_04 ON t_data_attachment (create_by);

COMMENT ON TABLE t_data_attachment IS 'ERP附件主表';
COMMENT ON COLUMN t_data_attachment.id IS 'ID';
COMMENT ON COLUMN t_data_attachment.status IS '状态';
COMMENT ON COLUMN t_data_attachment.entity IS '实体';
COMMENT ON COLUMN t_data_attachment.entity_id IS '实体Id';
COMMENT ON COLUMN t_data_attachment.file_id IS '文件ID';
COMMENT ON COLUMN t_data_attachment.expire_time IS '过期时间';
COMMENT ON COLUMN t_data_attachment.create_by IS '创建人';
COMMENT ON COLUMN t_data_attachment.create_time IS '创建时间(时间戳)';
COMMENT ON COLUMN t_data_attachment.update_by IS '更新人';
COMMENT ON COLUMN t_data_attachment.update_time IS '更新时间(时间戳)';


DROP TABLE IF EXISTS t_data_download;
CREATE TABLE t_data_download
(
    id            VARCHAR(32) NOT NULL,
    status        VARCHAR(8) NOT NULL,
    module        VARCHAR(16) NOT NULL,
    entity        VARCHAR(32) NOT NULL,
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
COMMENT ON COLUMN t_data_download.status IS '模块';
COMMENT ON COLUMN t_data_download.entity IS '实体';
COMMENT ON COLUMN t_data_download.status IS '任务状态';
COMMENT ON COLUMN t_data_download.attachment_id IS '附件ID';
COMMENT ON COLUMN t_data_download.content IS '内容';
COMMENT ON COLUMN t_data_download.fail_cause IS '失败原因';
COMMENT ON COLUMN t_data_download.create_by IS '创建人';
COMMENT ON COLUMN t_data_download.create_time IS '创建时间';
COMMENT ON COLUMN t_data_download.update_by IS '修改人';
COMMENT ON COLUMN t_data_download.update_time IS '更新时间';


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
    material_id VARCHAR(32) NOT NULL,
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
    file_id      VARCHAR(32) NOT NULL,
    status       VARCHAR(16) NOT NULL,
    type         VARCHAR(8) NOT NULL,
    title        VARCHAR(64) NOT NULL,
    name         VARCHAR(64) NOT NULL,
    expire_time  BIGINT NOT NULL,
    reference_count INT DEFAULT 0,
    create_by    VARCHAR(32) NOT NULL,
    create_time  BIGINT NOT NULL,
    update_by    VARCHAR(32) NOT NULL,
    update_time  BIGINT NOT NULL,
    CONSTRAINT pk_t_data_material PRIMARY KEY (id)
);

CREATE INDEX idx_t_data_material_01 ON t_data_material (file_id);
CREATE INDEX idx_t_data_material_02 ON t_data_material (expire_time);
CREATE INDEX idx_t_data_material_03 ON t_data_material (update_by);
CREATE INDEX idx_t_data_material_04 ON t_data_material (update_time);

COMMENT ON TABLE t_data_material IS '素材表';
COMMENT ON COLUMN t_data_material.id IS 'ID';
COMMENT ON COLUMN t_data_material.file_id IS '文件ID';
COMMENT ON COLUMN t_data_material.status IS '状态';
COMMENT ON COLUMN t_data_material.type IS '类型';
COMMENT ON COLUMN t_data_material.title IS '标题';
COMMENT ON COLUMN t_data_material.name IS '名称';
COMMENT ON COLUMN t_data_material.expire_time IS '过期时间';
COMMENT ON COLUMN t_data_material.reference_count IS '引用计数';
COMMENT ON COLUMN t_data_material.create_by IS '创建人';
COMMENT ON COLUMN t_data_material.create_time IS '创建时间';
COMMENT ON COLUMN t_data_material.update_by IS '修改人';
COMMENT ON COLUMN t_data_material.update_time IS '更新时间';