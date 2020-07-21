package com.seizedays.edu.statistic.client;


import com.baomidou.mybatisplus.extension.api.R;
import com.seizedays.edu.common.vo.ResultMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author helen
 * @since 2019/7/1
 */
@Component
@FeignClient("seizedays-user")
public interface UcenterClinet {

	@GetMapping(value = "/admin/ucenter/member/count-register/{day}")
	ResultMsg registerCount(
            @PathVariable("day") String day);

}
