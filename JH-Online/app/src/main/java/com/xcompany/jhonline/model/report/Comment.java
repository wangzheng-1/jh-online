package com.xcompany.jhonline.model.report;

import com.xcompany.jhonline.model.base.Model;

import java.io.Serializable;
import java.util.List;

/**
 * 汪正  爆料
 */
public class Comment extends Model implements Serializable {
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
     *  爆料数据id
     */
    private String fid;

    /**
     *  评论说明
     */
    private String business;

    /**
     *  端口
     */
    private String port;

    /**
     *  发表时间
     */
    private String addtime;

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

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }
}