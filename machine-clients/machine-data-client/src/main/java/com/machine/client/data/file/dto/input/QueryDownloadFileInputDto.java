package com.machine.client.data.file.dto.input;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 下载中心
 * 宋忠瑾
 * 2024 11 12
 *
 * @author 10546
 */
@Data
@NoArgsConstructor
public class QueryDownloadFileInputDto {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 任务状态枚举 ExportTaskStatusEnum
     */
    private List<String> statusList;

    /**
     * 失败原因
     */
    private String failCause;

    /**
     * 过期时间
     */
    private Long expirationTime;

    /**
     * 条数
     */
    private Integer pageSize = 20;
    /**
     * 重试次数
     */
    private Integer retryStatus;


}