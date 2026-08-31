package com.machine.service.iam.biam.user.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.shop.IDataShopOrganizationRelationClient;
import com.machine.client.iam.biam.user.dto.input.BIamDataPermission4ManageInputDto;
import com.machine.sdk.base.context.AppContextHolder;
import com.machine.sdk.base.envm.biam.permission.BIamDataPermissionResultTypeEnum;
import com.machine.sdk.base.envm.biam.permission.BIamDataPermissionScopeTypeEnum;
import com.machine.sdk.base.envm.biam.role.BIamUserRoleBusinessTypeEnum;
import com.machine.sdk.base.envm.biam.organization.BIamOrganizationSelectTypeEnum;
import com.machine.sdk.base.envm.biam.organization.BIamOrganizationTypeEnum;
import com.machine.sdk.base.exception.biam.BIamPermissionBusinessException;
import com.machine.sdk.base.model.dto.biam.auth.BIamDataPermissionDto;
import com.machine.sdk.base.model.dto.biam.auth.BIamDataPermissionMetaDto;
import com.machine.sdk.base.model.dto.biam.auth.BIamDataPermissionRuleDto;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.tool.JsonUtil;
import com.machine.service.iam.biam.permission.dao.IBIamPermissionDao;
import com.machine.service.iam.biam.permission.dao.mapper.entity.BIamPermissionEntity;
import com.machine.service.iam.biam.role.dao.IBIamRoleDao;
import com.machine.service.iam.biam.role.dao.IBIamRolePermissionRelationDao;
import com.machine.service.iam.biam.role.dao.mapper.entity.BIamRoleEntity;
import com.machine.service.iam.biam.role.dao.mapper.entity.BIamRolePermissionRelationEntity;
import com.machine.service.iam.biam.user.dao.IBIamUserOrganizationRelationDao;
import com.machine.service.iam.biam.user.dao.IBIamUserRoleBusinessRelationDao;
import com.machine.service.iam.biam.user.dao.IBIamUserRoleRelationDao;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserOrganizationRelationEntity;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserRoleBusinessRelationEntity;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserRoleRelationEntity;
import com.machine.service.iam.biam.user.service.IBIamUserPermissionService;
import com.machine.starter.redis.cache.biam.RedisBIamOrganizationCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.machine.sdk.base.constant.CommonConstant.EMPTY_LIST_STR;
import static com.machine.sdk.base.constant.CommonBIamConstant.DATA_PERMISSION_DEFAULT_FUNCTION_CODE;

@Slf4j
@Service
public class BIamUserPermissionServiceImpl implements IBIamUserPermissionService {

    @Autowired
    private RedisBIamOrganizationCache organizationCache;

    @Autowired
    private IBIamRoleDao roleDao;

    @Autowired
    private IBIamPermissionDao permissionDao;

    @Autowired
    private IBIamUserRoleRelationDao userRoleRelationDao;

    @Autowired
    private IBIamRolePermissionRelationDao rolePermissionRelationDao;

    @Autowired
    private IBIamUserOrganizationRelationDao userOrganizationRelationDao;

    @Autowired
    private IBIamUserRoleBusinessRelationDao userRoleBusinessRelationDao;

    @Autowired
    private IDataShopOrganizationRelationClient shopOrganizationRelationClient;

    @Override
    public BIamDataPermissionDto dataPermission4SuperApp() {
        String userId = AppContextHolder.getContext().getUserId();

        Map<BIamOrganizationTypeEnum, Set<String>> organizationRecursionIdMap = new HashMap<>();
        {//组织信息
            List<BIamUserOrganizationRelationEntity> entityList = userOrganizationRelationDao.listByUserId(userId);
            Map<BIamOrganizationTypeEnum, Set<String>> organizationIdMap = entityList.stream()
                    .collect(Collectors.groupingBy(
                            BIamUserOrganizationRelationEntity::getOrganizationType,
                            Collectors.mapping(
                                    BIamUserOrganizationRelationEntity::getOrganizationId,
                                    Collectors.toSet())));
            for (Map.Entry<BIamOrganizationTypeEnum, Set<String>> entry : organizationIdMap.entrySet()) {
                BIamOrganizationTypeEnum organizationType = entry.getKey();
                Set<String> organizationIds = entry.getValue();
                //递归查询组织Id
                organizationRecursionIdMap.put(organizationType, organizationCache.recursionListSubIds(organizationType, organizationIds));
            }
        }

        Set<String> shopIdSet = new HashSet<>();
        {//门店信息
            List<BIamUserRoleRelationEntity> iamUserRoleRelationEntityList = userRoleRelationDao.listByUserId(userId);

            if (CollectionUtil.isNotEmpty(iamUserRoleRelationEntityList)) {
                Set<String> userRoleRelationIdSet = iamUserRoleRelationEntityList.stream()
                        .map(BIamUserRoleRelationEntity::getId).collect(Collectors.toSet());

                List<BIamUserRoleBusinessRelationEntity> iamUserRoleBusinessRelationEntityList =
                        userRoleBusinessRelationDao.listByUserRoleRelationIdSet(userRoleRelationIdSet);

                if (CollectionUtil.isNotEmpty(iamUserRoleBusinessRelationEntityList)) {
                    for (BIamUserRoleBusinessRelationEntity entity : iamUserRoleBusinessRelationEntityList) {
                        if (BIamUserRoleBusinessTypeEnum.SHOP == entity.getBusinessType()) {
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
        BIamDataPermissionDto dataPermissionDto = new BIamDataPermissionDto();
        dataPermissionDto.setOrganizationIdMap(organizationRecursionIdMap);
        dataPermissionDto.setShopIdSet(shopIdSet);

        return dataPermissionDto;
    }

    @Override
    public BIamDataPermissionDto dataPermission4Manage(BIamDataPermission4ManageInputDto inputDto) {
        String userId = AppContextHolder.getContext().getUserId();

        String permissionCode = inputDto.getPermissionCode();
        String functionCode = inputDto.getFunctionCode();
        String dataPermissionCode = inputDto.getDataPermissionCode();
        BIamPermissionEntity iamPermissionEntity = permissionDao.getByCode(dataPermissionCode);

        String dataMetaInto = iamPermissionEntity.getDataMetaInto();
        if (StrUtil.isBlank(dataMetaInto) || EMPTY_LIST_STR.equals(dataMetaInto)) {
            //默认规则（取所有角色和角色关联权限的规则信息）

            if (!DATA_PERMISSION_DEFAULT_FUNCTION_CODE.equals(functionCode)) {
                throw new BIamPermissionBusinessException("iam.permission.data.permissionCodeWrong", "权限编码错误");
            }

            boolean all = false;
            int selfSize = 0;
            Map<BIamOrganizationTypeEnum, Set<String>> organizationIdOrgAndSubMap = new HashMap<>();
            Map<BIamOrganizationTypeEnum, Set<String>> organizationIdOrgMap = new HashMap<>();
            Map<BIamOrganizationTypeEnum, Set<String>> organizationIdCustomerMap = new HashMap<>();

            //查询当前权限关联的角色
            List<BIamRolePermissionRelationEntity> relationEntityList = rolePermissionRelationDao.selectByPermissionCode(permissionCode);
            for (BIamRolePermissionRelationEntity relationEntity : relationEntityList) {
                BIamRoleEntity iamRoleEntity = roleDao.getById(relationEntity.getRoleId());
                String dataPermissionRule = iamRoleEntity.getDataPermissionRule();
                if (StrUtil.isNotBlank(dataPermissionRule)) {
                    BIamDataPermissionRuleDto dataPermissionRuleDto = JsonUtil.safeToBean(dataPermissionRule, BIamDataPermissionRuleDto.class);
                    BIamDataPermissionScopeTypeEnum scopeType = BIamDataPermissionScopeTypeEnum.valueOf(dataPermissionRuleDto.getScopeCode());

                    if (BIamDataPermissionScopeTypeEnum.ALL == scopeType) {
                        all = true;
                        break;
                    } else if (BIamDataPermissionScopeTypeEnum.ORG_AND_SUB == scopeType) {
                        if (CollectionUtil.isNotEmpty(organizationIdOrgAndSubMap)) {
                            continue;
                        }
                        List<BIamUserOrganizationRelationEntity> entityList = userOrganizationRelationDao.listByUserId(userId);
                        Map<BIamOrganizationTypeEnum, Set<String>> organizationIdMap = entityList.stream()
                                .collect(Collectors.groupingBy(
                                        BIamUserOrganizationRelationEntity::getOrganizationType,
                                        Collectors.mapping(
                                                BIamUserOrganizationRelationEntity::getOrganizationId,
                                                Collectors.toSet())));
                        for (Map.Entry<BIamOrganizationTypeEnum, Set<String>> entry : organizationIdMap.entrySet()) {
                            BIamOrganizationTypeEnum organizationType = entry.getKey();
                            Set<String> organizationIds = entry.getValue();
                            //递归查询组织Id
                            organizationIdOrgAndSubMap.put(organizationType, organizationCache.recursionListSubIds(organizationType, organizationIds));
                        }
                    } else if (BIamDataPermissionScopeTypeEnum.ORG == scopeType) {
                        if (CollectionUtil.isNotEmpty(organizationIdOrgMap)) {
                            continue;
                        }
                        List<BIamUserOrganizationRelationEntity> entityList = userOrganizationRelationDao.listByUserId(userId);
                        organizationIdOrgMap = entityList.stream()
                                .collect(Collectors.groupingBy(
                                        BIamUserOrganizationRelationEntity::getOrganizationType,
                                        Collectors.mapping(
                                                BIamUserOrganizationRelationEntity::getOrganizationId,
                                                Collectors.toSet())));
                    } else if (BIamDataPermissionScopeTypeEnum.CUSTOM == scopeType) {
                        Map<BIamOrganizationTypeEnum, BIamDataPermissionRuleDto.OrganizationNode>
                                organizationNodeMap = dataPermissionRuleDto.getOrganizationNodeMap();

                        for (Map.Entry<BIamOrganizationTypeEnum, BIamDataPermissionRuleDto.OrganizationNode> entry : organizationNodeMap.entrySet()) {
                            BIamOrganizationTypeEnum organizationType = entry.getKey();
                            Set<String> customerOrganizationIdSet = organizationIdCustomerMap.computeIfAbsent(organizationType, k -> new HashSet<>());
                            if (BIamOrganizationSelectTypeEnum.SELF == entry.getValue().getSelectType()) {
                                customerOrganizationIdSet.addAll(entry.getValue().getOrganizationIdSet());
                            } else {
                                customerOrganizationIdSet.addAll(organizationCache.recursionListSubIds(entry.getValue().getOrganizationIdSet()));
                            }
                        }
                    } else if (BIamDataPermissionScopeTypeEnum.SELF == scopeType) {
                        selfSize++;
                    }
                } else {
                    //默认(ORG_AND_SUB:本组织及下级组织数据)
                    if (CollectionUtil.isNotEmpty(organizationIdOrgAndSubMap)) {
                        continue;
                    }
                    List<BIamUserOrganizationRelationEntity> entityList = userOrganizationRelationDao.listByUserId(userId);
                    Map<BIamOrganizationTypeEnum, Set<String>> organizationIdMap = entityList.stream()
                            .collect(Collectors.groupingBy(
                                    BIamUserOrganizationRelationEntity::getOrganizationType,
                                    Collectors.mapping(
                                            BIamUserOrganizationRelationEntity::getOrganizationId,
                                            Collectors.toSet())));
                    for (Map.Entry<BIamOrganizationTypeEnum, Set<String>> entry : organizationIdMap.entrySet()) {
                        BIamOrganizationTypeEnum organizationType = entry.getKey();
                        Set<String> organizationIds = entry.getValue();
                        //递归查询组织Id
                        organizationIdOrgAndSubMap.put(organizationType, organizationCache.recursionListSubIds(organizationType, organizationIds));
                    }
                }
            }
            if (all) {
                return new BIamDataPermissionDto(BIamDataPermissionResultTypeEnum.ALL);
            }
            if (selfSize == relationEntityList.size()) {
                return new BIamDataPermissionDto(BIamDataPermissionResultTypeEnum.NONE);
            }

            //数据合并
            Map<BIamOrganizationTypeEnum, Set<String>> organizationIdAllMap = Stream.of(
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
                List<BIamUserRoleRelationEntity> iamUserRoleRelationEntityList = userRoleRelationDao.listByUserId(userId);

                if (CollectionUtil.isNotEmpty(iamUserRoleRelationEntityList)) {
                    Set<String> userRoleRelationIdSet = iamUserRoleRelationEntityList.stream()
                            .map(BIamUserRoleRelationEntity::getId).collect(Collectors.toSet());

                    List<BIamUserRoleBusinessRelationEntity> iamUserRoleBusinessRelationEntityList =
                            userRoleBusinessRelationDao.listByUserRoleRelationIdSet(userRoleRelationIdSet);

                    if (CollectionUtil.isNotEmpty(iamUserRoleBusinessRelationEntityList)) {
                        for (BIamUserRoleBusinessRelationEntity entity : iamUserRoleBusinessRelationEntityList) {
                            if (BIamUserRoleBusinessTypeEnum.SHOP == entity.getBusinessType()) {
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
            BIamDataPermissionDto dataPermissionDto = new BIamDataPermissionDto();
            dataPermissionDto.setResultType(BIamDataPermissionResultTypeEnum.PART);
            dataPermissionDto.setOrganizationIdMap(organizationIdAllMap);
            dataPermissionDto.setShopIdSet(shopIdSet);
            return dataPermissionDto;
        } else {
            //指定权限
            List<BIamDataPermissionMetaDto> metaDtoList = JSONUtil.toList(dataMetaInto, BIamDataPermissionMetaDto.class);
            BIamDataPermissionMetaDto targetMetaDto = null;
            for (BIamDataPermissionMetaDto metaDto : metaDtoList) {
                if (metaDto.getFunctionCode().equals(functionCode)) {
                    targetMetaDto = metaDto;
                }
            }
            if (targetMetaDto == null) {
                throw new BIamPermissionBusinessException("iam.permission.data.targetMetaNotExists", "数据权限对应功能不存在");
            }

            List<BIamDataPermissionMetaDto.Scope> scopeList = targetMetaDto.getScopeList();

            //如果包含非标准的scope直接返回(标准scope:IamDataPermissionScopeTypeEnum)
            for (BIamDataPermissionMetaDto.Scope scopeDto : scopeList) {
                String scopeCode = scopeDto.getScopeCode();

                boolean contains = false;
                for (BIamDataPermissionScopeTypeEnum scopeType : BIamDataPermissionScopeTypeEnum.values()) {
                    if (scopeType.getName().equals(scopeCode)) {
                        contains = true;
                        break;
                    }
                }
                if (!contains) {
                    //自定义数据权限
                    return new BIamDataPermissionDto(BIamDataPermissionResultTypeEnum.CUSTOMER);
                }
            }

            //查询当前权限关联的角色
            List<BIamDataPermissionRuleDto> targetRuleDtoList = new ArrayList<>();
            List<BIamRolePermissionRelationEntity> relationEntityList = rolePermissionRelationDao.selectByPermissionCode(permissionCode);
            for (BIamRolePermissionRelationEntity relationEntity : relationEntityList) {
                BIamRoleEntity iamRoleEntity = roleDao.getById(relationEntity.getRoleId());
                String dataPermissionRule = iamRoleEntity.getDataPermissionRule();
                String dataPermissionRules = relationEntity.getDataPermissionRules();
                if (StrUtil.isBlank(dataPermissionRules) || EMPTY_LIST_STR.equals(dataPermissionRules)) {
                    //没有配置则使用角色的规则
                    if (StrUtil.isBlank(dataPermissionRule)) {
                        //角色没有配置则是默认配置
                        BIamDataPermissionRuleDto targetRuleDto = new BIamDataPermissionRuleDto();
                        targetRuleDto.setFunctionCode(functionCode);
                        targetRuleDto.setScopeCode(BIamDataPermissionScopeTypeEnum.ORG_AND_SUB.getName());
                        targetRuleDtoList.add(targetRuleDto);
                    } else {
                        BIamDataPermissionRuleDto targetRuleDto = JsonUtil.safeToBean(dataPermissionRule, BIamDataPermissionRuleDto.class);
                        targetRuleDto.setFunctionCode(functionCode);
                        targetRuleDtoList.add(targetRuleDto);
                    }
                } else {
                    BIamDataPermissionRuleDto targetRuleDto = null;
                    List<BIamDataPermissionRuleDto> dataPermissionRuleDtoList = JsonUtil.safeToList(dataPermissionRules, BIamDataPermissionRuleDto.class);
                    for (BIamDataPermissionRuleDto dto : dataPermissionRuleDtoList) {
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
                            targetRuleDto = new BIamDataPermissionRuleDto();
                            targetRuleDto.setFunctionCode(functionCode);
                            targetRuleDto.setScopeCode(BIamDataPermissionScopeTypeEnum.ORG_AND_SUB.getName());
                            targetRuleDtoList.add(targetRuleDto);
                        } else {
                            targetRuleDto = JsonUtil.safeToBean(dataPermissionRule, BIamDataPermissionRuleDto.class);
                            targetRuleDto.setFunctionCode(functionCode);
                            targetRuleDtoList.add(targetRuleDto);
                        }
                    }
                }
            }

            boolean all = false;
            int selfSize = 0;
            Map<BIamOrganizationTypeEnum, Set<String>> organizationIdOrgAndSubMap = new HashMap<>();
            Map<BIamOrganizationTypeEnum, Set<String>> organizationIdOrgMap = new HashMap<>();
            Map<BIamOrganizationTypeEnum, Set<String>> organizationIdCustomerMap = new HashMap<>();


            for (BIamDataPermissionRuleDto dataPermissionRuleDto : targetRuleDtoList) {
                BIamDataPermissionScopeTypeEnum scopeType = BIamDataPermissionScopeTypeEnum.valueOf(dataPermissionRuleDto.getScopeCode());

                if (BIamDataPermissionScopeTypeEnum.ALL == scopeType) {
                    all = true;
                    break;
                } else if (BIamDataPermissionScopeTypeEnum.ORG_AND_SUB == scopeType) {
                    if (CollectionUtil.isNotEmpty(organizationIdOrgAndSubMap)) {
                        continue;
                    }
                    List<BIamUserOrganizationRelationEntity> entityList = userOrganizationRelationDao.listByUserId(userId);
                    Map<BIamOrganizationTypeEnum, Set<String>> organizationIdMap = entityList.stream()
                            .collect(Collectors.groupingBy(
                                    BIamUserOrganizationRelationEntity::getOrganizationType,
                                    Collectors.mapping(
                                            BIamUserOrganizationRelationEntity::getOrganizationId,
                                            Collectors.toSet())));
                    for (Map.Entry<BIamOrganizationTypeEnum, Set<String>> entry : organizationIdMap.entrySet()) {
                        BIamOrganizationTypeEnum organizationType = entry.getKey();
                        Set<String> organizationIds = entry.getValue();
                        //递归查询组织Id
                        organizationIdOrgAndSubMap.put(organizationType, organizationCache.recursionListSubIds(organizationType, organizationIds));
                    }
                } else if (BIamDataPermissionScopeTypeEnum.ORG == scopeType) {
                    if (CollectionUtil.isNotEmpty(organizationIdOrgMap)) {
                        continue;
                    }
                    List<BIamUserOrganizationRelationEntity> entityList = userOrganizationRelationDao.listByUserId(userId);
                    organizationIdOrgMap = entityList.stream()
                            .collect(Collectors.groupingBy(
                                    BIamUserOrganizationRelationEntity::getOrganizationType,
                                    Collectors.mapping(
                                            BIamUserOrganizationRelationEntity::getOrganizationId,
                                            Collectors.toSet())));
                } else if (BIamDataPermissionScopeTypeEnum.CUSTOM == scopeType) {
                    Map<BIamOrganizationTypeEnum, BIamDataPermissionRuleDto.OrganizationNode>
                            organizationNodeMap = dataPermissionRuleDto.getOrganizationNodeMap();

                    for (Map.Entry<BIamOrganizationTypeEnum, BIamDataPermissionRuleDto.OrganizationNode> entry : organizationNodeMap.entrySet()) {
                        BIamOrganizationTypeEnum organizationType = entry.getKey();
                        Set<String> customerOrganizationIdSet = organizationIdCustomerMap.computeIfAbsent(organizationType, k -> new HashSet<>());
                        if (BIamOrganizationSelectTypeEnum.SELF == entry.getValue().getSelectType()) {
                            customerOrganizationIdSet.addAll(entry.getValue().getOrganizationIdSet());
                        } else {
                            customerOrganizationIdSet.addAll(organizationCache.recursionListSubIds(entry.getValue().getOrganizationIdSet()));
                        }
                    }
                } else if (BIamDataPermissionScopeTypeEnum.SELF == scopeType) {
                    selfSize++;
                }
            }
            if (all) {
                return new BIamDataPermissionDto(BIamDataPermissionResultTypeEnum.ALL);
            }
            if (selfSize == relationEntityList.size()) {
                return new BIamDataPermissionDto(BIamDataPermissionResultTypeEnum.NONE);
            }

            //数据合并
            Map<BIamOrganizationTypeEnum, Set<String>> organizationIdAllMap = Stream.of(
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
                List<BIamUserRoleRelationEntity> iamUserRoleRelationEntityList = userRoleRelationDao.listByUserId(userId);

                if (CollectionUtil.isNotEmpty(iamUserRoleRelationEntityList)) {
                    Set<String> userRoleRelationIdSet = iamUserRoleRelationEntityList.stream()
                            .map(BIamUserRoleRelationEntity::getId).collect(Collectors.toSet());

                    List<BIamUserRoleBusinessRelationEntity> iamUserRoleBusinessRelationEntityList =
                            userRoleBusinessRelationDao.listByUserRoleRelationIdSet(userRoleRelationIdSet);

                    if (CollectionUtil.isNotEmpty(iamUserRoleBusinessRelationEntityList)) {
                        for (BIamUserRoleBusinessRelationEntity entity : iamUserRoleBusinessRelationEntityList) {
                            if (BIamUserRoleBusinessTypeEnum.SHOP == entity.getBusinessType()) {
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
            BIamDataPermissionDto dataPermissionDto = new BIamDataPermissionDto();
            dataPermissionDto.setResultType(BIamDataPermissionResultTypeEnum.PART);
            dataPermissionDto.setOrganizationIdMap(organizationIdAllMap);
            dataPermissionDto.setShopIdSet(shopIdSet);
            return dataPermissionDto;
        }
    }

}
