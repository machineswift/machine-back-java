package com.machine.client.iam.biam.user.dto.input;

import com.machine.sdk.base.envm.biam.organization.BIamOrganizationTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema
@NoArgsConstructor
public class BIamDataUserNotBindOrganizationInputDto {

    @NotNull(message = "组织类型不能为空")
    @Schema(description = "组织类型（OrganizationTypeEnum）")
    private BIamOrganizationTypeEnum organizationType;

    public BIamDataUserNotBindOrganizationInputDto(BIamOrganizationTypeEnum organizationType) {
        this.organizationType = organizationType;
    }
}
