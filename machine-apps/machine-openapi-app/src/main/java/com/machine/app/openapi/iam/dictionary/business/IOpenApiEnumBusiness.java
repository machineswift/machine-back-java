package com.machine.app.openapi.iam.dictionary.business;

import com.machine.app.openapi.iam.dictionary.controller.request.OpenApiEnumQueryEnumInfoRequestVo;
import com.machine.app.openapi.iam.dictionary.controller.response.OpenApiEnumInfoResponse;

import java.util.List;

public interface IOpenApiEnumBusiness {

    List<OpenApiEnumInfoResponse> queryEnumInfo(OpenApiEnumQueryEnumInfoRequestVo request);
}
