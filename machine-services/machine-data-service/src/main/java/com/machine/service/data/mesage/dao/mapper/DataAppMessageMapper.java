package com.machine.service.data.mesage.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.message.dto.input.AppMessageGroupCountInputDto;
import com.machine.client.data.message.dto.input.AppMessagePageInputDto;
import com.machine.client.data.message.dto.input.AppMessagePageSuperInputDto;
import com.machine.client.data.message.dto.input.AppMessageUnreadCountInputDto;
import com.machine.client.data.message.dto.output.AppMessageGroupCountOutputDto;
import com.machine.client.data.message.dto.output.AppMessageListOutputDto;
import com.machine.client.data.message.dto.output.AppMessageListSuperOutputDto;
import com.machine.service.data.mesage.dao.mapper.entity.DataAppMessageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DataAppMessageMapper extends BaseMapper<DataAppMessageEntity> {
    Page<AppMessageListOutputDto> managePage(@Param("inputDto") AppMessagePageInputDto inputDto,
                                             IPage<AppMessageListOutputDto> page);

    Page<AppMessageListSuperOutputDto> superPage(@Param("inputDto") AppMessagePageSuperInputDto inputDto,
                                                 IPage<AppMessageListSuperOutputDto> page);

    void disposeMessage(@Param("batchCode") String batchCode, @Param("receiver") String receiver);

    void readMessage(@Param("batchCode") String batchCode, @Param("receiver") String receiver);

    List<AppMessageGroupCountOutputDto> groupCount(@Param("inputDto")AppMessageGroupCountInputDto inputDto);

    Integer getUnreadCount(@Param("inputDto")AppMessageUnreadCountInputDto inputDto);
}
