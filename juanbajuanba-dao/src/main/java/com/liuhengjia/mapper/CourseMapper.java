package com.liuhengjia.mapper;

import com.liuhengjia.entity.Course;
import com.liuhengjia.model.CourseBean;
import com.liuhengjia.model.StudentCourseBean;

import java.util.List;

public interface CourseMapper {
    Integer insertOne(Course course);

    Integer deleteById(Integer courseId);

    Integer updateOne(Course course);

    Course selectById(Integer courseId);

    Integer selectCountByCategoryId(Integer categoryId);

    Integer selectCount();

    List<CourseBean> selectAllAdministratorCourseBean();

    List<StudentCourseBean> selectAllStudentCourseBean();

    List<StudentCourseBean> selectByCategoryStudentCourseBean(Integer categoryId);

    List<Course> selectAll();
}
