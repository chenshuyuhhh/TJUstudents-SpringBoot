package com.chenshuyusc.TJUstudents.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chenshuyusc.TJUstudents.dao.SelectionsMapper;
import com.chenshuyusc.TJUstudents.model.Selections;
import com.chenshuyusc.TJUstudents.model.Students;
import com.chenshuyusc.TJUstudents.service.SelectionsService;
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
public class SelectionsServiceImpl extends AbstractService<Selections> implements SelectionsService {
    @Resource
    private SelectionsMapper selectionsMapper;

    /**
     * 能够根据学生的 sid ，找出这个学生所选所有课程，以数组形式返回
     * @param sid
     * @return
     */
    public List<Selections> findCoursesBySid(String sid){
        Selections temp = new Selections();
        temp.setSid(sid);
        List<Selections> list = selectionsMapper.select(temp);
        return list;
    }

    public List<Selections> findCoursesByCid(String cid){
        Selections temp = new Selections();
        temp.setCid(cid);
        List<Selections> list = selectionsMapper.select(temp);
        return list;
    }

    public List<Selections> findBySD(String sid,String cid){
        Selections temp = new Selections();
        temp.setCid(cid);
        temp.setSid(sid);
        List<Selections> list = selectionsMapper.select(temp);
        return list;
    }

    public Selections deleteOne(String selection){
        Selections selections = JSONObject.parseObject(selection,new TypeReference<Selections>(){});
        selectionsMapper.delete(selections);
        return selections;
    }
}
