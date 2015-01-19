package com.imdea.fioravantti.guido.ecousin_ptat.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeTools {

    public String getBeautifulDate () {
        String date = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss").format(new Date());

        return date;
    }

    public String getBeautifulTimestamp (long timestamp) {
        int days        = (int) TimeUnit.SECONDS.toDays(timestamp);
        long hours      =       TimeUnit.SECONDS.toHours(timestamp) - (days *24);
        long minutes    =       TimeUnit.SECONDS.toMinutes(timestamp) - (TimeUnit.SECONDS.toHours(timestamp)* 60);
        long seconds    =       TimeUnit.SECONDS.toSeconds(timestamp) - (TimeUnit.SECONDS.toMinutes(timestamp) *60);

        String toReturn = "";

        if(days != 0)       toReturn += days    + " days ";
        if(hours != 0)      toReturn += hours   + " hours ";
        if(minutes != 0)    toReturn += minutes + " minutes ";

        if(minutes == 0)
            toReturn += seconds + " seconds";
        else
            toReturn += "and " + seconds + " seconds";

        return toReturn;
    }
}
