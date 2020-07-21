package com.seizedays.edu.statistic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seizedays.edu.common.vo.ResultMsg;
import com.seizedays.edu.statistic.client.UcenterClinet;
import com.seizedays.edu.statistic.entity.Daily;
import com.seizedays.edu.statistic.mapper.DailyMapper;
import com.seizedays.edu.statistic.service.DailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author Helen
 * @since 2019-07-01
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

	@Autowired
	private UcenterClinet ucenterClinet;

	@Autowired
	private BaseMapper baseMapper;

	@Override
	public void createStatisticsByDay(String day) {

		//获取一条统计信息（远程调用）
		ResultMsg r = ucenterClinet.registerCount(day);
		Integer registerNum = (Integer)r.getData().get("countRegister");
		Integer loginNum = RandomUtils.nextInt(100, 200);
		Integer videoViewNum = RandomUtils.nextInt(100, 200);
		Integer courseNum = RandomUtils.nextInt(100, 200);
		//
		// 并将统计信息放入数据库
		Daily daily = new Daily();
		daily.setRegisterNum(registerNum);
		daily.setLoginNum(loginNum);
		daily.setVideoViewNum(videoViewNum);
		daily.setCourseNum(courseNum);
		daily.setDateCalculated(day);

		baseMapper.insert(daily);
	}

	/**
	 * //map   dataList [10, 20]   dateList ["2019-07-01", "2019-07-02"]
	 * @param begin
	 * @param end
	 * @param type
	 * @return
	 */
	@Override
	public Map<String, Object> getChartData(String begin, String end, String type) {


		QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();
		queryWrapper.select(type, "date_calculated");
		queryWrapper.between("date_calculated", begin, end);

		List<Daily> dailyList = baseMapper.selectList(queryWrapper);

		HashMap<String, Object> map = new HashMap<>();
		ArrayList<Integer> dataList = new ArrayList<>();
		ArrayList<String> dateList = new ArrayList<>();
		map.put("dataList", dataList);
		map.put("dateList", dateList);


		for (Daily daily : dailyList) {

			//遍历查询数据，将每一条记录的统计的数值取出放入数据列表
			switch (type){
				//`register_num``login_num``video_view_num``course_num`
				case "register_num":
					Integer registerNum = daily.getRegisterNum();
					dataList.add(registerNum);
					break;
				case "login_num":
					Integer loginNum = daily.getLoginNum();
					dataList.add(loginNum);
					break;
				case "video_view_num":
					Integer videoViewNum = daily.getVideoViewNum();
					dataList.add(videoViewNum);
					break;
				case "course_num":
					Integer courseNum = daily.getCourseNum();
					dataList.add(courseNum);
					break;
				default:
					break;

			}

			//遍历查询数据，将每一条记录的日期取出放入日期列表
			dateList.add(daily.getDateCalculated());
		}

		return map;
	}
}
