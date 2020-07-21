package com.seizedays.edu.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.file.key-id}")
    private String keyId;
    @Value("${aliyun.oss.file.key-secret}")
    private String keySecret;
    @Value("${aliyun.oss.file.bucket-name}")
    private String bucketName;
    @Value("${aliyun.oss.file.file-host}")
    private String fileHost;

    @Value(("${spring.profiles.active}"))
    public String active;

    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;
    public static String FILE_HOST;

    public void print(){
        System.out.println(endpoint);
        System.out.println(keySecret);
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        ACCESS_KEY_ID =keyId;
        ACCESS_KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
        FILE_HOST = fileHost;

    }
}
