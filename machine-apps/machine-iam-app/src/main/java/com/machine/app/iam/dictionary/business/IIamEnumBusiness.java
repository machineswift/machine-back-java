package com.machine.app.iam.dictionary.business;

import com.machine.app.iam.dictionary.controller.request.IamEnumQueryEnumInfoRequestVo;
import com.machine.app.iam.dictionary.controller.response.IamEnumInfoResponse;

import java.util.List;

public interface IIamEnumBusiness {

    List<IamEnumInfoResponse> queryEnumInfo(IamEnumQueryEnumInfoRequestVo request);
}
