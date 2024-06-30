package com.liuhengjia.service;

import com.liuhengjia.entity.Review;
import com.liuhengjia.model.AdministratorReviewBean;
import com.liuhengjia.model.StudentReviewBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ReviewService {
    @Transactional
    boolean insertOne(Review review);

    @Transactional
    boolean deleteReviewById(Integer reviewId);

    Review selectOne(Integer subscriptionId);

    List<AdministratorReviewBean> selectAdministratorReviewBeanList(Integer pageNum);

    List<StudentReviewBean> selectStudentReviewBeanList(int pageNum, Integer studentId);
}
