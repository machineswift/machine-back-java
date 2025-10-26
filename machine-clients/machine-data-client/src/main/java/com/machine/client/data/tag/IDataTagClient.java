package com.machine.client.data.tag;

import com.machine.client.data.tag.dto.input.DataTagCreateInputDto;
import com.machine.client.data.tag.dto.input.DataTagQueryPageInputDto;
import com.machine.client.data.tag.dto.input.DataTagUpdateCategoryDto;
import com.machine.client.data.tag.dto.input.DataTagUpdateInputDto;
import com.machine.client.data.tag.dto.output.DataTagDetailOutputDto;
import com.machine.client.data.tag.dto.output.DataTagListOutputDto;
import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/tag",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataTagClient {

    @PostMapping("create")
    String create(@RequestBody @Valid DataTagCreateInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Valid IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Valid DataTagUpdateInputDto inputDto);

    @PostMapping("update_category")
    int updateCategory(@RequestBody @Valid DataTagUpdateCategoryDto inputDto);

    @PostMapping("detail")
    DataTagDetailOutputDto detail(@RequestBody @Valid IdRequest request);

    @PostMapping("select_page")
    PageResponse<DataTagListOutputDto> selectPage(@RequestBody @Valid DataTagQueryPageInputDto inputDto);

}
