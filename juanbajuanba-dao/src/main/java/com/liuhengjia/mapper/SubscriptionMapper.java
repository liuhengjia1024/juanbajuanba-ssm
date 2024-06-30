package com.liuhengjia.mapper;

import com.liuhengjia.entity.Subscription;
import com.liuhengjia.model.AdministratorSubscriptionBean;
import com.liuhengjia.model.SubscriptionBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubscriptionMapper {
    Integer insertOne(Subscription subscription);

    Integer deleteSubscriptionById(Integer subscriptionId);

    Subscription selectOne(@Param("courseId") Integer courseId, @Param("studentId") Integer studentId);

    Subscription selectOneById(Integer subscriptionId);

    List<Subscription> selectByCourseId(Integer courseId);

    List<AdministratorSubscriptionBean> selectAdministratorSubscriptionBeanList();

    List<SubscriptionBean> selectByAccount(Integer studentId);
}
