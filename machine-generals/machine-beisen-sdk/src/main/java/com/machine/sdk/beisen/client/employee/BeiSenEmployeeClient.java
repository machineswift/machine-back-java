package com.machine.sdk.beisen.client.employee;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.machine.sdk.beisen.client.employee.dto.input.BeiSenEmployeeTimeWindowInputDto;
import com.machine.sdk.beisen.client.employee.dto.output.BeiSenEmployeeInfoOutputDto;
import com.machine.sdk.beisen.constant.BeiSenApiPathConstant;
import com.machine.sdk.beisen.domain.BeiSenBaseResponse;
import com.machine.sdk.beisen.util.BeiSenOkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

@Slf4j
public class BeiSenEmployeeClient {

    /**
     * 根据时间窗滚动查询变动的员工与单条任职信息
     * <a href="https://open.italent.cn/?_qrt=html#/open-document?menu=document-center&id=d2405033-0bb3-4574-ad82-b959799e8882"></a>
     */
    public static BeiSenBaseResponse<BeiSenEmployeeInfoOutputDto> getByTimeWindow(OkHttpClient okHttpClient,
                                                                                  BeiSenEmployeeTimeWindowInputDto inputDto) {
        log.info("北森根据时间窗滚动查询变动的员工与单条任职信息开始，inputDto={}", JSONUtil.toJsonStr(inputDto));
        long startTime = System.currentTimeMillis();
        String body = JSONUtil.toJsonStr(inputDto);
        String url = BeiSenApiPathConstant.Employee.GET_BY_TIME_WINDOW;
        String responseContent = BeiSenOkHttpUtil.responseContentByPost(okHttpClient, url, body);
        log.info("北森根据时间窗滚动查询变动的员工与单条任职信息结束，executeTime={},responseContent={}",
                (System.currentTimeMillis() - startTime), responseContent);

        return Convert.convert(new TypeReference<>() {
        }, JSONUtil.parse(responseContent));
    }


    /**
     * 动查询指定组织下的员工与单条任职信息
     * <a href="https://open.italent.cn/#/open-document?menu=document-center&id=e01aa14c-7dda-430f-be9d-0d9aafc25dca"></a>
     */
    public static BeiSenBaseResponse<BeiSenEmployeeInfoOutputDto> getEmployeeOfOrganization(OkHttpClient okHttpClient,
                                                                                            BeiSenEmployeeTimeWindowInputDto inputDto) {
        log.info("北森动查询指定组织下的员工与单条任职信息开始，inputDto={}", JSONUtil.toJsonStr(inputDto));
        long startTime = System.currentTimeMillis();
        String body = JSONUtil.toJsonStr(inputDto);
        String url = BeiSenApiPathConstant.Employee.GET_EMPLOYEE_OF_ORGANIZATION;
        String responseContent = BeiSenOkHttpUtil.responseContentByPost(okHttpClient, url, body);
        log.info("北森动查询指定组织下的员工与单条任职信息结束，executeTime={},responseContent={}",
                (System.currentTimeMillis() - startTime), responseContent);

        return Convert.convert(new TypeReference<>() {
        }, JSONUtil.parse(responseContent));
    }

}
