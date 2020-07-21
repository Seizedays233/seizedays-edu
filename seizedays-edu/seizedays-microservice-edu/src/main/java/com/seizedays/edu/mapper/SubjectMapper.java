package com.seizedays.edu.mapper;

import com.seizedays.edu.entity.Subject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seizedays.edu.vo.SubjectVo2;

import java.util.List;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author Seizedays
 * @since 2020-07-07
 */
public interface SubjectMapper extends BaseMapper<Subject> {
    List<SubjectVo2> selectNestedListByParentId(String parentId);
}
