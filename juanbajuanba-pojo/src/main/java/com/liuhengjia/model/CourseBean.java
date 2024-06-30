package com.liuhengjia.model;

import com.liuhengjia.entity.Category;
import com.liuhengjia.entity.Course;
import com.liuhengjia.entity.Instructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class CourseBean {
    private Course course;
    private Instructor instructor;
    private Category category;
}