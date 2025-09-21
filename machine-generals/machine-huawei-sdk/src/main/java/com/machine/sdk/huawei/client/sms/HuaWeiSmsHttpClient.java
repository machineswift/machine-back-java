package com.machine.sdk.huawei.client.sms;

import com.machine.sdk.huawei.domain.HuaWeiSmsBaseResponse;
import com.machine.sdk.huawei.client.sms.dto.input.HuaWeiSmsSendInputDto;
import com.machine.sdk.huawei.client.sms.dto.output.HuaWeiSmsSendOutputDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.*;

@Slf4j
public class HuaWeiSmsHttpClient {

    private final String appKey;
    private final String appSecret;
    private final String sender;

    public HuaWeiSmsHttpClient(String appKey,
                               String appSecret,
                               String sender) {
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.sender = sender;
    }

    /**
     * 发送短信API
     * <a href="https://support.huaweicloud.com/intl/zh-cn/api-msgsms/sms_05_0001.html#ZH-CN_TOPIC_0000001174513109__t120822d9ec0448e09ca147d5bf91ef23">发送短信API</a>
     */
    @SneakyThrows
    public HuaWeiSmsBaseResponse<HuaWeiSmsSendOutputDto> sendSms(HuaWeiSmsSendInputDto inputDto) {
//        log.info("华为云发送短信开始，inputDto={}", JSONUtil.toJsonStr(inputDto));
//
//        Request request = getRequest(inputDto);
//        HttpRequestBase signedRequest = Client.sign(request, SIGNATURE_ALGORITHM_SDK_HMAC_SHA256);
//        CloseableHttpClient client = (CloseableHttpClient) createHttpClient();
//        long startTime = System.currentTimeMillis();
//        HttpResponse response = client.execute(signedRequest);
//        String responseContent = EntityUtils.toString(response.getEntity());
//
//        log.info("华为云发送短信结束，executionTime={},response={}", (System.currentTimeMillis() - startTime), responseContent);
//        return Convert.convert(new TypeReference<>() {
//        }, JSONUtil.parse(responseContent));
        return null;
    }
//
//    private Request getRequest(HuaWeiSmsSendInputDto inputDto) throws UnsupportedEncodingException {
//        String url = HUA_WEI_SMS_API_BASE_URL + SEND_SMS_PATH;
//        String body = "from=" + URLEncoder.encode(sender, UTF_8) +
//                "&to=" + URLEncoder.encode(inputDto.getTo(), UTF_8) +
//                "&templateId=" + URLEncoder.encode(inputDto.getTemplateId(), UTF_8) +
//                "&templateParas=" + URLEncoder.encode(JSONUtil.toJsonStr(inputDto.getTemplateParas()), UTF_8);
//
//        Request request = new Request();
//        request.setKey(appKey);
//        request.setSecret(appSecret);
//        request.setMethod("POST");
//        request.setUrl(url);
//        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
//        request.setBody(body);
//        return request;
//    }

//    @SneakyThrows
//    private HttpClient createHttpClient() {
//        TrustAllManager[] trust = {new TrustAllManager()};
//        SSLContext sslContext = SSLContext.getInstance(INTERNATIONAL_PROTOCOL, "SunJSSE");
//        SecureRandom secureRandom = new SP800SecureRandomBuilder(SecureRandom.getInstanceStrong(), true)
//                .setEntropyBitsRequired(ENTROPY_BITS_REQUIRED)
//                .buildCTR(new AESEngine(), CIPHER_LEN, null, false);
//        sslContext.init(null, trust, secureRandom);
//        sslContext.getServerSessionContext().setSessionCacheSize(8192);
//        sslContext.getServerSessionContext().setSessionTimeout(3600);
//        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
//                null, null, new TrustAllHostnameVerifier());
//        return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
//    }

    /**
     * 不校验服务端证书
     */
//    private static class TrustAllManager implements X509TrustManager {
//        private final X509Certificate[] issuers;
//
//        public TrustAllManager() {
//            this.issuers = new X509Certificate[0];
//        }
//
//        public X509Certificate[] getAcceptedIssuers() {
//            return issuers;
//        }
//
//        public void checkClientTrusted(X509Certificate[] chain, String authType) {
//        }
//
//        public void checkServerTrusted(X509Certificate[] chain, String authType) {
//        }
//    }

    /**
     * 不校验域名
     */
    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static final int CIPHER_LEN = 256;
    private static final int ENTROPY_BITS_REQUIRED = 384;
    private static final String INTERNATIONAL_PROTOCOL = "TLSv1.2";
    public static final String SIGNATURE_ALGORITHM_SDK_HMAC_SHA256 = "SDK-HMAC-SHA256";
}
