package com.machine.client.data.config.dto;

import com.machine.sdk.common.envm.data.MessageTemplateTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
public class DataSystemConfigCustomCategoryDto implements Serializable {

    private static final long serialVersionUID = 12423409423L;

    private String baseEnumCategory;

    private Set<MessageTemplateTypeEnum> templateTypeEnums;

}