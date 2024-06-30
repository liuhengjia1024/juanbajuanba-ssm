package com.liuhengjia.controller;

import com.liuhengjia.common.PathConstant;
import com.liuhengjia.common.Result;
import com.liuhengjia.constant.CodeConstant;
import com.liuhengjia.constant.MessageConstant;
import com.liuhengjia.constant.Power;
import com.liuhengjia.entity.Administrator;
import com.liuhengjia.entity.Material;
import com.liuhengjia.exception.BusinessException;
import com.liuhengjia.exception.SystemException;
import com.liuhengjia.model.AdministratorDashboardBean;
import com.liuhengjia.model.UserBean;
import com.liuhengjia.service.*;
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
import java.util.Date;

@AllArgsConstructor
@RequestMapping("/administrator")
@RestController
public class AdministratorController {
    private ServletContext servletContext;
    private AdministratorService administratorService;
    private CourseService courseService;
    private InstructorService instructorService;
    private StudentService studentService;
    private MaterialService materialService;

    @GetMapping("/dashboard")
    public Result dashboard() {
        AdministratorDashboardBean administratorDashboardBean = AdministratorDashboardBean.builder()
                .courseNum(courseService.selectCount())
                .instructorNum(instructorService.selectCount())
                .studentNum(studentService.selectCount())
                .downloadNum(materialService.selectAll().stream().mapToInt(Material::getDownloadNumber).sum())
                .build();
        return Result.builder().data(administratorDashboardBean).build();
    }

    @PostMapping("/sign-up")
    public Result signUp(Administrator administrator,
                         @RequestParam(name = "checkCode", defaultValue = "0000") String checkCode,
                         @RequestParam(name = "multipartFile") MultipartFile multipartFile,
                         HttpServletRequest request) {
        HttpSession session = request.getSession();
        String checkCodeServer = (String) session.getAttribute("CHECK_CODE_SERVER");
        session.removeAttribute("CHECK_CODE_SERVER");
        if (checkCodeServer != null && checkCodeServer.equalsIgnoreCase(checkCode)) {
            boolean flag;
            String originalFilename = multipartFile.getOriginalFilename();
            File saveDir = FileUtil.createSaveDir(servletContext.getRealPath(File.separator), PathConstant.ADMINISTRATOR_AVATAR_PATH_DIRS);
            // 确认目录是否存在
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
                    throw new SystemException(e.getMessage() + '@' + MessageConstant.AVATAR_TRANSFER_FAILED);
                }
                administrator.setAvatarPath(originalFilename);
                administrator.setPassword((new Encodes(administrator.getPassword())).getEncodePassword());
                administrator.setJoinDate(new Date());
                if (administratorService.insertSignUp(administrator)) {
                    return Result.builder()
                            .code(CodeConstant.SUCCESS)
                            .data(administrator)
                            .message(MessageConstant.SIGN_UP_SUCCESS)
                            .build();
                }
                // 注册失败
                throw new BusinessException(MessageConstant.SIGN_UP_FAIL);
            }
            // 目录创建失败
            throw new SystemException(MessageConstant.DIRS_CREATE_FAILED);
        } else {
            // 验证码错误
            throw new BusinessException(MessageConstant.SIGN_UP_CHECK_ERROR);
        }
    }

    @PutMapping("/status-code")
    public Result statusCode(@RequestParam(name = "verificationCode", defaultValue = "") String verificationCode) {
        boolean flag = administratorService.updateStatusCode(verificationCode);
        if (flag) {
            return Result.builder().message(MessageConstant.SIGN_ACTIVE_SUCCESS).build();
        }
        throw new BusinessException(MessageConstant.SIGN_ACTIVE_FAIL_ADMINISTRATOR);
    }

    @PutMapping("/password")
    public Result password(@RequestParam(name = "verificationCode", defaultValue = "") String verificationCode, @RequestParam(name = "newPassword", defaultValue = "") String newPassword) {
        if (administratorService.updatePassword(verificationCode, (new Encodes(newPassword)).getEncodePassword())) {
            return Result.builder()
                    .code(CodeConstant.SUCCESS)
                    .message(MessageConstant.UPDATE_PWD_SUCCESS)
                    .build();
        }
        throw new BusinessException(MessageConstant.UPDATE_ADMINISTRATOR_PWD_FAIL);
    }

    @PostMapping("/sign-in")
    public Result signIn(Administrator administratorSignIn, HttpServletRequest request) {
        administratorSignIn.setPassword((new Encodes(administratorSignIn.getPassword())).getEncodePassword());
        UserBean userBean = administratorService.selectSignIn(administratorSignIn);
        if (userBean.getNickname() != null) {
            if (userBean.getStatusCode() != null && "Y".equals(userBean.getStatusCode())) {
                request.removeAttribute(Power.ADMINISTRATOR.getKey());
                request.getSession().setAttribute(Power.ADMINISTRATOR.getKey(), userBean);
                return Result.builder().message(MessageConstant.SIGN_IN_SUCCESS).build();
            } else {
                return Result.builder().code(CodeConstant.ERROR).message(MessageConstant.SIGN_NO_ACTIVE).build();
            }
        }
        return Result.builder().code(CodeConstant.ERROR).message(MessageConstant.SIGN_IN_FAIL).build();
    }

    @DeleteMapping("/sign-out")
    public Result signOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(Power.ADMINISTRATOR.getKey());
        if (session.getAttribute(Power.ADMINISTRATOR.getKey()) == null) {
            return Result.builder().message(MessageConstant.SIGN_OUT_SUCCESS).build();
        }
        return Result.builder().code(CodeConstant.ERROR).message(MessageConstant.SIGN_OUT_FAIL).build();
    }
}
