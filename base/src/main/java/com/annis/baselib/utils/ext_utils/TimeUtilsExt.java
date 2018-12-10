package com.annis.baselib.utils.ext_utils;

import com.annis.baselib.utils.utils_haoma.TimeUtils;

import java.util.Date;

public class TimeUtilsExt extends TimeUtils {
    /**
     * 1538979787L   ->  2018-10-08 14:23:07
     * 9000000000L   ->  2255-03-15 00:00:00
     * 如果带有毫秒 时间戳就该 再 * 1000
     */
    public static Long WithoutMillisecond = 9000000000L;//

    public static int getIntervalDay(Date newOne, Date oldOne) {
        return (int) ((newOne.getTime() - oldOne.getTime()) / (1000 * 3600 * 24));
    }

    public static int getIntervalDay(Long newOne, Long oldOne) {
        long adatTime = (newOne > WithoutMillisecond ? 1000 : 1) * (3600 * 24);
        return (int) ((newOne - oldOne) / adatTime);
    }
}
