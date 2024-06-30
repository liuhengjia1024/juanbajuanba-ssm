package com.liuhengjia.util;

import com.liuhengjia.common.PathConstant;
import com.liuhengjia.entity.*;
import com.liuhengjia.model.ReviewBean;
import com.liuhengjia.model.StudentCourseDetailBean;
import com.liuhengjia.service.*;

import java.util.ArrayList;
import java.util.List;

public class StudentSubscriptionHelper {
    private StudentSubscriptionHelper() {
    }

    public static StudentCourseDetailBean getStudentCourseDetailBean(
            Integer courseId, Integer studentId,
            CourseService courseService,
            CategoryService categoryService,
            InstructorService instructorService,
            SubscriptionService subscriptionService,
            ReviewService reviewService,
            StudentService studentService) {
        StudentCourseDetailBean studentCourseDetailBean = new StudentCourseDetailBean();
        Course course = courseService.selectById(courseId);
        Category category = categoryService.selectById(course.getCategoryId());
        Instructor instructor = instructorService.selectById(course.getInstructorId());
        course.setImgPath(PathConstant.COURSE_IMG_DIR_PATH + course.getImgPath());
        category.setImgPath(PathConstant.CATEGORY_IMG_DIR_PATH + category.getImgPath());
        instructor.setAvatarPath(PathConstant.INSTRUCTOR_AVATAR_DIR_PATH + instructor.getAvatarPath());
        List<Subscription> subscriptionList = subscriptionService.selectByCourseId(course.getId());
        studentCourseDetailBean.setSubscriptionListSize(subscriptionList.size());
        List<ReviewBean> reviewBeanList = new ArrayList<>();
        subscriptionList.forEach(e -> {
            if (studentId.equals(e.getStudentId())) {
                studentCourseDetailBean.setSubscription(e);
                studentCourseDetailBean.setReview(reviewService.selectOne(e.getId()));
            }
            Student student = studentService.selectById(e.getStudentId());
            student.setAvatarPath(PathConstant.STUDENT_AVATAR_DIR_PATH + student.getAvatarPath());
            Review review = reviewService.selectOne(e.getId());
            if (review != null) {
                reviewBeanList.add(new ReviewBean(student, review));
            }
        });

        int size = reviewBeanList.size();
        int totalRate = 0;

        for (ReviewBean reviewBean : reviewBeanList) {
            Review review = reviewBean.getReview();
            totalRate = totalRate + (review == null ? 0 : review.getRate());
        }

        studentCourseDetailBean.setReviewRate(size == 0 ? null : totalRate / size);
        studentCourseDetailBean.setCourse(course);
        studentCourseDetailBean.setCategory(category);
        studentCourseDetailBean.setInstructor(instructor);
        studentCourseDetailBean.setSubscriptionList(subscriptionList);
        studentCourseDetailBean.setReviewBeanList(reviewBeanList);
        return studentCourseDetailBean;
    }
}
