package com.liuhengjia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class StudentIndexBean {
    private Integer categoryId;
    private String categoryImgPath;
    private String categoryName;
    private Integer courseNum;
}
