package com.machine.service.data.filecenter.download.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.filecenter.download.dto.input.DataDownloadQueryPageInputDto;
import com.machine.service.data.filecenter.download.dao.mapper.entity.DataDownloadEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DataDownloadMapper extends BaseMapper<DataDownloadEntity> {

    Page<DataDownloadEntity> selectPage(@Param("inputDto") DataDownloadQueryPageInputDto inputDto,
                                        IPage<DataDownloadEntity> page);
}
