package com.seizedays.edu.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seizedays.edu.user.entity.Member;
import com.seizedays.edu.user.mapper.MemberMapper;
import com.seizedays.edu.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Helen
 * @since 2019-07-01
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {


	@Override
	public Integer countRegisterByDay(String day) {
		return baseMapper.selectRegisterCount(day);
	}
}
