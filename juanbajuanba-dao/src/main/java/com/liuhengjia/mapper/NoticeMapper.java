package com.liuhengjia.mapper;

import com.liuhengjia.entity.Notice;
import com.liuhengjia.model.NoticeBean;

import java.util.List;

public interface NoticeMapper {
    Integer insertOne(Notice notice);

    Integer deleteById(Integer noticeId);

    List<NoticeBean> selectAll();
}
