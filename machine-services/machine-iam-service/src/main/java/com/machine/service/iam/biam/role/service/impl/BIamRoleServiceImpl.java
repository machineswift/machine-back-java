package com.machine.service.iam.biam.role.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.leaf.IDataLeaf4IamCodeClient;
import com.machine.client.iam.biam.permission.dto.output.BIamPermissionTreeOutputDto;
import com.machine.client.iam.biam.role.dto.input.*;
import com.machine.client.iam.biam.role.dto.output.BIamRoleDetailOutputDto;
import com.machine.client.iam.biam.role.dto.output.BIamRoleListOutputDto;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.envm.biam.permission.BIamDataPermissionScopeTypeEnum;
import com.machine.sdk.base.envm.biam.permission.BIamPermissionResourceTypeEnum;
import com.machine.sdk.base.envm.biam.permission.BIamPermissionTypeEnum;
import com.machine.sdk.base.envm.biam.role.BIamCompanyDefaultRoleEnum;
import com.machine.sdk.base.envm.biam.role.BIamOpenApiDefaultRoleEnum;
import com.machine.sdk.base.envm.biam.role.BIamShopDefaultRoleEnum;
import com.machine.sdk.base.envm.biam.role.BIamSupplierDefaultRoleEnum;
import com.machine.sdk.base.exception.biam.BIamBusinessException;
import com.machine.sdk.base.model.dto.biam.auth.BIamDataPermissionRuleDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.tool.JsonUtil;
import com.machine.service.iam.biam.role.dao.IBIamRoleDao;
import com.machine.service.iam.biam.role.dao.IBIamRolePermissionRelationDao;
import com.machine.service.iam.biam.role.dao.mapper.entity.BIamRoleEntity;
import com.machine.service.iam.biam.role.dao.mapper.entity.BIamRolePermissionRelationEntity;
import com.machine.service.iam.biam.role.service.IBIamRoleService;
import com.machine.service.iam.biam.user.dao.IBIamUserRoleRelationDao;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserRoleRelationEntity;
import com.machine.starter.redis.cache.biam.RedisBIamPermissionCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.machine.sdk.base.constant.CommonConstant.EMPTY_LIST_STR;
import static com.machine.sdk.base.constant.CommonBIamConstant.DATA_PERMISSION_DEFAULT_FUNCTION_CODE;

@Slf4j
@Service
public class BIamRoleServiceImpl implements IBIamRoleService {

    @Autowired
    private IDataLeaf4IamCodeClient leafClient;

    @Autowired
    private RedisBIamPermissionCache permissionCache;

    @Autowired
    private IBIamRoleDao roleDao;

    @Autowired
    private IBIamUserRoleRelationDao userRoleRelationDao;

    @Autowired
    private IBIamRolePermissionRelationDao rolePermissionRelationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(BIamRoleCreateInputDto inputDto) {
        //验证名称是否存在
        BIamRoleEntity entityByName = roleDao.getByName(inputDto.getName());
        if (null != entityByName) {
            throw new BIamBusinessException("iam.role.service.create.nameAlreadyExists", "角色名称已经存在");
        }

        //验证数据权限
        BIamDataPermissionRuleDto dataPermissionRule = inputDto.getDataPermissionRule();
        String functionCode = dataPermissionRule.getFunctionCode();
        if (!DATA_PERMISSION_DEFAULT_FUNCTION_CODE.equals(functionCode)) {
            throw new BIamBusinessException("iam.role.service.create.functionCodeWrong", "数据权限编码错误");
        }

        boolean contains = false;
        String scopeCode = dataPermissionRule.getScopeCode();
        for (BIamDataPermissionScopeTypeEnum e : BIamDataPermissionScopeTypeEnum.values()) {
            if (e.getName().equals(scopeCode)) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            throw new BIamBusinessException("iam.role.service.create.functionScopeCodeWrong", "数据权限范围编码错误");
        }

        if (BIamDataPermissionScopeTypeEnum.CUSTOM.getName().equals(scopeCode)) {
            if (CollectionUtil.isEmpty(dataPermissionRule.getOrganizationNodeMap())) {
                throw new BIamBusinessException("iam.role.service.create.dataPermissionEmpty", "自定义数据权限范围为空");
            }
        } else {
            if (CollectionUtil.isNotEmpty(dataPermissionRule.getOrganizationNodeMap())) {
                throw new BIamBusinessException("iam.role.service.create.dataPermissionNotEmpty", "数据权限范围不为空");
            }
        }

        BIamRoleEntity insertEntity = new BIamRoleEntity();
        insertEntity.setParentId(inputDto.getType().name().toLowerCase());
        insertEntity.setStatus(StatusEnum.ENABLE);

        //角色编码
        insertEntity.setCode(leafClient.roleCode());
        insertEntity.setType(inputDto.getType());
        insertEntity.setName(inputDto.getName());
        insertEntity.setDescription(inputDto.getDescription());
        insertEntity.setDataPermissionRule(JSONUtil.toJsonStr(dataPermissionRule));
        insertEntity.setSort(System.currentTimeMillis());
        return roleDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(IdRequest request) {
        BIamRoleEntity entity = roleDao.getById(request.getId());
        if (null == entity) {
            return 0;
        }

        if (isDefaultRole(entity.getCode())) {
            throw new BIamBusinessException("iam.role.service.delete.defaultRole", "默认角色，不能删除");
        }

        //子角色信息
        List<BIamRoleEntity> subList = roleDao.listSub(new BIamRoleListSubInputDto(entity.getId()));
        if (!CollectionUtil.isEmpty(subList)) {
            throw new BIamBusinessException("iam.role.service.delete.hasSubRole", "下面有子数据，不能删除");
        }

        //是否关联用户
        List<BIamUserRoleRelationEntity> iamUserRoleRelationEntityList = userRoleRelationDao
                .selectByRoleId(request.getId());
        if (!CollectionUtil.isEmpty(iamUserRoleRelationEntityList)) {
            throw new BIamBusinessException("iam.role.service.delete.associationUser", "角色关联用户，不能删除");
        }

        //删除角色和权限关系
        rolePermissionRelationDao.deleteByRoleId(request.getId());
        return roleDao.delete(request.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(BIamRoleUpdateInputDto inputDto) {
        BIamRoleEntity entity = roleDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        //验证数据权限
        BIamDataPermissionRuleDto dataPermissionRule = inputDto.getDataPermissionRule();
        String functionCode = dataPermissionRule.getFunctionCode();
        if (!DATA_PERMISSION_DEFAULT_FUNCTION_CODE.equals(functionCode)) {
            throw new BIamBusinessException("iam.role.service.update.functionCodeWrong", "数据权限编码错误");
        }

        boolean contains = false;
        String scopeCode = dataPermissionRule.getScopeCode();
        for (BIamDataPermissionScopeTypeEnum e : BIamDataPermissionScopeTypeEnum.values()) {
            if (e.getName().equals(scopeCode)) {
                contains = true;
                break;
            }
        }
        if (!contains) {
            throw new BIamBusinessException("iam.role.service.update.functionScopeCodeWrong", "数据权限范围编码错误");
        }

        if (BIamDataPermissionScopeTypeEnum.CUSTOM.getName().equals(scopeCode)) {
            if (CollectionUtil.isEmpty(dataPermissionRule.getOrganizationNodeMap())) {
                throw new BIamBusinessException("iam.role.service.update.dataPermissionEmpty", "自定义数据权限范围为空");
            }
        } else {
            if (CollectionUtil.isNotEmpty(dataPermissionRule.getOrganizationNodeMap())) {
                throw new BIamBusinessException("iam.role.service.update.dataPermissionNotEmpty", "数据权限范围不为空");
            }
        }

        //验证名称在同一层级是否存在
        BIamRoleEntity entityByName = roleDao.getByName(inputDto.getName());
        if (null != entityByName && !entityByName.getId().equals(entity.getId())) {
            throw new BIamBusinessException("iam.role.service.update.nameAlreadyExists", "角色名称已经存在");
        }

        //修改角色信息
        BIamRoleEntity updateEntity = new BIamRoleEntity();
        updateEntity.setId(inputDto.getId());
        if (!isDefaultRole(entity.getCode())) {
            //不是默认角色可以修改名称
            updateEntity.setName(inputDto.getName());
        }
        updateEntity.setDescription(inputDto.getDescription());
        updateEntity.setDataPermissionRule(JSONUtil.toJsonStr(dataPermissionRule));
        return roleDao.update(updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(BIamRoleUpdateStatusInputDto inputDto) {
        BIamRoleEntity entity = roleDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        if (entity.getStatus() == inputDto.getStatus()) {
            return 0;
        }

        if (isDefaultRole(entity.getCode())) {
            throw new BIamBusinessException("iam.role.service.updateStatus.defaultRole", "默认角色，不能修改状态");
        }

        return roleDao.updateStatus(inputDto.getId(), inputDto.getStatus());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePermission(BIamRoleUpdatePermissionInputDto inputDto) {
        //处理角色关联的权限信息
        List<BIamRolePermissionRelationEntity> insertEntityList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(inputDto.getPermissionIdSet())) {
            List<BIamPermissionTreeOutputDto> outputDtoList = permissionCache.listByIdSet(inputDto.getPermissionIdSet());
            if (CollectionUtil.isNotEmpty(outputDtoList)) {
                //新增角色关联的权限信息
                Map<String, List<BIamDataPermissionRuleDto>> dataPermissionRuleMap = inputDto.getDataPermissionRuleMap();
                long sort = System.currentTimeMillis();
                for (BIamPermissionTreeOutputDto outputDto : outputDtoList) {
                    BIamRolePermissionRelationEntity e = new BIamRolePermissionRelationEntity();
                    e.setRoleId(inputDto.getId());
                    e.setPermissionId(outputDto.getId());
                    e.setType(BIamPermissionTypeEnum.READ);
                    if (BIamPermissionResourceTypeEnum.MENU == outputDto.getResourceType()) {
                        //保存数据权限信息
                        if (null != dataPermissionRuleMap) {
                            List<BIamDataPermissionRuleDto> dataPermissionRuleList = dataPermissionRuleMap.get(outputDto.getId());
                            if (CollectionUtil.isNotEmpty(dataPermissionRuleList)) {
                                e.setDataPermissionRules(JSONUtil.toJsonStr(dataPermissionRuleList));
                            } else {
                                e.setDataPermissionRules(EMPTY_LIST_STR);
                            }
                        }
                    }
                    e.setSort(--sort);
                    insertEntityList.add(e);
                }
            }
        }

        //删除角色关联的权限信息
        rolePermissionRelationDao.deleteByRoleId(inputDto.getId());
        if (CollectionUtil.isNotEmpty(insertEntityList)) {
            rolePermissionRelationDao.insert(insertEntityList);
        }
    }

    @Override
    public BIamRoleDetailOutputDto detail(IdRequest request) {
        BIamRoleEntity entity = roleDao.getById(request.getId());
        if (null == entity) {
            return null;
        }

        BIamRoleDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity), BIamRoleDetailOutputDto.class, true);
        String dataPermissionRule = entity.getDataPermissionRule();
        if (StrUtil.isNotBlank(dataPermissionRule)) {
            outputDto.setDataPermissionRule(JsonUtil.safeToBean(dataPermissionRule, BIamDataPermissionRuleDto.class));
        }
        return outputDto;
    }

    @Override
    public List<String> listSubId(BIamRoleListSubInputDto inputDto) {
        return roleDao.listSubId(inputDto);
    }

    @Override
    public List<String> listParentByTarget(IdRequest request) {
        return roleDao.listParentByTarget(request.getId());
    }

    @Override
    public List<BIamRoleListOutputDto> listSub(BIamRoleListSubInputDto inputDto) {
        List<BIamRoleEntity> entityList = roleDao.listSub(inputDto);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), BIamRoleListOutputDto.class);
    }

    @Override
    public Page<BIamRoleListOutputDto> selectPage(BIamRoleQueryPageInputDto inputDto) {
        Page<BIamRoleEntity> page = roleDao.selectPage(inputDto);
        Page<BIamRoleListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), BIamRoleListOutputDto.class));
        return pageResult;
    }

    @Override
    public Map<String, BIamRoleDetailOutputDto> mapByIdSet(IdSetRequest request) {
        List<BIamRoleEntity> userEntityList = roleDao.selectByIdSet(request.getIdSet());
        return userEntityList.stream()
                .collect(Collectors.toMap(BIamRoleEntity::getId, entity -> {
                    BIamRoleDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(entity), BIamRoleDetailOutputDto.class, true);
                    String dataPermissionRule = entity.getDataPermissionRule();
                    if (StrUtil.isNotBlank(dataPermissionRule)) {
                        outputDto.setDataPermissionRule(JsonUtil.safeToBean(dataPermissionRule, BIamDataPermissionRuleDto.class));
                    }
                    return outputDto;
                }));
    }


    private boolean isDefaultRole(String code) {
        if (CollectionUtil.isNotEmpty(DEFAULT_ROLE_CODE_SET)) {
            return DEFAULT_ROLE_CODE_SET.contains(code);
        }
        synchronized (BIamRoleServiceImpl.class) {
            if (CollectionUtil.isNotEmpty(DEFAULT_ROLE_CODE_SET)) {
                return DEFAULT_ROLE_CODE_SET.contains(code);
            }
            DEFAULT_ROLE_CODE_SET.addAll(Arrays.stream(BIamCompanyDefaultRoleEnum.values())
                    .map(BIamCompanyDefaultRoleEnum::getCode).collect(Collectors.toSet()));
            DEFAULT_ROLE_CODE_SET.addAll(Arrays.stream(BIamShopDefaultRoleEnum.values())
                    .map(BIamShopDefaultRoleEnum::getCode).collect(Collectors.toSet()));
            DEFAULT_ROLE_CODE_SET.addAll(Arrays.stream(BIamSupplierDefaultRoleEnum.values())
                    .map(BIamSupplierDefaultRoleEnum::getCode).collect(Collectors.toSet()));
            DEFAULT_ROLE_CODE_SET.addAll(Arrays.stream(BIamOpenApiDefaultRoleEnum.values())
                    .map(BIamOpenApiDefaultRoleEnum::getCode).collect(Collectors.toSet()));
        }
        return DEFAULT_ROLE_CODE_SET.contains(code);
    }

    private static final Set<String> DEFAULT_ROLE_CODE_SET = new HashSet<>();
}
