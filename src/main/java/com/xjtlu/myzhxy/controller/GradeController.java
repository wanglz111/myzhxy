package com.xjtlu.myzhxy.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xjtlu.myzhxy.pojo.Grade;
import com.xjtlu.myzhxy.service.GradeService;
import com.xjtlu.myzhxy.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGrade (
            @PathVariable("pageNo") Integer pageNo,
            @PathVariable("pageSize") Integer pageSize,
            String gradeName)
    {
        //分页 带条件查询
        Page<Grade> page = new Page<>(pageNo, pageSize);
        //通过服务层进行查询
        IPage<Grade> pageRs = gradeService.getGradeByOpr(page, gradeName);

        //封装Result并返回
        return Result.ok(pageRs);
    }

    @ApiOperation(value = "添加班级", notes = "添加班级")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@RequestBody Grade grade){
    	gradeService.saveOrUpdateGrade(grade);
    	return Result.ok();
    }
    @ApiOperation(value = "删除班级", notes = "根据id删除班级")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(@RequestBody Integer[] ids){
    	gradeService.deleteGrade(ids);
    	return Result.ok();
    }

    @GetMapping("/getGrades")
    public Result getGrades(){
    	List<Grade> grades = gradeService.getGrades();
    	return Result.ok(grades);
    }
}

