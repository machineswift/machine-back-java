package com.machine.sdk.beisen.client.jobpost;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.machine.sdk.beisen.client.jobpost.dto.input.BeiSenJobPostGetByOIdsInputDto;
import com.machine.sdk.beisen.client.jobpost.dto.input.BeiSenJobPostWindowInputDto;
import com.machine.sdk.beisen.client.jobpost.dto.output.BeiSenJobPostOutputDto;
import com.machine.sdk.beisen.constant.BeiSenApiPathConstant;
import com.machine.sdk.beisen.domain.BeiSenBaseResponse;
import com.machine.sdk.beisen.util.BeiSenOkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

@Slf4j
public class BeiSenJobPostClient {

    /**
     * 根据时间窗滚动查询变动的职务信息
     * <a href="https://open.italent.cn/#/open-document?menu=document-center&id=7c19ab15-5aa0-4268-9f72-3984566aa911"></a>
     */
    public static BeiSenBaseResponse<BeiSenJobPostOutputDto> getByTimeWindow(OkHttpClient okHttpClient,
                                                                             BeiSenJobPostWindowInputDto inputDto) {
        log.info("北森根据时间窗滚动查询变动的职务信息开始，inputDto={}", JSONUtil.toJsonStr(inputDto));
        long startTime = System.currentTimeMillis();
        String body = JSONUtil.toJsonStr(inputDto);
        String url = BeiSenApiPathConstant.JobPost.GET_BY_TIME_WINDOW;
        String responseContent = BeiSenOkHttpUtil.responseContentByPost(okHttpClient, url, body);
        log.info("北森根据时间窗滚动查询变动的职务信息结束，executeTime={},responseContent={}",
                (System.currentTimeMillis() - startTime), responseContent);

        return Convert.convert(new TypeReference<>() {
        }, JSONUtil.parse(responseContent));
    }

    /**
     * 通过职务OID获取职务信息
     * <a href="https://open.italent.cn/#/open-document?menu=document-center&id=b1536410-e283-43ff-943d-a7e398d62d37"></a>
     */
    public static BeiSenBaseResponse<BeiSenJobPostOutputDto> getByIds(OkHttpClient okHttpClient,
                                                                      BeiSenJobPostGetByOIdsInputDto inputDto) {
        log.info("北森通过职务OID获取职务信息开始，inputDto={}", JSONUtil.toJsonStr(inputDto));
        long startTime = System.currentTimeMillis();
        String body = JSONUtil.toJsonStr(inputDto);
        String url =  BeiSenApiPathConstant.JobPost.GET_BY_OIDS;
        String responseContent = BeiSenOkHttpUtil.responseContentByPost(okHttpClient, url, body);
        log.info("北森通过职务OID获取职务信息结束，executeTime={},responseContent={}",
                (System.currentTimeMillis() - startTime), responseContent);

        return Convert.convert(new TypeReference<>() {
        }, JSONUtil.parse(responseContent));
    }

}
