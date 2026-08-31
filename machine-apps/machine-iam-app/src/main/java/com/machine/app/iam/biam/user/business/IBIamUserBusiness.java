package com.machine.app.iam.biam.user.business;

import com.machine.app.iam.biam.user.controller.vo.request.*;
import com.machine.app.iam.biam.user.controller.vo.response.BIamUserDetailResponseVo;
import com.machine.app.iam.biam.user.controller.vo.response.BIamUserExpandListResponseVo;
import com.machine.app.iam.biam.user.controller.vo.response.BIamUserRoleInfoResponse;
import com.machine.app.iam.biam.user.controller.vo.response.BIamUserSimpleListResponseVo;
import com.machine.sdk.base.envm.biam.organization.BIamOrganizationTypeEnum;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.response.PageResponse;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IBIamUserBusiness {

    String create(BIamUserCreateRequestVo request);

    void update(BIamUserUpdateRequestVo request);

    void updateStatus(BIamUserUpdateStatusRequestVo request);

    void updatePhone(BIamUserUpdatePhoneRequestVo request);

    void updatePassword(BIamUserUpdatePasswordRequestVo request);

    void updatePermission(BIamUserUpdatePermissionRequestVo request);

    void extractedUserIdByOrganizationIdSet(  BIamOrganizationTypeEnum organizationType,
                                              Set<String> organizationIdSet,
                                            Set<String> finallyqueryShopIdSet);

    boolean computeFinallyQueryUserIdSet(Set<String> shopIdSet,
                                         Set<String> departmentIdSet,
                                         BIamOrganizationTypeEnum organizationType,
                                         Set<String> organizationIdSet,
                                         Set<String> roleIdSet,
                                         Set<String> finallyqueryUserIdSet);


    BIamUserDetailResponseVo detail(IdRequest request);

    Set<String> getIdByDepartmentIdSet(Set<String> departmentIdSet);

    List<BIamUserRoleInfoResponse> getUserRoleList(String userId);

    Map<String, List<BIamUserRoleInfoResponse>> getUserRoleListMap(Set<String> userIdSet);

    PageResponse<BIamUserSimpleListResponseVo> pageSimple(BIamUserQueryPageRequestVo request);

    PageResponse<BIamUserExpandListResponseVo> pageExpand(BIamUserQueryPageRequestVo request);

    void export(BIamUserExportRequestVo request);
}
