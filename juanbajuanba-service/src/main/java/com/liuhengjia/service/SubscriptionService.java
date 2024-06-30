package com.liuhengjia.service;

import com.liuhengjia.entity.Subscription;
import com.liuhengjia.model.AdministratorSubscriptionBean;
import com.liuhengjia.model.SubscriptionBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface SubscriptionService {
    @Transactional
    boolean insertOne(Subscription subscription);

    @Transactional
    boolean deleteSubscriptionById(Integer subscriptionId);

    Subscription selectOne(Integer courseId, Integer studentId);

    Subscription selectOneById(Integer subscriptionId);

    List<Subscription> selectByCourseId(Integer courseId);

    List<AdministratorSubscriptionBean> selectAdministratorSubscriptionBeanList(Integer pageNum);

    List<SubscriptionBean> selectByAccount(Integer pageNum, Integer studentId);
}
