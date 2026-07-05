-- =====================================================
-- 对话记录模型 - 会话表
-- =====================================================
DROP TABLE IF EXISTS t_ai_chat_conversation;
CREATE TABLE t_ai_chat_conversation
(
    id               VARCHAR(32) NOT NULL,
    session_id       VARCHAR(36) NOT NULL,
    user_id          VARCHAR(32) NOT NULL,
    title            VARCHAR(256) NOT NULL DEFAULT '',
    model            VARCHAR(64) NOT NULL DEFAULT '',
    status           VARCHAR(16) NOT NULL DEFAULT 'ACTIVE',
    metadata         TEXT,
    create_by        VARCHAR(32) NOT NULL,
    create_time      BIGINT NOT NULL,
    update_by        VARCHAR(32) NOT NULL,
    update_time      BIGINT NOT NULL,
    CONSTRAINT pk_t_ai_chat_conversation PRIMARY KEY (id),
    CONSTRAINT uk_t_ai_chat_conversation_01 UNIQUE (session_id)
);

CREATE INDEX idx_t_ai_chat_conversation_01 ON t_ai_chat_conversation (user_id);
CREATE INDEX idx_t_ai_chat_conversation_02 ON t_ai_chat_conversation (status);
CREATE INDEX idx_t_ai_chat_conversation_03 ON t_ai_chat_conversation (update_time DESC);
CREATE INDEX idx_t_ai_chat_conversation_04 ON t_ai_chat_conversation (user_id, status);

COMMENT ON TABLE t_ai_chat_conversation IS '对话会话表';
COMMENT ON COLUMN t_ai_chat_conversation.id IS '主键ID';
COMMENT ON COLUMN t_ai_chat_conversation.session_id IS '会话唯一标识(UUID)';
COMMENT ON COLUMN t_ai_chat_conversation.user_id IS '用户ID，关联t_iam_user.id';
COMMENT ON COLUMN t_ai_chat_conversation.title IS '会话标题';
COMMENT ON COLUMN t_ai_chat_conversation.model IS '使用的模型';
COMMENT ON COLUMN t_ai_chat_conversation.status IS '状态(ACTIVE:进行中,ENDED:已结束,ARCHIVED:已归档)';
COMMENT ON COLUMN t_ai_chat_conversation.message_count IS '消息总数';
COMMENT ON COLUMN t_ai_chat_conversation.total_token_used IS '累计Token消耗';
COMMENT ON COLUMN t_ai_chat_conversation.metadata IS '扩展元数据(JSON格式)';
COMMENT ON COLUMN t_ai_chat_conversation.create_by IS '创建人';
COMMENT ON COLUMN t_ai_chat_conversation.create_time IS '创建时间';
COMMENT ON COLUMN t_ai_chat_conversation.update_by IS '修改人';
COMMENT ON COLUMN t_ai_chat_conversation.update_time IS '更新时间';

-- =====================================================
-- 对话记录模型 - 消息表
-- =====================================================
DROP TABLE IF EXISTS t_ai_chat_message;
CREATE TABLE t_ai_chat_message
(
    id               VARCHAR(32) NOT NULL,
    conversation_id  VARCHAR(32) NOT NULL,
    parent_message_id VARCHAR(32) NOT NULL DEFAULT '',
    role             VARCHAR(16) NOT NULL,
    content          TEXT,
    content_type     VARCHAR(16) NOT NULL DEFAULT 'TEXT',
    token_count      INT NOT NULL DEFAULT 0,
    metadata         TEXT,
    create_by        VARCHAR(32) NOT NULL,
    create_time      BIGINT NOT NULL,
    update_by        VARCHAR(32) NOT NULL,
    update_time      BIGINT NOT NULL,
    CONSTRAINT pk_t_ai_chat_message PRIMARY KEY (id)
);

ALTER TABLE t_ai_chat_message
    ADD CONSTRAINT fk_t_ai_chat_message_01
        FOREIGN KEY (conversation_id) REFERENCES t_ai_chat_conversation(id) ON DELETE CASCADE;

CREATE INDEX idx_t_ai_chat_message_01 ON t_ai_chat_message (conversation_id);
CREATE INDEX idx_t_ai_chat_message_02 ON t_ai_chat_message (conversation_id, create_time);
CREATE INDEX idx_t_ai_chat_message_03 ON t_ai_chat_message (parent_message_id);
CREATE INDEX idx_t_ai_chat_message_04 ON t_ai_chat_message (create_time);

COMMENT ON TABLE t_ai_chat_message IS '对话消息表';
COMMENT ON COLUMN t_ai_chat_message.id IS '主键ID';
COMMENT ON COLUMN t_ai_chat_message.conversation_id IS '会话ID，关联t_ai_chat_conversation.id';
COMMENT ON COLUMN t_ai_chat_message.parent_message_id IS '父消息ID，用于树形分支对话';
COMMENT ON COLUMN t_ai_chat_message.role IS '消息角色(USER/ASSISTANT/SYSTEM/TOOL)';
COMMENT ON COLUMN t_ai_chat_message.content IS '文本内容';
COMMENT ON COLUMN t_ai_chat_message.content_type IS '内容类型(TEXT/IMAGE/VIDEO/AUDIO/FILE/MIXED)';
COMMENT ON COLUMN t_ai_chat_message.token_count IS 'Token消耗数';
COMMENT ON COLUMN t_ai_chat_message.metadata IS '扩展元数据(JSON格式，存储模型版本、思考过程等)';
COMMENT ON COLUMN t_ai_chat_message.sort IS '排序';
COMMENT ON COLUMN t_ai_chat_message.create_by IS '创建人';
COMMENT ON COLUMN t_ai_chat_message.create_time IS '创建时间';
COMMENT ON COLUMN t_ai_chat_message.update_by IS '修改人';
COMMENT ON COLUMN t_ai_chat_message.update_time IS '更新时间';

-- =====================================================
-- 对话记录模型 - 附件表
-- =====================================================
DROP TABLE IF EXISTS t_ai_chat_attachment;
CREATE TABLE t_ai_chat_attachment
(
    id              VARCHAR(32) NOT NULL,
    message_id      VARCHAR(32) NOT NULL,
    file_name       VARCHAR(256) NOT NULL,
    file_type       VARCHAR(64) NOT NULL,
    file_size       BIGINT NOT NULL DEFAULT 0,
    storage_type    VARCHAR(16) NOT NULL DEFAULT 'OSS',
    storage_path    VARCHAR(512) NOT NULL,
    storage_url     VARCHAR(512) NOT NULL DEFAULT '',
    thumbnail_url   VARCHAR(512) NOT NULL DEFAULT '',
    width           INT NOT NULL DEFAULT 0,
    height          INT NOT NULL DEFAULT 0,
    duration        INT NOT NULL DEFAULT 0,
    md5_hash        VARCHAR(64) NOT NULL DEFAULT '',
    metadata        TEXT,
    sort            BIGINT NOT NULL DEFAULT 0,
    create_by       VARCHAR(32) NOT NULL,
    create_time     BIGINT NOT NULL,
    update_by       VARCHAR(32) NOT NULL,
    update_time     BIGINT NOT NULL,
    CONSTRAINT pk_t_ai_chat_attachment PRIMARY KEY (id),
    CONSTRAINT uk_t_ai_chat_attachment_01 UNIQUE (storage_path)
);

ALTER TABLE t_ai_chat_attachment
    ADD CONSTRAINT fk_t_ai_chat_attachment_01
        FOREIGN KEY (message_id) REFERENCES t_ai_chat_message(id) ON DELETE CASCADE;

CREATE INDEX idx_t_ai_chat_attachment_01 ON t_ai_chat_attachment (message_id);
CREATE INDEX idx_t_ai_chat_attachment_02 ON t_ai_chat_attachment (file_type);
CREATE INDEX idx_t_ai_chat_attachment_03 ON t_ai_chat_attachment (md5_hash);
CREATE INDEX idx_t_ai_chat_attachment_04 ON t_ai_chat_attachment (create_time);

COMMENT ON TABLE t_ai_chat_attachment IS '对话附件表';
COMMENT ON COLUMN t_ai_chat_attachment.id IS '主键ID';
COMMENT ON COLUMN t_ai_chat_attachment.message_id IS '消息ID，关联t_ai_chat_message.id';
COMMENT ON COLUMN t_ai_chat_attachment.file_name IS '文件名';
COMMENT ON COLUMN t_ai_chat_attachment.file_type IS '文件MIME类型';
COMMENT ON COLUMN t_ai_chat_attachment.file_size IS '文件大小(字节)';
COMMENT ON COLUMN t_ai_chat_attachment.storage_type IS '存储类型(OSS/COS/S3/LOCAL)';
COMMENT ON COLUMN t_ai_chat_attachment.storage_path IS '存储路径';
COMMENT ON COLUMN t_ai_chat_attachment.storage_url IS '访问URL';
COMMENT ON COLUMN t_ai_chat_attachment.thumbnail_url IS '缩略图URL';
COMMENT ON COLUMN t_ai_chat_attachment.width IS '宽度(图片/视频)';
COMMENT ON COLUMN t_ai_chat_attachment.height IS '高度(图片/视频)';
COMMENT ON COLUMN t_ai_chat_attachment.duration IS '时长(秒，音频/视频)';
COMMENT ON COLUMN t_ai_chat_attachment.md5_hash IS '文件MD5哈希值，用于去重';
COMMENT ON COLUMN t_ai_chat_attachment.metadata IS '扩展元数据(JSON格式)';
COMMENT ON COLUMN t_ai_chat_attachment.sort IS '排序';
COMMENT ON COLUMN t_ai_chat_attachment.create_by IS '创建人';
COMMENT ON COLUMN t_ai_chat_attachment.create_time IS '创建时间';
COMMENT ON COLUMN t_ai_chat_attachment.update_by IS '修改人';
COMMENT ON COLUMN t_ai_chat_attachment.update_time IS '更新时间';

-- =====================================================
-- 对话记录模型 - 消息反馈表
-- =====================================================
DROP TABLE IF EXISTS t_ai_chat_message_feedback;
CREATE TABLE t_ai_chat_message_feedback
(
    id               VARCHAR(32) NOT NULL,
    message_id       VARCHAR(32) NOT NULL,
    user_id          VARCHAR(32) NOT NULL,
    feedback_type    VARCHAR(16) NOT NULL,
    rating           INT NOT NULL DEFAULT 0,
    comment          VARCHAR(1024) NOT NULL DEFAULT '',
    metadata         TEXT,
    create_by        VARCHAR(32) NOT NULL,
    create_time      BIGINT NOT NULL,
    update_by        VARCHAR(32) NOT NULL,
    update_time      BIGINT NOT NULL,
    CONSTRAINT pk_t_ai_chat_message_feedback PRIMARY KEY (id),
    CONSTRAINT uk_t_ai_chat_message_feedback_01 UNIQUE (message_id, user_id)
);

ALTER TABLE t_ai_chat_message_feedback
    ADD CONSTRAINT fk_t_ai_chat_message_feedback_01
        FOREIGN KEY (message_id) REFERENCES t_ai_chat_message(id) ON DELETE CASCADE;

CREATE INDEX idx_t_ai_chat_message_feedback_01 ON t_ai_chat_message_feedback (message_id);
CREATE INDEX idx_t_ai_chat_message_feedback_02 ON t_ai_chat_message_feedback (user_id);
CREATE INDEX idx_t_ai_chat_message_feedback_03 ON t_ai_chat_message_feedback (feedback_type);
CREATE INDEX idx_t_ai_chat_message_feedback_04 ON t_ai_chat_message_feedback (rating);

COMMENT ON TABLE t_ai_chat_message_feedback IS '消息反馈表';
COMMENT ON COLUMN t_ai_chat_message_feedback.id IS '主键ID';
COMMENT ON COLUMN t_ai_chat_message_feedback.message_id IS '消息ID，关联t_ai_chat_message.id';
COMMENT ON COLUMN t_ai_chat_message_feedback.user_id IS '用户ID，关联t_iam_user.id';
COMMENT ON COLUMN t_ai_chat_message_feedback.feedback_type IS '反馈类型(LIKE/DISLIKE/REPORT)';
COMMENT ON COLUMN t_ai_chat_message_feedback.rating IS '评分(1-5)';
COMMENT ON COLUMN t_ai_chat_message_feedback.comment IS '反馈意见';
COMMENT ON COLUMN t_ai_chat_message_feedback.metadata IS '扩展元数据(JSON格式)';
COMMENT ON COLUMN t_ai_chat_message_feedback.create_by IS '创建人';
COMMENT ON COLUMN t_ai_chat_message_feedback.create_time IS '创建时间';
COMMENT ON COLUMN t_ai_chat_message_feedback.update_by IS '修改人';
COMMENT ON COLUMN t_ai_chat_message_feedback.update_time IS '更新时间';

-- =====================================================
-- 对话记录模型 - Spring AI 内置记忆表（兼容保留）
-- =====================================================
DROP TABLE IF EXISTS t_ai_chat_spring_ai_memory;
CREATE TABLE t_ai_chat_spring_ai_memory
(
    conversation_id VARCHAR(36) NOT NULL,
    content         TEXT NOT NULL,
    type            VARCHAR(10) NOT NULL,
    timestamp       BIGINT NOT NULL,
    metadata        TEXT,
    create_by       VARCHAR(32) NOT NULL DEFAULT 'SYSTEM',
    create_time     BIGINT NOT NULL,
    update_by       VARCHAR(32) NOT NULL DEFAULT 'SYSTEM',
    update_time     BIGINT NOT NULL
);

CREATE INDEX idx_t_ai_chat_spring_ai_memory_01 ON t_ai_chat_spring_ai_memory (conversation_id, timestamp);

COMMENT ON TABLE t_ai_chat_spring_ai_memory IS 'Spring AI 内置对话记忆表';
COMMENT ON COLUMN t_ai_chat_spring_ai_memory.conversation_id IS '会话ID';
COMMENT ON COLUMN t_ai_chat_spring_ai_memory.content IS '消息内容';
COMMENT ON COLUMN t_ai_chat_spring_ai_memory.type IS '消息类型(USER/ASSISTANT/SYSTEM/TOOL)';
COMMENT ON COLUMN t_ai_chat_spring_ai_memory.timestamp IS '时间戳';
COMMENT ON COLUMN t_ai_chat_spring_ai_memory.metadata IS '扩展元数据';
COMMENT ON COLUMN t_ai_chat_spring_ai_memory.create_by IS '创建人';
COMMENT ON COLUMN t_ai_chat_spring_ai_memory.create_time IS '创建时间';
COMMENT ON COLUMN t_ai_chat_spring_ai_memory.update_by IS '修改人';
COMMENT ON COLUMN t_ai_chat_spring_ai_memory.update_time IS '更新时间';