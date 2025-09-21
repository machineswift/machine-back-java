package com.machine.app.manage.hrm.jobpost.business.impl;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.hrm.jobpost.business.IHrmJobPostBusiness;
import com.machine.app.manage.hrm.jobpost.controller.vo.request.HrmJobPostListSimpleRequestVo;
import com.machine.app.manage.hrm.jobpost.controller.vo.response.HrmJobPostListSimpleResponseVo;
import com.machine.client.hrm.jobpost.IHrmJobPostClient;
import com.machine.client.hrm.jobpost.dto.input.HrmJobPostListSimpleInputDto;
import com.machine.client.hrm.jobpost.dto.output.HrmJobPostListSimpleOutputDto;
import com.machine.sdk.common.model.response.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HrmJobPostBusinessImpl implements IHrmJobPostBusiness {

    @Autowired
    private IHrmJobPostClient jobPostClient;

    @Override
    public PageResponse<HrmJobPostListSimpleResponseVo> pageSimple(HrmJobPostListSimpleRequestVo request) {
        HrmJobPostListSimpleInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), HrmJobPostListSimpleInputDto.class);
        PageResponse<HrmJobPostListSimpleOutputDto> pageOutputDto = jobPostClient.pageSimple(inputDto);
        return new PageResponse<>(
                pageOutputDto.getCurrent(),
                pageOutputDto.getSize(),
                pageOutputDto.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutputDto.getRecords()), HrmJobPostListSimpleResponseVo.class));
    }

}
