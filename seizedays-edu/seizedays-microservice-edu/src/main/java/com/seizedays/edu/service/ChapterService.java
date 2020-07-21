package com.seizedays.edu.service;

import com.seizedays.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.seizedays.edu.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Seizedays
 * @since 2020-07-07
 */
public interface ChapterService extends IService<Chapter> {
    void removeChapterById(String id);

    List<ChapterVo> nestedList(String courseId);
}
