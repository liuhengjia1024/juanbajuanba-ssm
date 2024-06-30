package com.liuhengjia.controller;

import com.github.pagehelper.PageInfo;
import com.liuhengjia.common.PathConstant;
import com.liuhengjia.common.Result;
import com.liuhengjia.constant.CodeConstant;
import com.liuhengjia.constant.MessageConstant;
import com.liuhengjia.entity.Category;
import com.liuhengjia.exception.BusinessException;
import com.liuhengjia.exception.SystemException;
import com.liuhengjia.service.CategoryService;
import com.liuhengjia.service.CourseService;
import com.liuhengjia.util.FileUtil;
import com.liuhengjia.util.StudentIndexHelper;
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
@RequestMapping("/category")
@RestController
public class CategoryController {
    private ServletContext servletContext;
    private CategoryService categoryService;
    private CourseService courseService;

    @PostMapping("/admin")
    public Result adminInsertOne(@RequestParam(name = "categoryName") String categoryName, @RequestParam(name = "multipartFile") MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        File saveDir = FileUtil.createSaveDir(servletContext.getRealPath(File.separator), PathConstant.CATEGORY_IMG_PATH_DIRS);
        if (!saveDir.exists() || !saveDir.mkdirs()){
            throw new BusinessException(MessageConstant.DIRS_CREATE_FAILED);
        }
        File fileSavePath = FileUtil.getFileSavePath(saveDir, originalFilename);
        try {
            multipartFile.transferTo(fileSavePath);
        } catch (IOException e) {
            throw new SystemException(MessageConstant.UN_KNOW_ERROR + "@" + e.getMessage());
        }
        if (categoryService.insertOne(Category.builder().name(categoryName).imgPath(originalFilename).createTime(new Date()).build())) {
            return Result.builder().message(MessageConstant.INSERT_CATEGORY_SUCCESS).build();
        }
        throw new BusinessException(MessageConstant.INSERT_CATEGORY_FAIL);
    }

    @DeleteMapping("/admin")
    public Result adminDeleteById(@RequestParam(name = "categoryId", defaultValue = "0") Integer categoryId) {
        Category category = selectById(categoryId);
        File fileSavePath = FileUtil.getFileSavePath(FileUtil.createSaveDir(servletContext.getRealPath(File.separator), PathConstant.CATEGORY_IMG_PATH_DIRS), category.getImgPath());
        try {
            if (categoryService.deleteById(categoryId)) {
                Files.delete(fileSavePath.getAbsoluteFile().toPath());
                return Result.builder().message(MessageConstant.DELETE_CATEGORY_SUCCESS).build();
            }
        } catch (Exception e) {
            throw new SystemException(MessageConstant.DELETE_CATEGORY_FAIL_COURSE + "@" + e.getMessage());
        }
        throw new BusinessException(MessageConstant.DELETE_CATEGORY_FAIL);
    }

    @PutMapping("/admin")
    public Result adminUpdateOne(@RequestParam(name = "categoryId") Integer categoryId, 
                                 @RequestParam(name = "updateName") String categoryName, 
                                 @RequestParam(name = "multipartFile") MultipartFile multipartFile) {
        File fileSavePath = FileUtil.getFileSavePath(FileUtil.createSaveDir(servletContext.getRealPath(File.separator), PathConstant.CATEGORY_IMG_PATH_DIRS), selectById(categoryId).getImgPath());
        try {
            Files.delete(fileSavePath.getAbsoluteFile().toPath());
        } catch (IOException e) {
            throw new SystemException(MessageConstant.UN_KNOW_ERROR + "@" + e.getMessage());
        }
        String originalFilename = multipartFile.getOriginalFilename();
        File fileUpdatePath = FileUtil.getFileSavePath(FileUtil.createSaveDir(servletContext.getRealPath(File.separator), PathConstant.CATEGORY_IMG_PATH_DIRS), originalFilename);
        try {
            multipartFile.transferTo(fileUpdatePath);
        } catch (IOException e) {
            throw new SystemException(MessageConstant.UN_KNOW_ERROR + "@" + e.getMessage());
        }
        if (categoryService.updateOne(Category.builder().id(categoryId).name(categoryName).imgPath(originalFilename).build())) {
            return Result.builder().message(MessageConstant.UPDATE_CATEGORY_SUCCESS).build();
        }
        throw new BusinessException(MessageConstant.UPDATE_CATEGORY_FAIL);
    }

    @GetMapping("/admin-all")
    public Result adminSelectAll(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
        List<Category> categoryList = categoryService.selectAll(pageNum);
        categoryList.forEach(e -> e.setImgPath(PathConstant.CATEGORY_IMG_DIR_PATH + e.getImgPath()));
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        return Result.builder().data(pageInfo).build();
    }

    @GetMapping("/admin")
    public Result adminSelectCategoryById(@RequestParam(name = "categoryId", defaultValue = "0") Integer categoryId) {
        Category category = categoryService.selectById(categoryId);
        category.setImgPath(PathConstant.CATEGORY_IMG_DIR_PATH + category.getImgPath());
        return Result.builder().data(category).build();
    }

    @GetMapping("/index")
    public Result index() {
        return Result.builder()
                .code(CodeConstant.SUCCESS)
                .data((new StudentIndexHelper()).getStudentIndexBeanList(categoryService, courseService))
                .build();
    }

    private Category selectById(Integer categoryId) {
        return categoryService.selectById(categoryId);
    }
}
