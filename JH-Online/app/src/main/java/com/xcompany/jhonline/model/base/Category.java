package com.xcompany.jhonline.model.base;

import java.io.Serializable;

/**
 * Created by xieliang on 2018/12/16 13:55
 */
public class Category implements Serializable {

    private static final long serialVersionUID = 1141917308544860455L;

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
