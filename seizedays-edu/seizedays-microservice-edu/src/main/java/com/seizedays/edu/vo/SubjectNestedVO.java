package com.seizedays.edu.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SubjectNestedVO {
    private String id;
    private String title;
    private List<SubjectVO> children = new ArrayList<>();
}
