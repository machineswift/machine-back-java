package com.machine.service.iam.biam.log.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.iam.biam.log.dto.input.BIamOperationLogQueryPageInputDto;
import com.machine.service.iam.biam.log.dao.mapper.entity.BIamOperationLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BIamOperationLogMapper extends BaseMapper<BIamOperationLogEntity> {

    Page<BIamOperationLogEntity> selectPage(@Param("inputDto") BIamOperationLogQueryPageInputDto inputDto,
                                            IPage<BIamOperationLogEntity> page);

}
