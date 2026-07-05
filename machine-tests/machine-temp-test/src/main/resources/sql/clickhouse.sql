-- 创建数据库
CREATE DATABASE IF NOT EXISTS tutorial;

--  创建表
CREATE TABLE tutorial.hits_v1
(
    `WatchID` UInt64,
    `JavaEnable` UInt8,
    `Title` String,
    `GoodEvent` Int16,
    `EventTime` DateTime,
    `EventDate` Date,
    `CounterID` UInt32,
    `ClientIP` UInt32,
    `ClientIP6` FixedString(16),
    `RegionID` UInt32,
    `UserID` UInt64,
    `CounterClass` Int8,
    `OS` UInt8,
    `Age` UInt8,
    `Sex` UInt8,
    `ShareURL` String,
    `ShareTitle` String,
    `ParsedParams` Nested(
        Key1 String,
        Key2 String,
        Key3 String,
        Key4 String,
        Key5 String,
        ValueDouble Float64
    ),
    `IslandID` FixedString(16),
    `RequestNum` UInt32,
    `RequestTry` UInt8
)
    ENGINE = MergeTree()
PARTITION BY toYYYYMM(EventDate)
ORDER BY (CounterID, EventDate, intHash32(UserID))
SAMPLE BY intHash32(UserID);

-- 测试数据包
http://datasets.clickhouse.com/hits/tsv/hits_v1.tsv.xz


-- 复制文件到 ClickHouse 允许的目录
sudo cp hits_v1.tsv /var/lib/clickhouse/user_files/

-- 在浏览器执行脚本
INSERT INTO tutorial.hits_v1
SELECT * FROM file('hits_v1.tsv', 'TabSeparatedWithNames');


-- 创建表结构（基于hits_v1的数据）
CREATE TABLE tutorial.hits_v2
(
    WatchID    UInt64,
    UserID     UInt64,
    EventTime  DateTime,
    OS         UInt8,
    RegionID   UInt32,
    RequestNum UInt32,
    EventDate  Date
) ENGINE = MergeTree
PARTITION BY EventDate
ORDER BY (WatchID, UserID, EventTime)
AS
SELECT
    WatchID,
    UserID,
    EventTime,
    OS,
    RegionID,
    RequestNum,
    EventDate
FROM tutorial.hits_v1;

-- 查看创建结果
SHOW CREATE TABLE tutorial.hits_v2;







