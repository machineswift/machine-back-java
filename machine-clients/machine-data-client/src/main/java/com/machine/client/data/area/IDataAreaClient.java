package com.machine.client.data.area;

import com.machine.client.data.area.dto.input.DataAreaCreateInputDto;
import com.machine.client.data.area.dto.input.DataAreaTreeInputDto;
import com.machine.client.data.area.dto.input.DataAreaUpdateInputDto;
import com.machine.client.data.area.dto.input.DataAreaUpdateParentInputDto;
import com.machine.client.data.area.dto.output.DataAreaDetailOutputDto;
import com.machine.client.data.area.dto.output.DataAreaTreeOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/area",
        configuration = OpenFeignDefaultConfig.class)
public interface IDataAreaClient {

    @PostMapping("create")
    String create(@RequestBody @Validated DataAreaCreateInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Validated IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Validated DataAreaUpdateInputDto inputDto);

    @PostMapping("update_parent")
    int updateParent(@RequestBody @Validated DataAreaUpdateParentInputDto inputDto);

    @PostMapping("detail")
    DataAreaDetailOutputDto detail(IdRequest request);

    @PostMapping("tree")
    DataAreaTreeOutputDto tree(@RequestBody @Validated DataAreaTreeInputDto inputDto);

}
