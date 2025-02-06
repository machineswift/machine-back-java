package com.machine.app.manage.data.area.controller;

import com.machine.app.manage.data.area.business.IDataAreaBusiness;
import com.machine.app.manage.data.area.controller.vo.request.DataAreaTreeRequestVo;
import com.machine.client.data.area.dto.output.AreaTreeOutputDto;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【DATA】区域模块（省市区）")
@RestController
@RequestMapping("manage/data/area")
public class DataAreaController {

    @Autowired
    private IDataAreaBusiness areaBusiness;

    @Hidden
    @Operation(summary = "清理缓存")
    @GetMapping("clear_cache")
    public void clearCache() {
        areaBusiness.clearCache();
    }

    @Operation(summary = "树(应用于组件弹窗)")
    @PostMapping("tree_simple")
    public AreaTreeOutputDto treeSimple(@RequestBody @Validated DataAreaTreeRequestVo request) {
        return areaBusiness.treeSimple(request);
    }

}