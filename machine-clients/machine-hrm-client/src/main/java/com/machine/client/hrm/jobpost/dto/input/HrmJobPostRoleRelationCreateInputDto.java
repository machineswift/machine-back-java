package com.machine.client.hrm.jobpost.dto.input;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HrmJobPostRoleRelationCreateInputDto {

    /**
     * 职务Id
     */
    private String jobPostId;

    /**
     * 角色Id
     */
    private String roleId;

    public HrmJobPostRoleRelationCreateInputDto(String jobPostId,
                                                String roleId) {
        this.jobPostId = jobPostId;
        this.roleId = roleId;
    }
}
