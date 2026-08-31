package com.machine.service.iam.biam.identity.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.identity.dto.input.BIamOAuth2RegisteredClientPageQueryInputDto;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.service.iam.biam.identity.dao.mapper.entity.BIamOauth2RegisteredClientEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BIamOauth2RegisteredClientMapper extends BaseMapper<BIamOauth2RegisteredClientEntity> {

    List<String> allClientId(@Param("status") StatusEnum status);

    Page<BIamOauth2RegisteredClientEntity> selectPage(@Param("inputDto") BIamOAuth2RegisteredClientPageQueryInputDto inputDto,
                                                      IPage<BIamOauth2RegisteredClientEntity> page);

}
