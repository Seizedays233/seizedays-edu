package com.seizedays.edu.service;

import com.seizedays.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import com.seizedays.edu.form.VideoInfoForm;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Seizedays
 * @since 2020-07-07
 */
public interface VideoService extends IService<Video> {
    void saveVideoInfo(VideoInfoForm videoInfoForm);

    VideoInfoForm getVideoInfoFormById(String id);

    void updateVideoInfoById(VideoInfoForm videoInfoForm);

    void removeVideoById(String id);
}
