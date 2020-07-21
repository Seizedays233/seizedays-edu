package com.seizedays.edu.statistic.controller;



import com.baomidou.mybatisplus.extension.api.R;
import com.seizedays.edu.common.vo.ResultMsg;
import com.seizedays.edu.statistic.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author Helen
 * @since 2019-07-01
 */
@CrossOrigin
@RestController
@RequestMapping("/admin/statistics/daily")
public class DailyController {

	@Autowired
	private DailyService dailyService;

	@GetMapping("{day}")
	public ResultMsg createStatisticsByDate(@PathVariable String day){
		dailyService.createStatisticsByDay(day);
		return ResultMsg.ok();
	}

	@GetMapping("show-chart/{begin}/{end}/{type}")
	public ResultMsg showChart(
			@PathVariable String begin,
			@PathVariable String end,
			@PathVariable String type
	){
		Map<String, Object> map = dailyService.getChartData(begin, end, type);
		return ResultMsg.ok().data(map);
	}
}

