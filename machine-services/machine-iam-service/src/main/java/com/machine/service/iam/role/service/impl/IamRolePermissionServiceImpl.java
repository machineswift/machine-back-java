package com.machine.service.iam.role.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.role.dto.output.IamRolePermissionListOutputDto;
import com.machine.sdk.common.model.dto.iam.DataPermissionRuleDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.tool.JsonUtil;
import com.machine.service.iam.role.dao.IIamRolePermissionRelationDao;
import com.machine.service.iam.role.dao.mapper.entity.IamRolePermissionRelationEntity;
import com.machine.service.iam.role.service.IIamRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.machine.sdk.common.constant.CommonConstant.EMPTY_LIST_STR;

@Slf4j
@Service
public class IamRolePermissionServiceImpl implements IIamRolePermissionService {

    @Autowired
    private IIamRolePermissionRelationDao rolePermissionRelationDao;

    @Override
    public List<IamRolePermissionListOutputDto> listByRoleId(IdRequest request) {
        List<IamRolePermissionRelationEntity> entityList = rolePermissionRelationDao.selectByRoleId(request.getId());
        if (null == entityList) {
            return List.of();
        }

        List<IamRolePermissionListOutputDto> outputDtoList = new ArrayList<>();
        for (IamRolePermissionRelationEntity entity : entityList) {
            IamRolePermissionListOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity), IamRolePermissionListOutputDto.class, true);
            String dataPermissionRules = entity.getDataPermissionRules();
            if (StrUtil.isNotBlank(dataPermissionRules) && !EMPTY_LIST_STR.equals(dataPermissionRules)) {
                outputDto.setDataPermissionRuleList(JsonUtil.safeToList(dataPermissionRules, DataPermissionRuleDto.class));
            }
            outputDtoList.add(outputDto);
        }
        return outputDtoList;
    }

}
