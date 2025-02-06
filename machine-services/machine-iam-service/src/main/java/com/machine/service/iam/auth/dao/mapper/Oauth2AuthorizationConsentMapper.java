package com.machine.service.iam.auth.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.iam.auth.dao.mapper.entity.Oauth2AuthorizationConsentEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Oauth2AuthorizationConsentMapper extends BaseMapper<Oauth2AuthorizationConsentEntity> {
}
