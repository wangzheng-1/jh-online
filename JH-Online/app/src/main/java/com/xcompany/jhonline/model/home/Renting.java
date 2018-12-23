package com.xcompany.jhonline.model.home;

import java.io.Serializable;

/**
 * Created by xieliang on 2018/12/19 23:18
 */
public class Renting implements Serializable {

    private static final long serialVersionUID = 7952576559876129587L;

    /**
     * id : 837
     * name : 兰州市徐工XS263单钢轮压路机
     * cid : 单钢轮压路机
     * grade : 5
     * telephone : 15253978277
     * contacts_pid : 29
     * contacts_aid : 334
     * type : 0
     * deadline : 0
     * authentication : 0
     * contacts : 甘肃省  兰州市
     */

    private String id;
    private String name;
    private String cid;
    private int grade;
    private String telephone;
    private String contacts_pid;
    private String contacts_aid;
    private String type;
    private String deadline;
    private String authentication;
    private String contacts;

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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getContacts_pid() {
        return contacts_pid;
    }

    public void setContacts_pid(String contacts_pid) {
        this.contacts_pid = contacts_pid;
    }

    public String getContacts_aid() {
        return contacts_aid;
    }

    public void setContacts_aid(String contacts_aid) {
        this.contacts_aid = contacts_aid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }
}
