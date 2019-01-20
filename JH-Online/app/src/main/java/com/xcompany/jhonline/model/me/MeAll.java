package com.xcompany.jhonline.model.me;

import com.xcompany.jhonline.model.base.Model;

import java.io.Serializable;

public class MeAll extends Model implements Serializable {
    private static final long serialVersionUID = 7553074963902204865L;

    /**
     * 关注
     */
    private String give;

    /**
     * 关注
     */
    private String integral;


    /**
     * 关注
     */
    private String issue;

    /**
     * 关注
     */
    private String sign;

    public String getGive() {
        return give;
    }

    public void setGive(String give) {
        this.give = give;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
