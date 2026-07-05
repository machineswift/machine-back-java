package com.machine.app.manage.scm.property.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.scm.property.business.IScmPropertyBusiness;
import com.machine.app.manage.scm.property.controller.vo.request.ScmPropertyCreateRequestVo;
import com.machine.app.manage.scm.property.controller.vo.request.ScmPropertyQueryPageRequestVo;
import com.machine.app.manage.scm.property.controller.vo.request.ScmPropertyUpdateRequestVo;
import com.machine.app.manage.scm.property.controller.vo.response.ScmPropertyDetailResponseVo;
import com.machine.app.manage.scm.property.controller.vo.response.ScmPropertyListResponseVo;
import com.machine.app.manage.scm.property.controller.vo.response.ScmPropertySimpleListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.IdResponse;
import com.machine.sdk.base.model.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "【SCM】属性库")
@RestController
@RequestMapping("manage/scm/property")
public class ScmPropertyController {

    @Autowired
    private IScmPropertyBusiness propertyBusiness;

    @Operation(summary = "创建属性")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('SYSTEM:SCM:PROPERTY:CREATE')")
    public IdResponse<String> create(@RequestBody @Validated ScmPropertyCreateRequestVo request) {
        log.info("创建属性，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(propertyBusiness.create(request));
    }

    @Operation(summary = "修改属性")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('SYSTEM:SCM:PROPERTY:UPDATE')")
    public void update(@RequestBody @Validated ScmPropertyUpdateRequestVo request) {
        log.info("修改属性，request={}", JSONUtil.toJsonStr(request));
        propertyBusiness.update(request);
    }

    @Operation(summary = "删除属性")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('SYSTEM:SCM:PROPERTY:DELETE')")
    public void deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除属性，id={}", request.getId());
        propertyBusiness.deleteById(request);
    }

    @Operation(summary = "查询属性详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('SYSTEM:SCM:PROPERTY:DETAIL')")
    public ScmPropertyDetailResponseVo getById(@RequestBody @Valid IdRequest request) {
        return propertyBusiness.getById(request);
    }

    @Operation(summary = "分页查询属性（应用于组件弹窗/属性选择器）")
    @PostMapping("page_simple")
    @PreAuthorize("hasAuthority('SYSTEM:SCM:PROPERTY:PAGE_SIMPLE')")
    public PageResponse<ScmPropertySimpleListResponseVo> pageSimple(
            @RequestBody @Validated ScmPropertyQueryPageRequestVo request) {
        return propertyBusiness.pageSimple(request);
    }

    @Operation(summary = "分页查询属性（应用于属性库管理菜单）")
    @PostMapping("page_expand")
    @PreAuthorize("hasAuthority('SYSTEM:SCM:PROPERTY:PAGE_EXPAND')")
    public PageResponse<ScmPropertyListResponseVo> pageExpand(@RequestBody @Validated ScmPropertyQueryPageRequestVo request) {
        return propertyBusiness.pageExpand(request);
    }

}
