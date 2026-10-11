package com.machine.app.admin.scm.attribute.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.admin.scm.attribute.business.IScmAttributeGroupBusiness;
import com.machine.app.admin.scm.attribute.controller.vo.request.*;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeGroupDetailResponseVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeGroupListResponseVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeGroupRelationListResponseVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeGroupSimpleListResponseVo;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryTreeSimpleOutputDto;
import com.machine.client.scm.attribute.IScmAttributeClient;
import com.machine.client.scm.attribute.IScmAttributeGroupClient;
import com.machine.client.scm.attribute.IScmAttributeGroupAttributeRelationClient;
import com.machine.client.scm.attribute.dto.ScmAttributeGroupBackCategoryItemDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupCreateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupQueryListInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupQueryPageInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateAttributeInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateBackCategoryInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateCodeInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateInputDto;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupUpdateStatusInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupDetailOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupListOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupItemOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupAttributeRelationDetailOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupSimpleListOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeListOutputDto;
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
public class ScmAttributeGroupBusinessImpl implements IScmAttributeGroupBusiness {

    @Autowired
    private IBIamUserClient userClient;

    @Autowired
    private RedisScmBackCategoryCache cacheScmBackCategory;

    @Autowired
    private IScmAttributeGroupClient attributeGroupClient;

    @Autowired
    private IScmAttributeGroupAttributeRelationClient attributeGroupAttributeRelationClient;

    @Autowired
    private IScmAttributeClient attributeClient;

    @Override
    public String create(ScmAttributeGroupCreateRequestVo request) {
        ScmAttributeGroupCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmAttributeGroupCreateInputDto.class);
        return attributeGroupClient.create(inputDto);
    }

    @Override
    public void deleteById(IdRequest request) {
        attributeGroupClient.deleteById(request);
    }

    @Override
    public void update(ScmAttributeGroupUpdateRequestVo request) {
        ScmAttributeGroupUpdateInputDto inputDto =
                JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmAttributeGroupUpdateInputDto.class);
        attributeGroupClient.update(inputDto);
    }

    @Override
    public void updateStatus(ScmAttributeGroupUpdateStatusRequestVo request) {
        ScmAttributeGroupUpdateStatusInputDto inputDto =
                JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmAttributeGroupUpdateStatusInputDto.class);
        attributeGroupClient.updateStatus(inputDto);
    }

    @Override
    public void updateCode(ScmAttributeGroupUpdateCodeRequestVo request) {
        ScmAttributeGroupUpdateCodeInputDto inputDto =
                JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmAttributeGroupUpdateCodeInputDto.class);
        attributeGroupClient.updateCode(inputDto);
    }

    @Override
    public void updateBackCategory(ScmAttributeGroupUpdateBackCategoryRequestVo request) {
        ScmAttributeGroupUpdateBackCategoryInputDto inputDto = new ScmAttributeGroupUpdateBackCategoryInputDto();
        inputDto.setId(request.getId());
        inputDto.setBackCategoryItemList(
                JSONUtil.toList(JSONUtil.toJsonStr(request.getBackCategoryItemList()),
                        ScmAttributeGroupBackCategoryItemDto.class));
        attributeGroupClient.updateBackCategory(inputDto);
    }

    @Override
    public void updateAttribute(ScmAttributeGroupUpdateAttributeRequestVo request) {
        ScmAttributeGroupUpdateAttributeInputDto inputDto =
                JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmAttributeGroupUpdateAttributeInputDto.class);
        attributeGroupClient.updateAttribute(inputDto);
    }

    @Override
    public ScmAttributeGroupDetailResponseVo getById(IdRequest request) {
        ScmAttributeGroupDetailOutputDto outputDto = attributeGroupClient.getById(request);
        if (outputDto == null) {
            return null;
        }
        ScmAttributeGroupDetailResponseVo responseVo =
                JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), ScmAttributeGroupDetailResponseVo.class);

        // 关联的后台分类列表（client 详情不再组装，由业务层拼）
        responseVo.setBackCategoryItemList(JSONUtil.toList(
                JSONUtil.toJsonStr(attributeGroupClient.listBackCategoryByGroupId(request)),
                ScmAttributeGroupDetailResponseVo.BackCategoryItem.class));
        fillBackCategoryItemFullName(responseVo.getBackCategoryItemList());

        // 关联的属性列表（关联关系模型 + 属性模型，由业务层组装）
        responseVo.setAttributeItemList(listAttributeItem(responseVo.getId()));

        { // 填充用户信息
            Set<String> userIdSet = new HashSet<>();
            userIdSet.add(responseVo.getCreateBy());
            userIdSet.add(responseVo.getUpdateBy());
            Map<String, BIamUserDetailOutputDto> userMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            responseVo.setCreateName(getUserName(userMap, responseVo.getCreateBy()));
            responseVo.setUpdateName(getUserName(userMap, responseVo.getUpdateBy()));
        }
        return responseVo;
    }

    @Override
    public List<ScmAttributeGroupRelationListResponseVo> listSimple(ScmAttributeGroupQueryListRequestVo request) {
        ScmAttributeGroupQueryListInputDto inputDto =
                JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmAttributeGroupQueryListInputDto.class);
        List<ScmAttributeGroupSimpleListOutputDto> outputDtoList = attributeGroupClient.listSimple(inputDto);
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return List.of();
        }

        // 组装：分组基础信息 + 分组在当前后台分类下的关联配置
        Map<String, ScmAttributeGroupItemOutputDto> relationMap = mapRelationByGroupId(request.getBackCategoryId());

        List<ScmAttributeGroupRelationListResponseVo> responseList = new ArrayList<>(outputDtoList.size());
        for (ScmAttributeGroupSimpleListOutputDto outputDto : outputDtoList) {
            ScmAttributeGroupRelationListResponseVo responseVo =
                    JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), ScmAttributeGroupRelationListResponseVo.class);
            ScmAttributeGroupItemOutputDto relation = relationMap.get(responseVo.getId());
            if (null != relation) {
                responseVo.setSort(relation.getSort());
                responseVo.setRequiredFlag(relation.getRequiredFlag());
                responseVo.setDisplayFlag(relation.getDisplayFlag());
                responseVo.setSearchableFlag(relation.getSearchableFlag());
                responseVo.setFeatures(relation.getFeatures());
            }
            responseList.add(responseVo);
        }
        return responseList;
    }

    @Override
    public PageResponse<ScmAttributeGroupSimpleListResponseVo> pageSimple(ScmAttributeGroupQueryPageRequestVo request) {
        ScmAttributeGroupQueryPageInputDto pageInputDto =
                JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmAttributeGroupQueryPageInputDto.class);
        // 与 pageExpand 共用底层分页查询，仅按组件弹窗所需字段返回
        PageResponse<ScmAttributeGroupListOutputDto> pageOutputDto = attributeGroupClient.pageExpand(pageInputDto);
        if (CollectionUtil.isEmpty(pageOutputDto.getRecords())) {
            return new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal());
        }
        List<ScmAttributeGroupSimpleListResponseVo> records = pageOutputDto.getRecords().stream()
                .map(record -> JSONUtil.toBean(JSONUtil.toJsonStr(record), ScmAttributeGroupSimpleListResponseVo.class))
                .collect(Collectors.toList());
        return new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal(), records);
    }

    @Override
    public PageResponse<ScmAttributeGroupListResponseVo> pageExpand(ScmAttributeGroupQueryPageRequestVo request) {
        ScmAttributeGroupQueryPageInputDto pageInputDto =
                JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmAttributeGroupQueryPageInputDto.class);
        PageResponse<ScmAttributeGroupListOutputDto> pageOutputDto = attributeGroupClient.pageExpand(pageInputDto);
        if (CollectionUtil.isEmpty(pageOutputDto.getRecords())) {
            return new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal());
        }
        List<ScmAttributeGroupListResponseVo> records =
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutputDto.getRecords()), ScmAttributeGroupListResponseVo.class);

        // 填充关联的后台分类数量
        fillBackCategoryCount(records);

        // 填充关联的属性数量
        fillAttributeCount(records);

        { // 填充用户信息
            Set<String> userIdSet = new HashSet<>();
            for (ScmAttributeGroupListResponseVo record : records) {
                userIdSet.add(record.getCreateBy());
                userIdSet.add(record.getUpdateBy());
            }
            Map<String, BIamUserDetailOutputDto> userMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            for (ScmAttributeGroupListResponseVo record : records) {
                record.setCreateName(getUserName(userMap, record.getCreateBy()));
                record.setUpdateName(getUserName(userMap, record.getUpdateBy()));
            }
        }

        return new PageResponse<>(pageOutputDto.getCurrent(), pageOutputDto.getSize(), pageOutputDto.getTotal(), records);
    }

    private void fillBackCategoryItemFullName(List<ScmAttributeGroupDetailResponseVo.BackCategoryItem> itemList) {
        if (CollectionUtil.isEmpty(itemList)) {
            return;
        }
        Set<String> backCategoryIdSet = new HashSet<>();
        for (ScmAttributeGroupDetailResponseVo.BackCategoryItem item : itemList) {
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
        for (ScmAttributeGroupDetailResponseVo.BackCategoryItem item : itemList) {
            item.setBackCategoryFullName(joinBackCategoryName(nodeListMap.get(item.getBackCategoryId())));
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

    private List<ScmAttributeGroupDetailResponseVo.AttributeItem> listAttributeItem(String groupId) {
        List<ScmAttributeGroupAttributeRelationDetailOutputDto> relationList =
                attributeGroupAttributeRelationClient.listByGroupId(new IdRequest(groupId));
        if (CollectionUtil.isEmpty(relationList)) {
            return List.of();
        }
        Set<String> attributeIdSet = relationList.stream()
                .map(ScmAttributeGroupAttributeRelationDetailOutputDto::getAttributeId)
                .collect(Collectors.toSet());
        Map<String, ScmAttributeListOutputDto> attributeMap = attributeClient.listByIdSet(new IdSetRequest(attributeIdSet))
                .stream()
                .collect(Collectors.toMap(ScmAttributeListOutputDto::getId, attribute -> attribute, (a, b) -> a));

        List<ScmAttributeGroupDetailResponseVo.AttributeItem> itemList = new ArrayList<>(relationList.size());
        for (ScmAttributeGroupAttributeRelationDetailOutputDto relation : relationList) {
            ScmAttributeGroupDetailResponseVo.AttributeItem item =
                    JSONUtil.toBean(JSONUtil.toJsonStr(relation), ScmAttributeGroupDetailResponseVo.AttributeItem.class);
            ScmAttributeListOutputDto attribute = attributeMap.get(relation.getAttributeId());
            if (null != attribute) {
                item.setCode(attribute.getCode());
                item.setName(attribute.getName());
                item.setAttributeType(attribute.getAttributeType());
                item.setInputType(attribute.getInputType());
            }
            itemList.add(item);
        }
        return itemList;
    }

    private Map<String, ScmAttributeGroupItemOutputDto> mapRelationByGroupId(String backCategoryId) {
        if (StrUtil.isBlank(backCategoryId)) {
            return Map.of();
        }
        Map<String, List<ScmAttributeGroupItemOutputDto>> relationMap =
                attributeGroupClient.mapAttributeGroupByBackCategoryIdSet(new IdSetRequest(Set.of(backCategoryId)));
        if (null == relationMap) {
            return Map.of();
        }
        return relationMap.getOrDefault(backCategoryId, List.of()).stream()
                .collect(Collectors.toMap(ScmAttributeGroupItemOutputDto::getGroupId, item -> item, (a, b) -> a));
    }

    private String getUserName(Map<String, BIamUserDetailOutputDto> userMap, String userId) {
        BIamUserDetailOutputDto user = userMap.get(userId);
        return null == user ? null : user.getName();
    }

    private void fillBackCategoryCount(List<ScmAttributeGroupListResponseVo> records) {
        Set<String> groupIdSet = records.stream()
                .map(ScmAttributeGroupListResponseVo::getId)
                .collect(Collectors.toSet());
        Map<String, Integer> countMap = attributeGroupClient.mapBackCategoryCountByGroupIdSet(new IdSetRequest(groupIdSet));
        if (null == countMap) {
            countMap = Map.of();
        }
        for (ScmAttributeGroupListResponseVo record : records) {
            record.setBackCategoryCount(countMap.getOrDefault(record.getId(), 0));
        }
    }

    private void fillAttributeCount(List<ScmAttributeGroupListResponseVo> records) {
        Set<String> groupIdSet = records.stream()
                .map(ScmAttributeGroupListResponseVo::getId)
                .collect(Collectors.toSet());
        Map<String, Integer> countMap = attributeGroupClient.mapAttributeCountByGroupIdSet(new IdSetRequest(groupIdSet));
        if (null == countMap) {
            countMap = Map.of();
        }
        for (ScmAttributeGroupListResponseVo record : records) {
            record.setAttributeCount(countMap.getOrDefault(record.getId(), 0));
        }
    }

}
