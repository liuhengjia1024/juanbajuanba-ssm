package com.liuhengjia.service.impl;

import com.github.pagehelper.page.PageMethod;
import com.liuhengjia.entity.Course;
import com.liuhengjia.mapper.CourseMapper;
import com.liuhengjia.model.CourseBean;
import com.liuhengjia.model.StudentCourseBean;
import com.liuhengjia.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("courseService")
public class CourseServiceImpl implements CourseService {
    private final CourseMapper courseMapper;

    public CourseServiceImpl(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    public boolean insertOne(Course course) {
        return courseMapper.insertOne(course) > 0;
    }

    public boolean deleteById(Integer courseId) {
        return courseMapper.deleteById(courseId) > 0;
    }

    public boolean updateOne(Course course) {
        return courseMapper.updateOne(course) > 0;
    }

    public Course selectById(Integer courseId) {
        return courseMapper.selectById(courseId);
    }

    public Integer selectCountByCategoryId(Integer categoryId) {
        return courseMapper.selectCountByCategoryId(categoryId);
    }

    public Integer selectCount() {
        return courseMapper.selectCount();
    }

    public List<CourseBean> selectAllAdministratorCourseBean(Integer pageNum) {
        PageMethod.startPage(pageNum, 4);
        return courseMapper.selectAllAdministratorCourseBean();
    }

    public List<StudentCourseBean> selectAllStudentCourseBean(Integer pageNum) {
        PageMethod.startPage(pageNum, 4);
        return courseMapper.selectAllStudentCourseBean();
    }

    @Override
    public List<StudentCourseBean> selectByCategoryStudentCourseBean(Integer pageNum, Integer categoryId) {
        PageMethod.startPage(pageNum, 4);
        return courseMapper.selectByCategoryStudentCourseBean(categoryId);
    }

    public List<Course> selectAll() {
        return courseMapper.selectAll();
    }
}

