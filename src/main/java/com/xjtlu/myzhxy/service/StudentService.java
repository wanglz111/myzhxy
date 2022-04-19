package com.xjtlu.myzhxy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xjtlu.myzhxy.pojo.LoginForm;
import com.xjtlu.myzhxy.pojo.Student;

import java.util.List;

public interface StudentService extends IService<Student> {
    Student login(LoginForm loginForm);
    Student getStudentById(Long id);

    Page<Student> getStudentByOpr(Page<Student> page, Student student);

    void deleteStudent(List<Integer> ids);
}
