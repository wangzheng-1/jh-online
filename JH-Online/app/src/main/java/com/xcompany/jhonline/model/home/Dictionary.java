package com.xcompany.jhonline.model.home;

import java.io.Serializable;

/**
 * Created by xieliang on 2018/12/1 18:59
 */
public class Dictionary implements Serializable {

    private static final long serialVersionUID = -6977739487790460307L;

    public Dictionary(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * id : 612
     * name : 特项工程类
     * pid : 0
     * grade : 0
     */

    private String id;
    private String name;
    private String pid;
    private String grade;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

}
