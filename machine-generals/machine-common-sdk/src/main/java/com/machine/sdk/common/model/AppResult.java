package com.machine.sdk.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;

@Data
@Schema
@NoArgsConstructor
public class AppResult<T> {

    public AppResult(String code,
                     String message,
                     T data) {
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        this.traceId = TraceContext.traceId();
        this.data = data;
    }

    @Schema(description = "编码")
    private String code;

    @Schema(description = "消息内容")
    private String message;

    @Schema(description = "当前时间（UNIX 时间戳）")
    private Long timestamp;

    @Schema(description = "链路追踪Id")
    private String traceId;

    private T data;

    public static <T> AppResult<T> success(String message) {
        return success(message, null);
    }

    public static <T> AppResult<T> success(T data) {
        return success("操作成功", data);
    }

    public static <T> AppResult<T> success(String message, T data) {
        return generate("SUCCESS", message, data);
    }

    public static <T> AppResult<T> fail(String code,
                                        String message) {
        return fail(code, message, null);
    }

    public static <T> AppResult<T> fail(String code,
                                        String message,
                                        T data) {
        return generate(code, message, data);
    }


    public static <T> AppResult<T> generate(String code,
                                            String message,
                                            T data) {
        return new AppResult<>(code, message, data);
    }

}
