package com.xcompany.jhonline.model.me;

import com.xcompany.jhonline.model.base.Model;

import java.io.Serializable;
import java.util.List;

public class PersonCenterBean extends Model implements Serializable {
    private static final long serialVersionUID = 7553074963902204865L;

    private String id;

    private List<String>  images;

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
