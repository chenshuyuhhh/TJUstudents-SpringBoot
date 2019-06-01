package com.chenshuyusc.TJUstudents.web;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chenshuyusc.TJUstudents.core.Result;
import com.chenshuyusc.TJUstudents.core.ResultGenerator;
import com.chenshuyusc.TJUstudents.model.CourseInfo;
import com.chenshuyusc.TJUstudents.model.Courses;
import com.chenshuyusc.TJUstudents.model.Selections;
import com.chenshuyusc.TJUstudents.service.CoursesService;
import com.chenshuyusc.TJUstudents.service.impl.CoursesServiceImpl;
import com.chenshuyusc.TJUstudents.service.impl.SelectionsServiceImpl;
import com.chenshuyusc.TJUstudents.service.impl.StudentsServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CodeGenerator on 2019/04/29.
 */
@Controller
@RestController
@RequestMapping("/courses")
@Api(value = "课程信息的控制器", description = "Courses 的相关信息接口")
public class CoursesController {
    @Resource
    private CoursesServiceImpl coursesService;
    @Resource
    private SelectionsServiceImpl selectionsService;
    @Resource
    private StudentsServiceImpl studentsService;

    @PostMapping("/add")
    @ResponseBody
    @ApiOperation(value = "增加一个新的课程记录")
    @ApiImplicitParam(paramType = "query", name = "course", value = "课程信息的 json 字符串", required = true, dataType = "String")
    public Result add(@RequestParam String course) {
        Courses courses = JSONObject.parseObject(course, new TypeReference<Courses>() {
        });
        coursesService.save(courses);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    @ResponseBody
    @ApiOperation(value = "根据课程编号删除该课程信息")
    @ApiImplicitParam(paramType = "query", name = "id", value = "课程编号", required = true, dataType = "String")
    public Result delete(@RequestParam String id) {
        coursesService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    @ResponseBody
    @ApiOperation(value = "更新一个课程记录")
    @ApiImplicitParam(paramType = "query", name = "course", value = "选课信息的 json 字符串", required = true, dataType = "String")
    public Result update(@RequestParam String course) {
        Courses courses = JSONObject.parseObject(course, new TypeReference<Courses>() {
        });
        coursesService.update(courses);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/detail")
    @ResponseBody
    @ApiOperation(value = "根据课程的课程编号获得课程信息")
    @ApiImplicitParam(paramType = "query", name = "id", value = "课程编号", required = true, dataType = "String")
    public Result detail(@RequestParam String id) {
        Courses courses = coursesService.findById(id);
        return ResultGenerator.genSuccessResult(courses);
    }

    @GetMapping("/detailname")
    @ResponseBody
    @ApiOperation(value = "根据课程的课程编号获得课程信息")
    @ApiImplicitParam(paramType = "query", name = "name", value = "课程名称", required = true, dataType = "String")
    public Result detailByName(@RequestParam String name) {
        Courses courses = coursesService.findById(coursesService.selectByName(name));
        return ResultGenerator.genSuccessResult(courses);
    }

    @GetMapping("/list")
    @ResponseBody
    @ApiOperation(value = "根据页数和每页的大小获得课程信息列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "page", value = "第几页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页中列表元素个数", required = true, dataType = "Integer")
    })
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Courses> list = coursesService.findAll();
        PageInfo pageInfo = new PageInfo<>(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 根据学生的学号 sid 获得该生所选课的情况
     *
     * @param id
     * @return
     */
    @GetMapping("/studentsid")
    @ResponseBody
    @ApiOperation(value = "根据学生的学号获得该学生选课信息")
    @ApiImplicitParam(paramType = "query", name = "id", value = "学生学号", required = true, dataType = "String")
    public Result studentsid(@RequestParam String id) {
        List<Selections> list = selectionsService.findCoursesBySid(id);
        List<Courses> courses = new ArrayList<>();
        for (Selections s : list) {
            courses.add(coursesService.findById(s.getCid()));
        }
        PageHelper.startPage(1, list.size());
        PageInfo pageInfo = new PageInfo<>(courses);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/score")
    @ResponseBody
    @ApiOperation(value = "根据课程的编号获得该课程的成绩分布")
    @ApiImplicitParam(paramType = "query", name = "cid", value = "课程编号", required = true, dataType = "String")
    public Result score(@RequestParam String cid) {
        List<Selections> selections = selectionsService.findCoursesByCid(cid);
        int under60 = 0;
        int b6and7 = 0;
        int b7and8 = 0;
        int b8and9 = 0;
        int b9and10 = 0;
        int full = 0;
        double avg = 0;
        for (Selections temp : selections) {
            if (temp.getScore() < 60) {
                under60++;
            } else if (temp.getScore() < 70) {
                b6and7++;
            } else if (temp.getScore() < 80) {
                b7and8++;
            } else if (temp.getScore() < 90) {
                b8and9++;
            } else if (temp.getScore() < 100) {
                b9and10++;
            } else {
                full++;
            }
            avg += temp.getScore();
        }
        if(selections.isEmpty()){
            avg = 0;
        }else {
            avg = avg / selections.size();
        }
        CourseInfo courseInfo = new CourseInfo(coursesService.findById(cid), under60, b6and7, b7and8, b8and9, b9and10, full, avg,selections.size());
        return ResultGenerator.genSuccessResult(courseInfo);
    }
}
