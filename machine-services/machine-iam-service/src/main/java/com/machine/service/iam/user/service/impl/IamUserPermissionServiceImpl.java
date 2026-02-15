package com.machine.service.iam.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.shop.IDataShopOrganizationRelationClient;
import com.machine.client.iam.user.dto.input.IamDataPermission4ManageInputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.iam.permission.IamDataPermissionResultTypeEnum;
import com.machine.sdk.common.envm.iam.permission.IamDataPermissionScopeTypeEnum;
import com.machine.sdk.common.envm.iam.role.IamUserRoleBusinessTypeEnum;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationSelectTypeEnum;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import com.machine.sdk.common.exception.iam.IamPermissionBusinessException;
import com.machine.sdk.common.model.dto.iam.DataPermissionDto;
import com.machine.sdk.common.model.dto.iam.DataPermissionMetaDto;
import com.machine.sdk.common.model.dto.iam.DataPermissionRuleDto;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.tool.JsonUtil;
import com.machine.service.iam.permission.dao.IIamPermissionDao;
import com.machine.service.iam.permission.dao.mapper.entity.IamPermissionEntity;
import com.machine.service.iam.role.dao.IIamRoleDao;
import com.machine.service.iam.role.dao.IIamRolePermissionRelationDao;
import com.machine.service.iam.role.dao.mapper.entity.IamRoleEntity;
import com.machine.service.iam.role.dao.mapper.entity.IamRolePermissionRelationEntity;
import com.machine.service.iam.user.dao.IIamUserOrganizationRelationDao;
import com.machine.service.iam.user.dao.IIamUserRoleBusinessRelationDao;
import com.machine.service.iam.user.dao.IIamUserRoleRelationDao;
import com.machine.service.iam.user.dao.mapper.entity.IamUserOrganizationRelationEntity;
import com.machine.service.iam.user.dao.mapper.entity.IamUserRoleBusinessRelationEntity;
import com.machine.service.iam.user.dao.mapper.entity.IamUserRoleRelationEntity;
import com.machine.service.iam.user.service.IIamUserPermissionService;
import com.machine.starter.redis.cache.iam.RedisCacheIamOrganization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.machine.sdk.common.constant.CommonConstant.EMPTY_LIST_STR;
import static com.machine.sdk.common.constant.CommonIamConstant.DATA_PERMISSION_DEFAULT_FUNCTION_CODE;

@Slf4j
@Service
public class IamUserPermissionServiceImpl implements IIamUserPermissionService {

    @Autowired
    private RedisCacheIamOrganization organizationCache;

    @Autowired
    private IIamRoleDao roleDao;

    @Autowired
    private IIamPermissionDao permissionDao;

    @Autowired
    private IIamUserRoleRelationDao userRoleRelationDao;

    @Autowired
    private IIamRolePermissionRelationDao rolePermissionRelationDao;

    @Autowired
    private IIamUserOrganizationRelationDao userOrganizationRelationDao;

    @Autowired
    private IIamUserRoleBusinessRelationDao userRoleBusinessRelationDao;

    @Autowired
    private IDataShopOrganizationRelationClient shopOrganizationRelationClient;

    @Override
    public DataPermissionDto dataPermission4SuperApp() {
        String userId = AppContext.getContext().getUserId();

        Map<IamOrganizationTypeEnum, Set<String>> organizationRecursionIdMap = new HashMap<>();
        {//组织信息
            List<IamUserOrganizationRelationEntity> entityList = userOrganizationRelationDao.listByUserId(userId);
            Map<IamOrganizationTypeEnum, Set<String>> organizationIdMap = entityList.stream()
                    .collect(Collectors.groupingBy(
                            IamUserOrganizationRelationEntity::getOrganizationType,
                            Collectors.mapping(
                                    IamUserOrganizationRelationEntity::getOrganizationId,
                                    Collectors.toSet())));
            for (Map.Entry<IamOrganizationTypeEnum, Set<String>> entry : organizationIdMap.entrySet()) {
                IamOrganizationTypeEnum organizationType = entry.getKey();
                Set<String> organizationIds = entry.getValue();
                //递归查询组织Id
                organizationRecursionIdMap.put(organizationType, organizationCache.recursionListSubIds(organizationType, organizationIds));
            }
        }

        Set<String> shopIdSet = new HashSet<>();
        {//门店信息
            List<IamUserRoleRelationEntity> iamUserRoleRelationEntityList = userRoleRelationDao.listByUserId(userId);

            if (CollectionUtil.isNotEmpty(iamUserRoleRelationEntityList)) {
                Set<String> userRoleRelationIdSet = iamUserRoleRelationEntityList.stream()
                        .map(IamUserRoleRelationEntity::getId).collect(Collectors.toSet());

                List<IamUserRoleBusinessRelationEntity> iamUserRoleBusinessRelationEntityList =
                        userRoleBusinessRelationDao.listByUserRoleRelationIdSet(userRoleRelationIdSet);

                if (CollectionUtil.isNotEmpty(iamUserRoleBusinessRelationEntityList)) {
                    for (IamUserRoleBusinessRelationEntity entity : iamUserRoleBusinessRelationEntityList) {
                        if (IamUserRoleBusinessTypeEnum.SHOP == entity.getBusinessType()) {
                            shopIdSet.add(entity.getBusinessId());
                        }
                    }
                }
            }

            //查询部门关联的门店Id
            List<String> shopIdList = shopOrganizationRelationClient.listShopIdByOrganizationIdSet(new IdSetRequest(
                    organizationRecursionIdMap.values().stream().flatMap(Set::stream).collect(Collectors.toSet())));
            if (CollectionUtil.isNotEmpty(shopIdList)) {
                shopIdSet.addAll(shopIdList);
            }
        }

        //组装返回数据
        DataPermissionDto dataPermissionDto = new DataPermissionDto();
        dataPermissionDto.setOrganizationIdMap(organizationRecursionIdMap);
        dataPermissionDto.setShopIdSet(shopIdSet);

        return dataPermissionDto;
    }

    @Override
    public DataPermissionDto dataPermission4Manage(IamDataPermission4ManageInputDto inputDto) {
        String userId = AppContext.getContext().getUserId();

        String permissionCode = inputDto.getPermissionCode();
        String functionCode = inputDto.getFunctionCode();
        String dataPermissionCode = inputDto.getDataPermissionCode();
        IamPermissionEntity iamPermissionEntity = permissionDao.getByCode(dataPermissionCode);

        String dataMetaInto = iamPermissionEntity.getDataMetaInto();
        if (StrUtil.isBlank(dataMetaInto) || EMPTY_LIST_STR.equals(dataMetaInto)) {
            //默认规则（取所有角色和角色关联权限的规则信息）

            if (!DATA_PERMISSION_DEFAULT_FUNCTION_CODE.equals(functionCode)) {
                throw new IamPermissionBusinessException("iam.permission.data.permissionCodeWrong", "权限编码错误");
            }

            boolean all = false;
            int selfSize = 0;
            Map<IamOrganizationTypeEnum, Set<String>> organizationIdOrgAndSubMap = new HashMap<>();
            Map<IamOrganizationTypeEnum, Set<String>> organizationIdOrgMap = new HashMap<>();
            Map<IamOrganizationTypeEnum, Set<String>> organizationIdCustomerMap = new HashMap<>();

            //查询当前权限关联的角色
            List<IamRolePermissionRelationEntity> relationEntityList = rolePermissionRelationDao.selectByPermissionCode(permissionCode);
            for (IamRolePermissionRelationEntity relationEntity : relationEntityList) {
                IamRoleEntity iamRoleEntity = roleDao.getById(relationEntity.getRoleId());
                String dataPermissionRule = iamRoleEntity.getDataPermissionRule();
                if (StrUtil.isNotBlank(dataPermissionRule)) {
                    DataPermissionRuleDto dataPermissionRuleDto = JsonUtil.safeToBean(dataPermissionRule, DataPermissionRuleDto.class);
                    IamDataPermissionScopeTypeEnum scopeType = IamDataPermissionScopeTypeEnum.valueOf(dataPermissionRuleDto.getScopeCode());

                    if (IamDataPermissionScopeTypeEnum.ALL == scopeType) {
                        all = true;
                        break;
                    } else if (IamDataPermissionScopeTypeEnum.ORG_AND_SUB == scopeType) {
                        if (CollectionUtil.isNotEmpty(organizationIdOrgAndSubMap)) {
                            continue;
                        }
                        List<IamUserOrganizationRelationEntity> entityList = userOrganizationRelationDao.listByUserId(userId);
                        Map<IamOrganizationTypeEnum, Set<String>> organizationIdMap = entityList.stream()
                                .collect(Collectors.groupingBy(
                                        IamUserOrganizationRelationEntity::getOrganizationType,
                                        Collectors.mapping(
                                                IamUserOrganizationRelationEntity::getOrganizationId,
                                                Collectors.toSet())));
                        for (Map.Entry<IamOrganizationTypeEnum, Set<String>> entry : organizationIdMap.entrySet()) {
                            IamOrganizationTypeEnum organizationType = entry.getKey();
                            Set<String> organizationIds = entry.getValue();
                            //递归查询组织Id
                            organizationIdOrgAndSubMap.put(organizationType, organizationCache.recursionListSubIds(organizationType, organizationIds));
                        }
                    } else if (IamDataPermissionScopeTypeEnum.ORG == scopeType) {
                        if (CollectionUtil.isNotEmpty(organizationIdOrgMap)) {
                            continue;
                        }
                        List<IamUserOrganizationRelationEntity> entityList = userOrganizationRelationDao.listByUserId(userId);
                        organizationIdOrgMap = entityList.stream()
                                .collect(Collectors.groupingBy(
                                        IamUserOrganizationRelationEntity::getOrganizationType,
                                        Collectors.mapping(
                                                IamUserOrganizationRelationEntity::getOrganizationId,
                                                Collectors.toSet())));
                    } else if (IamDataPermissionScopeTypeEnum.CUSTOM == scopeType) {
                        Map<IamOrganizationTypeEnum, DataPermissionRuleDto.OrganizationNode>
                                organizationNodeMap = dataPermissionRuleDto.getOrganizationNodeMap();

                        for (Map.Entry<IamOrganizationTypeEnum, DataPermissionRuleDto.OrganizationNode> entry : organizationNodeMap.entrySet()) {
                            IamOrganizationTypeEnum organizationType = entry.getKey();
                            Set<String> customerOrganizationIdSet = organizationIdCustomerMap.computeIfAbsent(organizationType, k -> new HashSet<>());
                            if (IamOrganizationSelectTypeEnum.SELF == entry.getValue().getSelectType()) {
                                customerOrganizationIdSet.addAll(entry.getValue().getOrganizationIdSet());
                            } else {
                                customerOrganizationIdSet.addAll(organizationCache.recursionListSubIds(entry.getValue().getOrganizationIdSet()));
                            }
                        }
                    } else if (IamDataPermissionScopeTypeEnum.SELF == scopeType) {
                        selfSize++;
                    }
                } else {
                    //默认(ORG_AND_SUB:本组织及下级组织数据)
                    if (CollectionUtil.isNotEmpty(organizationIdOrgAndSubMap)) {
                        continue;
                    }
                    List<IamUserOrganizationRelationEntity> entityList = userOrganizationRelationDao.listByUserId(userId);
                    Map<IamOrganizationTypeEnum, Set<String>> organizationIdMap = entityList.stream()
                            .collect(Collectors.groupingBy(
                                    IamUserOrganizationRelationEntity::getOrganizationType,
                                    Collectors.mapping(
                                            IamUserOrganizationRelationEntity::getOrganizationId,
                                            Collectors.toSet())));
                    for (Map.Entry<IamOrganizationTypeEnum, Set<String>> entry : organizationIdMap.entrySet()) {
                        IamOrganizationTypeEnum organizationType = entry.getKey();
                        Set<String> organizationIds = entry.getValue();
                        //递归查询组织Id
                        organizationIdOrgAndSubMap.put(organizationType, organizationCache.recursionListSubIds(organizationType, organizationIds));
                    }
                }
            }
            if (all) {
                return new DataPermissionDto(IamDataPermissionResultTypeEnum.ALL);
            }
            if (selfSize == relationEntityList.size()) {
                return new DataPermissionDto(IamDataPermissionResultTypeEnum.NONE);
            }

            //数据合并
            Map<IamOrganizationTypeEnum, Set<String>> organizationIdAllMap = Stream.of(
                            organizationIdOrgAndSubMap,
                            organizationIdOrgMap,
                            organizationIdCustomerMap
                    )
                    .flatMap(map -> map.entrySet().stream())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (set1, set2) -> {
                                Set<String> mergedSet = new HashSet<>(set1);
                                mergedSet.addAll(set2);
                                return mergedSet;
                            }
                    ));


            Set<String> shopIdSet = new HashSet<>();
            {//门店信息
                List<IamUserRoleRelationEntity> iamUserRoleRelationEntityList = userRoleRelationDao.listByUserId(userId);

                if (CollectionUtil.isNotEmpty(iamUserRoleRelationEntityList)) {
                    Set<String> userRoleRelationIdSet = iamUserRoleRelationEntityList.stream()
                            .map(IamUserRoleRelationEntity::getId).collect(Collectors.toSet());

                    List<IamUserRoleBusinessRelationEntity> iamUserRoleBusinessRelationEntityList =
                            userRoleBusinessRelationDao.listByUserRoleRelationIdSet(userRoleRelationIdSet);

                    if (CollectionUtil.isNotEmpty(iamUserRoleBusinessRelationEntityList)) {
                        for (IamUserRoleBusinessRelationEntity entity : iamUserRoleBusinessRelationEntityList) {
                            if (IamUserRoleBusinessTypeEnum.SHOP == entity.getBusinessType()) {
                                shopIdSet.add(entity.getBusinessId());
                            }
                        }
                    }
                }

                //查询部门关联的门店Id
                List<String> shopIdList = shopOrganizationRelationClient.listShopIdByOrganizationIdSet(new IdSetRequest(
                        organizationIdAllMap.values().stream().flatMap(Set::stream).collect(Collectors.toSet())));
                if (CollectionUtil.isNotEmpty(shopIdList)) {
                    shopIdSet.addAll(shopIdList);
                }
            }

            //组装返回数据
            DataPermissionDto dataPermissionDto = new DataPermissionDto();
            dataPermissionDto.setResultType(IamDataPermissionResultTypeEnum.PART);
            dataPermissionDto.setOrganizationIdMap(organizationIdAllMap);
            dataPermissionDto.setShopIdSet(shopIdSet);
            return dataPermissionDto;
        } else {
            //指定权限
            List<DataPermissionMetaDto> metaDtoList = JSONUtil.toList(dataMetaInto, DataPermissionMetaDto.class);
            DataPermissionMetaDto targetMetaDto = null;
            for (DataPermissionMetaDto metaDto : metaDtoList) {
                if (metaDto.getFunctionCode().equals(functionCode)) {
                    targetMetaDto = metaDto;
                }
            }
            if (targetMetaDto == null) {
                throw new IamPermissionBusinessException("iam.permission.data.targetMetaNotExists", "数据权限对应功能不存在");
            }

            List<DataPermissionMetaDto.Scope> scopeList = targetMetaDto.getScopeList();

            //如果包含非标准的scope直接返回(标准scope:IamDataPermissionScopeTypeEnum)
            for (DataPermissionMetaDto.Scope scopeDto : scopeList) {
                String scopeCode = scopeDto.getScopeCode();

                boolean contains = false;
                for (IamDataPermissionScopeTypeEnum scopeType : IamDataPermissionScopeTypeEnum.values()) {
                    if (scopeType.getName().equals(scopeCode)) {
                        contains = true;
                        break;
                    }
                }
                if (!contains) {
                    //自定义数据权限
                    return new DataPermissionDto(IamDataPermissionResultTypeEnum.CUSTOMER);
                }
            }

            //查询当前权限关联的角色
            List<DataPermissionRuleDto> targetRuleDtoList = new ArrayList<>();
            List<IamRolePermissionRelationEntity> relationEntityList = rolePermissionRelationDao.selectByPermissionCode(permissionCode);
            for (IamRolePermissionRelationEntity relationEntity : relationEntityList) {
                IamRoleEntity iamRoleEntity = roleDao.getById(relationEntity.getRoleId());
                String dataPermissionRule = iamRoleEntity.getDataPermissionRule();
                String dataPermissionRules = relationEntity.getDataPermissionRules();
                if (StrUtil.isBlank(dataPermissionRules) || EMPTY_LIST_STR.equals(dataPermissionRules)) {
                    //没有配置则使用角色的规则
                    if (StrUtil.isBlank(dataPermissionRule)) {
                        //角色没有配置则是默认配置
                        DataPermissionRuleDto targetRuleDto = new DataPermissionRuleDto();
                        targetRuleDto.setFunctionCode(functionCode);
                        targetRuleDto.setScopeCode(IamDataPermissionScopeTypeEnum.ORG_AND_SUB.getName());
                        targetRuleDtoList.add(targetRuleDto);
                    } else {
                        DataPermissionRuleDto targetRuleDto = JsonUtil.safeToBean(dataPermissionRule, DataPermissionRuleDto.class);
                        targetRuleDto.setFunctionCode(functionCode);
                        targetRuleDtoList.add(targetRuleDto);
                    }
                } else {
                    DataPermissionRuleDto targetRuleDto = null;
                    List<DataPermissionRuleDto> dataPermissionRuleDtoList = JsonUtil.safeToList(dataPermissionRules, DataPermissionRuleDto.class);
                    for (DataPermissionRuleDto dto : dataPermissionRuleDtoList) {
                        if (dto.getFunctionCode().equals(functionCode)) {
                            targetRuleDto = dto;
                            break;
                        }
                    }

                    if (null != targetRuleDto) {
                        targetRuleDtoList.add(targetRuleDto);
                    } else {
                        //没有配置则使用角色的规则
                        if (StrUtil.isBlank(dataPermissionRule)) {
                            //角色没有配置则是默认配置
                            targetRuleDto = new DataPermissionRuleDto();
                            targetRuleDto.setFunctionCode(functionCode);
                            targetRuleDto.setScopeCode(IamDataPermissionScopeTypeEnum.ORG_AND_SUB.getName());
                            targetRuleDtoList.add(targetRuleDto);
                        } else {
                            targetRuleDto = JsonUtil.safeToBean(dataPermissionRule, DataPermissionRuleDto.class);
                            targetRuleDto.setFunctionCode(functionCode);
                            targetRuleDtoList.add(targetRuleDto);
                        }
                    }
                }
            }

            boolean all = false;
            int selfSize = 0;
            Map<IamOrganizationTypeEnum, Set<String>> organizationIdOrgAndSubMap = new HashMap<>();
            Map<IamOrganizationTypeEnum, Set<String>> organizationIdOrgMap = new HashMap<>();
            Map<IamOrganizationTypeEnum, Set<String>> organizationIdCustomerMap = new HashMap<>();


            for (DataPermissionRuleDto dataPermissionRuleDto : targetRuleDtoList) {
                IamDataPermissionScopeTypeEnum scopeType = IamDataPermissionScopeTypeEnum.valueOf(dataPermissionRuleDto.getScopeCode());

                if (IamDataPermissionScopeTypeEnum.ALL == scopeType) {
                    all = true;
                    break;
                } else if (IamDataPermissionScopeTypeEnum.ORG_AND_SUB == scopeType) {
                    if (CollectionUtil.isNotEmpty(organizationIdOrgAndSubMap)) {
                        continue;
                    }
                    List<IamUserOrganizationRelationEntity> entityList = userOrganizationRelationDao.listByUserId(userId);
                    Map<IamOrganizationTypeEnum, Set<String>> organizationIdMap = entityList.stream()
                            .collect(Collectors.groupingBy(
                                    IamUserOrganizationRelationEntity::getOrganizationType,
                                    Collectors.mapping(
                                            IamUserOrganizationRelationEntity::getOrganizationId,
                                            Collectors.toSet())));
                    for (Map.Entry<IamOrganizationTypeEnum, Set<String>> entry : organizationIdMap.entrySet()) {
                        IamOrganizationTypeEnum organizationType = entry.getKey();
                        Set<String> organizationIds = entry.getValue();
                        //递归查询组织Id
                        organizationIdOrgAndSubMap.put(organizationType, organizationCache.recursionListSubIds(organizationType, organizationIds));
                    }
                } else if (IamDataPermissionScopeTypeEnum.ORG == scopeType) {
                    if (CollectionUtil.isNotEmpty(organizationIdOrgMap)) {
                        continue;
                    }
                    List<IamUserOrganizationRelationEntity> entityList = userOrganizationRelationDao.listByUserId(userId);
                    organizationIdOrgMap = entityList.stream()
                            .collect(Collectors.groupingBy(
                                    IamUserOrganizationRelationEntity::getOrganizationType,
                                    Collectors.mapping(
                                            IamUserOrganizationRelationEntity::getOrganizationId,
                                            Collectors.toSet())));
                } else if (IamDataPermissionScopeTypeEnum.CUSTOM == scopeType) {
                    Map<IamOrganizationTypeEnum, DataPermissionRuleDto.OrganizationNode>
                            organizationNodeMap = dataPermissionRuleDto.getOrganizationNodeMap();

                    for (Map.Entry<IamOrganizationTypeEnum, DataPermissionRuleDto.OrganizationNode> entry : organizationNodeMap.entrySet()) {
                        IamOrganizationTypeEnum organizationType = entry.getKey();
                        Set<String> customerOrganizationIdSet = organizationIdCustomerMap.computeIfAbsent(organizationType, k -> new HashSet<>());
                        if (IamOrganizationSelectTypeEnum.SELF == entry.getValue().getSelectType()) {
                            customerOrganizationIdSet.addAll(entry.getValue().getOrganizationIdSet());
                        } else {
                            customerOrganizationIdSet.addAll(organizationCache.recursionListSubIds(entry.getValue().getOrganizationIdSet()));
                        }
                    }
                } else if (IamDataPermissionScopeTypeEnum.SELF == scopeType) {
                    selfSize++;
                }
            }
            if (all) {
                return new DataPermissionDto(IamDataPermissionResultTypeEnum.ALL);
            }
            if (selfSize == relationEntityList.size()) {
                return new DataPermissionDto(IamDataPermissionResultTypeEnum.NONE);
            }

            //数据合并
            Map<IamOrganizationTypeEnum, Set<String>> organizationIdAllMap = Stream.of(
                            organizationIdOrgAndSubMap,
                            organizationIdOrgMap,
                            organizationIdCustomerMap
                    )
                    .flatMap(map -> map.entrySet().stream())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (set1, set2) -> {
                                Set<String> mergedSet = new HashSet<>(set1);
                                mergedSet.addAll(set2);
                                return mergedSet;
                            }
                    ));


            Set<String> shopIdSet = new HashSet<>();
            {//门店信息
                List<IamUserRoleRelationEntity> iamUserRoleRelationEntityList = userRoleRelationDao.listByUserId(userId);

                if (CollectionUtil.isNotEmpty(iamUserRoleRelationEntityList)) {
                    Set<String> userRoleRelationIdSet = iamUserRoleRelationEntityList.stream()
                            .map(IamUserRoleRelationEntity::getId).collect(Collectors.toSet());

                    List<IamUserRoleBusinessRelationEntity> iamUserRoleBusinessRelationEntityList =
                            userRoleBusinessRelationDao.listByUserRoleRelationIdSet(userRoleRelationIdSet);

                    if (CollectionUtil.isNotEmpty(iamUserRoleBusinessRelationEntityList)) {
                        for (IamUserRoleBusinessRelationEntity entity : iamUserRoleBusinessRelationEntityList) {
                            if (IamUserRoleBusinessTypeEnum.SHOP == entity.getBusinessType()) {
                                shopIdSet.add(entity.getBusinessId());
                            }
                        }
                    }
                }

                //查询部门关联的门店Id
                List<String> shopIdList = shopOrganizationRelationClient.listShopIdByOrganizationIdSet(new IdSetRequest(
                        organizationIdAllMap.values().stream().flatMap(Set::stream).collect(Collectors.toSet())));
                if (CollectionUtil.isNotEmpty(shopIdList)) {
                    shopIdSet.addAll(shopIdList);
                }
            }

            //组装返回数据
            DataPermissionDto dataPermissionDto = new DataPermissionDto();
            dataPermissionDto.setResultType(IamDataPermissionResultTypeEnum.PART);
            dataPermissionDto.setOrganizationIdMap(organizationIdAllMap);
            dataPermissionDto.setShopIdSet(shopIdSet);
            return dataPermissionDto;
        }
    }

}
