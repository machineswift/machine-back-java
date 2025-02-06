package com.machine.service.data.area.service.bo;

import lombok.Data;

@Data
public class AreaReptileBO {

    private String parentCode;

    private String code;

    private String categoryCode;

    private String name;

    private Boolean hasChild;
}
