package com.xcompany.jhonline.network;

import java.io.Serializable;

/**
 * Created by xieliang on 2018/11/22 23:19
 */
public class FileResponse<T> extends JHResponse<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;

    private String ext;

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    @Override
    public String toString() {
        return "FileResponse{" +
                "code=" + code +
                ", msg=" + msg +
                ", ext='" + ext + '\'' +
                '}';
    }
}
