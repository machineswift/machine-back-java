-- =============================================
-- 统一身份管理系统 - 完整SQL脚本
-- 设计理念：身份图谱 + 多渠道识别 + 智能合并 + 合规安全
-- 版本：2.0

-- 核心身份管理：身份实体、强/弱标识、身份关系、图谱构建
-- 多渠道管理：渠道注册、会话管理、身份识别
-- 安全合规：验证记录、风险事件、隐私同意、数据删除
-- 核心功能：匹配规则、事件流、数据质量监控
-- 辅助功能：手机号管理、缓存同步、历史归档
-- =============================================

-- 1. 核心身份模型
-- =============================================

-- 1.1 身份实体表（核心主表）
DROP TABLE IF EXISTS t_crm_identity;
CREATE TABLE t_crm_identity
(
    id                  VARCHAR(32) NOT NULL,
    identity_status     VARCHAR(16) NOT NULL DEFAULT 'ACTIVE',
    identity_level      VARCHAR(16) NOT NULL DEFAULT 'L1',
    master_identity_id  VARCHAR(32),
    identity_score      DECIMAL(5,2) NOT NULL DEFAULT 0.0,
    trust_score         DECIMAL(5,2) NOT NULL DEFAULT 0.0,
    risk_score          DECIMAL(5,2) NOT NULL DEFAULT 0.0,
    is_hot_data         BOOLEAN NOT NULL DEFAULT FALSE,
    first_seen_time     BIGINT NOT NULL,
    last_active_time    BIGINT NOT NULL,
    established_time    BIGINT,
    version             BIGINT NOT NULL DEFAULT 1,
    create_time         BIGINT NOT NULL,
    update_time         BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_identity PRIMARY KEY (id)
);

CREATE INDEX idx_crm_identity_01 ON t_crm_identity (identity_status, identity_level);
CREATE INDEX idx_crm_identity_02 ON t_crm_identity (master_identity_id) WHERE master_identity_id IS NOT NULL;
CREATE INDEX idx_crm_identity_03 ON t_crm_identity (first_seen_time);
CREATE INDEX idx_crm_identity_04 ON t_crm_identity (last_active_time);
CREATE INDEX idx_crm_identity_05 ON t_crm_identity (is_hot_data, last_active_time);

COMMENT ON TABLE t_crm_identity IS '身份实体表 - 核心主表';
COMMENT ON COLUMN t_crm_identity.id IS '主身份ID，所有业务数据关联此ID';
COMMENT ON COLUMN t_crm_identity.identity_status IS '状态：ACTIVE-活跃, MERGED-已合并, CLOSED-关闭';
COMMENT ON COLUMN t_crm_identity.identity_level IS '身份等级：L1-匿名, L2-弱实名, L3-强实名';
COMMENT ON COLUMN t_crm_identity.master_identity_id IS '主身份ID，用于合并后的身份关联';
COMMENT ON COLUMN t_crm_identity.identity_score IS '身份质量评分，0-100';
COMMENT ON COLUMN t_crm_identity.trust_score IS '信任度评分，0-100';
COMMENT ON COLUMN t_crm_identity.risk_score IS '风险评分，0-100';
COMMENT ON COLUMN t_crm_identity.is_hot_data IS '是否热点数据';
COMMENT ON COLUMN t_crm_identity.first_seen_time IS '首次出现时间';
COMMENT ON COLUMN t_crm_identity.last_active_time IS '最后活跃时间';
COMMENT ON COLUMN t_crm_identity.established_time IS '身份建立时间';
COMMENT ON COLUMN t_crm_identity.version IS '版本号，用于乐观锁';
COMMENT ON COLUMN t_crm_identity.create_time IS '创建时间';
COMMENT ON COLUMN t_crm_identity.update_time IS '更新时间';

-- 1.2 身份等级配置表
DROP TABLE IF EXISTS t_crm_identity_level_config;
CREATE TABLE t_crm_identity_level_config
(
    level_code                  VARCHAR(16) NOT NULL,
    level_name                  VARCHAR(64) NOT NULL,
    required_identifiers        JSONB NOT NULL,
    verification_requirements  JSONB NOT NULL,
    privileges                  JSONB,
    is_active                   BOOLEAN NOT NULL DEFAULT TRUE,
    create_time                 BIGINT NOT NULL,

    CONSTRAINT pk_crm_identity_level_config PRIMARY KEY (level_code)
);

COMMENT ON TABLE t_crm_identity_level_config IS '身份等级配置表 - 定义各等级的身份要求和权限';
COMMENT ON COLUMN t_crm_identity_level_config.level_code IS '等级编码';
COMMENT ON COLUMN t_crm_identity_level_config.level_name IS '等级名称';
COMMENT ON COLUMN t_crm_identity_level_config.required_identifiers IS '必需标识符列表，JSON格式';
COMMENT ON COLUMN t_crm_identity_level_config.verification_requirements IS '验证要求，JSON格式';
COMMENT ON COLUMN t_crm_identity_level_config.privileges IS '权限列表，JSON格式';
COMMENT ON COLUMN t_crm_identity_level_config.is_active IS '是否启用';
COMMENT ON COLUMN t_crm_identity_level_config.create_time IS '创建时间';

-- 预置身份等级
INSERT INTO t_crm_identity_level_config VALUES
('L0', '匿名用户', '[]', '[]', '{"basic_access": true}', true, EXTRACT(EPOCH FROM NOW()) * 1000),
('L1', '弱实名', '["PHONE"]', '["PHONE_SMS"]', '{"order": true, "basic_service": true}', true, EXTRACT(EPOCH FROM NOW()) * 1000),
('L2', '强实名', '["PHONE", "ID_CARD"]', '["PHONE_SMS", "ID_CARD_VERIFY"]', '{"financial": true, "sensitive_operation": true}', true, EXTRACT(EPOCH FROM NOW()) * 1000),
('L3', '高级认证', '["PHONE", "ID_CARD", "BANK_CARD"]', '["PHONE_SMS", "ID_CARD_VERIFY", "BANK_CARD_VERIFY"]', '{"large_transaction": true, "premium_service": true}', true, EXTRACT(EPOCH FROM NOW()) * 1000);

-- 1.3 强身份标识表（唯一性约束）
DROP TABLE IF EXISTS t_crm_strong_identity;
CREATE TABLE t_crm_strong_identity
(
    id                      VARCHAR(32) NOT NULL,
    identity_id             VARCHAR(32) NOT NULL,
    identity_type           VARCHAR(32) NOT NULL,
    identity_value          VARCHAR(128) NOT NULL,
    identity_hash           VARCHAR(64) NOT NULL,
    verification_level      VARCHAR(16) NOT NULL DEFAULT 'L0',
    verification_method     VARCHAR(32),
    verified_time           BIGINT,
    verification_evidence   JSONB,
    verification_attempts   INTEGER DEFAULT 0,
    lock_until_time         BIGINT,
    mfa_factors             JSONB,
    is_primary              BOOLEAN NOT NULL DEFAULT FALSE,
    is_public               BOOLEAN NOT NULL DEFAULT FALSE,
    status                  VARCHAR(16) NOT NULL DEFAULT 'ACTIVE',
    trust_score             DECIMAL(3,2) NOT NULL DEFAULT 0.0,
    risk_level              VARCHAR(16) NOT NULL DEFAULT 'LOW',
    version                 BIGINT NOT NULL DEFAULT 1,
    create_time             BIGINT NOT NULL,
    update_time             BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_strong_identity PRIMARY KEY (id),
    CONSTRAINT uk_crm_strong_identity_unique UNIQUE (identity_type, identity_value)
        WHERE status IN ('ACTIVE', 'VERIFIED')
);

CREATE INDEX idx_crm_strong_identity_01 ON t_crm_strong_identity (identity_id);
CREATE INDEX idx_crm_strong_identity_02 ON t_crm_strong_identity (identity_type, identity_value);
CREATE INDEX idx_crm_strong_identity_03 ON t_crm_strong_identity (status, verification_level);
CREATE INDEX idx_crm_strong_identity_04 ON t_crm_strong_identity (identity_hash);
CREATE INDEX idx_crm_strong_identity_05 ON t_crm_strong_identity (lock_until_time) WHERE lock_until_time IS NOT NULL;

COMMENT ON TABLE t_crm_strong_identity IS '强身份标识表 - 具有唯一性约束的标识';
COMMENT ON COLUMN t_crm_strong_identity.id IS 'ID';
COMMENT ON COLUMN t_crm_strong_identity.identity_id IS '身份ID';
COMMENT ON COLUMN t_crm_strong_identity.identity_type IS '标识类型：PHONE-手机, EMAIL-邮箱, ID_CARD-身份证, WECHAT_UNIONID-微信, ALIPAY_USERID-支付宝';
COMMENT ON COLUMN t_crm_strong_identity.identity_value IS '标识值';
COMMENT ON COLUMN t_crm_strong_identity.identity_hash IS '标识哈希值';
COMMENT ON COLUMN t_crm_strong_identity.verification_level IS '验证等级：L0-未验, L1-弱验, L2-强验';
COMMENT ON COLUMN t_crm_strong_identity.verification_method IS '验证方法';
COMMENT ON COLUMN t_crm_strong_identity.verified_time IS '验证时间';
COMMENT ON COLUMN t_crm_strong_identity.verification_evidence IS '验证证据，JSON格式';
COMMENT ON COLUMN t_crm_strong_identity.verification_attempts IS '验证尝试次数';
COMMENT ON COLUMN t_crm_strong_identity.lock_until_time IS '锁定到期时间';
COMMENT ON COLUMN t_crm_strong_identity.mfa_factors IS '多因素认证因子，JSON格式';
COMMENT ON COLUMN t_crm_strong_identity.is_primary IS '是否为主标识';
COMMENT ON COLUMN t_crm_strong_identity.is_public IS '是否公开';
COMMENT ON COLUMN t_crm_strong_identity.status IS '状态：ACTIVE-活跃, INACTIVE-停用, LOCKED-锁定';
COMMENT ON COLUMN t_crm_strong_identity.trust_score IS '信任度评分，0-1之间';
COMMENT ON COLUMN t_crm_strong_identity.risk_level IS '风险等级：LOW-低, MEDIUM-中, HIGH-高';
COMMENT ON COLUMN t_crm_strong_identity.version IS '版本号，用于乐观锁';
COMMENT ON COLUMN t_crm_strong_identity.create_time IS '创建时间';
COMMENT ON COLUMN t_crm_strong_identity.update_time IS '更新时间';

-- 1.4 弱身份标识表（辅助识别）
DROP TABLE IF EXISTS t_crm_weak_identity;
CREATE TABLE t_crm_weak_identity
(
    id                  VARCHAR(32) NOT NULL,
    identity_id         VARCHAR(32) NOT NULL,
    identity_type       VARCHAR(32) NOT NULL,
    identity_value      TEXT NOT NULL,
    identity_hash       VARCHAR(64) NOT NULL,
    confidence_score    DECIMAL(3,2) NOT NULL DEFAULT 0.0,
    match_count         INTEGER NOT NULL DEFAULT 0,
    last_match_time     BIGINT NOT NULL,
    ttl_days            INTEGER NOT NULL DEFAULT 90,
    expires_time        BIGINT NOT NULL,
    metadata            JSONB,
    create_time         BIGINT NOT NULL,
    update_time         BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_weak_identity PRIMARY KEY (id)
);

CREATE INDEX idx_crm_weak_identity_01 ON t_crm_weak_identity (identity_type, identity_hash);
CREATE INDEX idx_crm_weak_identity_02 ON t_crm_weak_identity (identity_id);
CREATE INDEX idx_crm_weak_identity_03 ON t_crm_weak_identity (expires_time);
CREATE INDEX idx_crm_weak_identity_04 ON t_crm_weak_identity (confidence_score);

COMMENT ON TABLE t_crm_weak_identity IS '弱身份标识表 - 用于辅助识别，无唯一约束';
COMMENT ON COLUMN t_crm_weak_identity.id IS 'ID';
COMMENT ON COLUMN t_crm_weak_identity.identity_id IS '身份ID';
COMMENT ON COLUMN t_crm_weak_identity.identity_type IS '标识类型：DEVICE_ID-设备ID, COOKIE-浏览器Cookie, IP_SEGMENT-IP段, BROWSER_FINGERPRINT-浏览器指纹';
COMMENT ON COLUMN t_crm_weak_identity.identity_value IS '标识值';
COMMENT ON COLUMN t_crm_weak_identity.identity_hash IS '标识哈希值';
COMMENT ON COLUMN t_crm_weak_identity.confidence_score IS '置信度分数，0-1之间';
COMMENT ON COLUMN t_crm_weak_identity.match_count IS '匹配次数';
COMMENT ON COLUMN t_crm_weak_identity.last_match_time IS '最后匹配时间';
COMMENT ON COLUMN t_crm_weak_identity.ttl_days IS '生存时间，单位：天';
COMMENT ON COLUMN t_crm_weak_identity.expires_time IS '过期时间';
COMMENT ON COLUMN t_crm_weak_identity.metadata IS '元数据，JSON格式';
COMMENT ON COLUMN t_crm_weak_identity.create_time IS '创建时间';
COMMENT ON COLUMN t_crm_weak_identity.update_time IS '更新时间';

-- 2. 身份关系与图谱
-- =============================================

-- 2.1 身份关联关系表
DROP TABLE IF EXISTS t_crm_identity_relation;
CREATE TABLE t_crm_identity_relation
(
    id                  VARCHAR(32) NOT NULL,
    from_identity_id    VARCHAR(32) NOT NULL,
    to_identity_id      VARCHAR(32) NOT NULL,
    relation_type       VARCHAR(32) NOT NULL,
    relation_strength   DECIMAL(3,2) NOT NULL DEFAULT 0.0,
    evidence_type       VARCHAR(32) NOT NULL,
    confidence_score    DECIMAL(3,2) NOT NULL DEFAULT 0.0,
    evidence_data       JSONB NOT NULL,
    relation_status     VARCHAR(32) NOT NULL DEFAULT 'ACTIVE',
    reviewed_by         VARCHAR(32),
    reviewed_time       BIGINT,
    first_observed_time BIGINT NOT NULL,
    last_observed_time  BIGINT NOT NULL,
    expires_time        BIGINT,
    version             BIGINT NOT NULL DEFAULT 1,
    create_time         BIGINT NOT NULL,
    update_time         BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_identity_relation PRIMARY KEY (id),
    CONSTRAINT uk_crm_identity_relation UNIQUE (from_identity_id, to_identity_id, relation_type)
);

CREATE INDEX idx_crm_identity_relation_01 ON t_crm_identity_relation (from_identity_id, relation_type);
CREATE INDEX idx_crm_identity_relation_02 ON t_crm_identity_relation (to_identity_id, relation_type);
CREATE INDEX idx_crm_identity_relation_03 ON t_crm_identity_relation (relation_status, confidence_score);
CREATE INDEX idx_crm_identity_relation_04 ON t_crm_identity_relation (last_observed_time);

COMMENT ON TABLE t_crm_identity_relation IS '身份关联关系表 - 构建身份图谱';
COMMENT ON COLUMN t_crm_identity_relation.id IS 'ID';
COMMENT ON COLUMN t_crm_identity_relation.from_identity_id IS '源身份ID';
COMMENT ON COLUMN t_crm_identity_relation.to_identity_id IS '目标身份ID';
COMMENT ON COLUMN t_crm_identity_relation.relation_type IS '关系类型：SAME_PERSON-同一人, HOUSEHOLD-家庭成员, COLLEAGUE-同事, DEVICE_SHARING-设备共享';
COMMENT ON COLUMN t_crm_identity_relation.relation_strength IS '关系强度，0-1之间';
COMMENT ON COLUMN t_crm_identity_relation.evidence_type IS '证据类型';
COMMENT ON COLUMN t_crm_identity_relation.confidence_score IS '置信度分数，0-1之间';
COMMENT ON COLUMN t_crm_identity_relation.evidence_data IS '证据数据，JSON格式';
COMMENT ON COLUMN t_crm_identity_relation.relation_status IS '关系状态：ACTIVE-活跃, INACTIVE-停用, SUSPENDED-暂停';
COMMENT ON COLUMN t_crm_identity_relation.reviewed_by IS '审核人ID';
COMMENT ON COLUMN t_crm_identity_relation.reviewed_time IS '审核时间';
COMMENT ON COLUMN t_crm_identity_relation.first_observed_time IS '首次观察到的时间';
COMMENT ON COLUMN t_crm_identity_relation.last_observed_time IS '最后观察到的时间';
COMMENT ON COLUMN t_crm_identity_relation.expires_time IS '过期时间';
COMMENT ON COLUMN t_crm_identity_relation.version IS '版本号，用于乐观锁';
COMMENT ON COLUMN t_crm_identity_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_crm_identity_relation.update_time IS '更新时间';

-- 2.2 身份合并历史表
DROP TABLE IF EXISTS t_crm_identity_merge_history;
CREATE TABLE t_crm_identity_merge_history
(
    id                  VARCHAR(32) NOT NULL,
    operation_id        VARCHAR(32) NOT NULL,
    primary_identity_id VARCHAR(32) NOT NULL,
    merged_identity_id  VARCHAR(32) NOT NULL,
    merge_strategy      VARCHAR(32) NOT NULL,
    merge_reason        VARCHAR(256) NOT NULL,
    confidence_score    DECIMAL(3,2) NOT NULL DEFAULT 0.0,
    data_merge_plan     JSONB NOT NULL,
    merged_attributes   JSONB NOT NULL,
    conflict_resolution JSONB NOT NULL,
    operator_type       VARCHAR(32) NOT NULL,
    operator_id         VARCHAR(32) NOT NULL,
    operation_ip        VARCHAR(64),
    can_rollback        BOOLEAN NOT NULL DEFAULT TRUE,
    rollback_deadline   BIGINT NOT NULL,
    create_time         BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_identity_merge_history PRIMARY KEY (id),
    CONSTRAINT uk_crm_identity_merge_history UNIQUE (merged_identity_id)
);

CREATE INDEX idx_crm_identity_merge_01 ON t_crm_identity_merge_history (primary_identity_id);
CREATE INDEX idx_crm_identity_merge_02 ON t_crm_identity_merge_history (operation_id);
CREATE INDEX idx_crm_identity_merge_03 ON t_crm_identity_merge_history (merge_strategy, create_time);

COMMENT ON TABLE t_crm_identity_merge_history IS '身份合并历史表 - 记录身份合并操作';
COMMENT ON COLUMN t_crm_identity_merge_history.id IS 'ID';
COMMENT ON COLUMN t_crm_identity_merge_history.operation_id IS '操作ID';
COMMENT ON COLUMN t_crm_identity_merge_history.primary_identity_id IS '主身份ID';
COMMENT ON COLUMN t_crm_identity_merge_history.merged_identity_id IS '被合并的身份ID';
COMMENT ON COLUMN t_crm_identity_merge_history.merge_strategy IS '合并策略：AUTO_BY_PHONE-手机自动合并, AUTO_BY_WECHAT-微信自动合并, MANUAL-手动合并, AI_RECOMMEND-AI推荐合并';
COMMENT ON COLUMN t_crm_identity_merge_history.merge_reason IS '合并原因';
COMMENT ON COLUMN t_crm_identity_merge_history.confidence_score IS '置信度分数，0-1之间';
COMMENT ON COLUMN t_crm_identity_merge_history.data_merge_plan IS '数据合并计划，JSON格式';
COMMENT ON COLUMN t_crm_identity_merge_history.merged_attributes IS '合并的属性，JSON格式';
COMMENT ON COLUMN t_crm_identity_merge_history.conflict_resolution IS '冲突解决方式，JSON格式';
COMMENT ON COLUMN t_crm_identity_merge_history.operator_type IS '操作者类型：SYSTEM-系统, USER-用户, ADMIN-管理员';
COMMENT ON COLUMN t_crm_identity_merge_history.operator_id IS '操作者ID';
COMMENT ON COLUMN t_crm_identity_merge_history.operation_ip IS '操作IP地址';
COMMENT ON COLUMN t_crm_identity_merge_history.can_rollback IS '是否可回滚';
COMMENT ON COLUMN t_crm_identity_merge_history.rollback_deadline IS '回滚截止时间';
COMMENT ON COLUMN t_crm_identity_merge_history.create_time IS '创建时间';

-- 3. 多渠道身份管理
-- =============================================

-- 3.1 渠道注册记录表
DROP TABLE IF EXISTS t_crm_channel_registration;
CREATE TABLE t_crm_channel_registration
(
    id                  VARCHAR(32) NOT NULL,
    identity_id         VARCHAR(32) NOT NULL,
    channel_type        VARCHAR(32) NOT NULL,
    channel_app_id      VARCHAR(64) NOT NULL,
    channel_user_id     VARCHAR(128),
    register_device      JSONB NOT NULL,
    register_network     JSONB,
    register_geography  JSONB,
    register_referrer    VARCHAR(256),
    channel_identity     JSONB,
    channel_session      JSONB,
    is_active            BOOLEAN NOT NULL DEFAULT TRUE,
    last_login_time      BIGINT,
    login_count          INTEGER NOT NULL DEFAULT 0,
    create_time          BIGINT NOT NULL,
    update_time          BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_channel_registration PRIMARY KEY (id),
    CONSTRAINT uk_crm_channel_registration UNIQUE (channel_type, channel_app_id, channel_user_id)
);

CREATE INDEX idx_crm_channel_registration_01 ON t_crm_channel_registration (identity_id);
CREATE INDEX idx_crm_channel_registration_02 ON t_crm_channel_registration (channel_type, is_active);
CREATE INDEX idx_crm_channel_registration_03 ON t_crm_channel_registration (last_login_time);

COMMENT ON TABLE t_crm_channel_registration IS '渠道注册记录表 - 管理多渠道身份绑定';
COMMENT ON COLUMN t_crm_channel_registration.id IS 'ID';
COMMENT ON COLUMN t_crm_channel_registration.identity_id IS '身份ID';
COMMENT ON COLUMN t_crm_channel_registration.channel_type IS '渠道类型：WECHAT_MINI-微信小程序, ALIPAY_MINI-支付宝小程序, MOBILE_APP-手机APP, WEB-网站';
COMMENT ON COLUMN t_crm_channel_registration.channel_app_id IS '渠道应用ID';
COMMENT ON COLUMN t_crm_channel_registration.channel_user_id IS '渠道用户ID';
COMMENT ON COLUMN t_crm_channel_registration.register_device IS '注册设备信息，JSON格式';
COMMENT ON COLUMN t_crm_channel_registration.register_network IS '注册网络信息，JSON格式';
COMMENT ON COLUMN t_crm_channel_registration.register_geography IS '注册地理位置信息，JSON格式';
COMMENT ON COLUMN t_crm_channel_registration.register_referrer IS '注册来源';
COMMENT ON COLUMN t_crm_channel_registration.channel_identity IS '渠道特定身份信息，JSON格式';
COMMENT ON COLUMN t_crm_channel_registration.channel_session IS '渠道会话信息，JSON格式';
COMMENT ON COLUMN t_crm_channel_registration.is_active IS '是否活跃';
COMMENT ON COLUMN t_crm_channel_registration.last_login_time IS '最后登录时间';
COMMENT ON COLUMN t_crm_channel_registration.login_count IS '登录次数';
COMMENT ON COLUMN t_crm_channel_registration.create_time IS '创建时间';
COMMENT ON COLUMN t_crm_channel_registration.update_time IS '更新时间';

-- 3.2 统一会话管理表
DROP TABLE IF EXISTS t_crm_unified_session;
CREATE TABLE t_crm_unified_session
(
    id                  VARCHAR(32) NOT NULL,
    identity_id         VARCHAR(32) NOT NULL,
    session_type        VARCHAR(32) NOT NULL,
    session_token       VARCHAR(128) NOT NULL,
    parent_session_id   VARCHAR(32),
    security_level      VARCHAR(16) NOT NULL DEFAULT 'STANDARD',
    mfa_verified        BOOLEAN NOT NULL DEFAULT FALSE,
    device_trust_level  VARCHAR(16) NOT NULL DEFAULT 'UNKNOWN',
    channels            JSONB NOT NULL,
    last_activity_time  BIGINT NOT NULL,
    expires_time        BIGINT NOT NULL,
    create_time         BIGINT NOT NULL,

    CONSTRAINT pk_crm_unified_session PRIMARY KEY (id),
    CONSTRAINT uk_crm_unified_session_token UNIQUE (session_token)
);

CREATE INDEX idx_crm_unified_session_01 ON t_crm_unified_session (identity_id, expires_time);
CREATE INDEX idx_crm_unified_session_02 ON t_crm_unified_session (last_activity_time);
CREATE INDEX idx_crm_unified_session_03 ON t_crm_unified_session (parent_session_id) WHERE parent_session_id IS NOT NULL;

COMMENT ON TABLE t_crm_unified_session IS '统一会话管理表 - 跨渠道会话管理';
COMMENT ON COLUMN t_crm_unified_session.id IS 'ID';
COMMENT ON COLUMN t_crm_unified_session.identity_id IS '身份ID';
COMMENT ON COLUMN t_crm_unified_session.session_type IS '会话类型：WEB, MOBILE, CROSS_PLATFORM';
COMMENT ON COLUMN t_crm_unified_session.session_token IS '会话令牌';
COMMENT ON COLUMN t_crm_unified_session.parent_session_id IS '父会话ID';
COMMENT ON COLUMN t_crm_unified_session.security_level IS '安全等级：STANDARD-标准, HIGH-高, EXTREME-极高';
COMMENT ON COLUMN t_crm_unified_session.mfa_verified IS '是否通过多因素认证';
COMMENT ON COLUMN t_crm_unified_session.device_trust_level IS '设备信任等级：UNKNOWN-未知, TRUSTED-信任, UNTRUSTED-不信任';
COMMENT ON COLUMN t_crm_unified_session.channels IS '渠道列表，JSON格式';
COMMENT ON COLUMN t_crm_unified_session.last_activity_time IS '最后活动时间';
COMMENT ON COLUMN t_crm_unified_session.expires_time IS '过期时间';
COMMENT ON COLUMN t_crm_unified_session.create_time IS '创建时间';

-- 3.3 身份识别会话表
DROP TABLE IF EXISTS t_crm_identity_session;
CREATE TABLE t_crm_identity_session
(
    id                      VARCHAR(32) NOT NULL,
    session_token           VARCHAR(64) NOT NULL,
    identity_id             VARCHAR(32),
    session_context         JSONB NOT NULL,
    device_fingerprint      VARCHAR(64) NOT NULL,
    environment_fingerprint VARCHAR(64) NOT NULL,
    identification_status   VARCHAR(32) NOT NULL DEFAULT 'PENDING',
    candidate_identities    JSONB,
    selected_identity_id     VARCHAR(32),
    identification_evidence JSONB NOT NULL,
    confidence_score        DECIMAL(3,2) NOT NULL DEFAULT 0.0,
    session_start_time      BIGINT NOT NULL,
    session_end_time        BIGINT,
    ttl_seconds             INTEGER NOT NULL DEFAULT 86400,
    create_time             BIGINT NOT NULL,
    update_time             BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_identity_session PRIMARY KEY (id),
    CONSTRAINT uk_crm_identity_session UNIQUE (session_token)
);

CREATE INDEX idx_crm_identity_session_01 ON t_crm_identity_session (identity_id);
CREATE INDEX idx_crm_identity_session_02 ON t_crm_identity_session (device_fingerprint);
CREATE INDEX idx_crm_identity_session_03 ON t_crm_identity_session (session_start_time);
CREATE INDEX idx_crm_identity_session_04 ON t_crm_identity_session (identification_status);

COMMENT ON TABLE t_crm_identity_session IS '身份识别会话表 - 管理实时身份识别会话';
COMMENT ON COLUMN t_crm_identity_session.id IS 'ID';
COMMENT ON COLUMN t_crm_identity_session.session_token IS '会话令牌';
COMMENT ON COLUMN t_crm_identity_session.identity_id IS '身份ID';
COMMENT ON COLUMN t_crm_identity_session.session_context IS '会话上下文，JSON格式';
COMMENT ON COLUMN t_crm_identity_session.device_fingerprint IS '设备指纹';
COMMENT ON COLUMN t_crm_identity_session.environment_fingerprint IS '环境指纹';
COMMENT ON COLUMN t_crm_identity_session.identification_status IS '识别状态：PENDING-待识别, IDENTIFIED-已识别, AMBIGUOUS-模糊匹配, FAILED-识别失败';
COMMENT ON COLUMN t_crm_identity_session.candidate_identities IS '候选身份列表，JSON格式';
COMMENT ON COLUMN t_crm_identity_session.selected_identity_id IS '选中的身份ID';
COMMENT ON COLUMN t_crm_identity_session.identification_evidence IS '识别证据，JSON格式';
COMMENT ON COLUMN t_crm_identity_session.confidence_score IS '置信度分数，0-1之间';
COMMENT ON COLUMN t_crm_identity_session.session_start_time IS '会话开始时间';
COMMENT ON COLUMN t_crm_identity_session.session_end_time IS '会话结束时间';
COMMENT ON COLUMN t_crm_identity_session.ttl_seconds IS '生存时间，单位：秒';
COMMENT ON COLUMN t_crm_identity_session.create_time IS '创建时间';
COMMENT ON COLUMN t_crm_identity_session.update_time IS '更新时间';

-- 3.4 身份生命周期表
DROP TABLE IF EXISTS t_crm_identity_lifecycle;
CREATE TABLE t_crm_identity_lifecycle
(
    identity_id             VARCHAR(32) NOT NULL,
    lifecycle_stage         VARCHAR(32) NOT NULL,
    stage_start_time        BIGINT NOT NULL,
    stage_duration_days     INTEGER,
    stage_reason            VARCHAR(128),
    predicted_next_stage    VARCHAR(32),
    prediction_confidence   DECIMAL(3,2),
    update_time             BIGINT NOT NULL,

    CONSTRAINT pk_crm_identity_lifecycle PRIMARY KEY (identity_id)
);

CREATE INDEX idx_crm_identity_lifecycle_01 ON t_crm_identity_lifecycle (lifecycle_stage, stage_start_time);
CREATE INDEX idx_crm_identity_lifecycle_02 ON t_crm_identity_lifecycle (predicted_next_stage, prediction_confidence);

COMMENT ON TABLE t_crm_identity_lifecycle IS '身份生命周期表 - 用户生命周期阶段管理';
COMMENT ON COLUMN t_crm_identity_lifecycle.identity_id IS '身份ID';
COMMENT ON COLUMN t_crm_identity_lifecycle.lifecycle_stage IS '生命周期阶段：NEW-新用户, ACTIVIVE-活跃, AT_RISK-风险, CHURNED-流失, RECOVERED-召回';
COMMENT ON COLUMN t_crm_identity_lifecycle.stage_start_time IS '阶段开始时间';
COMMENT ON COLUMN t_crm_identity_lifecycle.stage_duration_days IS '阶段持续天数';
COMMENT ON COLUMN t_crm_identity_lifecycle.stage_reason IS '阶段原因';
COMMENT ON COLUMN t_crm_identity_lifecycle.predicted_next_stage IS '预测的下一个阶段';
COMMENT ON COLUMN t_crm_identity_lifecycle.prediction_confidence IS '预测置信度，0-1之间';
COMMENT ON COLUMN t_crm_identity_lifecycle.update_time IS '更新时间';


-- 5. 安全与合规
-- =============================================

-- 5.1 身份验证记录表
DROP TABLE IF EXISTS t_crm_identity_verification_log;
CREATE TABLE t_crm_identity_verification_log
(
    id                  VARCHAR(32) NOT NULL,
    identity_id         VARCHAR(32) NOT NULL,
    identity_type       VARCHAR(32) NOT NULL,
    verification_method VARCHAR(32) NOT NULL,
    verification_result VARCHAR(32) NOT NULL,
    client_info         JSONB NOT NULL,
    risk_score          DECIMAL(3,2),
    ip_address          VARCHAR(64),
    user_agent          TEXT,
    create_time         BIGINT NOT NULL,

    CONSTRAINT pk_crm_identity_verification_log PRIMARY KEY (id)
);

CREATE INDEX idx_crm_verification_log_01 ON t_crm_identity_verification_log (identity_id, create_time);
CREATE INDEX idx_crm_verification_log_02 ON t_crm_identity_verification_log (identity_type, verification_result);
CREATE INDEX idx_crm_verification_log_03 ON t_crm_identity_verification_log (risk_score, create_time);

COMMENT ON TABLE t_crm_identity_verification_log IS '身份验证记录表 - 记录所有身份验证操作';
COMMENT ON COLUMN t_crm_identity_verification_log.id IS 'ID';
COMMENT ON COLUMN t_crm_identity_verification_log.identity_id IS '身份ID';
COMMENT ON COLUMN t_crm_identity_verification_log.identity_type IS '身份类型';
COMMENT ON COLUMN t_crm_identity_verification_log.verification_method IS '验证方法';
COMMENT ON COLUMN t_crm_identity_verification_log.verification_result IS '验证结果';
COMMENT ON COLUMN t_crm_identity_verification_log.client_info IS '客户端信息，JSON格式';
COMMENT ON COLUMN t_crm_identity_verification_log.risk_score IS '风险评分，0-1之间';
COMMENT ON COLUMN t_crm_identity_verification_log.ip_address IS 'IP地址';
COMMENT ON COLUMN t_crm_identity_verification_log.user_agent IS '用户代理';
COMMENT ON COLUMN t_crm_identity_verification_log.create_time IS '创建时间';

-- 5.2 身份风险事件表
DROP TABLE IF EXISTS t_crm_identity_risk_event;
CREATE TABLE t_crm_identity_risk_event
(
    id              VARCHAR(32) NOT NULL,
    identity_id     VARCHAR(32),
    event_type      VARCHAR(64) NOT NULL,
    risk_level      VARCHAR(16) NOT NULL,
    risk_score      DECIMAL(3,2) NOT NULL,
    trigger_source  VARCHAR(64) NOT NULL,
    event_data      JSONB NOT NULL,
    handled         BOOLEAN NOT NULL DEFAULT FALSE,
    handler_id      VARCHAR(32),
    handle_time     BIGINT,
    handle_action   VARCHAR(32),
    create_time     BIGINT NOT NULL,

    CONSTRAINT pk_crm_identity_risk_event PRIMARY KEY (id)
);

CREATE INDEX idx_crm_risk_event_01 ON t_crm_identity_risk_event (identity_id, create_time);
CREATE INDEX idx_crm_risk_event_02 ON t_crm_identity_risk_event (risk_level, handled);
CREATE INDEX idx_crm_risk_event_03 ON t_crm_identity_risk_event (event_type, create_time);
CREATE INDEX idx_crm_risk_event_04 ON t_crm_identity_risk_event (trigger_source, create_time);

COMMENT ON TABLE t_crm_identity_risk_event IS '身份风险事件表 - 记录身份相关风险事件';
COMMENT ON COLUMN t_crm_identity_risk_event.id IS 'ID';
COMMENT ON COLUMN t_crm_identity_risk_event.identity_id IS '身份ID';
COMMENT ON COLUMN t_crm_identity_risk_event.event_type IS '事件类型：SUSPICIOUS_LOGIN-可疑登录, IDENTITY_CONFLICT-身份冲突, FRAUD_ATTEMPT-欺诈尝试';
COMMENT ON COLUMN t_crm_identity_risk_event.risk_level IS '风险等级：LOW-低, MEDIUM-中, HIGH-高, CRITICAL-严重';
COMMENT ON COLUMN t_crm_identity_risk_event.risk_score IS '风险评分，0-1之间';
COMMENT ON COLUMN t_crm_identity_risk_event.trigger_source IS '触发来源';
COMMENT ON COLUMN t_crm_identity_risk_event.event_data IS '事件数据，JSON格式';
COMMENT ON COLUMN t_crm_identity_risk_event.handled IS '是否已处理';
COMMENT ON COLUMN t_crm_identity_risk_event.handler_id IS '处理人ID';
COMMENT ON COLUMN t_crm_identity_risk_event.handle_time IS '处理时间';
COMMENT ON COLUMN t_crm_identity_risk_event.handle_action IS '处理动作';
COMMENT ON COLUMN t_crm_identity_risk_event.create_time IS '创建时间';

-- 5.3 数据隐私同意表
DROP TABLE IF EXISTS t_crm_privacy_consent;
CREATE TABLE t_crm_privacy_consent
(
    id              VARCHAR(32) NOT NULL,
    identity_id     VARCHAR(32) NOT NULL,
    consent_type    VARCHAR(64) NOT NULL,
    consent_version VARCHAR(32) NOT NULL,
    granted         BOOLEAN NOT NULL,
    granted_time    BIGINT,
    revoked_time    BIGINT,
    source          VARCHAR(32) NOT NULL,
    evidence        JSONB,

    CONSTRAINT pk_crm_privacy_consent PRIMARY KEY (id)
);

CREATE INDEX idx_crm_privacy_consent_01 ON t_crm_privacy_consent (identity_id, consent_type);
CREATE INDEX idx_crm_privacy_consent_02 ON t_crm_privacy_consent (granted, consent_type);
CREATE INDEX idx_crm_privacy_consent_03 ON t_crm_privacy_consent (granted_time, consent_type);

COMMENT ON TABLE t_crm_privacy_consent IS '数据隐私同意表 - GDPR合规支持';
COMMENT ON COLUMN t_crm_privacy_consent.id IS 'ID';
COMMENT ON COLUMN t_crm_privacy_consent.identity_id IS '身份ID';
COMMENT ON COLUMN t_crm_privacy_consent.consent_type IS '同意类型：PRIVACY_POLICY-隐私政策, MARKETING-营销, DATA_SHARING-数据共享';
COMMENT ON COLUMN t_crm_privacy_consent.consent_version IS '同意版本';
COMMENT ON COLUMN t_crm_privacy_consent.granted IS '是否同意';
COMMENT ON COLUMN t_crm_privacy_consent.granted_time IS '同意时间';
COMMENT ON COLUMN t_crm_privacy_consent.revoked_time IS '撤销时间';
COMMENT ON COLUMN t_crm_privacy_consent.source IS '来源';
COMMENT ON COLUMN t_crm_privacy_consent.evidence IS '证据，JSON格式';

-- 5.4 数据删除请求表
DROP TABLE IF EXISTS t_crm_data_deletion_request;
CREATE TABLE t_crm_data_deletion_request
(
    id                  VARCHAR(32) NOT NULL,
    identity_id         VARCHAR(32) NOT NULL,
    request_type        VARCHAR(32) NOT NULL,
    request_reason      VARCHAR(256),
    status              VARCHAR(32) NOT NULL DEFAULT 'PENDING',
    requested_by        VARCHAR(32) NOT NULL,
    requested_time      BIGINT NOT NULL,
    completed_time      BIGINT,
    completion_evidence JSONB,

    CONSTRAINT pk_crm_data_deletion_request PRIMARY KEY (id)
);

CREATE INDEX idx_crm_deletion_request_01 ON t_crm_data_deletion_request (identity_id, status);
CREATE INDEX idx_crm_deletion_request_02 ON t_crm_data_deletion_request (request_type, requested_time);
CREATE INDEX idx_crm_deletion_request_03 ON t_crm_data_deletion_request (status, requested_time);

COMMENT ON TABLE t_crm_data_deletion_request IS '数据删除请求表 - 支持GDPR删除权';
COMMENT ON COLUMN t_crm_data_deletion_request.id IS 'ID';
COMMENT ON COLUMN t_crm_data_deletion_request.identity_id IS '身份ID';
COMMENT ON COLUMN t_crm_data_deletion_request.request_type IS '请求类型';
COMMENT ON COLUMN t_crm_data_deletion_request.request_reason IS '请求原因';
COMMENT ON COLUMN t_crm_data_deletion_request.status IS '状态：PENDING-待处理, PROCESSING-处理中, COMPLETED-已完成, REJECTED-已拒绝';
COMMENT ON COLUMN t_crm_data_deletion_request.requested_by IS '请求人ID';
COMMENT ON COLUMN t_crm_data_deletion_request.requested_time IS '请求时间';
COMMENT ON COLUMN t_crm_data_deletion_request.completed_time IS '完成时间';
COMMENT ON COLUMN t_crm_data_deletion_request.completion_evidence IS '完成证据，JSON格式';

-- 6. 核心功能支持
-- =============================================

-- 6.1 身份匹配规则表
DROP TABLE IF EXISTS t_crm_identity_match_rule;
CREATE TABLE t_crm_identity_match_rule
(
    id                  VARCHAR(32) NOT NULL,
    rule_name           VARCHAR(64) NOT NULL,
    rule_type           VARCHAR(32) NOT NULL,
    match_conditions    JSONB NOT NULL,
    confidence_weight   DECIMAL(3,2) NOT NULL,
    is_active           BOOLEAN NOT NULL DEFAULT TRUE,
    priority            INTEGER NOT NULL DEFAULT 0,
    description         VARCHAR(256),
    create_time         BIGINT NOT NULL,
    update_time         BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_identity_match_rule PRIMARY KEY (id)
);

CREATE INDEX idx_crm_match_rule_01 ON t_crm_identity_match_rule (is_active, priority);
CREATE INDEX idx_crm_match_rule_02 ON t_crm_identity_match_rule (rule_type);

COMMENT ON TABLE t_crm_identity_match_rule IS '身份匹配规则表 - 定义身份匹配规则';
COMMENT ON COLUMN t_crm_identity_match_rule.id IS 'ID';
COMMENT ON COLUMN t_crm_identity_match_rule.rule_name IS '规则名称';
COMMENT ON COLUMN t_crm_identity_match_rule.rule_type IS '规则类型：EXACT-精确匹配, FUZZY-模糊匹配, BEHAVIOR-行为匹配';
COMMENT ON COLUMN t_crm_identity_match_rule.match_conditions IS '匹配条件，JSON格式';
COMMENT ON COLUMN t_crm_identity_match_rule.confidence_weight IS '置信度权重，0-1之间';
COMMENT ON COLUMN t_crm_identity_match_rule.is_active IS '是否启用';
COMMENT ON COLUMN t_crm_identity_match_rule.priority IS '优先级，数值越大优先级越高';
COMMENT ON COLUMN t_crm_identity_match_rule.description IS '描述';
COMMENT ON COLUMN t_crm_identity_match_rule.create_time IS '创建时间';
COMMENT ON COLUMN t_crm_identity_match_rule.update_time IS '更新时间';

-- 预置匹配规则
INSERT INTO t_crm_identity_match_rule (id, rule_name, rule_type, match_conditions, confidence_weight, priority, is_active, description) VALUES
('rule_001', '手机号精确匹配', 'EXACT', '{"fields": ["phone"], "operator": "exact", "threshold": 1.0}', 1.0, 100, true, '手机号完全一致'),
('rule_002', '微信UnionID匹配', 'EXACT', '{"fields": ["wechat_unionid"], "operator": "exact", "threshold": 1.0}', 1.0, 100, true, '微信UnionID一致'),
('rule_003', '身份证号匹配', 'EXACT', '{"fields": ["id_card"], "operator": "exact", "threshold": 1.0}', 1.0, 100, true, '身份证号完全一致'),
('rule_004', '邮箱地址匹配', 'EXACT', '{"fields": ["email"], "operator": "exact", "threshold": 1.0}', 0.9, 90, true, '邮箱地址完全一致'),
('rule_005', '设备指纹匹配', 'FUZZY', '{"fields": ["device_fingerprint"], "operator": "similarity", "threshold": 0.8}', 0.7, 80, true, '设备指纹相似度匹配'),
('rule_006', 'IP段匹配', 'FUZZY', '{"fields": ["ip_segment"], "operator": "same_segment", "threshold": 0.6}', 0.5, 70, true, 'IP地址段匹配'),
('rule_007', '行为模式匹配', 'BEHAVIOR', '{"fields": ["behavior_pattern"], "operator": "behavior_similarity", "threshold": 0.6}', 0.5, 60, true, '用户行为模式相似度匹配');

-- 6.2 身份事件流表
DROP TABLE IF EXISTS t_crm_identity_event;
CREATE TABLE t_crm_identity_event
(
    id              VARCHAR(32) NOT NULL,
    identity_id     VARCHAR(32) NOT NULL,
    event_type      VARCHAR(64) NOT NULL,
    event_subtype   VARCHAR(64),
    event_data      JSONB NOT NULL,
    event_metadata  JSONB,
    source_system   VARCHAR(32) NOT NULL,
    source_id       VARCHAR(64),
    operator_id     VARCHAR(32),
    event_time      BIGINT NOT NULL,
    processed_time  BIGINT,
    create_time     BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_identity_event PRIMARY KEY (id)
);

CREATE INDEX idx_crm_identity_event_01 ON t_crm_identity_event (identity_id, event_time);
CREATE INDEX idx_crm_identity_event_02 ON t_crm_identity_event (event_type, event_time);
CREATE INDEX idx_crm_identity_event_03 ON t_crm_identity_event USING gin (event_data);
CREATE INDEX idx_crm_identity_event_04 ON t_crm_identity_event (source_system, event_time);

COMMENT ON TABLE t_crm_identity_event IS '身份事件流表 - 记录所有身份相关事件';
COMMENT ON COLUMN t_crm_identity_event.id IS 'ID';
COMMENT ON COLUMN t_crm_identity_event.identity_id IS '身份ID';
COMMENT ON COLUMN t_crm_identity_event.event_type IS '事件类型：identity.created-身份创建, identity.merged-身份合并, identity.verified-身份验证';
COMMENT ON COLUMN t_crm_identity_event.event_subtype IS '事件子类型';
COMMENT ON COLUMN t_crm_identity_event.event_data IS '事件数据，JSON格式';
COMMENT ON COLUMN t_crm_identity_event.event_metadata IS '事件元数据，JSON格式';
COMMENT ON COLUMN t_crm_identity_event.source_system IS '来源系统';
COMMENT ON COLUMN t_crm_identity_event.source_id IS '来源ID';
COMMENT ON COLUMN t_crm_identity_event.operator_id IS '操作人ID';
COMMENT ON COLUMN t_crm_identity_event.event_time IS '事件时间';
COMMENT ON COLUMN t_crm_identity_event.processed_time IS '处理时间';
COMMENT ON COLUMN t_crm_identity_event.create_time IS '创建时间';

-- 6.3 身份数据质量监控表
DROP TABLE IF EXISTS t_crm_identity_data_quality;
CREATE TABLE t_crm_identity_data_quality
(
    id                  VARCHAR(32) NOT NULL,
    identity_id         VARCHAR(32) NOT NULL,
    quality_dimension   VARCHAR(32) NOT NULL,
    quality_score       DECIMAL(5,2) NOT NULL,
    check_items         JSONB NOT NULL,
    issues_count        INTEGER NOT NULL DEFAULT 0,
    last_check_time     BIGINT NOT NULL,
    next_check_time     BIGINT NOT NULL,

    CONSTRAINT pk_crm_identity_data_quality PRIMARY KEY (id)
);

CREATE INDEX idx_crm_data_quality_01 ON t_crm_identity_data_quality (identity_id, quality_dimension);
CREATE INDEX idx_crm_data_quality_02 ON t_crm_identity_data_quality (quality_score, last_check_time);
CREATE INDEX idx_crm_data_quality_03 ON t_crm_identity_data_quality (issues_count, last_check_time);

COMMENT ON TABLE t_crm_identity_data_quality IS '身份数据质量监控表 - 监控身份数据质量';
COMMENT ON COLUMN t_crm_identity_data_quality.id IS 'ID';
COMMENT ON COLUMN t_crm_identity_data_quality.identity_id IS '身份ID';
COMMENT ON COLUMN t_crm_identity_data_quality.quality_dimension IS '质量维度：COMPLETENESS-完整性, ACCURACY-准确性, CONSISTENCY-一致性, TIMELINESS-及时性';
COMMENT ON COLUMN t_crm_identity_data_quality.quality_score IS '质量评分，0-100';
COMMENT ON COLUMN t_crm_identity_data_quality.check_items IS '检查项，JSON格式';
COMMENT ON COLUMN t_crm_identity_data_quality.issues_count IS '问题数量';
COMMENT ON COLUMN t_crm_identity_data_quality.last_check_time IS '最后检查时间';
COMMENT ON COLUMN t_crm_identity_data_quality.next_check_time IS '下次检查时间';

-- 7. 辅助功能表
-- =============================================

-- 7.1 手机号变更历史表
DROP TABLE IF EXISTS t_crm_phone_change_history;
CREATE TABLE t_crm_phone_change_history
(
    id              VARCHAR(32) NOT NULL,
    identity_id     VARCHAR(32) NOT NULL,
    old_phone       VARCHAR(32) NOT NULL,
    new_phone       VARCHAR(32) NOT NULL,
    change_type     VARCHAR(32) NOT NULL,
    change_reason   VARCHAR(128),
    operator_id     VARCHAR(32) NOT NULL,
    operator_type   VARCHAR(32) NOT NULL,
    ip_address      VARCHAR(64),
    device_info     VARCHAR(128),
    create_time     BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_phone_change_history PRIMARY KEY (id)
);

CREATE INDEX idx_crm_phone_history_01 ON t_crm_phone_change_history (identity_id);
CREATE INDEX idx_crm_phone_history_02 ON t_crm_phone_change_history (old_phone);
CREATE INDEX idx_crm_phone_history_03 ON t_crm_phone_change_history (new_phone);
CREATE INDEX idx_crm_phone_history_04 ON t_crm_phone_change_history (create_time);

COMMENT ON TABLE t_crm_phone_change_history IS '手机号变更历史表 - 记录手机号变更历史';
COMMENT ON COLUMN t_crm_phone_change_history.id IS 'ID';
COMMENT ON COLUMN t_crm_phone_change_history.identity_id IS '身份ID';
COMMENT ON COLUMN t_crm_phone_change_history.old_phone IS '旧手机号';
COMMENT ON COLUMN t_crm_phone_change_history.new_phone IS '新手机号';
COMMENT ON COLUMN t_crm_phone_change_history.change_type IS '变更类型：USER_CHANGE-用户主动变更, ADMIN_CHANGE-管理员变更, RECYCLE-号码回收';
COMMENT ON COLUMN t_crm_phone_change_history.change_reason IS '变更原因';
COMMENT ON COLUMN t_crm_phone_change_history.operator_id IS '操作人ID';
COMMENT ON COLUMN t_crm_phone_change_history.operator_type IS '操作人类型：USER-用户, ADMIN-管理员, SYSTEM-系统';
COMMENT ON COLUMN t_crm_phone_change_history.ip_address IS 'IP地址';
COMMENT ON COLUMN t_crm_phone_change_history.device_info IS '设备信息';
COMMENT ON COLUMN t_crm_phone_change_history.create_time IS '创建时间';

-- 7.2 手机号冷却期表
DROP TABLE IF EXISTS t_crm_phone_cooling_period;
CREATE TABLE t_crm_phone_cooling_period
(
    id              VARCHAR(32) NOT NULL,
    phone           VARCHAR(16) NOT NULL,
    identity_id     VARCHAR(32) NOT NULL,
    cool_down_until BIGINT NOT NULL,
    reason          VARCHAR(32) NOT NULL,
    create_time     BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_phone_cooling_period PRIMARY KEY (id),
    CONSTRAINT uk_crm_phone_cooling_period UNIQUE (phone)
);

CREATE INDEX idx_crm_phone_cooling_01 ON t_crm_phone_cooling_period (cool_down_until);
CREATE INDEX idx_crm_phone_cooling_02 ON t_crm_phone_cooling_period (identity_id);

COMMENT ON TABLE t_crm_phone_cooling_period IS '手机号冷却期表 - 防止手机号立即被重复使用';
COMMENT ON COLUMN t_crm_phone_cooling_period.id IS 'ID';
COMMENT ON COLUMN t_crm_phone_cooling_period.phone IS '手机号';
COMMENT ON COLUMN t_crm_phone_cooling_period.identity_id IS '身份ID';
COMMENT ON COLUMN t_crm_phone_cooling_period.cool_down_until IS '冷却期截止时间';
COMMENT ON COLUMN t_crm_phone_cooling_period.reason IS '原因';
COMMENT ON COLUMN t_crm_phone_cooling_period.create_time IS '创建时间';

-- 7.3 缓存同步状态表
DROP TABLE IF EXISTS t_crm_cache_sync_status;
CREATE TABLE t_crm_cache_sync_status
(
    identity_id         VARCHAR(32) NOT NULL,
    table_name          VARCHAR(64) NOT NULL,
    last_updated_time   BIGINT NOT NULL,
    cache_key           VARCHAR(128) NOT NULL,
    cache_version       BIGINT NOT NULL DEFAULT 1,

    CONSTRAINT pk_crm_cache_sync_status PRIMARY KEY (identity_id, table_name)
);

CREATE INDEX idx_crm_cache_sync_01 ON t_crm_cache_sync_status (last_updated_time);
CREATE INDEX idx_crm_cache_sync_02 ON t_crm_cache_sync_status (cache_key);

COMMENT ON TABLE t_crm_cache_sync_status IS '缓存同步状态表 - 维护缓存一致性';
COMMENT ON COLUMN t_crm_cache_sync_status.identity_id IS '身份ID';
COMMENT ON COLUMN t_crm_cache_sync_status.table_name IS '表名';
COMMENT ON COLUMN t_crm_cache_sync_status.last_updated_time IS '最后更新时间';
COMMENT ON COLUMN t_crm_cache_sync_status.cache_key IS '缓存键';
COMMENT ON COLUMN t_crm_cache_sync_status.cache_version IS '缓存版本号';

-- 7.4 历史数据归档表
DROP TABLE IF EXISTS t_crm_identity_history;
CREATE TABLE t_crm_identity_history
(
    LIKE t_crm_identity INCLUDING DEFAULTS,
    archive_time    BIGINT NOT NULL,
    archive_reason  VARCHAR(32) NOT NULL
);

CREATE INDEX idx_crm_identity_history_01 ON t_crm_identity_history (identity_id);
CREATE INDEX idx_crm_identity_history_02 ON t_crm_identity_history (archive_time);
CREATE INDEX idx_crm_identity_history_03 ON t_crm_identity_history (archive_reason, archive_time);

COMMENT ON TABLE t_crm_identity_history IS '身份历史表 - 归档历史身份数据';
COMMENT ON COLUMN t_crm_identity_history.archive_time IS '归档时间';
COMMENT ON COLUMN t_crm_identity_history.archive_reason IS '归档原因';

-- =============================================
-- 初始化数据
-- =============================================

-- 插入系统用户身份（用于系统操作）
INSERT INTO t_crm_identity (identity_id, identity_status, identity_level, identity_score, trust_score, risk_score, is_hot_data, first_seen_time, last_active_time, create_time, update_time)
VALUES ('system_0000000000000000000000000', 'ACTIVE', 'L4', 100.00, 100.00, 0.00, true,
        EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000,
        EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000);

-- 插入匿名用户身份（用于未登录用户）
INSERT INTO t_crm_identity (identity_id, identity_status, identity_level, identity_score, trust_score, risk_score, is_hot_data, first_seen_time, last_active_time, create_time, update_time)
VALUES ('anonymous_0000000000000000000000', 'ACTIVE', 'L0', 0.00, 0.00, 0.00, false,
        EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000,
        EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000);

-- =============================================
-- 视图创建（便于查询）
-- =============================================

-- 身份实体完整视图
CREATE OR REPLACE VIEW v_crm_identity_complete AS
SELECT
    i.identity_id,
    i.identity_status,
    i.identity_level,
    i.identity_score,
    i.trust_score,
    i.risk_score,
    i.first_seen_time,
    i.last_active_time,
    up.profile_data->>'real_name' as real_name,
    up.profile_data->>'phone_number' as phone_number,
    up.profile_data->>'email' as email,
    up.profile_data->>'membership_level' as membership_level,
    ilc.lifecycle_stage,
    pa.available_points as points_balance
FROM t_crm_identity i
LEFT JOIN t_crm_unified_profile up ON i.identity_id = up.identity_id AND up.profile_type = 'BASIC' AND up.status = 'ACTIVE'
LEFT JOIN t_crm_identity_lifecycle ilc ON i.identity_id = ilc.identity_id
LEFT JOIN t_crm_points_account pa ON i.identity_id = pa.identity_id AND pa.status = 'ACTIVE'
WHERE i.identity_status = 'ACTIVE';

-- 身份标识汇总视图
CREATE OR REPLACE VIEW v_crm_identity_identifiers AS
SELECT
    i.identity_id,
    i.identity_level,
    si.identity_type as strong_identity_type,
    si.identity_value as strong_identity_value,
    si.verification_level,
    wi.identity_type as weak_identity_type,
    wi.identity_hash as weak_identity_hash,
    wi.confidence_score
FROM t_crm_identity i
LEFT JOIN t_crm_strong_identity si ON i.identity_id = si.identity_id AND si.status = 'ACTIVE'
LEFT JOIN t_crm_weak_identity wi ON i.identity_id = wi.identity_id
WHERE i.identity_status = 'ACTIVE';

COMMENT ON VIEW v_crm_identity_complete IS '身份实体完整视图 - 包含身份基本信息和业务属性';
COMMENT ON VIEW v_crm_identity_identifiers IS '身份标识汇总视图 - 展示所有身份标识信息';

-- =============================================
-- 外键约束（根据实际需要添加）
-- =============================================

-- 注意：在生产环境中，建议根据业务需求添加外键约束

ALTER TABLE t_crm_strong_identity ADD CONSTRAINT fk_crm_strong_identity_identity
    FOREIGN KEY (identity_id) REFERENCES t_crm_identity(identity_id);

ALTER TABLE t_crm_weak_identity ADD CONSTRAINT fk_crm_weak_identity_identity
    FOREIGN KEY (identity_id) REFERENCES t_crm_identity(identity_id);

ALTER TABLE t_crm_identity_relation ADD CONSTRAINT fk_crm_relation_from_identity
    FOREIGN KEY (from_identity_id) REFERENCES t_crm_identity(identity_id);

ALTER TABLE t_crm_identity_relation ADD CONSTRAINT fk_crm_relation_to_identity
    FOREIGN KEY (to_identity_id) REFERENCES t_crm_identity(identity_id);

ALTER TABLE t_crm_channel_registration ADD CONSTRAINT fk_crm_channel_identity
    FOREIGN KEY (identity_id) REFERENCES t_crm_identity(identity_id);