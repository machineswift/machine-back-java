package com.machine.service.data.area.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.area.dto.output.AreaListOutputDto;
import com.machine.client.data.area.dto.output.AreaTreeOutputDto;
import com.machine.client.data.leaf.IDataLeaf4RedisClient;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.service.data.area.dao.IAreaDao;
import com.machine.service.data.area.dao.mapper.entity.AreaEntity;
import com.machine.service.data.area.service.IAreaService;
import com.machine.starter.redis.function.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Data.DATA_AREA_TREE_LOCK;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.Area.DATA_AREA_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.Area.DATA_AREA_TREE_KEY;

@Slf4j
@Service
public class AreaServiceImpl implements IAreaService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IAreaDao areaDao;

    @Autowired
    private IDataLeaf4RedisClient leaf4RedisClient;

    @Override
    public void clearCache() {
        customerRedisCommands.del(DATA_AREA_TREE_KEY);
    }

    @Override
    public AreaTreeOutputDto tree() {
        //获取区域树的动态key
        String keyCode = customerRedisCommands.get(DATA_AREA_TREE_KEY);
        //如果存在则直接返回数据
        if (StrUtil.isNotEmpty(keyCode)) {
            String treeJson = customerRedisCommands.get(DATA_AREA_TREE_DATA + keyCode);
            if (StrUtil.isNotEmpty(treeJson)) {
                return JSONUtil.toBean(treeJson, AreaTreeOutputDto.class);
            }
        }

        //缓存击穿
        RLock lock = redissonClient.getLock(DATA_AREA_TREE_LOCK);
        try {
            lock.lock();

            keyCode = customerRedisCommands.get(DATA_AREA_TREE_KEY);
            if (StrUtil.isNotEmpty(keyCode)) {
                String treeJson = customerRedisCommands.get(DATA_AREA_TREE_DATA + keyCode);
                if (StrUtil.isNotEmpty(treeJson)) {
                    return JSONUtil.toBean(treeJson, AreaTreeOutputDto.class);
                }
            }

            //重新生成区域树的动态key
            keyCode = leaf4RedisClient.dataAreaTree();
            customerRedisCommands.set(DATA_AREA_TREE_KEY, keyCode, 24 * 60 * 60);

            //查询DB组装树
            List<AreaEntity> entityList = areaDao.selectAll();
            List<AreaTreeOutputDto> outputDtoList = JSONUtil.toList(JSONUtil.toJsonStr(entityList), AreaTreeOutputDto.class);
            AreaTreeOutputDto treeOutputDto = TreeUtil.buildTree(outputDtoList).getFirst();
            TreeUtil.sortTreeNodes(treeOutputDto.getChildren(), true);

            //Tree 数据缓存到redis
            customerRedisCommands.set(DATA_AREA_TREE_DATA + keyCode, JSONUtil.toJsonStr(treeOutputDto), 24 * 60 * 60 + 60);

            return treeOutputDto;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<AreaListOutputDto> listAll() {
        List<AreaEntity> entityList = areaDao.selectAll();
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), AreaListOutputDto.class);
    }
}
