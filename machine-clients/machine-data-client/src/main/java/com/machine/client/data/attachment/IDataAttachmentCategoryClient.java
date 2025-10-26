package com.machine.client.data.attachment;

import com.machine.client.data.attachment.dto.input.DataAttachmentCategoryCreateInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentCategoryUpdateInputDto;
import com.machine.client.data.attachment.dto.input.DataAttachmentCategoryUpdateParentInputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentCategoryDetailOutputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentCategoryListOutputDto;
import com.machine.client.data.attachment.dto.output.DataAttachmentCategoryTreeSimpleOutputDto;
import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import com.machine.sdk.common.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "machine-data-service",  path = "machine-data-service/server/data/attachment_category",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataAttachmentCategoryClient {

    @PostMapping("create")
    String create(@RequestBody @Validated DataAttachmentCategoryCreateInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Validated IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Validated DataAttachmentCategoryUpdateInputDto inputDto);

    @PostMapping("updateParent")
    int updateParent(@RequestBody @Validated DataAttachmentCategoryUpdateParentInputDto inputDto);

    @PostMapping("detail")
    DataAttachmentCategoryDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @GetMapping("list_all")
    List<DataAttachmentCategoryListOutputDto> listAll();

    @GetMapping("tree_all_simple")
    DataAttachmentCategoryTreeSimpleOutputDto treeAllSimple();

}



