package com.machine.service.data.tag.service;

import com.machine.client.data.tag.dto.input.*;
import com.machine.client.data.tag.dto.output.DataTagOptionDetailOutputDto;
import com.machine.client.data.tag.dto.output.DataTagOptionListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;

import java.util.List;

public interface IDataTagOptionService {

    String create(DataTagOptionCreateInputDto inputDto);

    int delete(IdRequest request);

    int update(DataTagOptionUpdateInputDto inputDto);

    int updateCode(DataTagOptionUpdateCodeInputDto inputDto);

    int updateStatus(DataTagOptionUpdateStatusInputDto inputDto);

    int updateSort(DataTagOptionUpdateSortInputDto inputDto);

    DataTagOptionDetailOutputDto detail(IdRequest request);

    List<DataTagOptionListOutputDto> selectList(DataTagOptionQueryListInputDto inputDto);
}
