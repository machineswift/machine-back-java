package com.machine.starter.mybatis.extension;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
 * P6spy SQL 打印策略
 */
public class CustomerP6SpyLogger implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId,
                                String now,
                                long elapsed,
                                String category,
                                String prepared,
                                String sql,
                                String url) {
        if (StringUtils.isBlank(sql)) {
            return String.format("Consume Time: %d ms | %s | category: %s",
                    elapsed, now, category);
        } else {
            return String.format("Consume Time: %d ms | %s | SQL: %s",
                    elapsed, now, sql.replaceAll("\\s+", " ").trim());
        }
    }
}
