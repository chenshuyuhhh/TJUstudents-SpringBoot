package com.chenshuyusc.TJUstudents.model;

import javax.persistence.Id;

public class StudentInfo {
    @Id
    private String sid;

    private String sname;

    private String gender;

    private Integer age;

    private Integer year;

    private String classname;

    private double avgScore;

    /**
     * @return sid
     */
    public String getSid() {
        return sid;
    }

    /**
     * @param sid
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * @return sname
     */
    public String getSname() {
        return sname;
    }

    /**
     * @param sname
     */
    public void setSname(String sname) {
        this.sname = sname;
    }

    /**
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @return year
     */
    public Integer getYear() {
        return year;
    }

    /**
     *
     * @return avgScore
     */
    public double getAvgScore() {
        return avgScore;
    }

    /**
     * @param year
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * @return classname
     */
    public String getClassname() {
        return classname;
    }

    /**
     * @param classname
     */
    public void setClassname(String classname) {
        this.classname = classname;
    }

    /**
     *
     * @param avgScore
     */
    public void setAvgScore(double avgScore) {
        this.avgScore = avgScore;
    }

    public StudentInfo(Students students,double avgScore){
        this.age = students.getAge();
        this.classname = students.getClassname();
        this.sid = students.getSid();
        this.sname = students.getSname();
        this.gender = students.getGender();
        this.year = students.getYear();
        this.avgScore = avgScore;
    }
}
