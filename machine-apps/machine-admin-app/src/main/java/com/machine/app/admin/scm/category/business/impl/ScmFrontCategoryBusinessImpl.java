package com.machine.app.admin.scm.category.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.admin.scm.category.business.IScmFrontCategoryBusiness;
import com.machine.app.admin.scm.category.controller.vo.request.ScmFrontCategoryCreateRequestVo;
import com.machine.app.admin.scm.category.controller.vo.request.ScmFrontCategoryUpdateBackCategoryRequestVo;
import com.machine.app.admin.scm.category.controller.vo.request.ScmFrontCategoryUpdateParentRequestVo;
import com.machine.app.admin.scm.category.controller.vo.request.ScmFrontCategoryUpdateRequestVo;
import com.machine.app.admin.scm.category.controller.vo.response.ScmFrontCategoryDetailResponseVo;
import com.machine.app.admin.scm.category.controller.vo.response.ScmFrontCategoryTreeExpandResponseVo;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.client.scm.category.IScmBackCategoryFrontRelationClient;
import com.machine.client.scm.category.IScmFrontCategoryClient;
import com.machine.client.scm.category.dto.input.ScmFrontCategoryCreateInputDto;
import com.machine.client.scm.category.dto.input.ScmFrontCategoryUpdateBackCategoryInputDto;
import com.machine.client.scm.category.dto.input.ScmFrontCategoryUpdateInputDto;
import com.machine.client.scm.category.dto.input.ScmFrontCategoryUpdateParentInputDto;
import com.machine.client.scm.category.dto.output.*;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.starter.redis.cache.scm.RedisScmFrontCategoryCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ScmFrontCategoryBusinessImpl implements IScmFrontCategoryBusiness {

    @Autowired
    private RedisScmFrontCategoryCache cacheScmFrontCategory;

    @Autowired
    private IBIamUserClient userClient;

    @Autowired
    private IScmFrontCategoryClient frontCategoryClient;

    @Autowired
    private IScmBackCategoryFrontRelationClient backCategoryFrontRelationClient;


    @Override
    public String create(ScmFrontCategoryCreateRequestVo request) {
        log.info("创建前台分类，request={}", JSONUtil.toJsonStr(request));
        ScmFrontCategoryCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmFrontCategoryCreateInputDto.class);
        return frontCategoryClient.create(inputDto);
    }

    @Override
    public void deleteById(IdRequest request) {
        log.info("删除前台分类，id={}", request.getId());
        frontCategoryClient.deleteById(request);
    }

    @Override
    public void update(ScmFrontCategoryUpdateRequestVo request) {
        log.info("修改前台分类，request={}", JSONUtil.toJsonStr(request));
        ScmFrontCategoryUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmFrontCategoryUpdateInputDto.class);
        frontCategoryClient.update(inputDto);
    }

    @Override
    public void updateParent(ScmFrontCategoryUpdateParentRequestVo request) {
        log.info("修改前台分类父ID，request={}", JSONUtil.toJsonStr(request));
        ScmFrontCategoryUpdateParentInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmFrontCategoryUpdateParentInputDto.class);
        frontCategoryClient.updateParent(inputDto);
    }

    @Override
    public void updateBackCategory(ScmFrontCategoryUpdateBackCategoryRequestVo request) {
        log.info("修改前台分类关联后台分类，request={}", JSONUtil.toJsonStr(request));
        ScmFrontCategoryUpdateBackCategoryInputDto inputDto =
                JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmFrontCategoryUpdateBackCategoryInputDto.class);
        frontCategoryClient.updateBackCategory(inputDto);
    }

    @Override
    public ScmFrontCategoryDetailResponseVo getById(IdRequest request) {
        log.info("获取前台分类详情，id={}", request.getId());
        ScmFrontCategoryDetailOutputDto outputDto = frontCategoryClient.getById(request);

        ScmFrontCategoryDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), ScmFrontCategoryDetailResponseVo.class);

        { // 关联的后台分类数据
            List<ScmBackCategoryFrontRelationListOutputDto> relationListOutputDtoList = backCategoryFrontRelationClient.listByFrontCategoryId(request);
            if (CollectionUtil.isNotEmpty(relationListOutputDtoList)) {
                responseVo.setBackCategoryIdSet(relationListOutputDtoList.stream()
                        .map(ScmBackCategoryFrontRelationListOutputDto::getBackCategoryId).collect(Collectors.toSet()));
            }
        }

        { //填充修改人创建人信息
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
    public ScmFrontCategoryTreeOutputDto treeSimple() {
        return cacheScmFrontCategory.treeSimple();
    }

    @Override
    public ScmFrontCategoryTreeExpandResponseVo treeExpand() {
        //查询全量扁平数据
        List<ScmFrontCategoryListOutputDto> listOutputDtoList = frontCategoryClient.listAll();

        //转换为中间对象并构建树
        List<ScmFrontCategoryTreeExpandResponseVo> expandTreeBoList = JSONUtil.toList(
                JSONUtil.toJsonStr(listOutputDtoList), ScmFrontCategoryTreeExpandResponseVo.class);

        ScmFrontCategoryTreeExpandResponseVo treeExpandOutputDto = TreeUtil.buildTree(expandTreeBoList).getFirst();

        //收集所有节点
        List<ScmFrontCategoryTreeExpandResponseVo> allNodes = TreeUtil.collectAllNodes(treeExpandOutputDto);

        {//填充创建人/修改人姓名
            Set<String> userIdSet = allNodes.stream()
                    .map(ScmFrontCategoryTreeExpandResponseVo::getCreateBy)
                    .collect(Collectors.toSet());
            userIdSet.addAll(allNodes.stream()
                    .map(ScmFrontCategoryTreeExpandResponseVo::getUpdateBy)
                    .collect(Collectors.toSet()));

            Map<String, BIamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));

            for (ScmFrontCategoryTreeExpandResponseVo node : allNodes) {
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

        {//计算前台分类数（后序递归累计子节点）
            calculateFrontCount(treeExpandOutputDto);
        }

        {//计算关联的后台分类数（后序递归累计子节点）
            calculateBackCount(treeExpandOutputDto);
        }

        return treeExpandOutputDto;
    }

    /**
     * 计算前台节点数量（后序遍历并统计子节点数量）
     */
    private void calculateFrontCount(ScmFrontCategoryTreeExpandResponseVo node) {
        if (CollectionUtil.isEmpty(node.getChildren())) {
            node.setFrontCategoryNumber(0);
            return;
        }

        int count = 0;
        for (ScmFrontCategoryTreeExpandResponseVo child : node.getChildren()) {
            calculateFrontCount(child);
            //子节点的 organizationNumber 已经是其子节点的数量，+1 算上子节点自身
            count += child.getFrontCategoryNumber() + 1;
        }
        node.setFrontCategoryNumber(count);
    }

    /**
     * 计算后台节点数量
     */
    private void calculateBackCount(ScmFrontCategoryTreeExpandResponseVo allTree) {
        List<ScmBackCategoryFrontRelationListOutputDto> relationList = backCategoryFrontRelationClient.listAll();

        if (CollectionUtil.isEmpty(relationList)) {
            return;
        }

        Map<String, Set<String>> frontBackIdMap = new HashMap<>();
        for (ScmBackCategoryFrontRelationListOutputDto relation : relationList) {
            String frontCategoryId = relation.getFrontCategoryId();
            String backCategoryId = relation.getBackCategoryId();

            Set<String> backIdSet = frontBackIdMap.computeIfAbsent(frontCategoryId, k -> new HashSet<>());
            backIdSet.add(backCategoryId);
        }

        for (String frontCategoryId : frontBackIdMap.keySet()) {
            Set<String> backCategoryIdSet = frontBackIdMap.get(frontCategoryId);
            ScmFrontCategoryTreeExpandResponseVo targetNode = TreeUtil.findNode(allTree, frontCategoryId);
            targetNode.setBackCategoryNumber(backCategoryIdSet.size());
        }
    }

}