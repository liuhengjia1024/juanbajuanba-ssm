package com.liuhengjia.controller;

import com.github.pagehelper.PageInfo;
import com.liuhengjia.common.PathConstant;
import com.liuhengjia.common.Result;
import com.liuhengjia.constant.MessageConstant;
import com.liuhengjia.constant.Power;
import com.liuhengjia.entity.Course;
import com.liuhengjia.entity.Review;
import com.liuhengjia.entity.Student;
import com.liuhengjia.entity.Subscription;
import com.liuhengjia.exception.BusinessException;
import com.liuhengjia.model.AdministratorReviewBean;
import com.liuhengjia.model.StudentReviewBean;
import com.liuhengjia.model.UserBean;
import com.liuhengjia.service.*;
import com.liuhengjia.util.StudentSubscriptionHelper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/review")
@RestController
public class ReviewController {
    SubscriptionService subscriptionService;
    ReviewService reviewService;
    StudentService studentService;
    CourseService courseService;
    CategoryService categoryService;
    InstructorService instructorService;

    @DeleteMapping("/admin-byId")
    public Result adminDeleteById(@RequestParam(name = "reviewId", defaultValue = "-1") Integer reviewId) {
        if (reviewService.deleteReviewById(reviewId)) {
            return Result.builder().message(MessageConstant.DELETE_REVIEW_SUCCESS).build();
        }
        throw new BusinessException(MessageConstant.DELETE_REVIEW_FAIL);
    }

    @GetMapping("/admin-all")
    public Result adminSelectAll(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
        List<AdministratorReviewBean> arbl = reviewService.selectAdministratorReviewBeanList(pageNum);
        initAdministratorReviewBeanList(arbl);
        PageInfo<AdministratorReviewBean> pageInfo = new PageInfo<>(arbl);
        return Result.builder().data(pageInfo).build();
    }

    @PostMapping("/one")
    public Result insertOne(HttpServletRequest request, Integer subscriptionId, Integer rate, String content) {
        Subscription subscription = subscriptionService.selectOneById(subscriptionId);
        if (subscription == null || !reviewService.insertOne(Review.builder().subscriptionId(subscriptionId).rate(rate).content(content).createTime(new Date()).build())) {
            throw new BusinessException(MessageConstant.SELECT_NO_SUBSCRIPTION);
        }
        UserBean userBean = (UserBean) request.getSession().getAttribute(Power.STUDENT.getKey());
        Integer studentId = -1;
        if (userBean != null) {
            studentId = userBean.getId();
        }
        return Result.builder().data(StudentSubscriptionHelper.getStudentCourseDetailBean(subscription.getCourseId(), studentId,
                courseService, categoryService, instructorService, subscriptionService, reviewService, studentService)).build();
    }

    @DeleteMapping("/byId")
    public Result deleteById(@RequestParam(name = "reviewId", defaultValue = "-1") Integer reviewId) {
        if (reviewService.deleteReviewById(reviewId)) {
            return Result.builder().message(MessageConstant.DELETE_REVIEW_SUCCESS).build();
        }
        throw new BusinessException(MessageConstant.DELETE_REVIEW_FAIL);
    }

    @GetMapping("/account-all")
    public Result selectAllByAccount(HttpServletRequest request, @RequestParam(name = "pageNum", defaultValue = "-1") Integer pageNum) {
        HttpSession session = request.getSession();
        UserBean student = (UserBean) session.getAttribute("student");
        if (student == null) {
            throw new BusinessException(MessageConstant.NOT_SIGN_IN);
        }
        List<StudentReviewBean> srbl = reviewService.selectStudentReviewBeanList(pageNum, student.getId());
        initStudentReviewBeanList(srbl);
        PageInfo<StudentReviewBean> pageInfo = new PageInfo<>(srbl);
        return Result.builder().data(pageInfo).build();
    }

    private void initStudentReviewBeanList(List<StudentReviewBean> studentReviewBeanList) {
        for (StudentReviewBean studentReviewBean : studentReviewBeanList) {
            Subscription subscription = subscriptionService.selectOneById(studentReviewBean.getReview().getSubscriptionId());
            Course course = courseService.selectById(subscription.getCourseId());
            course.setImgPath(PathConstant.COURSE_IMG_DIR_PATH + course.getImgPath());
            studentReviewBean.setCourse(course);
        }
    }

    private void initAdministratorReviewBeanList(List<AdministratorReviewBean> administratorReviewBeanList) {
        for (AdministratorReviewBean administratorReviewBean : administratorReviewBeanList) {
            Subscription subscription = subscriptionService.selectOneById(administratorReviewBean.getReview().getSubscriptionId());
            Student student = studentService.selectById(subscription.getStudentId());
            Course course = courseService.selectById(subscription.getCourseId());
            student.setPassword("");
            student.setAvatarPath(PathConstant.STUDENT_AVATAR_DIR_PATH + student.getAvatarPath());
            course.setImgPath(PathConstant.COURSE_IMG_DIR_PATH + course.getImgPath());
            administratorReviewBean.setStudent(student);
            administratorReviewBean.setCourse(course);
        }
    }
}
