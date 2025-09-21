package com.machine.sdk.beisen.client.position;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.machine.sdk.beisen.client.position.dto.input.BeiSenPositionGetByOIdsInputDto;
import com.machine.sdk.beisen.client.position.dto.input.BeiSenPositionWindowInputDto;
import com.machine.sdk.beisen.client.position.dto.output.BeiSenPositionOutputDto;
import com.machine.sdk.beisen.constant.BeiSenApiPathConstant;
import com.machine.sdk.beisen.domain.BeiSenBaseResponse;
import com.machine.sdk.beisen.util.BeiSenOkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

@Slf4j
public class BeiSenPositionClient {

    /**
     * 根据时间窗滚动查询变动的职位信息
     * <a href="https://open.italent.cn/#/open-document?menu=document-center&id=8aa6b3ba-a5dd-43d9-bd77-e9c1ae7296ca"></a>
     */
    public static BeiSenBaseResponse<BeiSenPositionOutputDto> getByTimeWindow(OkHttpClient okHttpClient,
                                                                              BeiSenPositionWindowInputDto inputDto) {
        log.info("北森根据时间窗滚动查询变动的职位信息开始，inputDto={}", JSONUtil.toJsonStr(inputDto));
        long startTime = System.currentTimeMillis();
        String body = JSONUtil.toJsonStr(inputDto);
        String url = BeiSenApiPathConstant.Position.GET_BY_TIME_WINDOW;
        String responseContent = BeiSenOkHttpUtil.responseContentByPost(okHttpClient, url, body);
        log.info("北森根据时间窗滚动查询变动的职位信息结束，executeTime={},responseContent={}",
                (System.currentTimeMillis() - startTime), responseContent);

        return Convert.convert(new TypeReference<>() {
        }, JSONUtil.parse(responseContent));
    }

    /**
     * 根据职位OId集合获取的职位相关信息
     * <a href="https://open.italent.cn/#/open-document?menu=document-center&id=13a1e776-cc5a-42b9-b3db-f5e4ac6767c7"></a>
     */
    public static BeiSenBaseResponse<BeiSenPositionOutputDto> getByIds(OkHttpClient okHttpClient,
                                                                       BeiSenPositionGetByOIdsInputDto inputDto) {
        log.info("北森根据职位OId集合获取的职位相关信息开始，inputDto={}", JSONUtil.toJsonStr(inputDto));
        long startTime = System.currentTimeMillis();
        String body = JSONUtil.toJsonStr(inputDto);
        String url = BeiSenApiPathConstant.Position.GET_BY_OIDS;
        String responseContent = BeiSenOkHttpUtil.responseContentByPost(okHttpClient, url, body);
        log.info("北森根据职位OId集合获取的职位相关信息结束，executeTime={},responseContent={}",
                (System.currentTimeMillis() - startTime), responseContent);

        return Convert.convert(new TypeReference<>() {
        }, JSONUtil.parse(responseContent));
    }

}
