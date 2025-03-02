package com.machine.service.iam.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.shop.IDataShopOrganizationRelationClient;
import com.machine.client.iam.user.dto.input.DataPermission4ManageInputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.iam.DataPermissionResultTypeEnum;
import com.machine.sdk.common.envm.iam.DataPermissionScopeTypeEnum;
import com.machine.sdk.common.envm.iam.UserRoleTargetTypeEnum;
import com.machine.sdk.common.envm.iam.organization.OrganizationSelectTypeEnum;
import com.machine.sdk.common.exception.iam.IamPermissionBusinessException;
import com.machine.sdk.common.model.dto.iam.DataPermissionDto;
import com.machine.sdk.common.model.dto.iam.DataPermissionMetaDto;
import com.machine.sdk.common.model.dto.iam.DataPermissionRuleDto;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.iam.permission.dao.IPermissionDao;
import com.machine.service.iam.permission.dao.mapper.entity.PermissionEntity;
import com.machine.service.iam.role.dao.IRolePermissionRelationDao;
import com.machine.service.iam.role.dao.mapper.entity.RolePermissionRelationEntity;
import com.machine.service.iam.user.dao.IUserRoleTargetRelationDao;
import com.machine.service.iam.user.dao.mapper.entity.UserRoleTargetRelationEntity;
import com.machine.service.iam.user.service.IUserPermissionService;
import com.machine.starter.redis.cache.RedisCacheIamOrganization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserPermissionServiceImpl implements IUserPermissionService {

    @Autowired
    private RedisCacheIamOrganization organizationCache;

    @Autowired
    private IPermissionDao permissionDao;

    @Autowired
    private IRolePermissionRelationDao rolePermissionRelationDao;

    @Autowired
    private IUserRoleTargetRelationDao userRoleTargetRelationDao;

    @Autowired
    private IDataShopOrganizationRelationClient shopOrganizationRelationClient;

    @Override
    public DataPermissionDto dataPermission4SuperApp() {
        List<UserRoleTargetRelationEntity> relationEntityList = userRoleTargetRelationDao
                .listByUserId(AppContext.getContext().getUserId());

        DataPermissionDto dataPermissionDto = new DataPermissionDto();
        if (CollectionUtil.isEmpty(relationEntityList)) {
            return dataPermissionDto;
        }

        Set<String> organizationIdSet = new HashSet<>();
        Set<String> shopIdSet = new HashSet<>();
        Set<String> projectIdSet = new HashSet<>();

        for (UserRoleTargetRelationEntity entity : relationEntityList) {
            if (UserRoleTargetTypeEnum.ORGANIZATION == entity.getTargetType()) {
                organizationIdSet.add(entity.getTargetId());
            } else if (UserRoleTargetTypeEnum.SHOP == entity.getTargetType()) {
                shopIdSet.add(entity.getTargetId());
            }
        }

        //递归查询部门Id
        organizationIdSet.addAll(organizationCache.recursionListSubIds(organizationIdSet));

        //查询部门关联的门店Id
        List<String> shopIdList = shopOrganizationRelationClient.listShopIdByOrganizationIdSet(new IdSetRequest(organizationIdSet));
        if (CollectionUtil.isNotEmpty(shopIdList)) {
            shopIdSet.addAll(shopIdList);
        }

        //组装返回数据
        dataPermissionDto.setOrganizationIdSet(organizationIdSet);
        dataPermissionDto.setShopIdSet(shopIdSet);
        dataPermissionDto.setProjectIdSet(projectIdSet);
        return dataPermissionDto;
    }

    @Override
    public DataPermissionDto dataPermission4SuperManage(DataPermission4ManageInputDto inputDto) {
        List<UserRoleTargetRelationEntity> relationEntityList = userRoleTargetRelationDao
                .listByUserId(AppContext.getContext().getUserId());
        if (CollectionUtil.isEmpty(relationEntityList)) {
            return new DataPermissionDto();
        }

        String moduleCode = inputDto.getModuleCode();
        String dataPermissionCode = inputDto.getDataPermissionCode();
        PermissionEntity permissionEntity = permissionDao.getByCode(dataPermissionCode);
        String dataMetaInto = permissionEntity.getDataMetaInto();
        if (StrUtil.isBlank(dataMetaInto)) {
            //默认权限
            Set<String> organizationIdSet = new HashSet<>();
            Set<String> shopIdSet = new HashSet<>();
            Set<String> projectIdSet = new HashSet<>();

            for (UserRoleTargetRelationEntity entity : relationEntityList) {
                if (UserRoleTargetTypeEnum.ORGANIZATION == entity.getTargetType()) {
                    organizationIdSet.add(entity.getTargetId());
                } else if (UserRoleTargetTypeEnum.SHOP == entity.getTargetType()) {
                    shopIdSet.add(entity.getTargetId());
                }
            }

            //递归查询部门Id
            organizationIdSet.addAll(organizationCache.recursionListSubIds(organizationIdSet));

            //查询部门关联的门店Id
            List<String> shopIdList = shopOrganizationRelationClient.listShopIdByOrganizationIdSet(new IdSetRequest(organizationIdSet));
            if (CollectionUtil.isNotEmpty(shopIdList)) {
                shopIdSet.addAll(shopIdList);
            }

            //组装返回数据
            DataPermissionDto dataPermissionDto = new DataPermissionDto();
            dataPermissionDto.setOrganizationIdSet(organizationIdSet);
            dataPermissionDto.setShopIdSet(shopIdSet);
            dataPermissionDto.setProjectIdSet(projectIdSet);
            return dataPermissionDto;
        } else {
            //指定权限
            List<DataPermissionMetaDto> metaDtoList = JSONUtil.toList(dataMetaInto, DataPermissionMetaDto.class);
            DataPermissionMetaDto targetMetaDto = null;
            for (DataPermissionMetaDto metaDto : metaDtoList) {
                if (metaDto.getModuleCode().equals(moduleCode)) {
                    targetMetaDto = metaDto;
                }
            }
            if (targetMetaDto == null) {
                throw new IamPermissionBusinessException("iam.permission.data.targetMetaNotExists", "数据权限对应模块不存在");
            }

            //用户关联的角色
            Set<String> userRoleIdSet = new HashSet<>();
            for (UserRoleTargetRelationEntity entity : relationEntityList) {
                userRoleIdSet.add(entity.getRoleId());
            }

            //角色关联数据权限规则信息
            List<DataPermissionRuleDto> targetRuleDtoList = new ArrayList<>();
            List<RolePermissionRelationEntity> rolePermissionRelationEntityList = rolePermissionRelationDao.selectByRoleIds(userRoleIdSet);
            if (CollectionUtil.isNotEmpty(rolePermissionRelationEntityList)) {
                for (RolePermissionRelationEntity entity : rolePermissionRelationEntityList) {
                    if (!permissionEntity.getId().equals(entity.getPermissionId())) {
                        continue;
                    }

                    DataPermissionRuleDto targetRuleDto = null;
                    String dataInto = entity.getDataInto();
                    List<DataPermissionRuleDto> dataPermissionRuleDtoList = JSONUtil.toList(dataInto, DataPermissionRuleDto.class);
                    if (CollectionUtil.isEmpty(dataPermissionRuleDtoList)) {
                        targetRuleDto = new DataPermissionRuleDto();
                        targetRuleDto.setModuleCode(moduleCode);
                        targetRuleDto.setScopeType(DataPermissionScopeTypeEnum.ORG_AND_SUB);
                    } else {
                        for (DataPermissionRuleDto ruleDto : dataPermissionRuleDtoList) {
                            if (ruleDto.getModuleCode().equals(moduleCode)) {
                                targetRuleDto = ruleDto;
                                break;
                            }

                        }
                    }
                    if (null == targetRuleDto) {
                        throw new IamPermissionBusinessException("iam.permission.data.permissionRuleNotExists", "数据权限对应规则不存在");
                    }
                    targetRuleDtoList.add(targetRuleDto);
                }
            }
            if (CollectionUtil.isEmpty(targetRuleDtoList)) {
                DataPermissionRuleDto targetRuleDto = new DataPermissionRuleDto();
                targetRuleDto.setModuleCode(moduleCode);
                targetRuleDto.setScopeType(DataPermissionScopeTypeEnum.ORG_AND_SUB);
                targetRuleDtoList.add(targetRuleDto);
            }

            //包含全部则直接返回

            boolean all = false;
            int selfSize = 0;
            Set<String> organizationIdOrgAndSub = new HashSet<>();
            Set<String> organizationIdOrg = new HashSet<>();
            Set<String> organizationIdCustomer = new HashSet<>();
            for (DataPermissionRuleDto ruleDto : targetRuleDtoList) {

                DataPermissionScopeTypeEnum scopeType = ruleDto.getScopeType();

                if (DataPermissionScopeTypeEnum.ALL == scopeType) {
                    all = true;
                    break;
                } else if (DataPermissionScopeTypeEnum.ORG_AND_SUB == scopeType) {
                    if (!organizationIdOrgAndSub.isEmpty()) {
                        continue;
                    }
                    for (UserRoleTargetRelationEntity entity : relationEntityList) {
                        if (UserRoleTargetTypeEnum.ORGANIZATION == entity.getTargetType()) {
                            organizationIdOrgAndSub.add(entity.getTargetId());
                        }
                    }
                    //递归查询部门Id
                    organizationIdOrgAndSub.addAll(organizationCache.recursionListSubIds(organizationIdOrgAndSub));
                } else if (DataPermissionScopeTypeEnum.ORG == scopeType) {
                    if (!organizationIdOrg.isEmpty()) {
                        continue;
                    }

                    for (UserRoleTargetRelationEntity entity : relationEntityList) {
                        if (UserRoleTargetTypeEnum.ORGANIZATION == entity.getTargetType()) {
                            organizationIdOrg.add(entity.getTargetId());
                        }
                    }
                } else if (DataPermissionScopeTypeEnum.CUSTOM == scopeType) {
                    List<DataPermissionRuleDto.OrganizationNode> organizationNodeList = ruleDto.getOrganizationNodeList();
                    for (DataPermissionRuleDto.OrganizationNode organizationNode : organizationNodeList) {
                        if (OrganizationSelectTypeEnum.SELF == organizationNode.getSelectType()) {
                            organizationIdCustomer.add(organizationNode.getOrganizationId());
                        } else {
                            organizationIdCustomer.addAll(organizationCache.recursionListSubIds(Set.of(organizationNode.getOrganizationId())));
                        }
                    }
                } else if (DataPermissionScopeTypeEnum.SELF == scopeType) {
                    selfSize++;
                } else {
                    throw new IamPermissionBusinessException("iam.permission.data.scopeTypeNotExists", "数据权限选择范围不存在");
                }
            }

            if (all) {
                return new DataPermissionDto(DataPermissionResultTypeEnum.ALL);
            }

            if (selfSize == targetRuleDtoList.size()) {
                return new DataPermissionDto(DataPermissionResultTypeEnum.NONE);
            }

            DataPermissionDto dataPermissionDto = new DataPermissionDto(DataPermissionResultTypeEnum.PART);
            Set<String> organizationIdSet = new HashSet<>();
            Set<String> shopIdSet = new HashSet<>();
            Set<String> projectIdSet = new HashSet<>();

            //组装组织ID
            organizationIdSet.addAll(organizationIdOrgAndSub);
            organizationIdSet.addAll(organizationIdOrg);
            organizationIdSet.addAll(organizationIdCustomer);

            //门店、项目
            for (UserRoleTargetRelationEntity entity : relationEntityList) {
                if (UserRoleTargetTypeEnum.SHOP == entity.getTargetType()) {
                    shopIdSet.add(entity.getTargetId());
                }
            }

            //查询部门关联的门店Id
            List<String> shopIdList = shopOrganizationRelationClient.listShopIdByOrganizationIdSet(new IdSetRequest(organizationIdSet));
            if (CollectionUtil.isNotEmpty(shopIdList)) {
                shopIdSet.addAll(shopIdList);
            }

            //组装返回数据
            dataPermissionDto.setOrganizationIdSet(organizationIdSet);
            dataPermissionDto.setShopIdSet(shopIdSet);
            dataPermissionDto.setProjectIdSet(projectIdSet);

            return dataPermissionDto;
        }
    }

}
