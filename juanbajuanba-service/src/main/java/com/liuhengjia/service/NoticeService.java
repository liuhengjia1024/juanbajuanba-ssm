package com.liuhengjia.service;

import com.liuhengjia.entity.Notice;
import com.liuhengjia.model.NoticeBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface NoticeService {
    @Transactional
    boolean insertOne(Notice notice);

    @Transactional
    boolean deleteById(Integer noticeId);

    List<NoticeBean> selectAll(Integer pageNum);
}
