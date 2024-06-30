package com.liuhengjia.mapper;

import com.liuhengjia.entity.Material;
import com.liuhengjia.model.MaterialBean;

import java.util.List;

public interface MaterialMapper {
    Integer insertOne(Material material);

    Integer deleteById(Integer materialId);

    Integer updateDownloadNumById(Integer materialId);

    List<MaterialBean> selectAll();

    Material selectById(Integer materialId);

    List<Material> selectByCourseId(Integer courseId);
}
