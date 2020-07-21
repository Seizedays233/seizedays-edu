package com.seizedays.edu.vod.controller.admin;



import com.baomidou.mybatisplus.extension.api.R;
import com.seizedays.edu.common.vo.ResultMsg;
import com.seizedays.edu.form.VideoInfoForm;
import com.seizedays.edu.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Helen
 * @since 2019-06-24
 */
@Api(description="课时管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/admin/edu/video")
public class VideoAdminController {

	@Autowired
	private VideoService videoService;

	@ApiOperation(value = "新增课时")
	@PostMapping("save-video-info")
	public ResultMsg  save(
			@ApiParam(name = "videoForm", value = "课时对象", required = true)
			@RequestBody VideoInfoForm videoInfoForm){

		videoService.saveVideoInfo(videoInfoForm);
		return ResultMsg.ok();
	}

	@ApiOperation(value = "根据ID查询课时")
	@GetMapping("video-info/{id}")
	public ResultMsg getVideInfoById(
			@ApiParam(name = "id", value = "课时ID", required = true)
			@PathVariable String id){

		VideoInfoForm videoInfoForm = videoService.getVideoInfoFormById(id);
		return ResultMsg.ok().data("item", videoInfoForm);
	}

	@ApiOperation(value = "更新课时")
	@PutMapping("update-video-info/{id}")
	public ResultMsg updateCourseInfoById(
			@ApiParam(name = "VideoInfoForm", value = "课时基本信息", required = true)
			@RequestBody VideoInfoForm videoInfoForm,

			@ApiParam(name = "id", value = "课时ID", required = true)
			@PathVariable String id){

		videoService.updateVideoInfoById(videoInfoForm);
		return ResultMsg.ok();
	}

	@ApiOperation(value = "根据ID删除课时")
	@DeleteMapping("{id}")
	public ResultMsg removeById(
			@ApiParam(name = "id", value = "课时ID", required = true)
			@PathVariable String id){

		videoService.removeVideoById(id);
		return ResultMsg.ok();
	}

}

