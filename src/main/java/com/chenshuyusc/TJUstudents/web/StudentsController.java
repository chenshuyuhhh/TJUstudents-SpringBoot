package com.chenshuyusc.TJUstudents.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chenshuyusc.TJUstudents.core.Result;
import com.chenshuyusc.TJUstudents.core.ResultGenerator;
import com.chenshuyusc.TJUstudents.model.*;
import com.chenshuyusc.TJUstudents.service.SelectionsService;
import com.chenshuyusc.TJUstudents.service.StudentsService;
import com.chenshuyusc.TJUstudents.service.impl.CoursesServiceImpl;
import com.chenshuyusc.TJUstudents.service.impl.SelectionsServiceImpl;
import com.chenshuyusc.TJUstudents.service.impl.StudentsServiceImpl;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2019/04/29.
 */
@Controller
@RestController
@RequestMapping("/students")
@Api(value = "学生信息的控制器", description = "Students 的相关信息接口")
public class StudentsController {
    @Resource
    private StudentsServiceImpl studentsService;
    @Resource
    private SelectionsServiceImpl selectionsService;
    @Resource
    private CoursesServiceImpl coursesService;

    @PostMapping("/add")
    @ResponseBody
    @ApiOperation(value = "增加一个新的学生信息")
    @ApiImplicitParam(paramType = "query", name = "student", value = "学生信息的 json 字符串", required = true, dataType = "String")
    public Result add(@RequestParam String student) {
        Students students = JSONObject.parseObject(student, new TypeReference<Students>() {
        });
        studentsService.save(students);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    @ResponseBody
    @ApiOperation(value = "删除一个学生的信息")
    @ApiImplicitParam(paramType = "query", name = "id", value = "学生的学号", required = true, dataType = "String")
    public Result delete(@RequestParam String id) {
        studentsService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    @ResponseBody
    @ApiOperation(value = "更新一个学生记录")
    @ApiImplicitParam(paramType = "query", name = "student", value = "更新后的学生信息的 json 字符串", required = true, dataType = "String")
    public Result update(@RequestParam String student) {
        Students students = JSONObject.parseObject(student, new TypeReference<Students>() {
        });
        studentsService.update(students);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/detail")
    @ResponseBody
    @ApiOperation(value = "根据学生的学号获得学生信息列表")
    @ApiImplicitParam(paramType = "query", name = "id", value = "学生学号", required = true, dataType = "String")
    public Result detail(@RequestParam String id) {
        Students students = studentsService.findById(id);
        return ResultGenerator.genSuccessResult(students);
    }

    @GetMapping("/list")
    @ResponseBody
    @ApiOperation(value = "根据页数和每页的大小获得学生信息列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "page", value = "第几页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页中列表元素个数", required = true, dataType = "Integer")
    })
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Students> list = studentsService.findAll();
        PageInfo pageInfo = new PageInfo<>(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 根据学生的学号查询学生的基本信息
     *
     * @param id 学生的学号 sid
     * @return 返回学生的信息的 json 格式数据
     */
    @GetMapping("/studentById")
    public Result studentById(@RequestParam String id) {
        Students students = studentsService.findById(id);
        return ResultGenerator.genSuccessResult(students);
    }

    /**
     * 根据学生的姓名查询学生的基本信息
     *
     * @param name 学生的姓名 sname
     * @return 返回学生的信息的数组
     */

    @GetMapping("/studentByName")
    @ResponseBody
    @ApiOperation(value = "根据学生的姓名获得学生信息列表")
    @ApiImplicitParam(paramType = "query", name = "name", value = "学生姓名", required = true, dataType = "String")
    public Result studentByName(@RequestParam String name) {
        List<String> ids = studentsService.selectByName(name);
        List<Students> list = new ArrayList<>();
        for (String id : ids
        ) {
            list.add(studentsService.findById(id));
        }
        PageHelper.startPage(1, ids.size());
        PageInfo pageInfo = new PageInfo<>(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 根据班级名称，或者这个班级的所有信息，包括学生的信息，以及每个学生的加权
     *
     * @param classname 班级名称
     * @return List<StudentInfo>
     */
    @GetMapping("/classAvg")
    @ResponseBody
    @ApiOperation(value = "根据班级名称（年级+班号）获得这个班级的所有信息，包括学生的信息，以及每个学生的加权")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "classname", value = "班级班号", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "year", value = "年级", required = true, dataType = "Integer")
    })
    public Result avgScoreClass(@RequestParam String classname, @RequestParam Integer year) {
        List<Students> studentsList = studentsService.selectByClass(classname, year);
        List<StudentInfo> studentInfos = new ArrayList<>();
        for (Students student : studentsList) {
            double temp = computStudentAvgScore(student.getSid());
            StudentInfo studentInfo = new StudentInfo(student, temp);
            studentInfos.add(studentInfo);
        }
        return ResultGenerator.genSuccessResult(studentInfos);
    }

    /**
     * 能够根据学生的 id 获得该学生的平均加权
     *
     * @param id 学生的学号
     * @return
     */
    @GetMapping("/score")
    @ResponseBody
    @ApiOperation(value = "根据学生的学号获得带学生加权的学生信息")
    @ApiImplicitParam(paramType = "query", name = "id", value = "学生学号", required = true, dataType = "String")
    private Result score(@RequestParam String id) {
        //获得该学生的所有课程
//        List<Selections> list = selectionsService.findCoursesBySid(id);
//
//        double avgscore = 0; // 加权
//        double sumcredit = 0; // 总学分
//        // 遍历这个学生选的所有课程
//        for (Selections selection : list) {
//            // 找到每一门课对应的课程信息，拿到学分，计算加权
//            Courses courses = coursesService.findById(selection.getCid());
//            avgscore += selection.getScore() * courses.getCredit(); // 计算总成绩
//            sumcredit += courses.getCredit(); // 计算总学分
//        }
//        avgscore = avgscore / sumcredit; // 平均加权
        double avgscore = computStudentAvgScore(id);
        StudentInfo studentInfo = new StudentInfo(studentsService.findById(id), avgscore);
        return ResultGenerator.genSuccessResult(studentInfo);
    }

    private double computStudentAvgScore(String id){
        //获得该学生的所有课程
        List<Selections> list = selectionsService.findCoursesBySid(id);

        double avgscore = 0; // 加权
        double sumcredit = 0; // 总学分
        // 遍历这个学生选的所有课程
        for (Selections selection : list) {
            // 找到每一门课对应的课程信息，拿到学分，计算加权
            Courses courses = coursesService.findById(selection.getCid());
            avgscore += selection.getScore() * courses.getCredit(); // 计算总成绩
            sumcredit += courses.getCredit(); // 计算总学分
        }
        avgscore = avgscore / sumcredit; // 平均加权
        return avgscore;
    }
}