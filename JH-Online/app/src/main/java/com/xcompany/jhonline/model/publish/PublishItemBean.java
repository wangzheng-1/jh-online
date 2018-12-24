package com.xcompany.jhonline.model.publish;

import com.xcompany.jhonline.model.base.Model;

import java.io.Serializable;

public class PublishItemBean extends Model implements Serializable {
    private static final long serialVersionUID = 7553074963902204865L;

    private String detailName;
    private Integer detailNum;

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public Integer getDetailNum() {
        return detailNum;
    }

    public void setDetailNum(Integer detailNum) {
        this.detailNum = detailNum;
    }

    public PublishItemBean() {

    }
    public PublishItemBean(String detailName) {
        this.detailName = detailName;

    }

    public PublishItemBean(String detailName, Integer detailNum) {
        this.detailName = detailName;
        this.detailNum = detailNum;
    }
}
