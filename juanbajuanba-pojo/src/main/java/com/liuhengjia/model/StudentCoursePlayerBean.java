package com.liuhengjia.model;

import com.liuhengjia.entity.Course;
import com.liuhengjia.entity.Material;
import com.liuhengjia.entity.Video;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class StudentCoursePlayerBean {
    private Course course;
    private List<Video> videoList;
    private List<Material> materialList;
}
