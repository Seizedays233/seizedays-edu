package com.seizedays.edu.vod.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @author helen
 * @since 2019/7/3
 */
public class AliyunVODSDKUtils {

	/**
	 * 初始化vod客户端调用对象
	 * @param accessKeyId
	 * @param accessKeySecret
	 * @return
	 * @throws ClientException
	 */
	public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
		String regionId = "cn-shanghai";  // 点播服务接入区域
		DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
		DefaultAcsClient client = new DefaultAcsClient(profile);
		return client;
	}
}
