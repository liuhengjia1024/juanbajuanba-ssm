package com.liuhengjia.model;

import com.liuhengjia.entity.Administrator;
import com.liuhengjia.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class NoticeBean {
    private Notice notice;
    private Administrator administrator;
}
