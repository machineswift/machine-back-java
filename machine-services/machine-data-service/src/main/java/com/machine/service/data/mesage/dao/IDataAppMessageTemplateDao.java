package com.machine.service.data.mesage.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.message.dto.input.AppMessageTemplateDetailByTypeInputDto;
import com.machine.client.data.message.dto.input.AppMessageTemplateQueryPageInputDto;
import com.machine.client.data.message.dto.input.AppMessageTemplateUpdateChannelInput;
import com.machine.client.data.message.dto.input.AppMessageTemplateUpdateStatusInputDto;
import com.machine.service.data.mesage.dao.mapper.entity.DataAppMessageTemplateEntity;

public interface IDataAppMessageTemplateDao {

    void update(DataAppMessageTemplateEntity dataAppMessageTemplateEntity);

    void updateMessageTemplateStatus(AppMessageTemplateUpdateStatusInputDto inputDto);

    void updateMessageTemplateChannel(AppMessageTemplateUpdateChannelInput inputDto);

    DataAppMessageTemplateEntity detailByType(AppMessageTemplateDetailByTypeInputDto inputDto);

    Page<DataAppMessageTemplateEntity> page(AppMessageTemplateQueryPageInputDto inputDto);
}
