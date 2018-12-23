package com.xcompany.jhonline.model.home;

import java.io.Serializable;

/**
 * Created by xieliang on 2018/12/16 10:31
 */
public class Hiring implements Serializable {

    private static final long serialVersionUID = 737622237416973221L;

    /**
     * id : 1708
     * name : 广东省惠州市项目--招塔吊司机
     * addtime : 1544869484
     * cid : 塔司信号工
     * type : 0
     * telephone : 18126449379
     * contacts_pid : 20
     * contacts_aid : 239
     * contacts : 广东省 惠州市
     * entryTime : 2018年12月15日
     */

    private String id;
    private String name;
    private String addtime;
    private String cid;
    private String type;
    private String telephone;
    private String contacts_pid;
    private String contacts_aid;
    private String contacts;
    private String entryTime;

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
