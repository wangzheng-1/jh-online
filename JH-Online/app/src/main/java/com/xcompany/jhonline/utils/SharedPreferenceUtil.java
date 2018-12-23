package com.xcompany.jhonline.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {
    private static final String SHAREDPREFERENCES_NAME = "jh_android_sp";

    private static SharedPreferences getPreference(Context context) {
        return context.getApplicationContext().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    /****************************************
     * 城市名
     ***************************************/
    private static final String CITYNAME = "CITYNAME";

    public static void setCityName(Context context, String cityName) {
        if (cityName == null) return;
        getPreference(context).edit().putString(CITYNAME, cityName).apply();
    }

    public static String getCityName(Context context) {
        return getPreference(context).getString(CITYNAME, "");
    }

    /****************************************
     * 城市id
     ***************************************/
    private static final String CITYID = "CITYID";

    public static void setCityId(Context context, String cityId) {
        if (cityId == null) return;
        getPreference(context).edit().putString(CITYID, cityId).apply();
    }

    public static String getCityId(Context context) {
        return getPreference(context).getString(CITYID, "0");
    }
}
