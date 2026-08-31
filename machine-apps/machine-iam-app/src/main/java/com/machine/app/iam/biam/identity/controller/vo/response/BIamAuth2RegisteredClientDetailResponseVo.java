package com.machine.app.iam.biam.identity.controller.vo.response;

import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.envm.biam.identity.BIamAuthorizationGrantTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Data
@Schema
@NoArgsConstructor
public class BIamAuth2RegisteredClientDetailResponseVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "授权方式")
    private BIamAuthorizationGrantTypeEnum authorizationGrantType;

    @Schema(description = "状态")
    private StatusEnum status;

    @Schema(description = "客户端ID")
    private String clientId;

    @Schema(description = "客户端名称")
    private String clientName;

    @Schema(description = "作用域列表")
    private Set<String> scopes;

    @Schema(description = "ip白名单")
    private TreeSet<String> allowedIps;

    // ---- 仅授权码模式需要 start  ----

    @Schema(description = "重定向URI")
    private List<String> redirectUris;

    @Schema(description = "登出后重定向URI")
    private List<String> postLogoutRedirectUris;

    // ---- 仅授权码模式需要 end  ----

    @Schema(description = "创建人ID")
    private String createBy;

    @Schema(description = "创建人姓名")
    private String createName;

    @Schema(description = "创建时间（Unix 时间戳）")
    private Long createTime;

    @Schema(description = "操作人ID")
    private String updateBy;

    @Schema(description = "操作人姓名")
    private String updateName;

    @Schema(description = "更新时间（Unix 时间戳）")
    private Long updateTime;
}
