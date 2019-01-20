package com.xcompany.jhonline.model.publish;

public enum PublishTypeEnum {

    SUBPACKAGE_QUALITY_TEAM(0,"分包单位（优质班组）"),
    SUBPACKAGE_BID(1,"分包单位（招标）"),
    DEVICE_LEASE(2,"设备器材（出租）"),
    DEVICE_HUNT_LEASE(3,"设备器材（寻租）"),
    PRODUCT_QUALITY_SUPPLIER(4,"建材产品（优质供应商）"),
    PRODUCT_PURCHASE(5,"建材产品（采购）"),
    APTITUDE_ANCHORED(6,"证照资质（挂靠）"),
    APTITUDE_HANDLE(7,"证照资质（资质办理）"),
    LABOUR_RECRUITING(8,"劳务工人（招工）"),
    LABOUR_JOB_HUNT(9,"劳务工人（找活）"),
    SKILL_RECRUITING(10,"技管人才（招聘）"),
    SKILL_JOB_HUNT(11,"技管人才（求职）"),
    CONSTRUCTION_MATCH(12,"工地配套"),
    MAJOR_SKILL(13,"专业技能"),
            ;

    private Integer type;

    private String name;

    public Integer getType(){
        return type;
    }

    public String getName() {
        return name;
    }

    PublishTypeEnum(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    public static PublishTypeEnum getPublishTypeEnum(Integer type, String name) {
        for (PublishTypeEnum publishTypeEnum: PublishTypeEnum.values()) {
            if (publishTypeEnum.name == name && publishTypeEnum.type == type) {
                return publishTypeEnum;
            }
        }
        return null;
    }
    public static PublishTypeEnum getPublishTypeEnum(Integer type) {
        if(type == null){
            return null;
        }
        for (PublishTypeEnum publishTypeEnum: PublishTypeEnum.values()) {
            if (type.equals(publishTypeEnum.type)) {
                return publishTypeEnum;
            }
        }
        return null;
    }
}
