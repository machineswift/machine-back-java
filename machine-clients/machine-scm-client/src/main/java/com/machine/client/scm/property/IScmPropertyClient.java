package com.machine.client.scm.property;

import com.machine.client.scm.property.dto.input.ScmPropertyCreateInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyQueryPageInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyUpdateInputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyDetailOutputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

@FeignClient(name = "machine-scm-service", path = "machine-scm-service/server/scm/property/property",
        configuration = OpenFeignMinTimeConfig.class)
public interface IScmPropertyClient {

    @PostMapping("create")
    String create(@RequestBody @Valid ScmPropertyCreateInputDto inputDto);

    @PostMapping("delete")
    int deleteById(@RequestBody @Valid IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Valid ScmPropertyUpdateInputDto inputDto);

    @PostMapping("detail")
    ScmPropertyDetailOutputDto getById(@RequestBody @Valid IdRequest request);

    @PostMapping("list_all")
    List<ScmPropertyListOutputDto> listAll();

    @PostMapping("select_page")
    PageResponse<ScmPropertyListOutputDto> selectPage(@RequestBody @Valid ScmPropertyQueryPageInputDto inputDto);
}