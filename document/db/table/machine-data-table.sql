DROP TABLE IF EXISTS `t_data_area`;
CREATE TABLE `t_data_area`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `parent_id`   VARCHAR(32) NOT NULL COMMENT '父ID',
    `code`        VARCHAR(12) NOT NULL COMMENT '编码',
    `name`        VARCHAR(64) NOT NULL COMMENT '名称',
    `sort`        BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `code` ) USING BTREE,
    KEY           `idx_01` ( `parent_id` ) USING BTREE
) COMMENT = '地区表';

DROP TABLE IF EXISTS `t_data_label_category`;
CREATE TABLE `t_data_label_category`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `parent_id`   VARCHAR(32) NOT NULL COMMENT '父ID',
    `code`        VARCHAR(32) NOT NULL COMMENT '编码',
    `name`        VARCHAR(32) NOT NULL COMMENT '名称',
    `sort`        BIGINT UNSIGNED NOT NULL DEFAULT '0' COMMENT '排序，sort值大的排序靠前',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `code` ) USING BTREE,
    UNIQUE KEY `uk_02` ( `parent_id`,`name` ) USING BTREE,
    KEY           `idx_01` ( `update_time` ) USING BTREE
) COMMENT = '人工标签分类表';

DROP TABLE IF EXISTS `t_data_label`;
CREATE TABLE `t_data_label`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `category_id` VARCHAR(32) NOT NULL COMMENT '分类ID',
    `code`        VARCHAR(32) NOT NULL COMMENT '编码',
    `name`        VARCHAR(32) NOT NULL COMMENT '名称',
    `sort`        BIGINT UNSIGNED NOT NULL DEFAULT '0' COMMENT '排序，sort值大的排序靠前',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `code` ) USING BTREE,
    UNIQUE KEY `uk_02` ( `category_id`,`name` ) USING BTREE,
    KEY           `idx_01` ( `update_time` ) USING BTREE
) COMMENT = '人工标签表';

DROP TABLE IF EXISTS `t_data_label_option`;
CREATE TABLE `t_data_label_option`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `label_id`    VARCHAR(32) NOT NULL COMMENT '人工标签ID',
    `code`        VARCHAR(32) NOT NULL COMMENT '编码',
    `name`        VARCHAR(32) NOT NULL DEFAULT '' COMMENT '名称',
    `status`      VARCHAR(8)  NOT NULL default 'DISABLE' COMMENT '状态,（DISABLE:禁用，ENABLE:启用）',
    `sort`        BIGINT UNSIGNED NOT NULL DEFAULT '0' COMMENT '排序，sort值大的排序靠前',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `code` ) USING BTREE,
    UNIQUE KEY `uk_02` (`label_id`, `name` ) USING BTREE,
    KEY           `idx_01` ( `update_time` ) USING BTREE
) COMMENT = '人工标签选项表';

DROP TABLE IF EXISTS `t_data_tag_category`;
CREATE TABLE `t_data_tag_category`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `parent_id`   VARCHAR(32) NOT NULL COMMENT '父ID',
    `code`        VARCHAR(16) NOT NULL COMMENT '编码',
    `name`        VARCHAR(32) NOT NULL DEFAULT '' COMMENT '名称',
    `sort`        INT UNSIGNED NOT NULL COMMENT '排序，sort值大的排序靠前',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `name` ) USING BTREE,
    KEY           `idx_01` ( `update_time` ) USING BTREE
) COMMENT = '智能标签分类表';

DROP TABLE IF EXISTS `t_data_tag`;
CREATE TABLE `t_data_tag`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `category_id` VARCHAR(32) NOT NULL COMMENT '分类ID',
    `sort`        BIGINT UNSIGNED NOT NULL COMMENT '排序，sort值大的排序靠前',
    `name`        VARCHAR(32) NOT NULL DEFAULT '' COMMENT '名称',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `name` ) USING BTREE,
    KEY           `idx_01` ( `update_time` ) USING BTREE
) COMMENT = '智能标签表';

DROP TABLE IF EXISTS `t_data_leaf_alloc`;
CREATE TABLE `t_data_leaf_alloc`
(
    `id`          varchar(32)   NOT NULL COMMENT 'ID',
    `biz_tag`     varchar(128)  NOT NULL COMMENT '业务标签',
    `max_id`      bigint unsigned NOT NULL DEFAULT '0' COMMENT '当前已分配的最大ID',
    `step`        int unsigned NOT NULL DEFAULT '100' COMMENT '每次分配的步长，默认为100',
    `expire_time` bigint unsigned NOT NULL COMMENT '过期时间',
    `remark`      varchar(2048) NOT NULL COMMENT '备注',
    `create_by`   varchar(32)   NOT NULL COMMENT '创建人',
    `create_time` bigint unsigned NOT NULL COMMENT '创建时间',
    `update_by`   varchar(32)   NOT NULL COMMENT '修改人',
    `update_time` bigint unsigned NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_01` (`biz_tag`),
    KEY           `idx_01` (`expire_time`)
) COMMENT='leaf 表';

DROP TABLE IF EXISTS `t_data_system_config`;
CREATE TABLE `t_data_system_config`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `category`    varchar(32) NOT NULL DEFAULT '' COMMENT '分类',
    `code`        varchar(64) NOT NULL COMMENT '编码',
    `content`     text COMMENT '内容',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_01` (`category`,`code`)
) COMMENT='配置信息表';


DROP TABLE IF EXISTS `t_data_material_category`;
CREATE TABLE `t_data_material_category`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `parent_id`   VARCHAR(32) NOT NULL COMMENT '父ID',
    `code`        VARCHAR(32) NOT NULL COMMENT '编码',
    `name`        VARCHAR(32) NOT NULL COMMENT '名称',
    `sort`        BIGINT UNSIGNED NOT NULL DEFAULT '0' COMMENT '排序，sort值大的排序靠前',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `code` ) USING BTREE,
    UNIQUE KEY `uk_02` ( `parent_id`,`name` ) USING BTREE,
    KEY           `idx_01` ( `update_time` ) USING BTREE
) COMMENT = '素材分类表';

DROP TABLE IF EXISTS `t_data_material_category_relation`;
CREATE TABLE `t_data_material_category_relation`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `category_id` VARCHAR(32) NOT NULL COMMENT '分类id',
    `material_id` VARCHAR(16) NOT NULL COMMENT '素材ID',
    `sort`        BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `category_id`,`material_id` ) USING BTREE,
    KEY           `idx_01` ( `material_id` ) USING BTREE
) COMMENT = '素材与分类的关系表';

DROP TABLE IF EXISTS `t_data_material`;
CREATE TABLE `t_data_material`
(
    `id`           VARCHAR(32)   NOT NULL COMMENT 'ID',
    `status`       VARCHAR(16)   NOT NULL COMMENT '状态',
    `type`         VARCHAR(8)    NOT NULL COMMENT '类型',
    `storage_type` VARCHAR(16)   NOT NULL COMMENT '持久化类型',
    `title`        VARCHAR(64)   NOT NULL COMMENT '标题',
    `name`         VARCHAR(64)   NOT NULL COMMENT '名称',
    `size`         BIGINT UNSIGNED NOT NULL COMMENT '大小（字节）',
    `file_info`    VARCHAR(4096) NOT NULL COMMENT 'obs文件信息',
    `expire_time`  bigint unsigned NOT NULL COMMENT '过期时间',
    `create_by`    VARCHAR(32)   NOT NULL COMMENT '创建人',
    `create_time`  BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`    VARCHAR(32)   NOT NULL COMMENT '修改人',
    `update_time`  BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY            `idx_01` ( `expire_time` ) USING BTREE,
    KEY            `idx_02` ( `create_time` ) USING BTREE,
    KEY            `idx_03` ( `update_time` ) USING BTREE
) COMMENT = '素材表';

DROP TABLE IF EXISTS `t_data_material_text`;
CREATE TABLE `t_data_material_text`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `material_id` VARCHAR(16) NOT NULL COMMENT '素材ID',
    `word_count`  int unsigned NOT NULL COMMENT '字数统计',
    `format`      VARCHAR(16) NOT NULL COMMENT '文本格式',
    `content`     TEXT        NOT NULL COMMENT '内容',
    `description` TEXT COMMENT '描述',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY           `idx_01` ( `material_id` ) USING BTREE
) COMMENT = '文本素材表';

DROP TABLE IF EXISTS `t_data_material_image`;
CREATE TABLE `t_data_material_image`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `material_id` VARCHAR(16) NOT NULL COMMENT '素材ID',
    `width`       int unsigned NOT NULL COMMENT '图片宽度(像素)',
    `height`      int unsigned NOT NULL COMMENT '图片高度(像素)',
    `format`      VARCHAR(16) NOT NULL COMMENT '图片格式',
    `dpi`         int unsigned DEFAULT NULL COMMENT '分辨率',
    `description` TEXT COMMENT '描述',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY           `idx_01` ( `material_id` ) USING BTREE
) COMMENT = '图片素材表';

DROP TABLE IF EXISTS `t_data_material_audio`;
CREATE TABLE `t_data_material_audio`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `material_id` VARCHAR(16) NOT NULL COMMENT '素材ID',
    `duration`    int unsigned NOT NULL COMMENT '时长(秒)',
    `format`      VARCHAR(16) NOT NULL COMMENT '音频格式',
    `bitrate`     int unsigned DEFAULT NULL COMMENT '比特率(kbps)',
    `sample_rate` int unsigned DEFAULT NULL COMMENT '采样率(Hz)',
    `channels`    tinyint DEFAULT NULL COMMENT '声道数(1-单声道,2-立体声)',
    `description` TEXT COMMENT '描述',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY           `idx_01` ( `material_id` ) USING BTREE
) COMMENT = '音频素材表';

DROP TABLE IF EXISTS `t_data_material_video`;
CREATE TABLE `t_data_material_video`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `material_id` VARCHAR(16) NOT NULL COMMENT '素材ID',
    `duration`    int unsigned NOT NULL COMMENT '时长(秒)',
    `width`       int unsigned NOT NULL COMMENT '视频宽度(像素)',
    `height`      int unsigned NOT NULL COMMENT '视频高度(像素)',
    `format`      VARCHAR(16) NOT NULL COMMENT '视频格式',
    `bitrate`     int unsigned DEFAULT NULL COMMENT '比特率(kbps)',
    `fps`         int unsigned DEFAULT NULL COMMENT '帧率',
    `codec`       VARCHAR(32) DEFAULT NULL COMMENT '视频编码格式',
    `audio_codec` VARCHAR(32) DEFAULT NULL COMMENT '音频编码格式',
    `description` TEXT COMMENT '描述',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY           `idx_01` ( `material_id` ) USING BTREE
) COMMENT = '视频素材表';

DROP TABLE IF EXISTS `t_data_material_document`;
CREATE TABLE `t_data_material_document`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `material_id` VARCHAR(16) NOT NULL COMMENT '素材ID',
    `format`      VARCHAR(16) NOT NULL COMMENT '文档格式',
    `page_count`  int unsigned DEFAULT NULL COMMENT '页数',
    `description` TEXT COMMENT '描述',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY           `idx_01` ( `material_id` ) USING BTREE
) COMMENT = '文档素材表';

DROP TABLE IF EXISTS `t_data_material_file`;
CREATE TABLE `t_data_material_file`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `material_id` VARCHAR(16) NOT NULL COMMENT '素材ID',
    `format`      VARCHAR(16) NOT NULL COMMENT '文件格式',
    `checksum`    VARCHAR(512) DEFAULT NULL COMMENT '文件校验值',
    `description` TEXT COMMENT '描述',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY           `idx_01` ( `material_id` ) USING BTREE
) COMMENT = '文件素材表';

DROP TABLE IF EXISTS `t_data_attachment_category`;
CREATE TABLE `t_data_attachment_category`
(
    `id`          VARCHAR(32) NOT NULL COMMENT '分类ID',
    `parent_id`   VARCHAR(32) NOT NULL COMMENT '父分类ID',
    `code`        VARCHAR(32) NOT NULL COMMENT '分类编码',
    `name`        VARCHAR(64) NOT NULL COMMENT '分类名称',
    `sort`        INT UNSIGNED NOT NULL DEFAULT '0' COMMENT '排序，值大的靠前',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间(时间戳)',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '更新人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间(时间戳)',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_01` (`code`),
    UNIQUE KEY `uk_02` (`parent_id`, `name`),
    KEY           `idx_01` (`update_time`)
) COMMENT='附件分类表';

DROP TABLE IF EXISTS `t_data_attachment_category_relation`;
CREATE TABLE `t_data_attachment_category_relation`
(
    `id`            VARCHAR(32) NOT NULL COMMENT '关系ID',
    `category_id`   VARCHAR(32) NOT NULL COMMENT '分类ID',
    `attachment_id` VARCHAR(32) NOT NULL COMMENT '附件ID',
    `sort`          BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
    `create_by`     VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`   BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`     VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`   BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_01` (`category_id`,`attachment_id`),
    KEY             `idx_01` (`attachment_id`)
) COMMENT='附件分类关系表';

DROP TABLE IF EXISTS `t_data_attachment`;
CREATE TABLE `t_data_attachment`
(
    `id`           VARCHAR(32)   NOT NULL COMMENT 'ID',
    `status`       VARCHAR(16)   NOT NULL COMMENT '状态',
    `type`         VARCHAR(8)    NOT NULL COMMENT '类型',
    `storage_type` VARCHAR(16)   NOT NULL COMMENT '持久化类型',
    `title`        VARCHAR(64)   NOT NULL COMMENT '标题',
    `name`         VARCHAR(64)   NOT NULL COMMENT '名称',
    `size`         BIGINT UNSIGNED NOT NULL COMMENT '大小（字节）',
    `file_info`    VARCHAR(4096) NOT NULL COMMENT 'obs文件信息',
    `expire_time`  bigint unsigned NOT NULL COMMENT '过期时间',
    `description`  VARCHAR(2048) COMMENT '文件描述',
    `create_by`    VARCHAR(32)   NOT NULL COMMENT '创建人',
    `create_time`  BIGINT UNSIGNED NOT NULL COMMENT '创建时间(时间戳)',
    `update_by`    VARCHAR(32)   NOT NULL COMMENT '更新人',
    `update_time`  BIGINT UNSIGNED NOT NULL COMMENT '更新时间(时间戳)',
    PRIMARY KEY (`id`),
    KEY            `idx_01` (`expire_time`),
    KEY            `idx_02` (`create_time`),
    KEY            `idx_03` (`update_time`),
    KEY            `idx_04` (`create_by`),
    KEY            `idx_05` (`update_by`)
) COMMENT='ERP附件主表';

DROP TABLE IF EXISTS `t_data_attachment_table_relation`;
CREATE TABLE `t_data_attachment_table_relation`
(
    `id`            VARCHAR(32) NOT NULL COMMENT 'ID',
    `attachment_id` VARCHAR(32) NOT NULL COMMENT '附件ID',
    `table_name`    VARCHAR(32) NOT NULL COMMENT '表名称',
    `data_id`       VARCHAR(32) NOT NULL COMMENT '数据ID',
    `sort`          BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
    `create_by`     VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`   BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`     VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`   BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_01` (`attachment_id`,`data_id` ,`table_name`),
    KEY             `idx_01` (`data_id` ,`table_name` )
) COMMENT='ERP附件和表关联表';

DROP TABLE IF EXISTS `t_data_attachment_operation_log`;
CREATE TABLE `t_data_attachment_operation_log`
(
    `id`             VARCHAR(32)  NOT NULL COMMENT '日志ID',
    `attachment_id`  VARCHAR(32)  NOT NULL COMMENT '附件ID',
    `operation_type` VARCHAR(16)  NOT NULL COMMENT '操作类型',
    `ip_address`     VARCHAR(128) NOT NULL COMMENT 'IP 地址',
    `platform`       VARCHAR(32)  NOT NULL DEFAULT '' COMMENT '登录平台',
    `user_agent`     VARCHAR(512) NOT NULL DEFAULT '' COMMENT '浏览器和操作系统等信息',
    `create_by`      VARCHAR(32)  NOT NULL COMMENT '创建人',
    `create_time`    BIGINT UNSIGNED NOT NULL COMMENT '创建时间(时间戳)',
    `update_by`      VARCHAR(32)  NOT NULL COMMENT '更新人',
    `update_time`    BIGINT UNSIGNED NOT NULL COMMENT '更新时间(时间戳)',
    PRIMARY KEY (`id`),
    KEY              `idx_01` (`attachment_id`),
    KEY              `idx_02` (`ip_address`),
    KEY              `idx_03` (`create_by`),
    KEY              `idx_04` (`create_time`)
) COMMENT='附件操作日志表';

DROP TABLE IF EXISTS `t_data_company_employee`;
CREATE TABLE `t_data_company_employee`
(
    `id`              VARCHAR(32) NOT NULL COMMENT 'ID',
    `user_id`         VARCHAR(32) NOT NULL COMMENT '用户id',
    `employee_status` VARCHAR(16) NOT NULL COMMENT '状态,（CompanyEmployeeStatusEnum）',
    `create_by`       VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`     BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`       VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`     BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `user_id` ) USING BTREE
) COMMENT = '公司员工';

DROP TABLE IF EXISTS `t_data_shop_employee`;
CREATE TABLE `t_data_shop_employee`
(
    `id`              VARCHAR(32) NOT NULL COMMENT 'ID',
    `user_id`         VARCHAR(32) NOT NULL COMMENT '用户id',
    `employee_status` VARCHAR(16) NOT NULL COMMENT '状态,（ShopEmployeeStatusEnum）',
    `create_by`       VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`     BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`       VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`     BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `user_id` ) USING BTREE
) COMMENT = '门店员工';

DROP TABLE IF EXISTS `t_data_supplier_user`;
CREATE TABLE `t_data_supplier_user`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `user_id`     VARCHAR(32) NOT NULL COMMENT '用户id',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `user_id` ) USING BTREE
) COMMENT = '供应商用户表';

DROP TABLE IF EXISTS `t_data_franchisee`;
CREATE TABLE `t_data_franchisee`
(
    `id`                        VARCHAR(32)    NOT NULL COMMENT 'ID',
    `user_id`                   VARCHAR(32)    NOT NULL COMMENT '用户id',
    `name`                      VARCHAR(32)    NOT NULL COMMENT '加盟商名称',
    `code`                      VARCHAR(32)    NOT NULL COMMENT '加盟商编码',
    `entity_type`               VARCHAR(16)    NOT NULL COMMENT '主体类型',
    `certificate_type`          VARCHAR(48)    NOT NULL COMMENT '证件类型（CertificateTypeEnum）',
    `certificate_number`        VARCHAR(32)    NOT NULL COMMENT '证件号码',
    `educational_qualification` VARCHAR(32) COMMENT '学历',
    `work_experience_type`      VARCHAR(32) COMMENT '工作经验类型',
    `work_experience_value`     VARCHAR(16) COMMENT '工作经验值',
    `personal_assets`           DECIMAL(15, 2) NOT NULL default '0' COMMENT '个人资产',
    `franchise_date`            BIGINT UNSIGNED NOT NULL default '0' COMMENT '加盟日期',
    `incoming_channel_first`    VARCHAR(32) COMMENT '一级进线渠道',
    `incoming_channel_second`   VARCHAR(32) COMMENT '二级进线渠道',
    `incoming_channel_third`    VARCHAR(32) COMMENT '三级进线渠道',
    `description`               VARCHAR(2048)  NOT NULL default '' COMMENT '描述',
    `create_by`                 VARCHAR(32)    NOT NULL COMMENT '创建人',
    `create_time`               BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`                 VARCHAR(32)    NOT NULL COMMENT '修改人',
    `update_time`               BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `user_id` ) USING BTREE,
    UNIQUE KEY `uk_02` ( `code` ) USING BTREE,
    UNIQUE KEY `uk_03` ( `certificate_number`,`certificate_type` ) USING BTREE,
    KEY                         `idx_01` ( `create_time` ) USING BTREE,
    KEY                         `idx_02` ( `update_time` ) USING BTREE
) COMMENT = '加盟商表';

DROP TABLE IF EXISTS `t_data_franchisee_shop_relation`;
CREATE TABLE `t_data_franchisee_shop_relation`
(
    `id`            VARCHAR(32) NOT NULL COMMENT 'ID',
    `franchisee_id` VARCHAR(32) NOT NULL COMMENT '加盟商id',
    `shop_id`       VARCHAR(32) NOT NULL COMMENT '门店ID',
    `sort`          BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
    `create_by`     VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`   BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`     VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`   BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` (`shop_id`) USING BTREE,
    KEY             `idx_01` ( `franchisee_id` ) USING BTREE
) COMMENT = '加盟商门店关系表';

DROP TABLE IF EXISTS `t_data_shop_organization_relation`;
CREATE TABLE `t_data_shop_organization_relation`
(
    `id`                VARCHAR(32) NOT NULL COMMENT 'ID',
    `shop_id`           VARCHAR(32) NOT NULL COMMENT '门店id',
    `organization_id`   VARCHAR(32) NOT NULL COMMENT '组织id',
    `organization_type` VARCHAR(16) NOT NULL COMMENT '类型',
    `sort`              BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
    `create_by`         VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`       BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`         VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`       BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` (`shop_id`,`organization_type`) USING BTREE,
    UNIQUE KEY `uk_02` (`organization_id`,`shop_id`) USING BTREE
) COMMENT = '门店组织关系表';

DROP TABLE IF EXISTS `t_data_supplier_company`;
CREATE TABLE `t_data_supplier_company`
(
    `id`                     VARCHAR(32)   NOT NULL COMMENT 'ID',
    `name`                   VARCHAR(32)   NOT NULL COMMENT '名称',
    `status`                 VARCHAR(8)    NOT NULL default 'DISABLE' COMMENT '状态,（StatusEnum）',
    `business_category`      VARCHAR(32)   NOT NULL COMMENT '供应商业务类别（SupplierBusinessCategoryEnum）',
    `code`                   VARCHAR(32)   NOT NULL default '' COMMENT '编码',
    `contact_name`           varchar(20)   NOT NULL DEFAULT '' COMMENT '联系人',
    `contact_phone`          varchar(20)   NOT NULL DEFAULT '' COMMENT '联系电话',
    `correspondence_address` varchar(512)  NOT NULL DEFAULT '' COMMENT '通讯地址',
    `financial_information`  varchar(512)  NOT NULL DEFAULT '' COMMENT '财务信息',
    `contract_information`   varchar(1024) NOT NULL DEFAULT '' COMMENT '合同信息',
    `create_by`              VARCHAR(32)   NOT NULL COMMENT '创建人',
    `create_time`            BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`              VARCHAR(32)   NOT NULL COMMENT '修改人',
    `update_time`            BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `code` ) USING BTREE,
    UNIQUE KEY `uk_02` ( `name` ) USING BTREE,
    UNIQUE KEY `uk_03` ( `contact_phone` ) USING BTREE
) COMMENT = '供应商公司表';

DROP TABLE IF EXISTS `t_data_supplier_company_relation`;
CREATE TABLE `t_data_supplier_company_relation`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `supplier_id` VARCHAR(32) NOT NULL COMMENT '供应商id',
    `company_id`  VARCHAR(32) NOT NULL COMMENT '归属公司ID',
    `sort`        BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `supplier_id`,`company_id`) USING BTREE,
    KEY           `idx_01` ( `company_id` ) USING BTREE
) COMMENT = '供应商公司关系表';

DROP TABLE IF EXISTS `t_data_shop`;
CREATE TABLE `t_data_shop`
(
    `id`               VARCHAR(32)   NOT NULL COMMENT 'ID',
    `code`             VARCHAR(16)   NOT NULL COMMENT '编码',
    `name`             VARCHAR(32)   NOT NULL COMMENT '名称',
    `business_status`  VARCHAR(32)   NOT NULL COMMENT '经营状态：门店的商业生命周期阶段',
    `operation_status` VARCHAR(32)   NOT NULL COMMENT '运营状态：日常营业的可操作性状态',
    `physical_status`  VARCHAR(32)   NOT NULL COMMENT '物理状态：门店物理空间的可用性',
    `country_code`     VARCHAR(16)   NOT NULL DEFAULT '' COMMENT '国家编码',
    `province_code`    VARCHAR(16)   NOT NULL DEFAULT '' COMMENT '省编码',
    `city_code`        VARCHAR(16)   NOT NULL DEFAULT '' COMMENT '市编码',
    `area_code`        VARCHAR(16)   NOT NULL DEFAULT '' COMMENT '区编码',
    `address`          varchar(512)  NOT NULL DEFAULT '' COMMENT '详细地址',
    `latitude`         DECIMAL(11, 8) COMMENT '经度',
    `longitude`        DECIMAL(11, 8) COMMENT '纬度',
    `description`      VARCHAR(2048) NOT NULL default '' COMMENT '描述',
    `create_by`        VARCHAR(32)   NOT NULL COMMENT '创建人',
    `create_time`      BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`        VARCHAR(32)   NOT NULL COMMENT '修改人',
    `update_time`      BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `code` ) USING BTREE,
    UNIQUE KEY `uk_02` ( `name` ) USING BTREE,
    KEY                `idx_01` ( `business_status` ) USING BTREE,
    KEY                `idx_02` ( `operation_status` ) USING BTREE,
    KEY                `idx_03` ( `physical_status` ) USING BTREE,
    KEY                `idx_04` ( `province_code` ) USING BTREE,
    KEY                `idx_05` ( `city_code` ) USING BTREE,
    KEY                `idx_06` ( `area_code` ) USING BTREE,
    KEY                `idx_07` ( `create_time` ) USING BTREE,
    KEY                `idx_08` ( `update_time`) USING BTREE
) COMMENT = '门店表';

DROP TABLE IF EXISTS `t_data_shop_label_option_relation`;
CREATE TABLE `t_data_shop_label_option_relation`
(
    `id`              VARCHAR(32) NOT NULL COMMENT 'ID',
    `shop_id`         VARCHAR(32) NOT NULL COMMENT '门店ID',
    `label_option_id` VARCHAR(32) NOT NULL COMMENT '人工标签选项id',
    `sort`            BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
    `create_by`       VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`     BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`       VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`     BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `shop_id`,`label_option_id` ) USING BTREE,
    KEY               `idx_01` (`label_option_id`,`shop_id`) USING BTREE
) COMMENT = '门店&人工标签选项关系表';

DROP TABLE IF EXISTS `t_data_user_collect_shop`;
CREATE TABLE `t_data_user_collect_shop`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `user_id`     VARCHAR(32) NOT NULL COMMENT '用户Id',
    `shop_id`     VARCHAR(32) NOT NULL COMMENT '门店ID',
    `sort`        BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `user_id`,`shop_id` ) USING BTREE
) COMMENT = '用户收藏门店信息';

DROP TABLE IF EXISTS `t_data_external_field_data`;
CREATE TABLE `t_data_external_field_data`
(
    `id`             VARCHAR(32) NOT NULL COMMENT 'ID',
    `table_name`     VARCHAR(32) NOT NULL COMMENT '表名',
    `field_name`     VARCHAR(32) NOT NULL COMMENT '字段名',
    `data_id`        VARCHAR(32) NOT NULL COMMENT '数据Id',
    `external_value` text DEFAULT NULL COMMENT '扩展字段值',
    `create_by`      VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`    BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`      VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`    BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_01` (`table_name`,`field_name`,`data_id`)
) COMMENT='扩展字段数据表';

DROP TABLE IF EXISTS `t_data_mq_reliable_message`;
CREATE TABLE `t_data_mq_reliable_message`
(
    `id`                 VARCHAR(32) NOT NULL COMMENT 'id',
    `message_uuid`       VARCHAR(32) NOT NULL COMMENT '消息唯一标识',
    `producer_name`      VARCHAR(64) NOT NULL COMMENT '生产者名称',
    `consumer_name`      VARCHAR(64) NOT NULL COMMENT '消费者名称',
    `resend_times`       INT UNSIGNED DEFAULT '0' COMMENT '重发次数',
    `last_send_time`     BIGINT UNSIGNED NOT NULL COMMENT '最后一次重发时间',
    `consumer_times`     INT UNSIGNED DEFAULT '0' COMMENT '消费次数',
    `last_consumer_time` BIGINT UNSIGNED NOT NULL COMMENT '最后一次消费时间',
    `next_exe_time`      BIGINT UNSIGNED NOT NULL COMMENT '下一次执行时间',
    `retry_strategy`     VARCHAR(512) COMMENT '重试策略',
    `create_by`          VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`        BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`          VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`        BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY `pk` ( `id` ) USING BTREE,
    UNIQUE KEY `uk_01` ( `message_uuid`,`consumer_name` ),
    KEY                  `idx_01` ( `next_exe_time` )
) COMMENT = 'MQ 可靠消息表';

DROP TABLE IF EXISTS `t_data_mq_dead_message`;
CREATE TABLE `t_data_mq_dead_message`
(
    `id`                 VARCHAR(32) NOT NULL COMMENT 'id',
    `message_uuid`       VARCHAR(32) NOT NULL COMMENT '消息唯一标识',
    `producer_name`      VARCHAR(64) NOT NULL COMMENT '生产者名称',
    `consumer_name`      VARCHAR(64) NOT NULL COMMENT '消费者名称',
    `resend_times`       INT UNSIGNED DEFAULT '0' COMMENT '重发次数',
    `last_send_time`     BIGINT UNSIGNED NOT NULL COMMENT '最后一次重发时间',
    `consumer_times`     INT UNSIGNED DEFAULT '0' COMMENT '消费次数',
    `last_consumer_time` BIGINT UNSIGNED NOT NULL COMMENT '最后一次消费时间',
    `next_exe_time`      BIGINT UNSIGNED NOT NULL COMMENT '下一次执行时间',
    `retry_strategy`     VARCHAR(512) COMMENT '重试策略',
    `create_by`          VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`        BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`          VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`        BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY `pk` ( `id` ) USING BTREE,
    UNIQUE KEY `uk_01` ( `message_uuid`,`consumer_name` ),
    KEY                  `idx_06` ( `create_time` )
) COMMENT = 'MQ 死亡消息信息表';

DROP TABLE IF EXISTS `t_data_mq_message_external`;
CREATE TABLE `t_data_mq_message_external`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'id',
    `table_name`  VARCHAR(32) NOT NULL COMMENT '表名',
    `data_id`     VARCHAR(32) NOT NULL COMMENT '数据Id',
    `content`     TEXT COMMENT '内容',
    `fail_reason` TEXT COMMENT '最近一次失败原因',
    `description` TEXT COMMENT '描述',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY `pk` ( `id` ) USING BTREE,
    KEY           `idx_01` ( `data_id`, `table_name` )
) COMMENT = 'MQ 消息扩展信息表';

DROP TABLE IF EXISTS `t_data_app_message_template`;
CREATE TABLE `t_data_app_message_template`
(
    `id`                VARCHAR(32) NOT NULL COMMENT 'ID',
    `template_type`     VARCHAR(100)         DEFAULT NULL COMMENT '模版类型(选址跟进提醒、迁址流程等具体消息的类型)',
    `template_category` VARCHAR(100)         DEFAULT NULL COMMENT '消息分类(合同、选址任务等)',
    `channel`           VARCHAR(500)         DEFAULT NULL COMMENT '通知渠道(站内信、AppPush、企微工作通知)',
    `template_info`     text                 DEFAULT NULL COMMENT '模版信息(通知时间、通知对象等)',
    `inform_title`      VARCHAR(100)         DEFAULT NULL COMMENT '通知标题',
    `inform_content`    text                 DEFAULT NULL COMMENT '通知内容',
    `sms_content`       text                 DEFAULT NULL COMMENT '短信内容',
    `status`            VARCHAR(10)          DEFAULT NULL COMMENT '状态(DISABLE-禁用，ENABLE-启用)',
    `create_by`         VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`       BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`         VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`       BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    `dr`                TINYINT     NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    KEY                 `idx_template_type` ( `template_type` ),
    KEY                 `idx_channel` ( `channel` )
) COMMENT = '超级APP消息模版表';

DROP TABLE IF EXISTS `t_data_app_message`;
CREATE TABLE `t_data_app_message`
(
    `id`                  VARCHAR(32) NOT NULL COMMENT 'ID',
    `batch_code`          VARCHAR(32)          DEFAULT NULL COMMENT '批次号',
    `message_template_id` VARCHAR(32) NOT NULL COMMENT '模版id',
    `message_category`    VARCHAR(255)         DEFAULT NULL COMMENT '消息分类',
    `operator_id`         VARCHAR(32)          DEFAULT NULL COMMENT '操作人',
    `operator_phone`      VARCHAR(20)          DEFAULT NULL COMMENT '操作人手机号',
    `receiver`            VARCHAR(32)          DEFAULT NULL COMMENT '接收人',
    `phone`               VARCHAR(20)          DEFAULT NULL COMMENT '接收人手机号',
    `readed`              TINYINT              DEFAULT NULL COMMENT '是否已读',
    `title`               VARCHAR(255)         DEFAULT NULL COMMENT '消息标题',
    `inform_content`      text                 DEFAULT NULL COMMENT '消息内容',
    `dispose`             TINYINT              DEFAULT NULL COMMENT '是否已处理',
    `channel`             VARCHAR(20)          DEFAULT NULL COMMENT '消息渠道',
    `query_source`        VARCHAR(255)         DEFAULT NULL COMMENT '查询来源',
    `business_content`    text                 DEFAULT NULL COMMENT '业务内容',
    `create_by`           VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`         BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`           VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`         BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    `dr`                  TINYINT     NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    KEY                   `idx_phone` ( `phone` ),
    KEY                   `idx_message_template_id` ( `message_template_id` ),
    KEY                   `idx_create_time` ( `create_time` )
) COMMENT = '超级APP消息表';

DROP TABLE IF EXISTS `t_data_download`;
CREATE TABLE `t_data_download`
(
    `id`            VARCHAR(32) NOT NULL COMMENT 'ID',
    `status`        VARCHAR(8)  NOT NULL COMMENT '任务状态',
    `attachment_id` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '附件ID',
    `content`       TEXT COMMENT '内容',
    `fail_cause`    TEXT COMMENT '失败原因',
    `create_by`     VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`   BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`     VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`   BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY             `idx_01` ( `attachment_id` ) USING BTREE,
    KEY             `idx_02` ( `update_by` ) USING BTREE,
    KEY             `idx_03` ( `create_time` ) USING BTREE
) COMMENT = '下载文件表';

DROP TABLE IF EXISTS `t_data_sms_record`;
CREATE TABLE `t_data_sms_record`
(
    `id`              VARCHAR(32) NOT NULL COMMENT 'ID',
    `category`        VARCHAR(32) NOT NULL COMMENT '分类(SmsCategoryEnum)',
    `code`            VARCHAR(64) NOT NULL COMMENT '编码',
    `result`          VARCHAR(8)  NOT NULL COMMENT '结果(SmsSendResultEnum)',
    `phone`           VARCHAR(16) NOT NULL COMMENT '手机号',
    `message_content` TEXT COMMENT '消息内容',
    `result_content`  TEXT COMMENT '返回内容',
    `fail_reason`     TEXT COMMENT '失败原因',
    `create_by`       VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`     BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`       VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`     BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY               `idx_01` ( `phone` ) USING BTREE,
    KEY               `idx_02` ( `category`, `code`,`phone` ) USING BTREE,
    KEY               `idx_03` ( `create_time` ) USING BTREE
) COMMENT = '短信记录表';


DROP TABLE IF EXISTS `t_data_item_category`;
CREATE TABLE `t_data_item_category`
(
    `id`          VARCHAR(32)   NOT NULL COMMENT 'ID',
    `parent_id`   VARCHAR(32)   NOT NULL COMMENT '父ID',
    `code`        VARCHAR(32)   NOT NULL COMMENT '编码',
    `name`        VARCHAR(32)   NOT NULL COMMENT '名称',
    `status`      VARCHAR(8)    NOT NULL default 'DISABLE' COMMENT '状态',
    `sort`        BIGINT UNSIGNED NOT NULL DEFAULT '0' COMMENT '排序，sort值大的排序靠前',
    `description` VARCHAR(2048) NOT NULL DEFAULT '' COMMENT '描述',
    `create_by`   VARCHAR(32)   NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32)   NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `code` ) USING BTREE,
    UNIQUE KEY `uk_02` ( `parent_id`,`name` ) USING BTREE,
    KEY           `idx_01` ( `create_time` ) USING BTREE,
    KEY           `idx_02` ( `update_time` ) USING BTREE
) COMMENT = '商品分类表';


DROP TABLE IF EXISTS `t_data_item_property`;
CREATE TABLE `t_data_item_property`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `code`        VARCHAR(32) NOT NULL COMMENT '编码',
    `name`        VARCHAR(32) NOT NULL COMMENT '名称',
    `sort`        BIGINT UNSIGNED NOT NULL DEFAULT '0' COMMENT '排序，sort值大的排序靠前',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `code` ) USING BTREE,
    UNIQUE KEY `uk_02` (`name` ) USING BTREE,
    KEY           `idx_01` ( `name` ) USING BTREE,
    KEY           `idx_02` ( `create_time` ) USING BTREE,
    KEY           `idx_03` ( `update_time` ) USING BTREE
) COMMENT = '商品属性表';


DROP TABLE IF EXISTS `t_data_item_property_value`;
CREATE TABLE `t_data_item_property_value`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `value`       VARCHAR(64) NOT NULL COMMENT '值',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `code` ) USING BTREE,
    UNIQUE KEY `uk_02` ( `value` ) USING BTREE,
    KEY           `idx_01` ( `update_time` ) USING BTREE
) COMMENT = '商品属性值表';


DROP TABLE IF EXISTS `t_data_item_property_value_relation`;
CREATE TABLE `t_data_item_property_value_relation`
(
    `id`                     VARCHAR(32) NOT NULL COMMENT 'ID',
    `item_property_id`       VARCHAR(32) NOT NULL COMMENT '商品属性ID',
    `item_property_value_id` VARCHAR(32) NOT NULL COMMENT '商品属性值ID',
    `sort`                   BIGINT UNSIGNED NOT NULL DEFAULT '0' COMMENT '排序，sort值大的排序靠前',
    `create_by`              VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`            BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`              VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`            BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `item_property_id`,`item_property_value_id` ) USING BTREE,
    KEY                      `idx_01` ( `item_property_value_id` ) USING BTREE
) COMMENT = '商品属性值关系表';


DROP TABLE IF EXISTS `t_data_item_spu`;
CREATE TABLE `t_data_item_spu`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `code`        VARCHAR(32) NOT NULL COMMENT '编码',
    `name`        VARCHAR(32) NOT NULL COMMENT '名称',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `code` ) USING BTREE,
    UNIQUE KEY `uk_02` ( `name` ) USING BTREE,
    KEY           `idx_01` ( `update_time` ) USING BTREE
) COMMENT = '商品spu表';

DROP TABLE IF EXISTS `t_data_item_sku`;
CREATE TABLE `t_data_item_sku`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `item_spu_id` VARCHAR(32) NOT NULL COMMENT '商品spu ID',
    `code`        VARCHAR(32) NOT NULL COMMENT '编码',
    `name`        VARCHAR(32) NOT NULL COMMENT '名称',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `code` ) USING BTREE,
    UNIQUE KEY `uk_02` ( `item_spu_id`,`name` ) USING BTREE,
    KEY           `idx_01` ( `update_time` ) USING BTREE
) COMMENT = '商品sku表';

DROP TABLE IF EXISTS `t_data_item_sn`;
CREATE TABLE `t_data_item_sn`
(
    `id`          VARCHAR(32) NOT NULL COMMENT 'ID',
    `item_sku_id` VARCHAR(32) NOT NULL COMMENT '商品sku ID',
    `code`        VARCHAR(32) NOT NULL COMMENT '编码',
    `name`        VARCHAR(32) NOT NULL COMMENT '名称',
    `create_by`   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `code` ) USING BTREE,
    KEY           `idx_01` ( `item_sku_id` ) USING BTREE,
    KEY           `idx_02` ( `update_time` ) USING BTREE
) COMMENT = '商品sn表';


DROP TABLE IF EXISTS `t_data_item_category_property_relation`;
CREATE TABLE `t_data_item_category_property_relation`
(
    `id`                          VARCHAR(32) NOT NULL COMMENT 'ID',
    `item_category_id`            VARCHAR(32) NOT NULL COMMENT '商品分类ID',
    `item_property_id`            VARCHAR(32) NOT NULL COMMENT '商品属性ID',
    `item_category_property_type` VARCHAR(16) NOT NULL COMMENT '商品属性类型',
    `item_property_alias`         VARCHAR(32) NOT NULL COMMENT '商品属性别名',
    `sort`                        BIGINT UNSIGNED NOT NULL DEFAULT '0' COMMENT '排序，sort值大的排序靠前',
    `create_by`                   VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`                 BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`                   VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`                 BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `item_category_id` ,`item_property_id`) USING BTREE,
    KEY                           `idx_01` ( `update_time` ) USING BTREE
) COMMENT = '商品分类属性关系表';


DROP TABLE IF EXISTS `t_data_item_category_property_value_relation`;
CREATE TABLE `t_data_item_category_property_value_relation`
(
    `id`                                 VARCHAR(32) NOT NULL COMMENT 'ID',
    `item_category_property_relation_id` VARCHAR(32) NOT NULL COMMENT '商品分类属性关系ID',
    `item_property_value_id`             VARCHAR(32) NOT NULL COMMENT '商品属性值ID',
    `item_property_value_alias`          VARCHAR(32) NOT NULL COMMENT '商品属性值别名',
    `sort`                               BIGINT UNSIGNED NOT NULL DEFAULT '0' COMMENT '排序，sort值大的排序靠前',
    `create_by`                          VARCHAR(32) NOT NULL COMMENT '创建人',
    `create_time`                        BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`                          VARCHAR(32) NOT NULL COMMENT '修改人',
    `update_time`                        BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `item_category_property_relation_id` ,`item_property_value_id`) USING BTREE,
    KEY                                  `idx_01` ( `update_time` ) USING BTREE
) COMMENT = '商品分类属性与属性值关系表';

DROP TABLE IF EXISTS `t_data_brand`;
CREATE TABLE `t_data_brand`
(
    `id`               VARCHAR(32)   NOT NULL COMMENT 'ID',
    `code`             VARCHAR(16)   NOT NULL COMMENT '编码',
    `name`             VARCHAR(64)   NOT NULL DEFAULT '' COMMENT '名称',
    `status`           VARCHAR(8)    NOT NULL default 'DISABLE' COMMENT '状态',
    `logo_material_id` VARCHAR(32)   NOT NULL DEFAULT '' COMMENT 'logo素材ID',
    `description`      VARCHAR(2048) NOT NULL default '' COMMENT '描述',
    `create_by`        VARCHAR(32)   NOT NULL COMMENT '创建人',
    `create_time`      BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
    `update_by`        VARCHAR(32)   NOT NULL COMMENT '修改人',
    `update_time`      BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_01` ( `code` ) USING BTREE,
    KEY                `idx_01` ( `create_time` ) USING BTREE,
    KEY                `idx_02` ( `update_time` ) USING BTREE
) COMMENT = '品牌信息表';
