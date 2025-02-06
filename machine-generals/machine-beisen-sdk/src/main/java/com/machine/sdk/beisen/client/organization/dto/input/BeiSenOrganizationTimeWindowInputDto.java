package com.machine.sdk.beisen.client.organization.dto.input;

import lombok.Data;

@Data
public class BeiSenOrganizationTimeWindowInputDto {

    /**
     * 时间范围开始时间 格式：2021-01-01T00:00:00
     */
    private String startTime;

    /**
     * 时间范围结束时间 格式：2021-09-01T00:00:00
     */
    private String stopTime;

    /**
     * 时间窗查询类型，1修改时间、2业务修改时间
     * ModifiedTime:修改时间，系统修改同步更新该时间。示例："1"
     * BusinessModifiedTime:业务修改时间，系统修改不同步更新该时间
     */
    private String timeWindowQueryType = "1";

    /**
     * 本批次的ScrollId，第一次查询为空，后续为上次结果返回的ScrollId。示例：""或"DXF1ZXJ5QW5kRmV0Y2gBAAAAAAVrsaUWdnVycEd3OEFRRm02aEpHRFZQZ2htdw=="
     * 注意：
     * 1.scroll有过期时间限制。两次Scroll接口调用间隔不能超出10秒，可以通过while循环查出全部数据后再处理业务。
     * 2.Scroll不能跳页，拿了第一页后要拿第三页，scroll做不到
     * 3.Scroll不能回跳，拿了第三页之后要跳回第二页，scroll做不到
     */
    private String scrollId;

    /**
     * 每批次查询个数，默认100个
     */
    private Integer capacity = 300;


    /**
     * 是否包含离职的记录，默认不包含，示例：false
     */
    private Boolean withDisabled = Boolean.TRUE;

    /**
     * 是否包括已删除数据，默认否，示例：true/false
     */
    private Boolean isWithDeleted = Boolean.TRUE;

    /**
     * 是否开启动态翻译，默认否，示例：true/false。开启后，自动翻译数据源等字典类型字段，
     * 结果在响应参数的TranslateProperties中，key为对应字段加Text后缀，值为翻译后文本
     */
    private Boolean enableTranslate;
}
