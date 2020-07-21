package com.seizedays.edu.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author helen
 * @since 2019/6/29
 */
@Data
public class SubjectVo2 {

	private String id;
	private String title;
	private List<SubjectVo2> children = new ArrayList<>();
}
