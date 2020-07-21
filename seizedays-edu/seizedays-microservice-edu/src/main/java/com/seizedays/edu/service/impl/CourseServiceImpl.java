package com.seizedays.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seizedays.edu.common.exception.SeizedaysException;
import com.seizedays.edu.entity.Chapter;
import com.seizedays.edu.entity.Course;
import com.seizedays.edu.entity.CourseDescription;
import com.seizedays.edu.entity.Video;
import com.seizedays.edu.form.CourseInfoForm;
import com.seizedays.edu.mapper.ChapterMapper;
import com.seizedays.edu.mapper.CourseDescriptionMapper;
import com.seizedays.edu.mapper.CourseMapper;
import com.seizedays.edu.mapper.VideoMapper;
import com.seizedays.edu.query.CourseQuery;
import com.seizedays.edu.service.CourseService;
import com.seizedays.edu.vo.CoursePublishVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Helen
 * @since 2019-06-24
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

	@Autowired
	private CourseDescriptionMapper courseDescriptionMapper;
	@Autowired
	private VideoMapper videoMapper;
	@Autowired
	private ChapterMapper chapterMapper;


	@Transactional
	@Override
	public String saveCourseInfo(CourseInfoForm courseInfoForm) {

		//保存课程基本信息
		Course course = new Course();
		BeanUtils.copyProperties(courseInfoForm, course);
		course.setStatus(Course.COURSE_DRAFT);
		baseMapper.insert(course);

		//保存课程详情信息
		CourseDescription courseDescription = new CourseDescription();
		courseDescription.setDescription(courseInfoForm.getDescription());
		courseDescription.setId(course.getId());//设置一对一关联id
		courseDescriptionMapper.insert(courseDescription);

		return course.getId();
	}

	@Override
	public CourseInfoForm getCourseInfoFormById(String id) {

		//从course表中取数据
		Course course = baseMapper.selectById(id);
		if(course == null){
			throw new SeizedaysException(20001, "数据不存在");
		}

		//从course_description表中取数据
		CourseDescription courseDescription = courseDescriptionMapper.selectById(id);
		if(courseDescription == null){
			throw new SeizedaysException(20001, "数据不完整");
		}

		//创建courseInfoForm对象
		CourseInfoForm courseInfoForm = new CourseInfoForm();
		BeanUtils.copyProperties(course, courseInfoForm);
		BeanUtils.copyProperties(courseDescription, courseInfoForm);

		return courseInfoForm;
	}

	@Transactional
	@Override
	public void updateCourseInfoById(CourseInfoForm courseInfoForm) {
		//保存课程基本信息
		Course course = new Course();
		BeanUtils.copyProperties(courseInfoForm, course);
		baseMapper.updateById(course);

		//保存课程详情信息
		CourseDescription courseDescription = new CourseDescription();
		courseDescription.setDescription(courseInfoForm.getDescription());
		courseDescriptionMapper.updateById(courseDescription);
	}

	@Override
	public void pageQuery(Page<Course> pageParam, CourseQuery courseQuery) {

		QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("gmt_create");

		if (courseQuery == null){
			baseMapper.selectPage(pageParam, queryWrapper);
			return;
		}

		String title = courseQuery.getTitle();
		String teacherId = courseQuery.getTeacherId();
		String subjectParentId = courseQuery.getSubjectParentId();
		String subjectId = courseQuery.getSubjectId();

		if (!StringUtils.isEmpty(title)) {
			queryWrapper.like("title", title);
		}

		if (!StringUtils.isEmpty(teacherId) ) {
			queryWrapper.eq("teacher_id", teacherId);
		}

		if (!StringUtils.isEmpty(subjectParentId)) {
			queryWrapper.ge("subject_parent_id", subjectParentId);
		}

		if (!StringUtils.isEmpty(subjectId)) {
			queryWrapper.ge("subject_id", subjectId);
		}

		baseMapper.selectPage(pageParam, queryWrapper);
	}

	@Transactional
	@Override
	public void removeCourseById(String id) {

		//根据id删除所有视频
		QueryWrapper<Video> queryWrapperVideo = new QueryWrapper<>();
		queryWrapperVideo.eq("course_id", id);
		videoMapper.delete(queryWrapperVideo);

		//根据id删除所有章节
		QueryWrapper<Chapter> queryWrapperChapter = new QueryWrapper<>();
		queryWrapperChapter.eq("course_id", id);
		chapterMapper.delete(queryWrapperChapter);

		//删除课程详情
		courseDescriptionMapper.deleteById(id);

		//根据id删除课程
		baseMapper.deleteById(id);
	}

	@Override
	public CoursePublishVo getCoursePublishVoById(String id) {
		return baseMapper.selectCoursePublishVoById(id);
	}

	@Override
	public void publishCourseById(String id) {
		Course course = new Course();
		course.setId(id);
		course.setStatus(Course.COURSE_NORMAL);
		baseMapper.updateById(course);
	}

	@Override
	public Map<String, Object> pageListWeb(Page<Course> coursePage) {

		QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
		queryWrapper.orderByDesc("gmt_modified");

		baseMapper.selectPage(coursePage, queryWrapper);

		List<Course> records = coursePage.getRecords();
		long current = coursePage.getCurrent();
		long size = coursePage.getSize();
		long total = coursePage.getTotal();
		boolean hasNext = coursePage.hasNext();
		boolean hasPrevious = coursePage.hasPrevious();
		long pages = coursePage.getPages();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("items", records);
		map.put("current", current);
		map.put("pages", pages);
		map.put("size", size);
		map.put("total", total);
		map.put("hasNext", hasNext);
		map.put("hasPrevious", hasPrevious);

		return map;
	}
}
