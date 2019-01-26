package com.xcompany.jhonline.model.home;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xieliang on 2018/12/22 22:28
 */
public class QualitySupplierDetail implements Serializable {

    private static final long serialVersionUID = -8026779055877964488L;

    /**
     * id : 6
     * contacts_aid : 239
     * contacts_cid : 2504
     * contacts_pid : 20
     * delivery : 0
     * door : 0
     * addtime : 1540538637
     * explain : &lt;p&gt;&lt;span&gt;如果您需要的生产型，找致和电线电缆有限公司就可以了，致和电线电缆有限公司以专业的服务、精良的技术（设备）为您提供一流的生产型服务。致和电线电缆有限公司拥有丰富的生产型经验，注册资本达1000万人民币元拥有较为雄厚的行业实力，立足于惠州为您提供专业的服务。拿起电话，交个诚意的朋友，做桩稳赚的生意。&lt;/span&gt;&lt;/p&gt;
     * imager : ["https://www.jhzxnet.com/Uploads/Supplier/images/2018-10-26/5bd2c0ca7044a.png"]
     * linkman : 杨泽建
     * loan : 0
     * name : 致和电线电缆有限公司
     * telephone : 15089347707
     * classname : 电线电缆
     * contacts : 广东省 惠州市市辖区
     * entryTime : 2018.10.26
     * case : [{"cid":"电线","imager":"https://www.jhzxnet.com/Uploads/Supplier/images/2018-10-26/5bd2c0ca7044a.png"}]
     */

    private String id;
    private String contacts_aid;
    private String contacts_cid;
    private String contacts_pid;
    private String delivery;
    private String door;
    private String addtime;
    private String explain;
    private String linkman;
    private String loan;
    private String name;
    private String telephone;
    private String classname;
    private String contacts;
    private String entryTime;
    private List<String> imager;
    @SerializedName("case")
    private List<CaseBean> caseX;
    private String sign;
    private String issue;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getDoor() {
        return door;
    }

    public void setDoor(String door) {
        this.door = door;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
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

    public String getLoan() {
        return loan;
    }

    public void setLoan(String loan) {
        this.loan = loan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public List<String> getImager() {
        return imager;
    }

    public void setImager(List<String> imager) {
        this.imager = imager;
    }

    public List<CaseBean> getCaseX() {
        return caseX;
    }

    public void setCaseX(List<CaseBean> caseX) {
        this.caseX = caseX;
    }

    public static class CaseBean {
        /**
         * cid : 电线
         * imager : https://www.jhzxnet.com/Uploads/Supplier/images/2018-10-26/5bd2c0ca7044a.png
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
