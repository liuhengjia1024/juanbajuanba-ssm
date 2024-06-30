package com.liuhengjia.service;

import com.liuhengjia.entity.Material;
import com.liuhengjia.model.MaterialBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface MaterialService {
    @Transactional
    boolean insertOne(Material material);

    @Transactional
    boolean deleteById(Integer materialId);

    @Transactional
    boolean updateDownloadNumById(Integer materialId);

    List<MaterialBean> selectAll(Integer pageNum);

    Material selectById(Integer materialId);

    List<Material> selectByCourseId(Integer courseId);

    List<Material> selectAll();
}
