DROP TABLE IF EXISTS t_ai_resource_provider;
CREATE TABLE t_ai_resource_provider
(
    id          VARCHAR(32)   NOT NULL,
    status      VARCHAR(8)    NOT NULL DEFAULT 'DISABLE',
    provider    VARCHAR(32)   NOT NULL,
    base_url    VARCHAR(256)  NOT NULL,
    api_key     VARCHAR(2048)  NOT NULL,
    description VARCHAR(2048) NOT NULL DEFAULT '',
    create_by   VARCHAR(32)   NOT NULL,
    create_time BIGINT        NOT NULL,
    update_by   VARCHAR(32)   NOT NULL,
    update_time BIGINT        NOT NULL,
    CONSTRAINT pk_t_ai_resource_provider PRIMARY KEY (id),
    CONSTRAINT uk_t_ai_resource_provider UNIQUE (provider)
);

-- 索引
CREATE INDEX idx_t_ai_resource_provider_01 ON t_ai_resource_provider (create_time);

-- 字段注释
COMMENT ON TABLE t_ai_resource_provider IS '模型厂商表';
COMMENT ON COLUMN t_ai_resource_provider.id IS '主键ID';
COMMENT ON COLUMN t_ai_resource_provider.status IS '状态';
COMMENT ON COLUMN t_ai_resource_provider.provider IS '厂商标识(deepseek, ollama)';
COMMENT ON COLUMN t_ai_resource_provider.base_url IS 'API基础地址';
COMMENT ON COLUMN t_ai_resource_provider.api_key IS 'API密钥';
COMMENT ON COLUMN t_ai_resource_provider.description IS '描述';
COMMENT ON COLUMN t_ai_resource_provider.create_by IS '创建人ID';
COMMENT ON COLUMN t_ai_resource_provider.create_time IS '创建时间';
COMMENT ON COLUMN t_ai_resource_provider.update_by IS '修改人ID';
COMMENT ON COLUMN t_ai_resource_provider.update_time IS '修改时间';



DROP TABLE IF EXISTS t_ai_resource_model;
CREATE TABLE t_ai_resource_model
(
    id          VARCHAR(32)   NOT NULL,
    provider_id VARCHAR(32)   NOT NULL,
    status      VARCHAR(8)    NOT NULL DEFAULT 'DISABLE',
    name        VARCHAR(64)   NOT NULL,
    code        VARCHAR(64)   NOT NULL,
    features    JSONB,
    description VARCHAR(2048) NOT NULL DEFAULT '',
    create_by   VARCHAR(32)   NOT NULL,
    create_time BIGINT        NOT NULL,
    update_by   VARCHAR(32)   NOT NULL,
    update_time BIGINT        NOT NULL,
    CONSTRAINT pk_t_ai_resource_model PRIMARY KEY (id),
    CONSTRAINT uk_t_ai_resource_model UNIQUE (provider_id, code)
);

-- 索引
CREATE INDEX idx_t_ai_resource_model_01 ON t_ai_resource_model (code);
CREATE INDEX idx_t_ai_resource_model_02 ON t_ai_resource_model USING GIN (features);
CREATE INDEX idx_t_ai_resource_model_03 ON t_ai_resource_model (create_time);

ALTER TABLE t_ai_resource_model
    ADD CONSTRAINT fk_t_ai_resource_model_provider_id
        FOREIGN KEY (provider_id) REFERENCES t_ai_resource_provider(id) ON DELETE CASCADE;

-- 字段注释
COMMENT ON TABLE t_ai_resource_model IS '模型配置表';

COMMENT ON COLUMN t_ai_resource_model.id IS '主键ID';
COMMENT ON COLUMN t_ai_resource_model.provider_id IS '厂商id';
COMMENT ON COLUMN t_ai_resource_model.status IS '状态';
COMMENT ON COLUMN t_ai_resource_model.name IS '模型名称';
COMMENT ON COLUMN t_ai_resource_model.code IS '模型编码(如deepseek-v4-flash,deepseek-v4-pro)';
COMMENT ON COLUMN t_ai_resource_model.features IS '扩展特性JSON';
COMMENT ON COLUMN t_ai_resource_model.description IS '描述';
COMMENT ON COLUMN t_ai_resource_model.create_by IS '创建人ID';
COMMENT ON COLUMN t_ai_resource_model.create_time IS '创建时间';
COMMENT ON COLUMN t_ai_resource_model.update_by IS '修改人ID';
COMMENT ON COLUMN t_ai_resource_model.update_time IS '修改时间';
