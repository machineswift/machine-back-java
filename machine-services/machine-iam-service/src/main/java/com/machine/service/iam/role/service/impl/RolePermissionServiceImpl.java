package com.machine.service.iam.role.service.impl;

import cn.hutool.json.JSONUtil;
import com.machine.client.iam.role.dto.output.IamRolePermissionListOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.service.iam.role.dao.IRolePermissionRelationDao;
import com.machine.service.iam.role.dao.mapper.entity.RolePermissionRelationEntity;
import com.machine.service.iam.role.service.IRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RolePermissionServiceImpl implements IRolePermissionService {

    @Autowired
    private IRolePermissionRelationDao rolePermissionRelationDao;

    @Override
    public List<IamRolePermissionListOutputDto> listByRoleId(IdRequest request) {
        List<RolePermissionRelationEntity> entityList = rolePermissionRelationDao.selectByRoleId(request.getId());
        if (null == entityList) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), IamRolePermissionListOutputDto.class);
    }

}
