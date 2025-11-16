package com.machine.service.iam.user.server;

import com.machine.client.iam.user.IIamUserTypeClient;
import com.machine.client.iam.user.dto.input.IamUserTypeExistsTypeInputDto;
import com.machine.client.iam.user.dto.output.IamUserTypeOutputDto;
import com.machine.sdk.common.envm.iam.user.IamUserTypeEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.iam.user.service.IIamUserTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("server/iam/user_type")
public class IamUserTypeServer implements IIamUserTypeClient {

    @Autowired
    private IIamUserTypeService userTypeService;

    @Override
    @PostMapping("exists_type")
    public boolean existsType(@RequestBody @Validated IamUserTypeExistsTypeInputDto inputDto) {
        return userTypeService.existsType(inputDto);
    }

    @Override
    @PostMapping("list_by_userId")
    public List<IamUserTypeOutputDto> listByUserId(@RequestBody @Validated IdRequest request) {
        return userTypeService.listByUserId(request);
    }

    @Override
    @PostMapping("list_type_by_userId")
    public List<IamUserTypeEnum> listTypeByUserId(@RequestBody @Validated IdRequest request) {
        return userTypeService.listTypeByUserId(request);
    }

    @Override
    @PostMapping("map_type_by_userIdSet")
    public Map<String, List<IamUserTypeEnum>> mapTypeByUserIdSet(@RequestBody @Validated IdSetRequest request) {
        return userTypeService.mapTypeByUserIdSet(request);
    }
}
