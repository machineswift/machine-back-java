package com.machine.client.data.label;

import com.machine.client.data.label.dto.input.*;
import com.machine.client.data.label.dto.output.DataLabelOptionDetailOutputDto;
import com.machine.client.data.label.dto.output.DataLabelOptionListOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/label_option",
        configuration = OpenFeignDefaultConfig.class)
public interface ILabelOptionClient {

    @PostMapping("create")
    String create(@RequestBody @Validated DataLabelOptionCreateInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Validated IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Validated DataLabelOptionUpdateInputDto inputDto);

    @PostMapping("update_status")
    int updateStatus(@RequestBody @Validated DataLabelOptionUpdateStatusInputDto inputDto);

    @PostMapping("detail")
    DataLabelOptionDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("list_by_idSet")
    List<DataLabelOptionListOutputDto> listByIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("list_by_offset")
    List<DataLabelOptionListOutputDto> listByOffset(@RequestBody @Validated DataLabelOptionQueryListOffsetInputDto inputDto);

    @PostMapping("map_by_idSet")
    Map<String, DataLabelOptionListOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("page")
    PageResponse<DataLabelOptionListOutputDto> page(@RequestBody @Validated DataLabelOptionQueryPageInputDto inputDto);

}
