package com.liuhengjia.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Power {
    ADMINISTRATOR("administrator", "管理员"),
    STUDENT("student", "学生"),
    BOSS_EMAIL("bossEmail", "dev16637712266@petalmail.com");

    private final String key;
    private final String value;
}
