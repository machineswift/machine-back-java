package com.machine.service.iam.auth.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.service.iam.auth.dao.mapper.entity.IamOauth2RegisteredClientEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IamOauth2RegisteredClientMapper extends BaseMapper<IamOauth2RegisteredClientEntity> {
    List<String> allClientId(@Param("status") StatusEnum status);
}
