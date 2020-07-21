package com.seizedays.edu.vod.controller.admin;



import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seizedays.edu.common.vo.ResultMsg;
import com.seizedays.edu.entity.Course;
import com.seizedays.edu.form.CourseInfoForm;
import com.seizedays.edu.query.CourseQuery;
import com.seizedays.edu.service.CourseService;
import com.seizedays.edu.vo.CoursePublishVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Helen
 * @since 2019-06-24
 */
@Api(description="课程管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/admin/edu/course")
public class CourseAdminController {

	@Autowired
	private CourseService courseService;

	@ApiOperation("新增课程")
	@PostMapping("save-course-info")
	public ResultMsg saveInfo(
			@ApiParam(name = "courseInfoForm", value="课程基本信息", required = true)
			@RequestBody CourseInfoForm courseInfoForm){

		String courseId = courseService.saveCourseInfo(courseInfoForm);
		return ResultMsg.ok().data("courseId", courseId);
	}

	@ApiOperation(value = "根据ID查询课程")
	@GetMapping("course-info/{id}")
	public ResultMsg getById(
			@ApiParam(name = "id", value = "课程ID", required = true)
			@PathVariable String id){

		CourseInfoForm courseInfoForm = courseService.getCourseInfoFormById(id);
		return ResultMsg.ok().data("item", courseInfoForm);
	}

	@ApiOperation(value = "更新课程")
	@PutMapping("update-course-info/{id}")
	public ResultMsg updateCourseInfoById(
			@ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
			@RequestBody CourseInfoForm courseInfoForm,

			@ApiParam(name = "id", value = "课程ID", required = true)
			@PathVariable String id){

		courseService.updateCourseInfoById(courseInfoForm);
		return ResultMsg.ok();
	}

	@ApiOperation(value = "分页课程列表")
	@GetMapping("{page}/{limit}")
	public ResultMsg pageQuery(
			@ApiParam(name = "page", value = "当前页码", required = true)
			@PathVariable Long page,

			@ApiParam(name = "limit", value = "每页记录数", required = true)
			@PathVariable Long limit,

			@ApiParam(name = "courseQuery", value = "查询对象", required = false)
					CourseQuery courseQuery){

		Page<Course> pageParam = new Page<>(page, limit);

		courseService.pageQuery(pageParam, courseQuery);
		List<Course> records = pageParam.getRecords();

		long total = pageParam.getTotal();

		return  ResultMsg.ok().data("total", total).data("rows", records);
	}

	@ApiOperation(value = "根据ID删除课程")
	@DeleteMapping("{id}")
	public ResultMsg removeById(
			@ApiParam(name = "id", value = "课程ID", required = true)
			@PathVariable String id){
		courseService.removeCourseById(id);
		return ResultMsg.ok();
	}

	@ApiOperation(value = "根据ID获取课程发布信息")
	@GetMapping("course-publish-info/{id}")
	public ResultMsg getCoursePublishVoById(
			@ApiParam(name = "id", value = "课程ID", required = true)
			@PathVariable String id){

		CoursePublishVo courseInfoForm = courseService.getCoursePublishVoById(id);
		return ResultMsg.ok().data("item", courseInfoForm);
	}

	@ApiOperation(value = "根据id发布课程")
	@PutMapping("publish-course/{id}")
	public ResultMsg publishCourseById(
			@ApiParam(name = "id", value = "课程ID", required = true)
			@PathVariable String id){

		courseService.publishCourseById(id);
		return ResultMsg.ok();
	}
}

