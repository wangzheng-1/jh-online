package com.xcompany.jhonline.model.home;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xieliang on 2018/12/16 02:40
 */
public class HiringDetail implements Serializable {

    private static final long serialVersionUID = 1261206243038514775L;

    /**
     * id : 1709
     * name : 广东省广州黄埔区项目--招油漆工
     * addtime : 2018-12-15 18:25:16
     * type : 0
     * telephone : 18126449379
     * contacts_pid : 20
     * contacts_aid : 229
     * deadline : 2019.01.14
     * explain : &lt;p&gt;项目名称：广东省广州黄埔区项目&lt;/p&gt;&lt;p&gt;招聘需求：油漆工&lt;/p&gt;&lt;p&gt;人数：2人&lt;/p&gt;&lt;p&gt;联系方式:董工18126449379&lt;/p&gt;&lt;p&gt;项目地址：广东省广州黄埔区&lt;/p&gt;&lt;p&gt;备注：广州黄埔区招批灰油漆师傅二名，300一天，可做到年底。要求勤劳，技术好，能独立完成整个工序。有住工地，做完立马结账。中间可以借支。少数民族勿扰，带身份证。进场需持真实有效的身份证，人证信息一致才可进场，非诚勿扰！&lt;/p&gt;
     * linkman : 董工
     * contacts : 广东省 广州市
     * case : [{"classname":"油漆工","number":"2"}]
     */

    private String id;
    private String name;
    private String addtime;
    private String type;
    private String telephone;
    private String contacts_pid;
    private String contacts_aid;
    private String deadline;
    private String explain;
    private String linkman;
    private String contacts;
    @SerializedName("case")
    private List<CaseBean> caseX;

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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
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

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public List<CaseBean> getCaseX() {
        return caseX;
    }

    public void setCaseX(List<CaseBean> caseX) {
        this.caseX = caseX;
    }

    public static class CaseBean {
        /**
         * classname : 油漆工
         * number : 2
         */

        private String classname;
        private String number;

        public String getClassname() {
            return classname;
        }

        public void setClassname(String classname) {
            this.classname = classname;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
