package com.seizedays.edu.user.controller.admin;

import com.seizedays.edu.common.vo.ResultMsg;
import com.seizedays.edu.user.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author helen
 * @since 2019/7/1
 */
@CrossOrigin
@RestController
@RequestMapping("/admin/ucenter/member")
@Api(tags = {"user"})
@Slf4j
public class MemberAdminController {

	@Autowired
	private MemberService memberService;

	@ApiOperation(value = "今日注册数")
	@GetMapping(value = "count-register/{day}")
	public ResultMsg registerCount(
			@ApiParam(name = "day", value = "统计日期")
			@PathVariable String day){

		Integer count = memberService.countRegisterByDay(day);

		return ResultMsg.ok().data("countRegister", count);
	}
}
