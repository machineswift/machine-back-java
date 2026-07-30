package com.machine.service.iam.identity.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.identity.dto.input.IamOAuth2RegisteredClientPageQueryInputDto;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.service.iam.identity.dao.mapper.entiry.IamOauth2RegisteredClientEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IamOauth2RegisteredClientMapper extends BaseMapper<IamOauth2RegisteredClientEntity> {

    List<String> allClientId(@Param("status") StatusEnum status);

    Page<IamOauth2RegisteredClientEntity> selectPage(@Param("inputDto") IamOAuth2RegisteredClientPageQueryInputDto inputDto,
                                                     IPage<IamOauth2RegisteredClientEntity> page);

}
