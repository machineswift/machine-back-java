package com.machine.client.data.brand;

import com.machine.client.data.brand.dto.input.DataBrandCreateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandQueryPageInputDto;
import com.machine.client.data.brand.dto.input.DataBrandQuerySimplePageInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateLogoAttachmentIdInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateParentIdInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateStatusInputDto;
import com.machine.client.data.brand.dto.output.DataBrandDetailOutputDto;
import com.machine.client.data.brand.dto.output.DataBrandListOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Set;

@FeignClient(name = "machine-data-service",
        path = "machine-data-service/server/data/brand",
        configuration = OpenFeignMinTimeConfig.class)
public interface IDataBrandClient {

    @PostMapping("create")
    String create(@RequestBody @Validated DataBrandCreateInputDto inputDto);

    @PostMapping("delete")
    int delete(@RequestBody @Validated IdRequest request);

    @PostMapping("update")
    int update(@RequestBody @Validated DataBrandUpdateInputDto inputDto);

    @PostMapping("update_status")
    int updateStatus(@RequestBody @Validated DataBrandUpdateStatusInputDto inputDto);

    @PostMapping("update_logo_attachmentId")
    int updateLogoAttachmentId(@RequestBody @Validated DataBrandUpdateLogoAttachmentIdInputDto inputDto);

    @PostMapping("update_parent")
    int updateParent(@RequestBody @Validated DataBrandUpdateParentIdInputDto inputDto);

    @PostMapping("exists")
    boolean exists(@RequestBody @Validated IdRequest request);

    @PostMapping("list_ancestor")
    List<DataBrandDetailOutputDto> listAncestorById(@RequestBody @Validated IdRequest request);

    @PostMapping("detail")
    DataBrandDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("list_has_children_idSet")
    Set<String> listHasChildrenIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("map_by_idSet")
    Map<String, DataBrandDetailOutputDto> mapByIdSet(@RequestBody @Validated IdSetRequest request);

    @PostMapping("page")
    PageResponse<DataBrandListOutputDto> page(@RequestBody @Validated DataBrandQueryPageInputDto inputDto);

    @PostMapping("children_page")
    PageResponse<DataBrandListOutputDto> childrenPage(@RequestBody @Validated DataBrandQueryPageInputDto inputDto);

    @PostMapping("simple_page")
    PageResponse<DataBrandListOutputDto> simplePage(@RequestBody @Validated DataBrandQuerySimplePageInputDto inputDto);

}



