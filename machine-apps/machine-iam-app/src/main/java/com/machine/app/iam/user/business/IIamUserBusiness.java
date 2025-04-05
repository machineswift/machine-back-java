package com.machine.app.iam.user.business;

import com.machine.app.iam.user.controller.vo.request.IamAdminChangePasswordRequestVo;
import com.machine.app.iam.user.controller.vo.request.*;
import com.machine.app.iam.user.controller.vo.response.IamUserRoleInfoResponse;
import com.machine.app.iam.user.controller.vo.response.IamUserSimpleListResponseVo;
import com.machine.sdk.common.model.response.PageResponse;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IIamUserBusiness {

    void updateStatus(IamUserUpdateStatusRequestVo requestVo);

    void adminChangePassword(IamAdminChangePasswordRequestVo requestVo);

    void extractedUserIdByOrganizationIdSet(Set<String> organizationIdSet,
                                            Set<String> finallyqueryShopIdSet);

    Set<String> getIdByDepartmentIdSet(Set<String> departmentIdSet);

    Set<String> getIdByOrganizationIdSet(Set<String> organizationIdSet);

    Set<String> getIdByRoleIdSet(Set<String> roleIdSet);

    Set<String> getIdByShopIdSet(Set<String> shopIdSet);

    List<IamUserRoleInfoResponse> getUserRoleList(String userId);

    Map<String, List<IamUserRoleInfoResponse>> getUserRoleListMap(Set<String> userIdSet);

    PageResponse<IamUserSimpleListResponseVo> pageSimpled(IamUserQueryPageSimpleRequestVo request);

}
