package com.machine.app.manage.hrm.jobpost.controller.vo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class HrmJobPostListSimpleResponseVo {

    @Schema(description = "id")
    private String id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "编码")
    private String code;

}
