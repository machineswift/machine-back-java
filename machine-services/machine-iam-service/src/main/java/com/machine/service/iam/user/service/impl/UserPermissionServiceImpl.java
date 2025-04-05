package com.machine.service.iam.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.shop.IDataShopOrganizationRelationClient;
import com.machine.client.iam.user.dto.input.DataPermission4ManageInputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.iam.DataPermissionResultTypeEnum;
import com.machine.sdk.common.envm.iam.DataPermissionScopeTypeEnum;
import com.machine.sdk.common.envm.iam.UserRoleBusinessTypeEnum;
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
import com.machine.service.iam.user.dao.IUserOrganizationRelationDao;
import com.machine.service.iam.user.dao.IUserRoleBusinessRelationDao;
import com.machine.service.iam.user.dao.IUserRoleRelationDao;
import com.machine.service.iam.user.dao.mapper.entity.UserOrganizationRelationEntity;
import com.machine.service.iam.user.dao.mapper.entity.UserRoleBusinessRelationEntity;
import com.machine.service.iam.user.dao.mapper.entity.UserRoleRelationEntity;
import com.machine.service.iam.user.service.IUserPermissionService;
import com.machine.starter.redis.cache.RedisCacheIamOrganization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserPermissionServiceImpl implements IUserPermissionService {

    @Autowired
    private RedisCacheIamOrganization organizationCache;

    @Autowired
    private IPermissionDao permissionDao;

    @Autowired
    private IUserRoleRelationDao userRoleRelationDao;

    @Autowired
    private IRolePermissionRelationDao rolePermissionRelationDao;

    @Autowired
    private IUserOrganizationRelationDao userOrganizationRelationDao;

    @Autowired
    private IUserRoleBusinessRelationDao userRoleBusinessRelationDao;

    @Autowired
    private IDataShopOrganizationRelationClient shopOrganizationRelationClient;

    @Override
    public DataPermissionDto dataPermission4SuperApp() {
        String userId = AppContext.getContext().getUserId();

        Set<String> organizationIdSet = new HashSet<>();
        Set<String> shopIdSet = new HashSet<>();

        {//组织信息
            List<UserOrganizationRelationEntity> organizationRelationEntityList =
                    userOrganizationRelationDao.listByUserId(userId);
            if (CollectionUtil.isNotEmpty(organizationRelationEntityList)) {
                organizationIdSet = organizationRelationEntityList.stream()
                        .map(UserOrganizationRelationEntity::getOrganizationId).collect(Collectors.toSet());
            }
            //递归查询部门Id
            organizationIdSet.addAll(organizationCache.recursionListSubIds(organizationIdSet));
        }

        {//门店信息
            List<UserRoleRelationEntity> userRoleRelationEntityList = userRoleRelationDao.listByUserId(userId);

            if (CollectionUtil.isNotEmpty(userRoleRelationEntityList)) {
                Set<String> userRoleRelationIdSet = userRoleRelationEntityList.stream()
                        .map(UserRoleRelationEntity::getId).collect(Collectors.toSet());

                List<UserRoleBusinessRelationEntity> userRoleBusinessRelationEntityList =
                        userRoleBusinessRelationDao.listByUserRoleRelationIdSet(userRoleRelationIdSet);

                if (CollectionUtil.isNotEmpty(userRoleBusinessRelationEntityList)) {
                    for (UserRoleBusinessRelationEntity entity : userRoleBusinessRelationEntityList) {
                        if (UserRoleBusinessTypeEnum.SHOP == entity.getBusinessType()) {
                            shopIdSet.add(entity.getBusinessId());
                        }
                    }
                }
            }

            //查询部门关联的门店Id
            List<String> shopIdList = shopOrganizationRelationClient.listShopIdByOrganizationIdSet(new IdSetRequest(organizationIdSet));
            if (CollectionUtil.isNotEmpty(shopIdList)) {
                shopIdSet.addAll(shopIdList);
            }
        }

        //组装返回数据
        DataPermissionDto dataPermissionDto = new DataPermissionDto();
        dataPermissionDto.setOrganizationIdSet(organizationIdSet);
        dataPermissionDto.setShopIdSet(shopIdSet);

        return dataPermissionDto;
    }

    @Override
    public DataPermissionDto dataPermission4SuperManage(DataPermission4ManageInputDto inputDto) {
        String userId = AppContext.getContext().getUserId();

        String moduleCode = inputDto.getModuleCode();
        String dataPermissionCode = inputDto.getDataPermissionCode();
        PermissionEntity permissionEntity = permissionDao.getByCode(dataPermissionCode);
        String dataMetaInto = permissionEntity.getDataMetaInto();
        if (StrUtil.isBlank(dataMetaInto)) {
            //默认权限
            Set<String> organizationIdSet = new HashSet<>();
            Set<String> shopIdSet = new HashSet<>();

            {//组织信息
                List<UserOrganizationRelationEntity> organizationRelationEntityList =
                        userOrganizationRelationDao.listByUserId(userId);
                if (CollectionUtil.isNotEmpty(organizationRelationEntityList)) {
                    organizationIdSet = organizationRelationEntityList.stream()
                            .map(UserOrganizationRelationEntity::getOrganizationId).collect(Collectors.toSet());
                }
                //递归查询部门Id
                organizationIdSet.addAll(organizationCache.recursionListSubIds(organizationIdSet));
            }

            {//门店信息
                List<UserRoleRelationEntity> userRoleRelationEntityList = userRoleRelationDao.listByUserId(userId);

                if (CollectionUtil.isNotEmpty(userRoleRelationEntityList)) {
                    Set<String> userRoleRelationIdSet = userRoleRelationEntityList.stream()
                            .map(UserRoleRelationEntity::getId).collect(Collectors.toSet());

                    List<UserRoleBusinessRelationEntity> userRoleBusinessRelationEntityList =
                            userRoleBusinessRelationDao.listByUserRoleRelationIdSet(userRoleRelationIdSet);

                    if (CollectionUtil.isNotEmpty(userRoleBusinessRelationEntityList)) {
                        for (UserRoleBusinessRelationEntity entity : userRoleBusinessRelationEntityList) {
                            if (UserRoleBusinessTypeEnum.SHOP == entity.getBusinessType()) {
                                shopIdSet.add(entity.getBusinessId());
                            }
                        }
                    }
                }

                //查询部门关联的门店Id
                List<String> shopIdList = shopOrganizationRelationClient.listShopIdByOrganizationIdSet(new IdSetRequest(organizationIdSet));
                if (CollectionUtil.isNotEmpty(shopIdList)) {
                    shopIdSet.addAll(shopIdList);
                }
            }

            //组装返回数据
            DataPermissionDto dataPermissionDto = new DataPermissionDto();
            dataPermissionDto.setOrganizationIdSet(organizationIdSet);
            dataPermissionDto.setShopIdSet(shopIdSet);
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
            List<UserRoleRelationEntity> userRoleRelationEntityList = userRoleRelationDao.listByUserId(userId);
            for (UserRoleRelationEntity entity : userRoleRelationEntityList) {
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
                    List<UserOrganizationRelationEntity> organizationRelationEntityList =
                            userOrganizationRelationDao.listByUserId(userId);
                    if (CollectionUtil.isNotEmpty(organizationRelationEntityList)) {
                        organizationIdOrgAndSub = organizationRelationEntityList.stream()
                                .map(UserOrganizationRelationEntity::getOrganizationId).collect(Collectors.toSet());
                    }
                    //递归查询部门Id
                    organizationIdOrgAndSub.addAll(organizationCache.recursionListSubIds(organizationIdOrgAndSub));
                } else if (DataPermissionScopeTypeEnum.ORG == scopeType) {
                    if (!organizationIdOrg.isEmpty()) {
                        continue;
                    }

                    List<UserOrganizationRelationEntity> organizationRelationEntityList =
                            userOrganizationRelationDao.listByUserId(userId);
                    if (CollectionUtil.isNotEmpty(organizationRelationEntityList)) {
                        organizationIdOrg = organizationRelationEntityList.stream()
                                .map(UserOrganizationRelationEntity::getOrganizationId).collect(Collectors.toSet());
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

            //门店
            if (CollectionUtil.isNotEmpty(userRoleRelationEntityList)) {
                Set<String> userRoleRelationIdSet = userRoleRelationEntityList.stream()
                        .map(UserRoleRelationEntity::getId).collect(Collectors.toSet());

                List<UserRoleBusinessRelationEntity> userRoleBusinessRelationEntityList =
                        userRoleBusinessRelationDao.listByUserRoleRelationIdSet(userRoleRelationIdSet);

                if (CollectionUtil.isNotEmpty(userRoleBusinessRelationEntityList)) {
                    for (UserRoleBusinessRelationEntity entity : userRoleBusinessRelationEntityList) {
                        if (UserRoleBusinessTypeEnum.SHOP == entity.getBusinessType()) {
                            shopIdSet.add(entity.getBusinessId());
                        }
                    }
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

            return dataPermissionDto;
        }
    }

}
