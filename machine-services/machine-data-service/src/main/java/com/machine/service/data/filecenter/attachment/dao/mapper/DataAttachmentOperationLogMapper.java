package com.machine.service.data.filecenter.attachment.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentOperationLogPageInputDto;
import com.machine.service.data.filecenter.attachment.dao.mapper.entity.DataAttachmentOperationLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DataAttachmentOperationLogMapper extends BaseMapper<DataAttachmentOperationLogEntity> {

    Page<DataAttachmentOperationLogEntity> selectPage(@Param("inputDto") DataAttachmentOperationLogPageInputDto inputDto,
                                                       IPage<DataAttachmentOperationLogEntity> page);

}
