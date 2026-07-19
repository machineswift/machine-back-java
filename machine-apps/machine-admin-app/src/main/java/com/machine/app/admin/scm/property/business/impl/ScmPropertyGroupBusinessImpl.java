package com.machine.app.admin.scm.property.business.impl;

import cn.hutool.json.JSONUtil;
import com.machine.app.admin.scm.property.business.IScmPropertyGroupBusiness;
import com.machine.app.admin.scm.property.controller.vo.request.ScmPropertyGroupCreateRequestVo;
import com.machine.app.admin.scm.property.controller.vo.request.ScmPropertyGroupListByBackCategoryRequestVo;
import com.machine.app.admin.scm.property.controller.vo.request.ScmPropertyGroupUpdateRequestVo;
import com.machine.app.admin.scm.property.controller.vo.request.ScmPropertyGroupUpdateSortRequestVo;
import com.machine.app.admin.scm.property.controller.vo.response.ScmPropertyGroupDetailResponseVo;
import com.machine.app.admin.scm.property.controller.vo.response.ScmPropertyGroupListResponseVo;
import com.machine.client.scm.property.IScmPropertyGroupClient;
import com.machine.client.scm.property.dto.input.ScmPropertyGroupCreateInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyGroupQueryInputDto;
import com.machine.client.scm.property.dto.input.ScmPropertyGroupUpdateInputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyGroupDetailOutputDto;
import com.machine.client.scm.property.dto.output.ScmPropertyGroupListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ScmPropertyGroupBusinessImpl implements IScmPropertyGroupBusiness {

    @Autowired
    private IScmPropertyGroupClient propertyGroupClient;

    @Override
    public String create(ScmPropertyGroupCreateRequestVo request) {
        log.info("创建属性分组，request={}", JSONUtil.toJsonStr(request));
        ScmPropertyGroupCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmPropertyGroupCreateInputDto.class);
        return propertyGroupClient.create(inputDto);
    }

    @Override
    public void update(ScmPropertyGroupUpdateRequestVo request) {
        log.info("修改属性分组，request={}", JSONUtil.toJsonStr(request));
        ScmPropertyGroupUpdateInputDto inputDto = new ScmPropertyGroupUpdateInputDto();
        inputDto.setId(request.getId());
        inputDto.setName(request.getName());
        inputDto.setSort(request.getSort());
        propertyGroupClient.update(inputDto);
    }

    @Override
    public void deleteById(IdRequest request) {
        log.info("删除属性分组，id={}", request.getId());
        propertyGroupClient.deleteById(request);
    }

    @Override
    public ScmPropertyGroupDetailResponseVo getById(IdRequest request) {
        log.info("获取属性分组详情，id={}", request.getId());
        ScmPropertyGroupDetailOutputDto outputDto = propertyGroupClient.getById(request);
        if (outputDto == null) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), ScmPropertyGroupDetailResponseVo.class);
    }

    @Override
    public void updateSort(ScmPropertyGroupUpdateSortRequestVo request) {
        log.info("修改属性分组排序，request={}", JSONUtil.toJsonStr(request));
        ScmPropertyGroupDetailOutputDto detail = propertyGroupClient.getById(new IdRequest(request.getId()));
        ScmPropertyGroupUpdateInputDto inputDto = new ScmPropertyGroupUpdateInputDto();
        inputDto.setId(request.getId());
        inputDto.setName(detail.getName());
        inputDto.setSort(request.getSort());
        propertyGroupClient.update(inputDto);
    }

    @Override
    public List<ScmPropertyGroupListResponseVo> listByBackCategoryId(ScmPropertyGroupListByBackCategoryRequestVo request) {
        log.info("根据后台分类ID获取属性分组列表，backCategoryId={}", request.getBackCategoryId());
        ScmPropertyGroupQueryInputDto inputDto = new ScmPropertyGroupQueryInputDto();
        inputDto.setBackCategoryId(request.getBackCategoryId());
        List<ScmPropertyGroupListOutputDto> outputDtoList = propertyGroupClient.listByBackCategoryId(inputDto);
        return outputDtoList.stream()
                .map(dto -> JSONUtil.toBean(JSONUtil.toJsonStr(dto), ScmPropertyGroupListResponseVo.class))
                .collect(Collectors.toList());
    }
}
