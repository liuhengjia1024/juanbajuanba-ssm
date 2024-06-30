package com.liuhengjia.service.impl;

import com.github.pagehelper.page.PageMethod;
import com.liuhengjia.entity.Review;
import com.liuhengjia.mapper.ReviewMapper;
import com.liuhengjia.mapper.SubscriptionMapper;
import com.liuhengjia.model.AdministratorReviewBean;
import com.liuhengjia.model.StudentReviewBean;
import com.liuhengjia.model.SubscriptionBean;
import com.liuhengjia.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {
    private ReviewMapper reviewMapper;
    private SubscriptionMapper subscriptionMapper;

    public boolean insertOne(Review review) {
        return reviewMapper.insertOne(review) > 0;
    }

    public boolean deleteReviewById(Integer reviewId) {
        return reviewMapper.deleteReviewById(reviewId) > 0;
    }

    public Review selectOne(Integer subscriptionId) {
        return reviewMapper.selectOne(subscriptionId);
    }

    public List<AdministratorReviewBean> selectAdministratorReviewBeanList(Integer pageNum) {
        PageMethod.startPage(pageNum, 4);
        return reviewMapper.selectAdministratorReviewBeanList();
    }

    public List<StudentReviewBean> selectStudentReviewBeanList(int pageNum, Integer studentId) {
        List<SubscriptionBean> subscriptionBeanList = subscriptionMapper.selectByAccount(studentId);
        List<Integer> subscriptionIdList = new ArrayList<>();
        for (SubscriptionBean subscriptionBean : subscriptionBeanList) {
            subscriptionIdList.add(subscriptionBean.getSubscription().getId());
        }
        if (!subscriptionIdList.isEmpty()) {
            PageMethod.startPage(pageNum, 4);
            return reviewMapper.selectStudentReviewBeanList(subscriptionIdList);
        } else {
            PageMethod.startPage(pageNum, 4);
            return new ArrayList<>();
        }
    }
}
