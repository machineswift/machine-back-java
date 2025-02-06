package com.machine.service.plugin.config.extension;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
 * P6spy SQL 打印策略
 */
public class CustomerP6SpyLogger implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category,
                                String prepared, String sql, String url) {
        if (StringUtils.isBlank(sql)) {
            return "";
        } else {
            return new StringBuilder(" Consume Time：")
                    .append(elapsed)
                    .append(" ms ")
                    .append(now)
                    .append("Execute SQL：").append(sql.replaceAll("[\\s]+", " "))
                    .append(";")
                    .toString();
        }
    }
}
