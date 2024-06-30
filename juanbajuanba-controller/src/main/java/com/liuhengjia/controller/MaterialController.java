package com.liuhengjia.controller;

import com.github.pagehelper.PageInfo;
import com.liuhengjia.common.PathConstant;
import com.liuhengjia.common.Result;
import com.liuhengjia.constant.MessageConstant;
import com.liuhengjia.entity.Course;
import com.liuhengjia.entity.Material;
import com.liuhengjia.exception.BusinessException;
import com.liuhengjia.exception.SystemException;
import com.liuhengjia.model.AdministratorMaterialBean;
import com.liuhengjia.model.MaterialBean;
import com.liuhengjia.service.CourseService;
import com.liuhengjia.service.MaterialService;
import com.liuhengjia.util.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@RequestMapping("/material")
@RestController
public class MaterialController {
    private ServletContext servletContext;
    private CourseService courseService;
    private MaterialService materialService;

    @PutMapping({"/download"})
    public void downloadMaterial(HttpServletRequest request, HttpServletResponse response, String downloadPath,
                                 @RequestParam(name = "materialId", defaultValue = "-1") Integer materialId) {
        Material material = getMaterial(materialId);
        if (materialId > -1) {
            materialService.updateDownloadNumById(materialId);
        }
        if (material != null) {
            downloadPath = request.getSession().getServletContext().getRealPath(downloadPath);
            File file = new File(downloadPath);
            if (!file.exists()) {
                response.setContentType("text/html;charset=UTF-8");
                try {
                    response.getWriter().print("<html><body><script type='text/javascript'>alert('您要下载的资源已被删除！');</script></body></html>");
                    response.getWriter().close();
                } catch (IOException e) {
                    throw new SystemException(MessageConstant.UN_KNOW_ERROR + "@" + e.getMessage());
                }
                throw new BusinessException(MessageConstant.MATERIAL_HAS_DELETED);
            }
            String filename = material.getDownloadPath();
            try {
                filename = URLEncoder.encode(filename, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new SystemException(MessageConstant.UN_KNOW_ERROR + "@" + e.getMessage());
            }
            response.addHeader("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + filename);
            response.setContentType("multipart/form-data");
            try (FileInputStream in = new FileInputStream(downloadPath);
                 OutputStream out = response.getOutputStream()) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
            } catch (IOException e) {
                throw new SystemException(MessageConstant.UN_KNOW_ERROR + "@" + e.getMessage());
            }
        }
    }

    @PostMapping({"/admin"})
    public Result adminInsertOne(Material material, @RequestParam(name = "multipartFile") MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        File saveDir = FileUtil.createSaveDir(servletContext.getRealPath(File.separator), PathConstant.getMaterialImgPathDirs(material.getCourseId()));
        boolean flag;
        if (!saveDir.exists()) {
            flag = saveDir.mkdirs();
        } else {
            flag = true;
        }
        if (flag) {
            File fileSavePath = FileUtil.getFileSavePath(saveDir, originalFilename);
            multipartFile.transferTo(fileSavePath);
            material.setDownloadPath(originalFilename);
            material.setDownloadNumber(0);
            material.setCreateTime(new Date());
            if (materialService.insertOne(material)) {
                return Result.builder().message(MessageConstant.INSERT_MATERIAL_SUCCESS).build();
            }
        }
        return Result.builder().message(MessageConstant.INSERT_MATERIAL_FAIL).build();
    }

    @DeleteMapping({"/admin"})
    public Result adminDeleteById(@RequestParam(name = "materialId", defaultValue = "0") Integer materialId) {
        Material material = getMaterial(materialId);
        File fileSavePath = FileUtil.getFileSavePath(FileUtil.createSaveDir(servletContext.getRealPath(File.separator), PathConstant.getMaterialImgPathDirs(material.getCourseId())), material.getDownloadPath());
        try {
            Files.delete(fileSavePath.getAbsoluteFile().toPath());
        } catch (IOException e) {
            throw new SystemException(MessageConstant.UN_KNOW_ERROR + "@" + e.getMessage());
        }
        if (materialService.deleteById(materialId)) {
            return Result.builder().message(MessageConstant.DELETE_MATERIAL_SUCCESS).build();
        }
        throw new BusinessException(MessageConstant.DELETE_MATERIAL_FAIL);
    }

    @GetMapping({"/admin-all"})
    public Result adminSelectAll(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
        List<MaterialBean> materialBeanList = materialService.selectAll(pageNum);
        materialBeanList.forEach(this::initMaterial);
        return Result.builder().data(AdministratorMaterialBean.<MaterialBean>builder().courseList(courseService.selectAll()).pageInfo(new PageInfo<>(materialBeanList)).build()).build();
    }

    private Material getMaterial(Integer materialId) {
        return materialService.selectById(materialId);
    }

    private void initMaterial(MaterialBean materialBean) {
        Course course = courseService.selectById(materialBean.getMaterial().getCourseId());
        course.setImgPath(PathConstant.COURSE_IMG_DIR_PATH + course.getImgPath());
        materialBean.getMaterial().setDownloadPath(
                PathConstant.getMaterialImgDirPath(materialBean.getMaterial().getCourseId()) + materialBean.getMaterial().getDownloadPath()
        );
        materialBean.setCourse(course);
    }
}
