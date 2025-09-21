package com.machine.client.data.message.dto.input;

import com.machine.sdk.common.envm.data.message.DataMessageTemplateCategoryEnum;
import com.machine.sdk.common.model.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AppMessageUnreadCountInputDto extends PageRequest {

    private DataMessageTemplateCategoryEnum templateCategory;

    private String receiver;
}