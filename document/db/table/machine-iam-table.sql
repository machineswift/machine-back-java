DROP TABLE IF EXISTS `t_iam_user`;
CREATE TABLE `t_iam_user`
(
    `id`          VARCHAR(32)   NOT NULL COMMENT 'ID',
    `username`    VARCHAR(32)   NOT NULL COMMENT '用户名（系统账号）',
    `status`      VARCHAR(8)    NOT NULL default 'DISABLE' COMMENT '状态',
    `password`    VARCHAR(256)  NOT NULL COMMENT '密码，加密存储',
    `code`        VARCHAR(32)   NOT NULL default '' COMMENT '编码（工号）',
    `phone`       VARCHAR(16)   NOT NULL default '' COMMENT '手机号',
    `name`        VARCHAR(64)   NOT NULL default '' COMMENT '姓名',
    `gender`      VARCHAR(16)   NOT NULL default 'UNDEFINED' COMMENT '性别',
    `description` VARCHAR(2048) NOT NULL default '' COMMENT '描述',
    `create_by`   VARCHAR(32)   NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32)   NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `user_name` ) USING BTREE,
    UNIQUE KEY `uk_02` ( `code` ) USING BTREE,
    UNIQUE KEY `uk_03` ( `phone` ) USING BTREE,
    KEY           `idx_01` ( `phone` ) USING BTREE,
    KEY           `idx_02` ( `create_time` ) USING BTREE,
    KEY           `idx_03` ( `update_time` ) USING BTREE
) COMMENT = '用户表';

DROP TABLE IF EXISTS `t_iam_user_type_relation`;
CREATE TABLE `t_iam_user_type_relation`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `user_id`     VARCHAR(32) NOT NULL COMMENT '用户id',
    `user_type`   VARCHAR(16) NOT NULL COMMENT '类型（UserTypeEnum）',
    `sort`        BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `user_id`,`user_type` ) USING BTREE
) COMMENT = '用户类型关系表';

DROP TABLE IF EXISTS `t_iam_role`;
CREATE TABLE `t_iam_role`
(
    `id`                   VARCHAR(32)   NOT NULL COMMENT 'ID',
    `parent_id`            VARCHAR(32)   NOT NULL COMMENT '父ID',
    `status`               VARCHAR(8)    NOT NULL default 'DISABLE' COMMENT '状态,（DISABLE:禁用，ENABLE:启用）',
    `code`                 VARCHAR(32)   NOT NULL COMMENT '编码',
    `type`                 VARCHAR(8)    NOT NULL COMMENT '类型',
    `name`                 VARCHAR(32)   NOT NULL COMMENT '名称',
    `description`          VARCHAR(2048) NOT NULL default '' COMMENT '描述',
    `data_permission_rule` text COMMENT '数据权限规则',
    `sort`                 BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
    `create_by`            VARCHAR(32)   NOT NULL COMMENT '创建人',
    `create_time`          BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`            VARCHAR(32)   NOT NULL COMMENT '修改人',
    `update_time`          BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `code` ) USING BTREE,
    UNIQUE KEY `uk_02` ( `parent_id`,`id` ) USING BTREE,
    KEY                    `idx_01` ( `sort` ) USING BTREE,
    KEY                    `idx_02` ( `create_time` ) USING BTREE,
    KEY                    `idx_03` ( `update_time` ) USING BTREE
) COMMENT = '角色表';

DROP TABLE IF EXISTS `t_iam_permission`;
CREATE TABLE `t_iam_permission`
(
    `id`             VARCHAR(32)   NOT NULL COMMENT 'ID',
    `parent_id`      VARCHAR(32)   NOT NULL COMMENT '父ID',
    `resource_type`  VARCHAR(16)   NOT NULL COMMENT '资源类型',
    `code`           VARCHAR(128)  NOT NULL COMMENT '编码',
    `name`           VARCHAR(32)   NOT NULL COMMENT '名称',
    `icon`           VARCHAR(256)  NOT NULL DEFAULT '' COMMENT '图标',
    `data_meta_into` VARCHAR(2048) NOT NULL DEFAULT '' COMMENT '数据权限元数据',
    `description`    VARCHAR(2048) NOT NULL DEFAULT '' COMMENT '描述',
    `sort`           BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '排序',
    `create_by`      VARCHAR(32)   NOT NULL COMMENT '创建人',
    `create_time`    BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`      VARCHAR(32)   NOT NULL COMMENT '修改人',
    `update_time`    BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `code` ) USING BTREE,
    UNIQUE KEY `uk_02` ( `parent_id`, `id` ) USING BTREE,
    KEY              `idx_01` ( `create_time` ) USING BTREE,
    KEY              `idx_02` ( `update_time` ) USING BTREE
) COMMENT = '权限表';

DROP TABLE IF EXISTS `t_iam_group`;
CREATE TABLE `t_iam_group`
(
    `id`          VARCHAR(32)   NOT NULL COMMENT 'ID',
    `parent_id`   VARCHAR(32)   NOT NULL COMMENT '父ID',
    `name`        VARCHAR(32)   NOT NULL COMMENT '名称',
    `description` VARCHAR(2048) NOT NULL default '' COMMENT '描述',
    `sort`        BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
    `create_by`   VARCHAR(32)   NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32)   NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY           `idx_01` ( `parent_id` ) USING BTREE,
    KEY           `idx_02` ( `create_time` ) USING BTREE,
    KEY           `idx_03` ( `update_time` ) USING BTREE
) COMMENT = '分组表';

DROP TABLE IF EXISTS `t_iam_role_permission_relation`;
CREATE TABLE `t_iam_role_permission_relation`
(
    `id`                    VARCHAR(32) NOT NULL COMMENT 'ID',
    `role_id`               VARCHAR(32) NOT NULL COMMENT '角色ID',
    `permission_id`         VARCHAR(32) NOT NULL COMMENT '权限id',
    `type`                  VARCHAR(8)  NOT NULL DEFAULT 'READ' COMMENT '权限类型,（READ:可访问，GRANT:可授权）',
    `data_permission_rules` text COMMENT '数据权限规则',
    `sort`                  BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
    `create_by`             VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`           BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`             VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`           BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `role_id`,`permission_id` ) USING BTREE
) COMMENT = '角色权限关系表';

DROP TABLE IF EXISTS `t_iam_group_permission_relation`;
CREATE TABLE `t_iam_group_permission_relation`
(
    `id`            VARCHAR(32) NOT NULL COMMENT 'ID',
    `group_id`      VARCHAR(32) NOT NULL COMMENT '分组ID',
    `permission_id` VARCHAR(32) NOT NULL COMMENT '权限id',
    `type`          VARCHAR(8)  NOT NULL DEFAULT 'READ' COMMENT '权限类型,（READ:可访问，GRANT:可授权）',
    `sort`          BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
    `create_by`     VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`   BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`     VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`   BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `group_id`,`permission_id` ) USING BTREE
) COMMENT = '分组权限关系表';

DROP TABLE IF EXISTS `t_iam_group_role_relation`;
CREATE TABLE `t_iam_group_role_relation`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `group_id`    VARCHAR(32) NOT NULL COMMENT '分组ID',
    `role_id`     VARCHAR(32) NOT NULL COMMENT '角色id',
    `sort`        BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `group_id`,`role_id` ) USING BTREE
) COMMENT = '分组角色关系表';

DROP TABLE IF EXISTS `t_iam_user_group_relation`;
CREATE TABLE `t_iam_user_group_relation`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `user_id`     VARCHAR(32) NOT NULL COMMENT '用户id',
    `group_id`    VARCHAR(32) NOT NULL COMMENT '组ID',
    `sort`        BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `user_id`,`group_id` ) USING BTREE
) COMMENT = '用户分组关系表';

DROP TABLE IF EXISTS `t_iam_user_permission_relation`;
CREATE TABLE `t_iam_user_permission_relation`
(
    `id`            VARCHAR(32) NOT NULL COMMENT 'ID',
    `user_id`       VARCHAR(32) NOT NULL COMMENT '用户ID',
    `permission_id` VARCHAR(32) NOT NULL COMMENT '权限id',
    `type`          VARCHAR(8)  NOT NULL DEFAULT 'READ' COMMENT '权限类型,（READ:可访问，GRANT:可授权）',
    `sort`          BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
    `create_by`     VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`   BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`     VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`   BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `user_id`,`permission_id` ) USING BTREE
) COMMENT = '用户权限关系表';

DROP TABLE IF EXISTS `t_iam_user_role_relation`;
CREATE TABLE `t_iam_user_role_relation`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `user_id`     VARCHAR(32) NOT NULL COMMENT '用户ID',
    `role_id`     VARCHAR(32) NOT NULL COMMENT '角色id',
    `sort`        BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `user_id`,`role_id` ) USING BTREE,
    KEY           `idx_01` ( `role_id` ) USING BTREE
) COMMENT = '用户角色关系表';

DROP TABLE IF EXISTS `t_iam_user_role_business_relation`;
CREATE TABLE `t_iam_user_role_business_relation`
(
    `id`                    VARCHAR(32) NOT NULL COMMENT 'ID',
    `user_role_relation_id` VARCHAR(32) NOT NULL COMMENT '用户角色关系ID',
    `business_id`           VARCHAR(32) NOT NULL COMMENT '业务id',
    `business_type`         VARCHAR(16) NOT NULL COMMENT '类型（UserRoleBusinessTypeEnum）',
    `sort`                  BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
    `create_by`             VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`           BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`             VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`           BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` (`user_role_relation_id`,`business_id`,`business_type`) USING BTREE,
    KEY                     `idx_01` ( `business_id`,`business_type` ) USING BTREE
) COMMENT = '用户角色业务关系表';

DROP TABLE IF EXISTS `t_iam_organization`;
CREATE TABLE `t_iam_organization`
(
    `id`          VARCHAR(32)   NOT NULL COMMENT 'ID',
    `parent_id`   VARCHAR(32)   NOT NULL COMMENT '父ID',
    `name`        VARCHAR(32)   NOT NULL COMMENT '名称',
    `code`        VARCHAR(16)   NOT NULL COMMENT '编码',
    `type`        VARCHAR(16)   NOT NULL COMMENT '类型',
    `description` VARCHAR(2048) NOT NULL default '' COMMENT '描述',
    `sort`        BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
    `create_by`   VARCHAR(32)   NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32)   NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `parent_id`,`id` ) USING BTREE,
    UNIQUE KEY `uk_02` ( `code` ) USING BTREE,
    KEY           `idx_01` ( `create_time` ) USING BTREE,
    KEY           `idx_02` ( `update_time` ) USING BTREE
) COMMENT = '组织表';

DROP TABLE IF EXISTS `t_iam_user_organization_relation`;
CREATE TABLE `t_iam_user_organization_relation`
(
    `id`                VARCHAR(32) NOT NULL COMMENT 'ID',
    `user_id`           VARCHAR(32) NOT NULL COMMENT '用户ID',
    `organization_id`   VARCHAR(32) NOT NULL COMMENT '组织id',
    `organization_type` VARCHAR(16) NOT NULL COMMENT '组织类型',
    `sort`              BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
    `create_by`         VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`       BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`         VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`       BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `organization_id`,`user_id` ) USING BTREE,
    KEY                 `idx_01` ( `user_id`,`organization_type` ) USING BTREE
) COMMENT = '用户组织关系表';

DROP TABLE IF EXISTS `t_iam_third_party_user`;
CREATE TABLE `t_iam_third_party_user`
(
    `id`               VARCHAR(32)  NOT NULL COMMENT 'ID',
    `source`           VARCHAR(16)  NOT NULL COMMENT '来源',
    `uuid`             VARCHAR(32)  NOT NULL COMMENT '第三方系统的唯一ID	',
    `user_name`        VARCHAR(32)  NOT NULL default '' COMMENT '用户名（账号）',
    `display_name`     VARCHAR(32)  NOT NULL default '' COMMENT '昵称',
    `head_picture_url` VARCHAR(256) NOT NULL default '' COMMENT '头像',
    `content`          text COMMENT '内容',
    `create_by`        VARCHAR(32)  NOT NULL COMMENT '创建人',
    `create_time`      BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`        VARCHAR(32)  NOT NULL COMMENT '修改人',
    `update_time`      BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `uuid`,`source` ) USING BTREE,
    KEY                `idx_02` ( `user_name`,`source` ) USING BTREE
) COMMENT = '第三方用户表';

DROP TABLE IF EXISTS `t_iam_user_third_party_user_relation`;
CREATE TABLE `t_iam_user_third_party_user_relation`
(
    `id`                  VARCHAR(32) NOT NULL COMMENT 'ID',
    `user_id`             VARCHAR(32) NOT NULL COMMENT '用户ID',
    `third_party_user_id` VARCHAR(32) NOT NULL COMMENT '第三方用户id',
    `sort`                BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
    `create_by`           VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`         BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`           VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`         BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` (`third_party_user_id` ) USING BTREE,
    KEY                   `idx_01` ( `third_party_user_id` ) USING BTREE
) COMMENT = '用户&第三方用户关系表';


DROP TABLE IF EXISTS `t_iam_user_login_log`;
CREATE TABLE `t_iam_user_login_log`
(
    `id`                   VARCHAR(32)  NOT NULL COMMENT 'ID',
    `user_id`              VARCHAR(32)  NOT NULL COMMENT '用户id',
    `username`             VARCHAR(32)  NOT NULL COMMENT '用户名（系统账号）',
    `auth_action`          VARCHAR(32)  NOT NULL COMMENT '操作(AuthActionEnum)',
    `auth_method`          VARCHAR(32)  NOT NULL COMMENT '登录方式(AuthMethodEnum)',
    `auth_result`          VARCHAR(8)   NOT NULL COMMENT '结果(AuthResultEnum)',
    `code`                 VARCHAR(32)  NOT NULL DEFAULT '' COMMENT '编码（工号）',
    `phone`                VARCHAR(16)  NOT NULL DEFAULT '' COMMENT '手机号',
    `name`                 VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '姓名',
    `ip_address`           VARCHAR(128) NOT NULL COMMENT 'IP 地址',
    `platform`             VARCHAR(32)  NOT NULL DEFAULT '' COMMENT '登录平台',
    `access_token_id`      VARCHAR(32)  NOT NULL DEFAULT '' COMMENT 'access token id',
    `refresh_token_id`     VARCHAR(32)  NOT NULL DEFAULT '' COMMENT 'refresh token id',
    `access_token_expire`  BIGINT UNSIGNED NOT NULL DEFAULT '0' COMMENT 'auth token 过期时间',
    `refresh_token_expire` BIGINT UNSIGNED NOT NULL DEFAULT '0' COMMENT 'refresh token 过期时间',
    `access_token`         VARCHAR(512) NOT NULL DEFAULT '' COMMENT 'access token',
    `refresh_token`        VARCHAR(512) NOT NULL DEFAULT '' COMMENT 'refresh token',
    `user_agent`           VARCHAR(512) NOT NULL DEFAULT '' COMMENT '浏览器和操作系统等信息',
    `fail_reason`          VARCHAR(512) NOT NULL DEFAULT '' COMMENT '失败原因',
    `description`          TEXT COMMENT '描述',
    `create_by`            VARCHAR(32)  NOT NULL COMMENT '创建人',
    `create_time`          BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`            VARCHAR(32)  NOT NULL COMMENT '修改人',
    `update_time`          BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY                    `idx_01` ( `user_id`, `access_token_id` ) USING BTREE,
    KEY                    `idx_02` ( `user_id`, `refresh_token_id` ) USING BTREE,
    KEY                    `idx_03` ( `username` ) USING BTREE,
    KEY                    `idx_04` ( `phone` ) USING BTREE,
    KEY                    `idx_05` ( `ip_address` ) USING BTREE,
    KEY                    `idx_06` ( `access_token_id` ) USING BTREE,
    KEY                    `idx_07` ( `refresh_token_id` ) USING BTREE,
    KEY                    `idx_08` ( `create_time` ) USING BTREE
) COMMENT = '登录日志表';

DROP TABLE IF EXISTS `t_iam_user_operate_log`;
CREATE TABLE `t_iam_user_operate_log`
(
    `id`            VARCHAR(32)   NOT NULL COMMENT 'ID',
    `user_id`       VARCHAR(32)   NOT NULL COMMENT '用户ID',
    `ip_address`    VARCHAR(128)  NOT NULL COMMENT 'IP地址',
    `platform`      VARCHAR(32)   NOT NULL DEFAULT '' COMMENT '登录平台',
    `action_status` VARCHAR(8)    NOT NULL DEFAULT 'SUCCESS' COMMENT '操作结果，FAIL:失败 SUCCESS:成功',
    `action_url`    VARCHAR(128)  NOT NULL COMMENT '操作地址',
    `action_param`  TEXT COMMENT '操作参数',
    `action_result` TEXT COMMENT '操作结果',
    `remark`        VARCHAR(2048) NOT NULL COMMENT '备注',
    `create_by`     VARCHAR(32)   NOT NULL COMMENT '创建人',
    `create_time`   BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`     VARCHAR(32)   NOT NULL COMMENT '修改人',
    `update_time`   BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY             `idx_01` ( `user_id` ) USING BTREE,
    KEY             `idx_02` ( `action_url` ) USING BTREE,
    KEY             `idx_03` ( `create_time` ) USING BTREE
) COMMENT = '用户操作日志表';

DROP TABLE IF EXISTS `t_iam_oauth2_registered_client`;
CREATE TABLE `t_iam_oauth2_registered_client`
(
    `id`                            VARCHAR(128)  NOT NULL COMMENT 'ID',
    `status`                        VARCHAR(8)    NOT NULL default 'ENABLE' COMMENT '状态,（DISABLE:禁用，ENABLE:启用）',
    `client_id`                     VARCHAR(64)   NOT NULL COMMENT '客户端ID',
    `client_id_issued_at`           BIGINT UNSIGNED NOT NULL COMMENT '客户端ID发放时间',
    `client_secret`                 VARCHAR(256)           DEFAULT NULL COMMENT '客户端密钥',
    `client_secret_expires_at`      BIGINT UNSIGNED COMMENT '客户端密钥过期时间',
    `client_name`                   VARCHAR(256)  NOT NULL COMMENT '客户端名称',
    `client_authentication_methods` VARCHAR(1024) NOT NULL COMMENT '客户端认证方法',
    `authorization_grant_types`     VARCHAR(1024) NOT NULL COMMENT '授权授予类型',
    `redirect_uris`                 VARCHAR(1024)          DEFAULT NULL COMMENT '重定向URI',
    `post_logout_redirect_uris`     VARCHAR(1024)          DEFAULT NULL COMMENT '注销后重定向URI',
    `scopes`                        VARCHAR(1024) NOT NULL COMMENT '作用域',
    `client_settings`               VARCHAR(4096) NOT NULL COMMENT '客户端设置',
    `token_settings`                VARCHAR(4096) NOT NULL COMMENT '令牌设置',
    `web_hook_info`                 VARCHAR(2048) COMMENT 'WebHook信息',
    `create_by`                     VARCHAR(32)   NOT NULL COMMENT '创建人',
    `create_time`                   BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`                     VARCHAR(32)   NOT NULL COMMENT '修改人',
    `update_time`                   BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT = 'OAuth2 客户端注册信息表';

DROP TABLE IF EXISTS `t_iam_oauth2_authorization_consent`;
CREATE TABLE `t_iam_oauth2_authorization_consent`
(
    `id`                   VARCHAR(128)  NOT NULL COMMENT '授权记录ID',
    `registered_client_id` VARCHAR(128)  NOT NULL COMMENT '注册客户端ID',
    `principal_name`       VARCHAR(256)  NOT NULL COMMENT '主体名称',
    `authorities`          VARCHAR(1000) NOT NULL COMMENT '授权权限',
    `create_by`            VARCHAR(32)   NOT NULL COMMENT '创建人',
    `create_time`          BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`            VARCHAR(32)   NOT NULL COMMENT '修改人',
    `update_time`          BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (registered_client_id, principal_name)
) COMMENT = 'OAuth2 授权同意信息表';

DROP TABLE IF EXISTS `t_iam_oauth2_authorization`;
CREATE TABLE `t_iam_oauth2_authorization`
(
    `id`                            VARCHAR(128) NOT NULL COMMENT '授权记录ID',
    `registered_client_id`          VARCHAR(128) NOT NULL COMMENT '注册客户端ID',
    `principal_name`                VARCHAR(256) NOT NULL COMMENT '主体名称',
    `authorization_grant_type`      VARCHAR(128) NOT NULL COMMENT '授权授予类型',
    `authorized_scopes`             VARCHAR(1024) DEFAULT NULL COMMENT '授权范围',
    `attributes`                    BLOB          DEFAULT NULL COMMENT '属性',
    `state`                         VARCHAR(512)  DEFAULT NULL COMMENT '状态',
    `authorization_code_value`      BLOB          DEFAULT NULL COMMENT '授权码值',
    `authorization_code_issued_at`  BIGINT UNSIGNED DEFAULT NULL COMMENT '授权码发放时间',
    `authorization_code_expires_at` BIGINT UNSIGNED DEFAULT NULL COMMENT '授权码过期时间',
    `authorization_code_metadata`   BLOB          DEFAULT NULL COMMENT '授权码元数据',
    `access_token_value`            BLOB          DEFAULT NULL COMMENT '访问令牌值',
    `access_token_issued_at`        BIGINT UNSIGNED DEFAULT NULL COMMENT '访问令牌发放时间',
    `access_token_expires_at`       BIGINT UNSIGNED DEFAULT NULL COMMENT '访问令牌过期时间',
    `access_token_metadata`         BLOB          DEFAULT NULL COMMENT '访问令牌元数据',
    `access_token_type`             VARCHAR(128)  DEFAULT NULL COMMENT '访问令牌类型',
    `access_token_scopes`           VARCHAR(1024) DEFAULT NULL COMMENT '访问令牌范围',
    `oidc_id_token_value`           BLOB          DEFAULT NULL COMMENT 'OpenID Connect ID 令牌值',
    `oidc_id_token_issued_at`       BIGINT UNSIGNED DEFAULT NULL COMMENT 'OpenID Connect ID 令牌发放时间',
    `oidc_id_token_expires_at`      BIGINT UNSIGNED DEFAULT NULL COMMENT 'OpenID Connect ID 令牌过期时间',
    `oidc_id_token_metadata`        BLOB          DEFAULT NULL COMMENT 'OpenID Connect ID 令牌元数据',
    `refresh_token_value`           BLOB          DEFAULT NULL COMMENT '刷新令牌值',
    `refresh_token_issued_at`       BIGINT UNSIGNED DEFAULT NULL COMMENT '刷新令牌发放时间',
    `refresh_token_expires_at`      BIGINT UNSIGNED DEFAULT NULL COMMENT '刷新令牌过期时间',
    `refresh_token_metadata`        BLOB          DEFAULT NULL COMMENT '刷新令牌元数据',
    `user_code_value`               BLOB          DEFAULT NULL COMMENT '用户码值',
    `user_code_issued_at`           BIGINT UNSIGNED DEFAULT NULL COMMENT '用户码发放时间',
    `user_code_expires_at`          BIGINT UNSIGNED DEFAULT NULL COMMENT '用户码过期时间',
    `user_code_metadata`            BLOB          DEFAULT NULL COMMENT '用户码元数据',
    `device_code_value`             BLOB          DEFAULT NULL COMMENT '设备码值',
    `device_code_issued_at`         BIGINT UNSIGNED DEFAULT NULL COMMENT '设备码发放时间',
    `device_code_expires_at`        BIGINT UNSIGNED DEFAULT NULL COMMENT '设备码过期时间',
    `device_code_metadata`          BLOB          DEFAULT NULL COMMENT '设备码元数据',
    `create_by`                     VARCHAR(32)  NOT NULL COMMENT '创建人',
    `create_time`                   BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`                     VARCHAR(32)  NOT NULL COMMENT '修改人',
    `update_time`                   BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (id)
) COMMENT = 'OAuth2 授权信息表';