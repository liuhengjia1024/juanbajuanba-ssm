package com.liuhengjia.mapper;

import com.liuhengjia.entity.Administrator;
import org.apache.ibatis.annotations.Param;

public interface AdministratorMapper {
    Integer insertSignUp(Administrator administrator);

    Integer updatePassword(@Param("verificationCode") String verificationCode, @Param("password") String password);

    Integer updateStatusCode(String verificationCode);

    Integer selectAdministratorExist(String email);

    Administrator selectById(Integer administratorId);

    Administrator selectSignIn(Administrator administrator);
}
