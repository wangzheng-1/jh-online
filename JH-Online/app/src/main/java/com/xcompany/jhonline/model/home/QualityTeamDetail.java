package com.xcompany.jhonline.model.home;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xieliang on 2018/12/16 23:04
 */
public class QualityTeamDetail implements Serializable {

    private static final long serialVersionUID = 4461999487024508152L;

    /**
     * id : 10
     * name : 建春施工队
     * explain : 本施工队入驻惠州12年，专业承接档口、酒店、别墅等各种房型的改建、加建。定制楼板楼梯。每年都要在惠州惠城、惠阳、大亚湾等地区承接众多此类项目。本施工队人手充足，可包工包料。可按业主要求灵活变动施工计划。价格公道 质量经得起考验。是本地区该类服务口碑甚好的承建商。欢迎各位朋友来电咨询。
     * contacts_pid : 20
     * contacts_aid : 239
     * contacts_cid : 2505
     * service : 广东省
     * inventory : 护坡/档口
     * idea : 诚信合作 质量取胜
     * enounce :
     * pay : 0.00
     * linkman : 曹建春
     * telephone : 13680823257
     * classname :  专业技术类
     * contacts : 广东省 惠州市惠城区
     * case : [{"entry":"惠州凯旋城项目","illustrate":"该项目主要是对超高层加隔层","imagerList":["https://www.jhzxnet.com/Uploads/Build/images/2018-10-19/5bc930a363e53.jpg","https://www.jhzxnet.com/Uploads/Build/images/2018-10-19/5bc930a38db57.jpg","https://www.jhzxnet.com/Uploads/Build/images/2018-10-19/5bc930a3a36ff.jpg","https://www.jhzxnet.com/Uploads/Build/images/2018-10-19/5bc930a3b2e97.jpg"],"imager":"https://www.jhzxnet.com/Uploads/Build/images/2018-10-19/5bc930a363e53.jpg"},{"entry":"惠阳雅居乐2标护坡工程","illustrate":"此项目是泥木铁一起","imagerList":["https://www.jhzxnet.com/Uploads/Build/images/2018-10-19/5bc930b21e2fa.jpg","https://www.jhzxnet.com/Uploads/Build/images/2018-10-19/5bc930b24d824.jpg","https://www.jhzxnet.com/Uploads/Build/images/2018-10-19/5bc930b278f9d.jpg"],"imager":"https://www.jhzxnet.com/Uploads/Build/images/2018-10-19/5bc930b21e2fa.jpg"},{"entry":"惠阳缘水岸","illustrate":"此地区是别墅区   我们做了其中几栋别墅的改建","imagerList":["https://www.jhzxnet.com/Uploads/Build/images/2018-10-19/5bc930bd8bc62.jpg","https://www.jhzxnet.com/Uploads/Build/images/2018-10-19/5bc930bdadf4e.jpg"],"imager":"https://www.jhzxnet.com/Uploads/Build/images/2018-10-19/5bc930bd8bc62.jpg"}]
     */

    private String id;
    private String name;
    private String explain;
    private String contacts_pid;
    private String contacts_aid;
    private String contacts_cid;
    private String service;
    private String inventory;
    private String idea;
    private String enounce;
    private String pay;
    private String linkman;
    private String telephone;
    private String classname;
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

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getIdea() {
        return idea;
    }

    public void setIdea(String idea) {
        this.idea = idea;
    }

    public String getEnounce() {
        return enounce;
    }

    public void setEnounce(String enounce) {
        this.enounce = enounce;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
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

    public List<CaseBean> getCaseX() {
        return caseX;
    }

    public void setCaseX(List<CaseBean> caseX) {
        this.caseX = caseX;
    }

    public static class CaseBean {
        /**
         * entry : 惠州凯旋城项目
         * illustrate : 该项目主要是对超高层加隔层
         * imagerList : ["https://www.jhzxnet.com/Uploads/Build/images/2018-10-19/5bc930a363e53.jpg","https://www.jhzxnet.com/Uploads/Build/images/2018-10-19/5bc930a38db57.jpg","https://www.jhzxnet.com/Uploads/Build/images/2018-10-19/5bc930a3a36ff.jpg","https://www.jhzxnet.com/Uploads/Build/images/2018-10-19/5bc930a3b2e97.jpg"]
         * imager : https://www.jhzxnet.com/Uploads/Build/images/2018-10-19/5bc930a363e53.jpg
         */

        private String entry;
        private String illustrate;
        private String imager;
        private List<String> imagerList;

        public String getEntry() {
            return entry;
        }

        public void setEntry(String entry) {
            this.entry = entry;
        }

        public String getIllustrate() {
            return illustrate;
        }

        public void setIllustrate(String illustrate) {
            this.illustrate = illustrate;
        }

        public String getImager() {
            return imager;
        }

        public void setImager(String imager) {
            this.imager = imager;
        }

        public List<String> getImagerList() {
            return imagerList;
        }

        public void setImagerList(List<String> imagerList) {
            this.imagerList = imagerList;
        }
    }
}
