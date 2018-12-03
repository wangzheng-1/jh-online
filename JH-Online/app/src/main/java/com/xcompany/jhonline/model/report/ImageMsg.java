package com.xcompany.jhonline.model.report;

import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */

public class ImageMsg {
    private List<String> list;

    public ImageMsg(List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
