package com.machine.client.hrm.employee.dto.input;

import com.machine.sdk.beisen.envm.BeiSenEmployeeStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class HrmEmployeeQueryIListInputDto {

    /**
     * 用户ID集合
     */
    private Set<String> userIdSet;

    /**
     * 部门ID集合
     */
    private Set<String> departmentIdSet;

    /**
     * 状态集合
     */
    private Set<BeiSenEmployeeStatusEnum> statusSet;

    /**
     * 是否是部门负责人
     */
    private Boolean isCharge;


    public HrmEmployeeQueryIListInputDto(Set<String> userIdSet,
                                         boolean onJob) {
        this.userIdSet = userIdSet;
        if (onJob) {
            Set<BeiSenEmployeeStatusEnum> statusSet = new HashSet<>();
            statusSet.add(BeiSenEmployeeStatusEnum.PENDING_HIRE);
            statusSet.add(BeiSenEmployeeStatusEnum.PROBATION);
            statusSet.add(BeiSenEmployeeStatusEnum.FULL_TIME);
            statusSet.add(BeiSenEmployeeStatusEnum.TRANSFER_OUT);
            statusSet.add(BeiSenEmployeeStatusEnum.PENDING_TRANSFER_IN);
            statusSet.add(BeiSenEmployeeStatusEnum.RETIRED);
            statusSet.add(BeiSenEmployeeStatusEnum.INFORMAL);
            this.statusSet = statusSet;
        }
    }

    public HrmEmployeeQueryIListInputDto(Set<String> departmentIdSet,
                                         Boolean isCharge,
                                         boolean onJob) {
        this.departmentIdSet = departmentIdSet;
        this.isCharge = isCharge;
        if (onJob) {
            Set<BeiSenEmployeeStatusEnum> statusSet = new HashSet<>();
            statusSet.add(BeiSenEmployeeStatusEnum.PENDING_HIRE);
            statusSet.add(BeiSenEmployeeStatusEnum.PROBATION);
            statusSet.add(BeiSenEmployeeStatusEnum.FULL_TIME);
            statusSet.add(BeiSenEmployeeStatusEnum.TRANSFER_OUT);
            statusSet.add(BeiSenEmployeeStatusEnum.PENDING_TRANSFER_IN);
            statusSet.add(BeiSenEmployeeStatusEnum.RETIRED);
            statusSet.add(BeiSenEmployeeStatusEnum.INFORMAL);
            this.statusSet = statusSet;
        }
    }
}
