package com.machine.client.iam.biam.user;

import com.machine.client.iam.biam.user.dto.input.BIamUserTypeExistsTypeInputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserTypeOutputDto;
import com.machine.sdk.base.config.OpenFeignMinTimeConfig;
import com.machine.sdk.base.envm.biam.user.BIamUserTypeEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "machine-iam-service",
        path = "machine-iam-service/server/iam/biam/user_type",
        configuration = OpenFeignMinTimeConfig.class)
public interface IBIamUserTypeClient {

    @PostMapping("exists_type")
    boolean existsType(@RequestBody @Validated BIamUserTypeExistsTypeInputDto inputDto);

    @PostMapping("list_by_userId")
    List<BIamUserTypeOutputDto> listByUserId(@RequestBody @Validated IdRequest request);

    @PostMapping("list_type_by_userId")
    List<BIamUserTypeEnum> listTypeByUserId(@RequestBody @Validated IdRequest request);

    @PostMapping("map_type_by_userIdSet")
    Map<String, List<BIamUserTypeEnum>> mapTypeByUserIdSet(@RequestBody @Validated IdSetRequest request);
}



