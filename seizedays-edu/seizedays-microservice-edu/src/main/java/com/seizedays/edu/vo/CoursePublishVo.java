package com.seizedays.edu.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author helen
 * @since 2019/7/2
 */
@ApiModel(value = "课程发布信息")
@Data
public class CoursePublishVo  implements Serializable {

	private static final long serialVersionUID = 1L;

	private String title;
	private String cover;
	private Integer lessonNum;
	private String subjectLevelOne;
	private String subjectLevelTwo;
	private String teacherName;
	private String price;//只用于显示
}
