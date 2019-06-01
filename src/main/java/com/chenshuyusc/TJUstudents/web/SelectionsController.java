package com.chenshuyusc.TJUstudents.web;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chenshuyusc.TJUstudents.core.Result;
import com.chenshuyusc.TJUstudents.core.ResultGenerator;
import com.chenshuyusc.TJUstudents.model.Courses;
import com.chenshuyusc.TJUstudents.model.Selections;
import com.chenshuyusc.TJUstudents.model.Students;
import com.chenshuyusc.TJUstudents.service.SelectionsService;
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
 * 有关选课的 apis
 *
 * @author chenshuyu
 */
@Controller
@RestController
@RequestMapping("/selections")
@Api(value = "选课信息的控制器", description = "Selection 的相关信息接口")
public class SelectionsController {

    @Resource
    private SelectionsServiceImpl selectionsService;
    @Resource
    private StudentsServiceImpl studentsService;
    @Resource
    private CoursesServiceImpl coursesService;

    @PostMapping("/add")
    @ResponseBody
    @ApiOperation(value = "增加一个新的选课记录")
    @ApiImplicitParam(paramType = "query", name = "selection", value = "选课信息的 json 字符串", required = true, dataType = "String")
    public Result add(@RequestParam String selection) {
        Selections selections = JSONObject.parseObject(selection, new TypeReference<Selections>() {
        });
        selectionsService.save(selections);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    @ResponseBody
    @ApiOperation(value = "根据课程编号删除选课信息")
    @ApiImplicitParam(paramType = "query", name = "id", value = "课程编号", required = true, dataType = "String")
    public Result delete(@RequestParam String id) {
        selectionsService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/deleteOne")
    @ResponseBody
    @ApiOperation(value = "删除一个选课记录")
    @ApiImplicitParam(paramType = "query", name = "selection", value = "选课信息的 json 字符串", required = true, dataType = "String")
    public Result deleteOne(@RequestParam String selection) {
        Selections selections = selectionsService.deleteOne(selection);
        return ResultGenerator.genSuccessResult(selection);
    }

    @PostMapping("/update")
    @ResponseBody
    @ApiOperation(value = "更新一个选课记录")
    @ApiImplicitParam(paramType = "query", name = "selection", value = "选课信息的 json 字符串", required = true, dataType = "String")
    public Result update(String selection) {
        Selections selections = JSONObject.parseObject(selection, new TypeReference<Selections>() {
        });
        selectionsService.update(selections);
        return ResultGenerator.genSuccessResult();
    }

//    @PostMapping("/detail")
//    public Result detail(@RequestParam String id) {
//        Selections selections = selectionsService.findBy("cid",id);
//        return ResultGenerator.genSuccessResult(selections);
//    }

    @GetMapping("/list")
    @ResponseBody
    @ApiOperation(value = "根据页数和每页的大小获得选课信息列表")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "page", value = "第几页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "每页中列表元素个数", required = true, dataType = "Integer")
    })
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Selections> list = selectionsService.findAll();
        PageInfo pageInfo = new PageInfo<>(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 根据学生的id 获得学生选课这个事件的信息
     *
     * @param id
     * @return
     */
    @GetMapping("/sid")
    @ResponseBody
    @ApiOperation(value = "根据学生的id 获得学生选课这个事件的信息")
    @ApiImplicitParam(paramType = "query", name = "id", value = "学生学号", required = true, dataType = "String")
    public Result sid(@RequestParam String id) {
        List<Selections> list = selectionsService.findCoursesBySid(id);
//        PageHelper.startPage(1, list.size());
//        PageInfo pageInfo = new PageInfo<>(list);
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 根据课程名称找到这个课程的被选中的情况
     *
     * @param name
     * @return
     */
    @GetMapping("/cname")
    @ResponseBody
    @ApiOperation(value = "根据课程的名称获得学生选课这个事件的信息")
    @ApiImplicitParam(paramType = "query", name = "name", value = "课程名称", required = true, dataType = "String")
    public Result cname(@RequestParam String name) {
        String id = coursesService.selectByName(name);
        List<Selections> selections = selectionsService.findCoursesByCid(id);
        return ResultGenerator.genSuccessResult(selections);
    }

    /**
     * 能够根据学生的 id 统计出该学生的平均成绩
     *
     * @param id
     * @return
     */
    @GetMapping("/savgscore")
    @ResponseBody
    @ApiOperation(value = "能够根据学生的 id 统计出该学生的平均加权成绩")
    @ApiImplicitParam(paramType = "query", name = "id", value = "学生学号", required = true, dataType = "String")
    public Result savgscore(@RequestParam String id) {

        //获得该学生的所有课程
        List<Selections> list = selectionsService.findCoursesBySid(id);

        double avgscore = 0; // 加权
        double sumcredit = 0; // 总学分
        // 遍历这个学生选的所有课程
        for (Selections selection : list) {
            // 找到每一门课对应的课程信息，拿到学分，计算加权
            Courses courses = coursesService.findById(selection.getCid());
            avgscore += selection.getScore() * courses.getCredit(); // 计算总成绩
            sumcredit = courses.getCredit(); // 计算总学分
        }
        avgscore = avgscore / sumcredit; // 平均加权
        return ResultGenerator.genSuccessResult(avgscore);
    }


    @GetMapping("/cid")
    @ResponseBody
    @ApiOperation(value = "根据课程的编号获得这个课程的选课信息")
    @ApiImplicitParam(paramType = "query", name = "id", value = "课程编号", required = true, dataType = "String")
    public Result cid(@RequestParam String id) {
        List<Selections> selections = selectionsService.findCoursesByCid(id);
        return ResultGenerator.genSuccessResult(selections);
    }

    @GetMapping("/sname")
    @ResponseBody
    @ApiOperation(value = "根据学生的名称获得学生选课这个事件的信息")
    @ApiImplicitParam(paramType = "query", name = "cname", value = "学生名称", required = true, dataType = "String")
    public Result sname(@RequestParam String name) {
        List<String> ids = studentsService.selectByName(name);
        PageHelper.startPage(1, ids.size());
        List<Selections> list = new ArrayList<>();
        for (String id : ids
        ) {
            list.add(selectionsService.findById(id));
        }
        PageInfo pageInfo = new PageInfo<>(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("/sidcid")
    @ResponseBody
    @ApiOperation(value = "根据学生的学号和课程的编号获得选课信息（包括成绩）")
    @ApiImplicitParams(
            {@ApiImplicitParam(paramType = "query", name = "sid", value = "学生学号", required = true, dataType = "String"),
                    @ApiImplicitParam(paramType = "query", name = "cid", value = "课程编号", required = true, dataType = "String")}
    )
    public Result sidcid(@RequestParam String sid, @RequestParam String cid) {
        List<Selections> selections = selectionsService.findBySD(sid, cid);
        return ResultGenerator.genSuccessResult(selections);
    }

    @GetMapping("/snamecid")
    @ResponseBody
    @ApiOperation(value = "根据学生的姓名和课程的编号获得选课信息（包括成绩）")
    @ApiImplicitParams(
            {@ApiImplicitParam(paramType = "query", name = "sname", value = "学生姓名", required = true, dataType = "String"),
                    @ApiImplicitParam(paramType = "query", name = "cid", value = "课程编号", required = true, dataType = "String")}
    )
    public Result snamecid(@RequestParam String sname, @RequestParam String cid) {
        List<String> ids = studentsService.selectByName(sname);
        PageHelper.startPage(1, ids.size());
        List<Selections> list = new ArrayList<>();
        for (String id : ids
        ) {
            list.addAll(selectionsService.findBySD(id, cid));
        }
        PageInfo pageInfo = new PageInfo<>(list);
        return ResultGenerator.genSuccessResult(list);
    }

    @GetMapping("/sidcname")
    @ResponseBody
    @ApiOperation(value = "根据学生的学号和课程的名称获得选课信息（包括成绩）")
    @ApiImplicitParams(
            {@ApiImplicitParam(paramType = "query", name = "sid", value = "学生学号", required = true, dataType = "String"),
                    @ApiImplicitParam(paramType = "query", name = "cname", value = "课程名称", required = true, dataType = "String")}
    )
    public Result sidcname(@RequestParam String sid, @RequestParam String cname) {
        String cid = coursesService.selectByName(cname);
        List<Selections> selections = selectionsService.findBySD(sid, cid);
        return ResultGenerator.genSuccessResult(selections);
    }

    @GetMapping("/snamecname")
    @ResponseBody
    @ApiOperation(value = "根据学生的姓名和课程的名称获得选课信息（包括成绩）")
    @ApiImplicitParams(
            {@ApiImplicitParam(paramType = "query", name = "sname", value = "学生姓名", required = true, dataType = "String"),
                    @ApiImplicitParam(paramType = "query", name = "cname", value = "课程名称", required = true, dataType = "String")}
    )
    public Result snamecname(@RequestParam String sname, @RequestParam String cname) {
        String cid = coursesService.selectByName(cname);
        List<String> ids = studentsService.selectByName(sname);
        PageHelper.startPage(1, ids.size());
        List<Selections> list = new ArrayList<>();
        for (String id : ids
        ) {
            list.addAll(selectionsService.findBySD(id, cid));
        }
        PageInfo pageInfo = new PageInfo<>(list);
        return ResultGenerator.genSuccessResult(list);
//        String cid = coursesService.selectByName(cname);
//        List<Selections> selections = selectionsService.findBySD(sid, cid);
//        return ResultGenerator.genSuccessResult(selections);
    }

//    @GetMapping("/selection")
//    public Result selection(@RequestParam String selection){
//        Selections selections = JSONObject.parseObject(selection,new TypeReference<Selections>(){});
//        List<Selections> selections = selectionsService.findBySD(sid,cid);
//        return ResultGenerator.genSuccessResult(selections);
//    }
}
