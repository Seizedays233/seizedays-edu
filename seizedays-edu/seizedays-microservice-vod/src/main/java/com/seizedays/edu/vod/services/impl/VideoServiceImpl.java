package com.seizedays.edu.vod.services.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import com.seizedays.edu.common.constants.ResultCodeEnum;
import com.seizedays.edu.common.exception.SeizedaysException;
import com.seizedays.edu.vod.services.VideoService;
import com.seizedays.edu.vod.util.AliyunVODSDKUtils;
import com.seizedays.edu.vod.utils.ConstantPropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class VideoServiceImpl implements VideoService {
    @Override
    public String uploadVideo(MultipartFile file) {
        InputStream inputStream = null;
        String videoId = "";
        try {
            inputStream = file.getInputStream();
            String fileName = file.getOriginalFilename();

            String title = "";
            if (null != fileName) {
                title = fileName.substring(0, fileName.lastIndexOf("."));
            }
            //创建请求对象
            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET,
                    title,
                    fileName,
                    inputStream);

            //创建文件上传器
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            videoId = response.getVideoId();
            if (response.isSuccess()) {
                System.out.print("VideoId=" + response.getVideoId() + "\n");
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new SeizedaysException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }


        return videoId;
    }

    @Override
    public void removeVideo(String videoId) {

        DefaultAcsClient client = null;
        try {
            client = AliyunVODSDKUtils.initVodClient(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);

            DeleteVideoResponse response = client.getAcsResponse(request);

        } catch (ClientException e) {
            throw new SeizedaysException(ResultCodeEnum.VIDEO_DELETE_ALIYUN_ERROR);
        }

    }

    /**
     * 获取上传凭证和上传地址
     * @param title
     * @param fileName
     * @return
     */
    @Override
    public CreateUploadVideoResponse getUploadAuthAndAddress(String title, String fileName) {

        try {
            //创建请求对象
            DefaultAcsClient client = AliyunVODSDKUtils.initVodClient(
                    ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET
            );

            //为请求对象组织私有参数
            CreateUploadVideoRequest request = new CreateUploadVideoRequest();
            request.setTitle(title);
            request.setFileName(fileName);

            //发送请求，获取响应对象
            CreateUploadVideoResponse response = client.getAcsResponse(request);

            //从响应对象中获取返回值
            return response;

        }catch (ClientException e){
            throw  new SeizedaysException(ResultCodeEnum.FETCH_VIDEO_PLAYAUTH_ERROR);
        }

    }

    @Override
    public RefreshUploadVideoResponse refreshUploadAuthAndAddress(String videoId) {

        try {
            //创建请求对象
            DefaultAcsClient client = AliyunVODSDKUtils.initVodClient(
                    ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET
            );

            //为请求对象组织私有参数
            RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
            request.setVideoId(videoId);

            //发送请求，获取响应对象
            RefreshUploadVideoResponse response = client.getAcsResponse(request);

            //从响应对象中获取返回值
            return response;

        }catch (ClientException e){
            throw  new SeizedaysException(ResultCodeEnum.REFRESH_VIDEO_PLAYAUTH_ERROR);
        }
    }
}
