DROP TABLE IF EXISTS t_iam_user;
CREATE TABLE t_iam_user
(
    id          VARCHAR(32) NOT NULL,
    username    VARCHAR(32) NOT NULL,
    status      VARCHAR(8) NOT NULL DEFAULT 'DISABLE',
    password    VARCHAR(256) NOT NULL,
    code        VARCHAR(32) NOT NULL DEFAULT '',
    phone       VARCHAR(16) NOT NULL DEFAULT '',
    name        VARCHAR(64) NOT NULL DEFAULT '',
    gender      VARCHAR(16) NOT NULL DEFAULT 'UNDEFINED',
    description VARCHAR(2048) NOT NULL DEFAULT '',
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_iam_user PRIMARY KEY (id),
    CONSTRAINT uk_t_iam_user_01 UNIQUE (username),
    CONSTRAINT uk_t_iam_user_02 UNIQUE (code),
    CONSTRAINT uk_t_iam_user_03 UNIQUE (phone)
);

CREATE INDEX idx_t_iam_user_01 ON t_iam_user (phone);
CREATE INDEX idx_t_iam_user_02 ON t_iam_user (create_time);
CREATE INDEX idx_t_iam_user_03 ON t_iam_user (update_time);

COMMENT ON TABLE t_iam_user IS '用户表';
COMMENT ON COLUMN t_iam_user.id IS 'ID';
COMMENT ON COLUMN t_iam_user.username IS '用户名（系统账号）';
COMMENT ON COLUMN t_iam_user.status IS '状态';
COMMENT ON COLUMN t_iam_user.password IS '密码，加密存储';
COMMENT ON COLUMN t_iam_user.code IS '编码（工号）';
COMMENT ON COLUMN t_iam_user.phone IS '手机号';
COMMENT ON COLUMN t_iam_user.name IS '姓名';
COMMENT ON COLUMN t_iam_user.gender IS '性别';
COMMENT ON COLUMN t_iam_user.description IS '描述';
COMMENT ON COLUMN t_iam_user.create_by IS '创建人';
COMMENT ON COLUMN t_iam_user.create_time IS '创建时间';
COMMENT ON COLUMN t_iam_user.update_by IS '修改人';
COMMENT ON COLUMN t_iam_user.update_time IS '更新时间';

DROP TABLE IF EXISTS t_iam_user_type_relation;
CREATE TABLE t_iam_user_type_relation
(
    id          VARCHAR(32) NOT NULL,
    user_id     VARCHAR(32) NOT NULL,
    user_type   VARCHAR(16) NOT NULL,
    sort        BIGINT NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_iam_user_type_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_iam_user_type_relation_01 UNIQUE (user_id, user_type)
);

COMMENT ON TABLE t_iam_user_type_relation IS '用户类型关系表';
COMMENT ON COLUMN t_iam_user_type_relation.id IS 'ID';
COMMENT ON COLUMN t_iam_user_type_relation.user_id IS '用户id';
COMMENT ON COLUMN t_iam_user_type_relation.user_type IS '类型（UserTypeEnum）';
COMMENT ON COLUMN t_iam_user_type_relation.sort IS '排序';
COMMENT ON COLUMN t_iam_user_type_relation.create_by IS '创建人';
COMMENT ON COLUMN t_iam_user_type_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_iam_user_type_relation.update_by IS '修改人';
COMMENT ON COLUMN t_iam_user_type_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_iam_role;
CREATE TABLE t_iam_role
(
    id                   VARCHAR(32) NOT NULL,
    parent_id            VARCHAR(32) NOT NULL,
    status               VARCHAR(8) NOT NULL DEFAULT 'DISABLE',
    code                 VARCHAR(32) NOT NULL,
    type                 VARCHAR(8) NOT NULL,
    name                 VARCHAR(32) NOT NULL,
    description          VARCHAR(2048) NOT NULL DEFAULT '',
    data_permission_rule TEXT,
    sort                 BIGINT NOT NULL DEFAULT 0,
    create_by            VARCHAR(32) NOT NULL,
    create_time          BIGINT NOT NULL,
    update_by            VARCHAR(32) NOT NULL,
    update_time          BIGINT NOT NULL,
    CONSTRAINT pk_t_iam_role PRIMARY KEY (id),
    CONSTRAINT uk_t_iam_role_01 UNIQUE (code),
    CONSTRAINT uk_t_iam_role_02 UNIQUE (parent_id, id)
);

CREATE INDEX idx_t_iam_role_01 ON t_iam_role (sort);
CREATE INDEX idx_t_iam_role_02 ON t_iam_role (create_time);
CREATE INDEX idx_t_iam_role_03 ON t_iam_role (update_time);

COMMENT ON TABLE t_iam_role IS '角色表';
COMMENT ON COLUMN t_iam_role.id IS 'ID';
COMMENT ON COLUMN t_iam_role.parent_id IS '父ID';
COMMENT ON COLUMN t_iam_role.status IS '状态,（DISABLE:禁用，ENABLE:启用）';
COMMENT ON COLUMN t_iam_role.code IS '编码';
COMMENT ON COLUMN t_iam_role.type IS '类型';
COMMENT ON COLUMN t_iam_role.name IS '名称';
COMMENT ON COLUMN t_iam_role.description IS '描述';
COMMENT ON COLUMN t_iam_role.data_permission_rule IS '数据权限规则';
COMMENT ON COLUMN t_iam_role.sort IS '排序';
COMMENT ON COLUMN t_iam_role.create_by IS '创建人';
COMMENT ON COLUMN t_iam_role.create_time IS '创建时间';
COMMENT ON COLUMN t_iam_role.update_by IS '修改人';
COMMENT ON COLUMN t_iam_role.update_time IS '更新时间';

DROP TABLE IF EXISTS t_iam_permission;
CREATE TABLE t_iam_permission
(
    id             VARCHAR(32) NOT NULL,
    parent_id      VARCHAR(32) NOT NULL,
    resource_type  VARCHAR(16) NOT NULL,
    code           VARCHAR(128) NOT NULL,
    name           VARCHAR(32) NOT NULL,
    icon           VARCHAR(256) NOT NULL DEFAULT '',
    data_meta_into VARCHAR(2048) NOT NULL DEFAULT '',
    description    VARCHAR(2048) NOT NULL DEFAULT '',
    sort           BIGINT NOT NULL DEFAULT 0,
    create_by      VARCHAR(32) NOT NULL,
    create_time    BIGINT NOT NULL,
    update_by      VARCHAR(32) NOT NULL,
    update_time    BIGINT NOT NULL,
    CONSTRAINT pk_t_iam_permission PRIMARY KEY (id),
    CONSTRAINT uk_t_iam_permission_01 UNIQUE (code),
    CONSTRAINT uk_t_iam_permission_02 UNIQUE (parent_id, id)
);

CREATE INDEX idx_t_iam_permission_01 ON t_iam_permission (create_time);
CREATE INDEX idx_t_iam_permission_02 ON t_iam_permission (update_time);

COMMENT ON TABLE t_iam_permission IS '权限表';
COMMENT ON COLUMN t_iam_permission.id IS 'ID';
COMMENT ON COLUMN t_iam_permission.parent_id IS '父ID';
COMMENT ON COLUMN t_iam_permission.resource_type IS '资源类型';
COMMENT ON COLUMN t_iam_permission.code IS '编码';
COMMENT ON COLUMN t_iam_permission.name IS '名称';
COMMENT ON COLUMN t_iam_permission.icon IS '图标';
COMMENT ON COLUMN t_iam_permission.data_meta_into IS '数据权限元数据';
COMMENT ON COLUMN t_iam_permission.description IS '描述';
COMMENT ON COLUMN t_iam_permission.sort IS '排序';
COMMENT ON COLUMN t_iam_permission.create_by IS '创建人';
COMMENT ON COLUMN t_iam_permission.create_time IS '创建时间';
COMMENT ON COLUMN t_iam_permission.update_by IS '修改人';
COMMENT ON COLUMN t_iam_permission.update_time IS '更新时间';

DROP TABLE IF EXISTS t_iam_group;
CREATE TABLE t_iam_group
(
    id          VARCHAR(32) NOT NULL,
    parent_id   VARCHAR(32) NOT NULL,
    name        VARCHAR(32) NOT NULL,
    description VARCHAR(2048) NOT NULL DEFAULT '',
    sort        BIGINT NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_iam_group PRIMARY KEY (id)
);

CREATE INDEX idx_t_iam_group_01 ON t_iam_group (parent_id);

COMMENT ON TABLE t_iam_group IS '分组表';
COMMENT ON COLUMN t_iam_group.id IS 'ID';
COMMENT ON COLUMN t_iam_group.parent_id IS '父ID';
COMMENT ON COLUMN t_iam_group.name IS '名称';
COMMENT ON COLUMN t_iam_group.description IS '描述';
COMMENT ON COLUMN t_iam_group.sort IS '排序';
COMMENT ON COLUMN t_iam_group.create_by IS '创建人';
COMMENT ON COLUMN t_iam_group.create_time IS '创建时间';
COMMENT ON COLUMN t_iam_group.update_by IS '修改人';
COMMENT ON COLUMN t_iam_group.update_time IS '更新时间';

DROP TABLE IF EXISTS t_iam_role_permission_relation;
CREATE TABLE t_iam_role_permission_relation
(
    id                    VARCHAR(32) NOT NULL,
    role_id               VARCHAR(32) NOT NULL,
    permission_id         VARCHAR(32) NOT NULL,
    type                  VARCHAR(8) NOT NULL DEFAULT 'READ',
    data_permission_rules TEXT,
    sort                  BIGINT NOT NULL DEFAULT 0,
    create_by             VARCHAR(32) NOT NULL,
    create_time           BIGINT NOT NULL,
    update_by             VARCHAR(32) NOT NULL,
    update_time           BIGINT NOT NULL,
    CONSTRAINT pk_t_iam_role_permission_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_iam_role_permission_relation_01 UNIQUE (role_id, permission_id)
);

COMMENT ON TABLE t_iam_role_permission_relation IS '角色权限关系表';
COMMENT ON COLUMN t_iam_role_permission_relation.id IS 'ID';
COMMENT ON COLUMN t_iam_role_permission_relation.role_id IS '角色ID';
COMMENT ON COLUMN t_iam_role_permission_relation.permission_id IS '权限id';
COMMENT ON COLUMN t_iam_role_permission_relation.type IS '权限类型,（READ:可访问，GRANT:可授权）';
COMMENT ON COLUMN t_iam_role_permission_relation.data_permission_rules IS '数据权限规则';
COMMENT ON COLUMN t_iam_role_permission_relation.sort IS '排序';
COMMENT ON COLUMN t_iam_role_permission_relation.create_by IS '创建人';
COMMENT ON COLUMN t_iam_role_permission_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_iam_role_permission_relation.update_by IS '修改人';
COMMENT ON COLUMN t_iam_role_permission_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_iam_group_permission_relation;
CREATE TABLE t_iam_group_permission_relation
(
    id            VARCHAR(32) NOT NULL,
    group_id      VARCHAR(32) NOT NULL,
    permission_id VARCHAR(32) NOT NULL,
    type          VARCHAR(8) NOT NULL DEFAULT 'READ',
    sort          BIGINT NOT NULL DEFAULT 0,
    create_by     VARCHAR(32) NOT NULL,
    create_time   BIGINT NOT NULL,
    update_by     VARCHAR(32) NOT NULL,
    update_time   BIGINT NOT NULL,
    CONSTRAINT pk_t_iam_group_permission_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_iam_group_permission_relation_01 UNIQUE (group_id, permission_id)
);

COMMENT ON TABLE t_iam_group_permission_relation IS '分组权限关系表';
COMMENT ON COLUMN t_iam_group_permission_relation.id IS 'ID';
COMMENT ON COLUMN t_iam_group_permission_relation.group_id IS '分组ID';
COMMENT ON COLUMN t_iam_group_permission_relation.permission_id IS '权限id';
COMMENT ON COLUMN t_iam_group_permission_relation.type IS '权限类型,（READ:可访问，GRANT:可授权）';
COMMENT ON COLUMN t_iam_group_permission_relation.sort IS '排序';
COMMENT ON COLUMN t_iam_group_permission_relation.create_by IS '创建人';
COMMENT ON COLUMN t_iam_group_permission_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_iam_group_permission_relation.update_by IS '修改人';
COMMENT ON COLUMN t_iam_group_permission_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_iam_group_role_relation;
CREATE TABLE t_iam_group_role_relation
(
    id          VARCHAR(32) NOT NULL,
    group_id    VARCHAR(32) NOT NULL,
    role_id     VARCHAR(32) NOT NULL,
    sort        BIGINT NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_iam_group_role_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_iam_group_role_relation_01 UNIQUE (group_id, role_id)
);

COMMENT ON TABLE t_iam_group_role_relation IS '分组角色关系表';
COMMENT ON COLUMN t_iam_group_role_relation.id IS 'ID';
COMMENT ON COLUMN t_iam_group_role_relation.group_id IS '分组ID';
COMMENT ON COLUMN t_iam_group_role_relation.role_id IS '角色id';
COMMENT ON COLUMN t_iam_group_role_relation.sort IS '排序';
COMMENT ON COLUMN t_iam_group_role_relation.create_by IS '创建人';
COMMENT ON COLUMN t_iam_group_role_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_iam_group_role_relation.update_by IS '修改人';
COMMENT ON COLUMN t_iam_group_role_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_iam_user_group_relation;
CREATE TABLE t_iam_user_group_relation
(
    id          VARCHAR(32) NOT NULL,
    user_id     VARCHAR(32) NOT NULL,
    group_id    VARCHAR(32) NOT NULL,
    sort        BIGINT NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_iam_user_group_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_iam_user_group_relation_01 UNIQUE (user_id, group_id)
);

COMMENT ON TABLE t_iam_user_group_relation IS '用户分组关系表';
COMMENT ON COLUMN t_iam_user_group_relation.id IS 'ID';
COMMENT ON COLUMN t_iam_user_group_relation.user_id IS '用户id';
COMMENT ON COLUMN t_iam_user_group_relation.group_id IS '组ID';
COMMENT ON COLUMN t_iam_user_group_relation.sort IS '排序';
COMMENT ON COLUMN t_iam_user_group_relation.create_by IS '创建人';
COMMENT ON COLUMN t_iam_user_group_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_iam_user_group_relation.update_by IS '修改人';
COMMENT ON COLUMN t_iam_user_group_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_iam_user_permission_relation;
CREATE TABLE t_iam_user_permission_relation
(
    id            VARCHAR(32) NOT NULL,
    user_id       VARCHAR(32) NOT NULL,
    permission_id VARCHAR(32) NOT NULL,
    type          VARCHAR(8) NOT NULL DEFAULT 'READ',
    sort          BIGINT NOT NULL DEFAULT 0,
    create_by     VARCHAR(32) NOT NULL,
    create_time   BIGINT NOT NULL,
    update_by     VARCHAR(32) NOT NULL,
    update_time   BIGINT NOT NULL,
    CONSTRAINT pk_t_iam_user_permission_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_iam_user_permission_relation_01 UNIQUE (user_id, permission_id)
);

COMMENT ON TABLE t_iam_user_permission_relation IS '用户权限关系表';
COMMENT ON COLUMN t_iam_user_permission_relation.id IS 'ID';
COMMENT ON COLUMN t_iam_user_permission_relation.user_id IS '用户ID';
COMMENT ON COLUMN t_iam_user_permission_relation.permission_id IS '权限id';
COMMENT ON COLUMN t_iam_user_permission_relation.type IS '权限类型,（READ:可访问，GRANT:可授权）';
COMMENT ON COLUMN t_iam_user_permission_relation.sort IS '排序';
COMMENT ON COLUMN t_iam_user_permission_relation.create_by IS '创建人';
COMMENT ON COLUMN t_iam_user_permission_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_iam_user_permission_relation.update_by IS '修改人';
COMMENT ON COLUMN t_iam_user_permission_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_iam_user_role_relation;
CREATE TABLE t_iam_user_role_relation
(
    id          VARCHAR(32) NOT NULL,
    user_id     VARCHAR(32) NOT NULL,
    role_id     VARCHAR(32) NOT NULL,
    sort        BIGINT NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_iam_user_role_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_iam_user_role_relation_01 UNIQUE (user_id, role_id)
);

CREATE INDEX idx_t_iam_user_role_relation_01 ON t_iam_user_role_relation (role_id);

COMMENT ON TABLE t_iam_user_role_relation IS '用户角色关系表';
COMMENT ON COLUMN t_iam_user_role_relation.id IS 'ID';
COMMENT ON COLUMN t_iam_user_role_relation.user_id IS '用户ID';
COMMENT ON COLUMN t_iam_user_role_relation.role_id IS '角色id';
COMMENT ON COLUMN t_iam_user_role_relation.sort IS '排序';
COMMENT ON COLUMN t_iam_user_role_relation.create_by IS '创建人';
COMMENT ON COLUMN t_iam_user_role_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_iam_user_role_relation.update_by IS '修改人';
COMMENT ON COLUMN t_iam_user_role_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_iam_user_role_business_relation;
CREATE TABLE t_iam_user_role_business_relation
(
    id                    VARCHAR(32) NOT NULL,
    user_role_relation_id VARCHAR(32) NOT NULL,
    business_id           VARCHAR(32) NOT NULL,
    business_type         VARCHAR(16) NOT NULL,
    sort                  BIGINT NOT NULL DEFAULT 0,
    create_by             VARCHAR(32) NOT NULL,
    create_time           BIGINT NOT NULL,
    update_by             VARCHAR(32) NOT NULL,
    update_time           BIGINT NOT NULL,
    CONSTRAINT pk_t_iam_user_role_business_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_iam_user_role_business_relation_01 UNIQUE (user_role_relation_id, business_id, business_type)
);

CREATE INDEX idx_t_iam_user_role_business_relation_01 ON t_iam_user_role_business_relation (business_id, business_type);

COMMENT ON TABLE t_iam_user_role_business_relation IS '用户角色业务关系表';
COMMENT ON COLUMN t_iam_user_role_business_relation.id IS 'ID';
COMMENT ON COLUMN t_iam_user_role_business_relation.user_role_relation_id IS '用户角色关系ID';
COMMENT ON COLUMN t_iam_user_role_business_relation.business_id IS '业务id';
COMMENT ON COLUMN t_iam_user_role_business_relation.business_type IS '类型（UserRoleBusinessTypeEnum）';
COMMENT ON COLUMN t_iam_user_role_business_relation.sort IS '排序';
COMMENT ON COLUMN t_iam_user_role_business_relation.create_by IS '创建人';
COMMENT ON COLUMN t_iam_user_role_business_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_iam_user_role_business_relation.update_by IS '修改人';
COMMENT ON COLUMN t_iam_user_role_business_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_iam_organization;
CREATE TABLE t_iam_organization
(
    id          VARCHAR(32) NOT NULL,
    parent_id   VARCHAR(32) NOT NULL,
    name        VARCHAR(32) NOT NULL,
    code        VARCHAR(16) NOT NULL,
    type        VARCHAR(16) NOT NULL,
    description VARCHAR(2048) NOT NULL DEFAULT '',
    sort        BIGINT NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_iam_organization PRIMARY KEY (id),
    CONSTRAINT uk_t_iam_organization_01 UNIQUE (parent_id, id),
    CONSTRAINT uk_t_iam_organization_02 UNIQUE (code)
);

COMMENT ON TABLE t_iam_organization IS '组织表';
COMMENT ON COLUMN t_iam_organization.id IS 'ID';
COMMENT ON COLUMN t_iam_organization.parent_id IS '父ID';
COMMENT ON COLUMN t_iam_organization.name IS '名称';
COMMENT ON COLUMN t_iam_organization.code IS '编码';
COMMENT ON COLUMN t_iam_organization.type IS '类型';
COMMENT ON COLUMN t_iam_organization.description IS '描述';
COMMENT ON COLUMN t_iam_organization.sort IS '排序';
COMMENT ON COLUMN t_iam_organization.create_by IS '创建人';
COMMENT ON COLUMN t_iam_organization.create_time IS '创建时间';
COMMENT ON COLUMN t_iam_organization.update_by IS '修改人';
COMMENT ON COLUMN t_iam_organization.update_time IS '更新时间';

DROP TABLE IF EXISTS t_iam_user_organization_relation;
CREATE TABLE t_iam_user_organization_relation
(
    id                VARCHAR(32) NOT NULL,
    user_id           VARCHAR(32) NOT NULL,
    organization_id   VARCHAR(32) NOT NULL,
    organization_type VARCHAR(16) NOT NULL,
    sort              BIGINT NOT NULL DEFAULT 0,
    create_by         VARCHAR(32) NOT NULL,
    create_time       BIGINT NOT NULL,
    update_by         VARCHAR(32) NOT NULL,
    update_time       BIGINT NOT NULL,
    CONSTRAINT pk_t_iam_user_organization_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_iam_user_organization_relation_01 UNIQUE (organization_id, user_id)
);

CREATE INDEX idx_t_iam_user_organization_relation_01 ON t_iam_user_organization_relation (user_id, organization_type);

COMMENT ON TABLE t_iam_user_organization_relation IS '用户组织关系表';
COMMENT ON COLUMN t_iam_user_organization_relation.id IS 'ID';
COMMENT ON COLUMN t_iam_user_organization_relation.user_id IS '用户ID';
COMMENT ON COLUMN t_iam_user_organization_relation.organization_id IS '组织id';
COMMENT ON COLUMN t_iam_user_organization_relation.organization_type IS '组织类型';
COMMENT ON COLUMN t_iam_user_organization_relation.sort IS '排序';
COMMENT ON COLUMN t_iam_user_organization_relation.create_by IS '创建人';
COMMENT ON COLUMN t_iam_user_organization_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_iam_user_organization_relation.update_by IS '修改人';
COMMENT ON COLUMN t_iam_user_organization_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_iam_third_party_user;
CREATE TABLE t_iam_third_party_user
(
    id               VARCHAR(32) NOT NULL,
    source           VARCHAR(16) NOT NULL,
    uuid             VARCHAR(32) NOT NULL,
    user_name        VARCHAR(32) NOT NULL DEFAULT '',
    display_name     VARCHAR(32) NOT NULL DEFAULT '',
    head_picture_url VARCHAR(256) NOT NULL DEFAULT '',
    content          TEXT,
    create_by        VARCHAR(32) NOT NULL,
    create_time      BIGINT NOT NULL,
    update_by        VARCHAR(32) NOT NULL,
    update_time      BIGINT NOT NULL,
    CONSTRAINT pk_t_iam_third_party_user PRIMARY KEY (id),
    CONSTRAINT uk_t_iam_third_party_user_01 UNIQUE (uuid, source)
);

CREATE INDEX idx_t_iam_third_party_user_01 ON t_iam_third_party_user (user_name, source);

COMMENT ON TABLE t_iam_third_party_user IS '第三方用户表';
COMMENT ON COLUMN t_iam_third_party_user.id IS 'ID';
COMMENT ON COLUMN t_iam_third_party_user.source IS '来源';
COMMENT ON COLUMN t_iam_third_party_user.uuid IS '第三方系统的唯一ID';
COMMENT ON COLUMN t_iam_third_party_user.user_name IS '用户名（账号）';
COMMENT ON COLUMN t_iam_third_party_user.display_name IS '昵称';
COMMENT ON COLUMN t_iam_third_party_user.head_picture_url IS '头像';
COMMENT ON COLUMN t_iam_third_party_user.content IS '内容';
COMMENT ON COLUMN t_iam_third_party_user.create_by IS '创建人';
COMMENT ON COLUMN t_iam_third_party_user.create_time IS '创建时间';
COMMENT ON COLUMN t_iam_third_party_user.update_by IS '修改人';
COMMENT ON COLUMN t_iam_third_party_user.update_time IS '更新时间';

DROP TABLE IF EXISTS t_iam_user_third_party_user_relation;
CREATE TABLE t_iam_user_third_party_user_relation
(
    id                  VARCHAR(32) NOT NULL,
    user_id             VARCHAR(32) NOT NULL,
    third_party_user_id VARCHAR(32) NOT NULL,
    sort                BIGINT NOT NULL DEFAULT 0,
    create_by           VARCHAR(32) NOT NULL,
    create_time         BIGINT NOT NULL,
    update_by           VARCHAR(32) NOT NULL,
    update_time         BIGINT NOT NULL,
    CONSTRAINT pk_t_iam_user_third_party_user_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_iam_user_third_party_user_relation_01 UNIQUE (third_party_user_id)
);

CREATE INDEX idx_t_iam_user_third_party_user_relation_01 ON t_iam_user_third_party_user_relation (third_party_user_id);

COMMENT ON TABLE t_iam_user_third_party_user_relation IS '用户&第三方用户关系表';
COMMENT ON COLUMN t_iam_user_third_party_user_relation.id IS 'ID';
COMMENT ON COLUMN t_iam_user_third_party_user_relation.user_id IS '用户ID';
COMMENT ON COLUMN t_iam_user_third_party_user_relation.third_party_user_id IS '第三方用户id';
COMMENT ON COLUMN t_iam_user_third_party_user_relation.sort IS '排序';
COMMENT ON COLUMN t_iam_user_third_party_user_relation.create_by IS '创建人';
COMMENT ON COLUMN t_iam_user_third_party_user_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_iam_user_third_party_user_relation.update_by IS '修改人';
COMMENT ON COLUMN t_iam_user_third_party_user_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_iam_user_login_log;
CREATE TABLE t_iam_user_login_log
(
    id                   VARCHAR(32) NOT NULL,
    user_id              VARCHAR(32) NOT NULL,
    username             VARCHAR(32) NOT NULL,
    auth_action          VARCHAR(32) NOT NULL,
    auth_method          VARCHAR(32) NOT NULL,
    auth_result          VARCHAR(8) NOT NULL,
    code                 VARCHAR(32) NOT NULL DEFAULT '',
    phone                VARCHAR(16) NOT NULL DEFAULT '',
    name                 VARCHAR(64) NOT NULL DEFAULT '',
    ip_address           VARCHAR(128) NOT NULL,
    platform             VARCHAR(32) NOT NULL DEFAULT '',
    access_token_id      VARCHAR(32) NOT NULL DEFAULT '',
    refresh_token_id     VARCHAR(32) NOT NULL DEFAULT '',
    access_token_expire  BIGINT NOT NULL DEFAULT 0,
    refresh_token_expire BIGINT NOT NULL DEFAULT 0,
    access_token         VARCHAR(512) NOT NULL DEFAULT '',
    refresh_token        VARCHAR(512) NOT NULL DEFAULT '',
    user_agent           VARCHAR(512) NOT NULL DEFAULT '',
    fail_reason          VARCHAR(512) NOT NULL DEFAULT '',
    description          TEXT,
    create_by            VARCHAR(32) NOT NULL,
    create_time          BIGINT NOT NULL,
    update_by            VARCHAR(32) NOT NULL,
    update_time          BIGINT NOT NULL,
    CONSTRAINT pk_t_iam_user_login_log PRIMARY KEY (id)
);

CREATE INDEX idx_t_iam_user_login_log_01 ON t_iam_user_login_log (user_id, access_token_id);
CREATE INDEX idx_t_iam_user_login_log_02 ON t_iam_user_login_log (user_id, refresh_token_id);
CREATE INDEX idx_t_iam_user_login_log_03 ON t_iam_user_login_log (username);
CREATE INDEX idx_t_iam_user_login_log_04 ON t_iam_user_login_log (phone);
CREATE INDEX idx_t_iam_user_login_log_05 ON t_iam_user_login_log (ip_address);
CREATE INDEX idx_t_iam_user_login_log_06 ON t_iam_user_login_log (access_token_id);
CREATE INDEX idx_t_iam_user_login_log_07 ON t_iam_user_login_log (refresh_token_id);
CREATE INDEX idx_t_iam_user_login_log_08 ON t_iam_user_login_log (create_time);

COMMENT ON TABLE t_iam_user_login_log IS '登录日志表';
COMMENT ON COLUMN t_iam_user_login_log.id IS 'ID';
COMMENT ON COLUMN t_iam_user_login_log.user_id IS '用户id';
COMMENT ON COLUMN t_iam_user_login_log.username IS '用户名（系统账号）';
COMMENT ON COLUMN t_iam_user_login_log.auth_action IS '操作(AuthActionEnum)';
COMMENT ON COLUMN t_iam_user_login_log.auth_method IS '登录方式(AuthMethodEnum)';
COMMENT ON COLUMN t_iam_user_login_log.auth_result IS '结果(AuthResultEnum)';
COMMENT ON COLUMN t_iam_user_login_log.code IS '编码（工号）';
COMMENT ON COLUMN t_iam_user_login_log.phone IS '手机号';
COMMENT ON COLUMN t_iam_user_login_log.name IS '姓名';
COMMENT ON COLUMN t_iam_user_login_log.ip_address IS 'IP 地址';
COMMENT ON COLUMN t_iam_user_login_log.platform IS '登录平台';
COMMENT ON COLUMN t_iam_user_login_log.access_token_id IS 'access token id';
COMMENT ON COLUMN t_iam_user_login_log.refresh_token_id IS 'refresh token id';
COMMENT ON COLUMN t_iam_user_login_log.access_token_expire IS 'auth token 过期时间';
COMMENT ON COLUMN t_iam_user_login_log.refresh_token_expire IS 'refresh token 过期时间';
COMMENT ON COLUMN t_iam_user_login_log.access_token IS 'access token';
COMMENT ON COLUMN t_iam_user_login_log.refresh_token IS 'refresh token';
COMMENT ON COLUMN t_iam_user_login_log.user_agent IS '浏览器和操作系统等信息';
COMMENT ON COLUMN t_iam_user_login_log.fail_reason IS '失败原因';
COMMENT ON COLUMN t_iam_user_login_log.description IS '描述';
COMMENT ON COLUMN t_iam_user_login_log.create_by IS '创建人';
COMMENT ON COLUMN t_iam_user_login_log.create_time IS '创建时间';
COMMENT ON COLUMN t_iam_user_login_log.update_by IS '修改人';
COMMENT ON COLUMN t_iam_user_login_log.update_time IS '更新时间';

DROP TABLE IF EXISTS t_iam_user_operate_log;
CREATE TABLE t_iam_user_operate_log
(
    id            VARCHAR(32) NOT NULL,
    user_id       VARCHAR(32) NOT NULL,
    ip_address    VARCHAR(128) NOT NULL,
    platform      VARCHAR(32) NOT NULL DEFAULT '',
    action_status VARCHAR(8) NOT NULL DEFAULT 'SUCCESS',
    action_url    VARCHAR(128) NOT NULL,
    action_param  TEXT,
    action_result TEXT,
    remark        VARCHAR(2048) NOT NULL,
    create_by     VARCHAR(32) NOT NULL,
    create_time   BIGINT NOT NULL,
    update_by     VARCHAR(32) NOT NULL,
    update_time   BIGINT NOT NULL,
    CONSTRAINT pk_t_iam_user_operate_log PRIMARY KEY (id)
);

CREATE INDEX idx_t_iam_user_operate_log_01 ON t_iam_user_operate_log (user_id);
CREATE INDEX idx_t_iam_user_operate_log_02 ON t_iam_user_operate_log (action_url);
CREATE INDEX idx_t_iam_user_operate_log_03 ON t_iam_user_operate_log (create_time);

COMMENT ON TABLE t_iam_user_operate_log IS '用户操作日志表';
COMMENT ON COLUMN t_iam_user_operate_log.id IS 'ID';
COMMENT ON COLUMN t_iam_user_operate_log.user_id IS '用户ID';
COMMENT ON COLUMN t_iam_user_operate_log.ip_address IS 'IP地址';
COMMENT ON COLUMN t_iam_user_operate_log.platform IS '登录平台';
COMMENT ON COLUMN t_iam_user_operate_log.action_status IS '操作结果，FAIL:失败 SUCCESS:成功';
COMMENT ON COLUMN t_iam_user_operate_log.action_url IS '操作地址';
COMMENT ON COLUMN t_iam_user_operate_log.action_param IS '操作参数';
COMMENT ON COLUMN t_iam_user_operate_log.action_result IS '操作结果';
COMMENT ON COLUMN t_iam_user_operate_log.remark IS '备注';
COMMENT ON COLUMN t_iam_user_operate_log.create_by IS '创建人';
COMMENT ON COLUMN t_iam_user_operate_log.create_time IS '创建时间';
COMMENT ON COLUMN t_iam_user_operate_log.update_by IS '修改人';
COMMENT ON COLUMN t_iam_user_operate_log.update_time IS '更新时间';

DROP TABLE IF EXISTS t_iam_oauth2_registered_client;
CREATE TABLE t_iam_oauth2_registered_client
(
    id                            VARCHAR(128) NOT NULL,
    status                        VARCHAR(8) NOT NULL DEFAULT 'ENABLE',
    client_id                     VARCHAR(64) NOT NULL,
    client_id_issued_at           BIGINT NOT NULL,
    client_secret                 VARCHAR(256),
    client_secret_expires_at      BIGINT,
    client_name                   VARCHAR(256) NOT NULL,
    client_authentication_methods VARCHAR(1024) NOT NULL,
    authorization_grant_types     VARCHAR(1024) NOT NULL,
    redirect_uris                 VARCHAR(1024),
    post_logout_redirect_uris     VARCHAR(1024),
    scopes                        VARCHAR(1024) NOT NULL,
    client_settings               VARCHAR(4096) NOT NULL,
    token_settings                VARCHAR(4096) NOT NULL,
    web_hook_info                 VARCHAR(2048),
    create_by                     VARCHAR(32) NOT NULL,
    create_time                   BIGINT NOT NULL,
    update_by                     VARCHAR(32) NOT NULL,
    update_time                   BIGINT NOT NULL,
    CONSTRAINT pk_t_iam_oauth2_registered_client PRIMARY KEY (id)
);

COMMENT ON TABLE t_iam_oauth2_registered_client IS 'OAuth2 客户端注册信息表';
COMMENT ON COLUMN t_iam_oauth2_registered_client.id IS 'ID';
COMMENT ON COLUMN t_iam_oauth2_registered_client.status IS '状态,（DISABLE:禁用，ENABLE:启用）';
COMMENT ON COLUMN t_iam_oauth2_registered_client.client_id IS '客户端ID';
COMMENT ON COLUMN t_iam_oauth2_registered_client.client_id_issued_at IS '客户端ID发放时间';
COMMENT ON COLUMN t_iam_oauth2_registered_client.client_secret IS '客户端密钥';
COMMENT ON COLUMN t_iam_oauth2_registered_client.client_secret_expires_at IS '客户端密钥过期时间';
COMMENT ON COLUMN t_iam_oauth2_registered_client.client_name IS '客户端名称';
COMMENT ON COLUMN t_iam_oauth2_registered_client.client_authentication_methods IS '客户端认证方法';
COMMENT ON COLUMN t_iam_oauth2_registered_client.authorization_grant_types IS '授权授予类型';
COMMENT ON COLUMN t_iam_oauth2_registered_client.redirect_uris IS '重定向URI';
COMMENT ON COLUMN t_iam_oauth2_registered_client.post_logout_redirect_uris IS '注销后重定向URI';
COMMENT ON COLUMN t_iam_oauth2_registered_client.scopes IS '作用域';
COMMENT ON COLUMN t_iam_oauth2_registered_client.client_settings IS '客户端设置';
COMMENT ON COLUMN t_iam_oauth2_registered_client.token_settings IS '令牌设置';
COMMENT ON COLUMN t_iam_oauth2_registered_client.web_hook_info IS 'WebHook信息';
COMMENT ON COLUMN t_iam_oauth2_registered_client.create_by IS '创建人';
COMMENT ON COLUMN t_iam_oauth2_registered_client.create_time IS '创建时间';
COMMENT ON COLUMN t_iam_oauth2_registered_client.update_by IS '修改人';
COMMENT ON COLUMN t_iam_oauth2_registered_client.update_time IS '更新时间';

DROP TABLE IF EXISTS t_iam_oauth2_authorization_consent;
CREATE TABLE t_iam_oauth2_authorization_consent
(
    id                   VARCHAR(128) NOT NULL,
    registered_client_id VARCHAR(128) NOT NULL,
    principal_name       VARCHAR(256) NOT NULL,
    authorities          VARCHAR(1000) NOT NULL,
    create_by            VARCHAR(32) NOT NULL,
    create_time          BIGINT NOT NULL,
    update_by            VARCHAR(32) NOT NULL,
    update_time          BIGINT NOT NULL,
    CONSTRAINT pk_t_iam_oauth2_authorization_consent PRIMARY KEY (registered_client_id, principal_name)
);

COMMENT ON TABLE t_iam_oauth2_authorization_consent IS 'OAuth2 授权同意信息表';
COMMENT ON COLUMN t_iam_oauth2_authorization_consent.id IS '授权记录ID';
COMMENT ON COLUMN t_iam_oauth2_authorization_consent.registered_client_id IS '注册客户端ID';
COMMENT ON COLUMN t_iam_oauth2_authorization_consent.principal_name IS '主体名称';
COMMENT ON COLUMN t_iam_oauth2_authorization_consent.authorities IS '授权权限';
COMMENT ON COLUMN t_iam_oauth2_authorization_consent.create_by IS '创建人';
COMMENT ON COLUMN t_iam_oauth2_authorization_consent.create_time IS '创建时间';
COMMENT ON COLUMN t_iam_oauth2_authorization_consent.update_by IS '修改人';
COMMENT ON COLUMN t_iam_oauth2_authorization_consent.update_time IS '更新时间';

DROP TABLE IF EXISTS t_iam_oauth2_authorization;
CREATE TABLE t_iam_oauth2_authorization
(
    id                            VARCHAR(128) NOT NULL,
    registered_client_id          VARCHAR(128) NOT NULL,
    principal_name                VARCHAR(256) NOT NULL,
    authorization_grant_type      VARCHAR(128) NOT NULL,
    authorized_scopes             VARCHAR(1024),
    attributes                    BYTEA,
    state                         VARCHAR(512),
    authorization_code_value      BYTEA,
    authorization_code_issued_at  BIGINT,
    authorization_code_expires_at BIGINT,
    authorization_code_metadata   BYTEA,
    access_token_value            BYTEA,
    access_token_issued_at        BIGINT,
    access_token_expires_at       BIGINT,
    access_token_metadata         BYTEA,
    access_token_type             VARCHAR(128),
    access_token_scopes           VARCHAR(1024),
    oidc_id_token_value           BYTEA,
    oidc_id_token_issued_at       BIGINT,
    oidc_id_token_expires_at      BIGINT,
    oidc_id_token_metadata        BYTEA,
    refresh_token_value           BYTEA,
    refresh_token_issued_at       BIGINT,
    refresh_token_expires_at      BIGINT,
    refresh_token_metadata        BYTEA,
    user_code_value               BYTEA,
    user_code_issued_at           BIGINT,
    user_code_expires_at          BIGINT,
    user_code_metadata            BYTEA,
    device_code_value             BYTEA,
    device_code_issued_at         BIGINT,
    device_code_expires_at        BIGINT,
    device_code_metadata          BYTEA,
    create_by                     VARCHAR(32) NOT NULL,
    create_time                   BIGINT NOT NULL,
    update_by                     VARCHAR(32) NOT NULL,
    update_time                   BIGINT NOT NULL,
    CONSTRAINT pk_t_iam_oauth2_authorization PRIMARY KEY (id)
);

COMMENT ON TABLE t_iam_oauth2_authorization IS 'OAuth2 授权信息表';
COMMENT ON COLUMN t_iam_oauth2_authorization.id IS '授权记录ID';
COMMENT ON COLUMN t_iam_oauth2_authorization.registered_client_id IS '注册客户端ID';
COMMENT ON COLUMN t_iam_oauth2_authorization.principal_name IS '主体名称';
COMMENT ON COLUMN t_iam_oauth2_authorization.authorization_grant_type IS '授权授予类型';
COMMENT ON COLUMN t_iam_oauth2_authorization.authorized_scopes IS '授权范围';
COMMENT ON COLUMN t_iam_oauth2_authorization.attributes IS '属性';
COMMENT ON COLUMN t_iam_oauth2_authorization.state IS '状态';
COMMENT ON COLUMN t_iam_oauth2_authorization.authorization_code_value IS '授权码值';
COMMENT ON COLUMN t_iam_oauth2_authorization.authorization_code_issued_at IS '授权码发放时间';
COMMENT ON COLUMN t_iam_oauth2_authorization.authorization_code_expires_at IS '授权码过期时间';
COMMENT ON COLUMN t_iam_oauth2_authorization.authorization_code_metadata IS '授权码元数据';
COMMENT ON COLUMN t_iam_oauth2_authorization.access_token_value IS '访问令牌值';
COMMENT ON COLUMN t_iam_oauth2_authorization.access_token_issued_at IS '访问令牌发放时间';
COMMENT ON COLUMN t_iam_oauth2_authorization.access_token_expires_at IS '访问令牌过期时间';
COMMENT ON COLUMN t_iam_oauth2_authorization.access_token_metadata IS '访问令牌元数据';
COMMENT ON COLUMN t_iam_oauth2_authorization.access_token_type IS '访问令牌类型';
COMMENT ON COLUMN t_iam_oauth2_authorization.access_token_scopes IS '访问令牌范围';
COMMENT ON COLUMN t_iam_oauth2_authorization.oidc_id_token_value IS 'OpenID Connect ID 令牌值';
COMMENT ON COLUMN t_iam_oauth2_authorization.oidc_id_token_issued_at IS 'OpenID Connect ID 令牌发放时间';
COMMENT ON COLUMN t_iam_oauth2_authorization.oidc_id_token_expires_at IS 'OpenID Connect ID 令牌过期时间';
COMMENT ON COLUMN t_iam_oauth2_authorization.oidc_id_token_metadata IS 'OpenID Connect ID 令牌元数据';
COMMENT ON COLUMN t_iam_oauth2_authorization.refresh_token_value IS '刷新令牌值';
COMMENT ON COLUMN t_iam_oauth2_authorization.refresh_token_issued_at IS '刷新令牌发放时间';
COMMENT ON COLUMN t_iam_oauth2_authorization.refresh_token_expires_at IS '刷新令牌过期时间';
COMMENT ON COLUMN t_iam_oauth2_authorization.refresh_token_metadata IS '刷新令牌元数据';
COMMENT ON COLUMN t_iam_oauth2_authorization.user_code_value IS '用户码值';
COMMENT ON COLUMN t_iam_oauth2_authorization.user_code_issued_at IS '用户码发放时间';
COMMENT ON COLUMN t_iam_oauth2_authorization.user_code_expires_at IS '用户码过期时间';
COMMENT ON COLUMN t_iam_oauth2_authorization.user_code_metadata IS '用户码元数据';
COMMENT ON COLUMN t_iam_oauth2_authorization.device_code_value IS '设备码值';
COMMENT ON COLUMN t_iam_oauth2_authorization.device_code_issued_at IS '设备码发放时间';
COMMENT ON COLUMN t_iam_oauth2_authorization.device_code_expires_at IS '设备码过期时间';
COMMENT ON COLUMN t_iam_oauth2_authorization.device_code_metadata IS '设备码元数据';
COMMENT ON COLUMN t_iam_oauth2_authorization.create_by IS '创建人';
COMMENT ON COLUMN t_iam_oauth2_authorization.create_time IS '创建时间';
COMMENT ON COLUMN t_iam_oauth2_authorization.update_by IS '修改人';
COMMENT ON COLUMN t_iam_oauth2_authorization.update_time IS '更新时间';