package com.machine.app.manage.scm.category.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.manage.scm.category.business.IScmBackCategoryBusiness;
import com.machine.app.manage.scm.category.controller.vo.request.ScmBackCategoryCreateRequestVo;
import com.machine.app.manage.scm.category.controller.vo.request.ScmBackCategoryUpdateParentRequestVo;
import com.machine.app.manage.scm.category.controller.vo.request.ScmBackCategoryUpdateRequestVo;
import com.machine.app.manage.scm.category.controller.vo.response.ScmBackCategoryDetailResponseVo;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.client.scm.category.IScmBackCategoryClient;
import com.machine.client.scm.category.dto.input.ScmBackCategoryCreateInputDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryUpdateInputDto;
import com.machine.client.scm.category.dto.input.ScmBackCategoryUpdateParentInputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryDetailOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryListOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryTreeExprandOutputDto;
import com.machine.client.scm.category.dto.output.ScmBackCategoryTreeSimpleOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.starter.redis.cache.scm.RedisScmBackCategoryCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    private IIamUserClient userClient;

    @Autowired
    private IScmBackCategoryClient backCategoryClient;

    @Override
    public String create(ScmBackCategoryCreateRequestVo request) {
        log.info("创建后台分类，request={}", JSONUtil.toJsonStr(request));
        ScmBackCategoryCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), ScmBackCategoryCreateInputDto.class);
        return backCategoryClient.create(inputDto);
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
    public void deleteById(IdRequest request) {
        log.info("删除后台分类，id={}", request.getId());
        backCategoryClient.deleteById(request);
    }

    @Override
    public ScmBackCategoryDetailResponseVo getById(IdRequest request) {
        log.info("获取后台分类详情，id={}", request.getId());
        ScmBackCategoryDetailOutputDto outputDto = backCategoryClient.getById(request);

        ScmBackCategoryDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), ScmBackCategoryDetailResponseVo.class);

        {  //填充修改人创建人信息
            Set<String> userIdSet = new HashSet<>();
            userIdSet.add(responseVo.getCreateBy());
            userIdSet.add(responseVo.getUpdateBy());
            Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));

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
    public ScmBackCategoryTreeExprandOutputDto treeExpand() {
        //查询全量扁平数据（含创建人/修改人信息）
        List<ScmBackCategoryListOutputDto> listOutputDtoList = backCategoryClient.listAll();

        //转换为中间对象并构建树
        List<ScmBackCategoryTreeExprandOutputDto> expandTreeBoList = JSONUtil.toList(
                JSONUtil.toJsonStr(listOutputDtoList), ScmBackCategoryTreeExprandOutputDto.class);

        ScmBackCategoryTreeExprandOutputDto treeExprandOutputDto = TreeUtil.buildTree(expandTreeBoList).getFirst();

        //收集所有节点
        List<ScmBackCategoryTreeExprandOutputDto> allNodes = TreeUtil.collectAllNodes(treeExprandOutputDto);

        {//填充创建人/修改人姓名
            Set<String> userIdSet = allNodes.stream()
                    .map(ScmBackCategoryTreeExprandOutputDto::getCreateBy)
                    .collect(Collectors.toSet());
            userIdSet.addAll(allNodes.stream()
                    .map(ScmBackCategoryTreeExprandOutputDto::getUpdateBy)
                    .collect(Collectors.toSet()));

            Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));

            for (ScmBackCategoryTreeExprandOutputDto node : allNodes) {
                IamUserDetailOutputDto createUser = userSimpleDetailMap.get(node.getCreateBy());
                if (null != createUser) {
                    node.setCreateName(createUser.getName());
                }
                IamUserDetailOutputDto updateUser = userSimpleDetailMap.get(node.getUpdateBy());
                if (null != updateUser) {
                    node.setUpdateName(updateUser.getName());
                }
            }
        }

        {//计算分类数（后序递归累计子节点）
            postorderTraversalAndCountChildren(treeExprandOutputDto);
        }

        return treeExprandOutputDto;
    }

    /**
     * 后序遍历并统计子节点数量
     */
    private void postorderTraversalAndCountChildren(ScmBackCategoryTreeExprandOutputDto node) {
        if (CollectionUtil.isEmpty(node.getChildren())) {
            node.setBackCategoryNumber(0);
            return;
        }

        int count = 0;
        for (ScmBackCategoryTreeExprandOutputDto child : node.getChildren()) {
            postorderTraversalAndCountChildren(child);
            //子节点的数量已经是其子节点的数量，+1 算上子节点自身
            count += child.getBackCategoryNumber() + 1;
        }
        node.setBackCategoryNumber(count);
    }

}