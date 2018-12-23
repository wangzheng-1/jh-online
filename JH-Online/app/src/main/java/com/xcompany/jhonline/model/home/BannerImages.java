package com.xcompany.jhonline.model.home;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xieliang on 2018/12/15 01:08
 */
public class BannerImages implements Serializable {

    private static final long serialVersionUID = -6435539023955388016L;
    /**
     * id : 1
     * images : ["/Uploads/Ad/images/2018-09-26/5baae2998c177.png","/Uploads/Ad/images/2018-09-26/5bab29c022325.png","/Uploads/Ad/images/2018-09-26/5bab29c41cc4c.png","/Uploads/Ad/images/2018-10-09/5bbc63fab6abb.png"]
     */

    private String id;
    private List<String> images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
