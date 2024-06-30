package com.liuhengjia.controller;

import com.github.pagehelper.PageInfo;
import com.liuhengjia.common.PathConstant;
import com.liuhengjia.common.Result;
import com.liuhengjia.constant.MessageConstant;
import com.liuhengjia.constant.Power;
import com.liuhengjia.entity.Student;
import com.liuhengjia.exception.BusinessException;
import com.liuhengjia.exception.SystemException;
import com.liuhengjia.model.UserBean;
import com.liuhengjia.service.StudentService;
import com.liuhengjia.util.FileUtil;
import com.liuhengjia.util.encode.Encodes;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/student")
@RestController
public class StudentController {
    private ServletContext servletContext;
    private StudentService studentService;

    @PutMapping("/admin-student-status-code")
    public Result adminUpdateStudentStatusCode(@RequestParam(name = "studentId", defaultValue = "-1") Integer studentId) {
        if (studentId != -1 && studentService.updateStatusCodeByStudentId(studentId)) {
            return Result.builder().message(MessageConstant.ACTIVE_STUDENT_SUCCESS).build();
        }
        throw new BusinessException(MessageConstant.ACTIVE_STUDENT_FAIL);
    }

    @PutMapping("/admin-student-verification-code")
    public Result adminUpdateStudentVerificationCode(@RequestParam(name = "studentId", defaultValue = "-1") Integer studentId) {
        if (studentId != -1 && studentService.updateVerificationCode(studentId)) {
            return Result.builder().message(MessageConstant.UPDATE_VERIFICATION_CODE_SUCCESS).build();
        }
        throw new BusinessException(MessageConstant.UPDATE_VERIFICATION_CODE_FAIL);
    }

    @GetMapping("/admin-all")
    public Result adminSelectAll(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
        List<Student> studentList = getStudentList(pageNum);
        PageInfo<Student> pageInfo = new PageInfo<>(studentList);
        return Result.builder().data(pageInfo).build();
    }

    @PostMapping("/sign-up")
    public Result insertSignUp(Student student, HttpServletRequest request,
                               @RequestParam(name = "checkCode", defaultValue = "0000") String checkCode,
                               @RequestParam(name = "multipartFile") MultipartFile multipartFile) {
        HttpSession session = request.getSession();
        String checkCodeServer = (String) session.getAttribute("CHECK_CODE_SERVER");
        session.removeAttribute("CHECK_CODE_SERVER");
        if (checkCodeServer == null || !checkCodeServer.equalsIgnoreCase(checkCode)) {
            throw new BusinessException(MessageConstant.SIGN_UP_CHECK_ERROR);
        }
        String originalFilename = multipartFile.getOriginalFilename();
        File saveDir = FileUtil.createSaveDir(servletContext.getRealPath(File.separator), PathConstant.STUDENT_AVATAR_PATH_DIRS);
        if (!saveDir.exists() && !saveDir.mkdirs()) {
            throw new BusinessException(MessageConstant.DIRS_CREATE_FAILED);
        }
        File fileSavePath = FileUtil.getFileSavePath(saveDir, originalFilename);
        try {
            multipartFile.transferTo(fileSavePath);
        } catch (IOException e) {
            throw new SystemException(MessageConstant.UN_KNOW_ERROR + "@" + e.getMessage());
        }
        student.setAvatarPath(originalFilename);
        student.setPassword((new Encodes(student.getPassword())).getEncodePassword());
        student.setJoinDate(new Date());
        if (studentService.insertSignUp(student)) {
            return Result.builder().message(MessageConstant.SIGN_UP_SUCCESS).build();
        }
        throw new BusinessException(MessageConstant.SIGN_UP_FAIL);
    }

    @PutMapping("/one")
    public Result updateOne(HttpServletRequest request, Student student, @RequestParam(name = "multipartFile") MultipartFile multipartFile) {
        HttpSession session = request.getSession();
        UserBean nowStudent = (UserBean) session.getAttribute(Power.STUDENT.getKey());
        if (nowStudent == null) {
            throw new BusinessException(MessageConstant.NOT_SIGN_IN);
        }
        File oldFileSavePath = FileUtil.getFileSavePath(FileUtil.createSaveDir(
                        servletContext.getRealPath(File.separator), PathConstant.STUDENT_AVATAR_PATH_DIRS),
                selectById(nowStudent.getId()).getAvatarPath());
        try {
            Files.delete(oldFileSavePath.getAbsoluteFile().toPath());
        } catch (IOException e) {
            throw new SystemException(MessageConstant.UN_KNOW_ERROR + "@" + e.getMessage());
        }
        String originalFilename = multipartFile.getOriginalFilename();
        File saveDir = FileUtil.createSaveDir(
                servletContext.getRealPath(File.separator),
                PathConstant.STUDENT_AVATAR_PATH_DIRS);
        if (saveDir.exists() || saveDir.mkdirs()) {
            File fileSavePath = FileUtil.getFileSavePath(saveDir, originalFilename);
            try {
                multipartFile.transferTo(fileSavePath);
            } catch (IOException e) {
                throw new SystemException(MessageConstant.UN_KNOW_ERROR + "@" + e.getMessage());
            }
            student.setId(nowStudent.getId());
            student.setAvatarPath(originalFilename);
        } else {
            throw new BusinessException(MessageConstant.DIRS_CREATE_FAILED);
        }
        if (studentService.updateOne(student)) {
            return Result.builder().message(MessageConstant.UPDATE_STUDENT_SUCCESS).build();
        }
        throw new BusinessException(MessageConstant.UPDATE_STUDENT_FAIL);
    }

    @PutMapping("/password")
    public Result updatePassword(@RequestParam(name = "verificationCode", defaultValue = "") String verificationCode, @RequestParam(name = "newPassword", defaultValue = "") String newPassword) {
        if (studentService.updatePassword(verificationCode, (new Encodes(newPassword)).getEncodePassword())) {
            return Result.builder().message(MessageConstant.UPDATE_PWD_SUCCESS).build();
        }
        throw new BusinessException(MessageConstant.UPDATE_STUDENT_PWD_FAIL);
    }

    @PutMapping("/status-code")
    public Result updateStatusCode(@RequestParam(name = "verificationCode", defaultValue = "") String verificationCode) {
        if (studentService.updateStatusCode(verificationCode)) {
            return Result.builder().message(MessageConstant.SIGN_ACTIVE_SUCCESS).build();
        }
        throw new BusinessException(MessageConstant.SIGN_ACTIVE_FAIL);
    }

    @GetMapping("/one")
    public Result selectOne(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserBean student = (UserBean) session.getAttribute(Power.STUDENT.getKey());
        if (student == null) {
            throw new BusinessException(MessageConstant.NOT_SIGN_IN);
        }
        Student formBackStudent = studentService.selectById(student.getId());
        if (formBackStudent != null) {
            formBackStudent.setPassword("");
            return Result.builder().data(formBackStudent).build();
        }
        throw new SystemException(MessageConstant.UN_KNOW_ERROR);
    }

    @PostMapping("/sign-in")
    public Result selectSignIn(HttpServletRequest request, Student studentSignIn) {
        studentSignIn.setPassword((new Encodes(studentSignIn.getPassword())).getEncodePassword());
        UserBean userBean = studentService.selectSignIn(studentSignIn);
        if (userBean.getNickname() != null) {
            if (userBean.getStatusCode() != null && "Y".equals(userBean.getStatusCode())) {
                request.removeAttribute(Power.STUDENT.getKey());
                request.getSession().setAttribute(Power.STUDENT.getKey(), userBean);
                return Result.builder().message(MessageConstant.SIGN_IN_SUCCESS).build();
            } else {
                throw new BusinessException(MessageConstant.SIGN_NO_ACTIVE);
            }
        }
        throw new BusinessException(MessageConstant.SIGN_IN_FAIL);
    }

    @DeleteMapping("/sign-out")
    public Result signOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object student = session.getAttribute(Power.STUDENT.getKey());
        if (student != null) {
            session.removeAttribute(Power.STUDENT.getKey());
            if (session.getAttribute(Power.STUDENT.getKey()) == null) {
                return Result.builder().message(MessageConstant.SIGN_OUT_SUCCESS).build();
            }
        }
        throw new BusinessException(MessageConstant.SIGN_OUT_FAIL);
    }

    private Student selectById(Integer studentId) {
        return studentService.selectById(studentId);
    }

    private List<Student> getStudentList(Integer pageNum) {
        List<Student> studentList = studentService.selectAll(pageNum);
        for (Student student : studentList) {
            student.setAvatarPath(PathConstant.STUDENT_AVATAR_DIR_PATH + student.getAvatarPath());
            student.setPassword("");
        }
        return studentList;
    }
}
