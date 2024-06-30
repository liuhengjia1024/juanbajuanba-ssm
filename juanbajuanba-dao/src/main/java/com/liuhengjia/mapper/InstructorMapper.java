package com.liuhengjia.mapper;

import com.liuhengjia.entity.Instructor;

import java.util.List;

public interface InstructorMapper {
    Integer insertOne(Instructor instructor);

    Integer deleteById(Integer instructorId);

    Integer updateOne(Instructor instructor);

    Instructor selectById(Integer instructorId);

    List<Instructor> selectAll();

    Integer selectCount();
}