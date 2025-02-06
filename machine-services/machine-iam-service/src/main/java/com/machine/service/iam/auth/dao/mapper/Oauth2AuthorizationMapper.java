package com.machine.service.iam.auth.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.iam.auth.dao.mapper.entity.Oauth2AuthorizationEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Oauth2AuthorizationMapper extends BaseMapper<Oauth2AuthorizationEntity> {

}
