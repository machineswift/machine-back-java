package com.machine.service.scm.attribute.server;

import cn.hutool.json.JSONUtil;
import com.machine.client.scm.attribute.IScmAttributeValueClient;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueCreateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueQueryInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueUpdateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueUpdateCodeInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeValueUpdateStatusInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeValueDetailOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeValueListOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeValueSimpleListOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.service.scm.attribute.service.IScmAttributeValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/scm/attribute/attribute_value")
public class ScmAttributeValueServer implements IScmAttributeValueClient {

    @Autowired
    private IScmAttributeValueService attributeValueService;

    @Override
    @PostMapping("create")
    public String create(@RequestBody @Validated ScmAttributeValueCreateInputDto inputDto) {
        log.info("创建属性值，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return attributeValueService.create(inputDto);
    }

    @Override
    @PostMapping("delete")
    public int deleteById(@RequestBody @Validated IdRequest request) {
        log.info("删除属性值，id={}", request.getId());
        return attributeValueService.deleteById(request);
    }

    @Override
    @PostMapping("update")
    public int update(@RequestBody @Validated ScmAttributeValueUpdateInputDto inputDto) {
        log.info("修改属性值，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return attributeValueService.update(inputDto);
    }

    @Override
    @PostMapping("update_status")
    public int updateStatus(@RequestBody @Validated ScmAttributeValueUpdateStatusInputDto inputDto) {
        log.info("修改属性值状态，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return attributeValueService.updateStatus(inputDto);
    }

    @Override
    @PostMapping("update_code")
    public int updateCode(@RequestBody @Validated ScmAttributeValueUpdateCodeInputDto inputDto) {
        log.info("修改属性值编码，inputDto={}", JSONUtil.toJsonStr(inputDto));
        return attributeValueService.updateCode(inputDto);
    }

    @Override
    @PostMapping("detail")
    public ScmAttributeValueDetailOutputDto getById(@RequestBody @Validated IdRequest request) {
        log.info("获取属性值详情，id={}", request.getId());
        return attributeValueService.getById(request);
    }

    @Override
    @PostMapping("list_simple")
    public List<ScmAttributeValueSimpleListOutputDto> listSimple(@RequestBody @Validated ScmAttributeValueQueryInputDto inputDto) {
        log.info("根据属性ID查询属性值列表（组件弹窗），attributeId={}", inputDto.getAttributeId());
        return attributeValueService.listSimple(inputDto);
    }

    @Override
    @PostMapping("list_expand")
    public List<ScmAttributeValueListOutputDto> listExpand(@RequestBody @Validated ScmAttributeValueQueryInputDto inputDto) {
        log.info("根据属性ID查询属性值列表，attributeId={}", inputDto.getAttributeId());
        return attributeValueService.listExpand(inputDto);
    }
}