package com.liuhengjia.entity;

import com.liuhengjia.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class Subscription {
    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private String isReview;
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date subscriptionTime;

    public String getSubscriptionTimeString() {
        if (this.subscriptionTime == null) {
            return "";
        }
        return DateUtil.date2String(this.subscriptionTime, "yyyy/MM/dd HH:mm");
    }
}
