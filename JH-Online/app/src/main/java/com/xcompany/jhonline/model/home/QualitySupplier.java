package com.xcompany.jhonline.model.home;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xieliang on 2018/12/22 22:06
 */
public class QualitySupplier implements Serializable {

    private static final long serialVersionUID = 8070337885212339661L;

    /**
     * id : 6
     * name : 致和电线电缆有限公司
     * cid : 电线
     * shop : ["https://www.jhzxnet.com/Uploads/Supplier/images/2018-10-26/5bd2c0e65f165.png"]
     * telephone : 15089347707
     * contacts_aid : 239
     * classname : 电线电缆
     */

    private String id;
    private String name;
    private String cid;
    private String telephone;
    private String contacts_aid;
    private String classname;
    private List<String> shop;

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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getContacts_aid() {
        return contacts_aid;
    }

    public void setContacts_aid(String contacts_aid) {
        this.contacts_aid = contacts_aid;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public List<String> getShop() {
        return shop;
    }

    public void setShop(List<String> shop) {
        this.shop = shop;
    }
}
