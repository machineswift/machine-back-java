package com.machine.service.data.area.service;


import com.machine.client.data.area.dto.output.AreaListOutputDto;
import com.machine.client.data.area.dto.output.AreaTreeOutputDto;

import java.util.List;

public interface IAreaService {

    void clearCache();

    AreaTreeOutputDto tree();

    List<AreaListOutputDto> listAll();

}
