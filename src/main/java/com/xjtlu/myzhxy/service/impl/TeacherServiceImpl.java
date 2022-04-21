package com.xjtlu.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.xjtlu.myzhxy.mapper.TeacherMapper;
import com.xjtlu.myzhxy.pojo.LoginForm;
import com.xjtlu.myzhxy.pojo.Teacher;
import com.xjtlu.myzhxy.service.TeacherService;
import com.xjtlu.myzhxy.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("teacherServiceImpl")
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    public Teacher login(LoginForm loginForm) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user", loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));

        return this.getOne(queryWrapper);
    }

    public Teacher getTeacherById(Long id) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

        return this.getOne(queryWrapper);
    }

    @Override
    public IPage<Teacher> getTeacher(IPage<Teacher> page, String teacherName) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isNullOrEmpty(teacherName)) {
            queryWrapper.like("name", teacherName);
        }

        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void deleteTeacher(List<Integer> ids) {
        baseMapper.deleteBatchIds(ids);
    }

    @Override
    public Teacher getAllByClazzNameAndId(String clazzName, String id) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("clazz_name", clazzName);
        queryWrapper.eq("id", id);

        return this.getOne(queryWrapper);
    }

}
