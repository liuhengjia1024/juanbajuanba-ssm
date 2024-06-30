package com.liuhengjia.service;

import com.liuhengjia.entity.Category;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface CategoryService {
    @Transactional
    boolean insertOne(Category category);

    @Transactional
    boolean deleteById(Integer categoryId);

    @Transactional
    boolean updateOne(Category category);

    Category selectById(Integer categoryId);

    List<Category> selectAll();

    List<Category> selectAll(Integer pageNum);
}
