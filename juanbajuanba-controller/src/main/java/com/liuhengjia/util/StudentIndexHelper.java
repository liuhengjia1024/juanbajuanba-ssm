package com.liuhengjia.util;

import com.liuhengjia.common.PathConstant;
import com.liuhengjia.entity.Category;
import com.liuhengjia.model.StudentIndexBean;
import com.liuhengjia.service.CategoryService;
import com.liuhengjia.service.CourseService;

import java.util.ArrayList;
import java.util.List;

public class StudentIndexHelper {
    public List<StudentIndexBean> getStudentIndexBeanList(CategoryService categoryService, CourseService courseService) {
        List<Category> categoryList = categoryService.selectAll();
        List<StudentIndexBean> studentIndexBeanList = new ArrayList<>();
        categoryList.forEach(e -> studentIndexBeanList.add(StudentIndexBean.builder()
                .categoryId(e.getId())
                .categoryImgPath(PathConstant.CATEGORY_IMG_DIR_PATH + e.getImgPath())
                .categoryName(e.getName())
                .courseNum(courseService.selectCountByCategoryId(e.getId()))
                .build()));
        return studentIndexBeanList;
    }
}
