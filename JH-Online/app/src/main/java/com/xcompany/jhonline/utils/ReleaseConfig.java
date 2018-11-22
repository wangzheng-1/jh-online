package com.xcompany.jhonline.utils;

/**
 * Created by wangzheng .
 *  用户多环境的定义
 */
public class ReleaseConfig {

    private static String RELEASE_TYPE = "DEBUG";

    public enum ReleaseType {
        DEV,        //开发环境
        SIT,        //测试环境
        UAT,        //验收环境
        PRD,        //生产环境
        DEFAULT,    //默认环境
    }


    public static String baseUrl() {
        return ReleaseConfig.getInstance().getBaseUrl();
    }

    private static ReleaseConfig singleInstance = null;

    public static ReleaseConfig getInstance() {
        if (singleInstance != null) {
            return singleInstance;

        }
        synchronized (ReleaseConfig.class) {
            if (singleInstance != null) {
                return singleInstance;
            }
            ReleaseConfig instance = new ReleaseConfig();
            instance.init();

            singleInstance = instance;
        }
        return singleInstance;
    }

    //实体类定义
    private ReleaseType releaseType;
    private URLInfo urlInfo;

    private ReleaseConfig() {
    }

    private void init() {
        ReleaseType releaseType = ReleaseType.valueOf(RELEASE_TYPE);
        this.releaseType = releaseType;
        this.urlInfo = new URLInfo(this.releaseType);
    }


    public ReleaseType getReleaseType() {
        return releaseType;
    }

    public String getBaseUrl() {
        return this.urlInfo.getBaseUrl();
    }

    private static class URLInfo {
        private String baseUrl;

        URLInfo(ReleaseType releaseType) {
            switch (releaseType) {
                case DEV:
                    this.baseUrl = "http://192.168.1.1:8080/jhzx/";
                    break;
                case SIT:
                    this.baseUrl = "http://192.168.1.1:8080/jhzx/";
                    break;
                case UAT:
                    this.baseUrl = "http://192.168.1.1:8080/jhzx/";
                    break;
                case PRD:
                    this.baseUrl = "http://192.168.1.1:8080/jhzx/";
                    break;
                default:
                    this.baseUrl = "http://192.168.1.1:8080/jhzx/";
                    break;
            }
        }

        public String getBaseUrl() {
            return baseUrl;
        }

    }
}