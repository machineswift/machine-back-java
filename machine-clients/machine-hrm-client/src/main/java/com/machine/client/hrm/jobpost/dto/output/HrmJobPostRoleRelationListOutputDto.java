package com.machine.client.hrm.jobpost.dto.output;

import lombok.Data;

@Data
public class HrmJobPostRoleRelationListOutputDto {

    private String id;

    /**
     * 职务Id
     */
    private String jobPostId;

    /**
     * 角色Id
     */
    private String roleId;

}
