package com.machine.service.hrm.department.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.leaf.IDataLeaf4RedisClient;
import com.machine.client.hrm.department.dto.input.HrmDepartmentCreateInputDto;
import com.machine.client.hrm.department.dto.output.*;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.service.hrm.department.dao.IDepartmentDao;
import com.machine.service.hrm.department.dao.IDepartmentExpansionDao;
import com.machine.service.hrm.department.dao.mapper.entity.DepartmentEntity;
import com.machine.service.hrm.department.dao.mapper.entity.DepartmentExpansionEntity;
import com.machine.service.hrm.department.service.IDepartmentService;
import com.machine.starter.redis.function.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.*;

import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Hrm.LOCK_HRM_DEPARTMENT_TREE;
import static com.machine.starter.redis.constant.RedisPrefix4HrmConstant.Department.HRM_DEPARTMENT_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4HrmConstant.Department.HRM_DEPARTMENT_TREE_KEY;

@Slf4j
@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IDepartmentDao departmentDao;

    @Autowired
    private IDepartmentExpansionDao departmentExpansionDao;

    @Autowired
    private IDataLeaf4RedisClient leaf4RedisClient;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(HrmDepartmentCreateInputDto inputDto) {
        //验证 parentId 是否存在
        DepartmentEntity entityById = departmentDao.getById(inputDto.getParentId());
        if (null == entityById) {
            throw new InvalidParameterException("父 ID 不存在");
        }

        //验证名称在同一层级是否存在
        DepartmentEntity entityByName = departmentDao.getByName(inputDto.getParentId(), inputDto.getName());
        if (null != entityByName) {
            throw new InvalidParameterException("部门名称已经存在");
        }

        DepartmentEntity insertEntity = new DepartmentEntity();
        insertEntity.setParentId(inputDto.getParentId());
        insertEntity.setName(inputDto.getName());
        insertEntity.setDescription(inputDto.getDescription());
        return departmentDao.insert(insertEntity);
    }

    @Override
    public HrmDepartmentDetailOutputDto detailById(IdRequest request) {
        DepartmentEntity entity = departmentDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), HrmDepartmentDetailOutputDto.class);
    }

    @Override
    public List<HrmDepartmentListOutputDto> listAll() {
        List<DepartmentEntity> entityList = departmentDao.listAll();
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), HrmDepartmentListOutputDto.class);
    }

    @Override
    public HrmDepartmentTreeOutputDto treeAllSimple() {
        //获取树的动态key
        String keyCode = customerRedisCommands.get(HRM_DEPARTMENT_TREE_KEY);

        //如果存在则直接返回数据
        if (StrUtil.isNotEmpty(keyCode)) {
            String treeJson = customerRedisCommands.get(HRM_DEPARTMENT_TREE_DATA + keyCode);
            if (StrUtil.isNotEmpty(treeJson)) {
                return JSONUtil.toBean(treeJson, HrmDepartmentTreeOutputDto.class);
            }
        }

        //缓存击穿
        RLock lock = redissonClient.getLock(LOCK_HRM_DEPARTMENT_TREE);
        try {
            lock.lock();

            keyCode = customerRedisCommands.get(HRM_DEPARTMENT_TREE_KEY);
            if (StrUtil.isNotEmpty(keyCode)) {
                String treeJson = customerRedisCommands.get(HRM_DEPARTMENT_TREE_DATA + keyCode);
                if (StrUtil.isNotEmpty(treeJson)) {
                    return JSONUtil.toBean(treeJson, HrmDepartmentTreeOutputDto.class);
                }
            }

            //重新生成树的动态key
            keyCode = leaf4RedisClient.hrmDepartmentTree();
            customerRedisCommands.set(HRM_DEPARTMENT_TREE_KEY, keyCode,24 * 60 * 60);

            //查询DB组装树
            List<DepartmentEntity> entityList = departmentDao.listAll();
            if (CollectionUtil.isEmpty(entityList)) {
                return null;
            }
            List<HrmDepartmentTreeOutputDto> outputDtoList = JSONUtil.toList(JSONUtil.toJsonStr(entityList), HrmDepartmentTreeOutputDto.class);
            HrmDepartmentTreeOutputDto treeOutputDto = TreeUtil.buildTree(outputDtoList).getFirst();

            //Tree 数据缓存到redis
            customerRedisCommands.set(HRM_DEPARTMENT_TREE_DATA + keyCode, JSONUtil.toJsonStr(treeOutputDto),24 * 60 * 60 + 60);

            return treeOutputDto;
        } finally {
            lock.unlock();
        }
    }


    @Override
    public Map<String, HrmDepartmentExpansionListOutputDto> mapDepartmentExpansionByDepartmentIdSet(IdSetRequest idSetRequest) {

        List<DepartmentExpansionEntity> departmentExpansionEntityList = departmentExpansionDao.listDepartmentExpansionByDepartmentIdSet(idSetRequest.getIdSet());
        if (CollectionUtil.isEmpty(departmentExpansionEntityList)) {
            return Map.of();
        }
        //根据DepartmentId拆分Map集合
        Map<String, HrmDepartmentExpansionListOutputDto> depMap = new HashMap<>();
        for (DepartmentExpansionEntity entity : departmentExpansionEntityList) {
            depMap.put(entity.getDepartmentId(), JSONUtil.toBean(JSONUtil.toJsonStr(entity), HrmDepartmentExpansionListOutputDto.class));
        }
        return depMap;
    }
}
