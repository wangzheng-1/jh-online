package com.xcompany.jhonline.utils;

import java.util.Iterator;
import java.util.List;

public class StringUtil {
    public static boolean isEmpty(String str){
        if(str == null || "".equals(str.trim())){
            return true;
        }
        return false;
    }
    public static String join(List<String> stringList, String separator){
        if(stringList == null || stringList.size() <= 0){
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (Iterator<String> iterator = stringList.iterator(); iterator.hasNext();) {
            String string = iterator.next();
            stringBuffer.append(string);
            if(iterator.hasNext()){
                stringBuffer.append(separator == null ? "" : separator);
            }
        }
        return stringBuffer.toString();
    }


}
