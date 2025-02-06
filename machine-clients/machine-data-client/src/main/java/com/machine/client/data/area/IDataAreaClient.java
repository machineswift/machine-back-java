package com.machine.client.data.area;

import com.machine.client.data.area.dto.output.AreaListOutputDto;
import com.machine.client.data.area.dto.output.AreaTreeOutputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/area",
        configuration = OpenFeignDefaultConfig.class)
public interface IDataAreaClient {

    /**
     * 清理缓存
     */
    @GetMapping("clear_cache")
    void clearCache();

    /**
     * 区域树
     */
    @GetMapping("tree")
    AreaTreeOutputDto tree();

    /**
     * 查询所有可用部门
     */
    @GetMapping("list_all")
    List<AreaListOutputDto> listAll();


}
