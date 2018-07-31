package com.glaserproject.spacexploration.Utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Class for methods to format the date from millis and so
 */

public class DateUtils {

    /**
     * @param dateToFormat is millis to be formated
     * @return formated string
     */
    public static String formateDate(long dateToFormat){

        Date date = new java.util.Date(dateToFormat*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return sdf.format(date);

    }

    /**
     *
     * @param millisUntilFinished is time till zero for timer
     * @return formatted time until T = 00
     */
    public static String formateMillisTo(long millisUntilFinished) {

        //create Strings from millis
        String daysToLaunch = String.format(Locale.getDefault(), "%02d", TimeUnit.MILLISECONDS.toDays(millisUntilFinished));
        String hoursToLaunch = String.format(Locale.getDefault(), "%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished)));
        String minutesToLaunch = String.format(Locale.getDefault(), "%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)));
        String secsToLaunch = String.format(Locale.getDefault(), "%02d", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

        //String Builder to put that into one string
        StringBuilder sb = new StringBuilder();

        sb.append("T - ");

        //don't display days if there is none
        if (!daysToLaunch.equals("00")){
            sb.append(daysToLaunch);
            sb.append("d ");
        }
        sb.append(hoursToLaunch);
        sb.append(":");
        sb.append(minutesToLaunch);
        sb.append(":");
        sb.append(secsToLaunch);

        return sb.toString();
    }
}
