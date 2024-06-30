package com.liuhengjia.service;

import com.liuhengjia.entity.Instructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface InstructorService {
    boolean insertOne(Instructor instructor);

    boolean deleteById(Integer instructorId);

    boolean updateOne(Instructor instructor);

    Instructor selectById(Integer instructorId);

    List<Instructor> selectAll();

    List<Instructor> selectAll(Integer pageNum);

    Integer selectCount();
}
