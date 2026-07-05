package com.machine.client.scm.property;

import com.machine.client.scm.property.dto.input.ScmPropertyGroupCreateInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyGroupQueryInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyGroupUpdateInputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyGroupDetailOutputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyGroupListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

@FeignClient(name = "machine-scm-service", path = "machine-scm-service/server/scm/property/property_group",
        configuration = OpenFeignMinTimeConfig.class)
public interface IScmPropertyGroupClient {

    @PostMapping("create")
    String create(@RequestBody @Valid ScmPropertyGroupCreateInputDto inputDto);

    @PostMapping("update")
    int update(@RequestBody @Valid ScmPropertyGroupUpdateInputDto inputDto);

    @PostMapping("delete")
    int deleteById(@RequestBody @Valid IdRequest request);

    @PostMapping("detail")
    ScmPropertyGroupDetailOutputDto getById(@RequestBody @Valid IdRequest request);

    @PostMapping("list_by_back_category_id")
    List<ScmPropertyGroupListOutputDto> listByBackCategoryId(@RequestBody @Valid ScmPropertyGroupQueryInputDto inputDto);

}