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
public class Category {
    private Integer id;
    private String name;
    private String imgPath;
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date createTime;

    public String getCreateTimeString() {
        String createTimeString = "";
        if (this.createTime != null) {
            createTimeString = DateUtil.date2String(this.createTime, "yyyy/MM/dd HH:mm");
        }

        return createTimeString;
    }
}
