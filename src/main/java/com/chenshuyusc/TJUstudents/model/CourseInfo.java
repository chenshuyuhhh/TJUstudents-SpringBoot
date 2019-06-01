package com.chenshuyusc.TJUstudents.model;

import sun.lwawt.macosx.CInputMethod;

import javax.persistence.Id;

public class CourseInfo extends Courses {
    @Id
    private Integer under60;

    private Integer b6and7;

    private Integer b7and8;

    private Integer b8and9;

    private Integer b9and10;

    private Integer full;

    private double avg;

    private int num;

    public CourseInfo() {

    }

    public CourseInfo(Courses courses, Integer under60, Integer b6and7, Integer b7and8, Integer b8and9, Integer b9and10, Integer full, Double avg,Integer num) {
        super(courses.getCid(), courses.getCname(), courses.getTeacher(), courses.getCredit(), courses.getSuitgrade(), courses.getCancelyear());
        this.under60 = under60;
        this.b6and7 = b6and7;
        this.b7and8 = b7and8;
        this.b8and9 = b8and9;
        this.b9and10 = b9and10;
        this.full = full;
        this.avg = avg;
        this.num = num;
    }

    public double getAvg() {
        return avg;
    }

    public Integer getB6and7() {
        return b6and7;
    }

    public Integer getB7and8() {
        return b7and8;
    }

    public Integer getB8and9() {
        return b8and9;
    }

    public Integer getB9and10() {
        return b9and10;
    }

    public Integer getUnder60() {
        return under60;
    }

    public Integer getFull() {
        return full;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public void setB6and7(Integer b6and7) {
        this.b6and7 = b6and7;
    }

    public void setB7and8(Integer b7and8) {
        this.b7and8 = b7and8;
    }

    public void setB8and9(Integer b8and9) {
        this.b8and9 = b8and9;
    }

    public void setB9and10(Integer b9and10) {
        this.b9and10 = b9and10;
    }

    public void setFull(Integer full) {
        this.full = full;
    }

    public void setUnder60(Integer under60) {
        this.under60 = under60;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
