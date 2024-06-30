package com.liuhengjia.service.impl;

import com.github.pagehelper.page.PageMethod;
import com.liuhengjia.entity.Student;
import com.liuhengjia.mapper.StudentMapper;
import com.liuhengjia.model.UserBean;
import com.liuhengjia.service.StudentService;
import com.liuhengjia.util.MailUtil;
import com.liuhengjia.util.UuidUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("studentService")
public class StudentServiceImpl implements StudentService {
    private final StudentMapper studentMapper;

    public StudentServiceImpl(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    public boolean insertSignUp(Student student) {
        Integer count = -1;
        if (studentMapper.selectStudentExist(student.getEmail()) <= 0) {
            student.setVerificationCode(UuidUtil.getUuid());
            student.setStatusCode("N");
            count = studentMapper.insertSignUp(student);
            String content = "激活码：" + student.getVerificationCode();
            MailUtil.sendMail(student.getEmail(), content, "激活邮件");
        }
        return count > 0;
    }

    public boolean updatePassword(String verificationCode, String newPassword) {
        return studentMapper.updatePassword(verificationCode, newPassword) > 0;
    }

    public boolean updateStatusCode(String verificationCode) {
        return studentMapper.updateStatusCode(verificationCode) > 0;
    }

    public boolean updateStatusCodeByStudentId(Integer studentId) {
        return studentMapper.updateStatusCodeByStudentId(studentId) > 0;
    }

    public boolean updateVerificationCode(Integer studentId) {
        Integer count = -1;
        Student student = studentMapper.selectById(studentId);
        if (student != null) {
            String verificationCode = UuidUtil.getUuid();
            String content = "校验码：" + verificationCode;
            MailUtil.sendMail(student.getEmail(), content, "重置密码邮件");
            count = studentMapper.updateVerificationCode(studentId, verificationCode);
        }

        return count > 0;
    }

    public Student selectById(Integer studentId) {
        return studentMapper.selectById(studentId);
    }

    public Integer selectCount() {
        return studentMapper.selectCount();
    }

    public UserBean selectSignIn(Student studentSignIn) {
        return new UserBean(studentMapper.selectSignIn(studentSignIn));
    }

    public List<Student> selectAll(Integer pageNum) {
        PageMethod.startPage(pageNum, 4);
        return studentMapper.selectAll();
    }

    public boolean updateOne(Student student) {
        return studentMapper.updateOne(student);
    }
}
