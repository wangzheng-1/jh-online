package com.xcompany.jhonline.model.home;

import java.io.Serializable;

/**
 * Created by xieliang on 2018/12/22 22:48
 */
public class Purchase implements Serializable {

    private static final long serialVersionUID = 3950767678075452980L;

    /**
     * id : 10
     * name : 绿花土
     * contacts_pid : 20
     * telephone : 13826571387
     * addtime : 1543886601
     * contacts : 广东省 . 深圳市
     * entryTime : 2018.12.04
     */

    private String id;
    private String name;
    private String contacts_pid;
    private String telephone;
    private String addtime;
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

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
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
