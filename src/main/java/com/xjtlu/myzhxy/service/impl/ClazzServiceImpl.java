package com.xjtlu.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.xjtlu.myzhxy.mapper.ClazzMapper;
import com.xjtlu.myzhxy.pojo.Clazz;
import com.xjtlu.myzhxy.service.ClazzService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("clazzServiceImpl")
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {
    @Override
    public IPage<Clazz> getClazzsByOpr(Page<Clazz> clazzs, Clazz clazz) {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isNullOrEmpty(clazz.getName())) {
            queryWrapper.like("name", clazz.getName());
        }
        if (!StringUtils.isNullOrEmpty(clazz.getGradeName())) {
            queryWrapper.like("grade_name", clazz.getGradeName());
        }
        queryWrapper.orderByDesc("id");
        return baseMapper.selectPage(clazzs, queryWrapper);
    }

    @Override
    public void deleteById(List<Integer> ids) {
        baseMapper.deleteBatchIds(ids);
    }
}
