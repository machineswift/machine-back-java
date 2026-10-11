package com.machine.app.admin.scm.attribute.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.admin.scm.attribute.business.IScmAttributeBusiness;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeCreateRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeQueryPageRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeUpdateAttributeGroupRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeUpdateBackCategoryRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeUpdateCodeRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.request.ScmAttributeUpdateRequestVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeDetailResponseVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeListResponseVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeSimpleListResponseVo;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.client.scm.attribute.IScmAttributeClient;
import com.machine.client.scm.attribute.IScmAttributeGroupClient;
import com.machine.client.scm.attribute.IScmAttributeGroupAttributeRelationClient;
import com.machine.client.scm.attribute.IScmBackCategoryAttributeRelationClient;
import com.machine.client.scm.attribute.dto.ScmAttributeBackCategoryItemDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeCreateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeQueryPageInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeUpdateAttributeGroupInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeUpdateBackCategoryInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeUpdateCodeInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeUpdateInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeDetailOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupItemOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupAttributeRelationDetailOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupSimpleListOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeListOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmBackCategoryAttributeRelationDetailOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryTreeSimpleOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.starter.redis.cache.scm.RedisScmBackCategoryCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ScmAttributeBusinessImpl implements IScmAttributeBusiness {

    @Autowired
    private IBIamUserClient userClient;

    @Autowired
    private IScmAttributeClient attributeClient;

    @Autowired
    private IScmAttributeGroupAttributeRelationClient attributeGroupAttributeRelationClient;

    @Autowired
    private IScmAttributeGroupClient attributeGroupClient;

    @Autowired
    private IScmBackCategoryAttributeRelationClient backCategoryAttributeRelationClient;

    @Autowired
    private RedisScmBackCategoryCache cacheScmBackCategory;

    @Override
    public String create(ScmAttributeCreateRequestVo request) {
        log.info("创建属性，request={}", JSONUtil.toJsonStr(request));
        ScmAttributeCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmAttributeCreateInputDto.class);
        return attributeClient.create(inputDto);
    }

    @Override
    public void deleteById(IdRequest request) {
        log.info("删除属性，id={}", request.getId());
        attributeClient.deleteById(request);
    }

    @Override
    public void update(ScmAttributeUpdateRequestVo request) {
        log.info("修改属性，request={}", JSONUtil.toJsonStr(request));
        ScmAttributeUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmAttributeUpdateInputDto.class);
        attributeClient.update(inputDto);
    }

    @Override
    public void updateCode(ScmAttributeUpdateCodeRequestVo request) {
        log.info("修改属性编码，request={}", JSONUtil.toJsonStr(request));
        ScmAttributeUpdateCodeInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmAttributeUpdateCodeInputDto.class);
        attributeClient.updateCode(inputDto);
    }

    @Override
    public void updateAttributeGroup(ScmAttributeUpdateAttributeGroupRequestVo request) {
        log.info("修改属性关联的属性分组，request={}", JSONUtil.toJsonStr(request));
        ScmAttributeUpdateAttributeGroupInputDto inputDto =
                JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmAttributeUpdateAttributeGroupInputDto.class);
        attributeClient.updateAttributeGroup(inputDto);
    }

    @Override
    public void updateBackCategory(ScmAttributeUpdateBackCategoryRequestVo request) {
        log.info("修改属性关联后台分类，request={}", JSONUtil.toJsonStr(request));
        ScmAttributeUpdateBackCategoryInputDto inputDto = new ScmAttributeUpdateBackCategoryInputDto();
        inputDto.setId(request.getId());
        inputDto.setBackCategoryItemList(
                JSONUtil.toList(JSONUtil.toJsonStr(request.getBackCategoryItemList()),
                        ScmAttributeBackCategoryItemDto.class));
        attributeClient.updateBackCategory(inputDto);
    }

    @Override
    public ScmAttributeDetailResponseVo getById(IdRequest request) {
        log.info("获取属性详情，id={}", request.getId());
        ScmAttributeDetailOutputDto outputDto = attributeClient.getById(request);
        if (outputDto == null) {
            return null;
        }
        ScmAttributeDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), ScmAttributeDetailResponseVo.class);
        // 关联的属性分组列表
        responseVo.setAttributeGroupItemList(listAttributeGroupItem(request));
        // 关联的后台分类列表
        responseVo.setBackCategoryList(listBackCategoryItem(request));

        // 填充用户信息
        fillUserName(responseVo);
        return responseVo;
    }


    @Override
    public PageResponse<ScmAttributeSimpleListResponseVo> pageSimple(ScmAttributeQueryPageRequestVo request) {
        ScmAttributeQueryPageInputDto pageInputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmAttributeQueryPageInputDto.class);
        PageResponse<ScmAttributeListOutputDto> pageOutputDto = attributeClient.selectPage(pageInputDto);
        if (CollectionUtil.isEmpty(pageOutputDto.getRecords())) {
            return new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal());
        }
        List<ScmAttributeSimpleListResponseVo> records = pageOutputDto.getRecords().stream()
                .map(item -> JSONUtil.toBean(JSONUtil.toJsonStr(item), ScmAttributeSimpleListResponseVo.class))
                .collect(Collectors.toList());
        return new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal(), records);
    }

    @Override
    public PageResponse<ScmAttributeListResponseVo> pageExpand(ScmAttributeQueryPageRequestVo request) {
        ScmAttributeQueryPageInputDto pageInputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmAttributeQueryPageInputDto.class);
        PageResponse<ScmAttributeListOutputDto> pageOutputDto = attributeClient.selectPage(pageInputDto);
        if (CollectionUtil.isEmpty(pageOutputDto.getRecords())) {
            return new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal());
        }
        List<ScmAttributeListResponseVo> records = JSONUtil.toList(
                JSONUtil.toJsonStr(pageOutputDto.getRecords()), ScmAttributeListResponseVo.class);
        // 填充关联的属性分组数
        fillAttributeGroupCount(records);
        // 填充关联的后台分类数
        fillBackCategoryCount(records);
        fillUserName(records);
        return new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal(), records);
    }

    private void fillBackCategoryCount(List<ScmAttributeListResponseVo> records) {
        Set<String> attributeIdSet = records.stream()
                .map(ScmAttributeListResponseVo::getId)
                .collect(Collectors.toSet());
        Map<String, Integer> countMap =
                backCategoryAttributeRelationClient.mapBackCategoryCountByAttributeIdSet(new IdSetRequest(attributeIdSet));
        if (null == countMap) {
            countMap = Map.of();
        }
        for (ScmAttributeListResponseVo record : records) {
            record.setBackCategoryNumber(countMap.getOrDefault(record.getId(), 0));
        }
    }

    private List<ScmAttributeDetailResponseVo.BackCategoryItem> listBackCategoryItem(IdRequest request) {
        List<ScmBackCategoryAttributeRelationDetailOutputDto> relationList =
                backCategoryAttributeRelationClient.listByAttributeId(request);
        if (CollectionUtil.isEmpty(relationList)) {
            return List.of();
        }

        List<ScmAttributeDetailResponseVo.BackCategoryItem> itemList = relationList.stream()
                .map(relation -> JSONUtil.toBean(JSONUtil.toJsonStr(relation),
                        ScmAttributeDetailResponseVo.BackCategoryItem.class))
                .collect(Collectors.toList());
        fillBackCategoryItemInfo(itemList);
        return itemList;
    }

    private void fillBackCategoryItemInfo(List<ScmAttributeDetailResponseVo.BackCategoryItem> itemList) {
        if (CollectionUtil.isEmpty(itemList)) {
            return;
        }
        Set<String> backCategoryIdSet = new HashSet<>();
        for (ScmAttributeDetailResponseVo.BackCategoryItem item : itemList) {
            if (StrUtil.isNotBlank(item.getBackCategoryId())) {
                backCategoryIdSet.add(item.getBackCategoryId());
            }
        }
        if (CollectionUtil.isEmpty(backCategoryIdSet)) {
            return;
        }

        ScmBackCategoryTreeSimpleOutputDto tree = cacheScmBackCategory.treeSimple();
        if (null == tree) {
            return;
        }

        Map<String, List<ScmBackCategoryTreeSimpleOutputDto>> nodeListMap =
                TreeUtil.getParentNodeListMap(tree, backCategoryIdSet);
        for (ScmAttributeDetailResponseVo.BackCategoryItem item : itemList) {
            List<ScmBackCategoryTreeSimpleOutputDto> nodeList = nodeListMap.get(item.getBackCategoryId());
            item.setBackCategoryFullName(joinBackCategoryName(nodeList));
            item.setBackCategoryCode(CollectionUtil.isEmpty(nodeList) ? null : nodeList.getLast().getCode());
        }
    }

    private String joinBackCategoryName(List<ScmBackCategoryTreeSimpleOutputDto> nodeList) {
        if (CollectionUtil.isEmpty(nodeList)) {
            return null;
        }
        return nodeList.stream()
                .map(ScmBackCategoryTreeSimpleOutputDto::getName)
                .collect(Collectors.joining("-"));
    }

    private void fillAttributeGroupCount(List<ScmAttributeListResponseVo> records) {
        Set<String> attributeIdSet = records.stream()
                .map(ScmAttributeListResponseVo::getId)
                .collect(Collectors.toSet());
        Map<String, Integer> countMap =
                attributeGroupClient.mapAttributeGroupCountByAttributeIdSet(new IdSetRequest(attributeIdSet));
        if (null == countMap) {
            countMap = Map.of();
        }
        for (ScmAttributeListResponseVo record : records) {
            record.setAttributeGroupNumber(countMap.getOrDefault(record.getId(), 0));
        }
    }

    private void fillUserName(ScmAttributeDetailResponseVo responseVo) {
        Set<String> userIdSet = new HashSet<>();
        userIdSet.add(responseVo.getCreateBy());
        userIdSet.add(responseVo.getUpdateBy());
        Map<String, BIamUserDetailOutputDto> userMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        responseVo.setCreateName(userMap.get(responseVo.getCreateBy()).getName());
        responseVo.setUpdateName(userMap.get(responseVo.getUpdateBy()).getName());
    }

    private void fillUserName(List<ScmAttributeListResponseVo> records) {
        Set<String> userIdSet = new HashSet<>();
        for (ScmAttributeListResponseVo record : records) {
            userIdSet.add(record.getCreateBy());
            userIdSet.add(record.getUpdateBy());
        }
        Map<String, BIamUserDetailOutputDto> userMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        for (ScmAttributeListResponseVo record : records) {
            record.setCreateName(userMap.get(record.getCreateBy()).getName());
            record.setUpdateName(userMap.get(record.getUpdateBy()).getName());
        }
    }

    private List<ScmAttributeGroupItemOutputDto> listAttributeGroupItem(IdRequest request) {
        List<ScmAttributeGroupAttributeRelationDetailOutputDto> relationList =
                attributeGroupAttributeRelationClient.listByAttributeId(request);
        if (CollectionUtil.isEmpty(relationList)) {
            return List.of();
        }
        Set<String> groupIdSet = relationList.stream()
                .map(ScmAttributeGroupAttributeRelationDetailOutputDto::getGroupId)
                .collect(Collectors.toSet());
        Map<String, ScmAttributeGroupSimpleListOutputDto> groupMap =
                attributeGroupClient.listByIdSet(new IdSetRequest(groupIdSet)).stream()
                        .collect(Collectors.toMap(ScmAttributeGroupSimpleListOutputDto::getId, group -> group, (a, b) -> a));

        List<ScmAttributeGroupItemOutputDto> itemList = new ArrayList<>(relationList.size());
        for (ScmAttributeGroupAttributeRelationDetailOutputDto relation : relationList) {
            ScmAttributeGroupItemOutputDto item =
                    JSONUtil.toBean(JSONUtil.toJsonStr(relation), ScmAttributeGroupItemOutputDto.class);
            ScmAttributeGroupSimpleListOutputDto group = groupMap.get(relation.getGroupId());
            if (null != group) {
                item.setGroupName(group.getName());
                item.setGroupCode(group.getCode());
            }
            itemList.add(item);
        }
        return itemList;
    }

}
