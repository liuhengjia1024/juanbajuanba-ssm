package com.liuhengjia.service;

import com.liuhengjia.entity.Video;
import com.liuhengjia.model.VideoBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface VideoService {
    @Transactional
    boolean insertOne(Video video);

    @Transactional
    boolean deleteById(Integer videoId);

    List<VideoBean> selectAll(Integer pageNum);

    Video selectById(Integer videoId);

    List<Video> selectByCourseId(Integer courseId);
}

