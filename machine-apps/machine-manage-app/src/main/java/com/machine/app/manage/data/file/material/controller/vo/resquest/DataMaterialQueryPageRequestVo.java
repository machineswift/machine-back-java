package com.machine.app.manage.data.file.material.controller.vo.resquest;

import com.machine.sdk.base.envm.data.file.DataFileTypeEnum;
import com.machine.sdk.base.envm.data.file.material.DataMaterialAuditStatusEnum;
import com.machine.sdk.base.envm.data.file.material.DataMaterialBusinessStatusEnum;
import com.machine.sdk.base.envm.data.file.material.DataMaterialProcessStatusEnum;
import com.machine.sdk.base.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class DataMaterialQueryPageRequestVo extends PageRequest {

    @Schema(description = "文件类型集合，多选")
    private Set<DataFileTypeEnum> fileTypeSet;

    @Schema(description = "素材标题，模糊匹配")
    private String title;

    @Schema(description = "素材名称，模糊匹配")
    private String name;

    @Schema(description = "系统处理状态")
    private DataMaterialProcessStatusEnum processStatus;

    @Schema(description = "业务状态")
    private DataMaterialBusinessStatusEnum businessStatus;

    @Schema(description = "审核状态")
    private DataMaterialAuditStatusEnum auditStatus;

    @Schema(description = "素材分类ID集合")
    private Set<String> categoryIdSet;

    @Schema(description = "创建人ID集合")
    private Set<String> createUserIdSet;

    @Schema(description = "修改人ID集合")
    private Set<String> updateUserIdSet;

    @Schema(description = "更新时间起（Unix 毫秒）")
    private Long updateStartTime;

    @Schema(description = "更新时间止（Unix 毫秒）")
    private Long updateEndTime;

    @Schema(description = "创建时间起（Unix 毫秒）")
    private Long createStartTime;

    @Schema(description = "创建时间止（Unix 毫秒）")
    private Long createEndTime;

}
