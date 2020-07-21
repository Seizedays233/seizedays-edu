package com.seizedays.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.seizedays.edu.entity.Course;
import com.seizedays.edu.form.CourseInfoForm;
import com.seizedays.edu.query.CourseQuery;
import com.seizedays.edu.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Helen
 * @since 2019-06-24
 */
public interface CourseService extends IService<Course> {

	String saveCourseInfo(CourseInfoForm courseInfoForm);

	CourseInfoForm getCourseInfoFormById(String id);

	void updateCourseInfoById(CourseInfoForm courseInfoForm);

	void pageQuery(Page<Course> pageParam, CourseQuery courseQuery);

	void removeCourseById(String id);

	CoursePublishVo getCoursePublishVoById(String id);

	void publishCourseById(String id);

	Map<String,Object> pageListWeb(Page<Course> coursePage);
}
