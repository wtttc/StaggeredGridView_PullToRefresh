package com.willwang.bitwaterfall.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtils {
    public static SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd HH:mm");
    public static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String timestamp2str(Timestamp time, String pattern) {
        if (time == null) {
            // throw new IllegalArgumentException("Timestamp is null");
            return "";
        }
        if (pattern != null && !"".equals(pattern)) {
            if (!"yyyyMMddHHmmss".equals(pattern) && !"yyyy-MM-dd HH:mm:ss".equals(pattern)
                    && !"yyyy-MM-dd".equals(pattern) && !"MM/dd/yyyy".equals(pattern) && !"MM-dd HH:mm".equals(pattern)
                    && !"MM.dd HH:mm".equals(pattern) && !"yyyy-MM-dd HH:mm".equals(pattern)) {
                // throw new IllegalArgumentException("Date format [" + pattern
                // + "] is invalid");
                return "";
            }
        } else {
            pattern = DEFAULT_PATTERN;
        }

        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        cal.setTime(time);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(cal.getTime());
    }

    public static Timestamp str2Timestamp(String timeStr, String pattern) {
        Timestamp result = null;
        if (timeStr == null) {
            // throw new IllegalArgumentException("Timestamp is null");
            return null;
        }
        if (pattern != null && !"".equals(pattern)) {
            if (!"yyyyMMddHHmmss".equals(pattern) && !"yyyy-MM-dd HH:mm:ss".equals(pattern)
                    && !"MM/dd/yyyy HH:mm:ss".equals(pattern) && !"yyyy-MM-dd".equals(pattern)
                    && !"MM/dd/yyyy".equals(pattern) && !"MM-dd HH:mm".equals(pattern)) {
                // throw new IllegalArgumentException("Date format [" + pattern
                // + "] is invalid");
                return null;
            }
        } else {
            pattern = DEFAULT_PATTERN;
        }

        Date d = null;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            d = sdf.parse(timeStr);
            result = new Timestamp(d.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getTime(long time) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd HH:mm");
        return sdf1.format(new Date(time));
    }

    public static String getCustomTime(long time) {
        long timeSpace = (System.currentTimeMillis() - time) / 1000;
        if (timeSpace < 10) {
            return "刚刚";
        } else if (timeSpace < 60) {
            return timeSpace + "秒前";
        } else if (timeSpace < 60 * 60) {
            return ((int) (timeSpace / 60)) + "分钟前";
        } else if (timeSpace < 24 * 60 * 60) {
            return ((int) (timeSpace / 60 / 60)) + "小时前";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.format(new Date(time));
        }
    }

    public static String getImageTempFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    /**
     * 验证手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobile) {
        try {
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,2,5-9]))\\d{8}$");
            Matcher m = p.matcher(mobile);
            return m.matches();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean validateEmail(String email) {
        String EmailRegEx = "^[_\\.0-9a-zA-Z+-]+@([0-9a-zA-Z]+[0-9a-zA-Z-]*\\.)+[a-zA-Z]{2,4}$";
        Pattern pattern = Pattern.compile(EmailRegEx);
        return pattern.matcher(email).find();
    }


}
