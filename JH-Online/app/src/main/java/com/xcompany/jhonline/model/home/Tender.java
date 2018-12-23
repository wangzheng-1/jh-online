package com.xcompany.jhonline.model.home;

import java.io.Serializable;

/**
 * Created by xieliang on 2018/12/22 20:42
 */
public class Tender implements Serializable {

    private static final long serialVersionUID = 3320533353928248646L;

    /**
     * id : 2
     * name : 团风县马曹庙镇百沙河片区综合治理工程
     * company : 深圳利万众建筑信息咨询服务有限公司
     * inventory : 水电班组
     * authentication : 0
     * addtime : 1545010166
     * classname : 分项劳务类
     * entryTime : 2018年12月中旬
     */

    private String id;
    private String name;
    private String company;
    private String inventory;
    private String authentication;
    private String addtime;
    private String classname;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }
}
