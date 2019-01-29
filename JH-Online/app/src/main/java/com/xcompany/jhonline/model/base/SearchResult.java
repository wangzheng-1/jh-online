package com.xcompany.jhonline.model.base;

import java.io.Serializable;

/**
 * Created by xieliang on 2019/1/29 22:59
 */
public class SearchResult implements Serializable {

    private static final long serialVersionUID = 1664570318386864556L;

    /**
     * fid : 1
     * name : 土<font color='red'>木工</font>程师 - 注册岩土工程师
     * type : 6
     * addtime : 2019.01.29 08:58
     */

    private String fid;
    private String name;
    private String type;
    private String addtime;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }
}
