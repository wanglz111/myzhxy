package com.xjtlu.myzhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xjtlu.myzhxy.pojo.Clazz;
import com.xjtlu.myzhxy.service.ClazzService;
import com.xjtlu.myzhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;


    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result<IPage<Clazz>> getClazzsByOpr(
            @PathVariable("pageNo") Integer pageNo,
            @PathVariable("pageSize") Integer pageSize,
            Clazz clazz
            )
    {
        Page<Clazz> page = new Page<>(pageNo,pageSize);
        IPage<Clazz> pageRs = clazzService.getClazzsByOpr(page,clazz);
        return Result.ok(pageRs);
    }

    @PostMapping("/saveOrUpdateClazz")
    public Result<Clazz> saveOrUpdate(@RequestBody Clazz clazz)
    {
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }

    @DeleteMapping("/deleteClazz")
    public Result<Clazz> deleteClazz(@RequestBody List<Integer> ids)
    {
        clazzService.deleteById(ids);
        return Result.ok();
    }

    @GetMapping("/getClazzs")
    public Result getClazzs()
    {
        List<Clazz> clazzs = clazzService.list();
        return Result.ok(clazzs);
    }

}
