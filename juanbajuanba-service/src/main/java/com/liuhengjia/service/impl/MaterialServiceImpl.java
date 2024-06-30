package com.liuhengjia.service.impl;

import com.github.pagehelper.page.PageMethod;
import com.liuhengjia.entity.Material;
import com.liuhengjia.mapper.MaterialMapper;
import com.liuhengjia.model.MaterialBean;
import com.liuhengjia.service.MaterialService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("materialService")
public class MaterialServiceImpl implements MaterialService {
    private final MaterialMapper materialMapper;

    public MaterialServiceImpl(MaterialMapper materialMapper) {
        this.materialMapper = materialMapper;
    }

    public boolean insertOne(Material material) {
        return materialMapper.insertOne(material) > 0;
    }

    public boolean deleteById(Integer materialId) {
        return materialMapper.deleteById(materialId) > 0;
    }

    public boolean updateDownloadNumById(Integer materialId) {
        return materialMapper.updateDownloadNumById(materialId) > 0;
    }

    public List<MaterialBean> selectAll(Integer pageNum) {
        PageMethod.startPage(pageNum, 4);
        return materialMapper.selectAll();
    }

    public Material selectById(Integer materialId) {
        return materialMapper.selectById(materialId);
    }

    public List<Material> selectByCourseId(Integer courseId) {
        return materialMapper.selectByCourseId(courseId);
    }

    public List<Material> selectAll() {
        List<MaterialBean> materialBeanList = materialMapper.selectAll();
        List<Material> materialList = new ArrayList<>();
        for (MaterialBean materialBean : materialBeanList) {
            materialList.add(materialBean.getMaterial());
        }
        return materialList;
    }
}
