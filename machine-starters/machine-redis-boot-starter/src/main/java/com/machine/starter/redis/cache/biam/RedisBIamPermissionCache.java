package com.machine.starter.redis.cache.biam;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.permission.IBIamPermissionClient;
import com.machine.client.iam.biam.permission.dto.output.BIamPermissionTreeOutputDto;
import com.machine.sdk.base.model.dto.biam.auth.BIamDataPermissionMetaDto;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.sdk.base.tool.Tuples;
import com.machine.starter.redis.command.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.Permission.BIAM_PERMISSION_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4BIamConstant.Permission.BIAM_PERMISSION_TREE_KEY;

@Slf4j
@Component
public class RedisBIamPermissionCache {

    private final AtomicReference<String> version = new AtomicReference<>();
    private volatile BIamPermissionTreeOutputDto cachedData;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IBIamPermissionClient permissionClient;

    public List<String> listSubId(String permissionId) {
        BIamPermissionTreeOutputDto treeOutputDto = treeAll();
        BIamPermissionTreeOutputDto targetNode = TreeUtil.findNode(treeOutputDto, permissionId);
        if (null == targetNode || CollectionUtil.isEmpty(targetNode.getChildren())) {
            return List.of();
        }

        List<String> childIdList = new ArrayList<>();
        for (BIamPermissionTreeOutputDto child : targetNode.getChildren()) {
            childIdList.add(child.getId());
        }
        return childIdList;
    }

    public List<BIamPermissionTreeOutputDto> listByIdSet(Collection<String> permissionIdSet) {
        BIamPermissionTreeOutputDto allTreeOutputDto = treeAll();
        List<BIamPermissionTreeOutputDto> outputDtoList = new ArrayList<>();
        for (String id : permissionIdSet) {
            BIamPermissionTreeOutputDto targetNode = TreeUtil.findNode(allTreeOutputDto, id);
            if (null != targetNode) {
                outputDtoList.add(targetNode);
            }
        }
        return outputDtoList;
    }

    public BIamPermissionTreeOutputDto treeAll() {
        String latestVersion = customerRedisCommands.get(BIAM_PERMISSION_TREE_KEY);

        // 如果版本号没变，直接返回缓存
        if (StrUtil.isNotEmpty(latestVersion)
                && latestVersion.equals(version.get())
                && cachedData != null) {
            return copyTree(cachedData);
        }

        // 版本号变了或缓存为空，同步加载
        synchronized (this) {
            // Double-Check：防止并发情况下重复加载
            String recheckVersion = customerRedisCommands.get(BIAM_PERMISSION_TREE_KEY);
            if (StrUtil.isNotEmpty(recheckVersion)
                    && recheckVersion.equals(version.get())
                    && cachedData != null) {
                return copyTree(cachedData);
            }

            BIamPermissionTreeOutputDto treeOutputDto = null;

            // Redis加载新数据
            if (StrUtil.isNotEmpty(recheckVersion)) {
                String treeJson = customerRedisCommands.get(BIAM_PERMISSION_TREE_DATA + recheckVersion);
                if (StrUtil.isNotEmpty(treeJson)) {
                    treeOutputDto = JSONUtil.toBean(treeJson, BIamPermissionTreeOutputDto.class);
                }
            }

            // Redis无数据，从远程加载
            if (null == treeOutputDto) {
                Tuples.Tuple2<String, BIamPermissionTreeOutputDto> tuple2 = permissionClient.treeAll();
                recheckVersion = tuple2._1();
                treeOutputDto = tuple2._2();
            }

            cachedData = treeOutputDto;
            version.set(recheckVersion);
            return copyTree(cachedData);
        }
    }

    /**
     * 递归深拷贝权限树，防止外部修改缓存数据影响业务
     */
    private BIamPermissionTreeOutputDto copyTree(BIamPermissionTreeOutputDto source) {
        if (null == source) {
            return null;
        }
        BIamPermissionTreeOutputDto target = new BIamPermissionTreeOutputDto();
        target.setId(source.getId());
        target.setParentId(source.getParentId());
        target.setName(source.getName());
        target.setSort(source.getSort());
        target.setResourceType(source.getResourceType());
        target.setCode(source.getCode());
        target.setIcon(source.getIcon());
        target.setDescription(source.getDescription());
        target.setCreateBy(source.getCreateBy());
        target.setCreateTime(source.getCreateTime());
        target.setUpdateBy(source.getUpdateBy());
        target.setUpdateTime(source.getUpdateTime());

        target.setDataPermissionMetaList(copyDataPermissionMetaList(source.getDataPermissionMetaList()));

        if (CollectionUtil.isNotEmpty(source.getChildren())) {
            List<BIamPermissionTreeOutputDto> children = new ArrayList<>(source.getChildren().size());
            for (BIamPermissionTreeOutputDto child : source.getChildren()) {
                children.add(copyTree(child));
            }
            target.setChildren(children);
        } else {
            target.setChildren(List.of());
        }
        return target;
    }

    private List<BIamDataPermissionMetaDto> copyDataPermissionMetaList(List<BIamDataPermissionMetaDto> sourceList) {
        if (CollectionUtil.isEmpty(sourceList)) {
            return List.of();
        }
        List<BIamDataPermissionMetaDto> targetList = new ArrayList<>(sourceList.size());
        for (BIamDataPermissionMetaDto source : sourceList) {
            BIamDataPermissionMetaDto target = new BIamDataPermissionMetaDto();
            target.setFunctionCode(source.getFunctionCode());
            target.setFunctionName(source.getFunctionName());
            if (CollectionUtil.isNotEmpty(source.getScopeList())) {
                List<BIamDataPermissionMetaDto.Scope> scopeList = new ArrayList<>(source.getScopeList().size());
                for (BIamDataPermissionMetaDto.Scope scope : source.getScopeList()) {
                    BIamDataPermissionMetaDto.Scope targetScope = new BIamDataPermissionMetaDto.Scope();
                    targetScope.setScopeCode(scope.getScopeCode());
                    targetScope.setScopeName(scope.getScopeName());
                    scopeList.add(targetScope);
                }
                target.setScopeList(scopeList);
            }
            targetList.add(target);
        }
        return targetList;
    }
}
