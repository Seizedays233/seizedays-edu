package com.seizedays.edu.vod.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seizedays.edu.common.constants.ResultCodeEnum;
import com.seizedays.edu.common.exception.SeizedaysException;
import com.seizedays.edu.common.vo.ResultMsg;
import com.seizedays.edu.entity.Teacher;
import com.seizedays.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/edu/teacher")
@CrossOrigin
@Api(tags = {"teacher"})
public class TeacherAdminController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping
    public ResultMsg list() {
        List<Teacher> teacherList = teacherService.list(null);
//        HashMap<String, List<Teacher>> listHashMap = new HashMap<>();
//        listHashMap.put("items", teacherList);
//        return ResultMsg.ok().data(listHashMap);.
        return ResultMsg.ok()
                .data("items", teacherList)
                .message("查询讲师成功");
    }

    @ApiOperation(value = "根据id删除讲师")
    @DeleteMapping("{id}")
    public ResultMsg removeById(
            @ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id) {
        teacherService.removeById(id);

        return ResultMsg.ok();
    }

    @ApiOperation(value = "分页显示讲师列表")
    @GetMapping("{page}/{limit}")
    public ResultMsg pageList(
            @ApiParam(name = "page", value = "页码", required = true) @PathVariable long page,
            @ApiParam(name = "limit", value = "每页显示条目", required = true) @PathVariable long limit) {

        //抛出自定义异常
        if (page <= 0 || limit <= 0){
            throw new SeizedaysException(ResultCodeEnum.PARAM_ERROR);
        }

        Page<Teacher> teacherPage = new Page<>(page, limit);

        teacherService.page(teacherPage, null);
        List<Teacher> teachersRecords = teacherPage.getRecords();
        long totalAmount = teacherPage.getTotal();

        return ResultMsg.ok().data("rows", teachersRecords).data("total", totalAmount);

    }

    @ApiOperation("新增讲师")
    @PostMapping
    public ResultMsg save(
            @ApiParam(name = "teacher", value = "讲师对象", required = true) @RequestBody Teacher teacher) {

        teacherService.save(teacher);
        return ResultMsg.ok();
    }

    @ApiOperation("根据id查询讲师")
    @GetMapping("{id}")
    public ResultMsg getById(
            @ApiParam(name = "id", value = "讲师id", required = true)
            @PathVariable String id) {
        Teacher teacher = teacherService.getById(id);
        return ResultMsg.ok().data("item", teacher);
    }

    @ApiOperation("根据id修改讲师")
    @PutMapping("{id}")
    public ResultMsg updateById(
            @ApiParam(name = "id", value = "讲师id", required = true)
            @PathVariable String id,
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody Teacher teacher
    ) {
        teacher.setId(id);
        teacherService.updateById(teacher);
        return ResultMsg.ok();
    }

}
