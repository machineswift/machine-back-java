package com.machine.sdk.beisen.client.employee.dto.input;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class BeiSenEmployeeGetEmployeeOfOrganizationInputDto {

    /**
     * 组织单元OId标识，示例：8785
     */
    private Integer orgOId;

    /**
     * 是否包括子组织，默认否，示例：true
     */
    private Boolean includeSubOrg = Boolean.FALSE;

    /**
     * 查询日期，默认查询当前日期，null也表示当前日期。示例：2021-01-01。注意：组织单元是有时间轴概念，类似任职记录
     */
    private String queryDate;

    /**
     * 是否包含停用组织，默认false，不包含。
     */
    private Boolean isWithDisable;

    /**
     * 本批次的ScrollId，第一次查询为空，后续为上次结果返回的ScrollId。
     */
    private String scrollId;


    /**
     * 人员状态: 默认空，表示所有。
     */
    private List<Integer> empStatus;

    /**
     * 雇佣类型 0:内部员工，1：外部人员，2：实习生
     */
    private List<Integer> employType = Arrays.asList(0,2);


    /**
     * 任职类型：默认查询主职。
     */
    private List<Integer> serviceType;


    /**
     * 审批状态：默认查询生效(4)，空集合或null表示查询生效。
     */
    private List<Integer> approvalStatuses;


    /**
     * 是否获取最新主职记录，默认true
     */
    private Boolean isGetLatestRecord=Boolean.TRUE;


    /**
     * 是否包含离职的记录，默认false
     */
    private Boolean withDisabled=Boolean.FALSE;


    /**
     * 每批次查询个数，默认300个
     */
    private Integer capacity = 300;

    /**
     * 是否包括已删除数据，默认否，示例：true/false
     */
    private Boolean isWithDeleted = Boolean.FALSE;

    /**
     * 是否开启动态翻译，默认否，示例：true/false。开启后，自动翻译数据源等字典类型字段，
     * 结果在响应参数的TranslateProperties中，key为对应字段加Text后缀，值为翻译后文本
     */
    private Boolean enableTranslate;

}
