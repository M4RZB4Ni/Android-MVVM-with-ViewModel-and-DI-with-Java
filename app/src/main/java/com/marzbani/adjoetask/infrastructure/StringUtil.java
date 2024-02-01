package com.marzbani.adjoetask.infrastructure;

import android.text.format.DateUtils;
import android.util.Log;

public class StringUtil {

    private static final String TAG = "StringUtil";

    public static String formatTime(long time){
        String formattedTime = DateUtils.getRelativeTimeSpanString(time , System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS).toString();
        Log.i(TAG, "formattedTime : " + formattedTime);
        return formattedTime;
    }
}
