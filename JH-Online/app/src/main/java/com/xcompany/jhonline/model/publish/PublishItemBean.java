package com.xcompany.jhonline.model.publish;

import com.xcompany.jhonline.model.base.Model;

import java.io.Serializable;

public class PublishItemBean extends Model implements Serializable {
    private static final long serialVersionUID = 7553074963902204865L;

    private String  id;

    private String name;

    private String linkman;

    private Integer status;

    private String addtime;


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

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }
}
