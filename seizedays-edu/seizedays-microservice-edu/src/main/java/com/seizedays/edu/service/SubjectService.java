package com.seizedays.edu.service;

import com.seizedays.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.seizedays.edu.vo.SubjectNestedVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Seizedays
 * @since 2020-07-07
 */
public interface SubjectService extends IService<Subject> {
   List<String> batchImport(MultipartFile file) throws Exception;

   List<SubjectNestedVO> nestedList();
}
