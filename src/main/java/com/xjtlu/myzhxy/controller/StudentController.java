package com.xjtlu.myzhxy.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xjtlu.myzhxy.pojo.Student;
import com.xjtlu.myzhxy.service.StudentService;
import com.xjtlu.myzhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/studentController")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(
            @PathVariable("pageNo") Integer pageNo,
            @PathVariable("pageSize") Integer pageSize,
            Student student)
    {
        Page<Student> page = new Page<>(pageNo, pageSize);
        Page<Student> pageRs = studentService.getStudentByOpr(page, student);

        return Result.ok(pageRs);
    }
    @DeleteMapping("/deleteStudent")
    public Result deleteStudent(@RequestBody List<Integer> ids)
    {
        studentService.deleteStudent(ids);
        return Result.ok();
    }

    @PostMapping("addOrUpdateStudent")
    public Result saveOrUpdateStudent(@RequestBody Student student)
    {
        studentService.saveOrUpdate(student);
        return Result.ok();
    }

}

