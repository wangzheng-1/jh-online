package com.xcompany.jhonline.model.base;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by xieliang on 2018/12/16 13:55
 */
public class Category extends Model implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(name, category.name) &&
                Objects.equals(pid, category.pid) &&
                Objects.equals(grade, category.grade);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, pid, grade);
    }
}
