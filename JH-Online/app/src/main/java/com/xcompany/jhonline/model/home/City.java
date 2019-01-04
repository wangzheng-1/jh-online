package com.xcompany.jhonline.model.home;

import java.io.Serializable;

/**
 * Created by xieliang on 2018/12/15 19:31
 */
public class City implements Serializable {

    private static final long serialVersionUID = -5792940146291929878L;

    /**
     * id : 2829
     * name : 市辖区
     * pid : 271
     * grade : 2
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

    @Override
    public String toString() {
        return name;
    }
}
