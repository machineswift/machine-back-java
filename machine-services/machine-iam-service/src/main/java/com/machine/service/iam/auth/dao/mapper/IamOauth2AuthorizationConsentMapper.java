package com.machine.service.iam.auth.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.iam.auth.dao.mapper.entity.IamOauth2AuthorizationConsentEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IamOauth2AuthorizationConsentMapper extends BaseMapper<IamOauth2AuthorizationConsentEntity> {
}
