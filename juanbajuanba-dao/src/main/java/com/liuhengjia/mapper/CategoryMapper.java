package com.liuhengjia.mapper;

import com.liuhengjia.entity.Category;

import java.util.List;

public interface CategoryMapper {
    Integer insertOne(Category category);

    Integer deleteById(Integer categoryId);

    Integer updateOne(Category category);

    Category selectById(Integer categoryId);

    List<Category> selectAll();
}
