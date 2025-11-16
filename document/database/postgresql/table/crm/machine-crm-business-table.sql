-- 统一用户档案表
DROP TABLE IF EXISTS t_crm_unified_profile;
CREATE TABLE t_crm_unified_profile (
    id                  VARCHAR(32) NOT NULL,
    identity_id           VARCHAR(32) NOT NULL,
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
    CONSTRAINT uk_crm_unified_profile UNIQUE (identity_id, profile_type)
);

CREATE INDEX idx_crm_unified_profile_01 ON t_crm_unified_profile (real_name);
CREATE INDEX idx_crm_unified_profile_02 ON t_crm_unified_profile (phone_number) WHERE phone_number IS NOT NULL;
CREATE INDEX idx_crm_unified_profile_03 ON t_crm_unified_profile (id_card_number) WHERE id_card_number IS NOT NULL;
CREATE INDEX idx_crm_unified_profile_04 ON t_crm_unified_profile (membership_level);
CREATE INDEX idx_crm_unified_profile_05 ON t_crm_unified_profile (create_time);

COMMENT ON TABLE t_crm_unified_profile IS '统一用户档案表 - 存储用户业务属性';
COMMENT ON COLUMN t_crm_unified_profile.profile_type IS '档案类型：BASIC-基础档案, PREFERENCE-偏好档案, BEHAVIOR-行为档案, RISK-风险档案';

-- 用户行为标签表
DROP TABLE IF EXISTS t_crm_behavior_tag;
CREATE TABLE t_crm_behavior_tag (
    id                  VARCHAR(32) NOT NULL,
    identity_id           VARCHAR(32) NOT NULL,
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

CREATE INDEX idx_crm_behavior_tag_01 ON t_crm_behavior_tag (identity_id, tag_category);
CREATE INDEX idx_crm_behavior_tag_02 ON t_crm_behavior_tag (tag_name, tag_value);
CREATE INDEX idx_crm_behavior_tag_03 ON t_crm_behavior_tag (effective_time, expires_time);
CREATE INDEX idx_crm_behavior_tag_04 ON t_crm_behavior_tag (tag_source, create_time);

COMMENT ON TABLE t_crm_behavior_tag IS '用户行为标签表 - 用户画像标签管理';
COMMENT ON COLUMN t_crm_behavior_tag.tag_category IS '标签分类：PURCHASE_PREFERENCE-购买偏好, ACTIVITY_LEVEL-活跃度, DEVICE_PREFERENCE-设备偏好';


-- 积分账户表
DROP TABLE IF EXISTS t_crm_points_account;
CREATE TABLE t_crm_points_account (
    id                  VARCHAR(32) NOT NULL,
    identity_id           VARCHAR(32) NOT NULL,
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
    CONSTRAINT uk_crm_points_account UNIQUE (identity_id)
);

CREATE INDEX idx_crm_points_account_01 ON t_crm_points_account (points_status);
CREATE INDEX idx_crm_points_account_02 ON t_crm_points_account (available_points);
CREATE INDEX idx_crm_points_account_03 ON t_crm_points_account (last_earn_time);

COMMENT ON TABLE t_crm_points_account IS '积分账户表 - 管理用户积分';
COMMENT ON COLUMN t_crm_points_account.points_status IS '状态：ACTIVE-活跃, FROZEN-冻结, CLOSED-关闭';

-- 优惠券表
DROP TABLE IF EXISTS t_crm_coupon;
CREATE TABLE t_crm_coupon (
    id                  VARCHAR(32) NOT NULL,
    identity_id           VARCHAR(32) NOT NULL,
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

CREATE INDEX idx_crm_coupon_01 ON t_crm_coupon (identity_id, status);
CREATE INDEX idx_crm_coupon_02 ON t_crm_coupon (coupon_code);
CREATE INDEX idx_crm_coupon_03 ON t_crm_coupon (valid_to, status);
CREATE INDEX idx_crm_coupon_04 ON t_crm_coupon (coupon_template_id);
CREATE INDEX idx_crm_coupon_05 ON t_crm_coupon (create_time);

COMMENT ON TABLE t_crm_coupon IS '优惠券表 - 管理用户优惠券';
COMMENT ON COLUMN t_crm_coupon.coupon_type IS '优惠券类型：DISCOUNT-折扣券, AMOUNT-金额券, GIFT-礼品券';
COMMENT ON COLUMN t_crm_coupon.status IS '状态：UNUSED-未使用, USED-已使用, EXPIRED-已过期, FROZEN-冻结';
