package com.annis.baselib.utils.ext_utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证 邮箱,手机号
 */
public class InfoCheck {
    /**
     * 邮箱检测
     *
     * @param string
     * @return
     */
    public static boolean isEmail(String string) {
        if (string == null)
            return false;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }

    /**
     * 手机号检测
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
//        String regex = "^((13[0-9])|(15[^4,\\D])|(18[0-9])|(14[5,7])|(17[0,1,3,5-8]))\\d{8}$";
        String regex = "^1\\d{10}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 固定电话验证带区号
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isTelValid(String phoneNumber) {
        boolean isValid = false;
        //String expression = "((^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]+\\d{3,8})?([-_－—]+\\d{1,7})?$)|(^0?1[35]\\d{9}$))";        
        String expression = "((^((0\\d{2,3})-)(\\d{7,8})(-(\\d{3,}))?$))";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * 是否是正确的手机号或者座机
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        String expression = "((^((0\\d{2,3})-)(\\d{7,8})(-(\\d{3,}))?$)|(^((13[0-9])|(15[^4,\\D])|(18[0-9])|(14[5,7])|(17[0,1,3,5-8]))\\d{8}$))";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


}
