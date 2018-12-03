package com.xcompany.jhonline.model.home;

/**
 * Created by xieliang on 2018/12/1 18:59
 */
public class Dictionary {
    private String key;
    private String value;

    public Dictionary() {
    }

    public Dictionary(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
