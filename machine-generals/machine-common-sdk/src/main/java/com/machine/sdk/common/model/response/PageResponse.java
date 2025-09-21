package com.machine.sdk.common.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@Schema
@NoArgsConstructor
public class PageResponse<T> {

    public PageResponse(long current,
                        long size,
                        long total) {
        this.current = current;
        this.size = size;
        this.total = total;
    }

    public PageResponse(long current,
                        long size,
                        long total,
                        List<T> records) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.records = records;
    }

    @Schema(description = "当前页")
    private Long current;

    @Schema(description = "每页的数量")
    private Long size;

    @Schema(description = "总记录数")
    protected Long total;

    @Schema(description = "结果集")
    protected List<T> records = Collections.emptyList();
}
