package com.machine.starter.redis.cache;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.area.IDataAreaClient;
import com.machine.client.data.area.dto.output.AreaTreeOutputDto;
import com.machine.starter.redis.function.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.Area.DATA_AREA_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4DataConstant.Area.DATA_AREA_TREE_KEY;

@Slf4j
@Component
public class RedisCacheDataArea {

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IDataAreaClient areaClient;

    public AreaTreeOutputDto treeSimple() {
        //获取区域树的动态key
        String keyCode = customerRedisCommands.get(DATA_AREA_TREE_KEY);

        //如果存在则直接返回数据
        if (StrUtil.isNotEmpty(keyCode)) {
            String treeJson = customerRedisCommands.get(DATA_AREA_TREE_DATA + keyCode);
            if (StrUtil.isNotEmpty(treeJson)) {
                return JSONUtil.toBean(treeJson, AreaTreeOutputDto.class);
            }
        }

        //查询树
        return areaClient.tree();
    }
}
