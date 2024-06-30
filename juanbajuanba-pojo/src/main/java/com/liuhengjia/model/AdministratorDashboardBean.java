package com.liuhengjia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class AdministratorDashboardBean {
    private Integer courseNum;
    private Integer studentNum;
    private Integer instructorNum;
    private Integer downloadNum;
}
