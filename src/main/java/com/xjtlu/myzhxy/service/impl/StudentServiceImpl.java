package com.xjtlu.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.xjtlu.myzhxy.mapper.StudentMapper;
import com.xjtlu.myzhxy.pojo.LoginForm;
import com.xjtlu.myzhxy.pojo.Student;
import com.xjtlu.myzhxy.service.StudentService;
import com.xjtlu.myzhxy.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("studentServiceImpl")
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    public Student login(LoginForm loginForm) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));

        return this.getOne(queryWrapper);
    }

    public Student getStudentById(Long id) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        return this.getOne(queryWrapper);
    }

    @Override
    public Page<Student> getStudentByOpr(Page<Student> page, Student student) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isNullOrEmpty(student.getName())) {
            queryWrapper.like("name", student.getName());
        }
        if (!StringUtils.isNullOrEmpty(student.getClazzName())) {
            queryWrapper.like("clazz_name", student.getClazzName());
        }
        queryWrapper.orderByDesc("id");

        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void deleteStudent(List<Integer> ids) {
        baseMapper.deleteBatchIds(ids);
    }

}
