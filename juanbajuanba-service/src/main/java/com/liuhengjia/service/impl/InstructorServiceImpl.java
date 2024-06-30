package com.liuhengjia.service.impl;

import com.github.pagehelper.page.PageMethod;
import com.liuhengjia.entity.Instructor;
import com.liuhengjia.mapper.InstructorMapper;
import com.liuhengjia.service.InstructorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("instructorService")
public class InstructorServiceImpl implements InstructorService {
    private final InstructorMapper instructorMapper;

    public InstructorServiceImpl(InstructorMapper instructorMapper) {
        this.instructorMapper = instructorMapper;
    }

    public boolean insertOne(Instructor instructor) {
        return instructorMapper.insertOne(instructor) > 0;
    }

    public boolean deleteById(Integer instructorId) {
        return instructorMapper.deleteById(instructorId) > 0;
    }

    public boolean updateOne(Instructor instructor) {
        return instructorMapper.updateOne(instructor) > 0;
    }

    public Instructor selectById(Integer instructorId) {
        return instructorMapper.selectById(instructorId);
    }

    public List<Instructor> selectAll() {
        return instructorMapper.selectAll();
    }

    public List<Instructor> selectAll(Integer pageNum) {
        PageMethod.startPage(pageNum, 4);
        return instructorMapper.selectAll();
    }

    public Integer selectCount() {
        return instructorMapper.selectCount();
    }
}
