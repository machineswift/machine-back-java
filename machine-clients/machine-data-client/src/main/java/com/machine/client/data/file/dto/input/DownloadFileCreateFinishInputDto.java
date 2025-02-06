package com.machine.client.data.file.dto.input;

import com.machine.sdk.common.envm.data.download.DownLoadFileChannelEnum;
import com.machine.sdk.common.model.dto.data.MaterialDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DownloadFileCreateFinishInputDto {

    @NotNull(message = "渠道不能为空")
    @Schema(description = "渠道（DownLoadFileChannelEnum）")
    private DownLoadFileChannelEnum channel;

    @NotBlank(message = "用户id不能为空")
    @Schema(description = "用户id")
    private String userId;

    @NotNull(message = "过期时间不能为空")
    @Schema(description = "过期时间（UNIX 时间戳）")
    private Long expirationIn;

    @NotNull(message = "附件信息不能为空")
    @Schema(description = "附件信息")
    private MaterialDto material;
}