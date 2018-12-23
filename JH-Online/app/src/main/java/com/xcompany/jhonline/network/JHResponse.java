package com.xcompany.jhonline.network;

import java.io.Serializable;

/**
 * Created by xieliang on 2018/11/22 23:19
 */
public class JHResponse<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;

    public int code;
    public T msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "JHResponse{" +
                "code=" + code +
                ", msg=" + msg +
                '}';
    }
}
