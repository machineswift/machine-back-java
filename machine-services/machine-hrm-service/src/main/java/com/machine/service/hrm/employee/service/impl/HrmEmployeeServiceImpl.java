package com.machine.service.hrm.employee.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.hrm.employee.dto.input.HrmEmployeeBatchInsertInputDto;
import com.machine.client.hrm.employee.dto.input.HrmEmployeeCreateInputDto;
import com.machine.client.hrm.employee.dto.input.HrmEmployeeQueryIListInputDto;
import com.machine.client.hrm.employee.dto.output.HrmEmployeeDetailOutputDto;
import com.machine.client.hrm.employee.dto.output.HrmEmployeeListOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.input.IamCompanyUserUpdate4BeiSenInputDto;
import com.machine.client.iam.user.dto.input.IamUserUpdateStatusInputDto;
import com.machine.client.iam.user.dto.input.IamCompanyUserCreateInputDto;
import com.machine.sdk.beisen.client.employee.BeiSenEmployeeClient;
import com.machine.sdk.beisen.client.employee.dto.input.BeiSenEmployeeTimeWindowInputDto;
import com.machine.sdk.beisen.client.employee.dto.output.BeiSenEmployeeInfoOutputDto;
import com.machine.sdk.beisen.domain.BeiSenBaseResponse;
import com.machine.sdk.beisen.envm.BeiSenEmployeeStatusEnum;
import com.machine.sdk.beisen.envm.BeiSenGenderEnum;
import com.machine.sdk.common.envm.BaseEnum;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.envm.crm.customer.GenderEnum;
import com.machine.sdk.common.envm.data.CompanyEmployeeStatusEnum;
import com.machine.sdk.common.envm.iam.role.ShopDefaultRoleEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.hrm.employee.dao.IEmployeeDao;
import com.machine.service.hrm.employee.dao.mapper.entity.EmployeeEntity;
import com.machine.service.hrm.employee.service.IHrmEmployeeService;
import com.machine.service.hrm.jobpost.dao.IJobPostRoleRelationDao;
import com.machine.service.hrm.jobpost.dao.mapper.entity.JobPostRoleRelationEntityEntity;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HrmEmployeeServiceImpl implements IHrmEmployeeService {

    @Resource(name = "beiSenOkHttpClient")
    private OkHttpClient okHttpClient;

    @Autowired
    private IEmployeeDao employeeDao;

    @Autowired
    private IJobPostRoleRelationDao jobPostRoleRelationDao;

    @Autowired
    private IIamUserClient userClient;

    @Override
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
        List<BeiSenEmployeeInfoOutputDto> outputDtoList = new ArrayList<>();

        while (true) {
            BeiSenEmployeeTimeWindowInputDto inputDto = new BeiSenEmployeeTimeWindowInputDto();
            inputDto.setStartTime(sdf.format(new Date(startTime)));
            inputDto.setStopTime(sdf.format(new Date(stopTime)));
            inputDto.setTimeWindowQueryType("2");
            inputDto.setScrollId(scrollId);
            BeiSenBaseResponse<BeiSenEmployeeInfoOutputDto> baseResponse = BeiSenEmployeeClient.getByTimeWindow(okHttpClient, inputDto);
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
            log.info("定时任务更新员工信息查询结果为空");
            return;
        }
        log.info("定时任务更新员工信息查询结果，size={}", outputDtoList.size());

        List<HrmEmployeeCreateInputDto> inputDtoList = new ArrayList<>();
        for (BeiSenEmployeeInfoOutputDto outputDto : outputDtoList) {
            HrmEmployeeCreateInputDto inputDto = new HrmEmployeeCreateInputDto();
            BeiSenEmployeeInfoOutputDto.EmployeeInfoDTO employeeInfo = outputDto.getEmployeeInfo();
            BeiSenEmployeeInfoOutputDto.RecordInfoDTO recordInfo = outputDto.getRecordInfo();

            inputDto.setId(employeeInfo.getUserID() + "");

            if (StrUtil.isBlank(employeeInfo.getMobilePhone())) {
                //手机号为空，不处理
                continue;
            }

            //默认为手机号
            inputDto.setUserName(recordInfo.getJobNumber());
            if (StrUtil.isBlank(inputDto.getUserName())) {
                inputDto.setUserName(employeeInfo.getMobilePhone());
            }

            inputDto.setStatus(BaseEnum.getInstance(BeiSenEmployeeStatusEnum.class, recordInfo.getEmployeeStatus()));

            //设置变编码
            if (StrUtil.isBlank(recordInfo.getJobNumber())) {
                inputDto.setCode(inputDto.getId());
            } else {
                inputDto.setCode(recordInfo.getJobNumber());
            }

            inputDto.setPhone(employeeInfo.getMobilePhone());
            inputDto.setName(employeeInfo.getName());
            if (null != employeeInfo.getGender()) {
                inputDto.setGender(BaseEnum.getInstance(BeiSenGenderEnum.class, employeeInfo.getGender() + ""));
            }
            inputDto.setEmail(employeeInfo.getEmail());
            inputDto.setLeaderId(recordInfo.getPOIdEmpAdmin() + "");
            inputDto.setJobPostId(recordInfo.getOIdJobPost());
            inputDto.setDepartmentId(recordInfo.getOIdDepartment() + "");
            inputDto.setIsCharge(Boolean.parseBoolean(recordInfo.getIsCharge()));
            inputDtoList.add(inputDto);
        }

        batchCreate(new HrmEmployeeBatchInsertInputDto(inputDtoList));
    }

    @Override
    public void batchCreate(HrmEmployeeBatchInsertInputDto inputDto) {
        List<HrmEmployeeCreateInputDto> inputDtoList = inputDto.getInputDtoList();
        if (CollectionUtil.isEmpty(inputDtoList)) {
            return;
        }

        for (HrmEmployeeCreateInputDto dto : inputDtoList) {
            EmployeeEntity entity = employeeDao.getById(dto.getId());
            if (null == entity) {
                EmployeeEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(dto), EmployeeEntity.class);
                String userId = UUID.randomUUID().toString().replaceAll("-", "");

                //创建员工信息
                insertEntity.setUserId(userId);
                employeeDao.insert(insertEntity);
                try {
                    //创建用户信息
                    IamCompanyUserCreateInputDto companyInputDto = new IamCompanyUserCreateInputDto();
                    companyInputDto.setId(userId);
                    companyInputDto.setUserName(dto.getCode());
                    companyInputDto.setCode(dto.getCode());
                    if (BeiSenEmployeeStatusEnum.LEFT == dto.getStatus()) {
                        companyInputDto.setStatus(StatusEnum.DISABLE);
                        companyInputDto.setEmployeeStatus(CompanyEmployeeStatusEnum.LEFT);
                    } else {
                        companyInputDto.setStatus(StatusEnum.ENABLE);
                        companyInputDto.setEmployeeStatus(CompanyEmployeeStatusEnum.FULL_TIME);
                    }
                    companyInputDto.setPhone(dto.getPhone());
                    companyInputDto.setName(dto.getName());
                    if (null == dto.getGender()) {
                        companyInputDto.setGender(GenderEnum.UNDEFINED);
                    } else {
                        companyInputDto.setGender(GenderEnum.valueOf(dto.getGender().getName()));
                    }

                    //获取关联的角色Id
                    List<JobPostRoleRelationEntityEntity> jobPostRoleRelationEntityEntityList =
                            jobPostRoleRelationDao.selectByJobPostId(dto.getJobPostId());
                    if (CollectionUtil.isNotEmpty(jobPostRoleRelationEntityEntityList)) {
                        Set<String> roleIdSet = jobPostRoleRelationEntityEntityList.stream()
                                .map(JobPostRoleRelationEntityEntity::getRoleId).collect(Collectors.toSet());
                        roleIdSet.add(ShopDefaultRoleEnum.STORE_MANAGER.getName().toLowerCase());
                        companyInputDto.setRoleIdSet(roleIdSet);
                    } else {
                        companyInputDto.setRoleIdSet(Set.of(ShopDefaultRoleEnum.STORE_MANAGER.getName().toLowerCase()));
                    }
                    userClient.createCompanyUser(companyInputDto);
                } catch (Exception e) {
                    // 捕获异常，防止以影响其他员工数据的新增
                    log.error("北森同步员工数据新增异常，inputDto={}", JSONUtil.toJsonStr(dto), e);
                }
            } else {
                EmployeeEntity updateEntity = JSONUtil.toBean(JSONUtil.toJsonStr(dto), EmployeeEntity.class);
                updateEntity.setUserId(null);
                updateEntity.setUserName(null);
                updateEntity.setCode(null);

                //修改员工信息
                employeeDao.update(updateEntity);

                if (BeiSenEmployeeStatusEnum.LEFT == dto.getStatus()) {
                    //禁用用户
                    IamUserUpdateStatusInputDto userUpdateStatusInputDto = new IamUserUpdateStatusInputDto();
                    userUpdateStatusInputDto.setId(entity.getUserId());
                    userUpdateStatusInputDto.setStatus(StatusEnum.DISABLE);
                    userClient.updateStatus(userUpdateStatusInputDto);
                } else {
                    //不会联动启用用户
                }

                try {
                    IamCompanyUserUpdate4BeiSenInputDto customerUpdateInputDto = new IamCompanyUserUpdate4BeiSenInputDto();
                    customerUpdateInputDto.setId(entity.getUserId());
                    customerUpdateInputDto.setPhone(entity.getPhone());
                    if (BeiSenEmployeeStatusEnum.LEFT == dto.getStatus()) {
                        customerUpdateInputDto.setEmployeeStatus(CompanyEmployeeStatusEnum.LEFT);
                    } else {
                        customerUpdateInputDto.setEmployeeStatus(CompanyEmployeeStatusEnum.FULL_TIME);
                    }
                    userClient.updateCompanyUser4BeiSen(customerUpdateInputDto);
                } catch (Exception e) {
                    // 捕获异常，防止以影响其他员工数据的新增
                    log.error("北森同步员工数据修改手机号异常，inputDto={}", JSONUtil.toJsonStr(dto), e);
                }

                entity.setUserId(entity.getUserId());
            }
        }
    }

    @Override
    public Integer countByDepartmentIds(IdSetRequest request) {
        return employeeDao.countByDepartmentIds(request.getIdSet());
    }

    @Override
    public HrmEmployeeDetailOutputDto detailById(IdRequest request) {
        EmployeeEntity entity = employeeDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), HrmEmployeeDetailOutputDto.class);
    }

    @Override
    public HrmEmployeeDetailOutputDto getByUserId(IdRequest request) {
        EmployeeEntity entity = employeeDao.getByUserId(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), HrmEmployeeDetailOutputDto.class);
    }

    @Override
    public List<HrmEmployeeListOutputDto> list4Charge(IdRequest request) {
        String departmentId = request.getId();
        List<EmployeeEntity> entityList = employeeDao.list4Charge(departmentId);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), HrmEmployeeListOutputDto.class);
    }

    @Override
    public List<HrmEmployeeListOutputDto> list(HrmEmployeeQueryIListInputDto inputDto) {
        List<EmployeeEntity> entityList = employeeDao.list(inputDto);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), HrmEmployeeListOutputDto.class);
    }

    @Override
    public Map<String, HrmEmployeeDetailOutputDto> mapByIdSet(IdSetRequest request) {
        List<EmployeeEntity> userEntityList = employeeDao.selectByIdSet(request.getIdSet());
        return userEntityList.stream()
                .collect(Collectors.toMap(EmployeeEntity::getId, user ->
                        JSONUtil.toBean(JSONUtil.toJsonStr(user), HrmEmployeeDetailOutputDto.class)));
    }

    @Override
    public Map<String, HrmEmployeeDetailOutputDto> mapByUserIdSet(IdSetRequest request) {
        List<EmployeeEntity> userEntityList = employeeDao.selectByUserIdSet(request.getIdSet());
        return userEntityList.stream()
                .collect(Collectors.toMap(EmployeeEntity::getUserId, user ->
                        JSONUtil.toBean(JSONUtil.toJsonStr(user), HrmEmployeeDetailOutputDto.class)));
    }

    @Override
    public Map<String, List<HrmEmployeeDetailOutputDto>> map4ChargeByDepartmentIdSet(IdSetRequest request) {
        List<EmployeeEntity> userEntityList = employeeDao.listChargeByDepartmentIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(userEntityList)) {
            return Map.of();
        }
        //根据DepartmentId拆分Map集合
        Map<String, List<HrmEmployeeDetailOutputDto>> userRoleTargetMap = new HashMap<>();
        for (EmployeeEntity entity : userEntityList) {
            String departmentId = entity.getDepartmentId();
            List<HrmEmployeeDetailOutputDto> outputDtoList = userRoleTargetMap.computeIfAbsent(departmentId, k -> new ArrayList<>());
            outputDtoList.add(JSONUtil.toBean(JSONUtil.toJsonStr(entity), HrmEmployeeDetailOutputDto.class));
        }
        return userRoleTargetMap;
    }

}
