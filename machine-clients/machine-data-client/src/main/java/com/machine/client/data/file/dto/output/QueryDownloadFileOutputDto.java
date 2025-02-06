package com.machine.client.data.file.dto.output;

import com.machine.sdk.common.envm.data.ExportTaskStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QueryDownloadFileOutputDto {

    /**
     * id下载中心主键
     */
    private String id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 任务状态枚举 ExportTaskStatusEnum
     */
    private ExportTaskStatusEnum status;

    /**
     * 失败原因
     */
    private String failCause;

    /**
     * 过期时间
     */
    private Long expirationIn;

    /**
     * 过期天数,-1代表失效
     */
    private Integer expirationDay;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 查询参数
     */
    private String jsonParams;


}