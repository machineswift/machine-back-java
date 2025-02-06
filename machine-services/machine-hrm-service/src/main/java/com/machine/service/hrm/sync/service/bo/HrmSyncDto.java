package com.machine.service.hrm.sync.service.bo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HrmSyncDto {

    @NotNull(message = "上次同步时间不能为空")
    private Long lastSyncTime;

    @NotNull(message = "同步截止时间不能为空")
    private Long enSyncTime;

    public HrmSyncDto(Long lastSyncTime,
                      Long enSyncTime) {
        this.lastSyncTime = lastSyncTime;
        this.enSyncTime = enSyncTime;
    }
}
