package com.machine.app.manage.scm.property.business.impl;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.scm.property.business.IScmPropertyValueBusiness;
import com.machine.app.manage.scm.property.controller.vo.request.ScmPropertyValueCreateRequestVo;
import com.machine.app.manage.scm.property.controller.vo.request.ScmPropertyValueListByPropertyRequestVo;
import com.machine.app.manage.scm.property.controller.vo.request.ScmPropertyValueUpdateRequestVo;
import com.machine.app.manage.scm.property.controller.vo.response.ScmPropertyValueListResponseVo;
import com.machine.client.scm.property.IScmPropertyValueClient;
import com.machine.client.scm.property.dto.input.ScmPropertyValueCreateInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyValueQueryInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyValueUpdateInputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyValueListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ScmPropertyValueBusinessImpl implements IScmPropertyValueBusiness {

    @Autowired
    private IScmPropertyValueClient propertyValueClient;

    @Override
    public String create(ScmPropertyValueCreateRequestVo request) {
        log.info("创建属性值，request={}", JSONUtil.toJsonStr(request));
        ScmPropertyValueCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmPropertyValueCreateInputDto.class);
        return propertyValueClient.create(inputDto);
    }

    @Override
    public void update(ScmPropertyValueUpdateRequestVo request) {
        log.info("修改属性值，request={}", JSONUtil.toJsonStr(request));
        ScmPropertyValueUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmPropertyValueUpdateInputDto.class);
        propertyValueClient.update(inputDto);
    }

    @Override
    public void deleteById(IdRequest request) {
        log.info("删除属性值，id={}", request.getId());
        propertyValueClient.deleteById(request);
    }

    @Override
    public List<ScmPropertyValueListResponseVo> listByPropertyId(ScmPropertyValueListByPropertyRequestVo request) {
        log.info("根据属性ID查询属性值列表，propertyId={}", request.getPropertyId());
        ScmPropertyValueQueryInputDto inputDto = new ScmPropertyValueQueryInputDto();
        inputDto.setPropertyId(request.getPropertyId());
        List<ScmPropertyValueListOutputDto> outputDtoList = propertyValueClient.listByPropertyId(inputDto);
        return outputDtoList.stream()
                .map(dto -> JSONUtil.toBean(JSONUtil.toJsonStr(dto), ScmPropertyValueListResponseVo.class))
                .collect(Collectors.toList());
    }
}
