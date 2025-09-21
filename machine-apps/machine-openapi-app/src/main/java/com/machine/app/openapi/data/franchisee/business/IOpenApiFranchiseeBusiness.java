package com.machine.app.openapi.data.franchisee.business;

import com.machine.app.openapi.data.franchisee.controller.vo.request.*;
import com.machine.app.openapi.data.franchisee.controller.vo.response.OpenApiFranchiseeDetailResponseVo;
import com.machine.app.openapi.data.franchisee.controller.vo.response.OpenApiFranchiseeListSampleResponseVo;
import com.machine.app.openapi.data.franchisee.controller.vo.response.OpenapiFranchiseeHealthCertificateResponseVo;
import com.machine.app.openapi.data.franchisee.controller.vo.response.OpenapiFranchiseeIdentityCardResponseVo;

import java.util.List;

public interface IOpenApiFranchiseeBusiness {

    OpenApiFranchiseeDetailResponseVo detail(OpenApiFranchiseeIdRequestVo request);

    OpenapiFranchiseeIdentityCardResponseVo identityCard(OpenApiFranchiseeIdRequestVo request);

    OpenapiFranchiseeHealthCertificateResponseVo healthCertificate(OpenApiFranchiseeIdRequestVo request);

    List<OpenApiFranchiseeListSampleResponseVo> listSample(OpenApiFranchiseeListSampleRequestVo request);

}
