package com.xcompany.jhonline.model.home;

import java.io.Serializable;

/**
 * Created by xieliang on 2018/12/23 00:11
 */
public class ProfessionalSkills implements Serializable {

    private static final long serialVersionUID = 6270828565223252135L;

    /**
     * id : 132
     * contacts_aid : 196
     * contacts_pid : 17
     * telephone : 15038442127
     * name : 专业承接：机钻孔，拆墙，打爆膜
     * classname : 专业打爆模
     * area : 河南省
     * city : 商丘市
     */

    private String id;
    private String contacts_aid;
    private String contacts_pid;
    private String telephone;
    private String name;
    private String classname;
    private String area;
    private String city;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContacts_aid() {
        return contacts_aid;
    }

    public void setContacts_aid(String contacts_aid) {
        this.contacts_aid = contacts_aid;
    }

    public String getContacts_pid() {
        return contacts_pid;
    }

    public void setContacts_pid(String contacts_pid) {
        this.contacts_pid = contacts_pid;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
