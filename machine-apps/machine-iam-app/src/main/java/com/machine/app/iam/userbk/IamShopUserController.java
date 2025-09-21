package com.machine.app.iam.userbk;

import cn.hutool.json.JSONUtil;
import com.machine.app.iam.userbk.business.IIamShopUserBusiness;
import com.machine.app.iam.userbk.vo.request.IamShopUserCreateRequestVo;
import com.machine.app.iam.userbk.vo.request.IamShopUserQueryPageExpandRequestVo;
import com.machine.app.iam.userbk.vo.request.IamShopUserUpdateRequestVo;
import com.machine.app.iam.userbk.vo.response.IamShopUserExpandListResponseVo;
import com.machine.app.iam.userbk.vo.response.IamShopUserDetailResponseVo;
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
@Tag(name = "【IAM】门店员工模块")
@RestController
@RequestMapping("iam/user_shop")
public class IamShopUserController {

    @Autowired
    private IIamShopUserBusiness shopUserBusiness;

    @Operation(summary = "创建")
    @PostMapping("create")
    public IdResponse<String> create(@RequestBody @Validated IamShopUserCreateRequestVo request) {
        log.info("创建门店员工，request={}", JSONUtil.toJsonStr(request));
        return new IdResponse<>(shopUserBusiness.create(request));
    }

    @Operation(summary = "修改")
    @PostMapping("update")
    public void update(@RequestBody @Validated IamShopUserUpdateRequestVo request) {
        log.info("修改门店员工，request={}", JSONUtil.toJsonStr(request));
        shopUserBusiness.update(request);
    }

    @Operation(summary = "详情")
    @PostMapping("detail")
    public IamShopUserDetailResponseVo detail(@RequestBody @Validated IdRequest request) {
        return shopUserBusiness.detail(request);
    }

    @Operation(summary = "分页查询(扩充，应用于员工管理菜单)")
    @PostMapping("page_expand")
    public PageResponse<IamShopUserExpandListResponseVo> pageExpand(@RequestBody @Validated IamShopUserQueryPageExpandRequestVo request) {
        return shopUserBusiness.pageExpand(request);
    }

}