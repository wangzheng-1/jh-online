package com.xcompany.jhonline.model.home;

import java.io.Serializable;

/**
 * Created by xieliang on 2018/12/16 13:20
 */
public class JobHunting implements Serializable {

    private static final long serialVersionUID = 536734297119219335L;

    /**
     * id : 1646
     * name : 钢筋工找活
     * addtime : 1544746753
     * cid : 钢筋工
     * type : 1
     * number : 7
     * image : https://www.jhzxnet.com/Public/org/image/hengselogo.png
     * telephone : 17797733452
     * contacts_pid : 20
     * contacts_aid : 229
     * contacts : 广东省 广州市
     * entryTime : 2018年12月14日
     */

    private String id;
    private String name;
    private String addtime;
    private String cid;
    private String type;
    private String number;
    private String image;
    private String telephone;
    private String contacts_pid;
    private String contacts_aid;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
