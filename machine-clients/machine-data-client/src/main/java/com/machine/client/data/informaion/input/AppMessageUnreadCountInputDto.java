package com.machine.client.data.informaion.input;

import com.machine.sdk.common.envm.data.message.MessageTemplateCategoryEnum;
import com.machine.sdk.common.model.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AppMessageUnreadCountInputDto extends PageRequest {

    private MessageTemplateCategoryEnum templateCategory;

    private String receiver;
}