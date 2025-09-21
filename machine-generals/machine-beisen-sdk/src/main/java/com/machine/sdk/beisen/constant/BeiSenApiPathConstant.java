package com.machine.sdk.beisen.constant;

public final class BeiSenApiPathConstant {

    public static class Token {
        public static final String ACCESS_TOKEN = "token";
    }

    /**
     * 职位
     */
    public static class Position {
        /**
         * 根据时间窗滚动查询变动的职位信息
         */
        public static final String GET_BY_TIME_WINDOW = "TenantBaseExternal/api/v5/Position/GetByTimeWindow";

        /**
         * 根据职位OId集合获取的职位相关信息
         */
        public static final String GET_BY_OIDS = "TenantBaseExternal/api/v5/Position/GetByOIds";
    }

    /**
     * 职务
     */
    public static class JobPost {
        /**
         * 根据时间窗滚动查询变动的职务信息
         */
        public static final String GET_BY_TIME_WINDOW = "TenantBaseExternal/api/v5/JobPost/GetByTimeWindow";

        /**
         * 通过职务OID获取职务信息
         */
        public static final String GET_BY_OIDS = "TenantBaseExternal/api/v5/JobPost/GetByOIds";
    }


    /**
     * 员工
     */
    public static class Employee {

        /**
         * 根据时间窗滚动查询变动的员工与单条任职信息
         */
        public static final String GET_BY_TIME_WINDOW = "TenantBaseExternal/api/v5/Employee/GetByTimeWindow";

        /**
         * 滚动查询指定组织下的员工与单条任职信息
         */
        public static final String GET_EMPLOYEE_OF_ORGANIZATION = "TenantBaseExternal/api/v5/Employee/GetEmployeeOfOrganization";
    }

    public static class Organization {
        /**
         * 根据时间窗滚动查询变动的组织单元信息
         */
        public static final String GET_BY_TIME_WINDOW = "TenantBaseExternal/api/v5/Organization/GetByTimeWindow";

        /**
         * 根据组织OId集合获取组织相关信息
         */
        public static final String GET_BY_IDS = "TenantBaseExternal/api/v5/Organization/GetByIds";

        /**
         * 查询指定组织的下级组织单元列表
         */
        public static final String GET_SUB_ORGANIZATIONS = "TenantBaseExternal/api/v5/Organization/GetSubOrganizations";

        /**
         * 查询指定组织的下级组织单元列表
         */
        public static final String GET_ORGANIZATIONS_BY_CODES = "TenantBaseExternal/api/v5/Organization/GetOrganizationInfoByCodes";
    }

}