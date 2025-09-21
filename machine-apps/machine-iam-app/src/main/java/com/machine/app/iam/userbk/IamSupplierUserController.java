package com.machine.app.iam.userbk;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.userbk.business.IIamSupplierUserBusiness;
import com.machine.app.iam.userbk.vo.request.IamSupplierUserCreateRequestVo;
import com.machine.app.iam.userbk.vo.request.IamSupplierUserQueryPageExpandRequestVo;
import com.machine.app.iam.userbk.vo.request.IamSupplierUserUpdateRequestVo;
import com.machine.app.iam.userbk.vo.response.IamSupplierUserDetailResponseVo;
import com.machine.app.iam.userbk.vo.response.IamSupplierUserExpandListResponseVo;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.IdResponse;
import com.machine.sdk.common.model.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【IAM】供应商模块")
@RestController
@RequestMapping("iam/user_supplier")
public class IamSupplierUserController {

    @Autowired
    private IIamSupplierUserBusiness supplierUserBusiness;

    @Operation(summary = "创建")
    @PostMapping("create")
    public IdResponse<String> create(@RequestBody @Validated IamSupplierUserCreateRequestVo request) {
        log.info("创建供应商，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(supplierUserBusiness.create(request));
    }

    @Operation(summary = "修改")
    @PostMapping("update")
    public void update(@RequestBody @Validated IamSupplierUserUpdateRequestVo request) {
        log.info("修改供应商，request={}", JSONUtil.toJsonStr(request));
        supplierUserBusiness.update(request);
    }

    @Operation(summary = "详情")
    @PostMapping("detail")
    public IamSupplierUserDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return supplierUserBusiness.detail(request);
    }

    @Operation(summary = "分页查询(扩充，应用于员工管理菜单)")
    @PostMapping("page_expand")
    public PageResponse<IamSupplierUserExpandListResponseVo> pageExpand(@RequestBody @Validated IamSupplierUserQueryPageExpandRequestVo request) {
        return supplierUserBusiness.pageExpand(request);
    }

}