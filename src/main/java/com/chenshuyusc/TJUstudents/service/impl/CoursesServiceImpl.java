package com.chenshuyusc.TJUstudents.service.impl;

import com.chenshuyusc.TJUstudents.dao.CoursesMapper;
import com.chenshuyusc.TJUstudents.model.Courses;
import com.chenshuyusc.TJUstudents.service.CoursesService;
import com.chenshuyusc.TJUstudents.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2019/04/29.
 */
@Service
@Transactional
public class CoursesServiceImpl extends AbstractService<Courses> implements CoursesService {
    @Resource
    private CoursesMapper coursesMapper;

    /**
     * 可以根据课程名称找出 id
     * @param name
     * @return
     */
    public String selectByName(String name){
        Courses courses = new Courses();
        courses.setCname(name);
        Courses courses1 = coursesMapper.selectOne(courses);
        return courses1.getCid();
    }
}
