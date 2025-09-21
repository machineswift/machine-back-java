package com.machine.app.manage.crm.customer.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.manage.crm.customer.business.ICrmCustomerBusiness;
import com.machine.app.manage.crm.customer.controller.vo.resquest.CrmCustomerQueryPageRequestVo;
import com.machine.app.manage.crm.member.controller.vo.response.CrmMemberDetailResponseVo;
import com.machine.app.manage.crm.member.controller.vo.response.CrmMemberExpandListResponseVo;
import com.machine.app.manage.crm.member.controller.vo.response.CrmMemberListResponseVo;
import com.machine.app.manage.crm.member.controller.vo.resquest.CrmMemberCreateRequestVo;
import com.machine.app.manage.crm.member.controller.vo.resquest.CrmMemberUpdateRequestVo;
import com.machine.client.crm.customer.ICrmCustomerClient;
import com.machine.client.crm.customer.dto.input.CrmCustomerCreateInputDto;
import com.machine.client.crm.customer.dto.input.CrmCustomerQueryPageInputDto;
import com.machine.client.crm.customer.dto.input.CrmCustomerUpdateInputDto;
import com.machine.client.crm.customer.dto.output.CrmCustomerDetailOutputDto;
import com.machine.client.crm.customer.dto.output.CrmCustomerListOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.model.response.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CrmCustomerBusinessImpl implements ICrmCustomerBusiness {

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private ICrmCustomerClient crmCustomerClient;

    @Override
    public String create(CrmMemberCreateRequestVo request) {
        CrmCustomerCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), CrmCustomerCreateInputDto.class);
        return crmCustomerClient.create(inputDto);
    }

    @Override
    public void delete(IdRequest request) {
        crmCustomerClient.delete(request);
    }

    @Override
    public void update(CrmMemberUpdateRequestVo request) {
        CrmCustomerUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), CrmCustomerUpdateInputDto.class);
        crmCustomerClient.update(inputDto);
    }

    @Override
    public CrmMemberDetailResponseVo detail(IdRequest request) {
        CrmCustomerDetailOutputDto outputDto = crmCustomerClient.detail(request);
        if (null == outputDto) {
            return null;
        }
        CrmMemberDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), CrmMemberDetailResponseVo.class);

        { //填充修改人创建人信息
            Set<String> userIdSet = new HashSet<>();
            userIdSet.add(outputDto.getCreateBy());
            userIdSet.add(outputDto.getUpdateBy());
            Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
            responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());
        }
        return responseVo;
    }

    @Override
    public PageResponse<CrmMemberListResponseVo> pageSimple(CrmCustomerQueryPageRequestVo request) {
        CrmCustomerQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), CrmCustomerQueryPageInputDto.class);
        PageResponse<CrmCustomerListOutputDto> page = crmCustomerClient.selectPage(inputDto);

        if (CollectionUtil.isEmpty(page.getRecords())) {
            return new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal());
        }

        return new PageResponse<>(
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), CrmMemberListResponseVo.class));
    }

    @Override
    public PageResponse<CrmMemberExpandListResponseVo> pageExpand(CrmCustomerQueryPageRequestVo request) {
        //查询分页数据
        CrmCustomerQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), CrmCustomerQueryPageInputDto.class);
        PageResponse<CrmCustomerListOutputDto> pageOutput = crmCustomerClient.selectPage(inputDto);

        if (CollectionUtil.isEmpty(pageOutput.getRecords())) {
            return new PageResponse<>(pageOutput.getCurrent(), pageOutput.getSize(), pageOutput.getTotal());
        }

        //转化为返回数据
        PageResponse<CrmMemberExpandListResponseVo> pageResponse = new PageResponse<>(
                pageOutput.getCurrent(),
                pageOutput.getSize(),
                pageOutput.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutput.getRecords()), CrmMemberExpandListResponseVo.class));


        {//创建人、修改人姓名
            Set<String> userIdSet = pageResponse.getRecords().stream().map(CrmMemberExpandListResponseVo::getCreateBy).collect(Collectors.toSet());
            userIdSet.addAll(pageResponse.getRecords().stream().map(CrmMemberExpandListResponseVo::getUpdateBy).collect(Collectors.toSet()));
            Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            for (CrmMemberExpandListResponseVo vo : pageResponse.getRecords()) {
                vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
                vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
            }
        }
        return pageResponse;
    }
}
