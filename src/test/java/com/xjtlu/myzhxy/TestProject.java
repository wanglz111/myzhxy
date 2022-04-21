package com.xjtlu.myzhxy;

import com.xjtlu.myzhxy.mapper.TeacherMapper;
import com.xjtlu.myzhxy.pojo.Teacher;
import com.xjtlu.myzhxy.service.TeacherService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestProject {

    @Autowired
    private TeacherMapper teacherMapper;

    @Test
    public void testTeacher() {
        Teacher teacher = teacherMapper.getTeacherByUsernameAndId(String.valueOf(123), "3");
        System.out.println(teacher);
    }
}
