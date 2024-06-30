package com.liuhengjia.mapper;

import com.liuhengjia.entity.Student;

import java.util.List;

public interface StudentMapper {
    Integer insertSignUp(Student student);

    Integer updatePassword(String verificationCode, String password);

    Integer updateStatusCode(String verificationCode);

    Integer updateStatusCodeByStudentId(Integer studentId);

    Integer updateVerificationCode(Integer studentId, String verificationCode);

    boolean updateOne(Student student);

    Student selectById(Integer studentId);

    Integer selectCount();

    Student selectSignIn(Student student);

    Integer selectStudentExist(String email);

    List<Student> selectAll();
}