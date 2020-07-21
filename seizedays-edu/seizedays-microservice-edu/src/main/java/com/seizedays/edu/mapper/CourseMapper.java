package com.seizedays.edu.mapper;

import com.seizedays.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seizedays.edu.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Seizedays
 * @since 2020-07-07
 */
public interface CourseMapper extends BaseMapper<Course> {
    CoursePublishVo selectCoursePublishVoById(String id);
}
