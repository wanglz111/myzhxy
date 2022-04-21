package com.xjtlu.myzhxy.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.cj.log.Log;
import com.xjtlu.myzhxy.pojo.Admin;
import com.xjtlu.myzhxy.pojo.LoginForm;
import com.xjtlu.myzhxy.pojo.Student;
import com.xjtlu.myzhxy.pojo.Teacher;
import com.xjtlu.myzhxy.service.AdminService;
import com.xjtlu.myzhxy.service.StudentService;
import com.xjtlu.myzhxy.service.TeacherService;
import com.xjtlu.myzhxy.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/sms/system")
public class SystemController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;


    @GetMapping("/getInfo")
    public Result getInfoByToken(@RequestHeader String token) {
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        //从token中解析
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        Map<String, Object> map = new LinkedHashMap<>();

        switch (userType) {
            case 1:
                Admin admin = adminService.getAdminById(userId);
                map.put("userType", 1);
                map.put("user", admin);
                break;
            case 2:
                Student student = studentService.getStudentById(userId);
                map.put("userType", 2);
                map.put("user", student);
                break;
            case 3:
                Teacher teacher = teacherService.getTeacherById(userId);
                map.put("userType", 3);
                map.put("user", teacher);
                break;
        }
        return Result.ok(map);
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm, HttpServletRequest request){
        //获取session中的验证码
        HttpSession session = request.getSession();
        String verifiCode = (String) session.getAttribute("verifiCode");
        String loginVerifiCode = loginForm.getVerifiCode();
        if("".equals(loginVerifiCode)||loginVerifiCode==null){
            return Result.fail().message("验证码失效，请重试");
        }
        if(!loginVerifiCode.equalsIgnoreCase(verifiCode)){
            return Result.fail().message("验证码有误，请重试");
        }
        session.removeAttribute("verifiCode");
        //验证用户
        Map<String, Object> map = new LinkedHashMap<>();
        switch (loginForm.getUserType()){
            case "1" :
            try {
                Admin admin = adminService.login(loginForm);
                if(admin != null){
                    //用户的id和类型转换成秘文
                    String token = JwtHelper.createToken(admin.getId().longValue(), 1);
                    map.put("token", token);
                } else {
                    throw new RuntimeException("用户名或密码错误");
                }
                return Result.ok(map);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.fail().message(e.getMessage());
            }
            case "2":
                try {
                    Student student = studentService.login(loginForm);
                if(student != null){
                    //用户的id和类型转换成秘文
                    String token = JwtHelper.createToken(student.getId().longValue(), 2);
                    map.put("token", token);
                } else {
                    throw new RuntimeException("用户名或密码错误");
                }
                return Result.ok(map);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.fail().message(e.getMessage());
            }
            case "3":
                try {
                    Teacher teacher = teacherService.login(loginForm);
                if(teacher != null){
                    //用户的id和类型转换成秘文
                    String token = JwtHelper.createToken(teacher.getId().longValue(), 3);
                    map.put("token", token);
                } else {
                    throw new RuntimeException("用户名或密码错误");
                }
                return Result.ok(map);
            } catch (Exception e) {
                e.printStackTrace();
                return Result.fail().message(e.getMessage());
            }
            default: return Result.fail().message("用户类型错误");
        }
    }

    @GetMapping("/getVerifiCodeImage")
    public  void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取验证码图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取图片上的验证码
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());
        //将验证码存入session
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode",verifiCode);
        //将验证码响应给浏览器
        try {
            ImageIO.write(verifiCodeImage,"jpeg",response.getOutputStream());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(@RequestPart("multipartFile") MultipartFile file, HttpServletRequest request) throws IOException {
        String uuid = UUID.randomUUID().toString().replace("-","").toLowerCase(Locale.ROOT);
        String fileName = file.getOriginalFilename();
        int indexSuffix = fileName.lastIndexOf(".");
        String newFileName = uuid.concat(fileName.substring(indexSuffix));

        // 保存文件发送到第三方，或独立的图片服务器
//        String filePath = "/Users/wangluzhi/Luzhi/myzhxy/target/classes/static/images/" + newFileName;
//        file.transferTo(new File(filePath));
//        String path = "/images/" + newFileName;
        UploadFile.testUpload(newFileName, file.getInputStream());

        return Result.ok();
    }

    @PostMapping("/updatePwd/{lastPwd}/{newPwd}")
    public Result updatePwd(@RequestHeader("token") String token,
                            @PathVariable("lastPwd") String lastPwd,
                            @PathVariable("newPwd") String newPwd,
                            HttpServletRequest request) {

        if (JwtHelper.isExpiration(token)) {
            return Result.fail().message("token已过期");
        }
        Integer userType = JwtHelper.getUserType(token);
        Long userId = JwtHelper.getUserId(token);
        if (userType == 1) {
            QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", userId.intValue());
            queryWrapper.eq("password", MD5.encrypt(lastPwd));
            Admin admin = adminService.getOne(queryWrapper);
            if (admin == null) {
                return Result.fail().message("原密码错误");
            } else {
                admin.setPassword(MD5.encrypt(newPwd));
                adminService.saveOrUpdate(admin);
            }
        } else if (userType == 2) {
            QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", userId.intValue());
            queryWrapper.eq("password", MD5.encrypt(lastPwd));
            Student student = studentService.getOne(queryWrapper);
            if (student == null) {
                return Result.fail().message("原密码错误");
            } else {
                student.setPassword(MD5.encrypt(newPwd));
                studentService.saveOrUpdate(student);
            }
        } else if (userType == 3) {
            QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", userId.intValue());
            queryWrapper.eq("password", MD5.encrypt(lastPwd));
            Teacher teacher = teacherService.getOne(queryWrapper);
            if (teacher == null) {
                return Result.fail().message("原密码错误");
            } else {
                teacher.setPassword(MD5.encrypt(newPwd));
                teacherService.saveOrUpdate(teacher);
            }
        }

        return Result.ok();
    }

}
