package com.annis.baselib.base.http.JsonFormat;

import android.text.TextUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Aswords
 * @Date 09/12/2016
 * @since v0.1
 */
@SuppressWarnings("rawtypes")
public class DateDeserializer implements JsonDeserializer {

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        String str = json.getAsString();
        try {
            if (TextUtils.isEmpty(str)) {
                return null;
            } else if (str.contains("-") && !str.contains(":")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.parse(str);
            } else if (str.contains("AM") || str.contains("PM")) {
                SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy K:m:s a", Locale.ENGLISH);
                return sdf.parse(str);
            } else if (str.contains("-") && str.contains(":")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return sdf.parse(str);
            } else if (str.contains(",")) {
                return parseHour(str);
            } else {
                return new Date(json.getAsLong());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param datdString "Tue Jul 12 2016 12:10:11 GMT+08:00 ";
     * @return 时分秒
     */
    public static Date parseHour(String datdString) {
        datdString = datdString.replace("GMT", "").replaceAll("\\(.*\\)", "");
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss", Locale.ENGLISH);
        try {
            return format.parse(datdString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
