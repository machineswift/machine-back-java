package com.machine.app.iam.biam.identity.controller.vo.request;

import com.machine.sdk.base.annotation.StrongPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Data
@Schema
@NoArgsConstructor
public class BIamAuth2RegisteredClientUpdateRequestVo {

    @NotBlank(message = "id 不能为空")
    @Schema(description = "ID")
    private String id;

    @Schema(description = "clientName")
    @NotBlank(message = "clientName 不能为空")
    private String clientName;

    @ToString.Exclude
    @Schema(description = "客户端密钥")
    @StrongPassword
    private String clientSecret;

    /**
     * 作用域:
     *  1.客户端模式存储的是角色id
     *  2.授权码模式存储的是openid、profile、email、address、phone
     */
    @NotEmpty(message = "scopes 不能为空")
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

}
