package com.liuhengjia.model;

import com.liuhengjia.entity.Course;
import com.liuhengjia.entity.Student;
import com.liuhengjia.entity.Subscription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class AdministratorSubscriptionBean {
    private Student student;
    private Subscription subscription;
    private Course course;
}
