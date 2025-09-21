package com.machine.service.hrm.jobpost.server;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.hrm.jobpost.IHrmJobPostClient;
import com.machine.client.hrm.jobpost.dto.input.HrmJobPostListSimpleInputDto;
import com.machine.client.hrm.jobpost.dto.output.HrmJobPostDetailOutputDto;
import com.machine.client.hrm.jobpost.dto.output.HrmJobPostListSimpleOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import com.machine.service.hrm.jobpost.service.IJobPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("server/hrm/job_post")
public class HrmJobPostServer implements IHrmJobPostClient {

    @Autowired
    private IJobPostService jobPostService;


    @Override
    @PostMapping("get_by_userId")
    public HrmJobPostDetailOutputDto getByUserId(@RequestBody @Validated IdRequest request) {
        return jobPostService.getByUserId(request);
    }

    @Override
    @PostMapping("list_by_roleId")
    public List<HrmJobPostListSimpleOutputDto> listByRoleId(@RequestBody @Validated IdRequest request) {
        return jobPostService.listByRoleId(request);
    }

    @Override
    @PostMapping("page_simple")
    public PageResponse<HrmJobPostListSimpleOutputDto> pageSimple(@RequestBody @Validated HrmJobPostListSimpleInputDto inputDto) {
        Page<HrmJobPostListSimpleOutputDto> pageResult = jobPostService.pageSimple(inputDto);
        return new PageResponse<>(
                pageResult.getCurrent(),
                pageResult.getSize(),
                pageResult.getTotal(),
                pageResult.getRecords());
    }

    @Override
    @PostMapping("map_by_idSet")
    public Map<String, HrmJobPostDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request) {
        return jobPostService.mapByIdSet(request);
    }

}
