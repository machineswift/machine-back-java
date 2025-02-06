package com.machine.client.data.supplier.dto.output;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DataSupplierDetailOutputDto {

    private String id;

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 归属公司Id集合
     */
    private List<String> companyIdList;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private long createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private long updateTime;

}
