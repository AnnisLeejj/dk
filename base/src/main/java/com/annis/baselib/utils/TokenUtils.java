package com.annis.baselib.utils;

import com.annis.baselib.utils.utils_haoma.SPUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TokenUtils {
    public static final String TokenFileName = "TokenFileName";


    private static final class SPConstants {
        public static final String HEADER_USERID = "HEADER_USERID";
        public static final String Token = "TokenStr";
    }

    public static SPUtils utils = null;

    public static void saveToken(String token) {
        if (utils == null) {
            utils = new SPUtils(TokenFileName);
        }
        utils.putString(SPConstants.Token, token);
    }

    public static String getToken() {
        if (utils == null) {
            utils = new SPUtils(TokenFileName);
        }
        return utils.getString(SPConstants.Token, "");
    }

    public static void saveUserId(String token) {
        if (utils == null) {
            utils = new SPUtils(TokenFileName);
        }
        utils.putString(SPConstants.HEADER_USERID, token);
    }

    public static String getUserId() {
        if (utils == null) {
            utils = new SPUtils(TokenFileName);
        }
        return utils.getString(SPConstants.HEADER_USERID, "");
    }

    public static String getSyslog() {
        return getUserId() + ",APP,APP";
    }

    /**
     * 获取时间戳 2018-08-30 22:35:00
     *
     * @return
     */
    public static String getTimestamp(Date date) {
        if (date == null) return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd  HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
