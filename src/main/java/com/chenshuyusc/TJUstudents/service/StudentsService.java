package com.chenshuyusc.TJUstudents.service;
import com.chenshuyusc.TJUstudents.model.Students;
import com.chenshuyusc.TJUstudents.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/04/29.
 */
public interface StudentsService extends Service<Students> {

    List<String> selectByName(String name);

    List<Students> selectByClass(String classname,Integer year);
}
