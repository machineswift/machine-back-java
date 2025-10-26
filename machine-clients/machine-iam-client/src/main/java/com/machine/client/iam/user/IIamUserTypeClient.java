package com.machine.client.iam.user;

import com.machine.client.iam.user.dto.input.IamUserTypeExistsTypeInputDto;
import com.machine.client.iam.user.dto.output.IamUserTypeOutputDto;
import com.machine.sdk.common.config.OpenFeignMinTimeConfig;
import com.machine.sdk.common.envm.iam.IamUserTypeEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "machine-iam-service", path = "machine-iam-service/server/iam/user_type",
        configuration = OpenFeignMinTimeConfig.class)
public interface IIamUserTypeClient {

    @PostMapping("exists_type")
    boolean existsType(@RequestBody @Validated IamUserTypeExistsTypeInputDto inputDto);

    @PostMapping("list_by_userId")
    List<IamUserTypeOutputDto> listByUserId(@RequestBody @Validated IdRequest request);

    @PostMapping("list_type_by_userId")
    List<IamUserTypeEnum> listTypeByUserId(@RequestBody @Validated IdRequest request);

    @PostMapping("map_type_by_userIdSet")
    Map<String, List<IamUserTypeEnum>> mapTypeByUserIdSet(@RequestBody @Validated IdSetRequest request);
}



