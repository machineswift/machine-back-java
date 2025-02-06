package com.machine.sdk.beisen.client.organization;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import com.machine.sdk.beisen.client.organization.dto.input.BeiSenOrganizationGetByIdsInputDto;
import com.machine.sdk.beisen.client.organization.dto.input.BeiSenOrganizationGetSubInputDto;
import com.machine.sdk.beisen.client.organization.dto.input.BeiSenOrganizationTimeWindowInputDto;
import com.machine.sdk.beisen.client.organization.dto.output.BeiSenOrganizationInfoOutputDto;
import com.machine.sdk.beisen.constant.BeiSenApiPathConstant;
import com.machine.sdk.beisen.domain.BeiSenBaseResponse;
import com.machine.sdk.beisen.util.BeiSenOkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;


@Slf4j
public class BeiSenOrganizationClient {

    /**
     * 根据时间窗滚动查询变动的组织单元信息
     * <a href="https://open.italent.cn/?_qrt=html#/open-document?menu=document-center&id=dc1e628c-21a6-49cc-ae0c-7fc4a037d1bd"></a>
     */
    public static BeiSenBaseResponse<BeiSenOrganizationInfoOutputDto> getByTimeWindow(OkHttpClient okHttpClient,
                                                                                      BeiSenOrganizationTimeWindowInputDto inputDto) {
        log.info("北森根据时间窗滚动查询变动的组织单元信息开始，inputDto={}", JSONUtil.toJsonStr(inputDto));
        long startTime = System.currentTimeMillis();
        String body = JSONUtil.toJsonStr(inputDto);
        String url = BeiSenApiPathConstant.Organization.GET_BY_TIME_WINDOW;
        String responseContent = BeiSenOkHttpUtil.responseContentByPost(okHttpClient, url, body);
        log.info("北森根据时间窗滚动查询变动的组织单元信息结束，executeTime={},responseContent={}",
                (System.currentTimeMillis() - startTime), responseContent);

        return Convert.convert(new TypeReference<>() {
        }, JSONUtil.parse(responseContent));
    }

    /**
     * 根据组织OId集合获取组织相关信息
     * <a href="https://open.italent.cn/#/open-document?menu=document-center&id=c59e60ee-345a-4441-967c-631f8ace1adf"></a>
     */
    public static BeiSenBaseResponse<BeiSenOrganizationInfoOutputDto> getByIds(OkHttpClient okHttpClient,
                                                                               BeiSenOrganizationGetByIdsInputDto inputDto) {
        log.info("北森根据组织OId集合获取组织相关信息开始，inputDto={}", JSONUtil.toJsonStr(inputDto));
        long startTime = System.currentTimeMillis();
        String body = JSONUtil.toJsonStr(inputDto);
        String url = BeiSenApiPathConstant.Organization.GET_BY_IDS;
        String responseContent = BeiSenOkHttpUtil.responseContentByPost(okHttpClient, url, body);
        log.info("北森根据组织OId集合获取组织相关信息结束，executeTime={},responseContent={}",
                (System.currentTimeMillis() - startTime), responseContent);

        return Convert.convert(new TypeReference<>() {
        }, JSONUtil.parse(responseContent));
    }

    /**
     * 查询指定组织的下级组织单元列表
     * <a href="https://open.italent.cn/#/open-document?menu=document-center&id=cab6a177-82a4-45ac-a0e3-18a4d9f4bb3a"></a>
     */
    public static BeiSenBaseResponse<BeiSenOrganizationInfoOutputDto> getSubOrganizations(OkHttpClient okHttpClient,
                                                                                          BeiSenOrganizationGetSubInputDto inputDto) {
        log.info("北森查询指定组织的下级组织单元列表开始，inputDto={}", JSONUtil.toJsonStr(inputDto));
        long startTime = System.currentTimeMillis();
        String body = JSONUtil.toJsonStr(inputDto);
        String url = BeiSenApiPathConstant.Organization.GET_SUB_ORGANIZATIONS;
        String responseContent = BeiSenOkHttpUtil.responseContentByPost(okHttpClient, url, body);
        log.info("北森查询指定组织的下级组织单元列表结束，executeTime={},responseContent={}",
                (System.currentTimeMillis() - startTime), responseContent);

        return Convert.convert(new TypeReference<>() {
        }, JSONUtil.parse(responseContent));
    }

}
