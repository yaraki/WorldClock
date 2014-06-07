package com.arakitech.worldclock;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TimeZoneInterpreter {

    private static TimeZoneInterpreter sInstance;

    private final String mLanguage;
    private final Map<String, String> mTimeZoneSegments = new HashMap<String, String>();

    private TimeZoneInterpreter(Context context) {
        mLanguage = context.getResources().getConfiguration().locale.getLanguage();
        try {
            loadNames(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TimeZoneInterpreter from(Context context) {
        if (null != sInstance && sInstance.mLanguage.equals(
                context.getResources().getConfiguration().locale.getLanguage())) {
            return sInstance;
        }
        return sInstance = new TimeZoneInterpreter(context);
    }

    public String getSimpleName(String timeZoneId) {
        String[] segments = TextUtils.split(timeZoneId, "/");
        return mTimeZoneSegments.get(segments[segments.length - 1]);
    }

    public String getFullName(String timeZoneId) {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (String segment : TextUtils.split(timeZoneId, "/")) {
            if (first) {
                first = false;
            } else {
                builder.append(" / ");
            }
            builder.append(mTimeZoneSegments.get(segment));
        }
        return builder.toString();
    }

    private void loadNames(Context context) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    context.getResources().openRawResource(R.raw.time_zones)));
            String line;
            while (null != (line = reader.readLine())) {
                String[] values = TextUtils.split(line.trim(), ",");
                mTimeZoneSegments.put(values[0], values[1]);
            }
        } finally {
            if (null != reader) {
                reader.close();
            }
        }
    }

}
