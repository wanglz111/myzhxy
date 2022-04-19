package com.xjtlu.myzhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.xjtlu.myzhxy.mapper.GradeMapper;
import com.xjtlu.myzhxy.pojo.Grade;
import com.xjtlu.myzhxy.service.GradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service("gradeServiceImpl")
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {

    @Override
    public IPage<Grade> getGradeByOpr(Page<Grade> page, String gradeName) {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isNullOrEmpty(gradeName)) {
            queryWrapper.like("name", gradeName);
        }

        queryWrapper.orderByDesc("id");
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public void saveOrUpdateGrade(Grade grade) {
        if (grade.getId() == null) {
            baseMapper.insert(grade);
        } else {
            baseMapper.updateById(grade);
        }
    }

    @Override
    public void deleteGrade(Integer[] ids) {
        baseMapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public List<Grade> getGrades() {
        return baseMapper.selectList(null);
    }
}
