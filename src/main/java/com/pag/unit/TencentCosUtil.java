package com.pag.unit;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;

/**
 * 腾讯云COS操作
 */
@Component
public class TencentCosUtil {
    @Value("${tencent.secretId}")
    private String secretId;

    @Value("${tencent.secretKey}")
    private String secretKey;

    @Value("${tencent.region}")
    private String region;

    @Value("${tencent.bucketName}")
    private String bucketName;

    /**
     * 上传文件到cos
     * 返回url
     *
     * @param filePath
     */
    public String uploadObject(String filePath) {
        // 创建一个cos客户端
        COSCredentials cred = new BasicCOSCredentials("AKIDTr4PkuuoX4U9250uwwukgaInL0Xs7D5s", "odOBwhTAuWwEMXrFlI2kJ3kJrOweUNQj");
        Region region = new Region("ap-guangzhou");
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        COSClient cosClient = new COSClient(cred, clientConfig);

        // 上传文件
        String key = "test/"+filePath.substring(filePath.lastIndexOf("/")+1);
        PutObjectRequest putObjectRequest = new PutObjectRequest("pag-1301860400", key, new File(filePath));
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        cosClient.shutdown();
        return "https://pag-1301860400.cos." + region.getRegionName() + ".myqcloud.com/" + key;
    }





}
