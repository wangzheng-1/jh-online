package com.xcompany.jhonline.model.home;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xieliang on 2018/12/19 23:43
 */
public class RentingDetail implements Serializable {

    private static final long serialVersionUID = -8864725768721497939L;

    /**
     * id : 477
     * explain : &lt;p&gt;&lt;span&gt;长期出租大小叉车&lt;/span&gt;&lt;/p&gt;
     * addtime : 1542169973
     * contacts_aid : 239
     * contacts_cid : 2504
     * contacts_pid : 20
     * telephone : 13725002883
     * linkman : 蒋明华
     * deadline : 0
     * type : 0
     * name : 惠州市宝骊7T叉车
     * classname : 叉车
     * contacts : 广东省 惠州市市辖区
     * entryTime : 2018.11.14
     * case : [{"cid":"叉车","imager":"https://www.jhzxnet.com/Uploads/Lease/images/2018-11-14/5beba558317d4.jpg"}]
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
    @SerializedName("case")
    private List<CaseBean> caseX;

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

    public List<CaseBean> getCaseX() {
        return caseX;
    }

    public void setCaseX(List<CaseBean> caseX) {
        this.caseX = caseX;
    }

    public static class CaseBean {
        /**
         * cid : 叉车
         * imager : https://www.jhzxnet.com/Uploads/Lease/images/2018-11-14/5beba558317d4.jpg
         */

        private String cid;
        private String imager;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getImager() {
            return imager;
        }

        public void setImager(String imager) {
            this.imager = imager;
        }
    }
}
