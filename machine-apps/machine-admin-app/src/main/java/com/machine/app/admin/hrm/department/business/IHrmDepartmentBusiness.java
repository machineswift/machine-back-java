package com.machine.app.admin.hrm.department.business;

import com.machine.app.admin.hrm.department.controller.vo.response.HrmDepartmentDetailResponseVo;
import com.machine.app.admin.hrm.department.controller.vo.response.HrmDepartmentExpandTreeResponseVo;
import com.machine.client.hrm.department.dto.output.HrmDepartmentTreeOutputDto;
import com.machine.sdk.base.model.request.IdRequest;

public interface IHrmDepartmentBusiness {

    HrmDepartmentDetailResponseVo detail(IdRequest request);

    HrmDepartmentExpandTreeResponseVo treeAllExpand();

    HrmDepartmentTreeOutputDto treeAllSimple();
}
