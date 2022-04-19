package com.xjtlu.myzhxy.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xjtlu.myzhxy.pojo.Admin;
import com.xjtlu.myzhxy.pojo.LoginForm;

import java.util.List;

public interface AdminService extends IService<Admin> {
    /**
     * 管理员登录
     * @param admin
     * @return
     */
    Admin login(LoginForm loginForm);
    Admin getAdminById(Long id);

    Page<Admin> getAllAdmin(Page<Admin> page, String adminName);

    void deletebyIds(List<Integer> ids);
}
