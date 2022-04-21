package com.xjtlu.myzhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xjtlu.myzhxy.pojo.LoginForm;
import com.xjtlu.myzhxy.pojo.Teacher;

import java.util.List;

public interface TeacherService extends IService<Teacher> {
    Teacher login(LoginForm loginForm);

    Teacher getTeacherById(Long id);

    IPage<Teacher> getTeacher(IPage<Teacher> page, String teacherName);

    void deleteTeacher(List<Integer> ids);

    Teacher getAllByClazzNameAndId(String clazzName, String id);
}
