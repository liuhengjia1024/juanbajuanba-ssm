package com.liuhengjia.model;

import com.liuhengjia.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class StudentCourseDetailBean {
    private Course course;
    private Category category;
    private Instructor instructor;
    private Subscription subscription;
    private List<Subscription> subscriptionList;
    private Integer subscriptionListSize;
    private Review review;
    private List<ReviewBean> reviewBeanList;
    private Integer reviewRate;
}