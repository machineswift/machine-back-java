package com.machine.app.iam.user.business;

import com.machine.app.iam.user.controller.vo.request.IamUserUpdatePasswordRequestVo;
import com.machine.app.iam.user.controller.vo.request.*;
import com.machine.app.iam.user.controller.vo.response.IamUserDetailResponseVo;
import com.machine.app.iam.user.controller.vo.response.IamUserExpandListResponseVo;
import com.machine.app.iam.user.controller.vo.response.IamUserRoleInfoResponse;
import com.machine.app.iam.user.controller.vo.response.IamUserSimpleListResponseVo;
import com.machine.sdk.common.envm.iam.organization.IamOrganizationTypeEnum;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.response.PageResponse;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IIamUserBusiness {

    String create(IamUserCreateRequestVo request);

    void update(IamUserUpdateRequestVo request);

    void updateStatus(IamUserUpdateStatusRequestVo request);

    void updatePhone(IamUserUpdatePhoneRequestVo request);

    void updatePassword(IamUserUpdatePasswordRequestVo request);

    void updatePermission(IamUserUpdatePermissionRequestVo request);

    void extractedUserIdByOrganizationIdSet(  IamOrganizationTypeEnum organizationType,
                                              Set<String> organizationIdSet,
                                            Set<String> finallyqueryShopIdSet);

    boolean computeFinallyQueryUserIdSet(Set<String> shopIdSet,
                                         Set<String> departmentIdSet,
                                         IamOrganizationTypeEnum organizationType,
                                         Set<String> organizationIdSet,
                                         Set<String> roleIdSet,
                                         Set<String> finallyqueryUserIdSet);


    IamUserDetailResponseVo detail(IdRequest request);

    Set<String> getIdByDepartmentIdSet(Set<String> departmentIdSet);

    List<IamUserRoleInfoResponse> getUserRoleList(String userId);

    Map<String, List<IamUserRoleInfoResponse>> getUserRoleListMap(Set<String> userIdSet);

    PageResponse<IamUserSimpleListResponseVo> pageSimple(IamUserQueryPageRequestVo request);

    PageResponse<IamUserExpandListResponseVo> pageExpand(IamUserQueryPageRequestVo request);

    void export(IamUserQueryPageRequestVo request);
}
