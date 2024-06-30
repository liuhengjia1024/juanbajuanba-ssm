package com.liuhengjia.controller;

import com.github.pagehelper.PageInfo;
import com.liuhengjia.common.PathConstant;
import com.liuhengjia.common.Result;
import com.liuhengjia.constant.MessageConstant;
import com.liuhengjia.constant.Power;
import com.liuhengjia.entity.Course;
import com.liuhengjia.entity.Student;
import com.liuhengjia.entity.Subscription;
import com.liuhengjia.exception.BusinessException;
import com.liuhengjia.model.AdministratorSubscriptionBean;
import com.liuhengjia.model.SubscriptionBean;
import com.liuhengjia.model.UserBean;
import com.liuhengjia.service.CourseService;
import com.liuhengjia.service.StudentService;
import com.liuhengjia.service.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RequestMapping({"/subscription"})
@RestController
public class SubscriptionController {
    private SubscriptionService subscriptionService;
    private StudentService studentService;
    private CourseService courseService;

    @DeleteMapping("/admin")
    public Result adminDeleteById(@RequestParam(name = "subscriptionId", defaultValue = "-1") Integer subscriptionId) {
        try {
            if (subscriptionService.deleteSubscriptionById(subscriptionId)) {
                return Result.builder().message(MessageConstant.DELETE_SUBSCRIPTION_SUCCESS).build();
            }
        } catch (Exception e) {
            throw new BusinessException(MessageConstant.DELETE_SUBSCRIPTION_FAIL_REVIEW + "@" + e.getMessage());
        }
        throw new BusinessException(MessageConstant.DELETE_SUBSCRIPTION_FAIL);
    }

    @GetMapping("/admin-all")
    public Result adminSelectAll(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
        List<AdministratorSubscriptionBean> asbl = subscriptionService.selectAdministratorSubscriptionBeanList(pageNum);
        asbl.forEach(this::initAdministratorSubscriptionBean);
        PageInfo<AdministratorSubscriptionBean> pageInfo = new PageInfo<>(asbl);
        return Result.builder().data(pageInfo).build();
    }

    @GetMapping("/byAccount")
    public Result selectByAccount(HttpServletRequest request, @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
        HttpSession session = request.getSession();
        UserBean student = (UserBean) session.getAttribute(Power.STUDENT.getKey());
        if (student == null) {
            throw new BusinessException(MessageConstant.NOT_SIGN_IN);
        }
        List<SubscriptionBean> sbl = subscriptionService.selectByAccount(pageNum, student.getId());
        sbl.forEach(this::initSubscriptionBean);
        PageInfo<SubscriptionBean> pageInfo = new PageInfo<>(sbl);
        return Result.builder().data(pageInfo).build();
    }

    @PostMapping({"/one"})
    public Result insertOne(HttpServletRequest request, @RequestParam(name = "courseId", defaultValue = "-1") Integer courseId) {
        UserBean userBean = (UserBean) request.getSession().getAttribute(Power.STUDENT.getKey());
        Integer studentId = -1;
        if (userBean == null) {
            throw new BusinessException(MessageConstant.INSERT_SUBSCRIPTION_FAIL_NI);
        }
        studentId = userBean.getId();
        if (studentId != -1 && courseId != -1 &&
                subscriptionService.insertOne(Subscription.builder().studentId(studentId).courseId(courseId).isReview("N").subscriptionTime(new Date()).build())) {
            return Result.builder().message(MessageConstant.INSERT_SUBSCRIPTION_SUCCESS).build();
        }
        throw new BusinessException(MessageConstant.INSERT_SUBSCRIPTION_FAIL);
        /*result.setData(StudentSubscriptionHelper.getStudentCourseDetailBean(courseId, studentId, courseService, categoryService, instructorService, subscriptionService, reviewService, studentService));
        return new ModelAndView(ViewName.STUDENT_COURSE_DETAIL, MessageConstant.RESULT, result);*/
    }

    @DeleteMapping({"/byId"})
    public Result deleteById(@RequestParam(name = "subscriptionId", defaultValue = "-1") Integer subscriptionId) {
        try {
            if (subscriptionService.deleteSubscriptionById(subscriptionId)) {
                return Result.builder().message(MessageConstant.DELETE_SUBSCRIPTION_SUCCESS).build();
            }
        } catch (Exception e) {
            throw new BusinessException(MessageConstant.DELETE_SUBSCRIPTION_FAIL_REVIEW + "@" + e.getMessage());
        }
        throw new BusinessException(MessageConstant.DELETE_SUBSCRIPTION_FAIL);
        /*HttpSession session = request.getSession();
        UserBean student = (UserBean) session.getAttribute(Power.STUDENT.getKey());
        if (student != null) {
            List<SubscriptionBean> sbl = subscriptionService.selectByAccount(1, student.getId());
            sbl.forEach(this::initSubscriptionBean);
            PageInfo<SubscriptionBean> pageInfo = new PageInfo<>(sbl);
            result.setData(pageInfo);
        }
        return new ModelAndView(ViewName.STUDENT_ACCOUNT, MessageConstant.RESULT, result);*/
    }

    private void initAdministratorSubscriptionBean(AdministratorSubscriptionBean administratorSubscriptionBean) {
        Student student = studentService.selectById(administratorSubscriptionBean.getSubscription().getStudentId());
        Course course = courseService.selectById(administratorSubscriptionBean.getSubscription().getCourseId());
        student.setPassword("");
        student.setAvatarPath(PathConstant.STUDENT_AVATAR_DIR_PATH + student.getAvatarPath());
        course.setImgPath(PathConstant.COURSE_IMG_DIR_PATH + course.getImgPath());
        administratorSubscriptionBean.setStudent(student);
        administratorSubscriptionBean.setCourse(course);
    }

    private void initSubscriptionBean(SubscriptionBean subscriptionBean) {
        Course course = courseService.selectById(subscriptionBean.getSubscription().getCourseId());
        course.setImgPath(PathConstant.COURSE_IMG_DIR_PATH + course.getImgPath());
        subscriptionBean.setCourse(course);
    }
}
