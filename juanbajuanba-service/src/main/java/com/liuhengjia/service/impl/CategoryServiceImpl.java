package com.liuhengjia.service.impl;

import com.github.pagehelper.page.PageMethod;
import com.liuhengjia.entity.Category;
import com.liuhengjia.mapper.CategoryMapper;
import com.liuhengjia.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    public boolean insertOne(Category category) {
        Integer count = categoryMapper.insertOne(category);
        return count > 0;
    }

    public boolean deleteById(Integer categoryId) {
        Integer count = categoryMapper.deleteById(categoryId);
        return count > 0;
    }

    public boolean updateOne(Category category) {
        Integer count = categoryMapper.updateOne(category);
        return count > 0;
    }

    public Category selectById(Integer categoryId) {
        return categoryMapper.selectById(categoryId);
    }

    public List<Category> selectAll() {
        return categoryMapper.selectAll();
    }

    public List<Category> selectAll(Integer pageNum) {
        PageMethod.startPage(pageNum, 4);
        return categoryMapper.selectAll();
    }
}
