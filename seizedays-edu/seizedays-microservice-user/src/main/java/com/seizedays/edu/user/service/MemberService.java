package com.seizedays.edu.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.seizedays.edu.user.entity.Member;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Helen
 * @since 2019-07-01
 */
public interface MemberService extends IService<Member> {
	Integer countRegisterByDay(String day);
}
