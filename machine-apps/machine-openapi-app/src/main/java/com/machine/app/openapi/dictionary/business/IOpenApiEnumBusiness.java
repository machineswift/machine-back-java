package com.machine.app.openapi.dictionary.business;

import com.machine.app.openapi.dictionary.controller.request.OpenApiEnumQueryEnumInfoRequestVo;
import com.machine.app.openapi.dictionary.controller.response.OpenApiEnumInfoResponse;

import java.util.List;

public interface IOpenApiEnumBusiness {

    List<OpenApiEnumInfoResponse> queryEnumInfo(OpenApiEnumQueryEnumInfoRequestVo request);
}
