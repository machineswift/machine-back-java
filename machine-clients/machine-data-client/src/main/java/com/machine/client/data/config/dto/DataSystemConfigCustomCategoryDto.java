package com.machine.client.data.config.dto;

import com.machine.sdk.common.envm.data.message.DataMessageTemplateTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class DataSystemConfigCustomCategoryDto  {

    private String baseEnumCategory;

    private Set<DataMessageTemplateTypeEnum> templateTypeEnums;

}