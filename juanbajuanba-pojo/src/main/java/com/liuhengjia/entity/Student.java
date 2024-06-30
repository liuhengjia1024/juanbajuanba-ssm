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
public class Student {
    private Integer id;
    private String email;
    private String password;
    private String avatarPath;
    private String nickname;
    private String realName;
    private String sex;
    private String personalSignature;
    private String statusCode;
    private String verificationCode;
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date joinDate;

    public String getStatusCodeString() {
        return "N".equals(this.statusCode) ? "未激活" : "已激活";
    }

    public String getJoinDateString() {
        if (this.joinDate == null) {
            return "";
        }
        return DateUtil.date2String(this.joinDate, "yyyy/MM/dd HH:mm");
    }
}
