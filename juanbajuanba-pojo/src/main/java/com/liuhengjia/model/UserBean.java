package com.liuhengjia.model;

import com.liuhengjia.common.PathConstant;
import com.liuhengjia.constant.Power;
import com.liuhengjia.entity.Administrator;
import com.liuhengjia.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class UserBean implements Serializable {
    private Integer id;
    private String avatarPath;
    private String email;
    private String power;
    private String nickname;
    private String statusCode;

    public UserBean(Student student) {
        if (student != null) {
            this.id = student.getId();
            this.avatarPath = PathConstant.STUDENT_AVATAR_DIR_PATH + student.getAvatarPath();
            this.email = student.getEmail();
            this.power = Power.STUDENT.getValue();
            this.nickname = student.getNickname();
            this.statusCode = student.getStatusCode();
        }
    }

    public UserBean(Administrator administrator) {
        if (administrator != null) {
            this.id = administrator.getId();
            this.avatarPath = PathConstant.ADMINISTRATOR_AVATAR_DIR_PATH + administrator.getAvatarPath();
            this.email = administrator.getEmail();
            this.power = Power.ADMINISTRATOR.getValue();
            this.nickname = administrator.getNickname();
            this.statusCode = administrator.getStatusCode();
        }
    }
}
