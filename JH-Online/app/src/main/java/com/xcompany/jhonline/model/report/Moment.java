package com.xcompany.jhonline.model.report;

import android.os.Parcel;
import android.os.Parcelable;

import com.xcompany.jhonline.model.base.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 汪正  爆料
 */
public class Moment extends Model implements Serializable {
    private static final long serialVersionUID = 7553074963902204865L;


    /**
     *  信息ID
     */
    private String id;

    /**
     *  说明
     */
    private String business;

    /**
     *  文件列表
     */
    private List<String> choosefile;

    /**
     *  文件格式
     */
    private String ext;

    /**
     *  点赞数
     */
    private String give;

    /**
     *  端口
     */
    private String port;

    /**
     *  发表时间
     */
    private String addtime;

    /**
     *  评论
     */
    private List<Comment> make;

    /**
     *  是否关注  1：已关注  2：未关注
     */
    private Integer follow;

    /**
     *  是否关注  1：已关注  2：未关注
     */
    private Integer givelist;


    /**
     *  是否关注  1：已点赞  2：未点赞
     */
    private Integer thumbs;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public List<String> getChoosefile() {
        return choosefile;
    }

    public void setChoosefile(List<String> choosefile) {
        this.choosefile = choosefile;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getGive() {
        return give;
    }

    public void setGive(String give) {
        this.give = give;
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

    public List<Comment> getMake() {
        return make;
    }

    public void setMake(List<Comment> make) {
        this.make = make;
    }

    public Integer getGivelist() {
        return givelist;
    }

    public void setGivelist(Integer givelist) {
        this.givelist = givelist;
    }

    public Integer getThumbs() {
        return thumbs;
    }

    public void setThumbs(Integer thumbs) {
        this.thumbs = thumbs;
    }

    public Integer getFollow() {
        return follow;
    }

    public void setFollow(Integer follow) {
        this.follow = follow;
    }
}