package com.machine.client.scm.property;

import com.machine.client.scm.property.dto.input.ScmPropertyValueCreateInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyValueQueryInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyValueUpdateInputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyValueDetailOutputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyValueListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

@FeignClient(name = "machine-scm-service", path = "machine-scm-service/server/scm/property/property_value",
        configuration = OpenFeignMinTimeConfig.class)
public interface IScmPropertyValueClient {

    @PostMapping("create")
    String create(@RequestBody @Valid ScmPropertyValueCreateInputDto inputDto);

    @PostMapping("update")
    int update(@RequestBody @Valid ScmPropertyValueUpdateInputDto inputDto);

    @PostMapping("delete")
    int deleteById(@RequestBody @Valid IdRequest request);

    @PostMapping("detail")
    ScmPropertyValueDetailOutputDto getById(@RequestBody @Valid IdRequest request);

    @PostMapping("list_by_property_id")
    List<ScmPropertyValueListOutputDto> listByPropertyId(@RequestBody @Valid ScmPropertyValueQueryInputDto inputDto);
}