package com.liuhengjia.service;

import com.liuhengjia.entity.Student;
import com.liuhengjia.model.UserBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface StudentService {
    @Transactional
    boolean insertSignUp(Student student);

    @Transactional
    boolean updateStatusCode(String verificationCode);

    @Transactional
    boolean updateStatusCodeByStudentId(Integer studentId);

    @Transactional
    boolean updateVerificationCode(Integer studentId);

    @Transactional
    boolean updateOne(Student student);

    @Transactional
    boolean updatePassword(String verificationCode, String newPassword);

    Student selectById(Integer studentId);

    Integer selectCount();

    UserBean selectSignIn(Student studentSignIn);

    List<Student> selectAll(Integer pageNum);
}
