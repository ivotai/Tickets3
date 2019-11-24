package com.unicorn.tickets.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class BasicAuthorizationUtils {

    private static final String HTTP_HEADER_TIME_ZONE = "GMT";
    private static final String HTTP_HEADER_DATE_FORMAT = "EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'z";

    public static Date parseDate(String dateStr) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat(HTTP_HEADER_DATE_FORMAT, Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone(HTTP_HEADER_TIME_ZONE));
//        if (StringUtils.isEmpty(dateStr)) {
//            return null;
//        }
        return dateFormat.parse(dateStr);
    }

    public static String formatDate(Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(HTTP_HEADER_DATE_FORMAT, Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone(HTTP_HEADER_TIME_ZONE));
        return dateFormat.format(date);
    }


    /**
     * 获取验证
     *
     * @param method
     * @param url
     * @param date
     */
    public static String getAuthorization(String method, String url, Date date, String secret) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(HTTP_HEADER_DATE_FORMAT, Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone(HTTP_HEADER_TIME_ZONE));
        String stringToSign = method.toUpperCase() + " " + url + " " + dateFormat.format(date);
        return getSignature(stringToSign.getBytes(), secret.getBytes());
    }

    /**
     * 生产签名
     *
     * @param data
     * @param key
     */
    public static String getSignature(byte[] data, byte[] key) {

//        HMac mac = new HMac(HmacAlgorithm.HmacSHA1, key);
//        String macHex1 = mac.digestHex(data);
//        return new String(Base64.encodeBase64(macHex1.getBytes()));
        return "";
    }
}