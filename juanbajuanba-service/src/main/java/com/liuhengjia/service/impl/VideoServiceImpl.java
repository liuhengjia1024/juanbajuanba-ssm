package com.liuhengjia.service.impl;

import com.github.pagehelper.page.PageMethod;
import com.liuhengjia.entity.Video;
import com.liuhengjia.mapper.VideoMapper;
import com.liuhengjia.model.VideoBean;
import com.liuhengjia.service.VideoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("videoService")
public class VideoServiceImpl implements VideoService {
    private final VideoMapper videoMapper;

    public VideoServiceImpl(VideoMapper videoMapper) {
        this.videoMapper = videoMapper;
    }

    public boolean insertOne(Video video) {
        return videoMapper.insertOne(video) > 0;
    }

    public boolean deleteById(Integer videoId) {
        return videoMapper.deleteById(videoId) > 0;
    }

    public List<VideoBean> selectAll(Integer pageNum) {
        PageMethod.startPage(pageNum, 4);
        return videoMapper.selectAll();
    }

    public Video selectById(Integer videoId) {
        return videoMapper.selectById(videoId);
    }

    public List<Video> selectByCourseId(Integer courseId) {
        return videoMapper.selectByCourseId(courseId);
    }
}