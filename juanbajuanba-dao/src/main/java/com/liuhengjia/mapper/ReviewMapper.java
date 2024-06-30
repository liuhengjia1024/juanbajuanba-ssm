package com.liuhengjia.mapper;

import com.liuhengjia.entity.Review;
import com.liuhengjia.model.AdministratorReviewBean;
import com.liuhengjia.model.StudentReviewBean;

import java.util.List;

public interface ReviewMapper {
    Integer insertOne(Review review);

    Integer deleteReviewById(Integer reviewId);

    Review selectOne(Integer subscriptionId);

    List<AdministratorReviewBean> selectAdministratorReviewBeanList();

    List<StudentReviewBean> selectStudentReviewBeanList(List<Integer> subscriptionIdList);
}
