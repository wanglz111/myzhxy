package com.xjtlu.myzhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

@Data
@TableName("tb_teacher")
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String tno;
    private String name;
    private char gender;
    private String password;
    private String email;
    private String telephone;
    private String address;
    private String clazzName;
    private String portraitPath;//存储头像的项目路径

//    @TableLogic
//    private Integer isDeleted;
}
