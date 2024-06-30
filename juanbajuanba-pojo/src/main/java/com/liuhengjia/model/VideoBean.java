package com.liuhengjia.model;

import com.liuhengjia.entity.Course;
import com.liuhengjia.entity.Video;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class VideoBean {
    private Video video;
    private Course course;
}
