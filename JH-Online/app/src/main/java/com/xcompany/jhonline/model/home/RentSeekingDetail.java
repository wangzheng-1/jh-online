package com.xcompany.jhonline.model.home;

import java.io.Serializable;

/**
 * Created by xieliang on 2018/12/20 00:16
 */
public class RentSeekingDetail implements Serializable {

    private static final long serialVersionUID = -8616740724435346674L;

    /**
     * id : 467
     * explain : &lt;p&gt;&lt;span&gt;挖土方需求租日立450、460、650大挖机回填工业园区、工期1年、&lt;/span&gt;&lt;/p&gt;
     * addtime : 1542120350
     * contacts_aid : 241
     * contacts_cid : 2519
     * contacts_pid : 20
     * telephone : 13828940649
     * linkman : 陈先生
     * deadline : 1549814400
     * type : 1
     * name : 汕尾求租460挖掘机
     * classname : 寻租信息
     * contacts : 广东省 汕尾市市辖区
     * entryTime : 2019年02月中旬
     */

    private String id;
    private String explain;
    private String addtime;
    private String contacts_aid;
    private String contacts_cid;
    private String contacts_pid;
    private String telephone;
    private String linkman;
    private String deadline;
    private String type;
    private String name;
    private String classname;
    private String contacts;
    private String entryTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
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
