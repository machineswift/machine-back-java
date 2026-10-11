package com.machine.app.admin.scm.category.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.admin.scm.category.business.IScmBackCategoryBusiness;
import com.machine.app.admin.scm.category.controller.vo.request.ScmBackCategoryCreateRequestVo;
import com.machine.app.admin.scm.category.controller.vo.request.ScmBackCategoryUpdateParentRequestVo;
import com.machine.app.admin.scm.category.controller.vo.request.ScmBackCategoryUpdateAttributeGroupRequestVo;
import com.machine.app.admin.scm.category.controller.vo.request.ScmBackCategoryUpdateAttributeRequestVo;
import com.machine.app.admin.scm.category.controller.vo.request.ScmBackCategoryUpdateRequestVo;
import com.machine.app.admin.scm.category.controller.vo.response.ScmBackCategoryDetailResponseVo;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.client.scm.category.IScmBackCategoryClient;
import com.machine.client.scm.category.IScmBackCategoryFrontRelationClient;
import com.machine.client.scm.category.dto.ScmBackCategoryAttributeGroupItemDto;
import com.machine.client.scm.category.dto.ScmBackCategoryAttributeItemDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryCreateInputDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryUpdateInputDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryUpdateParentInputDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryUpdateAttributeGroupInputDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryUpdateAttributeInputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryDetailOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryListOutputDto;
import com.machine.app.admin.scm.category.controller.vo.response.ScmBackCategoryTreeExpandResponseVo;
import com.machine.app.admin.scm.attribute.controller.vo.response.ScmAttributeGroupRelationListResponseVo;
import com.machine.client.scm.category.dto.output.ScmBackCategoryTreeSimpleOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryFrontRelationListOutputDto;
import com.machine.client.scm.attribute.IScmAttributeClient;
import com.machine.client.scm.attribute.IScmAttributeGroupClient;
import com.machine.client.scm.attribute.IScmBackCategoryAttributeRelationClient;
import com.machine.client.scm.attribute.dto.input.ScmAttributeGroupQueryListInputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupItemOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeGroupSimpleListOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmAttributeListOutputDto;
import com.machine.client.scm.attribute.dto.output.ScmBackCategoryAttributeRelationDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.starter.redis.cache.scm.RedisScmBackCategoryCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ScmBackCategoryBusinessImpl implements IScmBackCategoryBusiness {

    @Autowired
    private RedisScmBackCategoryCache cacheScmBackCategory;

    @Autowired
    private IBIamUserClient userClient;

    @Autowired
    private IScmBackCategoryClient backCategoryClient;

    @Autowired
    private IScmBackCategoryFrontRelationClient backCategoryFrontRelationClient;

    @Autowired
    private IScmAttributeGroupClient attributeGroupClient;

    @Autowired
    private IScmAttributeClient attributeClient;

    @Autowired
    private IScmBackCategoryAttributeRelationClient backCategoryAttributeRelationClient;

    @Override
    public String create(ScmBackCategoryCreateRequestVo request) {
        log.info("创建后台分类，request={}", JSONUtil.toJsonStr(request));
        ScmBackCategoryCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmBackCategoryCreateInputDto.class);
        return backCategoryClient.create(inputDto);
    }

    @Override
    public void deleteById(IdRequest request) {
        log.info("删除后台分类，id={}", request.getId());
        backCategoryClient.deleteById(request);
    }

    @Override
    public void update(ScmBackCategoryUpdateRequestVo request) {
        log.info("修改后台分类，request={}", JSONUtil.toJsonStr(request));
        ScmBackCategoryUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmBackCategoryUpdateInputDto.class);
        backCategoryClient.update(inputDto);
    }

    @Override
    public void updateParent(ScmBackCategoryUpdateParentRequestVo request) {
        log.info("修改后台分类父ID，request={}", JSONUtil.toJsonStr(request));
        ScmBackCategoryUpdateParentInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmBackCategoryUpdateParentInputDto.class);
        backCategoryClient.updateParent(inputDto);
    }

    @Override
    public void updateAttributeGroup(ScmBackCategoryUpdateAttributeGroupRequestVo request) {
        log.info("修改后台分类关联属性分组，request={}", JSONUtil.toJsonStr(request));
        ScmBackCategoryUpdateAttributeGroupInputDto inputDto = new ScmBackCategoryUpdateAttributeGroupInputDto();
        inputDto.setId(request.getId());
        inputDto.setAttributeGroupItemList(
                JSONUtil.toList(JSONUtil.toJsonStr(request.getAttributeGroupItemList()),
                        ScmBackCategoryAttributeGroupItemDto.class));
        backCategoryClient.updateAttributeGroup(inputDto);
    }

    @Override
    public void updateAttribute(ScmBackCategoryUpdateAttributeRequestVo request) {
        log.info("修改后台分类关联属性，request={}", JSONUtil.toJsonStr(request));
        ScmBackCategoryUpdateAttributeInputDto inputDto = new ScmBackCategoryUpdateAttributeInputDto();
        inputDto.setId(request.getId());
        inputDto.setAttributeItemList(
                JSONUtil.toList(JSONUtil.toJsonStr(request.getAttributeItemList()),
                        ScmBackCategoryAttributeItemDto.class));
        backCategoryClient.updateAttribute(inputDto);
    }

    @Override
    public ScmBackCategoryDetailResponseVo getById(IdRequest request) {
        log.info("获取后台分类详情，id={}", request.getId());
        ScmBackCategoryDetailOutputDto outputDto = backCategoryClient.getById(request);

        ScmBackCategoryDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), ScmBackCategoryDetailResponseVo.class);

        {  // 关联的前台分类数据
            List<ScmBackCategoryFrontRelationListOutputDto> relationListOutputDtoList = backCategoryFrontRelationClient.listByBackCategoryId(request);
            if (CollectionUtil.isNotEmpty(relationListOutputDtoList)) {
                responseVo.setFrontCategoryIdSet(relationListOutputDtoList.stream()
                        .map(ScmBackCategoryFrontRelationListOutputDto::getFrontCategoryId).collect(Collectors.toSet()));
            }
        }

        {  // 关联的属性分组数据（分组基础信息 + 分组在当前后台分类下的关联配置）
            ScmAttributeGroupQueryListInputDto queryInputDto = new ScmAttributeGroupQueryListInputDto();
            queryInputDto.setBackCategoryId(request.getId());
            List<ScmAttributeGroupSimpleListOutputDto> attributeGroupList = attributeGroupClient.listSimple(queryInputDto);
            if (CollectionUtil.isNotEmpty(attributeGroupList)) {
                responseVo.setAttributeGroupList(assembleAttributeGroupRelation(request.getId(), attributeGroupList));
            }
        }

        {  // 关联的属性数据
            List<ScmBackCategoryDetailResponseVo.AttributeItem> attributeList = listAttribute(request);
            if (CollectionUtil.isNotEmpty(attributeList)) {
                responseVo.setAttributeList(attributeList);
            }
        }

        {  //填充修改人创建人信息
            Set<String> userIdSet = new HashSet<>();
            userIdSet.add(responseVo.getCreateBy());
            userIdSet.add(responseVo.getUpdateBy());
            Map<String, BIamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));

            responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
            responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());
        }

        return responseVo;
    }

    @Override
    public ScmBackCategoryTreeSimpleOutputDto treeSimple() {
        return cacheScmBackCategory.treeSimple();
    }

    @Override
    public ScmBackCategoryTreeExpandResponseVo treeExpand() {
        //查询全量扁平数据（含创建人/修改人信息）
        List<ScmBackCategoryListOutputDto> listOutputDtoList = backCategoryClient.listAll();

        //转换为中间对象并构建树
        List<ScmBackCategoryTreeExpandResponseVo> expandTreeBoList = JSONUtil.toList(
                JSONUtil.toJsonStr(listOutputDtoList), ScmBackCategoryTreeExpandResponseVo.class);

        ScmBackCategoryTreeExpandResponseVo treeExprandOutputDto = TreeUtil.buildTree(expandTreeBoList).getFirst();

        //收集所有节点
        List<ScmBackCategoryTreeExpandResponseVo> allNodes = TreeUtil.collectAllNodes(treeExprandOutputDto);

        {//填充创建人/修改人姓名
            Set<String> userIdSet = allNodes.stream()
                    .map(ScmBackCategoryTreeExpandResponseVo::getCreateBy)
                    .collect(Collectors.toSet());
            userIdSet.addAll(allNodes.stream()
                    .map(ScmBackCategoryTreeExpandResponseVo::getUpdateBy)
                    .collect(Collectors.toSet()));

            Map<String, BIamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));

            for (ScmBackCategoryTreeExpandResponseVo node : allNodes) {
                BIamUserDetailOutputDto createUser = userSimpleDetailMap.get(node.getCreateBy());
                if (null != createUser) {
                    node.setCreateName(createUser.getName());
                }
                BIamUserDetailOutputDto updateUser = userSimpleDetailMap.get(node.getUpdateBy());
                if (null != updateUser) {
                    node.setUpdateName(updateUser.getName());
                }
            }
        }

        {//计算分类数（后序递归累计子节点）
            postorderTraversalAndCountChildren(treeExprandOutputDto);
        }

        {//计算关联的前台分类数
            calculateFrontCount(treeExprandOutputDto);
        }

        {//计算关联的属性分组数
            calculateAttributeGroupCount(allNodes);
        }

        {//计算关联的属性数量
            calculateAttributeCount(allNodes);
        }

        return treeExprandOutputDto;
    }

    /**
     * 后序遍历并统计子节点数量
     */
    private void postorderTraversalAndCountChildren(ScmBackCategoryTreeExpandResponseVo node) {
        if (CollectionUtil.isEmpty(node.getChildren())) {
            node.setBackCategoryNumber(0);
            return;
        }

        int count = 0;
        for (ScmBackCategoryTreeExpandResponseVo child : node.getChildren()) {
            postorderTraversalAndCountChildren(child);
            //子节点的数量已经是其子节点的数量，+1 算上子节点自身
            count += child.getBackCategoryNumber() + 1;
        }
        node.setBackCategoryNumber(count);
    }

    /**
     * 计算关联的前台分类数
     */
    private void calculateFrontCount(ScmBackCategoryTreeExpandResponseVo allTree) {
        List<ScmBackCategoryFrontRelationListOutputDto> relationList = backCategoryFrontRelationClient.listAll();

        if (CollectionUtil.isEmpty(relationList)) {
            return;
        }

        Map<String, Set<String>> backFrontIdMap = new HashMap<>();
        for (ScmBackCategoryFrontRelationListOutputDto relation : relationList) {
            String backCategoryId = relation.getBackCategoryId();
            String frontCategoryId = relation.getFrontCategoryId();

            Set<String> frontIdSet = backFrontIdMap.computeIfAbsent(backCategoryId, k -> new HashSet<>());
            frontIdSet.add(frontCategoryId);
        }

        for (Map.Entry<String, Set<String>> entry : backFrontIdMap.entrySet()) {
            ScmBackCategoryTreeExpandResponseVo targetNode = TreeUtil.findNode(allTree, entry.getKey());
            if (null == targetNode) {
                continue;
            }
            targetNode.setFrontCategoryNumber(entry.getValue().size());
        }
    }

    /**
     * 计算关联的属性分组数
     */
    private void calculateAttributeGroupCount(List<ScmBackCategoryTreeExpandResponseVo> allNodes) {
        Set<String> backCategoryIdSet = allNodes.stream()
                .map(ScmBackCategoryTreeExpandResponseVo::getId)
                .collect(Collectors.toSet());
        Map<String, Integer> countMap =
                attributeGroupClient.mapAttributeGroupCountByBackCategoryIdSet(new IdSetRequest(backCategoryIdSet));
        if (null == countMap) {
            countMap = Map.of();
        }
        for (ScmBackCategoryTreeExpandResponseVo node : allNodes) {
            node.setAttributeGroupNumber(countMap.getOrDefault(node.getId(), 0));
        }
    }

    /**
     * 计算关联的属性数量
     */
    private void calculateAttributeCount(List<ScmBackCategoryTreeExpandResponseVo> allNodes) {
        Set<String> backCategoryIdSet = allNodes.stream()
                .map(ScmBackCategoryTreeExpandResponseVo::getId)
                .collect(Collectors.toSet());
        Map<String, Integer> countMap =
                backCategoryAttributeRelationClient.mapAttributeCountByBackCategoryIdSet(new IdSetRequest(backCategoryIdSet));
        if (null == countMap) {
            countMap = Map.of();
        }
        for (ScmBackCategoryTreeExpandResponseVo node : allNodes) {
            node.setAttributeCount(countMap.getOrDefault(node.getId(), 0));
        }
    }

    /**
     * 组装属性分组基础信息 + 分组在当前后台分类下的关联配置
     */
    private List<ScmAttributeGroupRelationListResponseVo> assembleAttributeGroupRelation(
            String backCategoryId, List<ScmAttributeGroupSimpleListOutputDto> attributeGroupList) {
        Map<String, ScmAttributeGroupItemOutputDto> relationMap =
                attributeGroupClient.mapAttributeGroupByBackCategoryIdSet(new IdSetRequest(Set.of(backCategoryId)))
                        .getOrDefault(backCategoryId, List.of())
                        .stream()
                        .collect(Collectors.toMap(ScmAttributeGroupItemOutputDto::getGroupId, item -> item, (exist, replace) -> exist));

        List<ScmAttributeGroupRelationListResponseVo> groupList =
                JSONUtil.toList(JSONUtil.toJsonStr(attributeGroupList), ScmAttributeGroupRelationListResponseVo.class);
        for (ScmAttributeGroupRelationListResponseVo group : groupList) {
            ScmAttributeGroupItemOutputDto relation = relationMap.get(group.getId());
            if (null == relation) {
                continue;
            }
            group.setSort(relation.getSort());
            group.setRequiredFlag(relation.getRequiredFlag());
            group.setDisplayFlag(relation.getDisplayFlag());
            group.setSearchableFlag(relation.getSearchableFlag());
            group.setFeatures(relation.getFeatures());
        }
        return groupList;
    }

    /**
     * 查询后台分类关联的属性
     */
    private List<ScmBackCategoryDetailResponseVo.AttributeItem> listAttribute(IdRequest request) {
        List<ScmBackCategoryAttributeRelationDetailOutputDto> relationList =
                backCategoryAttributeRelationClient.listByBackCategoryId(request);
        if (CollectionUtil.isEmpty(relationList)) {
            return List.of();
        }

        Set<String> attributeIdSet = relationList.stream()
                .map(ScmBackCategoryAttributeRelationDetailOutputDto::getAttributeId)
                .collect(Collectors.toSet());
        Map<String, ScmAttributeListOutputDto> attributeMap = attributeClient.listByIdSet(new IdSetRequest(attributeIdSet)).stream()
                .collect(Collectors.toMap(ScmAttributeListOutputDto::getId, attribute -> attribute, (exist, replace) -> exist));

        List<ScmBackCategoryDetailResponseVo.AttributeItem> itemList = new ArrayList<>();
        for (ScmBackCategoryAttributeRelationDetailOutputDto relation : relationList) {
            ScmBackCategoryDetailResponseVo.AttributeItem item = new ScmBackCategoryDetailResponseVo.AttributeItem();
            item.setAttributeId(relation.getAttributeId());
            item.setRequiredFlag(relation.getRequiredFlag());
            item.setDisplayFlag(relation.getDisplayFlag());
            item.setSearchableFlag(relation.getSearchableFlag());
            item.setSort(relation.getSort());
            item.setFeatures(relation.getFeatures());

            ScmAttributeListOutputDto attribute = attributeMap.get(relation.getAttributeId());
            if (null != attribute) {
                item.setCode(attribute.getCode());
                item.setName(attribute.getName());
                item.setAttributeType(attribute.getAttributeType());
                item.setInputType(attribute.getInputType());
            }
            itemList.add(item);
        }

        //sort值大的排序靠前
        itemList.sort(Comparator.comparing(ScmBackCategoryDetailResponseVo.AttributeItem::getSort,
                Comparator.nullsLast(Comparator.reverseOrder())));
        return itemList;
    }

}