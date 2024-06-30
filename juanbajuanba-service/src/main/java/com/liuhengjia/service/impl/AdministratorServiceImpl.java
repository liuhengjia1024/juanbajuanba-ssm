package com.liuhengjia.service.impl;

import com.liuhengjia.constant.Power;
import com.liuhengjia.entity.Administrator;
import com.liuhengjia.mapper.AdministratorMapper;
import com.liuhengjia.model.UserBean;
import com.liuhengjia.service.AdministratorService;
import com.liuhengjia.util.MailUtil;
import com.liuhengjia.util.UuidUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service("administratorService")
@AllArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {
    private final AdministratorMapper administratorMapper;

    public boolean insertSignUp(Administrator administrator) {
        Integer count = -1;
        if (administratorMapper.selectAdministratorExist(administrator.getEmail()) <= 0) {
            administrator.setVerificationCode(UuidUtil.getUuid());
            administrator.setStatusCode("N");
            count = administratorMapper.insertSignUp(administrator);
            String content = Power.ADMINISTRATOR + administrator.getNickname() + "（" + administrator.getEmail() + "）的激活码：" + administrator.getVerificationCode();
            MailUtil.sendMail(Power.BOSS_EMAIL.getValue(), content, Power.ADMINISTRATOR + administrator.getNickname() + "的激活邮件");
        }
        return count > 0;
    }

    public boolean updatePassword(String verificationCode, String encodePassword) {
        return administratorMapper.updatePassword(verificationCode, encodePassword) > 0;
    }

    public boolean updateStatusCode(String verificationCode) {
        return administratorMapper.updateStatusCode(verificationCode) > 0;
    }

    public Administrator selectById(Integer administratorId) {
        return administratorMapper.selectById(administratorId);
    }

    public UserBean selectSignIn(Administrator administratorSignIn) {
        return new UserBean(administratorMapper.selectSignIn(administratorSignIn));
    }
}
