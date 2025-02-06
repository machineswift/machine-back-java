package com.machine.service.data.area.server;

import com.machine.client.data.area.IDataAreaClient;
import com.machine.client.data.area.dto.output.AreaListOutputDto;
import com.machine.client.data.area.dto.output.AreaTreeOutputDto;
import com.machine.service.data.area.service.IAreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("server/data/area")
public class DataAreaServer implements IDataAreaClient {

    @Autowired
    private IAreaService areaService;

    @Override
    @GetMapping("clear_cache")
    public void clearCache() {
        log.info("清理区域树缓存");
        areaService.clearCache();
    }

    @Override
    @GetMapping("tree")
    public AreaTreeOutputDto tree() {
        return areaService.tree();
    }

    @Override
    @GetMapping("list_all")
    public List<AreaListOutputDto> listAll() {
        return areaService.listAll();
    }

}
