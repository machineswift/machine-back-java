package com.machine.client.hrm.jobpost;

import com.machine.client.hrm.jobpost.dto.input.HrmJobPosRoleRelationBatchInsertInputDto;
import com.machine.client.hrm.jobpost.dto.output.HrmJobPostRoleRelationListOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "machine-hrm-service/machine-hrm-service/server/hrm/job_post_role_relation", configuration = OpenFeignDefaultConfig.class)
public interface IHrmJobPostRoleRelationClient {

    @PostMapping("batch_create")
    void batchCreate(@RequestBody @Validated HrmJobPosRoleRelationBatchInsertInputDto inputDto);

    @PostMapping("delete_by_roleId")
    int deleteByRoleId(@RequestBody @Validated IdRequest request);

    @PostMapping("list_by_roleIds")
    List<HrmJobPostRoleRelationListOutputDto> listByRoleIds(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_by_jobPostIds")
    List<HrmJobPostRoleRelationListOutputDto> listByJobPostIds(@RequestBody @Validated IdSetRequest request);
}
