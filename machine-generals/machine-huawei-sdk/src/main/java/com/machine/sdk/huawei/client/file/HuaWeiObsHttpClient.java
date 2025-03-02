package com.machine.sdk.huawei.client.file;

import cn.hutool.json.JSONUtil;
import com.machine.sdk.huawei.domain.UploadParamDto;
import com.obs.services.ObsClient;
import com.obs.services.model.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 参考文档:
 * <a href="https://support.huaweicloud.com/obs/index.html"></a>
 */
@Slf4j
public class HuaWeiObsHttpClient {

    private final String bucketName;

    private final String parentDir;

    private final ObsClient obsClient;

    public HuaWeiObsHttpClient(String ak,
                               String sk,
                               String endPoint,
                               String bucketName,
                               String parentDir) {
        this.bucketName = bucketName;
        this.parentDir = parentDir;
        this.obsClient = new ObsClient(ak, sk, endPoint);
    }

    public PutObjectResult uploadFileByExpires(UploadParamDto paramDto) throws Exception {
        PutObjectRequest request = new PutObjectRequest();
        request.setBucketName(bucketName);
        request.setObjectKey(parentDir + paramDto.getFilePath() + paramDto.getFileName());
        request.setInput(paramDto.getInputStream());
        request.setAcl(AccessControlList.REST_CANNED_PUBLIC_READ_WRITE);
        request.setExpires(paramDto.getExpirationDays());
        PutObjectResult putObjectResult = obsClient.putObject(request);
        log.info("华为云OBS返回数据，putObjectResult={}", JSONUtil.toJsonStr(putObjectResult));
        paramDto.getInputStream().close();
        return putObjectResult;
    }

    /**
     * 删除文件
     */
    public DeleteObjectResult deleteFile(String objectKey) {
        return obsClient.deleteObject(bucketName, objectKey);

    }

}
