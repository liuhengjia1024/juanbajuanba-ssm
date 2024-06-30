package com.liuhengjia.controller;

import com.github.pagehelper.PageInfo;
import com.liuhengjia.common.PathConstant;
import com.liuhengjia.common.Result;
import com.liuhengjia.constant.MessageConstant;
import com.liuhengjia.entity.Instructor;
import com.liuhengjia.exception.BusinessException;
import com.liuhengjia.exception.SystemException;
import com.liuhengjia.service.InstructorService;
import com.liuhengjia.util.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/instructor")
@RestController
public class InstructorController {
    private ServletContext servletContext;
    private InstructorService instructorService;

    @PostMapping("/admin-one")
    public Result insertOne(Instructor instructor, @RequestParam(name = "multipartFile") MultipartFile multipartFile) {
        boolean flag;
        String originalFilename = multipartFile.getOriginalFilename();
        File saveDir = FileUtil.createSaveDir(servletContext.getRealPath(File.separator), PathConstant.INSTRUCTOR_AVATAR_PATH_DIRS);
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
                throw new SystemException(MessageConstant.AVATAR_TRANSFER_FAILED + "@" + e.getMessage());
            }
            instructor.setAvatarPath(originalFilename);
            instructor.setJoinDate(new Date());
            if (instructorService.insertOne(instructor)) {
                return Result.builder().message(MessageConstant.INSERT_INSTRUCTOR_SUCCESS).build();
            }
        }
        throw new BusinessException(MessageConstant.INSERT_INSTRUCTOR_FAIL);
    }

    @DeleteMapping("/admin-byId")
    public Result adminDeleteById(@RequestParam(name = "instructorId", defaultValue = "-1") Integer instructorId) {
        Instructor instructor = getInstructor(instructorId);
        String avatarPath = instructor.getAvatarPath();
        File fileSavePath = FileUtil.getFileSavePath(FileUtil.createSaveDir(servletContext.getRealPath(File.separator), PathConstant.INSTRUCTOR_AVATAR_PATH_DIRS), avatarPath);

        try {
            if (instructorService.deleteById(instructorId)) {
                Files.delete(fileSavePath.getAbsoluteFile().toPath());
                return Result.builder().message(MessageConstant.DELETE_INSTRUCTOR_SUCCESS).build();
            }
        } catch (Exception e) {
            throw new SystemException(MessageConstant.DELETE_INSTRUCTOR_FAIL_COURSE + "@" + e.getMessage());
        }
        throw new BusinessException(MessageConstant.DELETE_INSTRUCTOR_FAIL);
    }

    @PutMapping("/admin-one")
    public Result adminUpdateOne(Instructor instructor, @RequestParam(name = "instructorId", defaultValue = "-1") Integer instructorId,
                                 @RequestParam(name = "multipartFile") MultipartFile multipartFile) {
        File fileSavePath = FileUtil.getFileSavePath(FileUtil.createSaveDir(servletContext.getRealPath(File.separator), PathConstant.INSTRUCTOR_AVATAR_PATH_DIRS), getInstructor(instructorId).getAvatarPath());
        try {
            Files.delete(fileSavePath.getAbsoluteFile().toPath());
        } catch (IOException e) {
            throw new SystemException(MessageConstant.UN_KNOW_ERROR + "@" + e.getMessage());
        }
        instructor.setId(instructorId);
        String originalFilename = multipartFile.getOriginalFilename();
        File fileUpdatePath = FileUtil.getFileSavePath(FileUtil.createSaveDir(servletContext.getRealPath(File.separator), PathConstant.INSTRUCTOR_AVATAR_PATH_DIRS), originalFilename);
        try {
            multipartFile.transferTo(fileUpdatePath);
        } catch (IOException e) {
            throw new SystemException(MessageConstant.UN_KNOW_ERROR + "@" + e.getMessage());
        }
        instructor.setAvatarPath(originalFilename);
        if (instructorService.updateOne(instructor)) {
            return Result.builder().message(MessageConstant.UPDATE_INSTRUCTOR_SUCCESS).build();
        }
        throw new BusinessException(MessageConstant.UPDATE_INSTRUCTOR_FAIL);
    }

    @GetMapping({"/byId"})
    public Result selectInstructorById(@RequestParam(name = "instructorId", defaultValue = "0") Integer instructorId) {
        Instructor instructor = instructorService.selectById(instructorId);
        instructor.setAvatarPath(PathConstant.INSTRUCTOR_AVATAR_DIR_PATH + instructor.getAvatarPath());
        return Result.builder().data(instructor).build();
    }

    @GetMapping({"/all"})
    public Result selectAll(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
        List<Instructor> instructorList = instructorService.selectAll(pageNum);
        instructorList.forEach(e -> e.setAvatarPath(PathConstant.INSTRUCTOR_AVATAR_DIR_PATH + e.getAvatarPath()));
        PageInfo<Instructor> pageInfo = new PageInfo<>(instructorList);
        return Result.builder().data(pageInfo).build();
    }

    public Instructor getInstructor(Integer instructorId) {
        return instructorService.selectById(instructorId);
    }
}
