package com.chenshuyusc.TJUstudents.model;

import javax.persistence.Id;

public class SelectCourse {
    @Id
    private String cid;

    private String cname;

    private String teacher;

    private Double credit;

    private Integer suitgrade;

    private Integer cancelyear;

    private Integer year;

    private Integer score;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Integer getCancelyear() {
        return cancelyear;
    }

    public void setCancelyear(Integer cancelyear) {
        this.cancelyear = cancelyear;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Integer getSuitgrade() {
        return suitgrade;
    }

    public void setSuitgrade(Integer suitgrade) {
        this.suitgrade = suitgrade;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
