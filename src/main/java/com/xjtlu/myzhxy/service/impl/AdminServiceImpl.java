package com.xjtlu.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.xjtlu.myzhxy.mapper.AdminMapper;
import com.xjtlu.myzhxy.pojo.Admin;
import com.xjtlu.myzhxy.pojo.LoginForm;
import com.xjtlu.myzhxy.service.AdminService;
import com.xjtlu.myzhxy.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("adminServiceImpl")
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    public Admin login(LoginForm loginForm) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));

        return this.getOne(queryWrapper);
    }

    @Override
    public Admin getAdminById(Long id) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        return this.getOne(queryWrapper);
    }

    @Override
    public Page<Admin> getAllAdmin(Page<Admin> page, String adminName) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isNullOrEmpty(adminName)) {
            queryWrapper.like("name", adminName);
        }
        queryWrapper.orderByAsc("id");
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void deletebyIds(List<Integer> ids) {
        baseMapper.deleteBatchIds(ids);
    }
}
