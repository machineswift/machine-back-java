package com.machine.client.data.brand.dto.input;

import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.model.request.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Schema
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DataBrandQueryPageInputDto extends PageRequest {

    @Schema(description = "父品牌ID（查询子品牌时使用，根节点为 root）")
    private String parentId;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "拼音全拼")
    private String namePinyinFull;

    @Schema(description = "拼音首字母")
    private String namePinyinInitial;

    @Schema(description = "英文名称")
    private String nameEn;

    @Schema(description = "编码")
    private String code;

    @Schema(description = "状态（StatusEnum）")
    private StatusEnum status;

    @Schema(description = "品牌分类集合")
    private Set<String> categoryIdSet;

    @Schema(description = "是否包含【未分配】节点，默认：false")
    private Boolean containVirtualNode = false;

    @Schema(description = "创建人ID集合")
    private Set<String> createUserIdSet;

    @Schema(description = "修改人ID集合")
    private Set<String> updateUserIdSet;

    @Schema(description = "创建开始时间")
    private Long createStartTime;

    @Schema(description = "创建结束时间")
    private Long createEndTime;
}


