package com.machine.service.iam.biam.user.server;

import com.machine.client.iam.biam.user.IBIamUserTypeClient;
import com.machine.client.iam.biam.user.dto.input.BIamUserTypeExistsTypeInputDto;
import com.machine.client.iam.biam.user.dto.output.BIamUserTypeOutputDto;
import com.machine.sdk.base.envm.biam.user.BIamUserTypeEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.service.iam.biam.user.service.IBIamUserTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("server/iam/biam/user_type")
public class BIamUserTypeServer implements IBIamUserTypeClient {

    @Autowired
    private IBIamUserTypeService userTypeService;

    @Override
    @PostMapping("exists_type")
    public boolean existsType(@RequestBody @Validated BIamUserTypeExistsTypeInputDto inputDto) {
        return userTypeService.existsType(inputDto);
    }

    @Override
    @PostMapping("list_by_userId")
    public List<BIamUserTypeOutputDto> listByUserId(@RequestBody @Validated IdRequest request) {
        return userTypeService.listByUserId(request);
    }

    @Override
    @PostMapping("list_type_by_userId")
    public List<BIamUserTypeEnum> listTypeByUserId(@RequestBody @Validated IdRequest request) {
        return userTypeService.listTypeByUserId(request);
    }

    @Override
    @PostMapping("map_type_by_userIdSet")
    public Map<String, List<BIamUserTypeEnum>> mapTypeByUserIdSet(@RequestBody @Validated IdSetRequest request) {
        return userTypeService.mapTypeByUserIdSet(request);
    }
}
