package com.machine.service.data.area.service;


import com.machine.client.data.area.dto.input.DataAreaCreateInputDto;
import com.machine.client.data.area.dto.input.DataAreaTreeInputDto;
import com.machine.client.data.area.dto.input.DataAreaUpdateInputDto;
import com.machine.client.data.area.dto.input.DataAreaUpdateParentInputDto;
import com.machine.client.data.area.dto.output.DataAreaDetailOutputDto;
import com.machine.client.data.area.dto.output.DataAreaTreeOutputDto;
import com.machine.sdk.common.model.request.IdRequest;

public interface IDataAreaService {

    String create(DataAreaCreateInputDto inputDto);

    int delete(IdRequest request);

    int update(DataAreaUpdateInputDto inputDto);

    int updateParent(DataAreaUpdateParentInputDto inputDto);

    DataAreaDetailOutputDto detail(IdRequest request);

    DataAreaTreeOutputDto tree(DataAreaTreeInputDto inputDto);
}
