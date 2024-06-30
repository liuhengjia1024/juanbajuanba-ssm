package com.liuhengjia.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class MailTest {

    @Test
    public void sendMailTest() {
        System.out.println(MailUtil.sendMail("liuhengjia@petalmail.com", "你好，这是一封测试邮件，无需回复。", "测试邮件") ? "发送成功" : "发送失败");
    }
}
