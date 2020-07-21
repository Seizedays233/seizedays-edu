package com.seizedays.edu.vod.controller.admin;

import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.aliyuncs.vod.model.v20170321.RefreshUploadVideoResponse;
import com.baomidou.mybatisplus.extension.api.R;
import com.seizedays.edu.common.vo.ResultMsg;
import com.seizedays.edu.vod.services.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = {"aliyun-video"})
@CrossOrigin
@RestController
@RequestMapping("/admin/vod/video")
public class VideoAdminController {
    @Autowired
    private VideoService videoService;

    @PostMapping("upload")
    public ResultMsg uploadVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file){

        String videoId = videoService.uploadVideo(file);
        return ResultMsg.ok().message("视频文件上传成功").data("videoId", videoId);
    }

    @DeleteMapping("{videoId}")
    public ResultMsg removeVideo(
            @ApiParam(name="videoId", value="阿里云视频id", required = true)
            @PathVariable String videoId){
        videoService.removeVideo(videoId);
        return ResultMsg.ok().message("视频删除成功");
    }

    @GetMapping("get-upload-auth-and-address/{title}/{fileName}")
    public ResultMsg getUploadAuthAndAddress(
            @ApiParam(name="title", value="视频标题", required = true)
            @PathVariable String title,

            @ApiParam(name="fileName", value="视频文件", required = true)
            @PathVariable String fileName){

        CreateUploadVideoResponse response = videoService.getUploadAuthAndAddress(title, fileName);
        return ResultMsg.ok().message("获取视频上传凭证和地址成功").data("response", response);
    }


    @GetMapping("refresh-upload-auth-and-address/{videoId}")
    public ResultMsg refreshUploadAuthAndAddress(
            @ApiParam(name="videoId", value="视频id", required = true)
            @PathVariable String videoId){

        RefreshUploadVideoResponse response = videoService.refreshUploadAuthAndAddress(videoId);
        return ResultMsg.ok().message("刷新视频上传凭证和地址成功").data("response", response);
    }
}
