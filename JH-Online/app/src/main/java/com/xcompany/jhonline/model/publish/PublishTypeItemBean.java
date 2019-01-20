package com.xcompany.jhonline.model.publish;

import com.xcompany.jhonline.model.base.Model;

import java.io.Serializable;

public class PublishTypeItemBean extends Model implements Serializable {
    private static final long serialVersionUID = 7553074963902204865L;

    private Integer type;

    private PublishItemBean list;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public PublishItemBean getList() {
        return list;
    }

    public void setList(PublishItemBean list) {
        this.list = list;
    }
}
