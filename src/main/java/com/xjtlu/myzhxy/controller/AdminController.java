package com.xjtlu.myzhxy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xjtlu.myzhxy.pojo.Admin;
import com.xjtlu.myzhxy.service.AdminService;
import com.xjtlu.myzhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/adminController")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(
            @PathVariable("pageNo") Integer pageNo,
            @PathVariable("pageSize") Integer pageSize,
            String adminName
    ) {
        Page<Admin> page = new Page<>(pageNo, pageSize);
        Page<Admin> pageRs = adminService.getAllAdmin(page, adminName);

        return Result.ok(pageRs);
    }

    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@RequestBody Admin admin) {
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }

    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(@RequestBody List<Integer> ids) {
        adminService.deletebyIds(ids);
        return Result.ok();
    }
}
