package com.xjtlu.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xjtlu.myzhxy.pojo.Teacher;
import com.xjtlu.myzhxy.service.TeacherService;
import com.xjtlu.myzhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @RequestMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeacher(
            @PathVariable("pageNo") Integer pageNo,
            @PathVariable("pageSize") Integer pageSize,
            String teacherName
    ) {
        IPage<Teacher> page = new Page<>(pageNo, pageSize);
        IPage<Teacher> teacherPage = teacherService.getTeacher(page, teacherName);

        return Result.ok(teacherPage);
    }

    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdate(@RequestBody Teacher teacher) {
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }

    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(@RequestBody List<Integer> ids) {
        teacherService.deleteTeacher(ids);
        return Result.ok();
    }

}
