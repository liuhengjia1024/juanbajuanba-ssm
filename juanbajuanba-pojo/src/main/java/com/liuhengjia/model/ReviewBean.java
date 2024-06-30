package com.liuhengjia.model;

import com.liuhengjia.entity.Review;
import com.liuhengjia.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class ReviewBean {
    private Student student;
    private Review review;
}
