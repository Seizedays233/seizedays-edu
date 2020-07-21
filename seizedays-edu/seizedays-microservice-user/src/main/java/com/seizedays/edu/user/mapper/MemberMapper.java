package com.seizedays.edu.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seizedays.edu.user.entity.Member;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author Helen
 * @since 2019-07-01
 */
@Repository
public interface MemberMapper extends BaseMapper<Member> {

	Integer selectRegisterCount(String day);
}
