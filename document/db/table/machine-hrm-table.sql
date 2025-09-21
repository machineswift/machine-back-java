DROP TABLE IF EXISTS `t_hrm_department`;
CREATE TABLE `t_hrm_department`
(
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `parent_id` varchar(32) NOT NULL COMMENT '父ID',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `code` varchar(16) NOT NULL COMMENT '编码',
  `status` varchar(8)  NOT NULL DEFAULT 'DISABLE' COMMENT '状态,（BeiSenDepartmentStatusEnum）',
  `description` varchar(2048) NOT NULL DEFAULT '' COMMENT '描述',
  `sort` bigint unsigned NOT NULL DEFAULT '0' COMMENT '排序，大的在前',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_time` bigint unsigned NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_time` bigint unsigned NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_01` (`parent_id`,`id`) USING BTREE,
  KEY `idx_01` (`code`) USING BTREE,
  KEY `idx_02` (`create_time`) USING BTREE,
  KEY `idx_03` (`update_time`) USING BTREE
)  COMMENT='部门表';

DROP TABLE IF EXISTS `t_hrm_department_expansion`;
CREATE TABLE `t_hrm_department_expansion`
(
  `id` varchar(32) NOT NULL COMMENT 'ID',
  `department_id`  varchar(32) NOT NULL COMMENT '部门ID',
  `person_in_charge` varchar(32) DEFAULT NULL COMMENT '部门负责人ID',
  `establish_date` bigint DEFAULT NULL COMMENT '设立日期',
  `start_date`  bigint DEFAULT NULL COMMENT '生效日期',
  `stop_time` bigint DEFAULT NULL COMMENT '失效日期',
  `create_by` varchar(32) NOT NULL COMMENT '创建人',
  `create_time` bigint unsigned NOT NULL COMMENT '创建时间',
  `update_by` varchar(32) NOT NULL COMMENT '修改人',
  `update_time` bigint unsigned NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_01` (`department_id`) USING BTREE,
  KEY `idx_02` (`create_time`) USING BTREE,
  KEY `idx_03` (`update_time`) USING BTREE
)  COMMENT='部门信息扩展表';

DROP TABLE IF EXISTS `t_hrm_job_post`;
CREATE TABLE `t_hrm_job_post` (
	`id` VARCHAR ( 32 ) NOT NULL COMMENT 'ID',
	`name` VARCHAR ( 64 ) NOT NULL default '' COMMENT '名称',
    `code` VARCHAR ( 64 ) NOT NULL default '' COMMENT '编码',
    `status` VARCHAR ( 8 ) NOT NULL default 'DISABLE' COMMENT '状态（BeiSenJobPostStatusEnum）',
	`description` VARCHAR ( 2048 ) NOT NULL  default '' COMMENT '描述',
    `sort` BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
	`create_by` VARCHAR ( 32 ) NOT NULL COMMENT '创建人',
	`create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
	`update_by` VARCHAR ( 32 ) NOT NULL COMMENT '修改人',
	`update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
	PRIMARY KEY ( `id` ) USING BTREE,
	KEY `idx_01` ( `code` ) USING BTREE
) COMMENT = '职务表';

DROP TABLE IF EXISTS `t_hrm_job_post_role_relation`;
CREATE TABLE `t_hrm_job_post_role_relation` (
	`id` VARCHAR ( 32 ) NOT NULL COMMENT 'ID',
	`job_post_id` VARCHAR ( 32 ) NOT NULL COMMENT '职务ID',
    `role_id` VARCHAR ( 32 ) NOT NULL COMMENT '角色ID',
    `sort` BIGINT UNSIGNED NOT NULL default 0 COMMENT '排序',
	`create_by` VARCHAR ( 32 ) NOT NULL COMMENT '创建人',
	`create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
	`update_by` VARCHAR ( 32 ) NOT NULL COMMENT '修改人',
	`update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
	PRIMARY KEY ( `id` ) USING BTREE,
	UNIQUE KEY `uk_01` ( `job_post_id`,`role_id` ) USING BTREE,
	KEY `idx_01` ( `role_id`,`job_post_id` ) USING BTREE
) COMMENT = '角色职务关系表';

DROP TABLE IF EXISTS `t_hrm_employee`;
CREATE TABLE `t_hrm_employee` (
	`id` VARCHAR ( 32 ) NOT NULL COMMENT 'ID',
	`user_id` VARCHAR ( 32 ) NOT NULL COMMENT '用户id',
	`user_name` VARCHAR ( 32 ) NOT NULL COMMENT '用户名（系统账号）',
    `status` VARCHAR ( 16 ) NOT NULL default 'DISABLE' COMMENT '状态,（BeiSenEmployeeStatusEnum）',
	`code` VARCHAR ( 32 ) NOT NULL default '' COMMENT '编码（工号）',
	`phone` VARCHAR ( 16 ) NOT NULL default '' COMMENT '手机号',
	`name` VARCHAR ( 64 ) NOT NULL default '' COMMENT '姓名',
	`gender` VARCHAR ( 16 ) NOT NULL default 'UNDEFINED'  COMMENT '性别',
	`email` VARCHAR ( 64 ) NOT NULL DEFAULT '' COMMENT '邮箱',
	`leader_id` VARCHAR ( 32 ) NOT NULL DEFAULT '' COMMENT '上级id',
	`job_post_id` VARCHAR ( 32 ) NOT NULL DEFAULT '' COMMENT '职务ID',
	`department_id` VARCHAR ( 32 ) NOT NULL DEFAULT '' COMMENT '部门ID',
	`is_charge` VARCHAR ( 32 ) NOT NULL DEFAULT '0' COMMENT '是否是部门负责人',
	`description` VARCHAR ( 2048 ) NOT NULL  default '' COMMENT '描述',
	`create_by` VARCHAR ( 32 ) NOT NULL COMMENT '创建人',
	`create_time` BIGINT UNSIGNED NOT NULL COMMENT '创建时间',
	`update_by` VARCHAR ( 32 ) NOT NULL COMMENT '修改人',
	`update_time` BIGINT UNSIGNED NOT NULL COMMENT '更新时间',
	PRIMARY KEY ( `id` ) USING BTREE,
	UNIQUE KEY `uk_01` ( `user_id` ) USING BTREE,
	UNIQUE KEY `uk_02` ( `user_name` ) USING BTREE,
	UNIQUE KEY `uk_03` ( `code` ) USING BTREE,
	KEY `idx_01` ( `phone` ) USING BTREE,
	KEY `idx_02` ( `leader_id` ) USING BTREE,
	KEY `idx_03` ( `job_post_id` ) USING BTREE,
	KEY `idx_04` ( `department_id` ) USING BTREE,
	KEY `idx_05` ( `create_time` ) USING BTREE,
	KEY `idx_06` ( `update_time` ) USING BTREE
) COMMENT = '员工表';
