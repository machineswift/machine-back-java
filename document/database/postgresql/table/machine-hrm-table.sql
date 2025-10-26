DROP TABLE IF EXISTS t_hrm_department;
CREATE TABLE t_hrm_department
(
    id          VARCHAR(32) NOT NULL,
    parent_id   VARCHAR(32) NOT NULL,
    name        VARCHAR(32) NOT NULL,
    code        VARCHAR(16) NOT NULL,
    status      VARCHAR(8) NOT NULL DEFAULT 'DISABLE',
    description VARCHAR(2048) NOT NULL DEFAULT '',
    sort        BIGINT NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_hrm_department PRIMARY KEY (id),
    CONSTRAINT uk_t_hrm_department_01 UNIQUE (parent_id, id)
);

CREATE INDEX idx_t_hrm_department_01 ON t_hrm_department (code);
CREATE INDEX idx_t_hrm_department_02 ON t_hrm_department (create_time);
CREATE INDEX idx_t_hrm_department_03 ON t_hrm_department (update_time);

COMMENT ON TABLE t_hrm_department IS '部门表';
COMMENT ON COLUMN t_hrm_department.id IS 'ID';
COMMENT ON COLUMN t_hrm_department.parent_id IS '父ID';
COMMENT ON COLUMN t_hrm_department.name IS '名称';
COMMENT ON COLUMN t_hrm_department.code IS '编码';
COMMENT ON COLUMN t_hrm_department.status IS '状态,（BeiSenDepartmentStatusEnum）';
COMMENT ON COLUMN t_hrm_department.description IS '描述';
COMMENT ON COLUMN t_hrm_department.sort IS '排序，大的在前';
COMMENT ON COLUMN t_hrm_department.create_by IS '创建人';
COMMENT ON COLUMN t_hrm_department.create_time IS '创建时间';
COMMENT ON COLUMN t_hrm_department.update_by IS '修改人';
COMMENT ON COLUMN t_hrm_department.update_time IS '更新时间';

DROP TABLE IF EXISTS t_hrm_department_expansion;
CREATE TABLE t_hrm_department_expansion
(
    id             VARCHAR(32) NOT NULL,
    department_id  VARCHAR(32) NOT NULL,
    person_in_charge VARCHAR(32),
    establish_date BIGINT,
    start_date     BIGINT,
    stop_time      BIGINT,
    create_by      VARCHAR(32) NOT NULL,
    create_time    BIGINT NOT NULL,
    update_by      VARCHAR(32) NOT NULL,
    update_time    BIGINT NOT NULL,
    CONSTRAINT pk_t_hrm_department_expansion PRIMARY KEY (id)
);

CREATE INDEX idx_t_hrm_department_expansion_01 ON t_hrm_department_expansion (department_id);
CREATE INDEX idx_t_hrm_department_expansion_02 ON t_hrm_department_expansion (create_time);
CREATE INDEX idx_t_hrm_department_expansion_03 ON t_hrm_department_expansion (update_time);

COMMENT ON TABLE t_hrm_department_expansion IS '部门信息扩展表';
COMMENT ON COLUMN t_hrm_department_expansion.id IS 'ID';
COMMENT ON COLUMN t_hrm_department_expansion.department_id IS '部门ID';
COMMENT ON COLUMN t_hrm_department_expansion.person_in_charge IS '部门负责人ID';
COMMENT ON COLUMN t_hrm_department_expansion.establish_date IS '设立日期';
COMMENT ON COLUMN t_hrm_department_expansion.start_date IS '生效日期';
COMMENT ON COLUMN t_hrm_department_expansion.stop_time IS '失效日期';
COMMENT ON COLUMN t_hrm_department_expansion.create_by IS '创建人';
COMMENT ON COLUMN t_hrm_department_expansion.create_time IS '创建时间';
COMMENT ON COLUMN t_hrm_department_expansion.update_by IS '修改人';
COMMENT ON COLUMN t_hrm_department_expansion.update_time IS '更新时间';

DROP TABLE IF EXISTS t_hrm_job_post;
CREATE TABLE t_hrm_job_post
(
    id          VARCHAR(32) NOT NULL,
    name        VARCHAR(64) NOT NULL DEFAULT '',
    code        VARCHAR(64) NOT NULL DEFAULT '',
    status      VARCHAR(8) NOT NULL DEFAULT 'DISABLE',
    description VARCHAR(2048) NOT NULL DEFAULT '',
    sort        BIGINT NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_hrm_job_post PRIMARY KEY (id)
);

CREATE INDEX idx_t_hrm_job_post_01 ON t_hrm_job_post (code);

COMMENT ON TABLE t_hrm_job_post IS '职务表';
COMMENT ON COLUMN t_hrm_job_post.id IS 'ID';
COMMENT ON COLUMN t_hrm_job_post.name IS '名称';
COMMENT ON COLUMN t_hrm_job_post.code IS '编码';
COMMENT ON COLUMN t_hrm_job_post.status IS '状态（BeiSenJobPostStatusEnum）';
COMMENT ON COLUMN t_hrm_job_post.description IS '描述';
COMMENT ON COLUMN t_hrm_job_post.sort IS '排序';
COMMENT ON COLUMN t_hrm_job_post.create_by IS '创建人';
COMMENT ON COLUMN t_hrm_job_post.create_time IS '创建时间';
COMMENT ON COLUMN t_hrm_job_post.update_by IS '修改人';
COMMENT ON COLUMN t_hrm_job_post.update_time IS '更新时间';

DROP TABLE IF EXISTS t_hrm_job_post_role_relation;
CREATE TABLE t_hrm_job_post_role_relation
(
    id          VARCHAR(32) NOT NULL,
    job_post_id VARCHAR(32) NOT NULL,
    role_id     VARCHAR(32) NOT NULL,
    sort        BIGINT NOT NULL DEFAULT 0,
    create_by   VARCHAR(32) NOT NULL,
    create_time BIGINT NOT NULL,
    update_by   VARCHAR(32) NOT NULL,
    update_time BIGINT NOT NULL,
    CONSTRAINT pk_t_hrm_job_post_role_relation PRIMARY KEY (id),
    CONSTRAINT uk_t_hrm_job_post_role_relation_01 UNIQUE (job_post_id, role_id)
);

CREATE INDEX idx_t_hrm_job_post_role_relation_01 ON t_hrm_job_post_role_relation (role_id, job_post_id);

COMMENT ON TABLE t_hrm_job_post_role_relation IS '角色职务关系表';
COMMENT ON COLUMN t_hrm_job_post_role_relation.id IS 'ID';
COMMENT ON COLUMN t_hrm_job_post_role_relation.job_post_id IS '职务ID';
COMMENT ON COLUMN t_hrm_job_post_role_relation.role_id IS '角色ID';
COMMENT ON COLUMN t_hrm_job_post_role_relation.sort IS '排序';
COMMENT ON COLUMN t_hrm_job_post_role_relation.create_by IS '创建人';
COMMENT ON COLUMN t_hrm_job_post_role_relation.create_time IS '创建时间';
COMMENT ON COLUMN t_hrm_job_post_role_relation.update_by IS '修改人';
COMMENT ON COLUMN t_hrm_job_post_role_relation.update_time IS '更新时间';

DROP TABLE IF EXISTS t_hrm_employee;
CREATE TABLE t_hrm_employee
(
    id            VARCHAR(32) NOT NULL,
    user_id       VARCHAR(32) NOT NULL,
    user_name     VARCHAR(32) NOT NULL,
    status        VARCHAR(16) NOT NULL DEFAULT 'DISABLE',
    code          VARCHAR(32) NOT NULL DEFAULT '',
    phone         VARCHAR(16) NOT NULL DEFAULT '',
    name          VARCHAR(64) NOT NULL DEFAULT '',
    gender        VARCHAR(16) NOT NULL DEFAULT 'UNDEFINED',
    email         VARCHAR(64) NOT NULL DEFAULT '',
    leader_id     VARCHAR(32) NOT NULL DEFAULT '',
    job_post_id   VARCHAR(32) NOT NULL DEFAULT '',
    department_id VARCHAR(32) NOT NULL DEFAULT '',
    is_charge     VARCHAR(32) NOT NULL DEFAULT '0',
    description   VARCHAR(2048) NOT NULL DEFAULT '',
    create_by     VARCHAR(32) NOT NULL,
    create_time   BIGINT NOT NULL,
    update_by     VARCHAR(32) NOT NULL,
    update_time   BIGINT NOT NULL,
    CONSTRAINT pk_t_hrm_employee PRIMARY KEY (id),
    CONSTRAINT uk_t_hrm_employee_01 UNIQUE (user_id),
    CONSTRAINT uk_t_hrm_employee_02 UNIQUE (user_name),
    CONSTRAINT uk_t_hrm_employee_03 UNIQUE (code)
);

CREATE INDEX idx_t_hrm_employee_01 ON t_hrm_employee (phone);
CREATE INDEX idx_t_hrm_employee_02 ON t_hrm_employee (leader_id);
CREATE INDEX idx_t_hrm_employee_03 ON t_hrm_employee (job_post_id);
CREATE INDEX idx_t_hrm_employee_04 ON t_hrm_employee (department_id);
CREATE INDEX idx_t_hrm_employee_05 ON t_hrm_employee (create_time);
CREATE INDEX idx_t_hrm_employee_06 ON t_hrm_employee (update_time);

COMMENT ON TABLE t_hrm_employee IS '员工表';
COMMENT ON COLUMN t_hrm_employee.id IS 'ID';
COMMENT ON COLUMN t_hrm_employee.user_id IS '用户id';
COMMENT ON COLUMN t_hrm_employee.user_name IS '用户名（系统账号）';
COMMENT ON COLUMN t_hrm_employee.status IS '状态,（BeiSenEmployeeStatusEnum）';
COMMENT ON COLUMN t_hrm_employee.code IS '编码（工号）';
COMMENT ON COLUMN t_hrm_employee.phone IS '手机号';
COMMENT ON COLUMN t_hrm_employee.name IS '姓名';
COMMENT ON COLUMN t_hrm_employee.gender IS '性别';
COMMENT ON COLUMN t_hrm_employee.email IS '邮箱';
COMMENT ON COLUMN t_hrm_employee.leader_id IS '上级id';
COMMENT ON COLUMN t_hrm_employee.job_post_id IS '职务ID';
COMMENT ON COLUMN t_hrm_employee.department_id IS '部门ID';
COMMENT ON COLUMN t_hrm_employee.is_charge IS '是否是部门负责人';
COMMENT ON COLUMN t_hrm_employee.description IS '描述';
COMMENT ON COLUMN t_hrm_employee.create_by IS '创建人';
COMMENT ON COLUMN t_hrm_employee.create_time IS '创建时间';
COMMENT ON COLUMN t_hrm_employee.update_by IS '修改人';
COMMENT ON COLUMN t_hrm_employee.update_time IS '更新时间';