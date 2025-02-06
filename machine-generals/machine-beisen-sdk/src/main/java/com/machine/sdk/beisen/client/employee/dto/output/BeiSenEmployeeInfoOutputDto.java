package com.machine.sdk.beisen.client.employee.dto.output;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
public class BeiSenEmployeeInfoOutputDto {
    /**
     * 外部ID标识，第三方系统唯一标识ID（非北森系统）
     */
    private String originalId;

    /**
     * 查询员工信息数据结果
     */
    private EmployeeInfoDTO employeeInfo;

    /**
     * 查询员工的任职记录数据结果
     */
    private RecordInfoDTO recordInfo;

    @NoArgsConstructor
    @Data
    public static class EmployeeInfoDTO {

        /**
         * 员工UserID，示例：115515434
         */
        private Integer userID;

        /**
         * 姓名，示例：王二
         */
        private String name;

        /**
         * 姓名（拼音），示例：wanger
         */
        private String _Name;

        /**
         * 姓拼音，示例：wang
         */
        private String phoneticOfXing;

        /**
         * 名拼音，示例：er
         */
        private String phoneticOfMing;

        /**
         * 性别：0男、1女
         */
        private Integer gender;

        /**
         * 电子邮件
         */
        private String email;

        /**
         * 证件类型
         */
        private String iDType;

        /**
         * 证件号码
         */
        private String iDNumber;

        /**
         * 是否长期证件
         */
        private Boolean isLongTermCertificate;

        /**
         * 证件开始日期
         */
        private String certificateStartDate;

        /**
         * 证件截止日期
         */
        private String certificateValidityTerm;

        /**
         * 出生日期
         */
        private String birthday;

        /**
         * 参加工作日期
         */
        private String workDate;

        /**
         * 联系地址
         */
        private String homeAddress;

        /**
         * 手机号码
         */
        private String mobilePhone;

        private String businessAddress;

        /**
         * 创建人员工UserID
         */
        private Integer createdBy;

        /**
         * 创建时间，示例：2021-01-01T14:00:00
         */
        private String createdTime;

        /**
         * 修改人员工UserID，示例：115515434。系统更新数据时，该字段值会修改。
         */
        private Integer modifiedBy;

        /**
         * 修改时间，示例：2021-01-01T14:00:00。系统更新数据时，该字段值会修改
         */
        private String modifiedTime;

        /**
         * 是否删除，示例：false
         */
        private Boolean stdIsDeleted;

        private HashMap<String,Object> translateProperties;

    }

    @NoArgsConstructor
    @Data
    public static class RecordInfoDTO {
        private Integer userID;
        private String pObjectDataID;
        private Integer oIdDepartment;
        private String startDate;
        private String stopDate;
        private String jobNumber;
        private String entryDate;
        private Object lastWorkDate;
        private Object regularizationDate;
        private Object probation;
        private Object order;
        private Integer employType;
        private Integer serviceType;
        private Integer serviceStatus;
        private Integer approvalStatus;
        private Object employmentSource;
        private String employmentForm;
        private String isCharge;
        private String oIdJobPost;
        private Object oIdJobSequence;
        private Object oIdProfessionalLine;
        private Object oIdJobPosition;
        private String oIdJobLevel;
        private Object oidJobGrade;
        private String place;
        private String employeeStatus;
        private String employmentType;
        private String employmentChangeID;
        private Object changedStatus;
        private Integer pOIdEmpAdmin;
        private Object pOIdEmpReserve2;
        private String businessTypeOID;
        private String changeTypeOID;
        private Object entryStatus;
        private Boolean isCurrentRecord;
        private Object lUOffer;
        private String entryType;
        private String staffID;
        private Object workYearBefore;
        private Object workYearGroupBefore;
        private Integer workYearCompanyBefore;
        private Double workYearTotal;
        private Object workYearGroupTotal;
        private Double workYearCompanyTotal;
        private Integer oIdOrganization;
        private Object whereabouts;
        private Object blackStaffDesc;
        private Object blackListAddReason;
        private Object transitionTypeOID;
        private Object changeReason;
        private Object probationResult;
        private Object probationActualStopDate;
        private Object probationStartDate;
        private Object probationStopDate;
        private String isHaveProbation;
        private Object remarks;
        private Object addOrNotBlackList;
        private Integer businessModifiedBy;
        private String businessModifiedTime;
        private Object traineeStartDate;
        private String objectId;
        private HashMap<String,Object> translateProperties;
        private Integer createdBy;
        private String createdTime;
        private Integer modifiedBy;
        private String modifiedTime;
        private Boolean stdIsDeleted;
    }
}
