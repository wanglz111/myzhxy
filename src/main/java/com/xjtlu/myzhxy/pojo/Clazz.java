package com.xjtlu.myzhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("tb_clazz")
public class Clazz {
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    private String name;
    private String number;
    private String introduction;
    private String headmaster;
    private String email;
    private String telephone;
    private String gradeName;
}
