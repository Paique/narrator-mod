package net.paiique.brpacks.narrator.util;

import java.util.Calendar;

public class DateUtil {
    public static String getActualDate() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        return day + "/" + month + "/" + year + " " + hour + ":" + minute + ":" + second;
    }
}
