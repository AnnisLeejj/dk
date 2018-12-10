package com.annis.baselib.utils.ext_utils;

import android.text.TextUtils;

public class StringUtils {
    public static String avoidEmpty(String content) {
        return TextUtils.isEmpty(content) ? "无" : content;
    }
}
