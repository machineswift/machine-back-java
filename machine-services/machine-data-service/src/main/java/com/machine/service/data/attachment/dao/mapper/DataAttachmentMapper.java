package com.machine.service.data.attachment.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.attachment.dto.input.DataAttachmentQueryPageInputDto;
import com.machine.service.data.attachment.dao.mapper.entity.DataAttachmentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DataAttachmentMapper extends BaseMapper<DataAttachmentEntity> {

    Page<DataAttachmentEntity> selectPage(@Param("inputDto") DataAttachmentQueryPageInputDto inputDto,
                                          IPage<DataAttachmentEntity> page);

}
