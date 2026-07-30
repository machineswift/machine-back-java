package com.machine.app.iam.dictionary.business;

import com.machine.app.iam.dictionary.controller.vo.request.IamDictionaryEnumRequestVo;
import com.machine.app.iam.dictionary.controller.vo.response.IamDictionaryEnumInfoResponse;

import java.util.List;

public interface IIamEnumBusiness {

    List<IamDictionaryEnumInfoResponse> queryEnumInfo(IamDictionaryEnumRequestVo request);
}
