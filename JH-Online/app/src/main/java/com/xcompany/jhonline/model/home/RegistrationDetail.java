package com.xcompany.jhonline.model.home;

import java.io.Serializable;

/**
 * Created by xieliang on 2018/12/22 23:46
 */
public class RegistrationDetail implements Serializable {

    private static final long serialVersionUID = -2135528852828559739L;

    /**
     * id : 1214
     * contacts_aid : 215
     * contacts_cid : 2269
     * contacts_pid : 19
     * deadline : 1553184000
     * image : 0
     * linkman :  庞工
     * telephone : 13728777959
     * type : 0
     * name : 湖南单位急聘一级机电带B挂资质
     * explain : &lt;p&gt;&lt;span&gt;湖南单位急聘一级机电带B证挂资质，全国证都可。湖南单位急聘一级机电带B证挂资质，全国证都可。湖南单位急聘一级机电带B证挂资质，全国证都可。湖南单位急聘一级机电带B证挂资质，全国证都可。湖南单位急聘一级机电带B证挂资质，全国证都可。&lt;/span&gt;&lt;/p&gt;
     * contacts : 湖南省 长沙市市辖区
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
    private String explain;
    private String contacts;
    private String entryTime;
    private String sign;
    private String issue;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

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

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
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
