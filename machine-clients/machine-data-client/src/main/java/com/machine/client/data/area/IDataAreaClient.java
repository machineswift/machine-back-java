package com.machine.client.data.area;

import com.machine.client.data.area.dto.input.DataAreaCreateInputDto;
import com.machine.client.data.area.dto.input.DataAreaUpdateInputDto;
import com.machine.client.data.area.dto.input.DataAreaUpdateParentInputDto;
import com.machine.client.data.area.dto.output.DataAreaDetailOutputDto;
import com.machine.client.data.area.dto.output.DataAreaTreeOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.envm.data.DataCountryEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.tool.Tuples;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "machine-data-service", path = "machine-data-service/server/data/area", configuration = OpenFeignMinTimeConfig.class)
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

    @GetMapping("tree_all")
    Tuples.Tuple2<String, DataAreaTreeOutputDto> treeAll(@RequestParam("country") DataCountryEnum country);

}
