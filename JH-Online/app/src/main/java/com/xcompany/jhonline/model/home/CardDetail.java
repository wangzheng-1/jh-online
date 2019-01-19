package com.xcompany.jhonline.model.home;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xieliang on 2019/1/20 00:17
 */
public class CardDetail implements Serializable {

    private static final long serialVersionUID = -799681704874038756L;

    /**
     * id : 4
     * name : 熊大大
     * company : 熊大大有限责任公司
     * telephone : 15220689930
     * contacts_pid : 20
     * contacts_aid : 231
     * contacts_cid : 2448
     * email : 847120432@qq.com
     * business : 测试结果表明模式开咯技术人员参考资料无忧保姆网女坡生活水平提高警惕这泼猴问题是因为他们对你是什么意思聚碳酸酯材质比较
     * uid : 28
     * image : /Uploads//images/2018-12-25/5c217fdcd9d9a.jpg
     * contacts : ["广东省","深圳市","宝安区"]
     */

    private String id;
    private String name;
    private String company;
    private String telephone;
    private String contacts_pid;
    private String contacts_aid;
    private String contacts_cid;
    private String email;
    private String business;
    private String uid;
    private String image;
    private List<String> contacts;

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

    public String getContacts_cid() {
        return contacts_cid;
    }

    public void setContacts_cid(String contacts_cid) {
        this.contacts_cid = contacts_cid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getContacts() {
        return contacts;
    }

    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }
}
