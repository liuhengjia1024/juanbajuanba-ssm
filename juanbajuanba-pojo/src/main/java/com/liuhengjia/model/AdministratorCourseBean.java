package com.liuhengjia.model;

import com.github.pagehelper.PageInfo;
import com.liuhengjia.entity.Category;
import com.liuhengjia.entity.Instructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class AdministratorCourseBean<T> {
    private List<Category> categoryList;
    private List<Instructor> instructorList;
    private CourseBean courseBean;
    private PageInfo<T> pageInfo;
}
