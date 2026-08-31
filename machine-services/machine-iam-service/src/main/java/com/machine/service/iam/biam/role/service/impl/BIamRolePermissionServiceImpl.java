package com.machine.service.iam.biam.role.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.role.dto.output.BIamRolePermissionListOutputDto;
import com.machine.sdk.base.model.dto.biam.auth.BIamDataPermissionRuleDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.tool.JsonUtil;
import com.machine.service.iam.biam.role.dao.IBIamRolePermissionRelationDao;
import com.machine.service.iam.biam.role.dao.mapper.entity.BIamRolePermissionRelationEntity;
import com.machine.service.iam.biam.role.service.IBIamRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.machine.sdk.base.constant.CommonConstant.EMPTY_LIST_STR;

@Slf4j
@Service
public class BIamRolePermissionServiceImpl implements IBIamRolePermissionService {

    @Autowired
    private IBIamRolePermissionRelationDao rolePermissionRelationDao;

    @Override
    public List<BIamRolePermissionListOutputDto> listByRoleId(IdRequest request) {
        List<BIamRolePermissionRelationEntity> entityList = rolePermissionRelationDao.selectByRoleId(request.getId());
        if (null == entityList) {
            return List.of();
        }

        List<BIamRolePermissionListOutputDto> outputDtoList = new ArrayList<>();
        for (BIamRolePermissionRelationEntity entity : entityList) {
            BIamRolePermissionListOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity), BIamRolePermissionListOutputDto.class, true);
            String dataPermissionRules = entity.getDataPermissionRules();
            if (StrUtil.isNotBlank(dataPermissionRules) && !EMPTY_LIST_STR.equals(dataPermissionRules)) {
                outputDto.setDataPermissionRuleList(JsonUtil.safeToList(dataPermissionRules, BIamDataPermissionRuleDto.class));
            }
            outputDtoList.add(outputDto);
        }
        return outputDtoList;
    }

}
