package com.chenshuyusc.TJUstudents.model;

import javax.persistence.*;

public class Courses {
    @Id
    private String cid;

    private String cname;

    private String teacher;

    private Double credit;

    private Integer suitgrade;

    private Integer cancelyear;

    public Courses() {

    }

    public Courses(String cid, String cname, String teacher, Double credit, Integer suitgrade, Integer cancelyear) {
        this.cid = cid;
        this.cname = cname;
        this.teacher = teacher;
        this.credit = credit;
        this.suitgrade = suitgrade;
        this.cancelyear = cancelyear;
    }

    /**
     * @return cid
     */
    public String getCid() {
        return cid;
    }

    /**
     * @param cid
     */
    public void setCid(String cid) {
        this.cid = cid;
    }

    /**
     * @return cname
     */
    public String getCname() {
        return cname;
    }

    /**
     * @param cname
     */
    public void setCname(String cname) {
        this.cname = cname;
    }

    /**
     * @return teacher
     */
    public String getTeacher() {
        return teacher;
    }

    /**
     * @param teacher
     */
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    /**
     * @return credit
     */
    public Double getCredit() {
        return credit;
    }

    /**
     * @param credit
     */
    public void setCredit(Double credit) {
        this.credit = credit;
    }

    /**
     * @return suitgrade
     */
    public Integer getSuitgrade() {
        return suitgrade;
    }

    /**
     * @param suitgrade
     */
    public void setSuitgrade(Integer suitgrade) {
        this.suitgrade = suitgrade;
    }

    /**
     * @return cancelyear
     */
    public Integer getCancelyear() {
        return cancelyear;
    }

    /**
     * @param cancelyear
     */
    public void setCancelyear(Integer cancelyear) {
        this.cancelyear = cancelyear;
    }
}