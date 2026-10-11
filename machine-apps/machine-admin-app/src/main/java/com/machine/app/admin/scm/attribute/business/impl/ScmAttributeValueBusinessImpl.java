package com.machine.app.admin.scm.attribute.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.admin.scm.attribute.business.IScmAttributeValueBusiness;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeValueCreateRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeValueListByAttributeRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeValueUpdateRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeValueUpdateCodeRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeValueUpdateStatusRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeValueListResponseVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeValueSimpleListResponseVo;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.client.scm.attribute.IScmAttributeValueClient;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueCreateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueQueryInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueUpdateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueUpdateCodeInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueUpdateStatusInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeValueListOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeValueSimpleListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class ScmAttributeValueBusinessImpl implements IScmAttributeValueBusiness {

    @Autowired
    private IBIamUserClient userClient;

    @Autowired
    private IScmAttributeValueClient attributeValueClient;

    @Override
    public String create(ScmAttributeValueCreateRequestVo request) {
        log.info("创建属性值，request={}", JSONUtil.toJsonStr(request));
        ScmAttributeValueCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmAttributeValueCreateInputDto.class);
        return attributeValueClient.create(inputDto);
    }

    @Override
    public void deleteById(IdRequest request) {
        log.info("删除属性值，id={}", request.getId());
        attributeValueClient.deleteById(request);
    }

    @Override
    public void update(ScmAttributeValueUpdateRequestVo request) {
        log.info("修改属性值，request={}", JSONUtil.toJsonStr(request));
        ScmAttributeValueUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmAttributeValueUpdateInputDto.class);
        attributeValueClient.update(inputDto);
    }

    @Override
    public void updateStatus(ScmAttributeValueUpdateStatusRequestVo request) {
        log.info("修改属性值状态，request={}", JSONUtil.toJsonStr(request));
        ScmAttributeValueUpdateStatusInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmAttributeValueUpdateStatusInputDto.class);
        attributeValueClient.updateStatus(inputDto);
    }

    @Override
    public void updateCode(ScmAttributeValueUpdateCodeRequestVo request) {
        log.info("修改属性值编码，request={}", JSONUtil.toJsonStr(request));
        ScmAttributeValueUpdateCodeInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmAttributeValueUpdateCodeInputDto.class);
        attributeValueClient.updateCode(inputDto);
    }

    @Override
    public List<ScmAttributeValueSimpleListResponseVo> listSimple(ScmAttributeValueListByAttributeRequestVo request) {
        log.info("根据属性ID查询属性值列表（组件弹窗），attributeId={}", request.getAttributeId());
        ScmAttributeValueQueryInputDto inputDto = new ScmAttributeValueQueryInputDto();
        inputDto.setAttributeId(request.getAttributeId());
        List<ScmAttributeValueSimpleListOutputDto> outputDtoList = attributeValueClient.listSimple(inputDto);
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(outputDtoList), ScmAttributeValueSimpleListResponseVo.class);
    }

    @Override
    public List<ScmAttributeValueListResponseVo> listExpand(ScmAttributeValueListByAttributeRequestVo request) {
        log.info("根据属性ID查询属性值列表，attributeId={}", request.getAttributeId());
        ScmAttributeValueQueryInputDto inputDto = new ScmAttributeValueQueryInputDto();
        inputDto.setAttributeId(request.getAttributeId());
        List<ScmAttributeValueListOutputDto> outputDtoList = attributeValueClient.listExpand(inputDto);
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return List.of();
        }
        List<ScmAttributeValueListResponseVo> records = JSONUtil.toList(
                JSONUtil.toJsonStr(outputDtoList), ScmAttributeValueListResponseVo.class);
        fillUserName(records);
        return records;
    }

    private void fillUserName(List<ScmAttributeValueListResponseVo> records) {
        Set<String> userIdSet = new HashSet<>();
        for (ScmAttributeValueListResponseVo record : records) {
            userIdSet.add(record.getCreateBy());
            userIdSet.add(record.getUpdateBy());
        }
        Map<String, BIamUserDetailOutputDto> userMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        for (ScmAttributeValueListResponseVo record : records) {
            record.setCreateName(userMap.get(record.getCreateBy()).getName());
            record.setUpdateName(userMap.get(record.getUpdateBy()).getName());
        }
    }
}
