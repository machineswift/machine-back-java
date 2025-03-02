package com.machine.client.data.informaion.input;

import com.machine.sdk.common.envm.data.message.ContentTypeEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class AppMessageTemplateDto implements Serializable {

    private final static long serialVersionUID = 1L;

    private ContentTypeEnum type;

    private String value;
}
