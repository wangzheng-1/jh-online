package com.xcompany.jhonline.model.report;

import java.io.Serializable;
import java.util.List;

public class MediaBaseBeanSerial implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;

    private List<MediaBaseBean> mediaBaseBeanList;

    public List<MediaBaseBean> getMediaBaseBeanList() {
        return mediaBaseBeanList;
    }

    public void setMediaBaseBeanList(List<MediaBaseBean> mediaBaseBeanList) {
        this.mediaBaseBeanList = mediaBaseBeanList;
    }
}
