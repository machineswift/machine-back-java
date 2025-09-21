package com.machine.service.hrm.jobpost.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.hrm.jobpost.IHrmJobPostRoleRelationClient;
import com.machine.client.hrm.jobpost.dto.input.HrmJobPosRoleRelationBatchInsertInputDto;
import com.machine.client.hrm.jobpost.dto.output.HrmJobPostRoleRelationListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.hrm.jobpost.service.IJobPostRoleRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/hrm/job_post_role_relation")
public class HrmJobPostRoleRelationServer implements IHrmJobPostRoleRelationClient {

    @Autowired
    private IJobPostRoleRelationService jobPostRoleRelationService;

    @Override
    @PostMapping("batch_create")
    public void batchCreate(@RequestBody @Validated HrmJobPosRoleRelationBatchInsertInputDto inputDto) {
        log.info("批量新增职务角色关系，inputDto={}", JSONUtil.toJsonStr(inputDto));
        jobPostRoleRelationService.batchCreate(inputDto);
    }

    @Override
    @PostMapping("delete_by_roleId")
    public int deleteByRoleId(@RequestBody @Validated IdRequest request) {
        log.info("批量新删除务角色关系，request={}", JSONUtil.toJsonStr(request));
        return jobPostRoleRelationService.deleteByRoleId(request);
    }

    @Override
    @PostMapping("list_by_roleIds")
    public List<HrmJobPostRoleRelationListOutputDto> listByRoleIds(@RequestBody @Validated IdSetRequest request) {
        return jobPostRoleRelationService.listByRoleIds(request);
    }

    @Override
    @PostMapping("list_by_jobPostIds")
    public List<HrmJobPostRoleRelationListOutputDto> listByJobPostIds(@RequestBody @Validated IdSetRequest request) {
        return jobPostRoleRelationService.listByJobPostIds(request);
    }
}
