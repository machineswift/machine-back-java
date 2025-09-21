package com.machine.app.iam.dictionary.business;

import com.machine.app.iam.dictionary.controller.request.IamDictionaryEnumRequestVo;
import com.machine.app.iam.dictionary.controller.response.IamDictionaryEnumInfoResponse;

import java.util.List;

public interface IIamEnumBusiness {

    List<IamDictionaryEnumInfoResponse> queryEnumInfo(IamDictionaryEnumRequestVo request);
}
