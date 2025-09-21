DROP TABLE IF EXISTS `t_crm_customer`;
CREATE TABLE `t_crm_customer`
(
    `id`                   VARCHAR(32) NOT NULL COMMENT 'ID',
    `code`                 VARCHAR(12) NOT NULL COMMENT '编码',
    `identity_card_number` VARCHAR(32) NOT NULL COMMENT '身份证号',
    `name`                 VARCHAR(64) NOT NULL COMMENT '姓名',
    `gender`               VARCHAR(16) NOT NULL 'UNDEFINED' COMMENT '性别',
    `create_by`            VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`          BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`            VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`          BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `code` ) USING BTREE,
    UNIQUE KEY `uk_02` ( `identity_card_number` ) USING BTREE,
    KEY                    `idx_01` ( `create_time` ) USING BTREE
) COMMENT = '客户表';


DROP TABLE IF EXISTS `t_crm_member`;
CREATE TABLE `t_crm_member`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `code`        VARCHAR(12) NOT NULL COMMENT '编码',
    `email`       VARCHAR(32) NOT NULL default '' COMMENT '邮箱',
    `phone`       VARCHAR(16) NOT NULL COMMENT '手机号',
    `name`        VARCHAR(64) NOT NULL COMMENT '姓名',
    `gender`      VARCHAR(16) NOT NULL default 'UNDEFINED' COMMENT '性别',
    `birth_year`  INT UNSIGNED NOT NULL DEFAULT '0' COMMENT '生日年',
    `birth_month` INT UNSIGNED NOT NULL DEFAULT '0' COMMENT '生日月',
    `birth_day`   INT UNSIGNED NOT NULL DEFAULT '0' COMMENT '生日天',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `code` ) USING BTREE,
    UNIQUE KEY `uk_01` ( `phone` ) USING BTREE,
    KEY           `idx_01` ( `email` ) USING BTREE,
    KEY           `idx_01` ( `create_time` ) USING BTREE,
) COMMENT = '会员表';
