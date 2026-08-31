package com.machine.service.iam.biam.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.client.iam.biam.user.dto.input.BIamUserTypeExistsTypeInputDto;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserTypeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BIamUserTypeMapper extends BaseMapper<BIamUserTypeEntity> {

    boolean existsType(@Param("inputDto") BIamUserTypeExistsTypeInputDto inputDto);

}
