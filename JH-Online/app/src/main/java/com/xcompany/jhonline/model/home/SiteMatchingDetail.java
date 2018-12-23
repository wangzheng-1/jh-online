package com.xcompany.jhonline.model.home;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xieliang on 2018/12/19 10:01
 */
public class SiteMatchingDetail implements Serializable {

    private static final long serialVersionUID = -3260516040171907112L;

    /**
     * id : 155
     * name : 湖南卓为物流有限公司
     * explain : &lt;p&gt;&lt;span&gt;CCTV优选品牌，您值得拥有；“我的整车物流专员--卓为物流”&lt;/span&gt;&lt;br&gt;&lt;span&gt;承接4-17.5米普通车、特种车，全国整车物流货运、物流承包等&lt;/span&gt;&lt;br&gt;&lt;span&gt;人就该有所“卓为”！服务热线13106068662&lt;/span&gt;&lt;/p&gt;
     * linkman : 师傅
     * telephone : 13106068662
     * contacts_pid : 12
     * contacts_aid : 126
     * contacts_cid : 1406
     * image : https://www.jhzxnet.com/Uploads/Serve/images/2018-12-18/5c185954b53b9.png
     * class : 装载运输服务
     * contacts : 浙江省 衢州市市辖区
     * images : ["https://www.jhzxnet.com/Uploads/Serve/images/2018-12-18/5c185954b53b9.png"]
     */

    private String id;
    private String name;
    private String explain;
    private String linkman;
    private String telephone;
    private String contacts_pid;
    private String contacts_aid;
    private String contacts_cid;
    private String image;
    @SerializedName("class")
    private String classX;
    private String contacts;
    private List<String> images;

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

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getClassX() {
        return classX;
    }

    public void setClassX(String classX) {
        this.classX = classX;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
