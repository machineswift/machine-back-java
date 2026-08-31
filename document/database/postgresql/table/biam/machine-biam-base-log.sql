DROP TABLE IF EXISTS t_biam_user_login_log;
CREATE TABLE t_biam_user_login_log
(
    -- 基础标识
    id                    VARCHAR(32)   NOT NULL,

    -- 用户信息
    user_id               VARCHAR(32)   NOT NULL,
    username              VARCHAR(32)   NOT NULL,
    real_name             VARCHAR(64)   NOT NULL DEFAULT '',
    phone                 VARCHAR(16)   NOT NULL DEFAULT '',

    -- 认证信息
    auth_action           VARCHAR(32)   NOT NULL,
    auth_method           VARCHAR(32)   NOT NULL,
    auth_result           VARCHAR(8)    NOT NULL,

    -- 访问环境
    ip_address            VARCHAR(64)   NOT NULL,
    platform              VARCHAR(32)   NOT NULL DEFAULT '',
    user_agent            VARCHAR(4096) NOT NULL DEFAULT '',

    -- Token 信息
    access_token_id       VARCHAR(32)   NOT NULL DEFAULT '',
    access_token_expire   BIGINT        NOT NULL DEFAULT 0,
    refresh_token_id      VARCHAR(32)   NOT NULL DEFAULT '',
    refresh_token_expire  BIGINT        NOT NULL DEFAULT 0,

    -- 结果信息
    fail_reason           TEXT,
    description           TEXT,

    -- 审计字段
    create_by             VARCHAR(32)   NOT NULL,
    create_time           BIGINT        NOT NULL,
    update_by             VARCHAR(32)   NOT NULL,
    update_time           BIGINT        NOT NULL,

    CONSTRAINT pk_t_biam_user_login_log PRIMARY KEY (id)
);

CREATE INDEX idx_t_biam_user_login_log_01 ON t_biam_user_login_log (user_id, access_token_id);
CREATE INDEX idx_t_biam_user_login_log_02 ON t_biam_user_login_log (user_id, refresh_token_id);
CREATE INDEX idx_t_biam_user_login_log_03 ON t_biam_user_login_log (username);
CREATE INDEX idx_t_biam_user_login_log_04 ON t_biam_user_login_log (phone);
CREATE INDEX idx_t_biam_user_login_log_05 ON t_biam_user_login_log (ip_address);
CREATE INDEX idx_t_biam_user_login_log_06 ON t_biam_user_login_log (access_token_id);
CREATE INDEX idx_t_biam_user_login_log_07 ON t_biam_user_login_log (refresh_token_id);
CREATE INDEX idx_t_biam_user_login_log_08 ON t_biam_user_login_log (create_time);

COMMENT ON TABLE t_biam_user_login_log IS '登录日志表';
COMMENT ON COLUMN t_biam_user_login_log.id IS 'ID';
COMMENT ON COLUMN t_biam_user_login_log.user_id IS '用户id';
COMMENT ON COLUMN t_biam_user_login_log.username IS '用户名（系统账号）';
COMMENT ON COLUMN t_biam_user_login_log.real_name IS '姓名';
COMMENT ON COLUMN t_biam_user_login_log.phone IS '手机号';
COMMENT ON COLUMN t_biam_user_login_log.auth_action IS '操作(AuthActionEnum)';
COMMENT ON COLUMN t_biam_user_login_log.auth_method IS '登录方式(AuthMethodEnum)';
COMMENT ON COLUMN t_biam_user_login_log.auth_result IS '结果(AuthResultEnum)';
COMMENT ON COLUMN t_biam_user_login_log.ip_address IS 'IP 地址';
COMMENT ON COLUMN t_biam_user_login_log.platform IS '登录平台';
COMMENT ON COLUMN t_biam_user_login_log.user_agent IS '浏览器和操作系统等信息';
COMMENT ON COLUMN t_biam_user_login_log.access_token_id IS 'access token id';
COMMENT ON COLUMN t_biam_user_login_log.access_token_expire IS 'auth token 过期时间';
COMMENT ON COLUMN t_biam_user_login_log.refresh_token_id IS 'refresh token id';
COMMENT ON COLUMN t_biam_user_login_log.refresh_token_expire IS 'refresh token 过期时间';
COMMENT ON COLUMN t_biam_user_login_log.fail_reason IS '失败原因';
COMMENT ON COLUMN t_biam_user_login_log.description IS '描述';
COMMENT ON COLUMN t_biam_user_login_log.create_by IS '创建人';
COMMENT ON COLUMN t_biam_user_login_log.create_time IS '创建时间';
COMMENT ON COLUMN t_biam_user_login_log.update_by IS '修改人';
COMMENT ON COLUMN t_biam_user_login_log.update_time IS '更新时间';


DROP TABLE IF EXISTS t_biam_user_access_log;
CREATE TABLE t_biam_user_access_log
(
    -- 基础标识
    id                    VARCHAR(32)   NOT NULL,

    -- 操作主体
    user_id               VARCHAR(32)   NOT NULL,
    username              VARCHAR(64)   NOT NULL,

    -- 操作信息
    operate_source        VARCHAR(32)   NOT NULL,
    module                VARCHAR(32)   NOT NULL,
    module_entity         VARCHAR(64)   NOT NULL,
    operate_type          VARCHAR(32)   NOT NULL,
    operate_name          VARCHAR(128)  NOT NULL,

    -- 请求链路
    trace_id              VARCHAR(64)   NOT NULL DEFAULT '',
    client_ip             VARCHAR(64)   NOT NULL,
    platform              VARCHAR(32)   NOT NULL DEFAULT '',
    device_id             VARCHAR(64)   NOT NULL DEFAULT '',
    user_agent            VARCHAR(4096)  NOT NULL DEFAULT '',

    -- HTTP 请求
    http_method           VARCHAR(16)   NOT NULL DEFAULT '',
    request_path          VARCHAR(256)  NOT NULL,
    query_string          VARCHAR(4096) NOT NULL DEFAULT '',
    request_body          TEXT,

    -- HTTP 响应
    http_status           INT           NOT NULL DEFAULT 200,
    response_body         TEXT,

    -- 操作结果
    action_status         VARCHAR(8)    NOT NULL DEFAULT 'SUCCESS',
    error_code            VARCHAR(32)   NOT NULL DEFAULT '',
    error_message         VARCHAR(4096) NOT NULL DEFAULT '',
    exception_stack       TEXT,

    -- 性能信息
    cost_time             BIGINT        NOT NULL DEFAULT 0,

    -- 扩展信息
    extend_info           JSONB,

    -- 审计字段
    create_by             VARCHAR(32)   NOT NULL,
    create_time           BIGINT        NOT NULL,
    update_by             VARCHAR(32)   NOT NULL,
    update_time           BIGINT        NOT NULL,
    CONSTRAINT pk_t_biam_user_access_log PRIMARY KEY (id)
);

CREATE INDEX idx_t_biam_user_access_log_01 ON t_biam_user_access_log (user_id);
CREATE INDEX idx_t_biam_user_access_log_02 ON t_biam_user_access_log (module);
CREATE INDEX idx_t_biam_user_access_log_03 ON t_biam_user_access_log (operate_type);
CREATE INDEX idx_t_biam_user_access_log_04 ON t_biam_user_access_log (trace_id);
CREATE INDEX idx_t_biam_user_access_log_05 ON t_biam_user_access_log (client_ip);
CREATE INDEX idx_t_biam_user_access_log_06 ON t_biam_user_access_log (request_path);
CREATE INDEX idx_t_biam_user_access_log_07 ON t_biam_user_access_log (http_status);
CREATE INDEX idx_t_biam_user_access_log_08 ON t_biam_user_access_log (create_time);
CREATE INDEX idx_t_biam_user_access_log_09 ON t_biam_user_access_log (user_id, module, create_time);
CREATE INDEX idx_t_biam_user_access_log_10 ON t_biam_user_access_log (module, operate_type, create_time);

CREATE INDEX idx_t_biam_user_access_log_fail ON t_biam_user_access_log (create_time) WHERE action_status = 'FAIL';

COMMENT ON TABLE t_biam_user_access_log IS '用户访问日志表';

COMMENT ON COLUMN t_biam_user_access_log.id IS 'ID';
COMMENT ON COLUMN t_biam_user_access_log.user_id IS '用户ID';
COMMENT ON COLUMN t_biam_user_access_log.username IS '用户名';
COMMENT ON COLUMN t_biam_user_access_log.operate_source IS '操作来源，对应 OperateSourceEnum';
COMMENT ON COLUMN t_biam_user_access_log.module IS '操作模块，对应 ModuleEnum';
COMMENT ON COLUMN t_biam_user_access_log.module_entity IS '操作模块实体，对应 ModuleEntityEnum';
COMMENT ON COLUMN t_biam_user_access_log.operate_type IS '操作分类(增/删/改/查/导出等)，对应 ActionTypeEnum';
COMMENT ON COLUMN t_biam_user_access_log.operate_name IS '操作名称，如：新增用户、修改密码等';
COMMENT ON COLUMN t_biam_user_access_log.trace_id IS '分布式链路追踪ID';
COMMENT ON COLUMN t_biam_user_access_log.client_ip IS '客户端真实IP';
COMMENT ON COLUMN t_biam_user_access_log.platform IS '客户端平台';
COMMENT ON COLUMN t_biam_user_access_log.device_id IS '设备ID（移动端）';
COMMENT ON COLUMN t_biam_user_access_log.user_agent IS 'User-Agent（浏览器/设备信息）';
COMMENT ON COLUMN t_biam_user_access_log.http_method IS 'HTTP方法（GET/POST/PUT/DELETE/PATCH）';
COMMENT ON COLUMN t_biam_user_access_log.request_path IS '请求路径（不含QueryString，便于聚合统计）';
COMMENT ON COLUMN t_biam_user_access_log.query_string IS 'QueryString参数';
COMMENT ON COLUMN t_biam_user_access_log.request_body IS '请求体（敏感字段需脱敏）';
COMMENT ON COLUMN t_biam_user_access_log.http_status IS 'HTTP状态码（200/404/500等）';
COMMENT ON COLUMN t_biam_user_access_log.response_body IS '响应体（敏感字段需脱敏）';
COMMENT ON COLUMN t_biam_user_access_log.action_status IS '业务状态，FAIL:失败 SUCCESS:成功';
COMMENT ON COLUMN t_biam_user_access_log.error_code IS '业务错误码';
COMMENT ON COLUMN t_biam_user_access_log.error_message IS '错误信息';
COMMENT ON COLUMN t_biam_user_access_log.exception_stack IS '异常堆栈（仅500时记录）';
COMMENT ON COLUMN t_biam_user_access_log.cost_time IS '接口总耗时（毫秒）';
COMMENT ON COLUMN t_biam_user_access_log.extend_info IS '扩展信息（JSONB格式）';
COMMENT ON COLUMN t_biam_user_access_log.create_by IS '创建人';
COMMENT ON COLUMN t_biam_user_access_log.create_time IS '记录创建时间（入库时间戳）';
COMMENT ON COLUMN t_biam_user_access_log.update_by IS '修改人';
COMMENT ON COLUMN t_biam_user_access_log.update_time IS '修改时间';


DROP TABLE IF EXISTS t_biam_operation_log;
CREATE TABLE t_biam_operation_log
(
    -- 基础标识
    id                    VARCHAR(32)   NOT NULL,

    -- 操作主体
    user_id               VARCHAR(32)   NOT NULL,
    username              VARCHAR(64)   NOT NULL,

    -- 操作信息
    operate_source        VARCHAR(32)   NOT NULL,
    module                VARCHAR(32)   NOT NULL,
    module_entity         VARCHAR(64)   NOT NULL,
    module_entity_id      VARCHAR(32)   NOT NULL,
    operate_type          VARCHAR(32)   NOT NULL,
    operate_name          VARCHAR(128)  NOT NULL,

    -- 请求链路
    trace_id              VARCHAR(64)   NOT NULL DEFAULT '',
    client_ip             VARCHAR(64)   NOT NULL,
    platform              VARCHAR(32)   NOT NULL DEFAULT '',
    device_id             VARCHAR(64)   NOT NULL DEFAULT '',
    user_agent            VARCHAR(512)  NOT NULL DEFAULT '',

    -- HTTP 请求
    http_method           VARCHAR(16)   NOT NULL DEFAULT '',
    request_path          VARCHAR(256)  NOT NULL,
    query_string          VARCHAR(4096) NOT NULL DEFAULT '',
    request_body          TEXT,

    -- HTTP 响应
    http_status           INT           NOT NULL DEFAULT 200,
    response_body         TEXT,

    -- 业务对象
    content               TEXT,
    diff_json             TEXT,

    -- 操作结果
    action_status         VARCHAR(8)    NOT NULL DEFAULT 'SUCCESS',
    error_code            VARCHAR(32)   NOT NULL DEFAULT '',
    error_message         VARCHAR(4096) NOT NULL DEFAULT '',
    exception_stack       TEXT,

    -- 性能信息
    cost_time             BIGINT        NOT NULL DEFAULT 0,

    -- 扩展信息
    extend_info           JSONB,

    -- 审计字段
    create_by             VARCHAR(32)   NOT NULL,
    create_time           BIGINT        NOT NULL,
    update_by             VARCHAR(32)   NOT NULL,
    update_time           BIGINT        NOT NULL,

    CONSTRAINT pk_t_biam_operation_log PRIMARY KEY (id)
);

CREATE INDEX idx_t_biam_operation_log_01 ON t_biam_operation_log (user_id);
CREATE INDEX idx_t_biam_operation_log_02 ON t_biam_operation_log (module);
CREATE INDEX idx_t_biam_operation_log_03 ON t_biam_operation_log (module_entity, module_entity_id);
CREATE INDEX idx_t_biam_operation_log_04 ON t_biam_operation_log (operate_type);
CREATE INDEX idx_t_biam_operation_log_05 ON t_biam_operation_log (action_status);
CREATE INDEX idx_t_biam_operation_log_06 ON t_biam_operation_log (trace_id);
CREATE INDEX idx_t_biam_operation_log_07 ON t_biam_operation_log (create_time);

COMMENT ON TABLE t_biam_operation_log IS '操作日志表（平台级通用业务操作日志）';
COMMENT ON COLUMN t_biam_operation_log.id IS 'ID';
COMMENT ON COLUMN t_biam_operation_log.user_id IS '操作人用户ID';
COMMENT ON COLUMN t_biam_operation_log.username IS '操作人用户名';
COMMENT ON COLUMN t_biam_operation_log.operate_source IS '操作来源，对应 OperateSourceEnum';
COMMENT ON COLUMN t_biam_operation_log.module IS '操作模块，对应 ModuleEnum';
COMMENT ON COLUMN t_biam_operation_log.module_entity IS '操作模块实体，对应 ModuleEntityEnum';
COMMENT ON COLUMN t_biam_operation_log.module_entity_id IS '操作模块实体主键ID';
COMMENT ON COLUMN t_biam_operation_log.operate_type IS '操作分类(增/删/改/查/授权等)，对应 ActionTypeEnum';
COMMENT ON COLUMN t_biam_operation_log.operate_name IS '操作名称，如：修改用户权限';
COMMENT ON COLUMN t_biam_operation_log.trace_id IS '分布式链路追踪ID';
COMMENT ON COLUMN t_biam_operation_log.client_ip IS '客户端真实IP';
COMMENT ON COLUMN t_biam_operation_log.platform IS '客户端平台';
COMMENT ON COLUMN t_biam_operation_log.device_id IS '设备ID（移动端）';
COMMENT ON COLUMN t_biam_operation_log.user_agent IS 'User-Agent（浏览器/客户端标识）';
COMMENT ON COLUMN t_biam_operation_log.http_method IS 'HTTP方法（GET/POST/PUT/DELETE/PATCH）';
COMMENT ON COLUMN t_biam_operation_log.request_path IS '请求路径';
COMMENT ON COLUMN t_biam_operation_log.query_string IS 'QueryString参数';
COMMENT ON COLUMN t_biam_operation_log.request_body IS '请求体快照';
COMMENT ON COLUMN t_biam_operation_log.http_status IS 'HTTP状态码（200/404/500等）';
COMMENT ON COLUMN t_biam_operation_log.response_body IS '响应体快照';
COMMENT ON COLUMN t_biam_operation_log.content IS '操作内容描述';
COMMENT ON COLUMN t_biam_operation_log.diff_json IS '变更diff（JSON文本），集合字段为 added/removed';
COMMENT ON COLUMN t_biam_operation_log.action_status IS '业务状态，FAIL:失败 SUCCESS:成功';
COMMENT ON COLUMN t_biam_operation_log.error_code IS '业务错误码';
COMMENT ON COLUMN t_biam_operation_log.error_message IS '错误信息';
COMMENT ON COLUMN t_biam_operation_log.exception_stack IS '异常堆栈';
COMMENT ON COLUMN t_biam_operation_log.cost_time IS '接口总耗时（毫秒）';
COMMENT ON COLUMN t_biam_operation_log.extend_info IS '扩展信息（JSONB格式）';
COMMENT ON COLUMN t_biam_operation_log.create_by IS '创建人';
COMMENT ON COLUMN t_biam_operation_log.create_time IS '记录创建时间（入库时间戳）';
COMMENT ON COLUMN t_biam_operation_log.update_by IS '修改人';
COMMENT ON COLUMN t_biam_operation_log.update_time IS '修改时间';