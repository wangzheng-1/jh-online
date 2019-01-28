package com.xcompany.jhonline.model.home;

import java.io.Serializable;

/**
 * Created by xieliang on 2018/12/22 23:01
 */
public class PurchaseDetail implements Serializable {

    private static final long serialVersionUID = -8074328586021883186L;

    /**
     * id : 8
     * name : 采购铜螺母
     * contacts_pid : 20
     * explain : &lt;p&gt;订购量: 5万；付款方式：签定合同后付总额30%定金，货款批交批结生产地提货；交货期：240-300天；现需长期&lt;a href=&quot;http://www.56ye.net/caigou/&quot; target=&quot;_blank&quot;&gt;&lt;strong class=&quot;keylink&quot;&gt;采购&lt;/strong&gt;&lt;/a&gt;寻求有实力，有供货能力的厂家长期合作（中间商勿扰），在价格上双方互惠互利，给我方最优惠的价格，如您愿意和我们合作，请您务必及时和我们取得电话联系&lt;/p&gt;
     * telephone : 15113602341
     * linkman : 王岳
     * contacts : 广东省 . 湛江市
     */

    private String id;
    private String name;
    private String contacts_pid;
    private String explain;
    private String telephone;
    private String linkman;
    private String contacts;
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

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
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

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }
}
