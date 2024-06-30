package com.liuhengjia.controller;

import com.github.pagehelper.PageInfo;
import com.liuhengjia.common.PathConstant;
import com.liuhengjia.common.Result;
import com.liuhengjia.constant.MessageConstant;
import com.liuhengjia.constant.Power;
import com.liuhengjia.entity.Course;
import com.liuhengjia.entity.Material;
import com.liuhengjia.entity.Subscription;
import com.liuhengjia.entity.Video;
import com.liuhengjia.exception.BusinessException;
import com.liuhengjia.exception.SystemException;
import com.liuhengjia.model.AdministratorMaterialBean;
import com.liuhengjia.model.StudentCoursePlayerBean;
import com.liuhengjia.model.UserBean;
import com.liuhengjia.model.VideoBean;
import com.liuhengjia.service.CourseService;
import com.liuhengjia.service.MaterialService;
import com.liuhengjia.service.SubscriptionService;
import com.liuhengjia.service.VideoService;
import com.liuhengjia.util.FileUtil;
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
@RequestMapping("/video")
@RestController
public class VideoController {
    private ServletContext servletContext;
    private CourseService courseService;
    private VideoService videoService;
    private SubscriptionService subscriptionService;
    private MaterialService materialService;

    @PostMapping("/admin")
    public Result adminInsertOne(Video video, @RequestParam(name = "multipartFile") MultipartFile multipartFile) {
        boolean flag;
        String originalFilename = multipartFile.getOriginalFilename();
        File saveDir = FileUtil.createSaveDir(servletContext.getRealPath(File.separator), PathConstant.getVideoImgPathDirs(video.getCourseId()));
        if (!saveDir.exists()) {
            flag = saveDir.mkdirs();
        } else {
            flag = true;
        }
        if (flag) {
            File fileSavePath = FileUtil.getFileSavePath(saveDir, originalFilename);
            try {
                multipartFile.transferTo(fileSavePath);
            } catch (IOException e) {
                throw new SystemException(MessageConstant.INSERT_VIDEO_FAIL + "@" + e.getMessage());
            }
            video.setVideoPath(originalFilename);
            video.setViewNumber(0);
            video.setCreateTime(new Date());
            if (videoService.insertOne(video)) {
                return Result.builder().message(MessageConstant.INSERT_VIDEO_SUCCESS).build();
            }
        }
        throw new BusinessException(MessageConstant.INSERT_VIDEO_FAIL);
    }

    @DeleteMapping("/admin")
    public Result adminDeleteById(@RequestParam(name = "videoId", defaultValue = "0") Integer videoId) {
        Video video = selectById(videoId);
        File fileSavePath = FileUtil.getFileSavePath(FileUtil.createSaveDir(servletContext.getRealPath(File.separator), PathConstant.getVideoImgPathDirs(video.getCourseId())), video.getVideoPath());
        try {
            Files.delete(fileSavePath.getAbsoluteFile().toPath());
        } catch (IOException e) {
            throw new SystemException(MessageConstant.DELETE_VIDEO_FAIL + "@" + e.getMessage());
        }
        if (videoService.deleteById(videoId)) {
            return Result.builder().message(MessageConstant.DELETE_VIDEO_SUCCESS).build();
        }
        throw new BusinessException(MessageConstant.DELETE_VIDEO_FAIL);
    }

    @GetMapping({"/admin-all"})
    public Result adminSelectAll(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
        List<VideoBean> videoBeanList = videoService.selectAll(pageNum);
        videoBeanList.forEach(this::initVideoBean);
        return Result.builder().data(AdministratorMaterialBean.<VideoBean>builder().courseList(courseService.selectAll()).pageInfo(new PageInfo<>(videoBeanList)).build()).build();
    }

    @GetMapping({"/video-player"})
    public Result selectVideoPlayer(HttpServletRequest request, @RequestParam(name = "courseId", defaultValue = "-1") Integer courseId) {
        UserBean userBean = (UserBean) request.getSession().getAttribute(Power.STUDENT.getKey());
        Integer studentId = -1;
        if (userBean == null) {
            throw new BusinessException(MessageConstant.INSERT_SUBSCRIPTION_FAIL_NI);
        }
        studentId = userBean.getId();
        Course course = courseService.selectById(courseId);
        Subscription subscription = subscriptionService.selectOne(courseId, studentId);
        List<Video> videoList = videoService.selectByCourseId(courseId);
        videoList.forEach(e -> e.setVideoPath(PathConstant.getVideoImgDirPath(course.getId()) + e.getVideoPath()));
        List<Material> materialList = materialService.selectByCourseId(courseId);
        materialList.forEach(e -> e.setDownloadPath(PathConstant.getMaterialImgDirPath(course.getId()) + e.getDownloadPath()));
        if (subscription == null) {
            throw new BusinessException(MessageConstant.SELECT_NO_SUBSCRIPTION);
        }
        return Result.builder().data(StudentCoursePlayerBean.builder().course(course).videoList(videoList).materialList(materialList)).build();
        /*return Result.builder().data(StudentSubscriptionHelper.getStudentCourseDetailBean(
                courseId, studentId,
                courseService, categoryService, instructorService, subscriptionService, reviewService, studentService)).build();*/
    }

    private void initVideoBean(VideoBean videoBean) {
        Course course = courseService.selectById(videoBean.getVideo().getCourseId());
        course.setImgPath(PathConstant.COURSE_IMG_DIR_PATH + course.getImgPath());
        videoBean.getVideo().setVideoPath(PathConstant.getVideoImgDirPath(videoBean.getVideo().getCourseId()) + videoBean.getVideo().getVideoPath());
        videoBean.setCourse(course);
    }

    private Video selectById(Integer videoId) {
        return videoService.selectById(videoId);
    }
}
