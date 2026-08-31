package com.machine.service.iam.biam.log.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.log.dto.input.BIamUserAccessLogQueryPageInputDto;
import com.machine.service.iam.biam.log.dao.mapper.entity.BIamUserAccessLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BIamUserAccessLogMapper extends BaseMapper<BIamUserAccessLogEntity> {

    int deleteByCreateTimeBefore(@Param("beforeCreateTime") Long beforeCreateTime);

    Page<BIamUserAccessLogEntity> selectPage(@Param("inputDto") BIamUserAccessLogQueryPageInputDto inputDto,
                                             IPage<BIamUserAccessLogEntity> page);

}
