DELETE FROM `machine_infra`.`xxl_job_info`;

INSERT INTO `machine_infra`.`xxl_job_info`(`id`, `job_group`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `schedule_type`, `schedule_conf`, `misfire_strategy`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`)
VALUES (1, 1, '测试任务1', '2018-11-03 22:21:31', '2018-11-03 22:21:31', 'XXL', '', 'CRON', '0 0 0 * * ? *', 'DO_NOTHING', 'FIRST', 'demoJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2018-11-03 22:21:31', '');


INSERT INTO `machine_infra`.`xxl_job_info` (`id`, `job_group`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `schedule_type`, `schedule_conf`, `misfire_strategy`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`, `trigger_status`, `trigger_last_time`, `trigger_next_time`)
 VALUES (90002, 1, '可靠消息', '2024-01-05 11:21:31', '2024-11-21 14:10:02', 'admin', '', 'CRON', '*/10 * * * * ?', 'DO_NOTHING', 'LEAST_RECENTLY_USED', 'ReliableMessageJobHandler', '', 'DISCARD_LATER', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-01-05 11:21:31', '', 0, 0, 0);

INSERT INTO `machine_infra`.`xxl_job_info` (`id`, `job_group`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `schedule_type`, `schedule_conf`, `misfire_strategy`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`, `trigger_status`, `trigger_last_time`, `trigger_next_time`)
VALUES (90004, 1, '下载中心', '2024-11-22 21:08:52', '2024-11-25 09:28:35', 'machine', '', 'CRON', '0/15 1/1 * * * ?', 'DO_NOTHING', 'LEAST_FREQUENTLY_USED', 'DownloadFileTaskScheduledJobHandler', '', 'DISCARD_LATER', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2024-11-22 21:08:52', '', 0, 0, 0);


INSERT INTO `machine_infra`.`xxl_job_info` (`id`, `job_group`, `job_desc`, `add_time`, `update_time`, `author`, `alarm_email`, `schedule_type`, `schedule_conf`, `misfire_strategy`, `executor_route_strategy`, `executor_handler`, `executor_param`, `executor_block_strategy`, `executor_timeout`, `executor_fail_retry_count`, `glue_type`, `glue_source`, `glue_remark`, `glue_updatetime`, `child_jobid`, `trigger_status`, `trigger_last_time`, `trigger_next_time`)
VALUES (90021, 1, 'Webhook抛出用户创建事件', '2024-11-20 10:07:41', '2024-11-20 10:12:39', 'machine', '', 'CRON', '0 0/0 0/3 * * ?', 'DO_NOTHING', 'LEAST_FREQUENTLY_USED', 'WebhookUserJobHandler', '', 'DISCARD_LATER', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2024-11-20 10:07:41', '', 0, 0, 0);

