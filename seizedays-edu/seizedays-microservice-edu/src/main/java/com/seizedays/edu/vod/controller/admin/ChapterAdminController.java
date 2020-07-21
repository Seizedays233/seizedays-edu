package com.seizedays.edu.vod.controller.admin;



import com.baomidou.mybatisplus.extension.api.R;
import com.seizedays.edu.common.vo.ResultMsg;
import com.seizedays.edu.entity.Chapter;
import com.seizedays.edu.service.ChapterService;
import com.seizedays.edu.vo.ChapterVo;
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
@Api(description="课程章节管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/admin/edu/chapter")
public class ChapterAdminController {

	@Autowired
	private ChapterService chapterService;

	@ApiOperation(value = "新增章节")
	@PostMapping
	public ResultMsg save(
			@ApiParam(name = "chapter", value = "章节对象", required = true)
			@RequestBody Chapter chapter){

		chapterService.save(chapter);
		return ResultMsg.ok();
	}

	@ApiOperation(value = "根据ID查询章节")
	@GetMapping("{id}")
	public ResultMsg getById(
			@ApiParam(name = "id", value = "章节ID", required = true)
			@PathVariable String id){

		Chapter chapter = chapterService.getById(id);
		return ResultMsg.ok().data("item", chapter);
	}

	@ApiOperation(value = "根据ID修改章节")
	@PutMapping("{id}")
	public ResultMsg updateById(
			@ApiParam(name = "id", value = "章节ID", required = true)
			@PathVariable String id,

			@ApiParam(name = "chapter", value = "章节对象", required = true)
			@RequestBody Chapter chapter){

		chapter.setId(id);
		chapterService.updateById(chapter);
		return ResultMsg.ok();
	}

	@ApiOperation(value = "根据ID删除章节")
	@DeleteMapping("{id}")
	public ResultMsg removeById(
			@ApiParam(name = "id", value = "章节ID", required = true)
			@PathVariable String id){

		chapterService.removeChapterById(id);
		return ResultMsg.ok();
	}

	@ApiOperation(value = "嵌套章节数据列表")
	@GetMapping("nested-list/{courseId}")
	public ResultMsg nestedListByCourseId(
			@ApiParam(name = "courseId", value = "课程ID", required = true)
			@PathVariable String courseId){

		List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);
		return ResultMsg.ok().data("items", chapterVoList);
	}
}

