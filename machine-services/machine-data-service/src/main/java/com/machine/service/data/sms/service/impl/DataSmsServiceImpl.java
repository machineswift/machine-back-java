package com.machine.service.data.sms.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.sms.dto.input.DataSmsCountRecordInputDto;
import com.machine.client.data.sms.dto.input.DataSmsForgetPasswordInputDto;
import com.machine.client.data.sms.dto.input.DataSmsPhoneLoginInputDto;
import com.machine.sdk.common.envm.BaseEnum;
import com.machine.sdk.common.envm.data.sms.DataSmsCategoryEnum;
import com.machine.sdk.common.envm.data.sms.DataSmsIamAuthActionEnum;
import com.machine.sdk.common.envm.data.sms.DataSmsSendResultEnum;
import com.machine.sdk.huawei.client.sms.HuaWeiSmsHttpClient;
import com.machine.sdk.huawei.client.sms.dto.input.HuaWeiSmsSendInputDto;
import com.machine.sdk.huawei.client.sms.dto.output.HuaWeiSmsSendOutputDto;
import com.machine.sdk.huawei.domain.HuaWeiSmsBaseResponse;
import com.machine.sdk.huawei.envm.HuaWeiSmsCodeEnum;
import com.machine.sdk.huawei.exception.HuaWeiSmsSdkException;
import com.machine.service.data.sms.dao.IDataSmsRecordDao;
import com.machine.service.data.sms.dao.mapper.entity.DataSmsRecordEntity;
import com.machine.service.data.sms.service.IDataSmsService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DataSmsServiceImpl implements IDataSmsService {

    /**
     * 验证码：手机号验证码登录
     */
    @Value("${huaweiyun.sms.template.captcha.phoneLogin:XXX}")
    private String phoneLoginTemplateId;

    /**
     * 验证码：忘记密码
     */
    @Value("${huaweiyun.sms.template.captcha.forgetPassword:XXX}")
    private String forgetPasswordTemplateId;

    @Resource(name = "huaWeiSmsCaptchaHttpClient")
    private HuaWeiSmsHttpClient huaWeiSmsCaptchaHttpClient;

    @Resource(name = "huaWeiSmsMessageHttpClient")
    private HuaWeiSmsHttpClient huaWeiSmsMessageHttpClient;

    @Autowired
    private IDataSmsRecordDao smsRecordDao;

    @Override
    public void send4PhoneLogin(DataSmsPhoneLoginInputDto inputDto) {
        DataSmsRecordEntity insertEntity = new DataSmsRecordEntity();
        insertEntity.setCategory(DataSmsCategoryEnum.IAM_AUTH);
        insertEntity.setCode(DataSmsIamAuthActionEnum.PHONE_CAPTCHA_LOGIN.getName());
        insertEntity.setResult(DataSmsSendResultEnum.SUCCESS);
        insertEntity.setPhone(inputDto.getTo());
        insertEntity.setMessageContent(inputDto.getCaptcha());

        HuaWeiSmsBaseResponse<HuaWeiSmsSendOutputDto> smsBaseResponse;
        try {
            HuaWeiSmsSendInputDto smsSendInputDto = new HuaWeiSmsSendInputDto();
            smsSendInputDto.setTemplateId(phoneLoginTemplateId);
            smsSendInputDto.setTo(inputDto.getTo());
            smsSendInputDto.setTemplateParas(List.of(inputDto.getCaptcha()));
            smsBaseResponse = huaWeiSmsCaptchaHttpClient.sendSms(smsSendInputDto);
        } catch (Exception e) {
            insertEntity.setResult(DataSmsSendResultEnum.FAIL);
            insertEntity.setFailReason(e.getMessage());
            smsRecordDao.insert(insertEntity);
            throw new HuaWeiSmsSdkException("sdk.huaWei.sms.sendSms.exception", "华为云发送短信异常", e);
        }

        //华为云返回结果
        insertEntity.setResultContent(JSONUtil.toJsonStr(smsBaseResponse));

        //处理返回结果
        extracted(smsBaseResponse, insertEntity);
    }

    @Override
    public void send4ForgetPassword(DataSmsForgetPasswordInputDto inputDto) {
        DataSmsRecordEntity insertEntity = new DataSmsRecordEntity();
        insertEntity.setCategory(DataSmsCategoryEnum.IAM_AUTH);
        insertEntity.setCode(DataSmsIamAuthActionEnum.USER_FORGET_PASSWORD.getName());
        insertEntity.setResult(DataSmsSendResultEnum.SUCCESS);
        insertEntity.setPhone(inputDto.getTo());
        insertEntity.setMessageContent(inputDto.getCaptcha());

        HuaWeiSmsBaseResponse<HuaWeiSmsSendOutputDto> smsBaseResponse;
        try {
            HuaWeiSmsSendInputDto smsSendInputDto = new HuaWeiSmsSendInputDto();
            smsSendInputDto.setTemplateId(forgetPasswordTemplateId);
            smsSendInputDto.setTo(inputDto.getTo());
            smsSendInputDto.setTemplateParas(List.of(inputDto.getCaptcha()));
            smsBaseResponse = huaWeiSmsCaptchaHttpClient.sendSms(smsSendInputDto);
        } catch (Exception e) {
            insertEntity.setResult(DataSmsSendResultEnum.FAIL);
            insertEntity.setFailReason(e.getMessage());
            smsRecordDao.insert(insertEntity);
            throw new HuaWeiSmsSdkException("sdk.huaWei.sms.sendSms.exception", "华为云发送短信异常", e);
        }

        //华为云返回结果
        insertEntity.setResultContent(JSONUtil.toJsonStr(smsBaseResponse));

        //处理返回结果
        extracted(smsBaseResponse, insertEntity);
    }

    @Override
    public int countByCondition(DataSmsCountRecordInputDto inputDto) {
        return smsRecordDao.countByCondition(inputDto);
    }


    private void extracted(HuaWeiSmsBaseResponse<HuaWeiSmsSendOutputDto> smsBaseResponse, DataSmsRecordEntity insertEntity) {
        String code = smsBaseResponse.getCode();
        if (HuaWeiSmsCodeEnum.SUCCESS.getCode().equals(code)) {
            List<HuaWeiSmsSendOutputDto> result = smsBaseResponse.getResult();
            if (CollUtil.isEmpty(result)) {
                insertEntity.setResult(DataSmsSendResultEnum.FAIL);
                insertEntity.setFailReason("华为云发送短信返回结果为空");
                smsRecordDao.insert(insertEntity);
                throw new HuaWeiSmsSdkException("sdk.huaWei.sms.sendSms.emptyResponse", "华为云发送短信返回结果为空");
            }
            if (result.size() > 1) {
                insertEntity.setResult(DataSmsSendResultEnum.FAIL);
                insertEntity.setFailReason("华为云发送短信返回结果大于1个");
                smsRecordDao.insert(insertEntity);
                throw new HuaWeiSmsSdkException("sdk.huaWei.sms.sendSms.responseTooMuch", "华为云发送短信返回结果大于1个");
            }

            smsRecordDao.insert(insertEntity);

        } else {
            insertEntity.setResult(DataSmsSendResultEnum.FAIL);
            insertEntity.setFailReason(BaseEnum.getInstance(HuaWeiSmsCodeEnum.class, code).getMessage());
            smsRecordDao.insert(insertEntity);
            throw new HuaWeiSmsSdkException("sdk.huaWei.sms.sendSms.httpError", BaseEnum.getInstance(HuaWeiSmsCodeEnum.class, code).getMessage());
        }
    }

}
