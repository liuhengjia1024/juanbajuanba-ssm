package com.liuhengjia.service;

import com.liuhengjia.entity.Administrator;
import com.liuhengjia.model.UserBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AdministratorService {
    @Transactional
    boolean insertSignUp(Administrator administrator);

    @Transactional
    boolean updatePassword(String verificationCode, String encodePassword);

    @Transactional
    boolean updateStatusCode(String verificationCode);

    Administrator selectById(Integer administratorId);

    UserBean selectSignIn(Administrator administratorSignIn);
}

