package com.machine.service.data.file.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.file.dto.input.DownloadFileQueryPageInputDto;
import com.machine.service.data.file.dao.mapper.entity.DownloadFileEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DownloadFileEntityMapper extends BaseMapper<DownloadFileEntity> {

    Page<DownloadFileEntity> page(@Param("inputDto") DownloadFileQueryPageInputDto inputDto,
                                  IPage<DownloadFileEntity> page);
}
