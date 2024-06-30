package com.liuhengjia.controller;

import com.github.pagehelper.PageInfo;
import com.liuhengjia.common.PathConstant;
import com.liuhengjia.common.Result;
import com.liuhengjia.constant.MessageConstant;
import com.liuhengjia.constant.Power;
import com.liuhengjia.entity.*;
import com.liuhengjia.exception.BusinessException;
import com.liuhengjia.model.AdministratorCourseBean;
import com.liuhengjia.model.CourseBean;
import com.liuhengjia.model.StudentCourseBean;
import com.liuhengjia.model.UserBean;
import com.liuhengjia.service.*;
import com.liuhengjia.util.FileUtil;
import com.liuhengjia.util.StudentSubscriptionHelper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/course")
@RestController
public class CourseController {
    private ServletContext servletContext;
    private CategoryService categoryService;
    private InstructorService instructorService;
    private CourseService courseService;
    private VideoService videoService;
    private MaterialService materialService;
    private SubscriptionService subscriptionService;
    private ReviewService reviewService;
    private StudentService studentService;

    @PostMapping("/admin")
    public Result adminInsertOne(Course course, @RequestParam(name = "multipartFile") MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        File saveDir = FileUtil.createSaveDir(servletContext.getRealPath(File.separator), PathConstant.COURSE_IMG_PATH_DIRS);
        boolean flag;
        if (!saveDir.exists()) {
            flag = saveDir.mkdirs();
        } else {
            flag = true;
        }
        if (flag) {
            File fileSavePath = FileUtil.getFileSavePath(saveDir, originalFilename);
            multipartFile.transferTo(fileSavePath);
            course.setImgPath(originalFilename);
            course.setCreateTime(new Date());
            if (courseService.insertOne(course)) {
                return Result.builder().message(MessageConstant.INSERT_COURSE_SUCCESS).build();
            }
        }
        throw new BusinessException(MessageConstant.INSERT_COURSE_FAIL);
    }

    @DeleteMapping("/admin")
    public Result adminDeleteById(@RequestParam(name = "courseId", defaultValue = "-1") Integer courseId) {
        File fileSavePath = FileUtil.getFileSavePath(FileUtil.createSaveDir(servletContext.getRealPath(File.separator), PathConstant.COURSE_IMG_PATH_DIRS), courseService.selectById(courseId).getImgPath());
        try {
            if (courseService.deleteById(courseId)) {
                Files.delete(fileSavePath.getAbsoluteFile().toPath());
                return Result.builder().message(MessageConstant.DELETE_COURSE_SUCCESS).build();
            }
        } catch (Exception e) {
            throw new BusinessException(MessageConstant.DELETE_COURSE_FAIL_SOR + "@" + e.getMessage());
        }
        throw new BusinessException(MessageConstant.DELETE_COURSE_FAIL);
    }

    @PutMapping("/admin")
    public Result adminUpdateOne(Course course, @RequestParam(name = "courseId", defaultValue = "-1") Integer courseId,
                                 @RequestParam(name = "multipartFile") MultipartFile multipartFile) throws IOException {
        File fileSavePath = FileUtil.getFileSavePath(FileUtil.createSaveDir(servletContext.getRealPath(File.separator), PathConstant.COURSE_IMG_PATH_DIRS), courseService.selectById(courseId).getImgPath());
        Files.delete(fileSavePath.getAbsoluteFile().toPath());
        course.setId(courseId);
        String originalFilename = multipartFile.getOriginalFilename();
        File fileUpdatePath = FileUtil.getFileSavePath(FileUtil.createSaveDir(servletContext.getRealPath(File.separator), PathConstant.COURSE_IMG_PATH_DIRS), originalFilename);
        multipartFile.transferTo(fileUpdatePath);
        course.setImgPath(originalFilename);
        if (courseService.updateOne(course)) {
            return Result.builder().message(MessageConstant.UPDATE_COURSE_SUCCESS).build();
        }
        throw new BusinessException(MessageConstant.UPDATE_COURSE_FAIL);
    }

    @GetMapping("/admin-courseById")
    public Result adminSelectCourseById(@RequestParam(name = "courseId", defaultValue = "0") Integer courseId) {
        Course course = courseService.selectById(courseId);
        CourseBean courseBean = CourseBean.builder().course(course).build();
        initAdministratorCourseBean(courseBean);
        return Result.builder().data(AdministratorCourseBean.builder()
                .categoryList(categoryService.selectAll())
                .instructorList(instructorService.selectAll())
                .courseBean(courseBean).build()).build();
    }

    @GetMapping("/admin-all")
    public Result adminSelectAll(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
        List<CourseBean> courseBeanList = courseService.selectAllAdministratorCourseBean(pageNum);
        courseBeanList.forEach(this::initAdministratorCourseBean);
        return Result.builder().data(AdministratorCourseBean.<CourseBean>builder()
                .categoryList(categoryService.selectAll())
                .instructorList(instructorService.selectAll())
                .pageInfo(new PageInfo<>(courseBeanList)).build()).build();
    }

    @GetMapping("/all")
    public Result selectAll(HttpServletRequest request, @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
        UserBean userBean = (UserBean) request.getSession().getAttribute(Power.STUDENT.getKey());
        Integer studentId = -1;
        if (userBean != null) {
            studentId = userBean.getId();
        }
        List<StudentCourseBean> studentCourseBeanList = courseService.selectAllStudentCourseBean(pageNum);
        initStudentCourseBeanList(studentCourseBeanList, studentId);
        PageInfo<StudentCourseBean> pageInfo = new PageInfo<>(studentCourseBeanList);
        return Result.builder().data(pageInfo).build();
    }

    @GetMapping("/by-category")
    public Result selectByCategory(
            HttpServletRequest request,
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "categoryId", defaultValue = "") Integer categoryId) {
        UserBean userBean = (UserBean) request.getSession().getAttribute(Power.STUDENT.getKey());
        Integer studentId = -1;
        if (userBean != null) {
            studentId = userBean.getId();
        }
        List<StudentCourseBean> studentCourseBeanList = courseService.selectByCategoryStudentCourseBean(pageNum, categoryId);
        initStudentCourseBeanList(studentCourseBeanList, studentId);
        PageInfo<StudentCourseBean> pageInfo = new PageInfo<>(studentCourseBeanList);
        return Result.builder().data(pageInfo).build();
    }

    @GetMapping("/detail")
    public Result selectDetail(HttpServletRequest request, @RequestParam(name = "courseId", defaultValue = "-1") Integer courseId) {
        UserBean userBean = (UserBean) request.getSession().getAttribute(Power.STUDENT.getKey());
        Integer studentId = -1;
        if (userBean != null) {
            studentId = userBean.getId();
        }
        return Result.builder().data(StudentSubscriptionHelper.getStudentCourseDetailBean(
                courseId, studentId,
                courseService, categoryService, instructorService, subscriptionService, reviewService, studentService
        )).build();
    }

    private void initAdministratorCourseBean(CourseBean courseBean) {
        Category category = categoryService.selectById(courseBean.getCourse().getCategoryId());
        Instructor instructor = instructorService.selectById(courseBean.getCourse().getInstructorId());
        category.setImgPath(PathConstant.CATEGORY_IMG_DIR_PATH + category.getImgPath());
        instructor.setAvatarPath(PathConstant.INSTRUCTOR_AVATAR_DIR_PATH + instructor.getAvatarPath());
        courseBean.getCourse().setImgPath(PathConstant.COURSE_IMG_DIR_PATH + courseBean.getCourse().getImgPath());
        courseBean.setCategory(category);
        courseBean.setInstructor(instructor);
    }

    private void initStudentCourseBeanList(List<StudentCourseBean> studentCourseBeanList, Integer studentId) {
        studentCourseBeanList.forEach(scb -> {
            Integer courseId = scb.getCourse().getId();
            Category category = categoryService.selectById(scb.getCourse().getCategoryId());
            List<Subscription> subscriptionList = subscriptionService.selectByCourseId(courseId);
            List<Video> videoList = videoService.selectByCourseId(courseId);
            List<Material> materialList = materialService.selectByCourseId(courseId);
            scb.getCourse().setImgPath(PathConstant.COURSE_IMG_DIR_PATH + scb.getCourse().getImgPath());
            scb.setCategoryName(category.getName());
            if (subscriptionList != null) {
                subscriptionList.forEach(s -> scb.setSubscription(studentId.equals(s.getStudentId())));
            }
            scb.setVideoNum(videoList.size());
            scb.setMaterialNum(materialList.size());
        });
    }
}
