package com.machine.service.data.messageTemplate.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.messageTemplate.dto.input.AppMessageTemplateDetailByTypeInputDto;
import com.machine.client.data.messageTemplate.dto.input.AppMessageTemplateQueryPageInputDto;
import com.machine.client.data.messageTemplate.dto.input.AppMessageTemplateUpdateChannelInput;
import com.machine.client.data.messageTemplate.dto.input.AppMessageTemplateUpdateStatusInputDto;
import com.machine.service.data.messageTemplate.dao.mapper.entity.AppMessageTemplateEntity;

public interface IAppMessageTemplateDao {

    Page<AppMessageTemplateEntity> page(AppMessageTemplateQueryPageInputDto inputDto);

    AppMessageTemplateEntity detailByType(AppMessageTemplateDetailByTypeInputDto inputDto);

    void update(AppMessageTemplateEntity appMessageTemplateEntity);

    void updateMessageTemplateStatus(AppMessageTemplateUpdateStatusInputDto inputDto);

    void updateMessageTemplateChannel(AppMessageTemplateUpdateChannelInput inputDto);
}
