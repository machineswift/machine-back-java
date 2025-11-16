-- =============================================
-- 统一身份管理系统 - 完整SQL脚本
-- 设计理念：身份图谱 + 多渠道识别 + 智能合并 + 合规安全
-- 版本：2.0
-- =============================================

-- 1. 核心身份模型
-- =============================================

-- 1.1 身份实体表（核心主表）
DROP TABLE IF EXISTS t_crm_identity_entity;
CREATE TABLE t_crm_identity_entity (
    entity_id           VARCHAR(32) NOT NULL,
    entity_status       VARCHAR(16) NOT NULL DEFAULT 'ACTIVE',
    entity_level        VARCHAR(16) NOT NULL DEFAULT 'L1',

    -- 全局唯一标识
    global_user_id      VARCHAR(64) UNIQUE,
    master_entity_id    VARCHAR(32),

    -- 身份质量评估
    identity_score      DECIMAL(5,2) NOT NULL DEFAULT 0.0,
    trust_score         DECIMAL(5,2) NOT NULL DEFAULT 0.0,
    risk_score          DECIMAL(5,2) NOT NULL DEFAULT 0.0,

    -- 性能优化字段
    is_hot_data         BOOLEAN NOT NULL DEFAULT FALSE,

    -- 生命周期
    first_seen_time     BIGINT NOT NULL,
    last_active_time    BIGINT NOT NULL,
    established_time    BIGINT,

    -- 审计字段
    version             BIGINT NOT NULL DEFAULT 1,
    create_time         BIGINT NOT NULL,
    update_time         BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_identity_entity PRIMARY KEY (entity_id)
);

CREATE INDEX idx_crm_identity_entity_01 ON t_crm_identity_entity (entity_status, entity_level);
CREATE INDEX idx_crm_identity_entity_02 ON t_crm_identity_entity (master_entity_id) WHERE master_entity_id IS NOT NULL;
CREATE INDEX idx_crm_identity_entity_03 ON t_crm_identity_entity (first_seen_time);
CREATE INDEX idx_crm_identity_entity_04 ON t_crm_identity_entity (last_active_time);
CREATE INDEX idx_crm_identity_entity_05 ON t_crm_identity_entity (is_hot_data, last_active_time);

COMMENT ON TABLE t_crm_identity_entity IS '身份实体表 - 核心主表';
COMMENT ON COLUMN t_crm_identity_entity.entity_id IS '主身份ID，所有业务数据关联此ID';
COMMENT ON COLUMN t_crm_identity_entity.entity_status IS '状态：ACTIVE-活跃, MERGED-已合并, CLOSED-关闭';
COMMENT ON COLUMN t_crm_identity_entity.entity_level IS '身份等级：L1-匿名, L2-弱实名, L3-强实名';

-- 1.2 身份等级配置表
DROP TABLE IF EXISTS t_crm_identity_level_config;
CREATE TABLE t_crm_identity_level_config (
    level_code          VARCHAR(16) NOT NULL,
    level_name          VARCHAR(64) NOT NULL,
    required_identifiers JSONB NOT NULL,
    verification_requirements JSONB NOT NULL,
    privileges          JSONB,
    is_active           BOOLEAN NOT NULL DEFAULT TRUE,
    create_time         BIGINT NOT NULL,

    CONSTRAINT pk_crm_identity_level_config PRIMARY KEY (level_code)
);

COMMENT ON TABLE t_crm_identity_level_config IS '身份等级配置表 - 定义各等级的身份要求和权限';

-- 预置身份等级
INSERT INTO t_crm_identity_level_config VALUES
('L0', '匿名用户', '[]', '[]', '{"basic_access": true}', true, EXTRACT(EPOCH FROM NOW()) * 1000),
('L1', '弱实名', '["PHONE"]', '["PHONE_SMS"]', '{"order": true, "basic_service": true}', true, EXTRACT(EPOCH FROM NOW()) * 1000),
('L2', '强实名', '["PHONE", "ID_CARD"]', '["PHONE_SMS", "ID_CARD_VERIFY"]', '{"financial": true, "sensitive_operation": true}', true, EXTRACT(EPOCH FROM NOW()) * 1000),
('L3', '高级认证', '["PHONE", "ID_CARD", "BANK_CARD"]', '["PHONE_SMS", "ID_CARD_VERIFY", "BANK_CARD_VERIFY"]', '{"large_transaction": true, "premium_service": true}', true, EXTRACT(EPOCH FROM NOW()) * 1000);

-- 1.3 强身份标识表（唯一性约束）
DROP TABLE IF EXISTS t_crm_strong_identity;
CREATE TABLE t_crm_strong_identity (
    id                  VARCHAR(32) NOT NULL,
    entity_id           VARCHAR(32) NOT NULL,
    identity_type       VARCHAR(32) NOT NULL,
    identity_value      VARCHAR(128) NOT NULL,
    identity_hash       VARCHAR(64) NOT NULL,

    -- 验证状态
    verification_level  VARCHAR(16) NOT NULL DEFAULT 'L0',
    verification_method VARCHAR(32),
    verified_time       BIGINT,
    verification_evidence JSONB,

    -- 安全控制
    verification_attempts INTEGER DEFAULT 0,
    lock_until_time    BIGINT,
    mfa_factors        JSONB,

    -- 使用状态
    is_primary          BOOLEAN NOT NULL DEFAULT FALSE,
    is_public           BOOLEAN NOT NULL DEFAULT FALSE,
    status              VARCHAR(16) NOT NULL DEFAULT 'ACTIVE',

    -- 风险评估
    trust_score         DECIMAL(3,2) NOT NULL DEFAULT 0.0,
    risk_level          VARCHAR(16) NOT NULL DEFAULT 'LOW',

    -- 审计字段
    version             BIGINT NOT NULL DEFAULT 1,
    create_time         BIGINT NOT NULL,
    update_time         BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_strong_identity PRIMARY KEY (id),
    CONSTRAINT uk_crm_strong_identity_unique UNIQUE (identity_type, identity_value)
        WHERE status IN ('ACTIVE', 'VERIFIED')
);

CREATE INDEX idx_crm_strong_identity_01 ON t_crm_strong_identity (entity_id);
CREATE INDEX idx_crm_strong_identity_02 ON t_crm_strong_identity (identity_type, identity_value);
CREATE INDEX idx_crm_strong_identity_03 ON t_crm_strong_identity (status, verification_level);
CREATE INDEX idx_crm_strong_identity_04 ON t_crm_strong_identity (identity_hash);
CREATE INDEX idx_crm_strong_identity_05 ON t_crm_strong_identity (lock_until_time) WHERE lock_until_time IS NOT NULL;

COMMENT ON TABLE t_crm_strong_identity IS '强身份标识表 - 具有唯一性约束的标识';
COMMENT ON COLUMN t_crm_strong_identity.identity_type IS '标识类型：PHONE-手机, EMAIL-邮箱, ID_CARD-身份证, WECHAT_UNIONID-微信, ALIPAY_USERID-支付宝';
COMMENT ON COLUMN t_crm_strong_identity.verification_level IS '验证等级：L0-未验, L1-弱验, L2-强验';

-- 1.4 弱身份标识表（辅助识别）
DROP TABLE IF EXISTS t_crm_weak_identity;
CREATE TABLE t_crm_weak_identity (
    id                  VARCHAR(32) NOT NULL,
    entity_id           VARCHAR(32) NOT NULL,
    identity_type       VARCHAR(32) NOT NULL,
    identity_value      TEXT NOT NULL,
    identity_hash       VARCHAR(64) NOT NULL,

    -- 置信度评估
    confidence_score    DECIMAL(3,2) NOT NULL DEFAULT 0.0,
    match_count         INTEGER NOT NULL DEFAULT 0,
    last_match_time     BIGINT NOT NULL,

    -- 生命周期
    ttl_days            INTEGER NOT NULL DEFAULT 90,
    expires_time        BIGINT NOT NULL,

    metadata            JSONB,
    create_time         BIGINT NOT NULL,
    update_time         BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_weak_identity PRIMARY KEY (id)
);

CREATE INDEX idx_crm_weak_identity_01 ON t_crm_weak_identity (identity_type, identity_hash);
CREATE INDEX idx_crm_weak_identity_02 ON t_crm_weak_identity (entity_id);
CREATE INDEX idx_crm_weak_identity_03 ON t_crm_weak_identity (expires_time);
CREATE INDEX idx_crm_weak_identity_04 ON t_crm_weak_identity (confidence_score);

COMMENT ON TABLE t_crm_weak_identity IS '弱身份标识表 - 用于辅助识别，无唯一约束';
COMMENT ON COLUMN t_crm_weak_identity.identity_type IS '标识类型：DEVICE_ID-设备ID, COOKIE-浏览器Cookie, IP_SEGMENT-IP段, BROWSER_FINGERPRINT-浏览器指纹';

-- 2. 身份关系与图谱
-- =============================================

-- 2.1 身份关联关系表
DROP TABLE IF EXISTS t_crm_identity_relation;
CREATE TABLE t_crm_identity_relation (
    id                  VARCHAR(32) NOT NULL,
    from_entity_id      VARCHAR(32) NOT NULL,
    to_entity_id        VARCHAR(32) NOT NULL,
    relation_type       VARCHAR(32) NOT NULL,
    relation_strength   DECIMAL(3,2) NOT NULL DEFAULT 0.0,

    -- 关系证据
    evidence_type       VARCHAR(32) NOT NULL,
    confidence_score    DECIMAL(3,2) NOT NULL DEFAULT 0.0,
    evidence_data       JSONB NOT NULL,

    -- 关系状态
    relation_status     VARCHAR(32) NOT NULL DEFAULT 'ACTIVE',
    reviewed_by         VARCHAR(32),
    reviewed_time       BIGINT,

    -- 时间窗口
    first_observed_time BIGINT NOT NULL,
    last_observed_time  BIGINT NOT NULL,
    expires_time        BIGINT,

    version             BIGINT NOT NULL DEFAULT 1,
    create_time         BIGINT NOT NULL,
    update_time         BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_identity_relation PRIMARY KEY (id),
    CONSTRAINT uk_crm_identity_relation UNIQUE (from_entity_id, to_entity_id, relation_type)
);

CREATE INDEX idx_crm_identity_relation_01 ON t_crm_identity_relation (from_entity_id, relation_type);
CREATE INDEX idx_crm_identity_relation_02 ON t_crm_identity_relation (to_entity_id, relation_type);
CREATE INDEX idx_crm_identity_relation_03 ON t_crm_identity_relation (relation_status, confidence_score);
CREATE INDEX idx_crm_identity_relation_04 ON t_crm_identity_relation (last_observed_time);

COMMENT ON TABLE t_crm_identity_relation IS '身份关联关系表 - 构建身份图谱';
COMMENT ON COLUMN t_crm_identity_relation.relation_type IS '关系类型：SAME_PERSON-同一人, HOUSEHOLD-家庭成员, COLLEAGUE-同事, DEVICE_SHARING-设备共享';

-- 2.2 身份合并历史表
DROP TABLE IF EXISTS t_crm_identity_merge_history;
CREATE TABLE t_crm_identity_merge_history (
    id                      VARCHAR(32) NOT NULL,
    operation_id            VARCHAR(32) NOT NULL,
    primary_entity_id       VARCHAR(32) NOT NULL,
    merged_entity_id        VARCHAR(32) NOT NULL,

    -- 合并策略
    merge_strategy          VARCHAR(32) NOT NULL,
    merge_reason            VARCHAR(256) NOT NULL,
    confidence_score        DECIMAL(3,2) NOT NULL DEFAULT 0.0,

    -- 数据合并详情
    data_merge_plan         JSONB NOT NULL,
    merged_attributes       JSONB NOT NULL,
    conflict_resolution     JSONB NOT NULL,

    -- 审计信息
    operator_type           VARCHAR(32) NOT NULL,
    operator_id             VARCHAR(32) NOT NULL,
    operation_ip            VARCHAR(64),

    -- 回滚支持
    can_rollback            BOOLEAN NOT NULL DEFAULT TRUE,
    rollback_deadline       BIGINT NOT NULL,

    create_time             BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_identity_merge_history PRIMARY KEY (id),
    CONSTRAINT uk_crm_identity_merge_history UNIQUE (merged_entity_id)
);

CREATE INDEX idx_crm_identity_merge_01 ON t_crm_identity_merge_history (primary_entity_id);
CREATE INDEX idx_crm_identity_merge_02 ON t_crm_identity_merge_history (operation_id);
CREATE INDEX idx_crm_identity_merge_03 ON t_crm_identity_merge_history (merge_strategy, create_time);

COMMENT ON TABLE t_crm_identity_merge_history IS '身份合并历史表 - 记录身份合并操作';
COMMENT ON COLUMN t_crm_identity_merge_history.merge_strategy IS '合并策略：AUTO_BY_PHONE-手机自动合并, AUTO_BY_WECHAT-微信自动合并, MANUAL-手动合并, AI_RECOMMEND-AI推荐合并';

-- 3. 多渠道身份管理
-- =============================================

-- 3.1 渠道注册记录表
DROP TABLE IF EXISTS t_crm_channel_registration;
CREATE TABLE t_crm_channel_registration (
    id                  VARCHAR(32) NOT NULL,
    entity_id           VARCHAR(32) NOT NULL,
    channel_type        VARCHAR(32) NOT NULL,
    channel_app_id      VARCHAR(64) NOT NULL,
    channel_user_id     VARCHAR(128),

    -- 注册上下文
    register_device     JSONB NOT NULL,
    register_network    JSONB,
    register_geography  JSONB,
    register_referrer   VARCHAR(256),

    -- 渠道特定身份
    channel_identity    JSONB,
    channel_session     JSONB,

    -- 状态控制
    is_active           BOOLEAN NOT NULL DEFAULT TRUE,
    last_login_time     BIGINT,
    login_count         INTEGER NOT NULL DEFAULT 0,

    create_time         BIGINT NOT NULL,
    update_time         BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_channel_registration PRIMARY KEY (id),
    CONSTRAINT uk_crm_channel_registration UNIQUE (channel_type, channel_app_id, channel_user_id)
);

CREATE INDEX idx_crm_channel_registration_01 ON t_crm_channel_registration (entity_id);
CREATE INDEX idx_crm_channel_registration_02 ON t_crm_channel_registration (channel_type, is_active);
CREATE INDEX idx_crm_channel_registration_03 ON t_crm_channel_registration (last_login_time);

COMMENT ON TABLE t_crm_channel_registration IS '渠道注册记录表 - 管理多渠道身份绑定';
COMMENT ON COLUMN t_crm_channel_registration.channel_type IS '渠道类型：WECHAT_MINI-微信小程序, ALIPAY_MINI-支付宝小程序, MOBILE_APP-手机APP, WEB-网站';

-- 3.2 统一会话管理表
DROP TABLE IF EXISTS t_crm_unified_session;
CREATE TABLE t_crm_unified_session (
    id                  VARCHAR(32) NOT NULL,
    entity_id           VARCHAR(32) NOT NULL,
    session_type        VARCHAR(32) NOT NULL,
    session_token       VARCHAR(128) NOT NULL,
    parent_session_id   VARCHAR(32),

    -- 会话安全
    security_level      VARCHAR(16) NOT NULL DEFAULT 'STANDARD',
    mfa_verified        BOOLEAN NOT NULL DEFAULT FALSE,
    device_trust_level  VARCHAR(16) NOT NULL DEFAULT 'UNKNOWN',

    -- 会话上下文
    channels            JSONB NOT NULL,
    last_activity_time  BIGINT NOT NULL,
    expires_time        BIGINT NOT NULL,

    create_time         BIGINT NOT NULL,

    CONSTRAINT pk_crm_unified_session PRIMARY KEY (id),
    CONSTRAINT uk_crm_unified_session_token UNIQUE (session_token)
);

CREATE INDEX idx_crm_unified_session_01 ON t_crm_unified_session (entity_id, expires_time);
CREATE INDEX idx_crm_unified_session_02 ON t_crm_unified_session (last_activity_time);
CREATE INDEX idx_crm_unified_session_03 ON t_crm_unified_session (parent_session_id) WHERE parent_session_id IS NOT NULL;

COMMENT ON TABLE t_crm_unified_session IS '统一会话管理表 - 跨渠道会话管理';
COMMENT ON COLUMN t_crm_unified_session.session_type IS '会话类型：WEB, MOBILE, CROSS_PLATFORM';

-- 3.3 身份识别会话表
DROP TABLE IF EXISTS t_crm_identity_session;
CREATE TABLE t_crm_identity_session (
    id                      VARCHAR(32) NOT NULL,
    session_token           VARCHAR(64) NOT NULL,
    entity_id               VARCHAR(32),

    -- 会话上下文
    session_context         JSONB NOT NULL,
    device_fingerprint      VARCHAR(64) NOT NULL,
    environment_fingerprint VARCHAR(64) NOT NULL,

    -- 识别状态
    identification_status   VARCHAR(32) NOT NULL DEFAULT 'PENDING',
    candidate_entities      JSONB,
    selected_entity_id      VARCHAR(32),

    -- 识别证据
    identification_evidence JSONB NOT NULL,
    confidence_score        DECIMAL(3,2) NOT NULL DEFAULT 0.0,

    -- 会话生命周期
    session_start_time      BIGINT NOT NULL,
    session_end_time        BIGINT,
    ttl_seconds             INTEGER NOT NULL DEFAULT 86400,

    create_time             BIGINT NOT NULL,
    update_time             BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_identity_session PRIMARY KEY (id),
    CONSTRAINT uk_crm_identity_session UNIQUE (session_token)
);

CREATE INDEX idx_crm_identity_session_01 ON t_crm_identity_session (entity_id);
CREATE INDEX idx_crm_identity_session_02 ON t_crm_identity_session (device_fingerprint);
CREATE INDEX idx_crm_identity_session_03 ON t_crm_identity_session (session_start_time);
CREATE INDEX idx_crm_identity_session_04 ON t_crm_identity_session (identification_status);

COMMENT ON TABLE t_crm_identity_session IS '身份识别会话表 - 管理实时身份识别会话';
COMMENT ON COLUMN t_crm_identity_session.identification_status IS '识别状态：PENDING-待识别, IDENTIFIED-已识别, AMBIGUOUS-模糊匹配, FAILED-识别失败';

-- 4. 业务数据扩展
-- =============================================

-- 4.1 统一用户档案表
DROP TABLE IF EXISTS t_crm_unified_profile;
CREATE TABLE t_crm_unified_profile (
    id                  VARCHAR(32) NOT NULL,
    entity_id           VARCHAR(32) NOT NULL,
    profile_type        VARCHAR(32) NOT NULL DEFAULT 'BASIC',

    -- 基础信息
    real_name           VARCHAR(64),
    id_card_number      VARCHAR(32),
    id_card_verified    BOOLEAN NOT NULL DEFAULT FALSE,

    -- 联系信息
    phone_number        VARCHAR(32),
    email               VARCHAR(128),

    -- 个人信息
    gender              VARCHAR(16) DEFAULT 'UNKNOWN',
    birth_date          DATE,
    avatar_url          VARCHAR(512),

    -- 业务属性
    membership_level    VARCHAR(32) DEFAULT 'BRONZE',
    growth_value        INTEGER NOT NULL DEFAULT 0,
    tags                JSONB,

    -- 偏好设置
    preferences         JSONB,

    -- 缓存版本控制
    cache_version       BIGINT NOT NULL DEFAULT 1,

    -- 统计信息
    total_order_count   INTEGER NOT NULL DEFAULT 0,
    total_spent_amount  DECIMAL(14,2) NOT NULL DEFAULT 0.00,
    last_order_time     BIGINT,

    version             BIGINT NOT NULL DEFAULT 1,
    create_time         BIGINT NOT NULL,
    update_time         BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_unified_profile PRIMARY KEY (id),
    CONSTRAINT uk_crm_unified_profile UNIQUE (entity_id, profile_type)
);

CREATE INDEX idx_crm_unified_profile_01 ON t_crm_unified_profile (real_name);
CREATE INDEX idx_crm_unified_profile_02 ON t_crm_unified_profile (phone_number) WHERE phone_number IS NOT NULL;
CREATE INDEX idx_crm_unified_profile_03 ON t_crm_unified_profile (id_card_number) WHERE id_card_number IS NOT NULL;
CREATE INDEX idx_crm_unified_profile_04 ON t_crm_unified_profile (membership_level);
CREATE INDEX idx_crm_unified_profile_05 ON t_crm_unified_profile (create_time);

COMMENT ON TABLE t_crm_unified_profile IS '统一用户档案表 - 存储用户业务属性';
COMMENT ON COLUMN t_crm_unified_profile.profile_type IS '档案类型：BASIC-基础档案, PREFERENCE-偏好档案, BEHAVIOR-行为档案, RISK-风险档案';

-- 4.2 用户行为标签表
DROP TABLE IF EXISTS t_crm_behavior_tag;
CREATE TABLE t_crm_behavior_tag (
    id                  VARCHAR(32) NOT NULL,
    entity_id           VARCHAR(32) NOT NULL,
    tag_category        VARCHAR(64) NOT NULL,
    tag_name            VARCHAR(128) NOT NULL,
    tag_value           DECIMAL(5,2) NOT NULL,
    tag_source          VARCHAR(64) NOT NULL,
    effective_time      BIGINT NOT NULL,
    expires_time        BIGINT,
    confidence_score    DECIMAL(3,2) NOT NULL,
    create_time         BIGINT NOT NULL,

    CONSTRAINT pk_crm_behavior_tag PRIMARY KEY (id)
);

CREATE INDEX idx_crm_behavior_tag_01 ON t_crm_behavior_tag (entity_id, tag_category);
CREATE INDEX idx_crm_behavior_tag_02 ON t_crm_behavior_tag (tag_name, tag_value);
CREATE INDEX idx_crm_behavior_tag_03 ON t_crm_behavior_tag (effective_time, expires_time);
CREATE INDEX idx_crm_behavior_tag_04 ON t_crm_behavior_tag (tag_source, create_time);

COMMENT ON TABLE t_crm_behavior_tag IS '用户行为标签表 - 用户画像标签管理';
COMMENT ON COLUMN t_crm_behavior_tag.tag_category IS '标签分类：PURCHASE_PREFERENCE-购买偏好, ACTIVITY_LEVEL-活跃度, DEVICE_PREFERENCE-设备偏好';

-- 4.3 身份生命周期表
DROP TABLE IF EXISTS t_crm_identity_lifecycle;
CREATE TABLE t_crm_identity_lifecycle (
    entity_id           VARCHAR(32) NOT NULL,
    lifecycle_stage     VARCHAR(32) NOT NULL,
    stage_start_time    BIGINT NOT NULL,
    stage_duration_days INTEGER,
    stage_reason        VARCHAR(128),
    predicted_next_stage VARCHAR(32),
    prediction_confidence DECIMAL(3,2),
    update_time         BIGINT NOT NULL,

    CONSTRAINT pk_crm_identity_lifecycle PRIMARY KEY (entity_id)
);

CREATE INDEX idx_crm_identity_lifecycle_01 ON t_crm_identity_lifecycle (lifecycle_stage, stage_start_time);
CREATE INDEX idx_crm_identity_lifecycle_02 ON t_crm_identity_lifecycle (predicted_next_stage, prediction_confidence);

COMMENT ON TABLE t_crm_identity_lifecycle IS '身份生命周期表 - 用户生命周期阶段管理';
COMMENT ON COLUMN t_crm_identity_lifecycle.lifecycle_stage IS '生命周期阶段：NEW-新用户, ACTIVIVE-活跃, AT_RISK-风险, CHURNED-流失, RECOVERED-召回';

-- 4.4 积分账户表
DROP TABLE IF EXISTS t_crm_points_account;
CREATE TABLE t_crm_points_account (
    id                  VARCHAR(32) NOT NULL,
    entity_id           VARCHAR(32) NOT NULL,
    total_points        BIGINT NOT NULL DEFAULT 0,
    available_points    BIGINT NOT NULL DEFAULT 0,
    frozen_points       BIGINT NOT NULL DEFAULT 0,
    expired_points      BIGINT NOT NULL DEFAULT 0,
    points_status       VARCHAR(32) NOT NULL DEFAULT 'ACTIVE',

    last_earn_time      BIGINT,
    last_use_time       BIGINT,

    version             BIGINT NOT NULL DEFAULT 1,
    create_time         BIGINT NOT NULL,
    update_time         BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_points_account PRIMARY KEY (id),
    CONSTRAINT uk_crm_points_account UNIQUE (entity_id)
);

CREATE INDEX idx_crm_points_account_01 ON t_crm_points_account (points_status);
CREATE INDEX idx_crm_points_account_02 ON t_crm_points_account (available_points);
CREATE INDEX idx_crm_points_account_03 ON t_crm_points_account (last_earn_time);

COMMENT ON TABLE t_crm_points_account IS '积分账户表 - 管理用户积分';
COMMENT ON COLUMN t_crm_points_account.points_status IS '状态：ACTIVE-活跃, FROZEN-冻结, CLOSED-关闭';

-- 4.5 优惠券表
DROP TABLE IF EXISTS t_crm_coupon;
CREATE TABLE t_crm_coupon (
    id                  VARCHAR(32) NOT NULL,
    entity_id           VARCHAR(32) NOT NULL,
    coupon_template_id  VARCHAR(32) NOT NULL,
    coupon_code         VARCHAR(64) NOT NULL,
    coupon_name         VARCHAR(128) NOT NULL,
    coupon_type         VARCHAR(32) NOT NULL,

    face_value          DECIMAL(10,2),
    discount_rate       DECIMAL(5,2),
    condition_amount    DECIMAL(10,2) DEFAULT 0,

    status              VARCHAR(32) NOT NULL,
    valid_from          BIGINT NOT NULL,
    valid_to            BIGINT NOT NULL,

    used_time           BIGINT,
    used_order_id       VARCHAR(32),

    version             BIGINT NOT NULL DEFAULT 1,
    create_time         BIGINT NOT NULL,
    update_time         BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_coupon PRIMARY KEY (id)
);

CREATE INDEX idx_crm_coupon_01 ON t_crm_coupon (entity_id, status);
CREATE INDEX idx_crm_coupon_02 ON t_crm_coupon (coupon_code);
CREATE INDEX idx_crm_coupon_03 ON t_crm_coupon (valid_to, status);
CREATE INDEX idx_crm_coupon_04 ON t_crm_coupon (coupon_template_id);
CREATE INDEX idx_crm_coupon_05 ON t_crm_coupon (create_time);

COMMENT ON TABLE t_crm_coupon IS '优惠券表 - 管理用户优惠券';
COMMENT ON COLUMN t_crm_coupon.coupon_type IS '优惠券类型：DISCOUNT-折扣券, AMOUNT-金额券, GIFT-礼品券';
COMMENT ON COLUMN t_crm_coupon.status IS '状态：UNUSED-未使用, USED-已使用, EXPIRED-已过期, FROZEN-冻结';

-- 5. 安全与合规
-- =============================================

-- 5.1 身份验证记录表
DROP TABLE IF EXISTS t_crm_identity_verification_log;
CREATE TABLE t_crm_identity_verification_log (
    id                  VARCHAR(32) NOT NULL,
    entity_id           VARCHAR(32) NOT NULL,
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

CREATE INDEX idx_crm_verification_log_01 ON t_crm_identity_verification_log (entity_id, create_time);
CREATE INDEX idx_crm_verification_log_02 ON t_crm_identity_verification_log (identity_type, verification_result);
CREATE INDEX idx_crm_verification_log_03 ON t_crm_identity_verification_log (risk_score, create_time);

COMMENT ON TABLE t_crm_identity_verification_log IS '身份验证记录表 - 记录所有身份验证操作';

-- 5.2 身份风险事件表
DROP TABLE IF EXISTS t_crm_identity_risk_event;
CREATE TABLE t_crm_identity_risk_event (
    id                  VARCHAR(32) NOT NULL,
    entity_id           VARCHAR(32),
    event_type          VARCHAR(64) NOT NULL,
    risk_level          VARCHAR(16) NOT NULL,
    risk_score          DECIMAL(3,2) NOT NULL,
    trigger_source      VARCHAR(64) NOT NULL,
    event_data          JSONB NOT NULL,
    handled             BOOLEAN NOT NULL DEFAULT FALSE,
    handler_id          VARCHAR(32),
    handle_time         BIGINT,
    handle_action       VARCHAR(32),
    create_time         BIGINT NOT NULL,

    CONSTRAINT pk_crm_identity_risk_event PRIMARY KEY (id)
);

CREATE INDEX idx_crm_risk_event_01 ON t_crm_identity_risk_event (entity_id, create_time);
CREATE INDEX idx_crm_risk_event_02 ON t_crm_identity_risk_event (risk_level, handled);
CREATE INDEX idx_crm_risk_event_03 ON t_crm_identity_risk_event (event_type, create_time);
CREATE INDEX idx_crm_risk_event_04 ON t_crm_identity_risk_event (trigger_source, create_time);

COMMENT ON TABLE t_crm_identity_risk_event IS '身份风险事件表 - 记录身份相关风险事件';
COMMENT ON COLUMN t_crm_identity_risk_event.event_type IS '事件类型：SUSPICIOUS_LOGIN-可疑登录, IDENTITY_CONFLICT-身份冲突, FRAUD_ATTEMPT-欺诈尝试';

-- 5.3 数据隐私同意表
DROP TABLE IF EXISTS t_crm_privacy_consent;
CREATE TABLE t_crm_privacy_consent (
    id                  VARCHAR(32) NOT NULL,
    entity_id           VARCHAR(32) NOT NULL,
    consent_type        VARCHAR(64) NOT NULL,
    consent_version     VARCHAR(32) NOT NULL,
    granted             BOOLEAN NOT NULL,
    granted_time        BIGINT,
    revoked_time        BIGINT,
    source              VARCHAR(32) NOT NULL,
    evidence            JSONB,

    CONSTRAINT pk_crm_privacy_consent PRIMARY KEY (id)
);

CREATE INDEX idx_crm_privacy_consent_01 ON t_crm_privacy_consent (entity_id, consent_type);
CREATE INDEX idx_crm_privacy_consent_02 ON t_crm_privacy_consent (granted, consent_type);
CREATE INDEX idx_crm_privacy_consent_03 ON t_crm_privacy_consent (granted_time, consent_type);

COMMENT ON TABLE t_crm_privacy_consent IS '数据隐私同意表 - GDPR合规支持';
COMMENT ON COLUMN t_crm_privacy_consent.consent_type IS '同意类型：PRIVACY_POLICY-隐私政策, MARKETING-营销, DATA_SHARING-数据共享';

-- 5.4 数据删除请求表
DROP TABLE IF EXISTS t_crm_data_deletion_request;
CREATE TABLE t_crm_data_deletion_request (
    id                  VARCHAR(32) NOT NULL,
    entity_id           VARCHAR(32) NOT NULL,
    request_type        VARCHAR(32) NOT NULL,
    request_reason      VARCHAR(256),
    status              VARCHAR(32) NOT NULL DEFAULT 'PENDING',
    requested_by        VARCHAR(32) NOT NULL,
    requested_time      BIGINT NOT NULL,
    completed_time      BIGINT,
    completion_evidence JSONB,

    CONSTRAINT pk_crm_data_deletion_request PRIMARY KEY (id)
);

CREATE INDEX idx_crm_deletion_request_01 ON t_crm_data_deletion_request (entity_id, status);
CREATE INDEX idx_crm_deletion_request_02 ON t_crm_data_deletion_request (request_type, requested_time);
CREATE INDEX idx_crm_deletion_request_03 ON t_crm_data_deletion_request (status, requested_time);

COMMENT ON TABLE t_crm_data_deletion_request IS '数据删除请求表 - 支持GDPR删除权';

-- 6. 核心功能支持
-- =============================================

-- 6.1 身份匹配规则表
DROP TABLE IF EXISTS t_crm_identity_match_rule;
CREATE TABLE t_crm_identity_match_rule (
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
COMMENT ON COLUMN t_crm_identity_match_rule.rule_type IS '规则类型：EXACT-精确匹配, FUZZY-模糊匹配, BEHAVIOR-行为匹配';

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
CREATE TABLE t_crm_identity_event (
    id                  VARCHAR(32) NOT NULL,
    entity_id           VARCHAR(32) NOT NULL,
    event_type          VARCHAR(64) NOT NULL,
    event_subtype       VARCHAR(64),

    -- 事件数据
    event_data          JSONB NOT NULL,
    event_metadata      JSONB,

    -- 事件来源
    source_system       VARCHAR(32) NOT NULL,
    source_id           VARCHAR(64),
    operator_id         VARCHAR(32),

    -- 事件时间
    event_time          BIGINT NOT NULL,
    processed_time      BIGINT,

    create_time         BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_identity_event PRIMARY KEY (id)
);

CREATE INDEX idx_crm_identity_event_01 ON t_crm_identity_event (entity_id, event_time);
CREATE INDEX idx_crm_identity_event_02 ON t_crm_identity_event (event_type, event_time);
CREATE INDEX idx_crm_identity_event_03 ON t_crm_identity_event USING gin (event_data);
CREATE INDEX idx_crm_identity_event_04 ON t_crm_identity_event (source_system, event_time);

COMMENT ON TABLE t_crm_identity_event IS '身份事件流表 - 记录所有身份相关事件';
COMMENT ON COLUMN t_crm_identity_event.event_type IS '事件类型：identity.created-身份创建, identity.merged-身份合并, identity.verified-身份验证';

-- 6.3 身份数据质量监控表
DROP TABLE IF EXISTS t_crm_identity_data_quality;
CREATE TABLE t_crm_identity_data_quality (
    id                  VARCHAR(32) NOT NULL,
    entity_id           VARCHAR(32) NOT NULL,
    quality_dimension   VARCHAR(32) NOT NULL,
    quality_score       DECIMAL(5,2) NOT NULL,
    check_items         JSONB NOT NULL,
    issues_count        INTEGER NOT NULL DEFAULT 0,
    last_check_time     BIGINT NOT NULL,
    next_check_time     BIGINT NOT NULL,

    CONSTRAINT pk_crm_identity_data_quality PRIMARY KEY (id)
);

CREATE INDEX idx_crm_data_quality_01 ON t_crm_identity_data_quality (entity_id, quality_dimension);
CREATE INDEX idx_crm_data_quality_02 ON t_crm_identity_data_quality (quality_score, last_check_time);
CREATE INDEX idx_crm_data_quality_03 ON t_crm_identity_data_quality (issues_count, last_check_time);

COMMENT ON TABLE t_crm_identity_data_quality IS '身份数据质量监控表 - 监控身份数据质量';
COMMENT ON COLUMN t_crm_identity_data_quality.quality_dimension IS '质量维度：COMPLETENESS-完整性, ACCURACY-准确性, CONSISTENCY-一致性, TIMELINESS-及时性';

-- 7. 辅助功能表
-- =============================================

-- 7.1 手机号变更历史表
DROP TABLE IF EXISTS t_crm_phone_change_history;
CREATE TABLE t_crm_phone_change_history (
    id                  VARCHAR(32) NOT NULL,
    entity_id           VARCHAR(32) NOT NULL,
    old_phone           VARCHAR(32) NOT NULL,
    new_phone           VARCHAR(32) NOT NULL,
    change_type         VARCHAR(32) NOT NULL,
    change_reason       VARCHAR(128),
    operator_id         VARCHAR(32) NOT NULL,
    operator_type       VARCHAR(32) NOT NULL,
    ip_address          VARCHAR(64),
    device_info         VARCHAR(128),
    create_time         BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_phone_change_history PRIMARY KEY (id)
);

CREATE INDEX idx_crm_phone_history_01 ON t_crm_phone_change_history (entity_id);
CREATE INDEX idx_crm_phone_history_02 ON t_crm_phone_change_history (old_phone);
CREATE INDEX idx_crm_phone_history_03 ON t_crm_phone_change_history (new_phone);
CREATE INDEX idx_crm_phone_history_04 ON t_crm_phone_change_history (create_time);

COMMENT ON TABLE t_crm_phone_change_history IS '手机号变更历史表 - 记录手机号变更历史';
COMMENT ON COLUMN t_crm_phone_change_history.change_type IS '变更类型：USER_CHANGE-用户主动变更, ADMIN_CHANGE-管理员变更, RECYCLE-号码回收';

-- 7.2 手机号冷却期表
DROP TABLE IF EXISTS t_crm_phone_cooling_period;
CREATE TABLE t_crm_phone_cooling_period (
    id                  VARCHAR(32) NOT NULL,
    phone               VARCHAR(16) NOT NULL,
    entity_id           VARCHAR(32) NOT NULL,
    cool_down_until     BIGINT NOT NULL,
    reason              VARCHAR(32) NOT NULL,
    create_time         BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_phone_cooling_period PRIMARY KEY (id),
    CONSTRAINT uk_crm_phone_cooling_period UNIQUE (phone)
);

CREATE INDEX idx_crm_phone_cooling_01 ON t_crm_phone_cooling_period (cool_down_until);
CREATE INDEX idx_crm_phone_cooling_02 ON t_crm_phone_cooling_period (entity_id);

COMMENT ON TABLE t_crm_phone_cooling_period IS '手机号冷却期表 - 防止手机号立即被重复使用';

-- 7.3 缓存同步状态表
DROP TABLE IF EXISTS t_crm_cache_sync_status;
CREATE TABLE t_crm_cache_sync_status (
    entity_id           VARCHAR(32) NOT NULL,
    table_name          VARCHAR(64) NOT NULL,
    last_updated_time   BIGINT NOT NULL,
    cache_key           VARCHAR(128) NOT NULL,
    cache_version       BIGINT NOT NULL DEFAULT 1,

    CONSTRAINT pk_crm_cache_sync_status PRIMARY KEY (entity_id, table_name)
);

CREATE INDEX idx_crm_cache_sync_01 ON t_crm_cache_sync_status (last_updated_time);
CREATE INDEX idx_crm_cache_sync_02 ON t_crm_cache_sync_status (cache_key);

COMMENT ON TABLE t_crm_cache_sync_status IS '缓存同步状态表 - 维护缓存一致性';

-- 7.4 历史数据归档表
DROP TABLE IF EXISTS t_crm_identity_entity_history;
CREATE TABLE t_crm_identity_entity_history (
    LIKE t_crm_identity_entity INCLUDING DEFAULTS,
    archive_time BIGINT NOT NULL,
    archive_reason VARCHAR(32) NOT NULL
);

CREATE INDEX idx_crm_entity_history_01 ON t_crm_identity_entity_history (entity_id);
CREATE INDEX idx_crm_entity_history_02 ON t_crm_identity_entity_history (archive_time);
CREATE INDEX idx_crm_entity_history_03 ON t_crm_identity_entity_history (archive_reason, archive_time);

COMMENT ON TABLE t_crm_identity_entity_history IS '身份实体历史表 - 归档历史身份数据';

-- 8. 外键约束（根据实际需要添加）
-- =============================================

-- 注意：在生产环境中，建议根据业务需求添加外键约束
/*
ALTER TABLE t_crm_strong_identity ADD CONSTRAINT fk_crm_strong_identity_entity
    FOREIGN KEY (entity_id) REFERENCES t_crm_identity_entity(entity_id);

ALTER TABLE t_crm_weak_identity ADD CONSTRAINT fk_crm_weak_identity_entity
    FOREIGN KEY (entity_id) REFERENCES t_crm_identity_entity(entity_id);

ALTER TABLE t_crm_identity_relation ADD CONSTRAINT fk_crm_relation_from_entity
    FOREIGN KEY (from_entity_id) REFERENCES t_crm_identity_entity(entity_id);

ALTER TABLE t_crm_identity_relation ADD CONSTRAINT fk_crm_relation_to_entity
    FOREIGN KEY (to_entity_id) REFERENCES t_crm_identity_entity(entity_id);

ALTER TABLE t_crm_channel_registration ADD CONSTRAINT fk_crm_channel_entity
    FOREIGN KEY (entity_id) REFERENCES t_crm_identity_entity(entity_id);

ALTER TABLE t_crm_unified_profile ADD CONSTRAINT fk_crm_profile_entity
    FOREIGN KEY (entity_id) REFERENCES t_crm_identity_entity(entity_id);

ALTER TABLE t_crm_points_account ADD CONSTRAINT fk_crm_points_entity
    FOREIGN KEY (entity_id) REFERENCES t_crm_identity_entity(entity_id);

ALTER TABLE t_crm_coupon ADD CONSTRAINT fk_crm_coupon_entity
    FOREIGN KEY (entity_id) REFERENCES t_crm_identity_entity(entity_id);
*/

-- =============================================
-- 初始化数据
-- =============================================

-- 插入系统用户身份（用于系统操作）
INSERT INTO t_crm_identity_entity (entity_id, entity_status, entity_level, global_user_id, identity_score, trust_score, risk_score, is_hot_data, first_seen_time, last_active_time, create_time, update_time)
VALUES ('system_0000000000000000000000000', 'ACTIVE', 'L4', 'SYSTEM_USER', 100.00, 100.00, 0.00, true,
        EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000,
        EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000);

-- 插入匿名用户身份（用于未登录用户）
INSERT INTO t_crm_identity_entity (entity_id, entity_status, entity_level, global_user_id, identity_score, trust_score, risk_score, is_hot_data, first_seen_time, last_active_time, create_time, update_time)
VALUES ('anonymous_0000000000000000000000', 'ACTIVE', 'L0', 'ANONYMOUS_USER', 0.00, 0.00, 0.00, false,
        EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000,
        EXTRACT(EPOCH FROM NOW()) * 1000, EXTRACT(EPOCH FROM NOW()) * 1000);

-- =============================================
-- 视图创建（便于查询）
-- =============================================

-- 身份实体完整视图
CREATE OR REPLACE VIEW v_crm_identity_entity_complete AS
SELECT
    ie.entity_id,
    ie.entity_status,
    ie.entity_level,
    ie.global_user_id,
    ie.identity_score,
    ie.trust_score,
    ie.risk_score,
    ie.first_seen_time,
    ie.last_active_time,
    up.real_name,
    up.phone_number,
    up.email,
    up.membership_level,
    ilc.lifecycle_stage,
    pa.available_points as points_balance
FROM t_crm_identity_entity ie
LEFT JOIN t_crm_unified_profile up ON ie.entity_id = up.entity_id AND up.profile_type = 'BASIC'
LEFT JOIN t_crm_identity_lifecycle ilc ON ie.entity_id = ilc.entity_id
LEFT JOIN t_crm_points_account pa ON ie.entity_id = pa.entity_id
WHERE ie.entity_status = 'ACTIVE';

-- 身份标识汇总视图
CREATE OR REPLACE VIEW v_crm_identity_identifiers AS
SELECT
    ie.entity_id,
    ie.entity_level,
    si.identity_type as strong_identity_type,
    si.identity_value as strong_identity_value,
    si.verification_level,
    wi.identity_type as weak_identity_type,
    wi.identity_hash as weak_identity_hash,
    wi.confidence_score
FROM t_crm_identity_entity ie
LEFT JOIN t_crm_strong_identity si ON ie.entity_id = si.entity_id AND si.status = 'ACTIVE'
LEFT JOIN t_crm_weak_identity wi ON ie.entity_id = wi.entity_id
WHERE ie.entity_status = 'ACTIVE';

COMMENT ON VIEW v_crm_identity_entity_complete IS '身份实体完整视图 - 包含身份基本信息和业务属性';
COMMENT ON VIEW v_crm_identity_identifiers IS '身份标识汇总视图 - 展示所有身份标识信息';