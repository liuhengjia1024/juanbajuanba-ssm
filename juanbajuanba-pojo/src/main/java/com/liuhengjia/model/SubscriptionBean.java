package com.liuhengjia.model;

import com.liuhengjia.entity.Course;
import com.liuhengjia.entity.Subscription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class SubscriptionBean {
    private Subscription subscription;
    private Course course;
}
