package com.xcompany.jhonline.model.publish;

import com.xcompany.jhonline.model.base.Model;

import java.io.Serializable;

public class CheckboxItemBean extends Model implements Serializable {
    private static final long serialVersionUID = 7553074963902204865L;
    private int id;
    private String name;
    private boolean isCheck = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
