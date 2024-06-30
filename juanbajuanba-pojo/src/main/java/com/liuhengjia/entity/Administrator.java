package com.liuhengjia.entity;

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
public class Administrator {
    private Integer id;
    private String email;
    private String password;
    private String avatarPath;
    private String nickname;
    private String realName;
    private String statusCode;
    private String verificationCode;
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date joinDate;
}
