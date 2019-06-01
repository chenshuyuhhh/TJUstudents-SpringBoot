package com.chenshuyusc.TJUstudents.service.impl;

import com.chenshuyusc.TJUstudents.dao.StudentsMapper;
import com.chenshuyusc.TJUstudents.model.Students;
import com.chenshuyusc.TJUstudents.service.StudentsService;
import com.chenshuyusc.TJUstudents.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by CodeGenerator on 2019/04/29.
 */
@Service
@Transactional
public class StudentsServiceImpl extends AbstractService<Students> implements StudentsService {
    @Resource
    private StudentsMapper studentsMapper;

    /**
     * 可以根据姓名找出对应的所有的学生学号
     *
     * @param name 学生姓名
     * @return
     */
    public List<String> selectByName(String name) {
        Students students = new Students();
        students.setSname(name);
        List<Students> studentsList = studentsMapper.select(students);
        List<String> ids = new ArrayList<>();
        for (Students s : studentsList) {
            ids.add(s.getSid());
        }
        return ids;
    }

    /**
     * 根据班级名称找出这个班级的所有的学生
     *
     * @param classname 班级名称
     * @return
     */
    @Override
    public List<Students> selectByClass(String classname, Integer year) {
        Students students = new Students();
        students.setClassname(classname);
        students.setYear(year);
        List<Students> studentsList = studentsMapper.select(students);
        return studentsList;
    }
}
