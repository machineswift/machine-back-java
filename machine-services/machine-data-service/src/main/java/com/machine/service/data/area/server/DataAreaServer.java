package com.machine.service.data.area.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.data.area.IDataAreaClient;
import com.machine.client.data.area.dto.input.DataAreaCreateInputDto;
import com.machine.client.data.area.dto.input.DataAreaUpdateInputDto;
import com.machine.client.data.area.dto.input.DataAreaUpdateParentInputDto;
import com.machine.client.data.area.dto.output.DataAreaDetailOutputDto;
import com.machine.client.data.area.dto.output.DataAreaTreeOutputDto;
import com.machine.sdk.base.envm.data.DataCountryEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.tool.Tuples;
import com.machine.service.data.area.service.IDataAreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("server/data/area")
public class DataAreaServer implements IDataAreaClient {

    @Autowired
    private IDataAreaService areaService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated DataAreaCreateInputDto inputDto) {
        log.info("创建区域，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return areaService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int delete(@RequestBody @Validated IdRequest request) {
        log.info("删除区域，request={}", JSONUtil.toJsonStr(request));
        return areaService.delete(request);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated DataAreaUpdateInputDto inputDto) {
        log.info("修改区域，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return areaService.update(inputDto);
    }

    @Override
    @PostMapping("update_parent")
    public int updateParent(@RequestBody @Validated DataAreaUpdateParentInputDto inputDto) {
        log.info("修改父区域，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return areaService.updateParent(inputDto);

    }

    @Override
    @PostMapping("detail")
    public DataAreaDetailOutputDto detail(@RequestBody @Validated IdRequest request) {
        return areaService.detail(request);
    }

    @Override
    @GetMapping("tree_all")
    public Tuples.Tuple2<String, DataAreaTreeOutputDto> treeAll(@RequestParam("country") DataCountryEnum country) {
        return areaService.treeAll(country);
    }

}
