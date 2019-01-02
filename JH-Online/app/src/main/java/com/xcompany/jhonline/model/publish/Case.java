package com.xcompany.jhonline.model.publish;

import java.io.Serializable;

/**
 * Created by xieliang on 2019/1/1 23:05
 */
public class Case implements Serializable {

    private static final long serialVersionUID = 8513446664198372508L;
    private String entry;
    private String imgUrl;
    private String illustrate;

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getIllustrate() {
        return illustrate;
    }

    public void setIllustrate(String illustrate) {
        this.illustrate = illustrate;
    }
}
