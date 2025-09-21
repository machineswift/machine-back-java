package com.machine.service.hrm.jobpost.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.hrm.jobpost.dto.input.HrmJobPosBatchInsertInputDto;
import com.machine.client.hrm.jobpost.dto.input.HrmJobPostListSimpleInputDto;
import com.machine.client.hrm.jobpost.dto.output.HrmJobPostDetailOutputDto;
import com.machine.client.hrm.jobpost.dto.output.HrmJobPostListSimpleOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;

import java.util.List;
import java.util.Map;

public interface IJobPostService {

    void sync(long lastSyncTime,
              long enSyncTime);

    void batchCreate(HrmJobPosBatchInsertInputDto inputDto);

    HrmJobPostDetailOutputDto getByUserId(IdRequest request);

    List<HrmJobPostListSimpleOutputDto> listByRoleId(IdRequest request);

    Page<HrmJobPostListSimpleOutputDto> pageSimple(HrmJobPostListSimpleInputDto inputDto);

    Map<String, HrmJobPostDetailOutputDto> mapByIdSet(IdSetRequest request);

}
