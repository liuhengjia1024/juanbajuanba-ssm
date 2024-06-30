package com.liuhengjia.service.impl;

import com.github.pagehelper.page.PageMethod;
import com.liuhengjia.entity.Subscription;
import com.liuhengjia.mapper.SubscriptionMapper;
import com.liuhengjia.model.AdministratorSubscriptionBean;
import com.liuhengjia.model.SubscriptionBean;
import com.liuhengjia.service.SubscriptionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("subscriptionService")
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionServiceImpl(SubscriptionMapper subscriptionMapper) {
        this.subscriptionMapper = subscriptionMapper;
    }

    public boolean insertOne(Subscription subscription) {
        return subscriptionMapper.insertOne(subscription) > 0;
    }

    public boolean deleteSubscriptionById(Integer subscriptionId) {
        return subscriptionMapper.deleteSubscriptionById(subscriptionId) > 0;
    }

    public Subscription selectOne(Integer courseId, Integer studentId) {
        return subscriptionMapper.selectOne(courseId, studentId);
    }

    public Subscription selectOneById(Integer subscriptionId) {
        return subscriptionMapper.selectOneById(subscriptionId);
    }

    public List<Subscription> selectByCourseId(Integer courseId) {
        return subscriptionMapper.selectByCourseId(courseId);
    }

    public List<AdministratorSubscriptionBean> selectAdministratorSubscriptionBeanList(Integer pageNum) {
        PageMethod.startPage(pageNum, 4);
        return subscriptionMapper.selectAdministratorSubscriptionBeanList();
    }

    public List<SubscriptionBean> selectByAccount(Integer pageNum, Integer studentId) {
        PageMethod.startPage(pageNum, 4);
        return subscriptionMapper.selectByAccount(studentId);
    }
}
