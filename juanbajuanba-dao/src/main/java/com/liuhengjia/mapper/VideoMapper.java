package com.liuhengjia.mapper;

import com.liuhengjia.entity.Video;
import com.liuhengjia.model.VideoBean;

import java.util.List;

public interface VideoMapper {
    Integer insertOne(Video video);

    Integer deleteById(Integer videoId);

    List<VideoBean> selectAll();

    Video selectById(Integer videoId);

    List<Video> selectByCourseId(Integer courseId);
}
