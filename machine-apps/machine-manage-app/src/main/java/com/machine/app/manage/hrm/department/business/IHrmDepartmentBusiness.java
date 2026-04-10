package com.machine.app.manage.hrm.department.business;

import com.machine.app.manage.hrm.department.controller.vo.response.*;
import com.machine.client.hrm.department.dto.output.HrmDepartmentTreeOutputDto;
import com.machine.sdk.base.model.request.IdRequest;

public interface IHrmDepartmentBusiness {

    HrmDepartmentDetailResponseVo detail(IdRequest request);

    HrmDepartmentExpandTreeResponseVo treeAllExpand();

    HrmDepartmentTreeOutputDto treeAllSimple();
}
