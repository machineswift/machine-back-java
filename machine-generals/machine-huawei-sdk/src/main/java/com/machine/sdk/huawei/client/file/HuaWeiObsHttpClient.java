package com.machine.sdk.huawei.client.file;

import com.obs.services.ObsClient;
import com.obs.services.model.*;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

@Slf4j
public class HuaWeiObsHttpClient {

    private final String bucketName;

    private final String parentDir;

    private final ObsClient obsClient;

    private final Integer expires;

    public HuaWeiObsHttpClient(String ak,
                               String sk,
                               String endPoint,
                               String bucketName,
                               String parentDir,
                               Integer expires) {
        this.bucketName = bucketName;
        this.parentDir = parentDir;
        this.expires = expires;
        this.obsClient = new ObsClient(ak, sk, endPoint);
    }

    /**
     * 上传文件 默认本地桶 本地文件夹
     *
     * @param inputStream 文件流
     * @param objectKey   文件名，如果桶中有文件夹的话，如往test文件上传test.txt文件，那么objectKey就是test/test.txt
     * @throws Exception
     */
    public PutObjectResult uploadFileByFileName(InputStream inputStream,
                                                String objectKey) throws Exception {
        PutObjectRequest request = new PutObjectRequest();
        request.setBucketName(bucketName);
        request.setObjectKey(parentDir + "/" + objectKey);
        request.setInput(inputStream);
        //公共读写
        request.setAcl(AccessControlList.REST_CANNED_PUBLIC_READ_WRITE);
        request.setExpires(expires);
        PutObjectResult putObjectResult = obsClient.putObject(request);
        inputStream.close();
        return putObjectResult;
    }

    /**
     * 默认本地桶 本地文件夹 自定义过期时间
     *
     * @param inputStream 文件流
     * @param expires     过期时间 天单位
     * @param objectKey
     * @return
     * @throws Exception
     */
    public PutObjectResult uploadFileByExpires(InputStream inputStream,
                                               Integer expires,
                                               String objectKey) throws Exception {
        PutObjectRequest request = new PutObjectRequest();
        request.setBucketName(bucketName);
        request.setObjectKey(parentDir + "/" + objectKey);
        request.setInput(inputStream);
        //公共读写
        request.setAcl(AccessControlList.REST_CANNED_PUBLIC_READ_WRITE);
        request.setExpires(expires);
        PutObjectResult putObjectResult = obsClient.putObject(request);
        inputStream.close();
        return putObjectResult;
    }

    /**
     * 自定义桶和文件夹 过期时间
     *
     * @param inputStream 文件流
     * @param expires     过期时间  天单位
     * @param objectKey   文件夹路径+文件名称  /songzhongjin/test.png
     * @return
     * @throws Exception
     */
    public PutObjectResult uploadFileByBucketName(InputStream inputStream,
                                                  Integer expires,
                                                  String bucketName,
                                                  String objectKey) throws Exception {
        PutObjectRequest request = new PutObjectRequest();
        request.setBucketName(bucketName);
        request.setObjectKey(objectKey);
        request.setInput(inputStream);
        request.setExpires(expires);
        //公共读写
        request.setAcl(AccessControlList.REST_CANNED_PUBLIC_READ_WRITE);
        PutObjectResult putObjectResult = obsClient.putObject(request);
        inputStream.close();
        return putObjectResult;
    }


    /**
     * 删除文件
     *
     * @param objectKey 文件名，如果桶中有文件夹的话，如往test文件上传test.txt文件，那么objectKey就是test/test.txt
     * @throws Exception
     */
    public DeleteObjectResult deleteFile(String objectKey) {
        DeleteObjectResult deleteObjectResult = obsClient.deleteObject(bucketName, objectKey);
        return deleteObjectResult;
    }


}
