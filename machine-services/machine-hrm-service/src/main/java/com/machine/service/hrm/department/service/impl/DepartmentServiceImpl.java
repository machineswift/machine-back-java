package com.machine.service.hrm.department.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.leaf.IDataLeaf4RedisClient;
import com.machine.client.hrm.department.dto.input.DepartmentCreateInputDto;
import com.machine.client.hrm.department.dto.output.DepartmentDetailOutputDto;
import com.machine.client.hrm.department.dto.output.DepartmentListOutputDto;
import com.machine.client.hrm.department.dto.output.DepartmentSimpleOutputDto;
import com.machine.client.hrm.department.dto.output.DepartmentTreeOutputDto;
import com.machine.sdk.beisen.client.organization.BeiSenOrganizationClient;
import com.machine.sdk.beisen.client.organization.dto.input.BeiSenOrganizationTimeWindowInputDto;
import com.machine.sdk.beisen.client.organization.dto.output.BeiSenOrganizationInfoOutputDto;
import com.machine.sdk.beisen.domain.BeiSenBaseResponse;
import com.machine.sdk.beisen.envm.BeiSenDepartmentStatusEnum;
import com.machine.sdk.common.envm.BaseEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.service.hrm.department.dao.IDepartmentDao;
import com.machine.service.hrm.department.dao.mapper.entity.DepartmentEntity;
import com.machine.service.hrm.department.service.IDepartmentService;
import com.machine.starter.redis.function.CustomerRedisCommands;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Hrm.HRM_DEPARTMENT_TREE_LOCK;
import static com.machine.starter.redis.constant.RedisPrefix4HrmConstant.Department.HRM_DEPARTMENT_TREE_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4HrmConstant.Department.HRM_DEPARTMENT_TREE_KEY;

@Slf4j
@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Resource(name = "beiSenOkHttpClient")
    private OkHttpClient okHttpClient;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private IDepartmentDao departmentDao;

    @Autowired
    private IDataLeaf4RedisClient leaf4RedisClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sync(long lastSyncTime,
                     long enSyncTime) {

        long nextSyncTime = lastSyncTime + 30 * 24 * 60 * 60 * 1000L;
        if (nextSyncTime > enSyncTime) {
            nextSyncTime = enSyncTime;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        long startTime = lastSyncTime;
        long stopTime = nextSyncTime;
        String scrollId = null;
        List<BeiSenOrganizationInfoOutputDto> outputDtoList = new ArrayList<>();

        while (true) {
            BeiSenOrganizationTimeWindowInputDto inputDto = new BeiSenOrganizationTimeWindowInputDto();
            inputDto.setStartTime(sdf.format(new Date(startTime)));
            inputDto.setStopTime(sdf.format(new Date(stopTime)));
            inputDto.setTimeWindowQueryType("2");
            inputDto.setScrollId(scrollId);
            BeiSenBaseResponse<BeiSenOrganizationInfoOutputDto> baseResponse = BeiSenOrganizationClient.getByTimeWindow(okHttpClient, inputDto);
            if (!CollectionUtil.isEmpty(baseResponse.getData())) {
                outputDtoList.addAll(baseResponse.getData());
            }

            Boolean isLastData = baseResponse.getIsLastData();
            if (isLastData || CollectionUtil.isEmpty(baseResponse.getData())) {
                scrollId = null;
                if (stopTime == enSyncTime) {
                    break;
                }
                startTime = stopTime;
                stopTime = startTime + 30 * 24 * 60 * 60 * 1000L;
                if (stopTime > enSyncTime) {
                    stopTime = enSyncTime;
                }
            } else {
                scrollId = baseResponse.getScrollId();
            }
        }

        if (CollectionUtil.isEmpty(outputDtoList)) {
            log.info("定时任务更新组织信息查询结果为空");
            return;
        }
        log.info("定时任务更新组织信息查询结果，size={}", outputDtoList.size());

        List<DepartmentCreateInputDto> inputDtoList = new ArrayList<>();
        for (BeiSenOrganizationInfoOutputDto outputDto : outputDtoList) {
            DepartmentCreateInputDto inputDto = new DepartmentCreateInputDto();
            inputDto.setId(outputDto.getOId() + "");
            inputDto.setParentId(outputDto.getPOIdOrgAdmin() + "");
            inputDto.setName(outputDto.getName());
            inputDto.setCode(outputDto.getCode());
            inputDto.setStatus(BaseEnum.getInstance(BeiSenDepartmentStatusEnum.class, outputDto.getStatus() + ""));
            inputDto.setEstablishDate(paraseTime(sdf, outputDto.getEstablishDate()));
            inputDto.setStarDate(paraseTime(sdf, outputDto.getStartDate()));
            inputDto.setStopTime(paraseTime(sdf, outputDto.getStopDate()));
            inputDto.setSort(outputDto.getOId().longValue());
            inputDtoList.add(inputDto);
        }

        List<DepartmentEntity> entityList = JSONUtil.toList(JSONUtil.toJsonStr(inputDtoList), DepartmentEntity.class);
        departmentDao.batchInsert(entityList);
    }

    @Override
    public void clearCache() {
        customerRedisCommands.del(HRM_DEPARTMENT_TREE_KEY);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DepartmentCreateInputDto inputDto) {
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
    public DepartmentDetailOutputDto detailById(IdRequest request) {
        DepartmentEntity entity = departmentDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), DepartmentDetailOutputDto.class);
    }

    @Override
    public List<DepartmentListOutputDto> listAll() {
        List<DepartmentEntity> entityList = departmentDao.listAll();
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DepartmentListOutputDto.class);
    }

    @Override
    public List<DepartmentSimpleOutputDto> listSub4Recursion(IdRequest request) {
        List<DepartmentEntity> entityList = departmentDao.listSub4Recursion(request.getId());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DepartmentSimpleOutputDto.class);
    }

    @Override
    public DepartmentTreeOutputDto treeAllSimple() {
        //获取树的动态key
        String keyCode = customerRedisCommands.get(HRM_DEPARTMENT_TREE_KEY);

        //如果存在则直接返回数据
        if (StrUtil.isNotEmpty(keyCode)) {
            String treeJson = customerRedisCommands.get(HRM_DEPARTMENT_TREE_DATA + keyCode);
            if (StrUtil.isNotEmpty(treeJson)) {
                return JSONUtil.toBean(treeJson, DepartmentTreeOutputDto.class);
            }
        }

        //缓存击穿
        RLock lock = redissonClient.getLock(HRM_DEPARTMENT_TREE_LOCK);
        try {
            lock.lock();

            keyCode = customerRedisCommands.get(HRM_DEPARTMENT_TREE_KEY);
            if (StrUtil.isNotEmpty(keyCode)) {
                String treeJson = customerRedisCommands.get(HRM_DEPARTMENT_TREE_DATA + keyCode);
                if (StrUtil.isNotEmpty(treeJson)) {
                    return JSONUtil.toBean(treeJson, DepartmentTreeOutputDto.class);
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
            List<DepartmentTreeOutputDto> outputDtoList = JSONUtil.toList(JSONUtil.toJsonStr(entityList), DepartmentTreeOutputDto.class);
            DepartmentTreeOutputDto treeOutputDto = TreeUtil.buildTree(outputDtoList).getFirst();

            //Tree 数据缓存到redis
            customerRedisCommands.set(HRM_DEPARTMENT_TREE_DATA + keyCode, JSONUtil.toJsonStr(treeOutputDto),24 * 60 * 60 + 60);

            return treeOutputDto;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 日期转换
     */
    private Long paraseTime(SimpleDateFormat sdf,
                            String source) {
        if (StrUtil.isBlank(source)) {
            return null;
        }
        try {
            Date date = sdf.parse(source);
            long time = date.getTime();
            if (time < 0) {
                return null;
            }
        } catch (ParseException e) {
            log.error("同步北森数据日期转化异常", e);
        }
        return null;
    }
}
