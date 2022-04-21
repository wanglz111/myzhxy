package com.xjtlu.myzhxy.mapper;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xjtlu.myzhxy.pojo.Teacher;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TeacherMapper extends BaseMapper<Teacher> {
    Teacher getTeacherByUsernameAndId(String username, String id);
}
