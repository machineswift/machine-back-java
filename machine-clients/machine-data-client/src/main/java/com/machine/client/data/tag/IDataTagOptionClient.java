package com.machine.client.data.tag;

import com.machine.client.data.tag.dto.input.*;
import com.machine.client.data.tag.dto.output.DataTagOptionDetailOutputDto;
import com.machine.client.data.tag.dto.output.DataTagOptionListOutputDto;
import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import com.machine.sdk.common.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

import java.util.List;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/tag/option",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataTagOptionClient {

    @PostMapping("create")
    String create(@RequestBody @Valid DataTagOptionCreateInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Valid IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Valid DataTagOptionUpdateInputDto inputDto);

    @PostMapping("update_code")
    int updateCode(@RequestBody @Valid DataTagOptionUpdateCodeInputDto inputDto);

    @PostMapping("update_status")
    int updateStatus(@RequestBody @Valid DataTagOptionUpdateStatusInputDto inputDto);

    @PostMapping("update_sort")
    int updateSort(@RequestBody @Valid DataTagOptionUpdateSortInputDto inputDto);

    @PostMapping("detail")
    DataTagOptionDetailOutputDto detail(@RequestBody @Valid IdRequest request);

    @PostMapping("select_list")
    List<DataTagOptionListOutputDto> selectList(@RequestBody @Valid DataTagOptionQueryListInputDto inputDto);

}
