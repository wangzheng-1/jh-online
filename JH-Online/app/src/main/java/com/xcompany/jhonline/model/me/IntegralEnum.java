package com.xcompany.jhonline.model.me;

public enum IntegralEnum {

    SIGN(0,"签到赠送"),
    PUBLISH(1,"发布赠送"),
    REGISTER(2,"注册赠送 "),
    LOOK_UP_MOBILE(3,"查看电话消耗"),

            ;

    private Integer type;

    private String name;

    public Integer getType(){
        return type;
    }

    public String getName() {
        return name;
    }

    IntegralEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static IntegralEnum getIntegralEnum(Integer type,String name) {
        for (IntegralEnum stockChangeTypeEnum: IntegralEnum.values()) {
            if (stockChangeTypeEnum.name == name && stockChangeTypeEnum.type == type) {
                return stockChangeTypeEnum;
            }
        }
        return null;
    }
    public static IntegralEnum getIntegralEnum(Integer type) {
        for (IntegralEnum stockChangeTypeEnum: IntegralEnum.values()) {
            if (type.equals(stockChangeTypeEnum.type)) {
                return stockChangeTypeEnum;
            }
        }
        return null;
    }
}
