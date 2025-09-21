package com.machine.client.data.message.dto.input;

import com.machine.sdk.common.envm.data.message.DataContentTypeEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class AppMessageTemplateDto implements Serializable {

    private final static long serialVersionUID = 1L;

    private DataContentTypeEnum type;

    private String value;
}
