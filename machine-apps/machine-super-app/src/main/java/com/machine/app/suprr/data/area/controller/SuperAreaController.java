package com.machine.app.suprr.data.area.controller;

import com.machine.app.suprr.data.area.business.ISuperAreaBusiness;
import com.machine.app.suprr.data.area.controller.vo.request.SuperAreaTreeRequestVo;
import com.machine.app.suprr.data.area.controller.vo.response.SuperAreaTreeExpandSelfResponseVo;
import com.machine.app.suprr.data.area.controller.vo.response.SupperAreaTreeSimpleResponseVo;
import com.machine.client.data.area.dto.output.DataAreaTreeOutputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "【DATA】区域模块")
@RestController
@RequestMapping("super/data/area")
public class SuperAreaController {

    @Autowired
    private ISuperAreaBusiness areaBusiness;

    @Operation(summary = "树(当前用户门店关联的区域基础信息)")
    @PostMapping("tree_self_simple")
    public DataAreaTreeOutputDto treeSelfSimple(@RequestBody @Validated SuperAreaTreeRequestVo request) {
        return areaBusiness.treeSelfSimple(request);
    }

    @Operation(summary = "树(当前用户门店关联的区域扩展信息)")
    @PostMapping("tree_self_expand")
    public SuperAreaTreeExpandSelfResponseVo treeSelfExpand(@RequestBody @Validated SuperAreaTreeRequestVo request) {
        return areaBusiness.treeSelfExpand(request);
    }

    @Operation(summary = "树(所有的区域基础信息)")
    @PostMapping("tree_all_simple")
    public SupperAreaTreeSimpleResponseVo treeAllSimple(@RequestBody @Validated SuperAreaTreeRequestVo request) {
        return areaBusiness.treeAllSimple(request);
    }
}
