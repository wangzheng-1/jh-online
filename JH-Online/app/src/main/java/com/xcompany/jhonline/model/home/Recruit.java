package com.xcompany.jhonline.model.home;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by xieliang on 2018/12/23 00:42
 */
public class Recruit implements Serializable {

    private static final long serialVersionUID = -4015764934653579219L;

    /**
     * id : 740
     * title : 广东省惠州市项目-招安全员
     * name : 私人老板
     * cid : 安全员
     * class :
     * contacts_pid : 20
     * contacts_aid : 239
     * number : 10
     * linkman : 	张总
     * telephone : 13617887777
     * addtime : 1545395719
     * area : 广东省
     * city : 惠州市
     * entryTime : 12月上旬
     */

    private String id;
    private String title;
    private String name;
    private String cid;
    @SerializedName("class")
    private String classX;
    private String contacts_pid;
    private String contacts_aid;
    private String number;
    private String linkman;
    private String telephone;
    private String addtime;
    private String area;
    private String city;
    private String entryTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getClassX() {
        return classX;
    }

    public void setClassX(String classX) {
        this.classX = classX;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
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

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }
}
