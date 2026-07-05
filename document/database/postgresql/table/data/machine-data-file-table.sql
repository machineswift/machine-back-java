DROP TABLE IF EXISTS t_data_file;
CREATE TABLE t_data_file
(
    id            VARCHAR(32)   NOT NULL,
    file_type     VARCHAR(16)   NOT NULL,
    original_name VARCHAR(128)  NOT NULL,
    storage_name  VARCHAR(128)  NOT NULL,
    storage_path  VARCHAR(256)  NOT NULL,
    hash_sha256   VARCHAR(64),
    file_info     VARCHAR(4096) NOT NULL,
    size          BIGINT        NOT NULL,
    create_by     VARCHAR(32)   NOT NULL,
    create_time   BIGINT        NOT NULL,
    update_by     VARCHAR(32)   NOT NULL,
    update_time   BIGINT        NOT NULL,
    CONSTRAINT pk_t_data_file PRIMARY KEY (id)
);

CREATE INDEX idx_t_data_file_01 ON t_data_file (hash_sha256);

COMMENT ON TABLE t_data_file IS 'ERP文件主表';
COMMENT ON COLUMN t_data_file.id IS '文件ID';
COMMENT ON COLUMN t_data_file.file_type IS '文件类型（扩展名）';
COMMENT ON COLUMN t_data_file.original_name IS '原始名称';
COMMENT ON COLUMN t_data_file.storage_name IS '存储名称';
COMMENT ON COLUMN t_data_file.storage_path IS '存储路径（bucket/路径）';
COMMENT ON COLUMN t_data_file.hash_sha256 IS '文件SHA-256哈希值-用于去重和秒传';
COMMENT ON COLUMN t_data_file.file_info IS '文件存储信息（JSON格式）';
COMMENT ON COLUMN t_data_file.size IS '文件大小（字节）';
COMMENT ON COLUMN t_data_file.create_by IS '创建人';
COMMENT ON COLUMN t_data_file.create_time IS '创建时间（时间戳）';
COMMENT ON COLUMN t_data_file.update_by IS '更新人';
COMMENT ON COLUMN t_data_file.update_time IS '更新时间（时间戳）';


DROP TABLE IF EXISTS t_data_file_temp;
CREATE TABLE t_data_file_temp
(
    id            VARCHAR(32)   NOT NULL,
    file_type     VARCHAR(16)   NOT NULL,
    original_name VARCHAR(128)  NOT NULL,
    storage_name  VARCHAR(128)  NOT NULL,
    storage_path  VARCHAR(256)  NOT NULL,
    file_info     VARCHAR(4096) NOT NULL,
    size          BIGINT        NOT NULL,
    expire_time   BIGINT        NOT NULL,
    create_by     VARCHAR(32)   NOT NULL,
    create_time   BIGINT        NOT NULL,
    update_by     VARCHAR(32)   NOT NULL,
    update_time   BIGINT        NOT NULL,
    CONSTRAINT pk_t_data_file_temp PRIMARY KEY (id)
);

CREATE INDEX idx_file_temp_01 ON t_data_file_temp (expire_time);

COMMENT ON TABLE t_data_file_temp IS 'ERP临时文件表-上传但未关联业务的文件';
COMMENT ON COLUMN t_data_file_temp.id IS '临时文件ID';
COMMENT ON COLUMN t_data_file_temp.file_type IS '文件类型（扩展名）';
COMMENT ON COLUMN t_data_file_temp.original_name IS '原始名称';
COMMENT ON COLUMN t_data_file_temp.storage_name IS '存储名称';
COMMENT ON COLUMN t_data_file_temp.storage_path IS '存储路径（bucket/路径）';
COMMENT ON COLUMN t_data_file_temp.file_info IS '文件存储信息（JSON格式）';
COMMENT ON COLUMN t_data_file_temp.size IS '文件大小（字节）';
COMMENT ON COLUMN t_data_file_temp.expire_time IS '过期时间-过期后定时清理';
COMMENT ON COLUMN t_data_file_temp.create_by IS '创建人';
COMMENT ON COLUMN t_data_file_temp.create_time IS '创建时间（时间戳）';
COMMENT ON COLUMN t_data_file_temp.update_by IS '更新人';
COMMENT ON COLUMN t_data_file_temp.update_time IS '更新时间（时间戳）';


DROP TABLE IF EXISTS t_data_attachment;
CREATE TABLE t_data_attachment
(
    id                 VARCHAR(32) NOT NULL,
    status             VARCHAR(16) NOT NULL DEFAULT 'ACTIVE',
    entity             VARCHAR(32) NOT NULL,
    entity_id          VARCHAR(32) NOT NULL,
    attachment_group   VARCHAR(32) NOT NULL,
    current_version_id VARCHAR(32),
    max_version_no     INT         NOT NULL,
    expire_time        BIGINT      NOT NULL,
    create_by          VARCHAR(32) NOT NULL,
    create_time        BIGINT      NOT NULL,
    update_by          VARCHAR(32) NOT NULL,
    update_time        BIGINT      NOT NULL,
    CONSTRAINT pk_t_data_attachment PRIMARY KEY (id),
    CONSTRAINT uk_attachment_group UNIQUE (entity, entity_id, attachment_group)
);

CREATE INDEX idx_t_attachment_01 ON t_data_attachment (entity, entity_id);
CREATE INDEX idx_t_attachment_02 ON t_data_attachment (expire_time);
CREATE INDEX idx_t_attachment_03 ON t_data_attachment (create_by);
CREATE INDEX idx_t_attachment_04 ON t_data_attachment (current_version_id);

COMMENT ON TABLE t_data_attachment IS 'ERP附件主表';
COMMENT ON COLUMN t_data_attachment.id IS '附件主键ID';
COMMENT ON COLUMN t_data_attachment.status IS '状态';
COMMENT ON COLUMN t_data_attachment.entity IS '业务实体类型';
COMMENT ON COLUMN t_data_attachment.entity_id IS '业务实体ID';
COMMENT ON COLUMN t_data_attachment.attachment_group IS '附件分组（同一分组所有版本共享）';
COMMENT ON COLUMN t_data_attachment.current_version_id IS '当前版本ID';
COMMENT ON COLUMN t_data_attachment.max_version_no IS '最大版本号';
COMMENT ON COLUMN t_data_attachment.expire_time IS '过期时间（时间戳）';
COMMENT ON COLUMN t_data_attachment.create_by IS '创建人';
COMMENT ON COLUMN t_data_attachment.create_time IS '创建时间（时间戳）';
COMMENT ON COLUMN t_data_attachment.update_by IS '更新人';
COMMENT ON COLUMN t_data_attachment.update_time IS '更新时间（时间戳）';


DROP TABLE IF EXISTS t_data_attachment_version;
CREATE TABLE t_data_attachment_version
(
    id                VARCHAR(32) NOT NULL,
    attachment_id     VARCHAR(32) NOT NULL,
    status            VARCHAR(16) NOT NULL,
    entity            VARCHAR(32) NOT NULL,
    entity_id         VARCHAR(32) NOT NULL,
    attachment_group  VARCHAR(32) NOT NULL,
    version_no        INT         NOT NULL,
    is_current        SMALLINT    NOT NULL,
    source_version_id VARCHAR(32),
    change_type       VARCHAR(32) NOT NULL,
    change_time       BIGINT      NOT NULL,
    change_desc       VARCHAR(2048),
    create_by         VARCHAR(32) NOT NULL,
    create_time       BIGINT      NOT NULL,
    update_by         VARCHAR(32) NOT NULL,
    update_time       BIGINT      NOT NULL,
    CONSTRAINT pk_t_attachment_version PRIMARY KEY (id),
    CONSTRAINT uk_version_group_no UNIQUE (attachment_id, version_no)
);

CREATE INDEX idx_t_data_attachment_version_01 ON t_data_attachment_version (entity, entity_id, attachment_group, version_no);
CREATE INDEX idx_t_data_attachment_version_02 ON t_data_attachment_version (entity, entity_id, attachment_group, is_current);
CREATE INDEX idx_t_data_attachment_version_03 ON t_data_attachment_version (entity, entity_id, is_current);
CREATE INDEX idx_t_data_attachment_version_04 ON t_data_attachment_version (source_version_id);
CREATE INDEX idx_t_data_attachment_version_05 ON t_data_attachment_version (update_time);
CREATE INDEX idx_t_data_attachment_version_08 ON t_data_attachment_version (change_time);

COMMENT ON TABLE t_data_attachment_version IS 'ERP附件版本管理表';

COMMENT ON COLUMN t_data_attachment_version.id IS '版本主键ID';
COMMENT ON COLUMN t_data_attachment_version.attachment_id IS '附件主表ID';
COMMENT ON COLUMN t_data_attachment_version.status IS '业务状态';
COMMENT ON COLUMN t_data_attachment_version.entity IS '业务实体类型-冗余字段';
COMMENT ON COLUMN t_data_attachment_version.entity_id IS '业务实体ID-冗余字段';
COMMENT ON COLUMN t_data_attachment_version.attachment_group IS '附件分组-冗余字段（同一分组所有版本共享）';
COMMENT ON COLUMN t_data_attachment_version.version_no IS '版本号（从1开始递增，同一分组内唯一）';
COMMENT ON COLUMN t_data_attachment_version.is_current IS '是否当前版本：1-是（有效版本），0-否（历史版本）';
COMMENT ON COLUMN t_data_attachment_version.source_version_id IS '来源版本ID';
COMMENT ON COLUMN t_data_attachment_version.change_type IS '变更类型';
COMMENT ON COLUMN t_data_attachment_version.change_time IS '变更发生时间（时间戳，毫秒）';
COMMENT ON COLUMN t_data_attachment_version.change_desc IS '变更说明（描述本次变更的原因或内容）';
COMMENT ON COLUMN t_data_attachment_version.create_by IS '创建人';
COMMENT ON COLUMN t_data_attachment_version.create_time IS '创建时间（时间戳，毫秒）';
COMMENT ON COLUMN t_data_attachment_version.update_by IS '更新人';
COMMENT ON COLUMN t_data_attachment_version.update_time IS '更新时间（时间戳，毫秒）';


DROP TABLE IF EXISTS t_data_attachment_version_file;
CREATE TABLE t_data_attachment_version_file
(
    id                    VARCHAR(32) NOT NULL,
    attachment_version_id VARCHAR(32) NOT NULL,
    file_id               VARCHAR(32) NOT NULL,
    sort                  BIGINT NOT NULL DEFAULT 0,
    features              TEXT,
    create_by         VARCHAR(32) NOT NULL,
    create_time       BIGINT      NOT NULL,
    update_by         VARCHAR(32) NOT NULL,
    update_time       BIGINT      NOT NULL,
    CONSTRAINT pk_t_attachment_version_file PRIMARY KEY (id),
    CONSTRAINT fk_t_data_attachment_version_file_01 FOREIGN KEY (attachment_version_id) REFERENCES t_data_attachment_version (id),
    CONSTRAINT fk_t_data_attachment_version_file_02 FOREIGN KEY (file_id) REFERENCES t_data_file (id),
    CONSTRAINT uk_version_file UNIQUE (attachment_version_id, file_id)
);

CREATE INDEX idx_ver_file_01 ON t_data_attachment_version_file (attachment_version_id);
CREATE INDEX idx_ver_file_02 ON t_data_attachment_version_file (file_id);

COMMENT ON TABLE t_data_attachment_version_file IS '附件版本文件明细表';
COMMENT ON COLUMN t_data_attachment_version_file.id IS '主键ID';
COMMENT ON COLUMN t_data_attachment_version_file.attachment_version_id IS '版本ID-关联t_data_attachment_version.id';
COMMENT ON COLUMN t_data_attachment_version_file.file_id IS '文件ID-关联t_data_file.id';
COMMENT ON COLUMN t_data_attachment_version_file.sort IS '排序，sort值大的排序靠前';
COMMENT ON COLUMN t_data_attachment_version_file.features IS '扩展信息JSON';
COMMENT ON COLUMN t_data_attachment_version_file.create_by IS '创建人';
COMMENT ON COLUMN t_data_attachment_version_file.create_time IS '创建时间（时间戳，毫秒）';
COMMENT ON COLUMN t_data_attachment_version_file.update_by IS '更新人';
COMMENT ON COLUMN t_data_attachment_version_file.update_time IS '更新时间（时间戳，毫秒）';


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
    id               VARCHAR(32) NOT NULL,
    file_type        VARCHAR(16) NOT NULL,
    attachment_id    VARCHAR(32) NOT NULL DEFAULT '',
    process_status   VARCHAR(32) NOT NULL,
    business_status  VARCHAR(32) NOT NULL,
    audit_status     VARCHAR(32) NOT NULL,
    title            VARCHAR(64) NOT NULL,
    create_by        VARCHAR(32) NOT NULL,
    create_time      BIGINT      NOT NULL,
    update_by        VARCHAR(32) NOT NULL,
    update_time      BIGINT      NOT NULL,
    CONSTRAINT pk_t_data_material PRIMARY KEY (id)
);

CREATE INDEX idx_t_data_material_01 ON t_data_material (attachment_id);
CREATE INDEX idx_t_data_material_02 ON t_data_material (create_by);
CREATE INDEX idx_t_data_material_03 ON t_data_material (update_by);
CREATE INDEX idx_t_data_material_04 ON t_data_material (update_time);

COMMENT ON TABLE t_data_material IS '素材表';
COMMENT ON COLUMN t_data_material.id IS 'ID';
COMMENT ON COLUMN t_data_material.file_type IS '文件类型';
COMMENT ON COLUMN t_data_material.attachment_id IS '附件ID';
COMMENT ON COLUMN t_data_material.process_status IS '系统处理状态';
COMMENT ON COLUMN t_data_material.business_status IS '业务状态';
COMMENT ON COLUMN t_data_material.audit_status IS '审核状态';
COMMENT ON COLUMN t_data_material.title IS '标题';
COMMENT ON COLUMN t_data_material.create_by IS '创建人';
COMMENT ON COLUMN t_data_material.create_time IS '创建时间';
COMMENT ON COLUMN t_data_material.update_by IS '修改人';
COMMENT ON COLUMN t_data_material.update_time IS '更新时间';


DROP TABLE IF EXISTS t_data_material_reference;
CREATE TABLE t_data_material_reference
(
    id            VARCHAR(32) NOT NULL,
    material_id   VARCHAR(32) NOT NULL,
    attachment_id VARCHAR(32) NOT NULL,
    entity        VARCHAR(32) NOT NULL,
    entity_id     VARCHAR(32) NOT NULL,
    create_by     VARCHAR(32) NOT NULL,
    create_time   BIGINT      NOT NULL,
    update_by     VARCHAR(32) NOT NULL,
    update_time   BIGINT      NOT NULL,
    CONSTRAINT pk_t_data_material_reference PRIMARY KEY (id),
    CONSTRAINT uk_t_data_material_reference_01 UNIQUE (material_id, entity_id, entity)
);


CREATE INDEX idx_t_data_material_reference_01 ON t_data_material_reference (attachment_id);
CREATE INDEX idx_t_data_material_reference_02 ON t_data_material_reference (entity_id,entity);

COMMENT ON TABLE t_data_material_reference IS '素材引用表';
COMMENT ON COLUMN t_data_material_reference.id IS 'ID';
COMMENT ON COLUMN t_data_material_reference.material_id IS '素材ID';
COMMENT ON COLUMN t_data_material_reference.entity IS '实体';
COMMENT ON COLUMN t_data_material_reference.entity_id IS '实体Id';
COMMENT ON COLUMN t_data_material_reference.create_by IS '创建人';
COMMENT ON COLUMN t_data_material_reference.create_time IS '创建时间(时间戳)';
COMMENT ON COLUMN t_data_material_reference.update_by IS '更新人';
COMMENT ON COLUMN t_data_material_reference.update_time IS '更新时间(时间戳)';


DROP TABLE IF EXISTS t_data_download;
CREATE TABLE t_data_download
(
    id            VARCHAR(32) NOT NULL,
    status        VARCHAR(8) NOT NULL,
    module        VARCHAR(16) NOT NULL,
    entity        VARCHAR(32) NOT NULL,
    attachment_id VARCHAR(32) NOT NULL DEFAULT '',
    content       TEXT,
    features      TEXT,
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
COMMENT ON COLUMN t_data_download.module IS '模块';
COMMENT ON COLUMN t_data_download.entity IS '实体';
COMMENT ON COLUMN t_data_download.attachment_id IS '附件ID';
COMMENT ON COLUMN t_data_download.content IS '内容';
COMMENT ON COLUMN t_data_download.features IS '扩展信息JSON';
COMMENT ON COLUMN t_data_download.fail_cause IS '失败原因';
COMMENT ON COLUMN t_data_download.create_by IS '创建人';
COMMENT ON COLUMN t_data_download.create_time IS '创建时间';
COMMENT ON COLUMN t_data_download.update_by IS '修改人';
COMMENT ON COLUMN t_data_download.update_time IS '更新时间';


DROP TABLE IF EXISTS t_data_attachment_operation_log;
CREATE TABLE t_data_attachment_operation_log
(
    id                 VARCHAR(32) NOT NULL,
    attachment_id      VARCHAR(32) NOT NULL,
    version_id         VARCHAR(32),
    operation_type     VARCHAR(32) NOT NULL,
    operation_result   VARCHAR(16) NOT NULL,
    ip_address         VARCHAR(64) NOT NULL DEFAULT '',
    platform           VARCHAR(32) NOT NULL DEFAULT '',
    user_agent         VARCHAR(512) NOT NULL DEFAULT '',
    error_msg          VARCHAR(1024),
    create_by          VARCHAR(32) NOT NULL,
    create_time        BIGINT NOT NULL,
    update_by          VARCHAR(32) NOT NULL,
    update_time        BIGINT NOT NULL,
    CONSTRAINT pk_t_data_attachment_operation_log PRIMARY KEY (id)
);

-- 索引优化
CREATE INDEX idx_t_attachment_op_log_01 ON t_data_attachment_operation_log (attachment_id, create_time DESC);
CREATE INDEX idx_t_attachment_op_log_02 ON t_data_attachment_operation_log (version_id);
CREATE INDEX idx_t_attachment_op_log_03 ON t_data_attachment_operation_log (operation_type, create_time DESC);
CREATE INDEX idx_t_attachment_op_log_04 ON t_data_attachment_operation_log (operation_result, create_time DESC);
CREATE INDEX idx_t_attachment_op_log_05 ON t_data_attachment_operation_log (create_by, create_time DESC);
CREATE INDEX idx_t_attachment_op_log_06 ON t_data_attachment_operation_log (create_time DESC);
CREATE INDEX idx_t_attachment_op_log_09 ON t_data_attachment_operation_log (attachment_id, operation_type, operation_result);

COMMENT ON TABLE t_data_attachment_operation_log IS '附件操作日志表）';

COMMENT ON COLUMN t_data_attachment_operation_log.id IS '日志ID';
COMMENT ON COLUMN t_data_attachment_operation_log.attachment_id IS '附件ID';
COMMENT ON COLUMN t_data_attachment_operation_log.version_id IS '版本ID';
COMMENT ON COLUMN t_data_attachment_operation_log.operation_type IS '操作类型定';
COMMENT ON COLUMN t_data_attachment_operation_log.operation_result IS '操作结果';
COMMENT ON COLUMN t_data_attachment_operation_log.ip_address IS '客户端IP地址';
COMMENT ON COLUMN t_data_attachment_operation_log.platform IS '客户端平台';
COMMENT ON COLUMN t_data_attachment_operation_log.user_agent IS '用户代理（浏览器/客户端标识）';
COMMENT ON COLUMN t_data_attachment_operation_log.error_msg IS '错误信息';
COMMENT ON COLUMN t_data_attachment_operation_log.create_by IS '操作人';
COMMENT ON COLUMN t_data_attachment_operation_log.create_time IS '操作时间';
COMMENT ON COLUMN t_data_attachment_operation_log.update_by IS '更新人';
COMMENT ON COLUMN t_data_attachment_operation_log.update_time IS '更新时间';