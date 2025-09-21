package com.machine.client.data.franchisee;

import com.machine.client.data.franchisee.dto.input.*;
import com.machine.client.data.franchisee.dto.output.DataFranchiseeDetailOutputDto;
import com.machine.client.data.franchisee.dto.output.DataFranchiseeListOutputDto;
import com.machine.client.data.franchisee.dto.output.OpenapiFranchiseeHealthCertificateOutputDto;
import com.machine.client.data.franchisee.dto.output.OpenapiFranchiseeIdentityCardOutputDto;
import com.machine.client.data.supplier.dto.input.DataFranchiseeListUserIdInputDto;
import com.machine.sdk.common.config.OpenFeignDefaultConfig;
import com.machine.sdk.common.model.request.IdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Set;

@FeignClient(name = "machine-data-service/machine-data-service/server/data/franchisee",
        configuration = OpenFeignDefaultConfig.class)
public interface IDataFranchiseeClient {

    @PostMapping("create")
    String create(@RequestBody @Validated DataFranchiseeCreateInputDto inputDto);

    @PostMapping("bind_shop")
    boolean bindShop(@RequestBody @Validated DataFranchiseeBindShopInputDto inputDto);

    @PostMapping("unbind_shop")
    boolean unbindShop(@RequestBody @Validated DataFranchiseeUnbindShopInputDto inputDto);

    @PostMapping("update_phone")
    int updatePhone(@RequestBody @Validated OpenApiFranchiseeUpdatePhoneInputDto inputDto);

    @PostMapping("update")
    int update(@RequestBody @Validated DataFranchiseeUpdateInputDto inputDto);

    @PostMapping("detail")
    DataFranchiseeDetailOutputDto detail(@RequestBody @Validated IdRequest request);

    @PostMapping("detail_by_userId")
    DataFranchiseeDetailOutputDto getByUserId(@RequestBody @Validated IdRequest request);

    @PostMapping("detail_by_code")
    DataFranchiseeDetailOutputDto detailByCode(@RequestBody @Validated IdRequest request);

    @PostMapping("identity_card")
    OpenapiFranchiseeIdentityCardOutputDto identityCard(@RequestBody @Validated IdRequest request);

    @PostMapping("health_certificate")
    OpenapiFranchiseeHealthCertificateOutputDto healthCertificate(@RequestBody @Validated IdRequest request);

    @PostMapping("list_userId_by_condition")
    Set<String> listUserIdByCondition(@RequestBody @Validated DataFranchiseeListUserIdInputDto inputDto);

    @PostMapping("list_by_offset")
    List<DataFranchiseeListOutputDto> listByOffset(@RequestBody @Validated DataFranchiseeQueryListOffsetInputDto inputDto);

}
