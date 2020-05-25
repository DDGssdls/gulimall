package com.edu.gulimail.oss.utils;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "aliyun.oss.file",ignoreInvalidFields = true)
// 使用的是 全局属性的绑定 使用的是 configurationProperties注解 但是使用这个注解需要将类中的get 和 set方法生成否个就是不能获取到
@Component
public class ConstantPropertiesUtils implements InitializingBean {

    private String endpoint;

    private String keyId;

    private String keySecret;

    private String bucketName;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getKeySecret() {
        return keySecret;
    }

    public void setKeySecret(String keySecret) {
        this.keySecret = keySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(endpoint + keyId + keySecret + bucketName);
    }
}
