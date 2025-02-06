package com.machine.app.manage.hrm.department.business;

import com.machine.app.manage.hrm.department.controller.vo.response.*;
import com.machine.client.hrm.department.dto.output.DepartmentTreeOutputDto;
import com.machine.sdk.common.model.request.IdRequest;

public interface IHrmDepartmentBusiness {

    void clearCache();

    void sync();

    HrmDepartmentDetailResponseVo detail(IdRequest request);

    HrmDepartmentExpandTreeResponseVo treeAllExpand();

    DepartmentTreeOutputDto treeAllSimple();
}
