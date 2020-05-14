package com.cbr.university.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbr.university.dao.impl.TeacherDaoImpl;
import com.cbr.university.model.Teacher;
import com.cbr.university.service.BaseService;

@Service
public class TeacherServiceImpl implements BaseService<Teacher> {

    private TeacherDaoImpl teacherDaoImpl;

    @Autowired
    public TeacherServiceImpl(TeacherDaoImpl teacherDaoImpl) {
        this.teacherDaoImpl = teacherDaoImpl;
    }

    @Override
    public void create(Teacher teacher) {
        teacherDaoImpl.create(teacher);
    }

    @Override
    public void update(Teacher teacher) {
        teacherDaoImpl.update(teacher);
    }

    @Override
    public void delete(Teacher teacher) {
        teacherDaoImpl.delete(teacher);
    }

    @Override
    public List<Teacher> getAll() {
        return teacherDaoImpl.getAll();
    }

    @Override
    public Teacher getById(int id) {
        return teacherDaoImpl.getById(id);
    }
}