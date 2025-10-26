package com.machine.client.hrm.jobpost;

import com.machine.client.hrm.jobpost.dto.input.HrmJobPostListSimpleInputDto;
import com.machine.client.hrm.jobpost.dto.output.HrmJobPostDetailOutputDto;
import com.machine.client.hrm.jobpost.dto.output.HrmJobPostListSimpleOutputDto;
import com.machine.sdk.common.config.OpenFeignMaxTimeConfig;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "machine-hrm-service", path = "machine-hrm-service/server/hrm/job_post",
        configuration = OpenFeignMaxTimeConfig.class)
public interface IHrmJobPostClient {

    @PostMapping("get_by_userId")
    HrmJobPostDetailOutputDto getByUserId(@RequestBody @Validated IdRequest request);

    @PostMapping("list_by_roleId")
    List<HrmJobPostListSimpleOutputDto> listByRoleId(@RequestBody @Validated IdRequest request);

    @PostMapping("page_simple")
    PageResponse<HrmJobPostListSimpleOutputDto> pageSimple(@RequestBody @Validated HrmJobPostListSimpleInputDto inputDto);

    @PostMapping("map_by_idSet")
    Map<String, HrmJobPostDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request);

}



