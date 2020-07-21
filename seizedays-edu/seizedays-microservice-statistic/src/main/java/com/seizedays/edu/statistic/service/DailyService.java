package com.seizedays.edu.statistic.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.seizedays.edu.statistic.entity.Daily;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author Helen
 * @since 2019-07-01
 */
public interface DailyService extends IService<Daily> {

	void createStatisticsByDay(String day);

	Map<String,Object> getChartData(String begin, String end, String type);
}
