package com.liuhengjia.model;

import com.liuhengjia.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class StudentCourseBean {
    private Course course;
    private String categoryName;
    private boolean isSubscription;
    private Integer videoNum;
    private Integer materialNum;

    public boolean isSubscription() {
        return this.isSubscription;
    }

    public void setSubscription(boolean subscription) {
        this.isSubscription = subscription;
    }

    public boolean getIsSubscription() {
        return this.isSubscription();
    }
}
