package com.xcompany.jhonline.model.report;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/14.
 */

public class MediaBaseBean implements Serializable{

    private static final long serialVersionUID = 5213230387175987834L;


    private String url;
    private boolean isvideo;
    private String videosize;
    private Long createTime;
    private boolean isCheckBox=false;

    public boolean isCheckBox() {
        return isCheckBox;
    }

    public void setCheckBox(boolean checkBox) {
        isCheckBox = checkBox;
    }

    public String getVideosize() {
        return videosize;
    }

    public void setVideosize(String videosize) {
        this.videosize = videosize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isvideo() {
        return isvideo;
    }

    public void setIsvideo(boolean isvideo) {
        this.isvideo = isvideo;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }


}
