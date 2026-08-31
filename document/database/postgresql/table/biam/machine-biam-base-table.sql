DROP TABLE IF EXISTS t_biam_user;
CREATE TABLE t_biam_user
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
    CONSTRAINT pk_t_biam_user PRIMARY KEY (id),
    CONSTRAINT uk_t_biam_user_01 UNIQUE (username),
    CONSTRAINT uk_t_biam_user_02 UNIQUE (code),
    CONSTRAINT uk_t_biam_user_03 UNIQUE (phone)
);

CREATE INDEX idx_t_biam_user_01 ON t_biam_user (phone);
CREATE INDEX idx_t_biam_user_02 ON t_biam_user (create_time);
CREATE INDEX idx_t_biam_user_03 ON t_biam_user (update_time);

COMMENT ON TABLE t_biam_user IS '用户表';
COMMENT ON COLUMN t_biam_user.id IS 'ID';
COMMENT ON COLUMN t_biam_user.username IS '用户名（系统账号）';
COMMENT ON COLUMN t_biam_user.status IS '状态';
COMMENT ON COLUMN t_biam_user.password IS '密码，加密存储';
COMMENT ON COLUMN t_biam_user.code IS '编码（工号）';
COMMENT ON COLUMN t_biam_user.phone IS '手机号';
COMMENT ON COLUMN t_biam_user.name IS '姓名';
COMMENT ON COLUMN t_biam_user.gender IS '性别';
COMMENT ON COLUMN t_biam_user.description IS '描述';
COMMENT ON COLUMN t_biam_user.create_by IS '创建人';
COMMENT ON COLUMN t_biam_user.create_time IS '创建时间';
COMMENT ON COLUMN t_biam_user.update_by IS '修改人';
COMMENT ON COLUMN t_biam_user.update_time IS '更新时间';

DROP TABLE IF EXISTS t_biam_user_type_relation;
CREATE TABLE t_biam_user_type_relation
(
    id          VARCHAR(32) NOT NULL,
    user_id     VARCHAR(32) NOT NULL,
    user_type   VARCHAR(16) NOT NULL,
    sort        BIGINT NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_biam_user_type_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_biam_user_type_relation_01 UNIQUE (user_id, user_type)
);

ALTER TABLE t_biam_user_type_relation
ADD CONSTRAINT fk_t_biam_user_type_relation_01
FOREIGN KEY (user_id) REFERENCES t_biam_user(id) ON DELETE CASCADE;

COMMENT ON TABLE t_biam_user_type_relation IS '用户类型关系表';
COMMENT ON COLUMN t_biam_user_type_relation.id IS 'ID';
COMMENT ON COLUMN t_biam_user_type_relation.user_id IS '用户id';
COMMENT ON COLUMN t_biam_user_type_relation.user_type IS '类型（UserTypeEnum）';
COMMENT ON COLUMN t_biam_user_type_relation.sort IS '排序';
COMMENT ON COLUMN t_biam_user_type_relation.create_by IS '创建人';
COMMENT ON COLUMN t_biam_user_type_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_biam_user_type_relation.update_by IS '修改人';
COMMENT ON COLUMN t_biam_user_type_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_biam_role;
CREATE TABLE t_biam_role
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
    CONSTRAINT pk_t_biam_role PRIMARY KEY (id),
    CONSTRAINT uk_t_biam_role_01 UNIQUE (code),
    CONSTRAINT uk_t_biam_role_02 UNIQUE (parent_id, id)
);

CREATE INDEX idx_t_biam_role_01 ON t_biam_role (sort);
CREATE INDEX idx_t_biam_role_02 ON t_biam_role (create_time);
CREATE INDEX idx_t_biam_role_03 ON t_biam_role (update_time);

COMMENT ON TABLE t_biam_role IS '角色表';
COMMENT ON COLUMN t_biam_role.id IS 'ID';
COMMENT ON COLUMN t_biam_role.parent_id IS '父ID';
COMMENT ON COLUMN t_biam_role.status IS '状态,（DISABLE:禁用，ENABLE:启用）';
COMMENT ON COLUMN t_biam_role.code IS '编码';
COMMENT ON COLUMN t_biam_role.type IS '类型';
COMMENT ON COLUMN t_biam_role.name IS '名称';
COMMENT ON COLUMN t_biam_role.description IS '描述';
COMMENT ON COLUMN t_biam_role.data_permission_rule IS '数据权限规则';
COMMENT ON COLUMN t_biam_role.sort IS '排序';
COMMENT ON COLUMN t_biam_role.create_by IS '创建人';
COMMENT ON COLUMN t_biam_role.create_time IS '创建时间';
COMMENT ON COLUMN t_biam_role.update_by IS '修改人';
COMMENT ON COLUMN t_biam_role.update_time IS '更新时间';

DROP TABLE IF EXISTS t_biam_permission;
CREATE TABLE t_biam_permission
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
    CONSTRAINT pk_t_biam_permission PRIMARY KEY (id),
    CONSTRAINT uk_t_biam_permission_01 UNIQUE (code),
    CONSTRAINT uk_t_biam_permission_02 UNIQUE (parent_id, id)
);

CREATE INDEX idx_t_biam_permission_01 ON t_biam_permission (create_time);
CREATE INDEX idx_t_biam_permission_02 ON t_biam_permission (update_time);

COMMENT ON TABLE t_biam_permission IS '权限表';
COMMENT ON COLUMN t_biam_permission.id IS 'ID';
COMMENT ON COLUMN t_biam_permission.parent_id IS '父ID';
COMMENT ON COLUMN t_biam_permission.resource_type IS '资源类型';
COMMENT ON COLUMN t_biam_permission.code IS '编码';
COMMENT ON COLUMN t_biam_permission.name IS '名称';
COMMENT ON COLUMN t_biam_permission.icon IS '图标';
COMMENT ON COLUMN t_biam_permission.data_meta_into IS '数据权限元数据';
COMMENT ON COLUMN t_biam_permission.description IS '描述';
COMMENT ON COLUMN t_biam_permission.sort IS '排序';
COMMENT ON COLUMN t_biam_permission.create_by IS '创建人';
COMMENT ON COLUMN t_biam_permission.create_time IS '创建时间';
COMMENT ON COLUMN t_biam_permission.update_by IS '修改人';
COMMENT ON COLUMN t_biam_permission.update_time IS '更新时间';

DROP TABLE IF EXISTS t_biam_group;
CREATE TABLE t_biam_group
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
    CONSTRAINT pk_t_biam_group PRIMARY KEY (id)
);

CREATE INDEX idx_t_biam_group_01 ON t_biam_group (parent_id);

COMMENT ON TABLE t_biam_group IS '分组表';
COMMENT ON COLUMN t_biam_group.id IS 'ID';
COMMENT ON COLUMN t_biam_group.parent_id IS '父ID';
COMMENT ON COLUMN t_biam_group.name IS '名称';
COMMENT ON COLUMN t_biam_group.description IS '描述';
COMMENT ON COLUMN t_biam_group.sort IS '排序';
COMMENT ON COLUMN t_biam_group.create_by IS '创建人';
COMMENT ON COLUMN t_biam_group.create_time IS '创建时间';
COMMENT ON COLUMN t_biam_group.update_by IS '修改人';
COMMENT ON COLUMN t_biam_group.update_time IS '更新时间';

DROP TABLE IF EXISTS t_biam_role_permission_relation;
CREATE TABLE t_biam_role_permission_relation
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
    CONSTRAINT pk_t_biam_role_permission_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_biam_role_permission_relation_01 UNIQUE (role_id, permission_id)
);

ALTER TABLE t_biam_role_permission_relation
ADD CONSTRAINT fk_t_biam_role_permission_relation_01
FOREIGN KEY (role_id) REFERENCES t_biam_role(id) ON DELETE CASCADE;

ALTER TABLE t_biam_role_permission_relation
ADD CONSTRAINT fk_t_biam_role_permission_relation_02
FOREIGN KEY (permission_id) REFERENCES t_biam_permission(id) ON DELETE CASCADE;

COMMENT ON TABLE t_biam_role_permission_relation IS '角色权限关系表';
COMMENT ON COLUMN t_biam_role_permission_relation.id IS 'ID';
COMMENT ON COLUMN t_biam_role_permission_relation.role_id IS '角色ID';
COMMENT ON COLUMN t_biam_role_permission_relation.permission_id IS '权限id';
COMMENT ON COLUMN t_biam_role_permission_relation.type IS '权限类型,（READ:可访问，GRANT:可授权）';
COMMENT ON COLUMN t_biam_role_permission_relation.data_permission_rules IS '数据权限规则';
COMMENT ON COLUMN t_biam_role_permission_relation.sort IS '排序';
COMMENT ON COLUMN t_biam_role_permission_relation.create_by IS '创建人';
COMMENT ON COLUMN t_biam_role_permission_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_biam_role_permission_relation.update_by IS '修改人';
COMMENT ON COLUMN t_biam_role_permission_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_biam_group_permission_relation;
CREATE TABLE t_biam_group_permission_relation
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
    CONSTRAINT pk_t_biam_group_permission_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_biam_group_permission_relation_01 UNIQUE (group_id, permission_id)
);

ALTER TABLE t_biam_group_permission_relation
ADD CONSTRAINT fk_t_biam_group_permission_relation_01
FOREIGN KEY (group_id) REFERENCES t_biam_group(id) ON DELETE CASCADE;

ALTER TABLE t_biam_group_permission_relation
ADD CONSTRAINT fk_t_biam_group_permission_relation_02
FOREIGN KEY (permission_id) REFERENCES t_biam_permission(id) ON DELETE CASCADE;

COMMENT ON TABLE t_biam_group_permission_relation IS '分组权限关系表';
COMMENT ON COLUMN t_biam_group_permission_relation.id IS 'ID';
COMMENT ON COLUMN t_biam_group_permission_relation.group_id IS '分组ID';
COMMENT ON COLUMN t_biam_group_permission_relation.permission_id IS '权限id';
COMMENT ON COLUMN t_biam_group_permission_relation.type IS '权限类型,（READ:可访问，GRANT:可授权）';
COMMENT ON COLUMN t_biam_group_permission_relation.sort IS '排序';
COMMENT ON COLUMN t_biam_group_permission_relation.create_by IS '创建人';
COMMENT ON COLUMN t_biam_group_permission_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_biam_group_permission_relation.update_by IS '修改人';
COMMENT ON COLUMN t_biam_group_permission_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_biam_group_role_relation;
CREATE TABLE t_biam_group_role_relation
(
    id          VARCHAR(32) NOT NULL,
    group_id    VARCHAR(32) NOT NULL,
    role_id     VARCHAR(32) NOT NULL,
    sort        BIGINT NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_biam_group_role_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_biam_group_role_relation_01 UNIQUE (group_id, role_id)
);

ALTER TABLE t_biam_group_role_relation
ADD CONSTRAINT fk_t_biam_group_role_relation_01
FOREIGN KEY (group_id) REFERENCES t_biam_group(id) ON DELETE CASCADE;

ALTER TABLE t_biam_group_role_relation
ADD CONSTRAINT fk_t_biam_group_role_relation_02
FOREIGN KEY (role_id) REFERENCES t_biam_role(id) ON DELETE CASCADE;

COMMENT ON TABLE t_biam_group_role_relation IS '分组角色关系表';
COMMENT ON COLUMN t_biam_group_role_relation.id IS 'ID';
COMMENT ON COLUMN t_biam_group_role_relation.group_id IS '分组ID';
COMMENT ON COLUMN t_biam_group_role_relation.role_id IS '角色id';
COMMENT ON COLUMN t_biam_group_role_relation.sort IS '排序';
COMMENT ON COLUMN t_biam_group_role_relation.create_by IS '创建人';
COMMENT ON COLUMN t_biam_group_role_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_biam_group_role_relation.update_by IS '修改人';
COMMENT ON COLUMN t_biam_group_role_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_biam_user_group_relation;
CREATE TABLE t_biam_user_group_relation
(
    id          VARCHAR(32) NOT NULL,
    user_id     VARCHAR(32) NOT NULL,
    group_id    VARCHAR(32) NOT NULL,
    sort        BIGINT NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_biam_user_group_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_biam_user_group_relation_01 UNIQUE (user_id, group_id)
);

ALTER TABLE t_biam_user_group_relation
ADD CONSTRAINT fk_t_biam_user_group_relation_01
FOREIGN KEY (user_id) REFERENCES t_biam_user(id) ON DELETE CASCADE;

ALTER TABLE t_biam_user_group_relation
ADD CONSTRAINT fk_t_biam_user_group_relation_02
FOREIGN KEY (group_id) REFERENCES t_biam_group(id) ON DELETE CASCADE;

COMMENT ON TABLE t_biam_user_group_relation IS '用户分组关系表';
COMMENT ON COLUMN t_biam_user_group_relation.id IS 'ID';
COMMENT ON COLUMN t_biam_user_group_relation.user_id IS '用户id';
COMMENT ON COLUMN t_biam_user_group_relation.group_id IS '组ID';
COMMENT ON COLUMN t_biam_user_group_relation.sort IS '排序';
COMMENT ON COLUMN t_biam_user_group_relation.create_by IS '创建人';
COMMENT ON COLUMN t_biam_user_group_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_biam_user_group_relation.update_by IS '修改人';
COMMENT ON COLUMN t_biam_user_group_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_biam_user_permission_relation;
CREATE TABLE t_biam_user_permission_relation
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
    CONSTRAINT pk_t_biam_user_permission_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_biam_user_permission_relation_01 UNIQUE (user_id, permission_id)
);

ALTER TABLE t_biam_user_permission_relation
ADD CONSTRAINT fk_t_biam_user_permission_relation_01
FOREIGN KEY (user_id) REFERENCES t_biam_user(id) ON DELETE CASCADE;

ALTER TABLE t_biam_user_permission_relation
ADD CONSTRAINT fk_t_biam_user_permission_relation_02
FOREIGN KEY (permission_id) REFERENCES t_biam_permission(id) ON DELETE CASCADE;

COMMENT ON TABLE t_biam_user_permission_relation IS '用户权限关系表';
COMMENT ON COLUMN t_biam_user_permission_relation.id IS 'ID';
COMMENT ON COLUMN t_biam_user_permission_relation.user_id IS '用户ID';
COMMENT ON COLUMN t_biam_user_permission_relation.permission_id IS '权限id';
COMMENT ON COLUMN t_biam_user_permission_relation.type IS '权限类型,（READ:可访问，GRANT:可授权）';
COMMENT ON COLUMN t_biam_user_permission_relation.sort IS '排序';
COMMENT ON COLUMN t_biam_user_permission_relation.create_by IS '创建人';
COMMENT ON COLUMN t_biam_user_permission_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_biam_user_permission_relation.update_by IS '修改人';
COMMENT ON COLUMN t_biam_user_permission_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_biam_user_role_relation;
CREATE TABLE t_biam_user_role_relation
(
    id          VARCHAR(32) NOT NULL,
    user_id     VARCHAR(32) NOT NULL,
    role_id     VARCHAR(32) NOT NULL,
    sort        BIGINT NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_biam_user_role_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_biam_user_role_relation_01 UNIQUE (user_id, role_id)
);

ALTER TABLE t_biam_user_role_relation
ADD CONSTRAINT fk_t_biam_user_role_relation_01
FOREIGN KEY (user_id) REFERENCES t_biam_user(id) ON DELETE CASCADE;

ALTER TABLE t_biam_user_role_relation
ADD CONSTRAINT fk_t_biam_user_role_relation_02
FOREIGN KEY (role_id) REFERENCES t_biam_role(id) ON DELETE CASCADE;

CREATE INDEX idx_t_biam_user_role_relation_01 ON t_biam_user_role_relation (role_id);

COMMENT ON TABLE t_biam_user_role_relation IS '用户角色关系表';
COMMENT ON COLUMN t_biam_user_role_relation.id IS 'ID';
COMMENT ON COLUMN t_biam_user_role_relation.user_id IS '用户ID';
COMMENT ON COLUMN t_biam_user_role_relation.role_id IS '角色id';
COMMENT ON COLUMN t_biam_user_role_relation.sort IS '排序';
COMMENT ON COLUMN t_biam_user_role_relation.create_by IS '创建人';
COMMENT ON COLUMN t_biam_user_role_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_biam_user_role_relation.update_by IS '修改人';
COMMENT ON COLUMN t_biam_user_role_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_biam_user_role_business_relation;
CREATE TABLE t_biam_user_role_business_relation
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
    CONSTRAINT pk_t_biam_user_role_business_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_biam_user_role_business_relation_01 UNIQUE (user_role_relation_id, business_id, business_type)
);

ALTER TABLE t_biam_user_role_business_relation
ADD CONSTRAINT fk_t_biam_user_role_business_relation_01
FOREIGN KEY (user_role_relation_id) REFERENCES t_biam_user_role_relation(id) ON DELETE CASCADE;

CREATE INDEX idx_t_biam_user_role_business_relation_01 ON t_biam_user_role_business_relation (business_id, business_type);

COMMENT ON TABLE t_biam_user_role_business_relation IS '用户角色业务关系表';
COMMENT ON COLUMN t_biam_user_role_business_relation.id IS 'ID';
COMMENT ON COLUMN t_biam_user_role_business_relation.user_role_relation_id IS '用户角色关系ID';
COMMENT ON COLUMN t_biam_user_role_business_relation.business_id IS '业务id';
COMMENT ON COLUMN t_biam_user_role_business_relation.business_type IS '类型（UserRoleBusinessTypeEnum）';
COMMENT ON COLUMN t_biam_user_role_business_relation.sort IS '排序';
COMMENT ON COLUMN t_biam_user_role_business_relation.create_by IS '创建人';
COMMENT ON COLUMN t_biam_user_role_business_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_biam_user_role_business_relation.update_by IS '修改人';
COMMENT ON COLUMN t_biam_user_role_business_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_biam_organization;
CREATE TABLE t_biam_organization
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
    CONSTRAINT pk_t_biam_organization PRIMARY KEY (id),
    CONSTRAINT uk_t_biam_organization_01 UNIQUE (parent_id, id),
    CONSTRAINT uk_t_biam_organization_02 UNIQUE (code)
);

COMMENT ON TABLE t_biam_organization IS '组织表';
COMMENT ON COLUMN t_biam_organization.id IS 'ID';
COMMENT ON COLUMN t_biam_organization.parent_id IS '父ID';
COMMENT ON COLUMN t_biam_organization.name IS '名称';
COMMENT ON COLUMN t_biam_organization.code IS '编码';
COMMENT ON COLUMN t_biam_organization.type IS '类型';
COMMENT ON COLUMN t_biam_organization.description IS '描述';
COMMENT ON COLUMN t_biam_organization.sort IS '排序';
COMMENT ON COLUMN t_biam_organization.create_by IS '创建人';
COMMENT ON COLUMN t_biam_organization.create_time IS '创建时间';
COMMENT ON COLUMN t_biam_organization.update_by IS '修改人';
COMMENT ON COLUMN t_biam_organization.update_time IS '更新时间';

DROP TABLE IF EXISTS t_biam_user_organization_relation;
CREATE TABLE t_biam_user_organization_relation
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
    CONSTRAINT pk_t_biam_user_organization_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_biam_user_organization_relation_01 UNIQUE (organization_id, user_id)
);

ALTER TABLE t_biam_user_organization_relation
ADD CONSTRAINT fk_t_biam_user_organization_relation_01
FOREIGN KEY (user_id) REFERENCES t_biam_user(id) ON DELETE CASCADE;

CREATE INDEX idx_t_biam_user_organization_relation_01 ON t_biam_user_organization_relation (user_id, organization_type);

COMMENT ON TABLE t_biam_user_organization_relation IS '用户组织关系表';
COMMENT ON COLUMN t_biam_user_organization_relation.id IS 'ID';
COMMENT ON COLUMN t_biam_user_organization_relation.user_id IS '用户ID';
COMMENT ON COLUMN t_biam_user_organization_relation.organization_id IS '组织id';
COMMENT ON COLUMN t_biam_user_organization_relation.organization_type IS '组织类型';
COMMENT ON COLUMN t_biam_user_organization_relation.sort IS '排序';
COMMENT ON COLUMN t_biam_user_organization_relation.create_by IS '创建人';
COMMENT ON COLUMN t_biam_user_organization_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_biam_user_organization_relation.update_by IS '修改人';
COMMENT ON COLUMN t_biam_user_organization_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_biam_third_party_user;
CREATE TABLE t_biam_third_party_user
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
    CONSTRAINT pk_t_biam_third_party_user PRIMARY KEY (id),
    CONSTRAINT uk_t_biam_third_party_user_01 UNIQUE (uuid, source)
);

CREATE INDEX idx_t_biam_third_party_user_01 ON t_biam_third_party_user (user_name, source);

COMMENT ON TABLE t_biam_third_party_user IS '第三方用户表';
COMMENT ON COLUMN t_biam_third_party_user.id IS 'ID';
COMMENT ON COLUMN t_biam_third_party_user.source IS '来源';
COMMENT ON COLUMN t_biam_third_party_user.uuid IS '第三方系统的唯一ID';
COMMENT ON COLUMN t_biam_third_party_user.user_name IS '用户名（账号）';
COMMENT ON COLUMN t_biam_third_party_user.display_name IS '昵称';
COMMENT ON COLUMN t_biam_third_party_user.head_picture_url IS '头像';
COMMENT ON COLUMN t_biam_third_party_user.content IS '内容';
COMMENT ON COLUMN t_biam_third_party_user.create_by IS '创建人';
COMMENT ON COLUMN t_biam_third_party_user.create_time IS '创建时间';
COMMENT ON COLUMN t_biam_third_party_user.update_by IS '修改人';
COMMENT ON COLUMN t_biam_third_party_user.update_time IS '更新时间';

DROP TABLE IF EXISTS t_biam_user_third_party_user_relation;
CREATE TABLE t_biam_user_third_party_user_relation
(
    id                  VARCHAR(32) NOT NULL,
    user_id             VARCHAR(32) NOT NULL,
    third_party_user_id VARCHAR(32) NOT NULL,
    sort                BIGINT NOT NULL DEFAULT 0,
    create_by           VARCHAR(32) NOT NULL,
    create_time         BIGINT NOT NULL,
    update_by           VARCHAR(32) NOT NULL,
    update_time         BIGINT NOT NULL,
    CONSTRAINT pk_t_biam_user_third_party_user_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_biam_user_third_party_user_relation_01 UNIQUE (third_party_user_id)
);

ALTER TABLE t_biam_user_third_party_user_relation
ADD CONSTRAINT fk_t_biam_user_third_party_user_relation_01
FOREIGN KEY (user_id) REFERENCES t_biam_user(id) ON DELETE CASCADE;

ALTER TABLE t_biam_user_third_party_user_relation
ADD CONSTRAINT fk_t_biam_user_third_party_user_relation_02
FOREIGN KEY (third_party_user_id) REFERENCES t_biam_third_party_user(id) ON DELETE CASCADE;

CREATE INDEX idx_t_biam_user_third_party_user_relation_01 ON t_biam_user_third_party_user_relation (third_party_user_id);

COMMENT ON TABLE t_biam_user_third_party_user_relation IS '用户&第三方用户关系表';
COMMENT ON COLUMN t_biam_user_third_party_user_relation.id IS 'ID';
COMMENT ON COLUMN t_biam_user_third_party_user_relation.user_id IS '用户ID';
COMMENT ON COLUMN t_biam_user_third_party_user_relation.third_party_user_id IS '第三方用户id';
COMMENT ON COLUMN t_biam_user_third_party_user_relation.sort IS '排序';
COMMENT ON COLUMN t_biam_user_third_party_user_relation.create_by IS '创建人';
COMMENT ON COLUMN t_biam_user_third_party_user_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_biam_user_third_party_user_relation.update_by IS '修改人';
COMMENT ON COLUMN t_biam_user_third_party_user_relation.update_time IS '更新时间';



DROP TABLE IF EXISTS t_biam_user_config;
CREATE TABLE t_biam_user_config
(
    id           VARCHAR(32) NOT NULL,
    user_id      VARCHAR(32) NOT NULL,
    config_key   VARCHAR(64) NOT NULL,
    config_value TEXT,
    create_by    VARCHAR(32) NOT NULL,
    create_time  BIGINT NOT NULL,
    update_by    VARCHAR(32) NOT NULL,
    update_time  BIGINT NOT NULL,
    CONSTRAINT pk_t_biam_user_config PRIMARY KEY (id),
    CONSTRAINT uk_t_biam_user_config_01 UNIQUE (user_id, config_key)
);

ALTER TABLE t_biam_user_config
ADD CONSTRAINT fk_t_biam_user_config_01
FOREIGN KEY (user_id) REFERENCES t_biam_user(id) ON DELETE CASCADE;

CREATE INDEX idx_t_biam_user_config_01 ON t_biam_user_config (user_id);
CREATE INDEX idx_t_biam_user_config_02 ON t_biam_user_config (config_key);
CREATE INDEX idx_t_biam_user_config_03 ON t_biam_user_config (create_time);

COMMENT ON TABLE t_biam_user_config IS '用户偏好配置表';
COMMENT ON COLUMN t_biam_user_config.id IS 'ID';
COMMENT ON COLUMN t_biam_user_config.user_id IS '用户ID';
COMMENT ON COLUMN t_biam_user_config.config_key IS '配置键';
COMMENT ON COLUMN t_biam_user_config.config_value IS '配置值（JSON字符串）';
COMMENT ON COLUMN t_biam_user_config.create_by IS '创建人';
COMMENT ON COLUMN t_biam_user_config.create_time IS '创建时间';
COMMENT ON COLUMN t_biam_user_config.update_by IS '修改人';
COMMENT ON COLUMN t_biam_user_config.update_time IS '更新时间';

