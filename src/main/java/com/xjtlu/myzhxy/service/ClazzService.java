package com.xjtlu.myzhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xjtlu.myzhxy.pojo.Clazz;

import java.util.List;

public interface ClazzService extends IService<Clazz> {
    IPage<Clazz> getClazzsByOpr(Page<Clazz> clazzs, Clazz clazz);

    void deleteById(List<Integer> ids);
}
