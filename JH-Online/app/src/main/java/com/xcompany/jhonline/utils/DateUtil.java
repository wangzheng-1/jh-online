package com.xcompany.jhonline.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String dateParseStr(Date date){
        if(date == null){
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }
}
