package com.machine.app.iam.user.controller;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.user.controller.vo.request.IamAdminChangePasswordRequestVo;
import com.machine.app.iam.user.business.IIamUserBusiness;
import com.machine.app.iam.user.controller.vo.request.*;
import com.machine.app.iam.user.controller.vo.response.IamUserSimpleListResponseVo;
import com.machine.app.iam.user.controller.vo.response.ShopUserSimpleListResponseVo;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.model.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "【IAM】用户模块")
@RestController
@RequestMapping("iam/user")
public class IamUserController {

    @Autowired
    private IIamUserBusiness userBusiness;

    @Operation(summary = "修改状态")
    @PostMapping("update_status")
    public void updateStatus(@RequestBody @Validated IamUserUpdateStatusRequestVo request) {
        log.info("修改员工状态，request={}", JSONUtil.toJsonStr(request));
        userBusiness.updateStatus(request);
    }

    @Operation(summary = "管理员修改密码")
    @PostMapping("change_password")
    public void adminChangePassword(@RequestBody @Validated IamAdminChangePasswordRequestVo request) {
        log.info("管理员修改密码，userId={} updateId={}",
                AppContext.getContext().getUserId(), request.getId());
        userBusiness.adminChangePassword(request);
    }

    @Operation(summary = "根据门店查询用户(区域经理、督导、门店用户，应用于组件弹窗)")
    @PostMapping("list_by_shopId")
    public ShopUserSimpleListResponseVo listByShopId(@RequestBody @Validated IamUserQueryListByShopIdRequestVo request) {
        return userBusiness.listByShopId(request);
    }

    @Operation(summary = "分页查询(应用于组件弹窗)")
    @PostMapping("page_simpled")
    public PageResponse<IamUserSimpleListResponseVo> pageSimpled(@RequestBody @Validated IamUserQueryPageSimpleRequestVo request) {
        return userBusiness.pageSimpled(request);
    }
}