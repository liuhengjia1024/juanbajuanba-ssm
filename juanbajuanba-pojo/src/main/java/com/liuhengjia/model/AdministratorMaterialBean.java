package com.liuhengjia.model;

import com.github.pagehelper.PageInfo;
import com.liuhengjia.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class AdministratorMaterialBean<T> {
    private List<Course> courseList;
    private PageInfo<T> pageInfo;
}
