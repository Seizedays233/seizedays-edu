package com.seizedays.edu.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author helen
 * @since 2019/7/2
 */
@ApiModel(value = "章节信息")
@Data
public class VideoVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String title;
	private Boolean free;
}
