package com.chenshuyusc.TJUstudents.model;

import com.chenshuyusc.TJUstudents.core.ResultGenerator;
import com.chenshuyusc.TJUstudents.service.impl.CoursesServiceImpl;
import com.chenshuyusc.TJUstudents.service.impl.SelectionsServiceImpl;
import com.chenshuyusc.TJUstudents.service.impl.StudentsServiceImpl;

import javax.annotation.Resource;
import javax.persistence.Id;
import java.util.*;

public class ClassInfo {

    @Id
    private List<Students> studentsList = new ArrayList<>();     // 班级内的所有学生

    private Map studentmap = new HashMap<Students, Double>();  //  以学生和学生对应的加权为键值对

    double avgScore; // 平均成绩


    public ClassInfo() {
    }

    public double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(double avgScore) {
        this.avgScore = avgScore;
    }

    public List<Students> getStudentsList() {
        return studentsList;
    }

    public void setStudentsList(List<Students> studentsList) {
        updateStudents(studentsList);
    }

    public Map getStudentmap() {
        return studentmap;
    }

    public void setStudentmap(Map studentmap) {
        this.studentmap = studentmap;
    }

    public ClassInfo(List<Students> studentsList) {
        this.studentsList = studentsList;

//        // 计算班级成绩的平均值
//        avgScore = 0;
//        for (Students student : studentsList) {
//            double temp = computStudentAvgScore(student.getSid());
//            avgScore += temp;
//            studentmap.put(student, temp);
//        }
//        avgScore = avgScore / studentsList.size();
    }

    /**
     * 增加一个学生
     * @param student 新加入的学生
     */
    public void addStudent(Students student) {
        studentsList.add(student);
       // studentmap.put(student, computStudentAvgScore(student.getSid()));
        //computeAvg();
    }

    /**
     * 加入新的学生，更新班级学生情况
     * @param studentsList 学生列表
     */
    public void updateStudents(List<Students> studentsList) {
        this.studentsList.clear();
        this.studentsList.addAll(studentsList);

//        // 更新 map
//        // 计算班级成绩的平均值
//        avgScore = 0;
//        studentmap.clear();
//        for (Students student : studentsList) {
//            double temp = computStudentAvgScore(student.getSid());
//            avgScore += temp;
//            studentmap.put(student, temp);
//        }
//        avgScore = avgScore / studentsList.size();
    }

    private void computeAvg() {
        // 先将原有的平均成绩清零
        avgScore = 0;
        Iterator it = studentmap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Students, Double> temp = (Map.Entry<Students, Double>) it.next();
            avgScore += temp.getValue();
        }
        avgScore = avgScore / studentmap.size();
    }

    /**
     * 能够根据学生的 id 获得该学生的平均加权
     *
     * @param id 学生的学号
     * @return
     */
//    private double computStudentAvgScore(String id) {
//        //获得该学生的所有课程
//        List<Selections> list = selectionsService.findCourses(id);
//
//        double avgscore = 0; // 加权
//        double sumcredit = 0; // 总学分
//        // 遍历这个学生选的所有课程
//        for (Selections selection : list) {
//            // 找到每一门课对应的课程信息，拿到学分，计算加权
//            Courses courses = coursesService.findById(selection.getCid());
//            avgscore += selection.getScore() * courses.getCredit(); // 计算总成绩
//            sumcredit = courses.getCredit(); // 计算总学分
//        }
//        avgscore = avgscore / sumcredit; // 平均加权
//        return avgscore;
//    }
}
