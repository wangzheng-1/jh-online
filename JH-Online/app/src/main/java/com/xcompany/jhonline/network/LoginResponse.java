package com.xcompany.jhonline.network;

import java.io.Serializable;

/**
 * Created by xieliang on 2018/11/22 23:19
 */
public class LoginResponse implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;

    public Integer md;
    public String code;
    public String uid;

    public Integer getMd() {
        return md;
    }

    public void setMd(Integer md) {
        this.md = md;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "md=" + md +
                ", code='" + code + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
