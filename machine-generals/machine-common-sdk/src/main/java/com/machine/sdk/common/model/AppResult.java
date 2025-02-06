package com.machine.sdk.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.http.HttpStatus;

@Data
@Schema
@NoArgsConstructor
public class AppResult<T> {

    public AppResult(int status,
                     String code,
                     String message,
                     T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
        this.traceId = TraceContext.traceId();
        this.data = data;
    }

    /**
     * 状态码
     * {@link HttpStatus}
     */
    @Schema(description = "状态码（HTTP 状态码）")
    private int status;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "消息内容")
    private String message;

    @Schema(description = "当前时间（UNIX 时间戳）")
    private Long timestamp;

    @Schema(description = "链路追踪Id")
    private String traceId;

    private T data;

    public static <T> AppResult<T> success(String msg) {
        return success(msg, null);
    }

    public static <T> AppResult<T> success(T data) {
        return success("操作成功", data);
    }

    public static <T> AppResult<T> success(String msg, T data) {
        return generate(HttpStatus.OK.value(), "SUCCESS", msg, data);
    }

    public static <T> AppResult<T> fail(String code,
                                        String msg) {
        return fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), code, msg);
    }

    public static <T> AppResult<T> fail(int status,
                                        String code,
                                        String msg) {
        return fail(status, code, msg, null);
    }

    public static <T> AppResult<T> fail(int status,
                                        String code,
                                        String msg,
                                        T data) {
        return generate(status, code, msg, data);
    }


    public static <T> AppResult<T> generate(int status,
                                            String code,
                                            String msg,
                                            T data) {
        return new AppResult<>(status, code, msg, data);
    }

}
