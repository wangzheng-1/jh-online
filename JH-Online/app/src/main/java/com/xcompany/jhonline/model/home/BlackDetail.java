package com.xcompany.jhonline.model.home;

import java.io.Serializable;

/**
 * Created by xieliang on 2018/12/23 01:27
 */
public class BlackDetail implements Serializable {

    private static final long serialVersionUID = -4452790362278422797L;

    /**
     * id : 5
     * name : 阿诗丹顿佛挡杀佛1123
     * explain : 士大夫发个梵蒂冈梵蒂冈士大夫士大夫士大夫士大夫士大夫士大夫士大夫士大夫士大夫第三方胜多负少的反倒是士大夫士大夫
     * image : /Uploads/Black/images/2018-12-21/5c1c4dcab9c33.jpg
     * number : 50
     * linkman : 萨芬
     * telephone : 15220689930
     */

    private String id;
    private String name;
    private String explain;
    private String image;
    private String number;
    private String linkman;
    private String telephone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
