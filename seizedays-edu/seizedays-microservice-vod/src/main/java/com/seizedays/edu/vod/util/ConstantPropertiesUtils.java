package com.seizedays.edu.vod.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author helen
 * @since 2019/7/3
 */
@Component
//@PropertySource("classpath:application.properties")
public class ConstantPropertiesUtils implements InitializingBean {

	@Value("${aliyun.vod.file.key-id}")
	private String keyId;

	@Value("${aliyun.vod.file.key-secret}")
	private String keySecret;

	public static String ACCESS_KEY_ID;
	public static String ACCESS_KEY_SECRET;

	@Override
	public void afterPropertiesSet() throws Exception {
		ACCESS_KEY_ID = keyId;
		ACCESS_KEY_SECRET = keySecret;
	}
}
