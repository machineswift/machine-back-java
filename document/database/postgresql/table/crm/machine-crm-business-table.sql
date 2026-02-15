-- =============================================
-- 统一身份管理系统 - 业务数据表
-- 设计理念：业务属性管理 + 用户画像 + 营销工具
-- 版本：2.0

-- 档案管理：统一档案、行为标签
-- 会员体系：积分账户、积分流水
-- 营销工具：优惠券、优惠券模板
-- 行为分析：访问记录、搜索记录
-- 业务视图：用户画像、积分统计
-- =============================================

-- 1. 统一档案管理
-- =============================================

-- 1.1 统一档案表
DROP TABLE IF EXISTS t_crm_unified_profile;
CREATE TABLE t_crm_unified_profile
(
    id              VARCHAR(32) NOT NULL,
    identity_id     VARCHAR(32) NOT NULL,
    profile_type    VARCHAR(32) NOT NULL,
    profile_data    JSONB NOT NULL,
    profile_version INTEGER NOT NULL DEFAULT 1,
    is_primary      BOOLEAN NOT NULL DEFAULT FALSE,
    source_channel  VARCHAR(32),
    source_id       VARCHAR(64),
    status          VARCHAR(16) NOT NULL DEFAULT 'ACTIVE',
    create_time     BIGINT NOT NULL,
    update_time     BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_unified_profile PRIMARY KEY (id)
);

CREATE INDEX idx_crm_unified_profile_01 ON t_crm_unified_profile (identity_id, profile_type);
CREATE INDEX idx_crm_unified_profile_02 ON t_crm_unified_profile (profile_type, status);
CREATE INDEX idx_crm_unified_profile_03 ON t_crm_unified_profile (source_channel, source_id);
CREATE INDEX idx_crm_unified_profile_04 ON t_crm_unified_profile USING gin (profile_data);

COMMENT ON TABLE t_crm_unified_profile IS '统一档案表 - 存储用户统一档案信息';
COMMENT ON COLUMN t_crm_unified_profile.id IS 'ID';
COMMENT ON COLUMN t_crm_unified_profile.identity_id IS '身份ID';
COMMENT ON COLUMN t_crm_unified_profile.profile_type IS '档案类型：BASIC-基础档案, EXTENDED-扩展档案, PREFERENCE-偏好档案';
COMMENT ON COLUMN t_crm_unified_profile.profile_data IS '档案数据，JSON格式存储';
COMMENT ON COLUMN t_crm_unified_profile.profile_version IS '档案版本号';
COMMENT ON COLUMN t_crm_unified_profile.is_primary IS '是否为主档案';
COMMENT ON COLUMN t_crm_unified_profile.source_channel IS '来源渠道';
COMMENT ON COLUMN t_crm_unified_profile.source_id IS '来源ID';
COMMENT ON COLUMN t_crm_unified_profile.status IS '状态：ACTIVE-活跃, INACTIVE-停用';
COMMENT ON COLUMN t_crm_unified_profile.create_time IS '创建时间';
COMMENT ON COLUMN t_crm_unified_profile.update_time IS '更新时间';

-- 1.2 用户行为标签表
DROP TABLE IF EXISTS t_crm_behavior_tag;
CREATE TABLE t_crm_behavior_tag
(
    id              VARCHAR(32) NOT NULL,
    identity_id     VARCHAR(32) NOT NULL,
    tag_category    VARCHAR(64) NOT NULL,
    tag_name        VARCHAR(128) NOT NULL,
    tag_value       DECIMAL(5,2) NOT NULL,
    tag_source      VARCHAR(64) NOT NULL,
    effective_time  BIGINT NOT NULL,
    expires_time    BIGINT,
    confidence_score DECIMAL(3,2) NOT NULL,
    create_time     BIGINT NOT NULL,

    CONSTRAINT pk_crm_behavior_tag PRIMARY KEY (id)
);

CREATE INDEX idx_crm_behavior_tag_01 ON t_crm_behavior_tag (identity_id, tag_category);
CREATE INDEX idx_crm_behavior_tag_02 ON t_crm_behavior_tag (tag_name, tag_value);
CREATE INDEX idx_crm_behavior_tag_03 ON t_crm_behavior_tag (effective_time, expires_time);
CREATE INDEX idx_crm_behavior_tag_04 ON t_crm_behavior_tag (tag_source, create_time);

COMMENT ON TABLE t_crm_behavior_tag IS '用户行为标签表 - 用户画像标签管理';
COMMENT ON COLUMN t_crm_behavior_tag.id IS 'ID';
COMMENT ON COLUMN t_crm_behavior_tag.identity_id IS '身份ID';
COMMENT ON COLUMN t_crm_behavior_tag.tag_category IS '标签分类：PURCHASE_PREFERENCE-购买偏好, ACTIVITY_LEVEL-活跃度, DEVICE_PREFERENCE-设备偏好';
COMMENT ON COLUMN t_crm_behavior_tag.tag_name IS '标签名称';
COMMENT ON COLUMN t_crm_behavior_tag.tag_value IS '标签值';
COMMENT ON COLUMN t_crm_behavior_tag.tag_source IS '标签来源';
COMMENT ON COLUMN t_crm_behavior_tag.effective_time IS '生效时间';
COMMENT ON COLUMN t_crm_behavior_tag.expires_time IS '过期时间';
COMMENT ON COLUMN t_crm_behavior_tag.confidence_score IS '置信度分数，0-1之间';
COMMENT ON COLUMN t_crm_behavior_tag.create_time IS '创建时间';

-- 2. 积分与会员体系
-- =============================================

-- 2.1 积分账户表
DROP TABLE IF EXISTS t_crm_points_account;
CREATE TABLE t_crm_points_account
(
    id                  VARCHAR(32) NOT NULL,
    identity_id         VARCHAR(32) NOT NULL,
    available_points    INTEGER NOT NULL DEFAULT 0,
    frozen_points       INTEGER NOT NULL DEFAULT 0,
    total_earned_points INTEGER NOT NULL DEFAULT 0,
    total_used_points   INTEGER NOT NULL DEFAULT 0,
    expired_points      INTEGER NOT NULL DEFAULT 0,
    membership_level    VARCHAR(32),
    level_expire_time   BIGINT,
    last_earn_time      BIGINT,
    last_use_time       BIGINT,
    status              VARCHAR(16) NOT NULL DEFAULT 'ACTIVE',
    version             BIGINT NOT NULL DEFAULT 1,
    create_time         BIGINT NOT NULL,
    update_time         BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_points_account PRIMARY KEY (id),
    CONSTRAINT uk_crm_points_account UNIQUE (identity_id)
);

CREATE INDEX idx_crm_points_account_01 ON t_crm_points_account (identity_id, status);
CREATE INDEX idx_crm_points_account_02 ON t_crm_points_account (membership_level, available_points);
CREATE INDEX idx_crm_points_account_03 ON t_crm_points_account (total_earned_points, create_time);
CREATE INDEX idx_crm_points_account_04 ON t_crm_points_account (last_earn_time);
CREATE INDEX idx_crm_points_account_05 ON t_crm_points_account (last_use_time);

COMMENT ON TABLE t_crm_points_account IS '积分账户表 - 管理用户积分信息';
COMMENT ON COLUMN t_crm_points_account.id IS 'ID';
COMMENT ON COLUMN t_crm_points_account.identity_id IS '身份ID';
COMMENT ON COLUMN t_crm_points_account.available_points IS '可用积分';
COMMENT ON COLUMN t_crm_points_account.frozen_points IS '冻结积分';
COMMENT ON COLUMN t_crm_points_account.total_earned_points IS '累计获得积分';
COMMENT ON COLUMN t_crm_points_account.total_used_points IS '累计使用积分';
COMMENT ON COLUMN t_crm_points_account.expired_points IS '过期积分';
COMMENT ON COLUMN t_crm_points_account.membership_level IS '会员等级';
COMMENT ON COLUMN t_crm_points_account.level_expire_time IS '等级过期时间';
COMMENT ON COLUMN t_crm_points_account.last_earn_time IS '最后获得积分时间';
COMMENT ON COLUMN t_crm_points_account.last_use_time IS '最后使用积分时间';
COMMENT ON COLUMN t_crm_points_account.status IS '状态：ACTIVE-活跃, FROZEN-冻结, CLOSED-关闭';
COMMENT ON COLUMN t_crm_points_account.version IS '版本号，用于乐观锁';
COMMENT ON COLUMN t_crm_points_account.create_time IS '创建时间';
COMMENT ON COLUMN t_crm_points_account.update_time IS '更新时间';

-- 2.2 积分流水表
DROP TABLE IF EXISTS t_crm_points_transaction;
CREATE TABLE t_crm_points_transaction
(
    id                  VARCHAR(32) NOT NULL,
    identity_id         VARCHAR(32) NOT NULL,
    account_id          VARCHAR(32) NOT NULL,
    transaction_type   VARCHAR(32) NOT NULL,
    points_change       INTEGER NOT NULL,
    available_balance   INTEGER NOT NULL,
    frozen_balance      INTEGER NOT NULL,
    transaction_time    BIGINT NOT NULL,
    expire_time         BIGINT,
    reference_type      VARCHAR(32),
    reference_id        VARCHAR(64),
    description         VARCHAR(256),
    operator_id         VARCHAR(32),
    create_time         BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_points_transaction PRIMARY KEY (id)
);

CREATE INDEX idx_crm_points_transaction_01 ON t_crm_points_transaction (identity_id, transaction_time);
CREATE INDEX idx_crm_points_transaction_02 ON t_crm_points_transaction (account_id, transaction_type);
CREATE INDEX idx_crm_points_transaction_03 ON t_crm_points_transaction (reference_type, reference_id);
CREATE INDEX idx_crm_points_transaction_04 ON t_crm_points_transaction (transaction_time);

COMMENT ON TABLE t_crm_points_transaction IS '积分流水表 - 记录积分变动明细';
COMMENT ON COLUMN t_crm_points_transaction.id IS 'ID';
COMMENT ON COLUMN t_crm_points_transaction.identity_id IS '身份ID';
COMMENT ON COLUMN t_crm_points_transaction.account_id IS '积分账户ID';
COMMENT ON COLUMN t_crm_points_transaction.transaction_type IS '交易类型：EARN-获取, USE-使用, EXPIRE-过期, ADJUST-调整';
COMMENT ON COLUMN t_crm_points_transaction.points_change IS '积分变动数量，正数表示增加，负数表示减少';
COMMENT ON COLUMN t_crm_points_transaction.available_balance IS '变动后可用余额';
COMMENT ON COLUMN t_crm_points_transaction.frozen_balance IS '变动后冻结余额';
COMMENT ON COLUMN t_crm_points_transaction.transaction_time IS '交易时间';
COMMENT ON COLUMN t_crm_points_transaction.expire_time IS '积分过期时间';
COMMENT ON COLUMN t_crm_points_transaction.reference_type IS '关联业务类型';
COMMENT ON COLUMN t_crm_points_transaction.reference_id IS '关联业务ID';
COMMENT ON COLUMN t_crm_points_transaction.description IS '描述';
COMMENT ON COLUMN t_crm_points_transaction.operator_id IS '操作人ID';
COMMENT ON COLUMN t_crm_points_transaction.create_time IS '创建时间';

-- 3. 营销工具
-- =============================================

-- 3.1 优惠券表
DROP TABLE IF EXISTS t_crm_coupon;
CREATE TABLE t_crm_coupon
(
    id                  VARCHAR(32) NOT NULL,
    identity_id         VARCHAR(32) NOT NULL,
    coupon_template_id  VARCHAR(32) NOT NULL,
    coupon_code         VARCHAR(64) NOT NULL,
    coupon_name         VARCHAR(128) NOT NULL,
    coupon_type         VARCHAR(32) NOT NULL,
    face_value          DECIMAL(10,2),
    discount_rate       DECIMAL(5,2),
    min_order_amount    DECIMAL(10,2),
    condition_amount    DECIMAL(10,2) DEFAULT 0,
    applicable_products JSONB,
    excluded_products   JSONB,
    valid_from          BIGINT NOT NULL,
    valid_to            BIGINT NOT NULL,
    status              VARCHAR(16) NOT NULL DEFAULT 'UNUSED',
    used_time           BIGINT,
    used_order_id       VARCHAR(32),
    order_id            VARCHAR(32),
    source_channel      VARCHAR(32),
    create_time         BIGINT NOT NULL,
    update_time         BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_coupon PRIMARY KEY (id),
    CONSTRAINT uk_crm_coupon_code UNIQUE (coupon_code)
);

CREATE INDEX idx_crm_coupon_01 ON t_crm_coupon (identity_id, status);
CREATE INDEX idx_crm_coupon_02 ON t_crm_coupon (coupon_template_id, status);
CREATE INDEX idx_crm_coupon_03 ON t_crm_coupon (valid_from, valid_to);
CREATE INDEX idx_crm_coupon_04 ON t_crm_coupon (status, valid_to);
CREATE INDEX idx_crm_coupon_05 ON t_crm_coupon (coupon_code);
CREATE INDEX idx_crm_coupon_06 ON t_crm_coupon (create_time);

COMMENT ON TABLE t_crm_coupon IS '优惠券表 - 管理用户优惠券信息';
COMMENT ON COLUMN t_crm_coupon.id IS 'ID';
COMMENT ON COLUMN t_crm_coupon.identity_id IS '身份ID';
COMMENT ON COLUMN t_crm_coupon.coupon_template_id IS '优惠券模板ID';
COMMENT ON COLUMN t_crm_coupon.coupon_code IS '优惠券编码';
COMMENT ON COLUMN t_crm_coupon.coupon_name IS '优惠券名称';
COMMENT ON COLUMN t_crm_coupon.coupon_type IS '优惠券类型：DISCOUNT-折扣券, AMOUNT-金额券, GIFT-礼品券';
COMMENT ON COLUMN t_crm_coupon.face_value IS '面值';
COMMENT ON COLUMN t_crm_coupon.discount_rate IS '折扣率';
COMMENT ON COLUMN t_crm_coupon.min_order_amount IS '最低订单金额';
COMMENT ON COLUMN t_crm_coupon.condition_amount IS '条件金额';
COMMENT ON COLUMN t_crm_coupon.applicable_products IS '适用商品列表，JSON格式';
COMMENT ON COLUMN t_crm_coupon.excluded_products IS '排除商品列表，JSON格式';
COMMENT ON COLUMN t_crm_coupon.valid_from IS '生效开始时间';
COMMENT ON COLUMN t_crm_coupon.valid_to IS '生效结束时间';
COMMENT ON COLUMN t_crm_coupon.status IS '状态：UNUSED-未使用, USED-已使用, EXPIRED-已过期, FROZEN-冻结';
COMMENT ON COLUMN t_crm_coupon.used_time IS '使用时间';
COMMENT ON COLUMN t_crm_coupon.used_order_id IS '使用的订单ID';
COMMENT ON COLUMN t_crm_coupon.order_id IS '订单ID';
COMMENT ON COLUMN t_crm_coupon.source_channel IS '来源渠道';
COMMENT ON COLUMN t_crm_coupon.create_time IS '创建时间';
COMMENT ON COLUMN t_crm_coupon.update_time IS '更新时间';

-- 3.2 优惠券模板表
DROP TABLE IF EXISTS t_crm_coupon_template;
CREATE TABLE t_crm_coupon_template
(
    id                  VARCHAR(32) NOT NULL,
    template_name       VARCHAR(128) NOT NULL,
    template_type       VARCHAR(32) NOT NULL,
    face_value          DECIMAL(10,2),
    discount_rate       DECIMAL(5,2),
    min_order_amount    DECIMAL(10,2),
    condition_amount    DECIMAL(10,2) DEFAULT 0,
    applicable_products JSONB,
    excluded_products   JSONB,
    total_quantity      INTEGER,
    remaining_quantity  INTEGER,
    limit_per_user       INTEGER DEFAULT 1,
    valid_days           INTEGER,
    valid_from           BIGINT NOT NULL,
    valid_to             BIGINT NOT NULL,
    status               VARCHAR(16) NOT NULL DEFAULT 'ACTIVE',
    description          VARCHAR(512),
    create_time          BIGINT NOT NULL,
    update_time          BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_coupon_template PRIMARY KEY (id)
);

CREATE INDEX idx_crm_coupon_template_01 ON t_crm_coupon_template (template_type, status);
CREATE INDEX idx_crm_coupon_template_02 ON t_crm_coupon_template (valid_from, valid_to);
CREATE INDEX idx_crm_coupon_template_03 ON t_crm_coupon_template (status, create_time);

COMMENT ON TABLE t_crm_coupon_template IS '优惠券模板表 - 管理优惠券模板';
COMMENT ON COLUMN t_crm_coupon_template.id IS 'ID';
COMMENT ON COLUMN t_crm_coupon_template.template_name IS '模板名称';
COMMENT ON COLUMN t_crm_coupon_template.template_type IS '模板类型';
COMMENT ON COLUMN t_crm_coupon_template.face_value IS '面值';
COMMENT ON COLUMN t_crm_coupon_template.discount_rate IS '折扣率';
COMMENT ON COLUMN t_crm_coupon_template.min_order_amount IS '最低订单金额';
COMMENT ON COLUMN t_crm_coupon_template.condition_amount IS '条件金额';
COMMENT ON COLUMN t_crm_coupon_template.applicable_products IS '适用商品列表，JSON格式';
COMMENT ON COLUMN t_crm_coupon_template.excluded_products IS '排除商品列表，JSON格式';
COMMENT ON COLUMN t_crm_coupon_template.total_quantity IS '总数量';
COMMENT ON COLUMN t_crm_coupon_template.remaining_quantity IS '剩余数量';
COMMENT ON COLUMN t_crm_coupon_template.limit_per_user IS '每用户限领数量';
COMMENT ON COLUMN t_crm_coupon_template.valid_days IS '有效天数';
COMMENT ON COLUMN t_crm_coupon_template.valid_from IS '生效开始时间';
COMMENT ON COLUMN t_crm_coupon_template.valid_to IS '生效结束时间';
COMMENT ON COLUMN t_crm_coupon_template.status IS '状态：ACTIVE-活跃, INACTIVE-停用, EXPIRED-过期';
COMMENT ON COLUMN t_crm_coupon_template.description IS '描述';
COMMENT ON COLUMN t_crm_coupon_template.create_time IS '创建时间';
COMMENT ON COLUMN t_crm_coupon_template.update_time IS '更新时间';

-- 4. 用户行为分析
-- =============================================

-- 4.1 用户访问记录表
DROP TABLE IF EXISTS t_crm_user_visit;
CREATE TABLE t_crm_user_visit
(
    id                  VARCHAR(32) NOT NULL,
    identity_id         VARCHAR(32) NOT NULL,
    session_id          VARCHAR(64) NOT NULL,
    visit_time          BIGINT NOT NULL,
    page_url            VARCHAR(512) NOT NULL,
    page_title          VARCHAR(256),
    referrer_url        VARCHAR(512),
    stay_duration       INTEGER,
    device_type         VARCHAR(32),
    browser_type        VARCHAR(64),
    operating_system    VARCHAR(64),
    screen_resolution   VARCHAR(32),
    ip_address          VARCHAR(64),
    country             VARCHAR(64),
    region              VARCHAR(64),
    city                VARCHAR(64),
    create_time         BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_user_visit PRIMARY KEY (id)
);

CREATE INDEX idx_crm_user_visit_01 ON t_crm_user_visit (identity_id, visit_time);
CREATE INDEX idx_crm_user_visit_02 ON t_crm_user_visit (session_id);
CREATE INDEX idx_crm_user_visit_03 ON t_crm_user_visit (visit_time);
CREATE INDEX idx_crm_user_visit_04 ON t_crm_user_visit (device_type, visit_time);

COMMENT ON TABLE t_crm_user_visit IS '用户访问记录表 - 记录用户访问行为';
COMMENT ON COLUMN t_crm_user_visit.id IS 'ID';
COMMENT ON COLUMN t_crm_user_visit.identity_id IS '身份ID';
COMMENT ON COLUMN t_crm_user_visit.session_id IS '会话ID';
COMMENT ON COLUMN t_crm_user_visit.visit_time IS '访问时间';
COMMENT ON COLUMN t_crm_user_visit.page_url IS '页面URL';
COMMENT ON COLUMN t_crm_user_visit.page_title IS '页面标题';
COMMENT ON COLUMN t_crm_user_visit.referrer_url IS '来源URL';
COMMENT ON COLUMN t_crm_user_visit.stay_duration IS '停留时长，单位：秒';
COMMENT ON COLUMN t_crm_user_visit.device_type IS '设备类型';
COMMENT ON COLUMN t_crm_user_visit.browser_type IS '浏览器类型';
COMMENT ON COLUMN t_crm_user_visit.operating_system IS '操作系统';
COMMENT ON COLUMN t_crm_user_visit.screen_resolution IS '屏幕分辨率';
COMMENT ON COLUMN t_crm_user_visit.ip_address IS 'IP地址';
COMMENT ON COLUMN t_crm_user_visit.country IS '国家';
COMMENT ON COLUMN t_crm_user_visit.region IS '地区';
COMMENT ON COLUMN t_crm_user_visit.city IS '城市';
COMMENT ON COLUMN t_crm_user_visit.create_time IS '创建时间';

-- 4.2 用户搜索记录表
DROP TABLE IF EXISTS t_crm_user_search;
CREATE TABLE t_crm_user_search
(
    id                  VARCHAR(32) NOT NULL,
    identity_id         VARCHAR(32) NOT NULL,
    search_keyword      VARCHAR(256) NOT NULL,
    search_time         BIGINT NOT NULL,
    search_result_count INTEGER,
    clicked_product_ids JSONB,
    device_type         VARCHAR(32),
    search_source       VARCHAR(32),
    create_time         BIGINT NOT NULL,

    CONSTRAINT pk_t_crm_user_search PRIMARY KEY (id)
);

CREATE INDEX idx_crm_user_search_01 ON t_crm_user_search (identity_id, search_time);
CREATE INDEX idx_crm_user_search_02 ON t_crm_user_search (search_keyword, search_time);
CREATE INDEX idx_crm_user_search_03 ON t_crm_user_search (search_source, search_time);

COMMENT ON TABLE t_crm_user_search IS '用户搜索记录表 - 记录用户搜索行为';
COMMENT ON COLUMN t_crm_user_search.id IS 'ID';
COMMENT ON COLUMN t_crm_user_search.identity_id IS '身份ID';
COMMENT ON COLUMN t_crm_user_search.search_keyword IS '搜索关键词';
COMMENT ON COLUMN t_crm_user_search.search_time IS '搜索时间';
COMMENT ON COLUMN t_crm_user_search.search_result_count IS '搜索结果数量';
COMMENT ON COLUMN t_crm_user_search.clicked_product_ids IS '点击的商品ID列表，JSON格式';
COMMENT ON COLUMN t_crm_user_search.device_type IS '设备类型';
COMMENT ON COLUMN t_crm_user_search.search_source IS '搜索来源';
COMMENT ON COLUMN t_crm_user_search.create_time IS '创建时间';

-- 5. 业务统计视图
-- =============================================

-- 用户完整业务画像视图
CREATE OR REPLACE VIEW v_crm_user_business_profile AS
SELECT
    i.identity_id,
    i.identity_level,
    i.identity_score,
    i.trust_score,
    i.last_active_time,
    up.profile_data->>'real_name' as real_name,
    up.profile_data->>'phone_number' as phone_number,
    up.profile_data->>'email' as email,
    up.profile_data->>'membership_level' as membership_level,
    up.profile_data->>'gender' as gender,
    pa.available_points,
    pa.membership_level as points_level,
    pa.total_earned_points,
    (SELECT COUNT(*) FROM t_crm_coupon c WHERE c.identity_id = i.identity_id AND c.status = 'UNUSED') as unused_coupon_count,
    (SELECT COUNT(*) FROM t_crm_behavior_tag bt WHERE bt.identity_id = i.identity_id AND bt.expires_time > EXTRACT(EPOCH FROM NOW()) * 1000) as active_tag_count,
    ilc.lifecycle_stage
FROM t_crm_identity i
    LEFT JOIN t_crm_unified_profile up ON i.identity_id = up.identity_id AND up.profile_type = 'BASIC' AND up.status = 'ACTIVE'
    LEFT JOIN t_crm_points_account pa ON i.identity_id = pa.identity_id AND pa.status = 'ACTIVE'
    LEFT JOIN t_crm_identity_lifecycle ilc ON i.identity_id = ilc.identity_id
WHERE i.identity_status = 'ACTIVE';

COMMENT ON VIEW v_crm_user_business_profile IS '用户完整业务画像视图 - 包含身份信息和业务属性';

-- 用户积分统计视图
CREATE OR REPLACE VIEW v_crm_user_points_summary AS
SELECT
    i.identity_id,
    pa.available_points,
    pa.total_earned_points,
    pa.total_used_points,
    pa.expired_points,
    pa.membership_level,
    (SELECT COUNT(*) FROM t_crm_points_transaction pt WHERE pt.identity_id = i.identity_id AND pt.transaction_type = 'EARN' AND pt.transaction_time >= EXTRACT(EPOCH FROM NOW() - INTERVAL '30 days') * 1000) as earn_count_30d,
    (SELECT COALESCE(SUM(pt.points_change), 0) FROM t_crm_points_transaction pt WHERE pt.identity_id = i.identity_id AND pt.transaction_type = 'EARN' AND pt.transaction_time >= EXTRACT(EPOCH FROM NOW() - INTERVAL '30 days') * 1000) as earn_points_30d
FROM t_crm_identity i
    LEFT JOIN t_crm_points_account pa ON i.identity_id = pa.identity_id AND pa.status = 'ACTIVE'
WHERE i.identity_status = 'ACTIVE';

COMMENT ON VIEW v_crm_user_points_summary IS '用户积分统计视图 - 积分相关统计信息';

-- =============================================
-- 外键约束
-- =============================================

-- 业务表外键约束
ALTER TABLE t_crm_unified_profile ADD CONSTRAINT fk_crm_profile_identity
    FOREIGN KEY (identity_id) REFERENCES t_crm_identity(identity_id);

ALTER TABLE t_crm_behavior_tag ADD CONSTRAINT fk_crm_behavior_tag_identity
    FOREIGN KEY (identity_id) REFERENCES t_crm_identity(identity_id);

ALTER TABLE t_crm_points_account ADD CONSTRAINT fk_crm_points_identity
    FOREIGN KEY (identity_id) REFERENCES t_crm_identity(identity_id);

ALTER TABLE t_crm_points_transaction ADD CONSTRAINT fk_crm_points_transaction_identity
    FOREIGN KEY (identity_id) REFERENCES t_crm_identity(identity_id);

ALTER TABLE t_crm_points_transaction ADD CONSTRAINT fk_crm_points_transaction_account
    FOREIGN KEY (account_id) REFERENCES t_crm_points_account(id);

ALTER TABLE t_crm_coupon ADD CONSTRAINT fk_crm_coupon_identity
    FOREIGN KEY (identity_id) REFERENCES t_crm_identity(identity_id);

ALTER TABLE t_crm_user_visit ADD CONSTRAINT fk_crm_user_visit_identity
    FOREIGN KEY (identity_id) REFERENCES t_crm_identity(identity_id);

ALTER TABLE t_crm_user_search ADD CONSTRAINT fk_crm_user_search_identity
    FOREIGN KEY (identity_id) REFERENCES t_crm_identity(identity_id);
