package com.machine.starter.redis.cache;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.area.IDataAreaClient;
import com.machine.client.data.area.dto.input.DataAreaTreeInputDto;
import com.machine.client.data.area.dto.output.DataAreaTreeOutputDto;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.starter.redis.function.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.Area.DATA_AREA_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.Area.DATA_AREA_TREE_KEY;

@Slf4j
@Component
public class RedisCacheDataArea {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IDataAreaClient areaClient;

    public DataAreaTreeOutputDto tree(String countryCode) {
        //获取区域树的动态key
        String keyCode = customerRedisCommands.get(DATA_AREA_TREE_KEY + countryCode);

        //如果存在则直接返回数据
        if (StrUtil.isNotEmpty(keyCode)) {
            String treeJson = customerRedisCommands.get(DATA_AREA_TREE_DATA + countryCode + keyCode);
            if (StrUtil.isNotEmpty(treeJson)) {
                return JSONUtil.toBean(treeJson, DataAreaTreeOutputDto.class);
            }
        }

        //查询树
        return areaClient.tree(new DataAreaTreeInputDto(countryCode));
    }

    public Set<String> recursionListSubId(String areaId, DataAreaTreeOutputDto areaTree) {
        //找到指定的节点
        DataAreaTreeOutputDto treeNode = TreeUtil.findNode(areaTree, areaId);
        if (null == treeNode) {
            return Set.of();
        }

        //获取节点以及子节点的所有数据
        List<DataAreaTreeOutputDto> outputDtoList = TreeUtil.collectAllNodes(treeNode);
        return outputDtoList.stream().map(DataAreaTreeOutputDto::getId).collect(Collectors.toSet());
    }

    public Set<String> recursionListSubId(Set<String> areaIdSet, DataAreaTreeOutputDto areaTree) {
        Set<String> recursionIdSet = new HashSet<>();
        for (String areaId : areaIdSet) {
            recursionIdSet.addAll(recursionListSubId(areaId, areaTree));
        }
        return recursionIdSet;
    }
}
