package com.liuhengjia.service.impl;

import com.github.pagehelper.page.PageMethod;
import com.liuhengjia.entity.Notice;
import com.liuhengjia.mapper.NoticeMapper;
import com.liuhengjia.model.NoticeBean;
import com.liuhengjia.service.NoticeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
    private final NoticeMapper noticeMapper;

    public NoticeServiceImpl(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    public boolean insertOne(Notice notice) {
        return noticeMapper.insertOne(notice) > 0;
    }

    public boolean deleteById(Integer noticeId) {
        return noticeMapper.deleteById(noticeId) > 0;
    }

    public List<NoticeBean> selectAll(Integer pageNum) {
        PageMethod.startPage(pageNum, 4);
        return noticeMapper.selectAll();
    }
}

