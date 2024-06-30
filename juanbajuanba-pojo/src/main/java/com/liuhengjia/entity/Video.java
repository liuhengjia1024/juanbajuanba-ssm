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
public class Video {
    private Integer id;
    private Integer courseId;
    private String name;
    private String videoPath;
    private Integer viewNumber;
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private Date createTime;

    public String getCreateTimeString() {
        if (this.createTime == null) {
            return "";
        }
        return DateUtil.date2String(this.createTime, "yyyy/MM/dd HH:mm");
    }
}
