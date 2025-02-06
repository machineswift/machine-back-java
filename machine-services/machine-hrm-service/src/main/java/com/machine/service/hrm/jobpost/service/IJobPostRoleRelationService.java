package com.machine.service.hrm.jobpost.service;

import com.machine.client.hrm.jobpost.dto.input.HrmJobPosRoleRelationBatchInsertInputDto;
import com.machine.client.hrm.jobpost.dto.output.HrmJobPostRoleRelationListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;

public interface IJobPostRoleRelationService {

    void batchCreate(HrmJobPosRoleRelationBatchInsertInputDto inputDto);

    int deleteByRoleId(IdRequest request);

    List<HrmJobPostRoleRelationListOutputDto> listByRoleIds(IdSetRequest request);

    List<HrmJobPostRoleRelationListOutputDto> listByJobPostIds(IdSetRequest request);

}
