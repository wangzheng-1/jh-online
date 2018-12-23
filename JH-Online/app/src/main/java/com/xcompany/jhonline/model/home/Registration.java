package com.xcompany.jhonline.model.home;

import java.io.Serializable;

/**
 * Created by xieliang on 2018/12/22 23:18
 */
public class Registration implements Serializable {

    private static final long serialVersionUID = -1525115430400467111L;

    /**
     * id : 1223
     * contacts_aid : 106
     * contacts_cid : 1217
     * contacts_pid : 11
     * deadline : 1553184000
     * image : 0
     * linkman :  王经理
     * telephone : 18795868622
     * type : 0
     * name : 注册二级结构江苏企业诚聘，见证全款
     * price : 0.00
     * classname : 寻证挂靠
     * contacts : 江苏省 南京市市辖区
     * entryTime : 2019.03.22
     */

    private String id;
    private String contacts_aid;
    private String contacts_cid;
    private String contacts_pid;
    private String deadline;
    private String image;
    private String linkman;
    private String telephone;
    private String type;
    private String name;
    private String price;
    private String classname;
    private String contacts;
    private String entryTime;

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

    public String getContacts_cid() {
        return contacts_cid;
    }

    public void setContacts_cid(String contacts_cid) {
        this.contacts_cid = contacts_cid;
    }

    public String getContacts_pid() {
        return contacts_pid;
    }

    public void setContacts_pid(String contacts_pid) {
        this.contacts_pid = contacts_pid;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }
}
