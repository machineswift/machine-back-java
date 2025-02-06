package com.machine.client.data.area.dto.output;

import lombok.Data;

@Data
public class AreaListOutputDto {

    private String id;

    /**
     * 父Id
     */
    private String parentId;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;
}
