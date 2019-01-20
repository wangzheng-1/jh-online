package com.xcompany.jhonline.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by xieliang on 2019/1/10 21:59
 */
public class SnCalUtils {
    public static String getSnkey(String ip) {
        String snKey = null;
        try {
            Map paramsMap = new LinkedHashMap<String, String>();
            paramsMap.put("ip", ip);
            paramsMap.put("ak", "tKlirU8a30iVYnGyGh4ah9esteZRBkZy");
            String paramsStr = toQueryString(paramsMap);
            String wholeStr = new String("/location/ip?" + paramsStr + "xuXdvwrABEMEi11yQjFQp20MKHs6Xhqw");
            String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
            snKey = MD5(tempStr);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return snKey;
    }

    // 对Map内所有value作utf8编码，拼接返回结果
    public static String toQueryString(Map<?, ?> data)
            throws UnsupportedEncodingException {
        StringBuffer queryString = new StringBuffer();
        for (Map.Entry<?, ?> pair : data.entrySet()) {
            queryString.append(pair.getKey() + "=");
            queryString.append(URLEncoder.encode((String) pair.getValue(),
                    "UTF-8") + "&");
        }
        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }
        return queryString.toString();
    }

    // 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                        .substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}
