package com.machine.starter.redis.cache.data;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.area.IDataAreaClient;
import com.machine.client.data.area.dto.output.DataAreaTreeOutputDto;
import com.machine.sdk.base.envm.data.DataCountryEnum;
import com.machine.sdk.base.tool.TreeUtil;
import com.machine.sdk.base.tool.Tuples;
import com.machine.starter.redis.command.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.Area.DATA_AREA_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.Area.DATA_AREA_TREE_KEY;

@Slf4j
@Component
public class RedisDataAreaCache {

    /**
     * 各国家的二级缓存（版本号 + 本地树数据）
     */
    private final Map<DataCountryEnum, CountryTreeCache> countryTreeCacheMap = new EnumMap<>(
            DataCountryEnum.class);

    {
        for (DataCountryEnum country : DataCountryEnum.values()) {
            countryTreeCacheMap.put(country, new CountryTreeCache());
        }
    }

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IDataAreaClient areaClient;

    public Set<String> recursionListSubId(String areaId,
                                          DataAreaTreeOutputDto areaTree) {
        // 找到指定的节点
        DataAreaTreeOutputDto treeNode = TreeUtil.findNode(areaTree, areaId);
        if (null == treeNode) {
            return Set.of();
        }

        // 获取节点以及子节点的所有数据
        List<DataAreaTreeOutputDto> outputDtoList = TreeUtil.collectAllNodes(treeNode);
        return outputDtoList.stream().map(DataAreaTreeOutputDto::getId).collect(Collectors.toSet());
    }

    public Set<String> recursionListSubId(Set<String> areaIdSet,
                                          DataAreaTreeOutputDto areaTree) {
        Set<String> recursionIdSet = new HashSet<>();
        for (String areaId : areaIdSet) {
            recursionIdSet.addAll(recursionListSubId(areaId, areaTree));
        }
        return recursionIdSet;
    }

    public DataAreaTreeOutputDto treeAll(DataCountryEnum country) {
        String countryName = country.getName();
        String keyCode = customerRedisCommands.get(DATA_AREA_TREE_KEY + countryName);

        // 二级缓存命中
        CountryTreeCache countryCache = countryTreeCacheMap.get(country);
        if (StrUtil.isNotEmpty(keyCode) &&
                keyCode.equals(countryCache.version.get()) &&
                countryCache.cachedData != null) {
            return copyTree(countryCache.cachedData);
        }

        synchronized (countryCache) {
            // Double-Check：防止并发情况下重复加载
            String recheckKeyCode = customerRedisCommands.get(DATA_AREA_TREE_KEY + countryName);
            if (StrUtil.isNotEmpty(recheckKeyCode) &&
                    recheckKeyCode.equals(countryCache.version.get()) &&
                    countryCache.cachedData != null) {
                return copyTree(countryCache.cachedData);
            }

            DataAreaTreeOutputDto treeOutputDto = null;

            // Redis加载数据（一级缓存）
            if (StrUtil.isNotEmpty(recheckKeyCode)) {
                String treeJson = customerRedisCommands.get(DATA_AREA_TREE_DATA + countryName + recheckKeyCode);
                if (StrUtil.isNotEmpty(treeJson)) {
                    treeOutputDto = JSONUtil.toBean(treeJson, DataAreaTreeOutputDto.class);
                }
            }

            // Redis无数据，从远程加载
            if (null == treeOutputDto) {
                Tuples.Tuple2<String, DataAreaTreeOutputDto> tuple2 = areaClient.treeAll(country);
                recheckKeyCode = tuple2._1();
                treeOutputDto = tuple2._2();
            }

            countryCache.cachedData = treeOutputDto;
            countryCache.version.set(recheckKeyCode);
            return copyTree(countryCache.cachedData);
        }
    }

    /**
     * 递归深拷贝区域树，防止调用方修改缓存数据影响业务
     */
    private DataAreaTreeOutputDto copyTree(DataAreaTreeOutputDto source) {
        if (null == source) {
            return null;
        }
        DataAreaTreeOutputDto target = new DataAreaTreeOutputDto();
        target.setId(source.getId());
        target.setParentId(source.getParentId());
        target.setName(source.getName());
        target.setSort(source.getSort());
        target.setCode(source.getCode());
        target.setCreateBy(source.getCreateBy());
        target.setCreateTime(source.getCreateTime());
        target.setUpdateBy(source.getUpdateBy());
        target.setUpdateTime(source.getUpdateTime());

        if (CollectionUtil.isNotEmpty(source.getChildren())) {
            List<DataAreaTreeOutputDto> children = new ArrayList<>(source.getChildren().size());
            for (DataAreaTreeOutputDto child : source.getChildren()) {
                children.add(copyTree(child));
            }
            target.setChildren(children);
        } else {
            target.setChildren(List.of());
        }
        return target;
    }

    private static class CountryTreeCache {
        private final AtomicReference<String> version = new AtomicReference<>();
        private volatile DataAreaTreeOutputDto cachedData;
    }

}
