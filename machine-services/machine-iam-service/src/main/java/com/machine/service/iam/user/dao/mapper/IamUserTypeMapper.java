package com.machine.service.iam.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.client.iam.user.dto.input.IamUserTypeExistsTypeInputDto;
import com.machine.service.iam.user.dao.mapper.entity.IamUserTypeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IamUserTypeMapper extends BaseMapper<IamUserTypeEntity> {

    boolean existsType(@Param("inputDto") IamUserTypeExistsTypeInputDto inputDto);

}
