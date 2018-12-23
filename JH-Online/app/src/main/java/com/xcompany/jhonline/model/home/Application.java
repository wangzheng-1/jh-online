package com.xcompany.jhonline.model.home;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xieliang on 2018/12/23 01:00
 */
public class Application implements Serializable {

    private static final long serialVersionUID = -3855670124692067138L;

    /**
     * id : 4
     * name : 木工师傅
     * image : https://www.jhzxnet.com/Uploads/Jop/images/2018-10-24/5bcffba53c4b6.jpg
     * education : 1
     * duration : 2
     * cid : 其他
     * class : 木工师傅
     * contacts_pid : 20
     * contacts_aid : 231
     * price : 0.00
     * project : ["深圳碧桂园"]
     * linkman : 先生
     * telephone : 13265718388
     * area : 广东省
     * city : 深圳市
     */

    private String id;
    private String name;
    private String image;
    private String education;
    private String duration;
    private String cid;
    @SerializedName("class")
    private String classX;
    private String contacts_pid;
    private String contacts_aid;
    private String price;
    private String linkman;
    private String telephone;
    private String area;
    private String city;
    private List<String> project;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public List<String> getProject() {
        return project;
    }

    public void setProject(List<String> project) {
        this.project = project;
    }
}
