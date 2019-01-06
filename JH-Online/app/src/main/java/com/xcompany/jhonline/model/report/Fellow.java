package com.xcompany.jhonline.model.report;

import com.xcompany.jhonline.model.base.Model;

import java.io.Serializable;

/**
 * 汪正  爆料
 */
public class Fellow extends Model implements Serializable {
    private static final long serialVersionUID = 7553074963902204865L;


    /**
     *  信息ID
     */
    private String id;

    /**
     * 评论者ID
     */
    private String uid;

    /**
     *  类型  0：点赞  1：关注
     */
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}