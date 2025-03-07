package com.machine.service.data.information.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.informaion.input.AppMessageGroupCountInputDto;
import com.machine.client.data.informaion.input.AppMessagePageInputDto;
import com.machine.client.data.informaion.input.AppMessagePageSuperInputDto;
import com.machine.client.data.informaion.input.AppMessageUnreadCountInputDto;
import com.machine.client.data.informaion.output.AppMessageGroupCountOutputDto;
import com.machine.client.data.informaion.output.AppMessageListOutputDto;
import com.machine.client.data.informaion.output.AppMessageListSuperOutputDto;
import com.machine.service.data.information.dao.mapper.entity.AppMessageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppMessageMapper extends BaseMapper<AppMessageEntity> {
    Page<AppMessageListOutputDto> managePage(@Param("inputDto") AppMessagePageInputDto inputDto,
                                             IPage<AppMessageListOutputDto> page);

    Page<AppMessageListSuperOutputDto> superPage(@Param("inputDto") AppMessagePageSuperInputDto inputDto,
                                                 IPage<AppMessageListSuperOutputDto> page);

    void disposeMessage(@Param("batchCode") String batchCode, @Param("receiver") String receiver);

    void readMessage(@Param("batchCode") String batchCode, @Param("receiver") String receiver);

    List<AppMessageGroupCountOutputDto> groupCount(@Param("inputDto")AppMessageGroupCountInputDto inputDto);

    Integer getUnreadCount(@Param("inputDto")AppMessageUnreadCountInputDto inputDto);
}
