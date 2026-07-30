package com.machine.app.admin.scm.property.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.admin.scm.property.business.IScmPropertyGroupBusiness;
import com.machine.app.admin.scm.property.controller.vo.request.ScmPropertyGroupCreateRequestVo;
import com.machine.app.admin.scm.property.controller.vo.request.ScmPropertyGroupListByBackCategoryRequestVo;
import com.machine.app.admin.scm.property.controller.vo.request.ScmPropertyGroupUpdateRequestVo;
import com.machine.app.admin.scm.property.controller.vo.request.ScmPropertyGroupUpdateSortRequestVo;
import com.machine.app.admin.scm.property.controller.vo.response.ScmPropertyGroupDetailResponseVo;
import com.machine.app.admin.scm.property.controller.vo.response.ScmPropertyGroupListResponseVo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.IdResponse;
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

import java.util.List;

/**
 * 后台叶子类目下的属性展示分组
 */
@Slf4j
@Tag(name = "【SCM】属性分组")
@RestController
@RequestMapping("admin/scm/property_group")
public class ScmPropertyGroupController {

    @Autowired
    private IScmPropertyGroupBusiness propertyGroupBusiness;

    @Operation(summary = "创建属性分组")
    @PostMapping("create")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:SCM:PROPERTY_GROUP:CREATE')")
    public IdResponse<String> create(@RequestBody @Validated ScmPropertyGroupCreateRequestVo request) {
        log.info("创建属性分组，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(propertyGroupBusiness.create(request));
    }

    @Operation(summary = "修改属性分组")
    @PostMapping("update")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:SCM:PROPERTY_GROUP:UPDATE')")
    public void update(@RequestBody @Validated ScmPropertyGroupUpdateRequestVo request) {
        log.info("修改属性分组，request={}", JSONUtil.toJsonStr(request));
        propertyGroupBusiness.update(request);
    }

    @Operation(summary = "删除属性分组")
    @PostMapping("delete")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:SCM:PROPERTY_GROUP:DELETE')")
    public void deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除属性分组，id={}", request.getId());
        propertyGroupBusiness.deleteById(request);
    }

    @Operation(summary = "查询属性分组详情")
    @PostMapping("detail")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:SCM:PROPERTY_GROUP:DETAIL')")
    public ScmPropertyGroupDetailResponseVo getById(@RequestBody @Valid IdRequest request) {
        return propertyGroupBusiness.getById(request);
    }

    @Operation(summary = "修改属性分组排序")
    @PostMapping("update_sort")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:SCM:PROPERTY_GROUP:UPDATE_SORT')")
    public void updateSort(@RequestBody @Validated ScmPropertyGroupUpdateSortRequestVo request) {
        log.info("修改属性分组排序，request={}", JSONUtil.toJsonStr(request));
        propertyGroupBusiness.updateSort(request);
    }

    @Operation(summary = "根据后台叶子类目ID查询属性分组列表")
    @PostMapping("list_by_back_category_id")
    @PreAuthorize("hasAuthority('MANAGE_APP:SYSTEM:SCM:PROPERTY_GROUP:LIST')")
    public List<ScmPropertyGroupListResponseVo> listByBackCategoryId(
            @RequestBody @Validated ScmPropertyGroupListByBackCategoryRequestVo request) {
        return propertyGroupBusiness.listByBackCategoryId(request);
    }

}
