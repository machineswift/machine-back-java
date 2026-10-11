package com.machine.client.scm.attribute;

import com.machine.client.scm.attribute.dto.input.ScmAttributeValueCreateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueQueryInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueUpdateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueUpdateCodeInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueUpdateStatusInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeValueDetailOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeValueListOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeValueSimpleListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import java.util.List;

@FeignClient(name = "machine-scm-service", path = "machine-scm-service/server/scm/attribute/attribute_value",
        configuration = OpenFeignMinTimeConfig.class)
public interface IScmAttributeValueClient {

    @PostMapping("create")
    String create(@RequestBody @Valid ScmAttributeValueCreateInputDto inputDto);

    @PostMapping("delete")
    int deleteById(@RequestBody @Valid IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Valid ScmAttributeValueUpdateInputDto inputDto);

    @PostMapping("update_status")
    int updateStatus(@RequestBody @Valid ScmAttributeValueUpdateStatusInputDto inputDto);

    @PostMapping("update_code")
    int updateCode(@RequestBody @Valid ScmAttributeValueUpdateCodeInputDto inputDto);

    @PostMapping("detail")
    ScmAttributeValueDetailOutputDto getById(@RequestBody @Valid IdRequest request);

    @PostMapping("list_simple")
    List<ScmAttributeValueSimpleListOutputDto> listSimple(@RequestBody @Valid ScmAttributeValueQueryInputDto inputDto);

    @PostMapping("list_expand")
    List<ScmAttributeValueListOutputDto> listExpand(@RequestBody @Valid ScmAttributeValueQueryInputDto inputDto);
}