package com.seizedays.sysuser.controller;

import com.seizedays.edu.common.vo.ResultMsg;
import com.seizedays.sysuser.entity.Sysuser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * @author helen
 * @since 2019/3/1
 */
@Api(tags = {"sysuser"})
@CrossOrigin //跨域
@RestController
@RequestMapping("admin/sysuser")
public class SysUserController {

	@PostMapping("login")
	@ApiOperation(value = "用户登录")
	public ResultMsg login(
			@ApiParam(name = "sysuser", value = "系统用户对象", required = true)
			@RequestBody Sysuser sysuser){

		return ResultMsg.ok().data("token", "admin");
	}

	@GetMapping("info")
	@ApiOperation(value = "获取用户信息")
	public ResultMsg info(
			@ApiParam(name = "token", value = "令牌", required = true)
			@RequestParam String token){
		return ResultMsg.ok()
				.data("roles", "admin")
				.data("name", "admin")
				.data("avatar","https://seizedays-file.oss-cn-beijing.aliyuncs.com/avatar/head.jpg");
	}

	@GetMapping("/test")
	@ApiOperation(value = "测试Nginx")
	public ResultMsg test(){
		return ResultMsg.ok();
	}

	@PostMapping("logout")
	@ApiOperation(value = "用户登出")
	public ResultMsg logout(){
		return ResultMsg.ok();
	}
}


