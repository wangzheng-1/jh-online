package com.xcompany.jhonline.model.me;

import com.xcompany.jhonline.model.base.Model;

import java.io.Serializable;

public class MeCreditDetailBean extends Model implements Serializable {
    private static final long serialVersionUID = 7553074963902204865L;


    private Integer id;

    private Integer optxt;

    private Integer sign;

    private String uid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOptxt() {
        return optxt;
    }

    public void setOptxt(Integer optxt) {
        this.optxt = optxt;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
