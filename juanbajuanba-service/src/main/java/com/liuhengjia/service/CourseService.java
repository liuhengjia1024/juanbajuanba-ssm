package com.liuhengjia.service;

import com.liuhengjia.entity.Course;
import com.liuhengjia.model.CourseBean;
import com.liuhengjia.model.StudentCourseBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface CourseService {
    @Transactional
    boolean insertOne(Course course);

    @Transactional
    boolean deleteById(Integer courseId);

    @Transactional
    boolean updateOne(Course course);

    Course selectById(Integer courseId);

    Integer selectCountByCategoryId(Integer categoryId);

    Integer selectCount();

    List<CourseBean> selectAllAdministratorCourseBean(Integer pageNum);

    List<StudentCourseBean> selectAllStudentCourseBean(Integer pageNum);

    List<StudentCourseBean> selectByCategoryStudentCourseBean(Integer pageNum, Integer categoryId);

    List<Course> selectAll();
}
