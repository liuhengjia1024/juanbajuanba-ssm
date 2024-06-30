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
public class Instructor {
    private Integer id;
    private String avatarPath;
    private String realName;
    private String email;
    private String description;
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date joinDate;

    public String getJoinDateString() {
        String joinDateString = "";
        if (this.joinDate != null) {
            joinDateString = DateUtil.date2String(this.joinDate, "yyyy/MM/dd HH:mm");
        }
        return joinDateString;
    }
}
