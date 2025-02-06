package com.machine.service.iam.user.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.client.iam.user.dto.input.IamUserTypeExistsTypeInputDto;
import com.machine.service.iam.user.dao.mapper.entity.UserTypeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IUserTypeMapper extends BaseMapper<UserTypeEntity> {

    boolean existsType(@Param("inputDto") IamUserTypeExistsTypeInputDto inputDto);

}
