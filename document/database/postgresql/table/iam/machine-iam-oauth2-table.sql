DROP TABLE IF EXISTS t_iam_oauth2_registered_client;
CREATE TABLE t_iam_oauth2_registered_client
(
    id                            VARCHAR(32) NOT NULL,
    status                        VARCHAR(8) NOT NULL DEFAULT 'ENABLE',
    client_id                     VARCHAR(32) NOT NULL,
    client_id_issued_at           BIGINT NOT NULL,
    client_secret                 VARCHAR(512),
    client_secret_expires_at      BIGINT,
    client_name                   VARCHAR(64) NOT NULL,
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
    CONSTRAINT pk_t_iam_oauth2_registered_client PRIMARY KEY (id),
    CONSTRAINT uk_t_iam_user_01 UNIQUE (client_id),
    CONSTRAINT uk_t_iam_user_02 UNIQUE (client_name)
);

COMMENT ON TABLE t_iam_oauth2_registered_client IS 'OAuth2 客户端信息表';
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