package com.machine.service.data.tag.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.tag.dto.input.DataTagCreateInputDto;
import com.machine.client.data.tag.dto.input.DataTagQueryPageInputDto;
import com.machine.client.data.tag.dto.input.DataTagUpdateCategoryDto;
import com.machine.client.data.tag.dto.input.DataTagUpdateInputDto;
import com.machine.client.data.tag.dto.output.DataTagDetailOutputDto;
import com.machine.client.data.tag.dto.output.DataTagListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;

public interface IDataTagService {

    String create(DataTagCreateInputDto inputDto);

    int delete(IdRequest request);

    int update(DataTagUpdateInputDto inputDto);

    int updateCategory(DataTagUpdateCategoryDto inputDto);

    DataTagDetailOutputDto detail(IdRequest request);

    Page<DataTagListOutputDto> selectPage(DataTagQueryPageInputDto inputDto);

}
